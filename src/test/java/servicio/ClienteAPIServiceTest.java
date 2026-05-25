/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package servicio;

import modelo.ClienteRegistrado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isabe
 */
public class ClienteAPIServiceTest {
    
    public ClienteAPIServiceTest() {
    }

    /**
     * Test el método getCliente, de la clase ClienteAPIService.
     */
    @Test
    public void testGetClienteValido() throws Exception{

        ClienteAPIService service = new ClienteAPIService();
        ClienteRegistrado cliente = service.getCliente(1);

        assertNotNull(cliente);
    }

    /**
     * Test2 el método getCliente, de la clase ClienteAPIService.
     */
    @Test
    public void testGetCliente() throws Exception{

        ClienteAPIService service = new ClienteAPIService();
        ClienteRegistrado cliente = service.getCliente(1);

        assertNotNull(cliente.getNombre());
        assertNotNull(cliente.getCorreo());
        assertNotNull(cliente.getTelefono());
        assertNotNull(cliente.getDireccion());
    }

    /**
     * Test3 el método getCliente, de la clase ClienteAPIService.
     */
    @Test
    public void testGetClienteNoexiste() throws Exception{

        ClienteAPIService service = new ClienteAPIService();
        ClienteRegistrado cliente = service.getCliente(999999);

        assertNull(cliente);
    }
}
