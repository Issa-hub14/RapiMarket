/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isabe
 */
import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private List<Producto> productos;

    public Carrito() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto p) {
        for (Producto existente : productos) {
            if (existente.getNombre().equalsIgnoreCase(p.getNombre())) {
                existente.setCantidad(existente.getCantidad() + 1);
                return;
            }
        }
        productos.add(p);
    }

    public void eliminarProducto(String nombre) {
        productos.removeIf(p -> p.getNombre().equalsIgnoreCase(nombre));
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double obtenerTotal() {
        double total = 0;

        for (Producto p : productos) {
            total += p.PrecioTotal();
        }

        return total;
    }

    public int getCantidadItems() {
        int cantidad = 0;

        for (Producto p : productos) {
            cantidad += p.getCantidad();
        }

        return cantidad;
    }

    public void vaciar() {
        productos.clear();
    }

    public String obtenerResumenParaVoz() {
        if (productos.isEmpty()) {
            return "Tu carrito está vacío";
        }
        String texto = "Tu carrito tiene: ";

        for (Producto p : productos) {
            texto += p.getCantidad() + " " + p.getNombre() + " | ";
        }
        

        texto += "Total: " + obtenerTotal() + " pesos";

        return texto;
    }
}
