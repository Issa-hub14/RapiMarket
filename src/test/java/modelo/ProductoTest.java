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
public class ProductoTest {

    public ProductoTest() {
    }
   
    /**
     * Test del constructor, de la clase Producto.
     */
    @Test
    public void testConstructorProducto() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        assertEquals("Leche", producto.getNombre(),"Error en nombre");
        assertEquals(3500, producto.getPrecio(),"Error en precio");
        assertEquals("Lácteos", producto.getCategoria(),"Error en categoría");
        assertEquals("Pasillo 1", producto.getPasillo(),"Error en pasillo");
        assertEquals("", producto.getDescripcion(),"La descripción debe iniciar vacía");
        assertEquals(1, producto.getCantidad(),"La cantidad debe iniciar en 1");
    }

    /**
     * Test2 del constructor, de la clase Producto.
     */
    @Test
    public void testConstructorProductoConDescripcion() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1",
                "Leche entera"
        );
        assertEquals("Leche entera",producto.getDescripcion(),"Error en descripción");
    }
    
    /**
     * Test del método setNombre, de la clase Producto.
     */
    @Test
    public void testSetNombre() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        producto.setNombre("Queso");
        assertEquals("Queso", producto.getNombre(),"Error al cambiar nombre");
    }

    /**
     * Test del método setPrecio, de la clase Producto.
     */
    @Test
    public void testSetPrecio() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        producto.setPrecio(5000);
        assertEquals(5000, producto.getPrecio(),"Error al cambiar precio");
    }

    /**
     * Test2 del método setPrecio, de la clase Producto.
     */
    @Test
    public void testSetPrecioNegativo() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        producto.setPrecio(-100);
        assertEquals(3500, producto.getPrecio(), "No debe aceptar precios negativos");
    }

    /**
     * Test del método setCategoria, de la clase Producto.
     */
    @Test
    public void testSetCategoria() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        producto.setCategoria("Bebidas");
        assertEquals("Bebidas", producto.getCategoria(), "Error al cambiar categoría");
    }

    /**
     * Test del método setPasillo, de la clase Producto.
     */
    @Test
    public void testSetPasillo() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        producto.setPasillo("Pasillo 5");
        assertEquals("Pasillo 5", producto.getPasillo(), "Error al cambiar pasillo");
    }

    /**
     * Test del método setDescripcion, de la clase Producto.
     */
    @Test
    public void testSetDescripcion() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        producto.setDescripcion("Producto fresco");
        assertEquals("Producto fresco", producto.getDescripcion(), "Error al cambiar descripción");
    }

    /**
     * Test del método setCantidad, de la clase Producto.
     */
    @Test
    public void testSetCantidad() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        producto.setCantidad(3);
        assertEquals(3, producto.getCantidad(), "Error al cambiar cantidad");
    }

    /**
     * Test2 del método setCantidad, de la clase Producto.
     */
    @Test
    public void testSetCantidadNegativa() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        producto.setCantidad(-5);
        assertEquals(1, producto.getCantidad(), "No debe aceptar cantidades negativas");
    }

    /**
     * Test del método precioTotal, de la clase Producto.
     */
    @Test
    public void testPrecioTotal() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        producto.setCantidad(2);
        double total = producto.PrecioTotal();
        assertEquals(7000, total, "Error en el cálculo del precio total");
    }

    /**
     * Test del método toString, de la clase Producto.
     */
    @Test
    public void testToString() {
        Producto producto = new Producto(
                "Leche",
                3500,
                "Lácteos",
                "Pasillo 1"
        );
        String texto = producto.toString();
        assertTrue(texto.contains("Leche"), "Debe contener el nombre");
        assertTrue(texto.contains("3500"), "Debe contener el precio");
    }

}
