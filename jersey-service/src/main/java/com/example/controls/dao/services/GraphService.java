package com.example.controls.dao.services;

import com.example.controls.dao.GraphDao;
import com.example.controls.tda.Graph.GraphLabelDirect;
import com.example.controls.tda.Graph.GraphLabelNotDirect;
import com.example.models.Estacion;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GraphService {
    GraphLabelNotDirect graph;
    EstacionService cs = new EstacionService();
 
    
    public GraphService(){
        this.graph = new GraphLabelNotDirect();
    }
    

    public GraphLabelNotDirect getGraph() {
        return graph;
    }

    public void setGraph(GraphLabelNotDirect graph) {
        this.graph = graph;
    }

    public void saveEstacionGraph(Estacion origen, Estacion destino, Double peso) throws Exception {
        this.graph = new GraphLabelNotDirect(cs.listAll().getSize(), Estacion.class);
        this.graph.labelVertex(origen.getId(), origen);
        this.graph.labelVertex(destino.getId(), destino);
        this.graph.addEmptyLabels(new Estacion());
        this.graph.addEdgeLabel(origen, destino, peso.floatValue());
        this.graph.saveGraph();
    }
    public void saveGraph() throws Exception {
        GraphDao dao = new GraphDao();
        dao.setGraph(this.graph); // Asegurarse de que el DAO tenga la versión actualizada del grafo
        dao.saveGraph();
    }
    public JSONObject graphJson() throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(this.graph.readGraph());
        return json;
    }
    public Integer[] getMinPath(String algoritmo, Integer origen, Integer destino) throws Exception {
        switch (algoritmo) {
            case "bellman":
                //return this.getGraph().minPathBellman(origen, destino);
            case "floyd":
                return this.getGraph().minPathFloyd(origen, destino);
            default:
                return null;
        }
    }

    public Float getMinWeight(String algoritmo, Integer origen, Integer destino) throws Exception {
        switch (algoritmo) {
            case "bellman":
                //return this.getGraph().minPathBellman(origen, destino);
            case "floyd":
                return this.getGraph().minWeightFloyd(origen, destino);
            default:
                return null;
        }
    }


}