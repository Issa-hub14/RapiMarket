/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author isabe
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import util.LectorVoz;

public abstract class VistaBase extends JFrame implements IVista {

    protected LectorVoz lectorVoz;

    // Barra de estado inferior — compartida por todas las vistas
    private JLabel lblEstado;

    protected VistaBase(String titulo) {
        super(titulo);
        this.lectorVoz = LectorVoz.getInstance();
        configurarVentana();
        initComponentes();      
        agregarBarraEstado();  
    }

    protected abstract void initComponentes();


    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 250));
        setResizable(false);
    }

    private void agregarBarraEstado() {
        lblEstado = new JLabel(" Listo");
        lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblEstado.setForeground(new Color(80, 80, 120));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblEstado.setOpaque(true);
        lblEstado.setBackground(new Color(230, 230, 245));
        add(lblEstado, BorderLayout.SOUTH);
    }

    @Override
    public void actualizarEstado(String mensaje) {
        SwingUtilities.invokeLater(()
                -> lblEstado.setText(" " + mensaje)
        );
    }

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
