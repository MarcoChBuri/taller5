package com.example.controls.tda.Graph;


import com.example.controls.tda.list.LinkedList;

import java.io.FileWriter;

import com.example.controls.exeception.*;

public class GraphDirect extends Graph{
    private Integer nroVertex;
    private Integer nroEdges;
    private LinkedList<Adyacencia> listAdy [];

    public GraphDirect(Integer nroVertex){
        this.nroVertex = nroVertex;
        this.nroEdges = 0;
        this.listAdy = new LinkedList [nroVertex + 1];
        for (int i = 1; i <= nroVertex; i++) {
            this.listAdy[i] = new LinkedList<>();
        }
    }

    @Override
    public Integer nroVertex() {
        return this.nroVertex;
    }

    @Override
    public Integer nroEdges() {
        return this.nroEdges;
    }

    @Override
    public Boolean isEdge(Integer v1, Integer v2) throws Exception{
        Boolean band = false;
        if (v1.intValue() <= nroVertex && v2.intValue() <= nroVertex) {
            LinkedList<Adyacencia> adyList= this.listAdy[v1];
            if (!adyList.isEmpty()) {
                Adyacencia[] matrix = adyList.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if (aux.getDestino().intValue() == v2.intValue()) {
                        band = true;
                        break;
                    }
                }
            }
        }else{
            throw new OverFlowException("los vertices estan fuera de raango");
        }
        return band;
    }

    @Override
    public Float weighEdge(Integer v1, Integer v2) throws Exception {
        Float weight = 0.0f;
        if (isEdge(v1, v2)) {
            LinkedList<Adyacencia> listAdy = this.listAdy[v1];
            if (!listAdy.isEmpty()) {
                Adyacencia[] matrix = listAdy.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if (aux.getDestino().intValue() == v2.intValue()) {
                        weight = aux.getWeight();
                        break;
                    }
                }
            }
        } else {
            throw new Exception("No existe arista");
        }
        return weight;
    }

    @Override
    public void addEdge(Integer v1, Integer v2) throws Exception{
        this.addEdge(v1, v2, Float.NaN);
    }

    @Override
    public void addEdge(Integer v1, Integer v2, Float w) throws Exception {
        if (v1.intValue() <= nroVertex && v2.intValue() <= nroVertex) {
            if (!isEdge(v1, v2)) {
                Adyacencia aux = new Adyacencia(v2, w);
                this.listAdy[v1].add(aux);
                this.nroEdges++;
            } 
        } else{
            throw new OverFlowException("los vertices estan fuera de raango");

        }
    }

    @Override
    public LinkedList<Adyacencia> adjacents(Integer v){
        return this.listAdy[v];
    }

    public void setNroEdges(Integer nroEdges){
        this.nroEdges = nroEdges;
    }

    /*public void setListAdy(LinkedList<Adyacencia>[] listAdy){
        this.listAdy = listAdy;
    }*/

    public LinkedList<Adyacencia>[] getListAdy(){
        return this.listAdy;
    }
    
    public Integer[][] FloydWarshallPath() throws Exception {
        Integer[][] path = new Integer[nroVertex + 1][nroVertex + 1];
        Float[][] adjMatrix = createAdjmatrix();
        try {
            for (int k = 1; k <= nroVertex; k++) {
                for (int j = 1; j <= nroVertex; j++) {
                    path[k][j] = j;
                }
            }
            for (int k = 1; k <= nroVertex; k++) {
                for (int i = 1; i <= nroVertex; i++) {
                    for (int j = 1; j <= nroVertex; j++) {
                        if (adjMatrix[i][k] + adjMatrix[k][j] < adjMatrix[i][j]) {
                            path[i][j] = path[i][k];
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return path;
    }
    private Float[][] createAdjmatrix() throws Exception {
        Float[][] matrix = new Float[nroVertex + 1][nroVertex + 1];
        try {
            for (int i = 1; i <= nroVertex; i++) {
                for (int j = 1; j <= nroVertex; j++) {
                    if (i == j) {
                        matrix[i][j] = 0.0f;
                    } else {
                        if (isEdge(i, j)) {
                            matrix[i][j] = weighEdge(i, j);
                        } else {
                            matrix[i][j] = Float.POSITIVE_INFINITY;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return matrix;
    }
    public Float[][] FloydWarshall() throws Exception {
        Float[][] adjMatrix = createAdjmatrix();
        try {
            for (int k = 1; k <= nroVertex; k++) {
                for (int i = 1; i <= nroVertex; i++) {
                    for (int j = 1; j <= nroVertex; j++) {
                        if (adjMatrix[i][k] + adjMatrix[k][j] < adjMatrix[i][j]) {
                            adjMatrix[i][j] = adjMatrix[i][k] + adjMatrix[k][j]; // Minimo entre el peso actual y el de
                                                                                 // la suma de los vertices
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return adjMatrix;
    }
    public Integer[] minPathFloyd(Integer v1, Integer v2) throws Exception {
        Integer[] path = new Integer[nroVertex + 1];
        Integer[][] pathMatrix = FloydWarshallPath();
        try {
            path[0] = v1;
            int i = 1;
            while (v1 != v2) {
                v1 = pathMatrix[v1][v2];
                path[i] = v1;
                i++;
            }
        } catch (Exception e) {
        }
        return path;
    }

    public Float minWeightFloyd(Integer v1, Integer v2) throws Exception {
        Float[][] adjMatrix = FloydWarshall();
        Float weight = 0.0f;
        try {
            weight = adjMatrix[v1][v2];
        } catch (Exception e) {
        }
        return weight;
    }

    public Integer[] BellmanFord(Integer origen, Integer destino) {
        Float[] dist = new Float[nroVertex + 1];
        Integer[] predecesores = new Integer[nroVertex + 1];
        Integer[] path = new Integer[nroVertex + 1];
        try {
            for (int i = 1; i <= nroVertex; i++) {
                dist[i] = Float.POSITIVE_INFINITY;
                predecesores[i] = null;
            }
            dist[origen] = 0.0f;
            for (int i = 1; i < nroVertex; i++) {
                for (int j = 1; j <= nroVertex; j++) {
                    Adyacencia[] listAdy = this.listAdy[j].toArray();
                    for (Adyacencia ady : listAdy) {
                        Integer dest = ady.getDestino();
                        Float weight = ady.getWeight();
                        if (dist[j] != Float.POSITIVE_INFINITY && dist[j] + weight < dist[dest]) {
                            dist[dest] = dist[j] + weight;
                            predecesores[dest] = j;
                        }
                    }
                }
            }

            if (dist[destino] == Float.POSITIVE_INFINITY) {
                throw new Exception("No existe camino");
            }

            path = recreatePath(predecesores, origen, destino);
        } catch (Exception e) {
        }
        return path;
    }

    private Integer[] recreatePath(Integer[] predecesores, Integer origen, Integer destino) {
        Integer[] path = new Integer[nroVertex + 1];
        int i = 0;
        Integer aux = destino;
        while (aux != null) {
            path[i] = aux;
            aux = predecesores[aux];
            i++;
        }

        Integer[] pathAux = new Integer[i];
        for (int j = 0; j < i; j++) {
            pathAux[j] = path[i - j - 1];
        }
        return pathAux;
    }
}