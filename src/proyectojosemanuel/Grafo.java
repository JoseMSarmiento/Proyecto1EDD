/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectojosemanuel;



/**
 *
 * @author JM
 */
public class Grafo {

    ListaEstaciones[] vertices;
    int maxVertices;
    int numVertices;
    int t;

    public Grafo(int maxVertices) {
        this.vertices = new ListaEstaciones[maxVertices];
        for (int i = 0; i < maxVertices; i++) {
            this.vertices[i] = new ListaEstaciones();
        }
        this.maxVertices = maxVertices;
        this.numVertices = 0;
        t = 3;
    }

    public void insertar(String estacion) {
        for (int i = 0; i < this.maxVertices; i++) {
            if (this.vertices[i].pEstacion == null) {
                this.vertices[i].insertar(new Estacion(estacion));
            }
        }
    }

    public void enlazar(String origen, String destino) {
        if (this.buscar(origen) != null && this.buscar(destino) != null && this.buscar(destino).buscar(origen) == null) {        
            this.buscar(origen).insertar(this.buscar(destino).pEstacion);
            this.buscar(destino).insertar(this.buscar(origen).pEstacion);

        }
    }

    public ListaEstaciones buscar(String estacion) {
        for (int i = 0; i < this.maxVertices; i++) {
            if (this.vertices[i].pEstacion.nombre.equals(estacion)) {
                return this.vertices[i];
            }
        }
        return null;
    }
    
    
        //procedimiento recursivo
    public void recorrerProfundidad(int v, boolean[] visitados, int aux) {
//se marca el vértice v como visitado
        visitados[v] = true;
//el tratamiento del vértice consiste únicamente en imprimirlo en pantalla
        System.out.println(v);

        if (aux != t) {
//se examinan los vértices adyacentes a v para continuar el recorrido
            for (int i = 0; i < this.maxVertices; i++) {
                if ((v != i) && (!visitados[i]) && this.vertices[i].buscar(this.vertices[v].pEstacion.nombre) != null) {
                    recorrerProfundidad(i, visitados, aux + 1);
                }
            }
        }
    }
//procedimiento no recursivo

    public void profundidad() {
        boolean visitados[] = new boolean[this.maxVertices];

        for (int i = 0; i < this.maxVertices; i++) //inicializar vector con campos false
        {
            visitados[i] = false;
        }
        for (int i = 0; i < this.maxVertices; i++) { //Relanza el recorrido en cada
            if (!visitados[i]) //vértice visitado
            {
                recorrerProfundidad(i, visitados, 0);
            }
        }
    }

}
