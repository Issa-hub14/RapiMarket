/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package servicio;

import modelo.Carrito;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;
import modelo.*;

/**
 *
 * @author isabe
 */
public class VentaTXTServiceTest {

    public VentaTXTServiceTest() {
    }

    /**
     * Test del método guardarVenta, de la clase VentaTXTService.
     */
    @Test
    public void testGuardarVentaClienteRegistrado() throws Exception {
        Files.deleteIfExists(Paths.get("ventas.txt"));

        VentaTXTService ventaService = new VentaTXTService();
        Carrito carrito = new Carrito();
        Producto producto = new Producto("Leche", 2000, "Lácteos", "A1");
        carrito.agregarProducto(producto);

        ventaService.guardarVenta(carrito, true);

        String contenido = new String(Files.readAllBytes(Paths.get("ventas.txt")));

        assertTrue(contenido.contains("Tipo: Cliente Registrado"));
        assertTrue(contenido.contains("Leche"));
        assertTrue(contenido.contains("TOTAL: $2000"));
    }

    /**
     * Test2 del método guardarVenta, de la clase VentaTXTService.
     */
    @Test
    public void testGuardarVentaClienteInvitado() throws Exception {
        Files.deleteIfExists(Paths.get("ventas.txt"));

        VentaTXTService ventaService = new VentaTXTService();
        Carrito carrito = new Carrito();
        Producto producto = new Producto("Pan", 1500, "Panadería", "B2");
        carrito.agregarProducto(producto);
        ventaService.guardarVenta(carrito, false);

        String contenido = new String(Files.readAllBytes(Paths.get("ventas.txt")));

        assertTrue(contenido.contains("Tipo: Cliente Invitado"));
        assertTrue(contenido.contains("Pan"));
        assertTrue(contenido.contains("TOTAL: $1500"));
    }

    /**
     * Test3 del método guardarVenta, de la clase VentaTXTService.
     */
    @Test
    public void testGuardarVentaMultiplesProductos() throws Exception {
        Files.deleteIfExists(Paths.get("ventas.txt"));

        VentaTXTService ventaService = new VentaTXTService();
        Carrito carrito = new Carrito();
        Producto leche = new Producto("Leche", 2000, "Lácteos", "A1");
        Producto pan = new Producto("Pan", 1500, "Panadería", "B2");
        carrito.agregarProducto(leche);
        carrito.agregarProducto(pan);

        ventaService.guardarVenta(carrito, true);

        String contenido = new String(Files.readAllBytes(Paths.get("ventas.txt")));

        assertTrue(contenido.contains("Leche"));
        assertTrue(contenido.contains("Pan"));
        assertTrue(contenido.contains("TOTAL: $3500"));
    }

}
