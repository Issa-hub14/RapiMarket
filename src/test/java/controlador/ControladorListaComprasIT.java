/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package controlador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import modelo.*;
import vista.*;
import java.util.List;
/**
 *
 * @author isabe
 */
public class ControladorListaComprasIT {
    
    public ControladorListaComprasIT() {
    }
    
    /**
     * Test de iniciar, la clase ControladorListaCompras.
     */
    @Test
    public void testControladorListaComprasInicializaCorrectamente() {
        ModeloTienda modelo = new ModeloTienda();
        VistaListaCompras vista = new VistaListaCompras();
        ControladorListaCompras controlador = new ControladorListaCompras(vista, modelo);

        assertNotNull(controlador, "El controlador debe inicializarse");
    }

    /**
     * Test del método agregarProductoListaMercado, de la clase ControladorListaCompras.
     */
    @Test
    public void testAgregarProductoListaMercado() {
        ModeloTienda modelo = new ModeloTienda();
        modelo.agregarAListaMercado("Leche Entera 1L");
        assertTrue( modelo.obtenerListaDeMercado().contains("Leche Entera 1L"),"Debe agregar el producto a la lista");
    }

    /**
     * Test2 del método agregarProductoListaMercado, de la clase ControladorListaCompras.
     */
    @Test
    public void testNoAgregarProductoDuplicado() {
        ModeloTienda modelo = new ModeloTienda();
        modelo.agregarAListaMercado("Leche");
        modelo.agregarAListaMercado("Leche");

        assertEquals(1,modelo.obtenerListaDeMercado().size(),"No debe agregar productos duplicados"
        );
    }

    /**
     * Test del método eliminarProductoLista, de la clase ControladorListaCompras.
     */
    @Test
    public void testEliminarProductoLista() {
        ModeloTienda modelo = new ModeloTienda();
        modelo.agregarAListaMercado("Arroz 2kg");
        modelo.eliminarDeListaMercado("Arroz 2kg");
        assertFalse(modelo.obtenerListaDeMercado().contains("Arroz 2kg"),"Debe eliminar el producto"
        );
    }

    /**
     * Test del método buscarProducto (PorNombre), de la clase ControladorListaCompras.
     */
    @Test
    public void testBuscarProductoPorNombre() {
        ModeloTienda modelo = new ModeloTienda();
        List<Producto> resultados = modelo.buscarProductos("Leche");

        assertFalse(resultados.isEmpty(),"Debe encontrar resultados");
        assertEquals("Leche Entera 1L",resultados.get(0).getNombre(),"Producto incorrecto");
    }

    /**
     * Test del método buscarProducto (inexistente), de la clase ControladorListaCompras.
     */
    @Test
    public void testBuscarProductoInexistente() {
        ModeloTienda modelo = new ModeloTienda();
        List<Producto> resultados = modelo.buscarProductos("Chocolate");
        assertTrue(resultados.isEmpty(),"No debería encontrar resultados");
    }

    /**
     * Test de incio de lista vacia de la clase ControladorListaCompras.
     */
    @Test
    public void testListaMercadoIniciaVacia() {
        ModeloTienda modelo = new ModeloTienda();
        assertTrue(modelo.obtenerListaDeMercado().isEmpty(), "La lista debe iniciar vacía");
    }

    /**
     * Test del método agregarItem (vacio), de la clase ControladorListaCompras.
     */
    @Test
    public void testAgregarItemVacioListaMercado() {

        ModeloTienda modelo = new ModeloTienda();
        modelo.agregarAListaMercado("");
        assertTrue(modelo.obtenerListaDeMercado().isEmpty(),"No debe agregar elementos vacíos");
    }

     /**
     * Test del método agregarItem (null), de la clase ControladorListaCompras.
     */
    @Test
    public void testAgregarItemNullListaMercado() {
        ModeloTienda modelo = new ModeloTienda();
        modelo.agregarAListaMercado(null);
        assertTrue( modelo.obtenerListaDeMercado().isEmpty(), "No debe agregar elementos null");
    }

     /**
     * Test del método obtenerCatalogoProductos, de la clase ControladorListaCompras.
     */
    @Test
    public void testObtenerCatalogoProductos() {
        ModeloTienda modelo = new ModeloTienda();
        List<Producto> catalogo = modelo.obtenerCatalogo();
        assertFalse(catalogo.isEmpty(),"El catálogo no debe estar vacío");
    }

     /**
     * Test del método buscarProductos (categoria), de la clase ControladorListaCompras.
     */
    @Test
    public void testBuscarProductosPorCategoria() {
        ModeloTienda modelo = new ModeloTienda();
        List<Producto> resultados= modelo.buscarProductos("Carnes");
        assertFalse(resultados.isEmpty(),"Debe encontrar productos por categoría");
    }

     /**
     * Test del método obetenrCarrito, de la clase ControladorListaCompras.
     */
    @Test
    public void testObtenerCarrito() {
        ModeloTienda modelo = new ModeloTienda();
        Carrito carrito = modelo.obtenerCarrito();
        assertNotNull(carrito,"El carrito no debe ser null");
    }
    
}
