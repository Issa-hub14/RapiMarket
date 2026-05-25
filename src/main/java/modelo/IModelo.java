/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo;

import java.util.List;

/**
 * Interfaz que define las operaciones principales del modelo utilizadas en la aplicación
 * 
 * @author isabe
 */
public interface IModelo {

    List<Producto> buscarProductos(String termino);

    List<Producto> obtenerCatalogo();

    Carrito obtenerCarrito();

    List<String> obtenerListaDeMercado();

    void agregarAListaMercado(String item);

    void eliminarDeListaMercado(String item);

    Producto buscarProductoPorNombre(String nombre);
}
