/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author isabe
 */
import modelo.ClienteInvitado;
import modelo.ClienteRegistrado;
import servicio.ClienteAPIService;
import vista.VistaPedidoOnline;
import vista.VistaUsuario;
import util.LectorVoz;
import modelo.*;

public class ControladorUsuario {

    private VistaUsuario vista;
    private ClienteAPIService apiService;
    private final IModelo modelo;
    private ClienteRegistrado clienteActual;
    private final LectorVoz lectorVoz;

    public ControladorUsuario(VistaUsuario vista, ClienteAPIService apiService, IModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.apiService = apiService;
        this.lectorVoz = LectorVoz.getInstance();

        iniciarEventos();
        new Thread(() -> {
            lectorVoz.hablar("Modo ingreso de usuario.");
        }).start();
    }

    private void iniciarEventos() {

        vista.addBuscarListener(e -> buscarCliente());
        vista.addInvitadoListener(e -> continuarInvitado());
        vista.addContinuarListener(e -> continuar());
        vista.addVolverListener(e -> volver());
        vista.addMicrofonoListener(e -> activarMicrofono());
    }

    private void buscarCliente() {

        String id = vista.getIdCliente();
        if (id.isBlank()) {
            vista.actualizarEstado("Ingresa un ID válido");
            lectorVoz.hablar("Ingrese un ID válido");
            return;
        }
        try {
            int idNumero = Integer.parseInt(id);
            ClienteRegistrado cliente = apiService.getCliente(idNumero);
            if (cliente != null) {
                clienteActual = cliente;
                vista.mostrarCliente(cliente);
                vista.actualizarEstado("Cliente encontrado correctamente");
                lectorVoz.hablar("Cliente encontrado " + cliente.getNombre()
                );

            } else {
                vista.actualizarEstado("No se encontró el cliente");
                lectorVoz.hablar("Cliente no encontrado ");
            }

        } catch (NumberFormatException e) {
            vista.mostrarError("El ID debe ser numérico");

        } catch (Exception e) {
            vista.mostrarError("Error al conectar con la API" + e.getMessage()
            );

        }
    }

    private void continuarInvitado() {
        ClienteInvitado invitado = new ClienteInvitado("Invitado");
        lectorVoz.hablar("Continuando como invitado");
        
        vista.dispose();

        VistaPedidoOnline pedido = new VistaPedidoOnline();
        new ControladorPedido(pedido, modelo, clienteActual);
        pedido.setVisible(true);

    }

    private void continuar() {
        if (clienteActual == null) {
             vista.actualizarEstado(
                    "Primero debes buscar un cliente"
            );
            lectorVoz.hablar("Primero debes buscar un cliente");
            return;
        }
        lectorVoz.hablar("Bienvenido " + clienteActual.getNombre());
     

        vista.dispose();
        

        VistaPedidoOnline pedido = new VistaPedidoOnline();
        new ControladorPedido(pedido, modelo, clienteActual);
        pedido.setVisible(true);

    }

    private void volver() {
        vista.setVisible(false);
        javax.swing.SwingUtilities.invokeLater(() -> {
            for (java.awt.Window w : java.awt.Window.getWindows()) {
                if (w.getClass().getSimpleName().equals("VistaPrincipal")) {
                    w.setVisible(true);
                    break;
                }
            }
        });

    }

    private void activarMicrofono() {
        vista.actualizarEstado(
                "Micrófono activado"
        );
        lectorVoz.hablar(
                "Micrófono activado"
        );

        /*
         Aquí luego conectas Vosk:
         
         String texto = reconocedor.escuchar();
         
         vista.setTextoBusqueda(texto);
         */
    }
    
}
