/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author isabe
 */
import java.util.*;

public class ModeloTienda implements IModelo {

    private final List<Producto> catalogoProductos;
    private final Carrito carrito;
    private final List<String> listaDeMercado;
    private Persona clienteActual;

    public ModeloTienda() {
        this.carrito = new Carrito();
        this.listaDeMercado = new ArrayList<>();
        this.catalogoProductos = new ArrayList<>();
        this.clienteActual = new ClienteInvitado("Invitado");
        cargarProductos();
    }

    public Persona getClienteActual() {
        return clienteActual;
    }

    public void setClienteActual(Persona clienteActual) {
        if (clienteActual != null) {
            this.clienteActual = clienteActual;
        }
    }

    private void cargarProductos() {

        agregarProductoCatalogo("Leche Entera 1L", 3500, "Lácteos", "Pasillo 1, derecha");
        agregarProductoCatalogo("Aceite de oliva 500ML", 8000, "Aderezos", "Pasillo 2, izquierda");
        agregarProductoCatalogo("Yogurt Fresa 200g", 2800, "Lácteos", "Pasillo 1, izquierda");
        agregarProductoCatalogo("Queso Campesino 500g", 8500, "Lácteos", "Pasillo 1, izquierda");

        agregarProductoCatalogo("Arroz 2kg", 6500, "Granos", "Pasillo 2, izquierda");
        agregarProductoCatalogo("Frijol Bolo 500g", 4800, "Granos", "Pasillo 2, derecha");
        agregarProductoCatalogo("Lenteja 500g", 3900, "Granos", "Pasillo 2, derecha");

        agregarProductoCatalogo("Pan Tajado", 5200, "Panadería", "Pasillo 3, derecha");
        agregarProductoCatalogo("Arepa x6", 4500, "Panadería", "Pasillo 3, derecha");

        agregarProductoCatalogo("Pechuga de Pollo 1kg", 12000, "Carnes", "Pasillo 4, refrigerados");
        agregarProductoCatalogo("Carne Molida 500g", 19500, "Carnes", "Pasillo 4, refrigerados");

        agregarProductoCatalogo("Jabón Azul 500g", 3200, "Aseo", "Pasillo 5, izquierda");
        agregarProductoCatalogo("Detergente 1kg", 15000, "Aseo", "Pasillo 5, izquierda");
        agregarProductoCatalogo("Shampoo Savital 400ml", 18500, "Aseo", "Pasillo 5, derecha");
    }

    private void agregarProductoCatalogo(String nom, double pre, String cat, String pasillo) {
        catalogoProductos.add(new Producto(nom, pre, cat, pasillo));
    }

    @Override
    public List<Producto> buscarProductos(String termino) {
        List<Producto> resultado = new ArrayList<>();

        if (termino == null || termino.isBlank()) {
            return new ArrayList<>(catalogoProductos);
        }

        String t = termino.toLowerCase().trim();

        for (Producto p : catalogoProductos) {
            if (p.getNombre().toLowerCase().contains(t)
                    || p.getCategoria().toLowerCase().contains(t)) {
                resultado.add(p);
            }
        }

        return resultado;
    }

    @Override
    public Producto buscarProductoPorNombre(String nombre) {
        for (Producto p : catalogoProductos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Carrito obtenerCarrito() {
        return carrito;
    }

    @Override
    public List<Producto> obtenerCatalogo() {
        return Collections.unmodifiableList(catalogoProductos);
    }

    @Override
    public List<String> obtenerListaDeMercado() {
        return listaDeMercado;
    }

    @Override
    public void agregarAListaMercado(String item) {
        if (item != null && !item.isBlank() && !listaDeMercado.contains(item)) {
            listaDeMercado.add(item.trim());
        }
    }

    @Override
    public void eliminarDeListaMercado(String item) {
        listaDeMercado.remove(item);
    }
}
