package com.example.controls.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.example.controls.dao.services.ConexionService;
import com.example.controls.dao.services.EstacionService;
import com.example.controls.tda.Graph.*;
import com.example.models.Conexion;
import com.example.models.Estacion;
import com.google.gson.Gson;

public class GraphDao {
    private GraphLabelNotDirect graph;

    public GraphDao() {
        this.graph = new GraphLabelNotDirect();
    }

    public GraphLabelNotDirect getGraph() throws Exception {
        Gson gson = new Gson();
        this.graph = gson.fromJson(this.readGraph(), GraphLabelNotDirect.class);
        if (this.graph == null) {
            this.graph = new GraphLabelNotDirect();
        }
        return graph;
    }

    public void setGraph(GraphLabelNotDirect graph) {
        this.graph = graph;
    }

    private void addEstaciones() throws Exception {
        EstacionService cs = new EstacionService();
        this.graph = new GraphLabelNotDirect(cs.listAll().getSize(), Estacion.class);
        for (Object obj : cs.listAll().toArray()) {
            Estacion Estacion = (Estacion) obj;
            this.graph.labelVertex(Estacion.getId(), Estacion);
        }
    }

    private void addConexiones() throws Exception {
        ConexionService crs = new ConexionService();
        EstacionService cs = new EstacionService();
        for (Object obj : crs.listAll().toArray()) {
            Estacion estacionOrigen = cs.get(((Conexion) obj).getEstacionOrigen());
            Estacion estacionDestino = cs.get(((Conexion) obj).getEstacionDestino());
            Double peso = ((Conexion) obj).getDistancia();
            this.graph.addEdgeLabel(estacionOrigen, estacionDestino, peso.floatValue());
        }
    }

    public void createGraph() throws Exception {
        this.addEstaciones();
        this.addConexiones();
        this.saveGraph();
    }
    

    public String toJson() {
        EstacionService cs = new EstacionService();
        ConexionService crs = new ConexionService();

        HashMap<String, Object> grafo = new HashMap<String, Object>();

        Estacion[] Estaciones = (Estacion[]) cs.listAll().toArray();
        HashMap<String, Object>[] nodes = new HashMap[Estaciones.length];
        for (int i = 0; i < Estaciones.length; i++) {
            HashMap<String, Object> node = new HashMap<String, Object>();
            node.put("id", Estaciones[i].getId());
            node.put("nombre", Estaciones[i].getNombre());
            node.put("lat", Estaciones[i].getLatitud());
            node.put("long", Estaciones[i].getLongitud());
            nodes[i] = node;
        }
        grafo.put("nodes", nodes);
        
        Conexion[] Conexions = (Conexion[]) crs.listAll().toArray();
        HashMap<String, Number>[] edges = new HashMap[Conexions.length];
        for (int i = 0; i < Conexions.length; i++) {
            HashMap<String, Number> edge = new HashMap<String, Number>();
            edge.put("from", Conexions[i].getEstacionOrigen());
            edge.put("to", Conexions[i].getEstacionDestino());
            edge.put("weight", Conexions[i].getDistancia());
            edges[i] = edge;
        }
        grafo.put("edges", edges);
        
        String json = new Gson().toJson(grafo);
        //System.out.println(json);
        return json;
    }

    public void saveGraph () throws Exception {
        try {
            FileWriter file = new FileWriter("media/Graph" + ".json");
            file.write(this.toJson());
            file.flush();
            file.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public String readGraph() throws Exception {
        File file = new File("media/Graph" + ".json");
        StringBuilder sb = new StringBuilder();
        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                sb.append(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            // TODO: handle exception
        }
        return sb.toString();
    }

    public JSONObject graphJson() throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(this.readGraph());
        return json;
    }
}