/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectojosemanuel;

/**
 *
 * @author JM
 */
public class Estacion {
    String nombre;
    boolean sucursal;
    boolean visitado;
    
    public Estacion(String name){
        this.nombre = name;
        this.sucursal = this.visitado = false;
    }
}
