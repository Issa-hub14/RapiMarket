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

/**
 * Clase encargada de gestionar los productos agregados al carrito de compras
 * Permite agregar, eliminar, calcular totales y generar resumenes 
 * 
 * @author isabe
 */
public class Carrito {

    private List<Producto> productos;

    public Carrito() {
        this.productos = new ArrayList<>();
    }
    
    /**
     * Agrega un producto al carrito.
     * Si el producto ya existe aumenta su cantidad
     * @param p Producto que desea agregar
     */
    public void agregarProducto(Producto p) {
        if (p == null) {
            return;
        }
        for (Producto existente : productos) {
            if (existente.getNombre().equalsIgnoreCase(p.getNombre())) {
                existente.setCantidad(existente.getCantidad() + 1);
                return;
            }
        }
        productos.add(p);
    }

    /**
     * Elimina un producto del carrito utilizando su nombre
     * @param nombre Nombre del producto a eliminar 
     */
    public void eliminarProducto(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return;
        }
        productos.removeIf(p -> p.getNombre().equalsIgnoreCase(nombre));
    }

    /**
     * Obtiene la lista de productos del carrito
     * @return Lista de productos almacenados
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Calcula el total de la compra
     * @return valor total de los producto del carrto
     */
    public double obtenerTotal() {
        double total = 0;

        for (Producto p : productos) {
            total += p.PrecioTotal();
        }

        return total;
    }

    /**
     * Obtiene la cantidad total de productos en el carrito
     * @return Cantidad total de items
     */
    public int getCantidadItems() {
        int cantidad = 0;

        for (Producto p : productos) {
            cantidad += p.getCantidad();
        }

        return cantidad;
    }

    /**
     * Vacia completamente le carrito de compras
     */
    public void vaciar() {
        productos.clear();
    }

    /**
     * Genera un resumen del carrito para ser leido por voz
     * @return Texto con el resumen del carrito
     */
    public String obtenerResumenParaVoz() {
        if (productos.isEmpty()) {
            return "Tu carrito está vacío";
        }
        
        String texto = "Tu carrito tiene: ";
        for (Producto p : productos) {
            texto += p.getCantidad() + " " + p.getNombre() + " | ";
        }
        texto += "Para un total de: " + obtenerTotal() + " pesos";

        return texto;

    }

    /**
     * Devuelve una representación en texto del carrito
     * @return Texto con la información del los productos y el total
     */
    @Override
    public String toString() {
        if (productos.isEmpty()) {
            return "Carrito vacío";
        }
        
        String texto = "Productos:";
        for (Producto producto : productos) {

            texto += (producto.getCantidad())
                    + (" x ")
                    + (producto.getNombre())
                    + (" - $")
                    + (producto.PrecioTotal())
                    + ("\n");
        }
        texto += ("\nTOTAL: $")
                + (obtenerTotal());

        return texto;
    }
}
