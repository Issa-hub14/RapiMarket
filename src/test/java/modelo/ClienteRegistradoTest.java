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
public class ClienteRegistradoTest {

    public ClienteRegistradoTest() {
    }

    /**
     * Test del método agregarPuntos, de la clase ClienteRegistrado.
     */
    @Test
    public void testAgregarPuntos() {
        ClienteRegistrado cliente = new ClienteRegistrado(
                "Juan",
                123,
                "Calle 10",
                "juan@gmail.com",
                "3001234567"
        );

        cliente.agregarPuntos(50);

        assertEquals(50, cliente.getPuntos(), "Error al agregar puntos");
    }
    
    /**
     * Test2 del método agregarPuntos, de la clase ClienteRegistrado.
     */
    @Test
    public void testAgregarPuntosNegativos() {
        ClienteRegistrado cliente = new ClienteRegistrado(
                "Juan",
                123,
                "Calle 10",
                "juan@gmail.com",
                "3001234567"
        );

        cliente.agregarPuntos(-20);

        assertEquals(0, cliente.getPuntos(), "No debe agregar puntos negativos");
    }

    /**
     * Test del constructor, de la clase ClienteRegistrado.
     */
    @Test
    public void testConstructorClienteRegistrado() {
        ClienteRegistrado cliente = new ClienteRegistrado(
                "Juan",
                123,
                "Calle 10",
                "juan@gmail.com",
                "3001234567"
        );

        assertEquals("Juan", cliente.getNombre(), "Error en nombre");
        assertEquals(123, cliente.getId(), "Error en identificación");
        assertEquals("Calle 10", cliente.getDireccion(), "Error en dirección");
        assertEquals("juan@gmail.com", cliente.getCorreo(), "Error en correo");
        assertEquals("3001234567", cliente.getTelefono(), "Error en teléfono");
        assertEquals(0, cliente.getPuntos(), "Los puntos deben iniciar en 0");
    }
    
    /**
     * Test del método setDireccion, de la clase ClienteRegistrado.
     */
    @Test
    public void testSetDireccion() {
        ClienteRegistrado cliente = new ClienteRegistrado(
                "Juan",
                123,
                "Calle 10",
                "juan@gmail.com",
                "3001234567"
        );

        cliente.setDireccion("Carrera 50");

        assertEquals("Carrera 50", cliente.getDireccion(), "Error al cambiar dirección");
    }

    /**
     * Test2 del método setDireccion, de la clase ClienteRegistrado.
     */
    @Test
    public void testSetDireccionVacia() {
        ClienteRegistrado cliente = new ClienteRegistrado(
                "Juan",
                123,
                "Calle 10",
                "juan@gmail.com",
                "3001234567"
        );

        cliente.setDireccion("");

        assertEquals("Calle 10", cliente.getDireccion(), "No debe aceptar dirección vacia");
    }

    /**
     * Test del método obetenerTipoCliente, de la clase ClienteRegistrado.
     */
    @Test
    public void testObtenerTipoCliente() {
        ClienteRegistrado cliente = new ClienteRegistrado(
                "Juan",
                123,
                "Calle 10",
                "juan@gmail.com",
                "3001234567"
        );

        String tipo = cliente.obtenerTipoCliente();

        assertEquals("Cliente Registrado", tipo, "Error en tipo de cliente");
    }

    /**
     * Test del método toString, de la clase ClienteRegistrado.
     */
    @Test
    public void testToString() {
        ClienteRegistrado cliente = new ClienteRegistrado(
                "Juan",
                123,
                "Calle 10",
                "juan@gmail.com",
                "3001234567"
        );

        String texto = cliente.toString();

        assertTrue(texto.contains("Juan"), "Debe contener el nombre");
        assertTrue(texto.contains("Calle 10"), "Debe contener la dirección");
        assertTrue(texto.contains("juan@gmail.com"), "Debe contener el correo");
        assertTrue(texto.contains("3001234567"), "Debe contener el teléfono");
    }

}
