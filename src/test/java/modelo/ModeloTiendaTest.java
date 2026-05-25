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
public class ModeloTiendaTest {

    public ModeloTiendaTest() {
    }

    /**
     * Test del constructor, de la clase ModeloTienda.
     */
    @Test
    public void testConstructorModeloTienda() {
        ModeloTienda modelo = new ModeloTienda();

        assertNotNull(modelo.obtenerCarrito(), "El carrito no debe ser null");
        assertNotNull(modelo.obtenerCatalogo(), "El catálogo no debe ser null");
        assertNotNull(modelo.obtenerListaDeMercado(), "La lista de mercado no debe ser null");
        assertFalse(modelo.obtenerCatalogo().isEmpty(), "El catálogo debe tener productos");
    }

    /**
     * Test de ClienteActual, de la clase ModeloTienda.
     */
    @Test
    public void testClienteActualInicial() {
        ModeloTienda modelo = new ModeloTienda();

        assertEquals(
                "Cliente Invitado",
                modelo.getClienteActual().obtenerTipoCliente(),
                "Debe iniciar con cliente invitado"
        );
    }

    /**
     * Test del método setClienteActual, de la clase ModeloTienda.
     */
    @Test
    public void testSetClienteActual() {
        ModeloTienda modelo = new ModeloTienda();

        ClienteRegistrado cliente = new ClienteRegistrado(
                "Juan",
                123,
                "Calle 10",
                "juan@gmail.com",
                "3001234567"
        );

        modelo.setClienteActual(cliente);
        assertEquals(cliente, modelo.getClienteActual(), "Error al cambiar cliente");
    }

    /**
     * Test2 del método setClienteActual, de la clase ModeloTienda.
     */
    @Test
    public void testSetClienteActualNull() {
        ModeloTienda modelo = new ModeloTienda();

        Persona anterior = modelo.getClienteActual();

        modelo.setClienteActual(null);

        assertEquals(anterior, modelo.getClienteActual(),
                "No debe cambiar el cliente si es null");
    }

    /**
     * Test del método BuscarProductos (PorNombre), de la clase ModeloTienda.
     */
    @Test
    public void testBuscarProductosPorNombre() {
        ModeloTienda modelo = new ModeloTienda();

        List<Producto> resultados = modelo.buscarProductos("Leche");

        assertFalse(resultados.isEmpty(), "Debe encontrar productos");
        assertEquals("Leche Entera 1L",
                resultados.get(0).getNombre(),
                "Producto incorrecto");
    }

    /**
     * Test2 del método BuscarProductos (PorCategoria), de la clase
     * ModeloTienda.
     */
    @Test
    public void testBuscarProductosPorCategoria() {
        ModeloTienda modelo = new ModeloTienda();

        List<Producto> resultados = modelo.buscarProductos("Lácteos");

        assertFalse(resultados.isEmpty(), "Debe encontrar productos por categoría");
    }

    /**
     * Test3 del método BuscarProductos (vacio), de la clase ModeloTienda.
     */
    @Test
    public void testBuscarProductosVacio() {
        ModeloTienda modelo = new ModeloTienda();

        List<Producto> resultados = modelo.buscarProductos("");

        assertEquals(
                modelo.obtenerCatalogo().size(),
                resultados.size(),
                "Debe devolver todo el catálogo"
        );
    }

    /**
     * Test4 del método BuscarProductos (PorNombre), de la clase ModeloTienda.
     */
    @Test
    public void testBuscarProductoPorNombre() {
        ModeloTienda modelo = new ModeloTienda();

        Producto producto = modelo.buscarProductoPorNombre("Arroz 2kg");

        assertNotNull(producto, "Debe encontrar el producto");
        assertEquals("Granos", producto.getCategoria(), "Categoría incorrecta");
    }

    /**
     * Test5 del método BuscarProductos (inexistente), de la clase ModeloTienda.
     */
    @Test
    public void testBuscarProductoPorNombreInexistente() {
        ModeloTienda modelo = new ModeloTienda();

        Producto producto = modelo.buscarProductoPorNombre("Pizza");

        assertNull(producto, "Debe retornar null");
    }

    /**
     * Test del método obtenerCarrito, de la clase ModeloTienda.
     */
    @Test
    public void testObtenerCarrito() {
        ModeloTienda modelo = new ModeloTienda();

        Carrito carrito = modelo.obtenerCarrito();

        assertNotNull(carrito, "El carrito no debe ser null");
    }

    /**
     * Test del método obtenerCatalogo, de la clase ModeloTienda.
     */
    @Test
    public void testObtenerCatalogo() {
        ModeloTienda modelo = new ModeloTienda();

        List<Producto> catalogo = modelo.obtenerCatalogo();

        assertFalse(catalogo.isEmpty(), "El catálogo no debe estar vacío");
    }

    /**
     * Test del método agregarAListaMercado, de la clase ModeloTienda.
     */
    @Test
    public void testAgregarAListaMercado() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Leche");

        assertTrue(
                modelo.obtenerListaDeMercado().contains("Leche"),
                "Debe agregar el producto"
        );
    }

    /**
     * Test2 del método agregarAListaMercado, de la clase ModeloTienda.
     */
    @Test
    public void testAgregarDuplicadoListaMercado() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Leche");
        modelo.agregarAListaMercado("Leche");

        assertEquals(
                1,
                modelo.obtenerListaDeMercado().size(),
                "No debe agregar duplicados"
        );
    }

    /**
     * Test3 del método agregarAListaMercado (vacio), de la clase ModeloTienda.
     */
    @Test
    public void testAgregarItemVacioListaMercado() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("");

        assertTrue(
                modelo.obtenerListaDeMercado().isEmpty(),
                "No debe agregar elementos vacíos"
        );
    }

    /**
     * Test del método eliminarDeListaMercado, de la clase ModeloTienda.
     */
    @Test
    public void testEliminarDeListaMercado() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Leche");
        modelo.eliminarDeListaMercado("Leche");

        assertFalse(
                modelo.obtenerListaDeMercado().contains("Leche"),
                "Debe eliminar el producto"
        );
    }
}
