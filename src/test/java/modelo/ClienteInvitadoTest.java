/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isabe
 */
public class ClienteInvitadoTest {

    public ClienteInvitadoTest() {
    }

    /**
     * Test de creación de cliente, de la clase ClienteInvitado.
     */
    @Test
    public void testCrearClienteInvitado() {
        ClienteInvitado cliente = new ClienteInvitado("Juan");

        String nombre = cliente.getNombre();

        assertEquals("Juan", nombre, "Error al crear cliente invitado");
    }

    /**
     * Test de compraRapida, de la clase ClienteInvitado.
     */
    @Test
    public void testCompraRapida() {
        ClienteInvitado cliente = new ClienteInvitado("Maria");

        boolean compraRapida = cliente.isCompraRapida();

        assertTrue(compraRapida, "La compra rápida debería ser true");
    }

    /**
     * Test del metodo obtenerTipoCliente, de la clase ClienteInvitado.
     */
    @Test
    public void testObtenerTipoCliente() {
        ClienteInvitado cliente = new ClienteInvitado("Carlos");

        String tipo = cliente.obtenerTipoCliente();

        assertEquals("Cliente Invitado", tipo, "Tipo de cliente incorrecto");
    }

    /**
     * Test del metodo toString, de la clase ClienteInvitado.
     */
    @Test
    public void testToString() {
        ClienteInvitado cliente = new ClienteInvitado("Ana");

        String texto = cliente.toString();

        assertTrue(texto.contains("Compra rapida"), "No contiene texto esperado");
    }

}
