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
import vista.VistaListaCompras;
import vista.VistaUsuario;
import vista.VistaSuperMercado;
import util.LectorVoz;
import servicio.ClienteAPIService;

/**
 * Controlador Principal de la aplicación, se encarga de gestionar la navegación entre las diferentes vistas
 * 
 * @author isabe
 */
public class ControladorPrincipal {

    private final VistaPrincipal vista;
    private final IModelo modelo;
    private final LectorVoz lectorVoz;
    private ControladorSuperMercado controladorSuper;

    private VistaUsuario vistaUsuario;
    private VistaSuperMercado vistaSuper;
    private VistaListaCompras vistaLista;
    private final ClienteAPIService apiService;

    public ControladorPrincipal(VistaPrincipal vista, IModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.lectorVoz = LectorVoz.getInstance();
        this.apiService = new ClienteAPIService();

        conectarBotones();

    }

    /**
     * Conecta los botones y los eventos de la vista principal
     */
    private void conectarBotones() {
        vista.addBtnSupermercadoListener(e -> abrirSupermercado());

        vista.addBtnOnlineListener(e -> abrirPedidoOnline());

        vista.addBtnListaListener(e -> {
            abrirLista();
            lectorVoz.hablar("Mostrando tu lista de compras.");
        });

        vista.addChkVozListener(e -> {
            lectorVoz.setActivo(vista.isVozActiva());
            String estado = vista.isVozActiva() ? "activada" : "desactivada";
            lectorVoz.hablar("Voz " + estado);
        });
    }

    /**
     * Abre el módulo de pedidos en linea
     */
    private void abrirPedidoOnline() {
        if (vistaUsuario == null) {
            vistaUsuario = new VistaUsuario();
            new ControladorUsuario(vistaUsuario, apiService, modelo);
        }
        vista.setVisible(false);
        vistaUsuario.setVisible(true);
             
    }

    /**
     * Abre el módulo de super Mercado
     */
    private void abrirSupermercado() {
        if (vistaSuper == null) {
            vistaSuper = new VistaSuperMercado();
            controladorSuper = new ControladorSuperMercado(vistaSuper, modelo);
        }
        controladorSuper.refrescarLista(); 
        vista.setVisible(false);
        vistaSuper.setVisible(true);
        
    }
    
    /**
     * Abre la vista de la lista de compras
     */
    private void abrirLista() {
        if (vistaLista == null) {
            vistaLista = new VistaListaCompras();
            new ControladorListaCompras(vistaLista, modelo);
        }
        vista.setVisible(false);
        vistaLista.setVisible(true);
        
    }

    /**
     * Inicia la aplicación mostrando la vista principal
     */
    public void iniciar() {
        vista.setVisible(true);
    }
}
