package com.example.controls.tda.Graph;

public class GraphNotDirect extends GraphDirect{
    public GraphNotDirect(Integer nroVertex) {
        super(nroVertex);
    }

    @Override
    public void addEdge(Integer v1, Integer v2, Float w) throws Exception {
        if (v1.intValue() <= nroVertex() && v2.intValue() <= nroVertex()) {
            if (!isEdge(v1, v2)) {
                Adyacencia origin = new Adyacencia(v2, w);
                this.getListAdy()[v1].add(origin);

                Adyacencia destination = new Adyacencia(v1, w);
                this.getListAdy()[v2].add(destination);

                this.setNroEdges(this.nroEdges() + 1);
            } 
        } 
    }
}