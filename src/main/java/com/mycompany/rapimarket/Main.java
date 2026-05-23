/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rapimarket;

/**
 *
 * @author isabe
 */
import modelo.ModeloMicrofono;
import modelo.ModeloTienda;
import vista.VistaPrincipal;
import controlador.ControladorPrincipal;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ModeloMicrofono modeloMic = new ModeloMicrofono();

            ModeloTienda modelo = new ModeloTienda();
            VistaPrincipal vista = new VistaPrincipal();
            new ControladorPrincipal(vista, modelo).iniciar();
        });
    }
}
