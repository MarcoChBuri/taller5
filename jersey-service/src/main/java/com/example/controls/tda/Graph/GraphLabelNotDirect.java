package com.example.controls.tda.Graph;
import com.example.controls.exeception.*;


public class GraphLabelNotDirect<E> extends GraphLabelDirect<E>{
    public GraphLabelNotDirect(Integer nroVertex, Class<E> clazz) {
        super(nroVertex, clazz);
    }

    public GraphLabelNotDirect(){
        super();
    }

    @Override
    public void addEdgeLabel(E v1, E v2, Float w) throws Exception {
        if (islabelsGraph()) {
            addEdge(getVertexLabel(v1), getVertexLabel(v2), w);
            addEdge(getVertexLabel(v2), getVertexLabel(v1), w);
        } else {
            throw new LabelException("No se han asignado etiquetas a los vertices");
        }
    }

    @Override
    public void addEdgeLabel(E v1, E v2) throws Exception {
        if (islabelsGraph()) {
            addEdge(getVertexLabel(v1), getVertexLabel(v2), 0.0f);
            addEdge(getVertexLabel(v2), getVertexLabel(v1), 0.0f);
        } else {
            throw new LabelException("No se han asignado etiquetas a los vertices");
        }
    }
    
}