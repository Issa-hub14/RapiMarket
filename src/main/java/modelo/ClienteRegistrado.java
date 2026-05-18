/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isabe
 */
public class ClienteRegistrado extends Persona {

    private int puntos;
    private String direccion;
    private String correo;
    private String telefono;

    public ClienteRegistrado(String nombre, String identificacion, String direccion, String correo, String telefono) {
        super(nombre, identificacion);

        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.puntos = 0;
    }

    public int getPuntos() {
        return puntos;
    }

    public void agregarPuntos(int puntos) {
        if (puntos > 0) {
            this.puntos += puntos;
        }
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        if (direccion != null && !direccion.isBlank()) {
            this.direccion = direccion;
        }
    }
    
    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String obtenerTipoCliente() {
        return "Cliente Registrado";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Dirección: "+ direccion
                + " | Correo: " + correo
                + " | Teléfono: " + telefono
                + " | Puntos: " + puntos;
    }
}
