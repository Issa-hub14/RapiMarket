/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package modelo;

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
public class ProductoTest {
    
    public ProductoTest() {
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
     * Test of getNombre method, of class Producto.
     */
    @Test
    public void testGetNombre() {
        System.out.println("getNombre");
        Producto instance = null;
        String expResult = "";
        String result = instance.getNombre();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrecio method, of class Producto.
     */
    @Test
    public void testGetPrecio() {
        System.out.println("getPrecio");
        Producto instance = null;
        double expResult = 0.0;
        double result = instance.getPrecio();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCategoria method, of class Producto.
     */
    @Test
    public void testGetCategoria() {
        System.out.println("getCategoria");
        Producto instance = null;
        String expResult = "";
        String result = instance.getCategoria();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPasillo method, of class Producto.
     */
    @Test
    public void testGetPasillo() {
        System.out.println("getPasillo");
        Producto instance = null;
        String expResult = "";
        String result = instance.getPasillo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDescripcion method, of class Producto.
     */
    @Test
    public void testGetDescripcion() {
        System.out.println("getDescripcion");
        Producto instance = null;
        String expResult = "";
        String result = instance.getDescripcion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCantidad method, of class Producto.
     */
    @Test
    public void testGetCantidad() {
        System.out.println("getCantidad");
        Producto instance = null;
        int expResult = 0;
        int result = instance.getCantidad();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNombre method, of class Producto.
     */
    @Test
    public void testSetNombre() {
        System.out.println("setNombre");
        String nombre = "";
        Producto instance = null;
        instance.setNombre(nombre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrecio method, of class Producto.
     */
    @Test
    public void testSetPrecio() {
        System.out.println("setPrecio");
        double precio = 0.0;
        Producto instance = null;
        instance.setPrecio(precio);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCategoria method, of class Producto.
     */
    @Test
    public void testSetCategoria() {
        System.out.println("setCategoria");
        String categoria = "";
        Producto instance = null;
        instance.setCategoria(categoria);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPasillo method, of class Producto.
     */
    @Test
    public void testSetPasillo() {
        System.out.println("setPasillo");
        String pasillo = "";
        Producto instance = null;
        instance.setPasillo(pasillo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDescripcion method, of class Producto.
     */
    @Test
    public void testSetDescripcion() {
        System.out.println("setDescripcion");
        String descripcion = "";
        Producto instance = null;
        instance.setDescripcion(descripcion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCantidad method, of class Producto.
     */
    @Test
    public void testSetCantidad() {
        System.out.println("setCantidad");
        int cantidad = 0;
        Producto instance = null;
        instance.setCantidad(cantidad);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of PrecioTotal method, of class Producto.
     */
    @Test
    public void testPrecioTotal() {
        System.out.println("PrecioTotal");
        Producto instance = null;
        double expResult = 0.0;
        double result = instance.PrecioTotal();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Producto.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Producto instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
