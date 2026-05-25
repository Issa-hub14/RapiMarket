/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package controlador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import modelo.*;
import java.util.List;

/**
 *
 * @author isabe
 */
public class ControladorPedidoIT {

    public ControladorPedidoIT() {
    }

    /**
     * Test del método buscarProducto, de la clase ControladorPedido.
     */
    @Test
    public void testBuscarProducto() {
        ModeloTienda modelo = new ModeloTienda();
        List<Producto> resultados = modelo.buscarProductos("Leche");
        assertFalse(resultados.isEmpty(), "Fallo en el testBuscarProducto");
    }

    /**
     * Test2 del método buscarProducto, de la clase ControladorPedido.
     */
    @Test
    public void testBuscarProductoSinResultados() {
        ModeloTienda modelo = new ModeloTienda();
        List<Producto> resultados = modelo.buscarProductos("Xbox");
        assertTrue(resultados.isEmpty(), "Fallo en el testBuscarProductoSinResultados");
    }

    /**
     * Test3 del método buscarProducto, de la clase ControladorPedido.
     */
    @Test
    public void testBuscarProductoPorNombre() {
        ModeloTienda modelo = new ModeloTienda();
        Producto producto = modelo.buscarProductoPorNombre("Arroz 2kg");
        assertNotNull(producto, "Fallo en el testBuscarProductoPorNombre");
    }

    /**
     * Test4 del método buscarProducto, de la clase ControladorPedido.
     */
    @Test
    public void testBuscarProductoPorNombreNoExiste() {
        ModeloTienda modelo = new ModeloTienda();
        Producto producto = modelo.buscarProductoPorNombre("PlayStation");
        assertNull(producto, "Fallo en el testBuscarProductoPorNombreNoExiste");
    }

    /**
     * Test del método agregarProductoAlCarrito, de la clase ControladorPedido.
     */
    @Test
    public void testAgregarProductoAlCarrito() {
        ModeloTienda modelo = new ModeloTienda();
        Producto producto = modelo.buscarProductoPorNombre("Pan Tajado");
        modelo.obtenerCarrito().agregarProducto(producto);

        assertEquals(1, modelo.obtenerCarrito().getProductos().size(),"Fallo en el testAgregarProductoAlCarrito");
    }

    /**
     * Test del método eliminarProductoDelCarrito, de la clase ControladorPedido.
     */
    @Test
    public void testEliminarProductoDelCarrito() {
        ModeloTienda modelo = new ModeloTienda();
        Producto producto = modelo.buscarProductoPorNombre("Pan Tajado");

        modelo.obtenerCarrito().agregarProducto(producto);
        modelo.obtenerCarrito().eliminarProducto("Pan Tajado");

        assertTrue(modelo.obtenerCarrito().getProductos().isEmpty(),"Fallo en el testEliminarProductoDelCarrito");
    }

    /**
     * Test del método agregarAListaMercado, de la clase ControladorPedido.
     */
    @Test
    public void testAgregarAListaMercado() {
        ModeloTienda modelo = new ModeloTienda();
        modelo.agregarAListaMercado("Leche Entera 1L");

        assertEquals(1, modelo.obtenerListaDeMercado().size(),"Fallo en el testAgregarAListaMercado");
    }

    /**
     * Test2 del método agregarAListaMercado, de la clase ControladorPedido.
     */
    @Test
    public void testNoAgregarDuplicadoListaMercado() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Leche Entera 1L");
        modelo.agregarAListaMercado("Leche Entera 1L");

        assertEquals(1, modelo.obtenerListaDeMercado().size(), "Fallo en el testNoAgregarDuplicadoListaMercado");
    }

     /**
     * Test del método eliminarDeListaMercado, de la clase ControladorPedido.
     */
    @Test
    public void testEliminarDeListaMercado() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Leche Entera 1L");
        modelo.eliminarDeListaMercado("Leche Entera 1L");

        assertTrue(modelo.obtenerListaDeMercado().isEmpty(),"Fallo en el testEliminarDeListaMercado");
    }

     /**
     * Test del método obtenerTotalCarrito, de la clase ControladorPedido.
     */
    @Test
    public void testObtenerTotalCarrito() {
        ModeloTienda modelo = new ModeloTienda();

        Producto producto = modelo.buscarProductoPorNombre("Arroz 2kg");
        modelo.obtenerCarrito().agregarProducto(producto);
        double total = modelo.obtenerCarrito().obtenerTotal();

        assertEquals(6500, total,"Fallo en el testObtenerTotalCarrito");
    }

     /**
     * Test2 del método vaciarCarrito, de la clase ControladorPedido.
     */
    @Test
    public void testVaciarCarrito() {
        ModeloTienda modelo = new ModeloTienda();

        Producto producto = modelo.buscarProductoPorNombre("Arroz 2kg");

        modelo.obtenerCarrito().agregarProducto(producto);
        modelo.obtenerCarrito().vaciar();

        assertTrue(modelo.obtenerCarrito().getProductos().isEmpty(),"Fallo en el testVaciarCarrito");
    }

     /**
     * Test2 del método setClienteActual, de la clase ControladorPedido.
     */
    @Test
    public void testSetClienteActual() {
        ModeloTienda modelo = new ModeloTienda();
        ClienteRegistrado cliente = new ClienteRegistrado(
                "Ana",
                123,
                "Calle 1",
                "ana@gmail.com",
                "3001234567"
        );
        modelo.setClienteActual(cliente);
        assertEquals(cliente,modelo.getClienteActual(), "Fallo en el testSetClienteActual");
    }

}
