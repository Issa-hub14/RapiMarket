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
import util.ReceptorVozVosk;
import util.ReceptorVozVosk.Comando;
import java.util.List;
import servicio.PuntosDBService;
import servicio.VentaTXTService;

public class ControladorPedido {

    private final VistaPedidoOnline vista;
    private final IModelo modelo;
    private final LectorVoz lectorVoz;
    private final ReceptorVozVosk receptorVoz;
    private final PuntosDBService puntosDBService;
    private final ClienteRegistrado clienteActual;
    private final VentaTXTService ventaTXTService;
    private Comando ultimoComando = null;

    public ControladorPedido(VistaPedidoOnline vista, IModelo modelo, ClienteRegistrado clienteActual) {
        this.vista = vista;
        this.modelo = modelo;
        this.lectorVoz = LectorVoz.getInstance();
        this.receptorVoz = ReceptorVozVosk.getInstance();
        this.puntosDBService = new PuntosDBService();
        this.clienteActual = clienteActual;
        this.ventaTXTService = new VentaTXTService();

        conectarBotones();
        vista.mostrarResultados(modelo.obtenerCatalogo());
        actualizarCarritoEnVista();

        new Thread(() -> {
            lectorVoz.hablar("Modo compra en línea. Que producto buscas?");
        }).start();
    }

    private void conectarBotones() {
        vista.addBtnBuscarListener(e -> buscar());
        vista.addBtnAgregarListener(e -> agregarSeleccionado());
        vista.addBtnEliminarListener(e -> eliminarSeleccionado());
        vista.addBtnConfirmarListener(e -> confirmarPedido());
        vista.addBtnLeerCarritoListener(e -> leerCarrito());
        vista.addBtnVolverListener(e -> volver());
        vista.addBtnMicrofonoListener(e -> manejarMicrofono());

        vista.addDobleClickProductoListener(e -> agregarSeleccionado());
    }

    private void buscar() {
        String termino = vista.getTextoBusqueda();
        if (termino.isBlank()) {
            lectorVoz.hablar("Escribe o di que producto buscas.");
            return;
        }
        List<Producto> resultados = modelo.buscarProductos(termino);
        vista.mostrarResultados(resultados);
        vista.actualizarEstado("Búsqueda: " + termino);
        if (!resultados.isEmpty()) {
            lectorVoz.hablar("Encontre " + resultados.size() + " productos. Primer resultado: " + resultados.get(0).getNombre());
        }
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
                lectorVoz.hablar("Producto no encontrado");
                throw new IllegalArgumentException("Producto no encontrado");
            }

