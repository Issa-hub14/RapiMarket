/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package servicio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isabe
 */
public class PuntosDBServiceTest {

    public PuntosDBServiceTest() {
    }

    /**
     * Test del método insertarCliente, de la clase PuntosDBService.
     */
    @Test
    public void testInsertarCliente() {
        PuntosDBService service = new PuntosDBService();
        int puntos = service.actualizarPuntos(2001, "Cliente Test", 5000);
        assertTrue(puntos > 0, "Fallo en test1");
    }
    
    /**
     * Test del método actualizarPuntos, de la clase PuntosDBService.
     */
    @Test
    public void testActualizarPuntos() {
        PuntosDBService service = new PuntosDBService();
        service.actualizarPuntos(2002, "Cliente Test", 2000);
        int puntos = service.actualizarPuntos(2002, "Cliente Test", 3000);

        assertTrue(puntos >= 2, "Fallo en test2");
    }

    /**
     * Test del método calcularPuntos, de la clase PuntosDBService.
     */
    @Test
    public void testCalcularPuntos() {
        PuntosDBService service = new PuntosDBService();
        int puntos = service.actualizarPuntos(2003, "Test", 1000);

        assertEquals(1, puntos);
    }

}
