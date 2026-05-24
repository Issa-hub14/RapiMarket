/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase abstracta que representa una persona dentro del sistema Define
 * atributos y comportamientos comunes para los tipos de clientes
 *
 * @author isabe
 */
public abstract class Persona {

    protected String nombre;
    protected int id;

    public Persona(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            this.nombre = nombre;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int identi) {
        this.id = identi;
    }

     /**
      * Obtiene el tipo de cliente
      * @return Tipo de cliente
      */
    public abstract String obtenerTipoCliente();

    /**
     * Devuelve una representación en texto de la persona
     * @return Información básica de la persona
     */
    @Override
    public String toString() {

        return nombre + " - " + obtenerTipoCliente();
    }
}
