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
public class ControladorPrincipalIT {

    public ControladorPrincipalIT() {
    }

    /**
     * Test de iniciar la clase ControladorPrincipal.
     */
    @Test
    public void testModeloInicializado() {
        ModeloTienda modelo = new ModeloTienda();

        assertNotNull(modelo, "Fallo en el testModeloInicializado");
    }

    /**
     * Test de clienteInicial, de la clase ControladorPrincipal.
     */
    @Test
    public void testClienteInicialEsInvitado() {
        ModeloTienda modelo = new ModeloTienda();
        Persona cliente = modelo.getClienteActual();

        assertEquals("Cliente Invitado",cliente.obtenerTipoCliente(),"Fallo en el testClienteInicialEsInvitado");
    }

    /**
     * Test de catalogo, de la clase ControladorPrincipal.
     */
    @Test
    public void testCatalogoNoVacio() {
        ModeloTienda modelo = new ModeloTienda();

        assertFalse(modelo.obtenerCatalogo().isEmpty(),"Fallo en el testCatalogoNoVacio");
    }

    /**
     * Test del método agregarProductoListaMercado, de la clase ControladorPrincipal.
     */
    @Test
    public void testAgregarProductoListaMercado() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Pan Tajado");

        assertTrue(modelo.obtenerListaDeMercado().contains("Pan Tajado"),"Fallo en el testAgregarProductoListaMercado");
    }

    /**
     * Test del método eliminarProductoListaMercado, de la clase ControladorPrincipal.
     */
    @Test
    public void testEliminarProductoListaMercado() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Pan Tajado");
        modelo.eliminarDeListaMercado("Pan Tajado");

        assertFalse(modelo.obtenerListaDeMercado().contains("Pan Tajado"),"Fallo en el testEliminarProductoListaMercado");
    }

    /**
     * Test del método buscarProducto (Por categoria), de la clase ControladorPrincipal.
     */
    @Test
    public void testBuscarProductoPorCategoria() {
        ModeloTienda modelo = new ModeloTienda();
        List<Producto> resultados = modelo.buscarProductos("Lácteos");

        assertFalse(resultados.isEmpty(), "Fallo en el testBuscarProductoPorCategoria");
    }
    
    /**
     * Test del carrito (vacio), de la clase ControladorPrincipal.
     */
    @Test
    public void testCarritoVacio() {
        ModeloTienda modelo = new ModeloTienda();

        assertTrue(modelo.obtenerCarrito().getProductos().isEmpty(),"Fallo en el testCarritoVacio");
    }

    /**
     * Test del método agregarProductoCarrito, de la clase ControladorPrincipal.
     */
    @Test
    public void testAgregarProductoCarrito() {
        ModeloTienda modelo = new ModeloTienda();

        Producto producto = modelo.buscarProductoPorNombre("Arepa x6");
        modelo.obtenerCarrito().agregarProducto(producto);

        assertEquals(1, modelo.obtenerCarrito().getCantidadItems(),"Fallo en el testAgregarProductoCarrito");
    }

    /**
     * Test del método totalCarrito, de la clase ControladorPrincipal.
     */
    @Test
    public void testTotalCarrito() {
        ModeloTienda modelo = new ModeloTienda();

        Producto producto1 = modelo.buscarProductoPorNombre("Arepa x6");
        Producto producto2 = modelo.buscarProductoPorNombre("Arroz 2kg");

        modelo.obtenerCarrito().agregarProducto(producto1);
        modelo.obtenerCarrito().agregarProducto(producto2);

        double total = modelo.obtenerCarrito().obtenerTotal();

        assertEquals(11000,total,"Fallo en el testTotalCarrito");
    }

    /**
     * Test del método cambiarClienteActual, de la clase ControladorPrincipal.
     */
    @Test
    public void testCambiarClienteActual() {
        ModeloTienda modelo = new ModeloTienda();

        ClienteRegistrado cliente = new ClienteRegistrado(
                "Carlos",
                456,
                "Calle 10",
                "carlos@gmail.com",
                "3011111111"
        );
        modelo.setClienteActual(cliente);
        assertEquals("Carlos",modelo.getClienteActual().getNombre(),"Fallo en el testCambiarClienteActual");
    }

}
