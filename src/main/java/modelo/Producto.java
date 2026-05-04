/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isabe
 */
public class Producto {

    private String nombre;
    private double precio;
    private String categoria;
    private String pasillo;
    private String descripcion;
    private int cantidad;

    public Producto(String nombre, double precio, String categoria, String pasillo) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.pasillo = pasillo;
        this.descripcion = "";
        this.cantidad = 1;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getPasillo() {
        return pasillo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPasillo(String pasillo) {
        this.pasillo = pasillo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double PrecioTotal() {
        return precio * cantidad;
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}
