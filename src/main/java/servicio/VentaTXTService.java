/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

import modelo.*;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Servicio encargado de guardar el registro de ventas en un archivo de texto
 * 
 * @author isabe
 */
public class VentaTXTService {

    private static final String ARCHIVO = "ventas.txt";

    /**
     * Guarda la información de una venta en un archivo txt
     * @param carrito Carrito que contiene los productos vendidos
     * @param esRegistrado Indica si el clente el registrado o invitado
     */
    public void guardarVenta(Carrito carrito, boolean esRegistrado) {

        try (
                FileWriter fw = new FileWriter(ARCHIVO, true); PrintWriter pw = new PrintWriter(fw)) {

            pw.println("===== REGISTRO DE VENTA =====");
            pw.println();
            if (esRegistrado) {
                pw.println("Tipo: Cliente Registrado");
            } else {
                pw.println("Tipo: Cliente Invitado");
            }
            pw.println("PRODUCTOS:");
            for (Producto p : carrito.getProductos()) {
                pw.println(
                        p.getNombre()
                        + " x"
                        + p.getCantidad()
                        + " -> $"
                        + p.PrecioTotal()
                );
            }
            pw.println();
            pw.println("TOTAL: $" + carrito.obtenerTotal());

            pw.println("============================");
            pw.println();

        } catch (Exception e) {
            System.out.println("Error guardando venta TXT: " + e.getMessage()
            );
        }
    }
}
