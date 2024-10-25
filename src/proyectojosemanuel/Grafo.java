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

    public ListaEstaciones[] vertices;
    public int maxVertices;
    int numVertices;
    public int t;
    Validaciones validaciones;

    public Grafo(int maxVertices) {
        validaciones = new Validaciones();
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
                break;
            }
        }
    }

    public void enlazar(String origen, String destino) {
        if (this.buscar(origen) != null && this.buscar(destino) != null && this.buscar(destino).buscar(origen, this.validaciones.buscarTransferencias(this)) == null) {
            this.buscar(origen).insertar(this.buscar(destino).pEstacion);
            this.buscar(destino).insertar(this.buscar(origen).pEstacion);
            System.out.println("ARISTA CREADA\n                                             -> " + this.buscar(origen).pEstacion.nombre + " / / / / " + this.buscar(destino).pEstacion.nombre);
            System.out.println(this.buscar(destino).buscar(origen, this.validaciones.buscarTransferencias(this)) == null);

        } else {
            if (this.buscar(origen) != null && this.buscar(destino) != null && this.buscar(destino).buscar(origen, this.validaciones.buscarTransferencias(this)) != null) {
                System.out.println(origen + "%%%%%%" + destino + "           " + this.buscar(destino).buscar(origen, this.validaciones.buscarTransferencias(this)) == null);

            } else {
                System.out.println(this.buscar(origen) + " -- " + this.buscar(destino));
                System.out.println(origen + " -- " + destino);

            }
        }
    }

    public ListaEstaciones buscar(String estacion) {
        estacion = this.validaciones.validarString(estacion);
        estacion = estacion.split("---")[0];
        String[] transferencias = this.validaciones.buscarTransferencias(this);
        if (this.validaciones.esTransferencia(estacion, transferencias)) {
            for (int i = 0; i < this.maxVertices; i++) {
                if (this.vertices[i].pEstacion != null) {

                    String vertice = this.validaciones.validarString(this.vertices[i].pEstacion.nombre);
                    if (vertice.contains(estacion)) {
                        return this.vertices[i];
                    }
                }
            }
        } else {
            for (int i = 0; i < this.maxVertices; i++) {
                if (this.vertices[i].pEstacion != null) {

                    String vertice = this.validaciones.validarString(this.vertices[i].pEstacion.nombre);
                    if (vertice.equals(estacion)) {
                        return this.vertices[i];
                    }
                }
            }
        }
        return null;
    }

    public int indiceVertice(String estacion) {
        estacion = this.validaciones.validarString(estacion);
        String[] transferencias = this.validaciones.buscarTransferencias(this);

        if (this.validaciones.esTransferencia(estacion, transferencias)) {
            for (int i = 0; i < this.maxVertices; i++) {
                if (this.vertices[i].pEstacion != null) {
                    String vertice = this.validaciones.validarString(this.vertices[i].pEstacion.nombre);
                    if (vertice.contains(estacion)) {
                        return i;
                    }
                }
            }
        } else {
            for (int i = 0; i < this.maxVertices; i++) {
                if (this.vertices[i].pEstacion != null) {
                    String vertice = this.validaciones.validarString(this.vertices[i].pEstacion.nombre);
                    if (vertice.equals(estacion)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    //procedimiento recursivo
    public String[] recorrerProfundidad(int v, boolean[] visitados, int aux, String[] alcance) {
//se marca el vértice v como visitado
        visitados[v] = true;
//el tratamiento del vértice consiste únicamente en imprimirlo en pantalla
        alcance[v] = this.vertices[v].pEstacion.nombre;
//        System.out.println(v);

        if (aux != t) {
            String[] transferencias = this.validaciones.buscarTransferencias(this);
//se examinan los vértices adyacentes a v para continuar el recorrido
            for (int i = 0; i < this.maxVertices; i++) {
                if ((v != i) && (!visitados[i]) && this.vertices[v].pEstacion != null && this.vertices[i].buscar(this.vertices[v].pEstacion.nombre, transferencias) != null) {
                    alcance = recorrerProfundidad(i, visitados, aux + 1, alcance);
                }
            }
        }
        return alcance;
    }
//procedimiento no recursivo

    public String[] profundidad(String estacion) {
        estacion = this.validaciones.validarString(estacion);
        boolean visitados[] = new boolean[this.maxVertices];
        String[] alcance = new String[this.maxVertices];
        for (int i = 0; i < this.maxVertices; i++) //inicializar vector con campos false
        {
            alcance[i] = "";
            visitados[i] = false;
        }
        int i = this.indiceVertice(estacion);
        return recorrerProfundidad(i, visitados, 0, alcance);

    }

    public String[] amplitud(String estacion) {
        estacion = this.validaciones.validarString(estacion);
        String[] alcance = new String[this.maxVertices]; // Arreglo para almacenar el alcance
        ColaEstaciones cola = new ColaEstaciones();
        boolean visitados[] = new boolean[this.maxVertices];
        int v = -1; // vértice actual

        // Se inicializa el vector visitados [] a false
        for (int i = 0; i < this.maxVertices; i++) {
            visitados[i] = false;
        }

        int index = 0;
        int i = this.indiceVertice(estacion);
        cola.encolar(this.vertices[i].pEstacion);
        visitados[i] = true;

        int nivel = 0; // Nivel actual
        int nodosEnNivel = 1; // Nodos en el nivel actual
        int nodosEnSiguienteNivel = 0; // Nodos en el siguiente nivel

        while (!cola.estaVacia() && nivel < t+1) { // Continuar mientras no esté vacía y no se haya alcanzado la distancia n
            // Procesar todos los nodos en el nivel actual
            for (int k = 0; k < nodosEnNivel; k++) {
                Estacion desencolado = cola.desencolar(); // Desencolar y tratar el vértice
                alcance[index] = desencolado.nombre;
                v = this.indiceVertice(desencolado.nombre);

                // Encolar los nodos adyacentes a v
                for (int j = 0; j < this.maxVertices; j++) {
                    if ((v != j) && this.vertices[j].pEstacion != null && this.hayArista(v, j) && (!visitados[j])) {
                        cola.encolar(this.vertices[j].pEstacion);
                        visitados[j] = true;
                        nodosEnSiguienteNivel++;
                    }
                }
                index++;
            }

            nivel++; // Aumentar el nivel
            nodosEnNivel = nodosEnSiguienteNivel; // Actualizar nodosEnNivel
            nodosEnSiguienteNivel = 0; // Reiniciar para el siguiente nivel
        }

        // Ajustar el tamaño del arreglo alcance si se visitaron menos nodos
        String[] alcanceAjustado = new String[index];
        System.arraycopy(alcance, 0, alcanceAjustado, 0, index);
        return alcanceAjustado;
    }

    public boolean hayArista(int i, int j) {
        return this.vertices[i].buscar(this.vertices[j].pEstacion.nombre, this.validaciones.buscarTransferencias(this)) != null;
    }

    public ListaEstaciones estacionesSinCubrir() {
        boolean visitados[] = new boolean[this.maxVertices];
        boolean cubiertos[] = new boolean[this.maxVertices];
        for (int i = 0; i < this.maxVertices; i++) {
            if (this.vertices[i].pEstacion != null && this.vertices[i].pEstacion.sucursal) {

                String[] recorrido = this.profundidad(this.vertices[i].pEstacion.nombre);
                for (int j = 0; j < recorrido.length; j++) {
                    int k = this.indiceVertice(recorrido[j]);
                    cubiertos[j] = true;
                }

            }

        }
        ListaEstaciones sinCubrir = new ListaEstaciones();
        for (int i = 0;
                i
                < this.maxVertices;
                i++) {
            if (!cubiertos[i] && this.vertices[i].pEstacion != null && !this.vertices[i].pEstacion.nombre.equals("")) {
                sinCubrir.insertar(this.vertices[i].pEstacion);
            }
        }
        NodoLista aux = sinCubrir.pFirst;
        ListaEstaciones sugeridos = new ListaEstaciones();
        while (sinCubrir.pFirst
                != null) {
            aux = sinCubrir.pFirst;
            aux.estacion.sucursal = true;
            sugeridos.insertar(aux.estacion);
            sinCubrir = this.estacionesSinCubrir();
        }
        NodoLista temp = sugeridos.pFirst;

        while (temp
                != null) {
            temp.estacion.sucursal = false;
            temp = temp.pNext;
        }
//        System.out.println(sugeridos.imprimir());
        for (int i = 0;
                i
                < this.maxVertices;
                i++) {
            if (this.vertices[i].pEstacion != null && this.vertices[i].pEstacion.sucursal) {
                System.out.println(this.vertices[i].pEstacion.nombre);
            }
        }
        return sugeridos;
    }

    public void imprimir() {
        for (int i = 0; i < this.maxVertices; i++) {
            if (this.vertices[i].pEstacion != null) {
                System.out.println(this.vertices[i].pEstacion.nombre);
            }
        }
    }
}
