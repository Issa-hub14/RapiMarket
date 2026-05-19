/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isabe
 */
public class ClienteInvitado extends Persona {

    private boolean compraRapida;

    public ClienteInvitado(String nombre) {
        super(nombre, 0);
        this.compraRapida = true;
    }

    public boolean isCompraRapida() {
        return compraRapida;
    }

    @Override
    public String obtenerTipoCliente() {
        return "Cliente Invitado";
    }

    @Override
    public String toString() {
        return super.toString()
                + " | Compra rapida";
    }
}
