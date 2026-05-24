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

/**
 * Controlador encargado de gestionar el proceso de compra en linea.
 * Permite buscar productos, administrar el carrito y confirmar pedidos.
 * 
 * @author isabe
 */
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

    /**
     * Conecta los botones y eventos de la vista
     */
    private void conectarBotones() {
        vista.addBtnBuscarListener(e -> buscar());
        vista.addBtnAgregarListener(e -> agregarProducto());
        vista.addBtnEliminarListener(e -> eliminarProducto());
        vista.addBtnConfirmarListener(e -> confirmarPedido());
        vista.addBtnLeerCarritoListener(e -> leerCarrito());
        vista.addBtnVolverListener(e -> volver());
        vista.addBtnMicrofonoListener(e -> manejarMicrofono());

        vista.addDobleClickProductoListener(e -> agregarProducto());
    }

    /**
     * Busca productos utilizando el texto ingresado
     */
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

    /**
     * Agrega el producto seleccionado al carrito
     * 
     */
    private void agregarProducto() {
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

    /**
     * Elimina el producto seleccionado del carrito
     */
    private void eliminarProducto() {
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

    /**
     * Confirma el pedido actual y procesa la compra
     */
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
        if (clienteActual != null) {
            int puntosTotales = puntosDBService.actualizarPuntos(clienteActual.getId(), clienteActual.getNombre(), totalCompra);
            lectorVoz.hablar(resumen + "Pedido confirmado. Gracias por tu compra. Ahora tienes " + puntosTotales + " puntos acumulados.");
            ventaTXTService.guardarVenta(carrito, true);  // true = registrado
        } else {
            lectorVoz.hablar(resumen + "Pedido confirmado. Gracias por tu compra.");
            ventaTXTService.guardarVenta(carrito, false); // false = invitado
        }
        carrito.vaciar();
        actualizarCarritoEnVista();

    }

    /**
     * Lee en voz alta el contenido del carrito
     */
    private void leerCarrito() {
        String resumen = modelo.obtenerCarrito().obtenerResumenParaVoz();
        lectorVoz.hablar(resumen);
        vista.actualizarEstado("Leyendo carrito...");
    }

    /**
     * Regresa a la vista principal de la aplicación
     */
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
                receptorVoz.setEnviarProductoParaAgregar(true);
                receptorVoz.setEnviarProductoParaEliminar(true);

                receptorVoz.setManejadorComando(cmd -> {
                    ultimoComando = cmd;
                    switch (cmd) {
                        case AGREGAR -> {
                        }
                        case ELIMINAR -> {
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
                        agregarProducto(producto);
                        ultimoComando = null;
                    } else if (ultimoComando == Comando.ELIMINAR) {
                        eliminarProducto(producto);
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

    /**
     * Actualiza la información del carrito mostrada en la vista
     */
    private void actualizarCarritoEnVista() {
        Carrito c = modelo.obtenerCarrito();
        String texto = "";
        for (Producto p : c.getProductos()) {
            texto = texto + p.getCantidad() + "×  "
                    + p.getNombre()
                    + "  —  $" + String.format("%,.0f", p.PrecioTotal())
                    + "\n";
        }
        vista.actualizarCarrito(texto, c.obtenerTotal());
    }

    /**
     * Agrega un producto directamente al carrito utilizando su nombre
     * @param producto Nombre del producto agregado
     */
    private void agregarProducto(String producto) {
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

    /**
     * Elimina un producto directamente del carrito utilizando su nombre
     * @param producto Nombre del producto a eliminar
     */
    private void eliminarProducto(String producto) {
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
