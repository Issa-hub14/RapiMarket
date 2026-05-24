/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo;

/**
 * Interfaz que define las operaciones principales del modelo utilizadas en la aplicación
 * 
 * @author isabe
 */
import java.util.List;

public interface IModelo {

    List<Producto> buscarProductos(String termino);

    List<Producto> obtenerCatalogo();

    Carrito obtenerCarrito();

    List<String> obtenerListaDeMercado();

    void agregarAListaMercado(String item);

    void eliminarDeListaMercado(String item);

    Producto buscarProductoPorNombre(String nombre);
}
