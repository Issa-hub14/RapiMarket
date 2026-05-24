/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rapimarket;

import modelo.ModeloTienda;
import vista.VistaPrincipal;
import controlador.ControladorPrincipal;
import javax.swing.SwingUtilities;

/**
 * Clase Principal encargada de la aplicación rapiMarket
 * 
 * @author isabe
 */
public class Main {
    /**
     * Método principal que ejecuta la aplicación
     * @param args Argumentos de la linea de comandos
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {        
            ModeloTienda modelo = new ModeloTienda();
            VistaPrincipal vista = new VistaPrincipal();
            new ControladorPrincipal(vista, modelo).iniciar();
        });
    }
}
