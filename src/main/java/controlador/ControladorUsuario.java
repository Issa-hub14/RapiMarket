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
import util.ReceptorVozVosk;
import util.ReceptorVozVosk.Comando;
import modelo.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador encargado de gestionar la interacción entre la vista, el modelo y los servicios relacionados con los usuarios.
 * 
 * @author isabe
 */

public class ControladorUsuario {

    private final VistaUsuario vista;
    private final ClienteAPIService apiService;
    private final IModelo modelo;
    private ClienteRegistrado clienteActual;
    private final LectorVoz lectorVoz;
    private final ReceptorVozVosk receptorVoz;

    public ControladorUsuario(VistaUsuario vista, ClienteAPIService apiService, IModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.apiService = apiService;
        this.lectorVoz = LectorVoz.getInstance();
        this.receptorVoz = ReceptorVozVosk.getInstance();

        iniciarEventos();
        new Thread(() -> {
            lectorVoz.hablar("Modo ingreso de usuario.");
        }).start();
    }

    /**
     * Inicializa todos los eventos y listeners de la vista
     */
    private void iniciarEventos() {

        vista.addBuscarListener(e -> buscarCliente());
        vista.addInvitadoListener(e -> continuarInvitado());
        vista.addContinuarListener(e -> continuar());
        vista.addVolverListener(e -> volver());
        vista.addMicrofonoListener(e -> manejarMicrofono());
    }

    /**
     * Busca un cliente registrado utilizando el ID ingresado
     */
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
    
    /**
     * Continuar el proceso como ClienteInvitado (sin ID)
     */
    private void continuarInvitado() {
        clienteActual = null;
        vista.limpiarCampos();
        ClienteInvitado invitado = new ClienteInvitado("Invitado");
        lectorVoz.hablar("Continuando como invitado");

        vista.dispose();

        VistaPedidoOnline pedido = new VistaPedidoOnline();
        new ControladorPedido(pedido, modelo, clienteActual);
        pedido.setVisible(true);

    }
    
    /**
     * Continua el proceso utilizando el cliente registrado actual 
     */
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
    
    /**
     * Regresa a la vistaPrincipal de la aplicación
     */
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
    
    /**
     * Activa o desactiva el reconocimiento por voz
     */
    private void manejarMicrofono() {
        if (receptorVoz.isGrabando()) {
            receptorVoz.detenerGrabacion();
            lectorVoz.hablar("Microfono desactivado");
            vista.actualizarEstado("Microfono desactivado");
        } else {
            try {
                receptorVoz.setManejadorComando(cmd -> {
                    switch (cmd) {
                        case BUSCAR ->
                            buscarCliente();
                        case CONTINUAR ->
                            continuar();
                        case VOLVER ->
                            volver();
                        default ->
                            lectorVoz.hablar("Comando no reconocido.");
                    }
                });

                receptorVoz.setManejadorTexto(texto -> {
                    if (texto == null || texto.isEmpty()) {
                        return;
                    }
                    if (texto.toLowerCase().contains("invitado")) {
                        continuarInvitado();
                    } else {
                        String numero = convertirPalabraANumero(texto);
                        if (numero.matches("\\d+")) {
                            vista.setTextoBusqueda(numero);
                            buscarCliente();
                        } else {
                            lectorVoz.hablar("No entendi. Di: id digito por digito, invitado, continuar o volver");
                        }
                    }
                });

                receptorVoz.iniciarGrabacion();
                lectorVoz.hablar("Microfono activado.");
                vista.actualizarEstado("Escuchando...");
            } catch (Exception ex) {
                vista.mostrarError("Error: " + ex.getMessage());
            }
        }
    }
    /**
     * Convierte numeros escritos en palabras a su representación numérica   
     * @param texto Texto ingresado por el usuario en palabras
     * @return Cadena con los numeros convertidos o el texto original si alguna palabra no es reconocida
     */
    public String convertirPalabraANumero(String texto) {
        String lower = texto.toLowerCase().trim();

        Map<String, String> mapa = new HashMap<>();
        mapa.put("uno", "1");
        mapa.put("dos", "2");
        mapa.put("tres", "3");
        mapa.put("cuatro", "4");
        mapa.put("cinco", "5");
        mapa.put("seis", "6");
        mapa.put("siete", "7");
        mapa.put("ocho", "8");
        mapa.put("nueve", "9");
        mapa.put("cero", "0");

        String[] palabras = lower.split("\\s+");
        String resultado = "";

        for (String palabra : palabras) {
            String digito = mapa.get(palabra);
            if (digito == null) {
                return texto;
            }
            resultado = resultado + digito;
        }

        return resultado;
    }

}
