/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase que representa un producto dentro del sistema
 * Contiene información relacionada con precio, categoria, ubicación y cantidad
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

     /**
      * Constructor del producto
      * @param nombre Nombre del producto
      * @param precio Precio del producto
      * @param categoria Categoria del producto
      * @param pasillo Ubicación del producto
      */
    public Producto(String nombre, double precio, String categoria, String pasillo) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.pasillo = pasillo;
        this.descripcion = "";
        this.cantidad = 1;
    }

     /**
      * Constructor del producto con descripción 
      * @param nombre Nombre del producto
      * @param precio Precio del producto
      * @param categoria Categoria del producto
      * @param pasillo Ubiación del producto
      * @param descripcion Descripción del producto
      */
    public Producto(String nombre, double precio, String categoria, String pasillo, String descripcion) {
        this(nombre, precio, categoria, pasillo);
        this.descripcion = descripcion;
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
        if (precio >= 0) {
            this.precio = precio;
        }
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
        if (cantidad > 0) {
            this.cantidad = cantidad;
        }
    }

    /**
     * Calcula el precio total del producto de acuerdo a su cantidad
     * @return Precio total del producto
     */
    public double PrecioTotal() {
        return precio * cantidad;
    }

     /**
      * Devuelve una representación en texto del producto
      * @return Información básica del producto
      */
    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}
