/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import util.LectorVoz;

/**
 * Clase base para las vistas de la aplicación
 * Contiene configuraciones y comportamientos compartidos entre todas las interfaces gráficas
 * 
 * @author isabe
 */
public abstract class VistaBase extends JFrame implements IVista {

    protected LectorVoz lectorVoz;
    private JLabel lblEstado;

    protected VistaBase(String titulo) {
        super(titulo);
        this.lectorVoz = LectorVoz.getInstance();
        configurarVentana();
        initComponentes();      
        agregarBarraEstado();  
    }

    /**
     * Inicializa los componentes específicos de la vista
     */
    protected abstract void initComponentes();

    /**
     * Configura las propiedades geenerales de la ventana
     */
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 250));
        setResizable(false);
    }

    /**
     * Agrega la barra inferior de estado en la ventana
     */
    private void agregarBarraEstado() {
        lblEstado = new JLabel(" Listo");
        lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblEstado.setForeground(new Color(80, 80, 120));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblEstado.setOpaque(true);
        lblEstado.setBackground(new Color(230, 230, 245));
        add(lblEstado, BorderLayout.SOUTH);
    }

    /**
     * Actualiza el mensaje
     * @param mensaje Mensaje de estado
     */
    @Override
    public void actualizarEstado(String mensaje) {
        SwingUtilities.invokeLater(()
                -> lblEstado.setText(" " + mensaje)
        );
    }

    /**
     * Muestra un mensaje de error en pantalla y mediante voz
     * @param error Mensaje de error a mostar
     */
    @Override
    public void mostrarError(String error) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                    this,
                    error,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            lectorVoz.hablar("Ocurrió un error: " + error);
        });
    }
}
