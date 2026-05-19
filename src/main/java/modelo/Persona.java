/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isabe
 */
public abstract class Persona {

    protected String nombre;
    protected int id;
    protected boolean accesoActivo;

    public Persona(String nombre, int id) {

        this.nombre = nombre;
        this.id = id;
        this.accesoActivo = true;
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

    public boolean isAccesibilidadActiva() {
        return accesoActivo;
    }

    public void setAccesibilidadActiva(boolean accesoActivo) {
        this.accesoActivo = accesoActivo;
    }
    
    public abstract String obtenerTipoCliente();

    @Override
    public String toString() {
        
        return nombre + " - " + obtenerTipoCliente();
    }
}
