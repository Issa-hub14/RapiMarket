/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package vista;

/**
 * Interfaz que define las operaciones básicas que deben implementar las vistas de la aplicación
 * 
 * @author isabe
 */
public interface IVista {

    void actualizarEstado(String mensaje);

    void mostrarError(String error);

    void setVisible(boolean visible);
}
