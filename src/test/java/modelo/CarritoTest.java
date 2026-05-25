/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package modelo;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author isabe
 */
public class CarritoTest {

    public CarritoTest() {
    }
    /**
     * Test del método agregarProducto, de la clase Carrito.
     */
    @Test
    public void testAgregarProducto() {
        Carrito carrito = new Carrito();

        Producto producto = new Producto("Leche", 5000, "A1", "Pasillo");
        carrito.agregarProducto(producto);

        int cantidad = carrito.getProductos().size();

        assertEquals(1, cantidad, "Error al agregar producto");
    }
    
    /**
     * Test2 del método agregarProducto, de la clase Carrito.
     */
    @Test
    public void testAgregarProductoDuplicado() {
        Carrito carrito = new Carrito();

        Producto producto1 = new Producto("Arroz", 3000, "B2", "Pasillo");
        Producto producto2 = new Producto("Arroz", 3000, "B2", "Pasillo");

        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);

        int cantidadProductos = carrito.getProductos().size();
        int cantidadItems = carrito.getCantidadItems();

        assertEquals(1, cantidadProductos, "Se duplicó el producto");
        assertEquals(2, cantidadItems, "No aumentó la cantidad");
    }
    
    /**
     * Test null del método agregarProducto, de la clase Carrito.
     */
    @Test
    public void testAgregarProductoNull() {
        Carrito carrito = new Carrito();

        carrito.agregarProducto(null);

        int cantidad = carrito.getProductos().size();

        assertEquals(0, cantidad, "Se agregó un producto null");
    }
    
    /**
     * Test del método eliminarProducto, de la clase Carrito.
     */
    @Test
    public void testEliminarProducto() {
        Carrito carrito = new Carrito();

        Producto producto = new Producto("Huevos", 12000, "C3", "Pasillo");

        carrito.agregarProducto(producto);
        carrito.eliminarProducto("Huevos");

        int cantidad = carrito.getProductos().size();

        assertEquals(0, cantidad, "No eliminó el producto");
    }

    /**
     * Test2 del método eliminarProducto, de la clase Carrito.
     */
    @Test
    public void testEliminarProductoNull() {
        Carrito carrito = new Carrito();

        Producto producto = new Producto("Pan", 2500, "A2", "Pasillo");

        carrito.agregarProducto(producto);
        carrito.eliminarProducto(null);

        int cantidad = carrito.getProductos().size();

        assertEquals(1, cantidad, "Eliminó un producto incorrectamente");
    }
    
    /**
     * Test del método getProductos, de la clase Carrito.
     */
    @Test
    public void testGetProductos() {
        Carrito carrito = new Carrito();

        Producto producto = new Producto("Queso", 8000, "D1", "Pasillo");

        carrito.agregarProducto(producto);

        List<Producto> productos = carrito.getProductos();

        assertFalse(productos.isEmpty(), "La lista está vacía");
    }
    
    /**
     * Test del método obtenerTotal, de la clase Carrito.
     */
    @Test
    public void testObtenerTotal() {
        Carrito carrito = new Carrito();

        Producto producto1 = new Producto("Leche", 5000, "A1", "Pasillo");
        Producto producto2 = new Producto("Pan", 2000, "B1", "Pasillo");

        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);

        double total = carrito.obtenerTotal();

        assertEquals(7000, total, "Error al calcular total");
    }
    
    /**
     * Test del método getCantidadItems, de la clase Carrito.
     */
    @Test
    public void testGetCantidadItems() {
        Carrito carrito = new Carrito();

        Producto producto1 = new Producto("Galletas", 3000, "A4", "Pasillo");
        Producto producto2 = new Producto("Galletas", 3000, "A4", "Pasillo");

        carrito.agregarProducto(producto1);
        carrito.agregarProducto(producto2);

        int cantidad = carrito.getCantidadItems();

        assertEquals(2, cantidad, "Cantidad incorrecta");
    }
    
    /**
     * Test del método vaciar, de la clase Carrito.
     */
    @Test
    public void testVaciar() {
        Carrito carrito = new Carrito();

        Producto producto = new Producto("Café", 10000, "E2", "Pasillo");

        carrito.agregarProducto(producto);
        carrito.vaciar();

        int cantidad = carrito.getProductos().size();

        assertEquals(0, cantidad, "No se vació el carrito");
    }

    /**
     * Test del método obtenerResumenParaVoz, de la clase Carrito.
     */
    @Test
    public void testObtenerResumenParaVoz() {
        Carrito carrito = new Carrito();

        Producto producto = new Producto("Arroz", 4000, "B2", "Pasillo");

        carrito.agregarProducto(producto);

        String resumen = carrito.obtenerResumenParaVoz();

        assertTrue(resumen.contains("Arroz"), "No contiene el producto");
    }

    /**
     * Test2 del método obtenerResumenParaVoz, de la clase Carrito.
     */
    @Test
    public void testObtenerResumenParaVozVacio() {
        Carrito carrito = new Carrito();

        String resumen = carrito.obtenerResumenParaVoz();

        assertEquals("Tu carrito está vacío", resumen, "Mensaje incorrecto");
    }

    /**
     * Test del método ToString, de la clase Carrito.
     */
    @Test
    public void testToString() {
        Carrito carrito = new Carrito();

        Producto producto = new Producto("Azúcar", 3500, "C1", "Pasillo");

        carrito.agregarProducto(producto);

        String texto = carrito.toString();

        assertTrue(texto.contains("Azúcar"), "No aparece el producto");
    }

    /**
     * Test2 del método ToString, de la clase Carrito.
     */
    @Test
    public void testToStringVacio() {
        Carrito carrito = new Carrito();

        String texto = carrito.toString();

        assertEquals("Carrito vacío", texto, "Texto incorrecto");
    }

}
