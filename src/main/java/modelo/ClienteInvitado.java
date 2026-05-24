/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase que representa un cliente Invitado dentro del sistema.
 * Un cliente Invitado realiza compras rápidas sin registro.
 * 
 * @author isabe
 */
public class ClienteInvitado extends Persona {

    private boolean compraRapida;

    public ClienteInvitado(String nombre) {
        super(nombre, 0);
        this.compraRapida = true;
    }

    /**
     * Indica si el cliente utiliza compra rápida
     * @return True si utiliza compra rapido, Falso en caso contrario
     */
    public boolean isCompraRapida() {
        return compraRapida;
    }

    /**
     * Obtiene el tipo de cliente
     * @return Tipo de cliente invitado
     */
    @Override
    public String obtenerTipoCliente() {
        return "Cliente Invitado";
    }

    /**
     * Devuelve una representación en texto del cliente invitado
     * @return Información del cliente invitado
     */
    @Override
    public String toString() {
        return super.toString()
                + " | Compra rapida";
    }
}
