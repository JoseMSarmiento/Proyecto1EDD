/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectojosemanuel;

import Interfaces.InterfazMenu;

/**
 *
 * @author JM
 */
public class ProyectoJoseManuel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FuncionesJSON funciones = new FuncionesJSON();
        
        InterfazMenu im = new InterfazMenu(funciones.leerArchivo("Caracas.json"));
    }
    
}
