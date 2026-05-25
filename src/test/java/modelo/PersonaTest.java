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
public class PersonaTest {

    public PersonaTest() {
    }

    /**
     * Test del constructor, de la clase Persona.
     */
    @Test
    public void testConstructorPersona() {

        Persona persona = new ClienteInvitado("Juan");

        assertEquals("Juan", persona.getNombre(), "Error en nombre");
        assertEquals(0, persona.getId(), "Error en ID");
    }

    /**
     * Test del método getNombre, de la clase Persona.
     */
    @Test
    public void testSetNombre() {

        Persona persona = new ClienteInvitado("Juan");

        persona.setNombre("Pedro");

        assertEquals("Pedro", persona.getNombre(),
                "Error al cambiar nombre");
    }

    /**
     * Test del método setNombre, de la clase Persona.
     */
    @Test
    public void testSetNombreVacio() {

        Persona persona = new ClienteInvitado("Juan");

        persona.setNombre("");

        assertEquals("Juan", persona.getNombre(),
                "No debe aceptar nombres vacíos");
    }

    /**
     * Test2 del método setNombre, de la clase Persona.
     */
    @Test
    public void testSetNombreNull() {

        Persona persona = new ClienteInvitado("Juan");

        persona.setNombre(null);

        assertEquals("Juan", persona.getNombre(),
                "No debe aceptar nombres null");
    }
    
    /**
     * Test del método setId, de la clase Persona.
     */
    @Test
    public void testSetId() {

        Persona persona = new ClienteInvitado("Juan");

        persona.setId(999);

        assertEquals(999, persona.getId(),
                "Error al cambiar ID");
    }

    /**
     * Test del método obtenerTipoCliente, de la clase Persona.
     */
    @Test
    public void testObtenerTipoCliente() {

        Persona persona = new ClienteInvitado("Juan");

        String tipo = persona.obtenerTipoCliente();

        assertEquals("Cliente Invitado", tipo,
                "Error en el tipo de cliente");
    }

    /**
     * Test del método toString, de la clase Persona.
     */
    @Test
    public void testToString() {

        Persona persona = new ClienteInvitado("Juan");

        String texto = persona.toString();

        assertTrue(texto.contains("Juan"),
                "Debe contener el nombre");

        assertTrue(texto.contains("Cliente Invitado"),
                "Debe contener el tipo de cliente");
    }
}
