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
public class ControladorSuperMercadoIT {

    public ControladorSuperMercadoIT() {
    }

    /**
     * Test del método refrescarLista, de la clase ControladorSuperMercado.
     */
    @Test
        public void testRefrescarListaAgregarProducto() {
        ModeloTienda modelo = new ModeloTienda();
        modelo.agregarAListaMercado("Arroz 2kg");
        List<String> lista = modelo.obtenerListaDeMercado();

        assertEquals(1,lista.size(),"Fallo en el testRefrescarListaDespuesAgregarProducto");
    }
    
    /**
     * Test2 del método refrescarLista, de la clase ControladorSuperMercado.
     */
    @Test
    public void testRefrescarListaDespuesEliminarProducto() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Arroz 2kg");
        modelo.eliminarDeListaMercado("Arroz 2kg");

        List<String> lista = modelo.obtenerListaDeMercado();

        assertTrue(lista.isEmpty(),"Fallo en el testRefrescarListaDespuesEliminarProducto");
    }

    /**
     * Test del método siguienteProducto (lista vacia), de la clase ControladorSuperMercado.
     */
    @Test
    public void testSiguienteProductoListaVacia() {
        ModeloTienda modelo = new ModeloTienda();
        boolean vacia = modelo.obtenerListaDeMercado().isEmpty();

        assertTrue(vacia,"Fallo en el testSiguienteProductoListaVacia");
    }

    /**
     * Test del método siguienteProducto (un elemento), de la clase ControladorSuperMercado.
     */
    @Test
    public void testSiguienteProductoUnElemento() {
        ModeloTienda modelo = new ModeloTienda();
        modelo.agregarAListaMercado("Leche Entera 1L");
        String producto = modelo.obtenerListaDeMercado().get(0);

        assertEquals("Leche Entera 1L", producto, "Fallo en el testSiguienteProductoUnElemento");
    }

    /**
     * Test del método siguienteProducto (varios elementos), de la clase ControladorSuperMercado.
     */
    @Test
    public void testSiguienteProductoVariosElementos() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Leche Entera 1L");
        modelo.agregarAListaMercado("Arroz 2kg");
        modelo.agregarAListaMercado("Pan Tajado");

        assertEquals(3, modelo.obtenerListaDeMercado().size(), "Fallo en el testSiguienteProductoVariosElementos");
    }

    /**
     * Test del método buscarProducto (por categoria), de la clase ControladorSuperMercado.
     */
    @Test
    public void testBuscarProductoPorCategoria() {
        ModeloTienda modelo = new ModeloTienda();

        List<Producto> resultados = modelo.buscarProductos("Aseo");

        assertFalse(resultados.isEmpty(), "Fallo en el testBuscarProductoPorCategoria");
    }

    /**
     * Test del método buscarProducto (por Nombre), de la clase ControladorSuperMercado.
     */
    @Test
    public void testBuscarProductoPorNombre() {
        ModeloTienda modelo = new ModeloTienda();

        Producto producto = modelo.buscarProductoPorNombre("Detergente 1kg");

        assertEquals("Detergente 1kg", producto.getNombre(), "Fallo en el testBuscarProductoPorNombre");
    }

    /**
     * Test del método agregarProducto, de la clase ControladorSuperMercado.
     */
    @Test
    public void testAgregarProducto() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Leche Entera 1L");
        modelo.agregarAListaMercado("Pan Tajado");

        assertEquals(2,modelo.obtenerListaDeMercado().size(),"Fallo en el testAgregarProducto");
    }

    /**
     * Test del método agregarProducto (no agregarlo), de la clase ControladorSuperMercado.
     */
    @Test
    public void testNoAgregarProducto() {
        ModeloTienda modelo = new ModeloTienda();

        modelo.agregarAListaMercado("Pan Tajado");
        modelo.agregarAListaMercado("Pan Tajado");

        assertEquals(1,
                modelo.obtenerListaDeMercado().size(),
                "Fallo en el testNoAgregarProducto");
    }

}
