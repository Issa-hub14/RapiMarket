/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package util;

/**
 * Interfaz que define las operaciones básicas para la reproducción del a voz en la aplicación
 * 
 * @author isabe
 */
public interface IReproducible {

    void hablar(String texto);

    void detener();

    void setActivo(boolean activo);

    boolean isActivo();
}
