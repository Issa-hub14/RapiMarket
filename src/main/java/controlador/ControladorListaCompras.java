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
import java.util.List;

public class ControladorListaCompras {

    private final VistaListaCompras vista;
    private final IModelo modelo;
    private final LectorVoz lectorVoz;

    public ControladorListaCompras(VistaListaCompras vista, IModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.lectorVoz = LectorVoz.getInstance();

        conectarBotones();
        cargarCatalogoInicial();
    }

    private void cargarCatalogoInicial() {
        vista.mostrarResultados(modelo.obtenerCatalogo());
    }

    private void conectarBotones() {
        vista.addBtnBuscarListener(e -> buscar());
        vista.addBtnAgregarListaListener(e -> agregarALista());
        vista.addBtnQuitarListaListener(e -> quitarDeLista());
        vista.addBtnRepetirListener(e -> repetirProducto()
        );
        vista.addBtnMicrofonoListener(e -> lectorVoz.hablar("Micrófono activado")
        );
        vista.addBtnVolverListener(e -> volver());
    }

    private void repetirProducto() {
        String seleccionado = vista.getItemListaSeleccionado();

        if (seleccionado == null || seleccionado.isBlank()) {
            lectorVoz.hablar("Selecciona un producto de tu lista primero");
            return;
        }

        lectorVoz.hablar("Producto seleccionado: " + seleccionado);
    }

    private void buscar() {

        String texto = vista.getTextoBusqueda();
        if (texto.isBlank()) {
            lectorVoz.hablar("Escribe un producto para buscar");
            return;
        }

        List<Producto> resultados = modelo.buscarProductos(texto);

        vista.mostrarResultados(resultados);
        lectorVoz.hablar("Resultados para " + texto);
    }

    private void agregarALista() {

        String seleccionado = vista.getProductoSeleccionado();

        if (seleccionado == null) {
            lectorVoz.hablar("Selecciona un producto primero");
            return;
        }

        String nombre = seleccionado.split("—")[0].trim();

        modelo.agregarAListaMercado(nombre);
        actualizarLista();

        lectorVoz.hablar(nombre + " agregado a tu lista de compras");
    }

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

    private void actualizarLista() {
        vista.actualizarListaMercado(modelo.obtenerListaDeMercado());
    }

    private void volver() {
        //if (receptorVoz.isGrabando()) {
        // receptorVoz.detenerGrabacion();
        //}
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

    //private void manejarMicrofono() {
    //if (receptorVoz.isGrabando()) {
    //receptorVoz.detenerGrabacion();
    //lectorVoz.hablar("Micrófono desactivado");
    //vista.actualizarEstado("Micrófono desactivado");
    //} else {
    //    try {
    //       receptorVoz.iniciarGrabacion();
    //     lectorVoz.hablar("Micrófono activado. Di un comando o producto");
    //      vista.actualizarEstado("Grabando... clic de nuevo para detener.");
    //  } catch (Exception ex) {
    //    vista.mostrarError("Error al acceder al micrófono: " + ex.getMessage());
    //   }
    //}
    // }
}
