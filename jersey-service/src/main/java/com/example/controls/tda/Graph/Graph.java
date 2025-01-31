package com.example.controls.tda.Graph;
import com.example.controls.tda.list.*;

public abstract class Graph {
    public abstract Integer nroVertex();
    public abstract Integer nroEdges();
    public abstract Boolean isEdge(Integer v1, Integer v2)throws Exception;
    public abstract Float weighEdge(Integer v1, Integer v2) throws Exception;
    public abstract void addEdge(Integer v1, Integer v2) throws Exception;
    public abstract void addEdge(Integer v1, Integer v2, Float w) throws Exception;
    public abstract LinkedList<Adyacencia> adjacents(Integer v);

    @Override
    public String toString(){
        String grafo = "";
        try {
            for (int i = 1; i < this.nroVertex(); i++) {
                grafo += "v" + i + "\n";
                LinkedList<Adyacencia> listAdj = this.adjacents(i);
                if (!listAdj.isEmpty()) {
                    Adyacencia[] ady = listAdj.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        grafo += "ady = v " + ady[j].getDestino() + " peso " + ady[j].getWeight() + "\n";
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return grafo;
    }
}
