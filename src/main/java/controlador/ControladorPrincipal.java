/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author isabe
 */
import modelo.IModelo;
import vista.VistaPrincipal;
import vista.VistaPedidoOnline;
import vista.VistaSuperMercado;
import util.LectorVoz;
import util.ReceptorVoz;

public class ControladorPrincipal {

    private final VistaPrincipal vista;
    private final IModelo modelo;
    private final LectorVoz lectorVoz;
    private final ReceptorVoz receptorVoz;

    private VistaPedidoOnline vistaPedido;
    private VistaSuperMercado vistaSuper;

    public ControladorPrincipal(VistaPrincipal vista, IModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.lectorVoz = LectorVoz.getInstance();
        this.receptorVoz = new ReceptorVoz();

        conectarBotones();
        configurarVoz();
    }

    private void conectarBotones() {
        vista.addBtnSupermercadoListener(e -> abrirSupermercado());

        vista.addBtnOnlineListener(e -> abrirPedidoOnline());

        vista.addBtnListaListener(e -> {
            abrirSupermercado();
            lectorVoz.hablar("Mostrando tu lista de compras.");
        });

        vista.addChkVozListener(e -> {
            lectorVoz.setActivo(vista.isVozActiva());
            String estado = vista.isVozActiva() ? "activada" : "desactivada";
            lectorVoz.hablar("Voz " + estado);
        });
    }

    private void configurarVoz() {
        receptorVoz.setManejadorComando(cmd -> {
            switch (cmd) {
                case BUSCAR ->
                    abrirPedidoOnline();
                case SIGUIENTE ->
                    abrirSupermercado();
                case VOLVER ->
                    lectorVoz.hablar("Ya estás en el menú principal.");
                default ->
                    lectorVoz.hablar("Di: supermercado, en línea, o lista.");
            }
        });

        //receptorVoz.iniciarGrabacion();
    }

    private void abrirPedidoOnline() {
        if (vistaPedido == null) {
            vistaPedido = new VistaPedidoOnline();
            new ControladorPedido(vistaPedido, modelo);
        }
        vista.setVisible(false);
        vistaPedido.setVisible(true);
        lectorVoz.hablar("Abriendo compra en línea.");
    }

    private void abrirSupermercado() {
        if (vistaSuper == null) {
            vistaSuper = new VistaSuperMercado();
            new ControladorSuperMercado(vistaSuper, modelo);
        }
        vista.setVisible(false);
        vistaSuper.setVisible(true);
        lectorVoz.hablar("Abriendo guía de supermercado.");
    }

    public void iniciar() {
        vista.setVisible(true);
    }
}
