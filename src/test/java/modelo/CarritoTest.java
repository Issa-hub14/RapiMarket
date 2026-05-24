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
public class CarritoTest {
    
    public CarritoTest() {
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
     * Test of agregarProducto method, of class Carrito.
     */
    @Test
    public void testAgregarProducto() {
        System.out.println("agregarProducto");
        Producto p = null;
        Carrito instance = new Carrito();
        instance.agregarProducto(p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of eliminarProducto method, of class Carrito.
     */
    @Test
    public void testEliminarProducto() {
        System.out.println("eliminarProducto");
        String nombre = "";
        Carrito instance = new Carrito();
        instance.eliminarProducto(nombre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProductos method, of class Carrito.
     */
    @Test
    public void testGetProductos() {
        System.out.println("getProductos");
        Carrito instance = new Carrito();
        List<Producto> expResult = null;
        List<Producto> result = instance.getProductos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerTotal method, of class Carrito.
     */
    @Test
    public void testObtenerTotal() {
        System.out.println("obtenerTotal");
        Carrito instance = new Carrito();
        double expResult = 0.0;
        double result = instance.obtenerTotal();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCantidadItems method, of class Carrito.
     */
    @Test
    public void testGetCantidadItems() {
        System.out.println("getCantidadItems");
        Carrito instance = new Carrito();
        int expResult = 0;
        int result = instance.getCantidadItems();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of vaciar method, of class Carrito.
     */
    @Test
    public void testVaciar() {
        System.out.println("vaciar");
        Carrito instance = new Carrito();
        instance.vaciar();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of obtenerResumenParaVoz method, of class Carrito.
     */
    @Test
    public void testObtenerResumenParaVoz() {
        System.out.println("obtenerResumenParaVoz");
        Carrito instance = new Carrito();
        String expResult = "";
        String result = instance.obtenerResumenParaVoz();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Carrito.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Carrito instance = new Carrito();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
