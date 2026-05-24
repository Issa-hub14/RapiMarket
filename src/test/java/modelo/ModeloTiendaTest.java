/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package modelo;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isabe
 */
public class ModeloTiendaTest {
    
    public ModeloTiendaTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getClienteActual method, of class ModeloTienda.
     */
    @Test
    public void testGetClienteActual() {
        System.out.println("getClienteActual");
        ModeloTienda instance = new ModeloTienda();
        Persona expResult = null;
        Persona result = instance.getClienteActual();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setClienteActual method, of class ModeloTienda.
     */
    @Test
    public void testSetClienteActual() {
        System.out.println("setClienteActual");
        Persona clienteActual = null;
        ModeloTienda instance = new ModeloTienda();
        instance.setClienteActual(clienteActual);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarProductos method, of class ModeloTienda.
     */
    @Test
    public void testBuscarProductos() {
        System.out.println("buscarProductos");
        String termino = "";
        ModeloTienda instance = new ModeloTienda();
        List<Producto> expResult = null;
        List<Producto> result = instance.buscarProductos(termino);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarProductoPorNombre method, of class ModeloTienda.
     */
    @Test
    public void testBuscarProductoPorNombre() {
        System.out.println("buscarProductoPorNombre");
        String nombre = "";
        ModeloTienda instance = new ModeloTienda();
        Producto expResult = null;
        Producto result = instance.buscarProductoPorNombre(nombre);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerCarrito method, of class ModeloTienda.
     */
    @Test
    public void testObtenerCarrito() {
        System.out.println("obtenerCarrito");
        ModeloTienda instance = new ModeloTienda();
        Carrito expResult = null;
        Carrito result = instance.obtenerCarrito();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerCatalogo method, of class ModeloTienda.
     */
    @Test
    public void testObtenerCatalogo() {
        System.out.println("obtenerCatalogo");
        ModeloTienda instance = new ModeloTienda();
        List<Producto> expResult = null;
        List<Producto> result = instance.obtenerCatalogo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerListaDeMercado method, of class ModeloTienda.
     */
    @Test
    public void testObtenerListaDeMercado() {
        System.out.println("obtenerListaDeMercado");
        ModeloTienda instance = new ModeloTienda();
        List<String> expResult = null;
        List<String> result = instance.obtenerListaDeMercado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of agregarAListaMercado method, of class ModeloTienda.
     */
    @Test
    public void testAgregarAListaMercado() {
        System.out.println("agregarAListaMercado");
        String item = "";
        ModeloTienda instance = new ModeloTienda();
        instance.agregarAListaMercado(item);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarDeListaMercado method, of class ModeloTienda.
     */
    @Test
    public void testEliminarDeListaMercado() {
        System.out.println("eliminarDeListaMercado");
        String item = "";
        ModeloTienda instance = new ModeloTienda();
        instance.eliminarDeListaMercado(item);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