            modelo.obtenerCarrito().agregarProducto(p);
            actualizarCarritoEnVista();
            lectorVoz.hablar("Producto agregado " + nombre + " Carrito actualizado. Total: " + modelo.obtenerCarrito().obtenerTotal() + " pesos");
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
        lectorVoz.hablar("Producto eliminado " + nombre + " Carrito actualizado. Total: " + modelo.obtenerCarrito().obtenerTotal() + " pesos");

    }

    private void confirmarPedido() {
        Carrito carrito = modelo.obtenerCarrito();
        if (carrito.getProductos().isEmpty()) {
            lectorVoz.hablar("Tu carrito esta vacio. Agrega productos primero.");
            return;
        }

        String resumen = carrito.obtenerResumenParaVoz();
        vista.actualizarEstado("Pedido confirmado — Total: $"
                + String.format("%,.0f", carrito.obtenerTotal()));

        double totalCompra = carrito.obtenerTotal();
        int puntosGanados = (int) totalCompra / 1000;
        if (clienteActual != null) {
            int puntosTotales = puntosDBService.actualizarPuntos(clienteActual.getId(), clienteActual.getNombre(), totalCompra);
            lectorVoz.hablar(resumen + "Pedido confirmado. Gracias por tu compra. Ahora tienes" + puntosTotales + " puntos acumulados. "
                    + "Carrito actualizado. Total: " + modelo.obtenerCarrito().obtenerTotal() + " pesos");
        } else {
            lectorVoz.hablar(resumen + "Pedido confirmado. Gracias por tu compra.");
        }
        ventaTXTService.guardarVenta(carrito);
        carrito.vaciar();
        actualizarCarritoEnVista();

    }

    private void leerCarrito() {
        String resumen = modelo.obtenerCarrito().obtenerResumenParaVoz();
        lectorVoz.hablar(resumen);
        vista.actualizarEstado("Leyendo carrito...");
    }

    private void volver() {
        if (receptorVoz.isGrabando()) {
            receptorVoz.detenerGrabacion();
        }
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

    private void manejarMicrofono() {
        if (receptorVoz.isGrabando()) {
            receptorVoz.detenerGrabacion();
            lectorVoz.hablar("Microfono desactivado");
            vista.actualizarEstado("Microfono desactivado");
        } else {
            try {
                receptorVoz.setEnviarProductoParaAgregar(true);
                receptorVoz.setEnviarProductoParaEliminar(true);

                receptorVoz.setManejadorComando(cmd -> {
                    ultimoComando = cmd;
                    switch (cmd) {
                        case AGREGAR ->{
                            }
                        case ELIMINAR ->{
                        }
                        case CONFIRMAR ->
                            confirmarPedido();
                        case LEER_CARRITO ->
                            leerCarrito();
                        case VOLVER ->
                            volver();
                        case SIGUIENTE, REPETIR, LEER, CONTINUAR -> {
                            lectorVoz.hablar("Este comando no está disponible en el supermercado. Ve a tu lista de compras.");
                        }
                    }
                });

                receptorVoz.setManejadorTexto(producto -> {
                    if (producto == null || producto.isEmpty()) {
                        return;
                    }
                    if (ultimoComando == Comando.AGREGAR) {
                        agregarProductoDirecto(producto);
                        ultimoComando = null;
                    } else if (ultimoComando == Comando.ELIMINAR) {
                        eliminarProductoDirecto(producto);
                        ultimoComando = null;
                    } else {
                        vista.setTextoBusqueda(producto);
                        buscar();
                    }
                    }
                    );

                receptorVoz.iniciarGrabacion();
                    lectorVoz.hablar("Microfono activado.");
                    vista.actualizarEstado("Escuchando...");
                } catch (Exception ex) {
                vista.mostrarError("Error: " + ex.getMessage());
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

    private void agregarProductoDirecto(String producto) {
        if (producto == null || producto.isEmpty()) {
            lectorVoz.hablar("No entendí qué producto quieres agregar.");
            return;
        }

        List<Producto> resultados = modelo.buscarProductos(producto);
        if (resultados.isEmpty()) {
            lectorVoz.hablar(producto + " no está disponible en el catálogo.");
            return;
        }

        Producto p = resultados.get(0);
        modelo.obtenerCarrito().agregarProducto(p);
        actualizarCarritoEnVista();
        lectorVoz.hablar(p.getNombre() + " agregado al carrito. Total: " + (int) modelo.obtenerCarrito().obtenerTotal() + " pesos");
    }

    private void eliminarProductoDirecto(String producto) {
        if (producto == null || producto.isEmpty()) {
            lectorVoz.hablar("No entendí qué producto quieres eliminar.");
            return;
        }

        Carrito carrito = modelo.obtenerCarrito();
        String productoEncontrado = null;
        for (Producto p : carrito.getProductos()) {
            if (p.getNombre().toLowerCase().contains(producto.toLowerCase())) {
                productoEncontrado = p.getNombre();
                break;
            }
        }

        if (productoEncontrado == null) {
            lectorVoz.hablar(producto + " no está en tu carrito.");
            return;
        }

        carrito.eliminarProducto(productoEncontrado);
        actualizarCarritoEnVista();
        lectorVoz.hablar(productoEncontrado + " eliminado del carrito. Total: " + (int) carrito.obtenerTotal() + " pesos");
    }
}
