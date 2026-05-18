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
import vista.VistaSuperMercado;
import util.LectorVoz;
import util.ReceptorVozVosk;
import java.util.List;

public class ControladorSuperMercado {

    private final VistaSuperMercado vista;
    private final IModelo modelo;
    private final LectorVoz lectorVoz;
    private final ReceptorVozVosk receptorVoz;

    private int indiceActual = 0;

    public ControladorSuperMercado(VistaSuperMercado vista, IModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.lectorVoz = LectorVoz.getInstance();
        this.receptorVoz = ReceptorVozVosk.getInstance();

        conectarBotones();
        configurarVoz();
        actualizarLista();
    }

    private void conectarBotones() {
        vista.addBtnBuscarListener(e -> buscarProducto(vista.getTextoBusqueda()));
        vista.addBtnSiguienteListener(e -> siguienteProducto());
        vista.addBtnRepetirListener(e -> vista.repetirIndicacion());
        vista.addBtnAgregarListaListener(e -> agregarALista());
        vista.addBtnQuitarListaListener(e -> quitarDeLista());
        vista.addBtnVolverListener(e -> volver());
        vista.addBtnMicrofonoListener(e -> manejarMicrofono());
    }

    private void configurarVoz() {
        receptorVoz.setManejadorComando(cmd -> {
            String texto = limpiarComando(receptorVoz.getUltimoTextoReconocido());
            switch (cmd) {
                case BUSCAR ->
                    buscarProducto(texto);
                case SIGUIENTE ->
                    siguienteProducto();
                case ANTERIOR ->
                    anteriorProducto();
                case REPETIR ->
                    vista.repetirIndicacion();
                case AGREGAR ->
                    agregarALista();
                case ELIMINAR ->
                    quitarDeLista();
                case VOLVER ->
                    volver();
                default ->
                    lectorVoz.hablar(
                            "Di: buscar, siguiente, anterior, repetir, agregar, quitar o volver.");
            }
        });

        receptorVoz.setManejadorTexto(texto -> {
            if (texto == null || texto.isBlank()) {
                return;
            }
            buscarProducto(texto);
        });

    }

    private String limpiarComando(String texto) {
        if (texto == null) {
            return "";
        }
        texto = texto.toLowerCase();

        texto = texto.replace("quiero", "");
        texto = texto.replace("buscar", "");
        texto = texto.replace("busca", "");
        texto = texto.replace("agregar", "");
        texto = texto.replace("añadir", "");
        texto = texto.replace("el producto", "");
        texto = texto.replace("producto", "");

        return texto.trim();
    }

    private void buscarProducto(String texto) {
        if (texto == null || texto.isBlank()) {
            lectorVoz.hablar("Di o escribe el producto que deseas buscar.");
            return;
        }

        try {
            vista.setTextoBusqueda(texto);
            List<Producto> resultados = modelo.buscarProductos(texto);

            if (resultados.isEmpty()) {
                vista.mostrarInfoProducto(null);
                lectorVoz.hablar("No encontré " + texto);

            } else {
                Producto producto = resultados.get(0);
                vista.mostrarInfoProducto(producto);
            }
        } catch (Exception e) {
            vista.mostrarError(
                    "Error al buscar: "
                    + e.getMessage()
            );
        }
    }

    private void siguienteProducto() {
        List<String> lista = modelo.obtenerListaDeMercado();
        if (lista.isEmpty()) {
            lectorVoz.hablar("Tu lista de compras está vacía.");
            return;
        }
        if (indiceActual >= lista.size()) {
            indiceActual = 0;
            lectorVoz.hablar("Fin de la lista. Volviendo al primer producto.");
        }
        String nombreProducto = lista.get(indiceActual);
        Producto p = modelo.buscarProductoPorNombre(nombreProducto);
        vista.mostrarInfoProducto(p);
        indiceActual++;
    }

    private void anteriorProducto() {
        List<String> lista = modelo.obtenerListaDeMercado();
        if (lista.isEmpty()) {
            lectorVoz.hablar("Tu lista de compras está vacía.");
            return;
        }
        indiceActual = Math.max(0, indiceActual - 2);
        siguienteProducto();
    }

    private void agregarALista() {
        String texto = vista.getTextoBusqueda().trim();
        if (texto.isBlank()) {
            lectorVoz.hablar("Escribe o di el nombre del producto a agregar a la lista.");
            return;
        }
        modelo.agregarAListaMercado(texto);
        actualizarLista();
        lectorVoz.hablar(texto + " agregado a tu lista de compras.");
    }

    private void quitarDeLista() {
        String item = vista.getItemListaSeleccionado();
        if (item == null) {
            lectorVoz.hablar("Selecciona un producto de la lista para quitarlo.");
            return;
        }
        modelo.eliminarDeListaMercado(item);
        actualizarLista();
        lectorVoz.hablar(item + " quitado de tu lista.");
        if (indiceActual > 0) {
            indiceActual--;
        }
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

    private void actualizarLista() {
        vista.actualizarListaMercado(modelo.obtenerListaDeMercado());
    }

    private void manejarMicrofono() {
        if (receptorVoz.isGrabando()) {
            receptorVoz.detenerGrabacion();
            lectorVoz.hablar("Micrófono desactivado");
            vista.actualizarEstado("Micrófono desactivado");
        } else {
            try {
                receptorVoz.iniciarGrabacion();
                lectorVoz.hablar("Micrófono activado. Di un comando o producto");
                vista.actualizarEstado("Grabando... clic de nuevo para detener.");
            } catch (Exception ex) {
                vista.mostrarError("Error al acceder al micrófono: " + ex.getMessage());
            }
        }
    }
}
