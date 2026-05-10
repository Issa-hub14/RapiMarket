/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author isabe
 */
import modelo.*;
import vista.VistaPedidoOnline;
import util.LectorVoz;
import util.ReceptorVoz;
import util.ReceptorVoz.Comando;
import java.util.List;

public class ControladorPedido {

    private final VistaPedidoOnline vista;
    private final IModelo modelo;
    private final LectorVoz lectorVoz;
    private final ReceptorVoz receptorVoz;

    public ControladorPedido(VistaPedidoOnline vista, IModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.lectorVoz = LectorVoz.getInstance();
        this.receptorVoz = new ReceptorVoz();

        conectarBotones();
        configurarVoz();
        actualizarCarritoEnVista();
    }

    private void conectarBotones() {
        vista.addBtnBuscarListener(e -> buscar());
        vista.addBtnAgregarListener(e -> agregarSeleccionado());
        vista.addBtnEliminarListener(e -> eliminarSeleccionado());
        vista.addBtnConfirmarListener(e -> confirmarPedido());
        vista.addBtnLeerCarritoListener(e -> leerCarrito());
        vista.addBtnVolverListener(e -> volver());
        vista.addBtnMicrofonoListener(e -> activarMicrofono());

        vista.addDobleClickProductoListener(e -> agregarSeleccionado());
    }

    private void configurarVoz() {
        receptorVoz.setManejadorComando(cmd -> {
            switch (cmd) {
                case BUSCAR ->
                    buscar();
                case AGREGAR ->
                    agregarSeleccionado();
                case ELIMINAR ->
                    eliminarSeleccionado();
                case CONFIRMAR ->
                    confirmarPedido();
                case LEER_CARRITO ->
                    leerCarrito();
                case VOLVER ->
                    volver();
                default ->
                    lectorVoz.hablar(
                            "Di: buscar, agregar, eliminar, confirmar, carrito o volver.");
            }
        });

        receptorVoz.setManejadorTexto(texto -> {
            vista.actualizarEstado("Buscando: " + texto);
            List<Producto> resultados = modelo.buscarProductos(texto);
            vista.mostrarResultados(resultados);
        });

        //receptorVoz.iniciarGrabacion();
    }

    private void buscar() {
        String termino = vista.getTextoBusqueda();
        if (termino.isBlank()) {
            lectorVoz.hablar("Escribe o di qué producto buscas.");
            return;
        }
        List<Producto> resultados = modelo.buscarProductos(termino);
        vista.mostrarResultados(resultados);
        vista.actualizarEstado("Búsqueda: " + termino);
    }

    private void agregarSeleccionado() {
        String seleccionado = vista.getProductoSeleccionado();
        if (seleccionado == null) {
            lectorVoz.hablar("Primero selecciona un producto de la lista.");
            return;
        }

        String nombre = seleccionado.split("  —  ")[0].trim();

        try {
            Producto p = modelo.buscarProductoPorNombre(nombre);
            if (p == null) {
                throw new IllegalArgumentException("Producto no encontrado");
            }

            modelo.obtenerCarrito().agregarProducto(p);
            actualizarCarritoEnVista();
            lectorVoz.hablar(p.getNombre() + " agregado al carrito.");
            vista.actualizarEstado(nombre + " agregado al carrito");

        } catch (Exception e) {
            vista.mostrarError("No se pudo agregar el producto: " + e.getMessage());
        }
    }

    private void eliminarSeleccionado() {
        String seleccionado = vista.getProductoSeleccionado();
        if (seleccionado == null) {
            lectorVoz.hablar("Selecciona un producto para eliminar.");
            return;
        }
        String nombre = seleccionado.split("  —  ")[0].trim();
        modelo.obtenerCarrito().eliminarProducto(nombre);
        actualizarCarritoEnVista();
        lectorVoz.hablar(nombre + " eliminado del carrito.");
    }

    private void confirmarPedido() {
        Carrito carrito = modelo.obtenerCarrito();
        if (carrito.getProductos().isEmpty()) {
            lectorVoz.hablar("Tu carrito está vacío. Agrega productos primero.");
            return;
        }

        String resumen = carrito.obtenerResumenParaVoz();
        lectorVoz.hablar(resumen + ". ¿Confirmas el pedido?");
        vista.actualizarEstado("Pedido confirmado — Total: $"
                + String.format("%,.0f", carrito.obtenerTotal()));

        javax.swing.SwingUtilities.invokeLater(() -> {
            int opcion = javax.swing.JOptionPane.showConfirmDialog(
                    vista,
                    "Tu pedido:\n" + resumen + "\n\n¿Confirmar?",
                    "Confirmar Pedido",
                    javax.swing.JOptionPane.YES_NO_OPTION
            );
            if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                carrito.vaciar();
                actualizarCarritoEnVista();
                lectorVoz.hablar("Pedido confirmado. ¡Gracias por tu compra!");
            }
        });
    }

    private void leerCarrito() {
        String resumen = modelo.obtenerCarrito().obtenerResumenParaVoz();
        lectorVoz.hablar(resumen);
        vista.actualizarEstado("Leyendo carrito...");
    }

    private void volver() {
        receptorVoz.detenerYProcesar();
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
        //lectorVoz.hablar("Habla ahora. Tienes 3 segundos.");
        // vista.actualizarEstado("Escuchando...");
        // receptorVoz.iniciarGrabacion();
        // receptorVoz.detenerYProcesar();

        //vista.actualizarEstado("Listo");
        if (receptorVoz.isGrabando()) {
            new Thread(() -> { receptorVoz.detenerYProcesar();
                vista.actualizarEstado("Audio procesado.");
            }).start();
            vista.actualizarEstado("Procesando...");

        } else {
            try {
                receptorVoz.iniciarGrabacion();
                vista.actualizarEstado("Grabando... pulsa de nuevo para detener.");
            } catch (Exception ex) {
                vista.mostrarError(
                        "Error al acceder al micrófono: " + ex.getMessage()
                );
            }
        }
    }

    private void actualizarCarritoEnVista() {
        Carrito c = modelo.obtenerCarrito();
        StringBuilder sb = new StringBuilder();
        for (Producto p : c.getProductos()) {
            sb.append(p.getCantidad()).append("×  ")
                    .append(p.getNombre())
                    .append("  —  $").append(String.format("%,.0f", p.PrecioTotal()))
                    .append("\n");
        }
        vista.actualizarCarrito(sb.toString(), c.obtenerTotal());
    }
}
