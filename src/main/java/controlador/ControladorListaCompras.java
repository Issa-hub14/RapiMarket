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
import vista.VistaListaCompras;
import util.LectorVoz;
import util.ReceptorVozVosk;
import util.ReceptorVozVosk.Comando;
import java.util.List;

/**
 * Controlador encargado de gestionar la lista de compras.
 * Permite buscar Productos, agregar o eliminar elementos de la lista y utilizar comandos por voz
 * 
 * @author isabe
 */
public class ControladorListaCompras {

    private final VistaListaCompras vista;
    private final IModelo modelo;
    private final LectorVoz lectorVoz;
    private final ReceptorVozVosk receptorVoz;

    private Comando ultimoComando = null;

    public ControladorListaCompras(VistaListaCompras vista, IModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.lectorVoz = LectorVoz.getInstance();
        this.receptorVoz = ReceptorVozVosk.getInstance();

        conectarBotones();
        cargarCatalogoInicial();
        actualizarLista();
    }

    /**
     * Carga el catalogo inicial de productos en la vista
     */
    private void cargarCatalogoInicial() {
        vista.mostrarResultados(modelo.obtenerCatalogo());
    }

    /**
     * Conecta los botones y eventos de la vista
     */
    private void conectarBotones() {
        vista.addBtnBuscarListener(e -> buscarProducto(vista.getTextoBusqueda()));
        vista.addBtnAgregarListaListener(e -> agregarALista());
        vista.addBtnQuitarListaListener(e -> quitarDeLista());
        vista.addBtnLeerListaListener(e -> vista.leerLista());
        vista.addBtnMicrofonoListener(e -> manejarMicrofono());
        vista.addBtnVolverListener(e -> volver());
    }

    /**
     * Lee en voz alta los productos de la lista de compras
     */
    private void leerLista() {
        List<String> lista = modelo.obtenerListaDeMercado();

        if (lista == null || lista.isEmpty()) {
            lectorVoz.hablar("Tu lista de compras está vacia.");
            return;
        }
         String mensaje = "Tu lista tiene" + lista.size() + "Productos";
        
        for (int i = 0; i < lista.size(); i++) {
            if(i>0){
                mensaje = mensaje + ",";
            }
            mensaje = mensaje + lista.get(i);
        }
        lectorVoz.hablar(mensaje);
    }

    /**
     * Busca un producto utilizando el texto ingresado
     * @param texto Nombre del producto a buscar
     */
    private void buscarProducto(String texto) {
        if (texto == null || texto.isBlank()) {
            lectorVoz.hablar("Di o escribe el producto que deseas buscar.");
            return;
        }
        try {
            vista.setTextoBusqueda(texto);
            List<Producto> resultados = modelo.buscarProductos(texto);
            vista.mostrarResultados(resultados);

            if (resultados.isEmpty()) {
                lectorVoz.hablar("No encontre " + texto);
            } else {
                Producto producto = resultados.get(0);

                lectorVoz.hablar("Encontre " + resultados.size() + " productos. "
                        + "Primer resultado: " + producto.getNombre()
                        + ". Precio " + (int) producto.getPrecio() + " pesos. ");
            }
        } catch (Exception e) {
            vista.mostrarError("Error al buscar: " + e.getMessage());
        }
    }

    /**
     * Agrega un producto seleccionado a la lista de compras
     */
    private void agregarALista() {
        String seleccionado = vista.getProductoSeleccionado();

        if (seleccionado == null || seleccionado.isEmpty()) {
            lectorVoz.hablar("Selecciona un producto primero");
            return;
        }
        String nombre = seleccionado.split("—")[0].trim();
        modelo.agregarAListaMercado(nombre);
        actualizarLista();

        lectorVoz.hablar(nombre + " agregado a tu lista de compras");
    }

    /**
     * Elimina un producto seleccionado de la lista de compras
     */
    private void quitarDeLista() {

        String seleccionado = vista.getItemListaSeleccionado();

        if (seleccionado == null) {
            lectorVoz.hablar("Selecciona un producto de tu lista para eliminarlo");
            return;
        }

        modelo.eliminarDeListaMercado(seleccionado);
        actualizarLista();
        lectorVoz.hablar("Producto eliminado de la lista");

    }

    /**
     * Actualiza la lista de compras mostrada en la vista
     */
    private void actualizarLista() {
        vista.actualizarListaMercado(modelo.obtenerListaDeMercado());
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
                        case LEER ->
                            leerLista();
                        case ELIMINAR -> {
                        }
                        case VOLVER ->
                            volver();
                        case LEER_CARRITO, CONFIRMAR, SIGUIENTE -> {
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
                        buscarProducto(producto);
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
     * Agregar un producto directamente a la lista de compras
     * @param producto Nombre del producto a agregar
     */
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
        Producto encontrado = resultados.get(0);
        String nombreEncontrado = encontrado.getNombre();
        modelo.agregarAListaMercado(nombreEncontrado);
        actualizarLista();
        lectorVoz.hablar(producto + " agregado a tu lista");
    }

    /**
     * Eliminar un producto directamente a la lista de compras
     * @param producto Nombre del producto a eliminar
     */
    private void eliminarProductoDirecto(String producto) {
        if (producto == null || producto.isEmpty()) {
            lectorVoz.hablar("No entendí qué producto quieres eliminar.");
            return;
        }
        List<String> listaActual = modelo.obtenerListaDeMercado();
        String productoEncontrado = null;
        for (String item : listaActual) {
            if (item.toLowerCase().contains(producto.toLowerCase())) {
                productoEncontrado = item;
                break;
            }
        }
        if (productoEncontrado == null) {
            lectorVoz.hablar(producto + " no está en tu lista de compras.");
            return;
        }

        modelo.eliminarDeListaMercado(productoEncontrado);
        actualizarLista();
        lectorVoz.hablar(productoEncontrado + " eliminado de tu lista");
    }

}
