/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author isabe
 */
import modelo.*;
import java.io.FileWriter;
import java.io.PrintWriter;

public class VentaTXTService {

    private static final String ARCHIVO = "ventas.txt";

    public void guardarVenta(Carrito carrito) {

        try (
            FileWriter fw = new FileWriter( ARCHIVO, true); 
                PrintWriter pw = new PrintWriter(fw)) {

            pw.println("===== REGISTRO DE VENTA =====");
            pw.println();
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
            pw.println("TOTAL: $"+ carrito.obtenerTotal());

            pw.println( "============================");
            pw.println();

        } catch (Exception e) {
            System.out.println( "Error guardando venta TXT: " + e.getMessage()
            );
        }
    }
}
