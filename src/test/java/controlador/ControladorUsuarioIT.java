/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package controlador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import vista.VistaUsuario;

/**
 *
 * @author isabe
 */
public class ControladorUsuarioIT {

    public ControladorUsuarioIT() {
    }

    /**
     * Test del método convertirPalabraANumero (solo numeros), de la clase ControladorUsuario.
     */
    @Test
    public void testConvertirPalabraANumero() {
        VistaUsuario vista = new VistaUsuario();
        ControladorUsuario controlador = new ControladorUsuario(vista, null, null);
        String resultado = controlador.convertirPalabraANumero("uno dos tres");
        assertEquals("123", resultado, "Fallo en el test1");
    }

    /**
     * Test2 del método convertirPalabraANumero, de la clase ControladorUsuario.
     */
    @Test
    public void testConvertirPalabraANumero2() {
        VistaUsuario vista = new VistaUsuario();
        ControladorUsuario controlador = new ControladorUsuario(vista, null, null);
        String resultado = controlador.convertirPalabraANumero("cero cinco nueve");
        assertEquals("059", resultado, "Fallo en el test2");
    }

    /**
     * Test3 del método convertirPalabraANumero (solo texto), de la clase ControladorUsuario.
     */
    @Test
    public void testConvertirPalabraANumero3() {
        VistaUsuario vista = new VistaUsuario();
        ControladorUsuario controlador = new ControladorUsuario(vista, null, null);
        String resultado = controlador.convertirPalabraANumero("hola mundo");
        assertEquals("hola mundo", resultado, "Fallo en el test3");
    }

    /**
     * Test4 del método convertirPalabraANumero (texto y numeros), de la clase ControladorUsuario.
     */
    @Test
    public void testConvertirPalabraANumero4() {
        VistaUsuario vista = new VistaUsuario();
        ControladorUsuario controlador = new ControladorUsuario(vista, null, null);
        String resultado = controlador.convertirPalabraANumero("uno perro dos");
        assertEquals("uno perro dos", resultado, "Fallo en el test4");
    }

}
