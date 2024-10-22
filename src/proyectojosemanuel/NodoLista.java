/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectojosemanuel;

/**
 *
 * @author JM
 */
public class NodoLista {
    Estacion estacion;
    NodoLista pNext;
    NodoLista pPrev;
    
    public NodoLista(Estacion station){
        this.estacion = station;
        this.pNext =this.pPrev =  null;
    }
}
