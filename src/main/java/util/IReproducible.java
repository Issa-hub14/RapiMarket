/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package util;

/**
 *
 * @author isabe
 */
public interface IReproducible {

    void hablar(String texto);

    void detener();

    void setActivo(boolean activo);

    boolean isActivo();
}
