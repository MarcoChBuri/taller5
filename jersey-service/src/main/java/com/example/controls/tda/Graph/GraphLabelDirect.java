package com.example.controls.tda.Graph;
 import java.lang.reflect.Array;
 import java.util.HashMap;


 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.example.controls.exeception.LabelException;
import com.example.controls.tda.list.*;
import com.example.models.Estacion;

public class GraphLabelDirect<E> extends GraphDirect {
    protected E labels [];
    protected HashMap<E, Integer> dicVertext;
    private Class<E> clazz;

    public GraphLabelDirect(Integer nroVertex, Class<E> clazz) {
        super(nroVertex);
        this.clazz = clazz;
        this.labels = (E[]) Array.newInstance(clazz, nroVertex + 1);
        this.dicVertext = new HashMap<>();
    }

    public GraphLabelDirect(){
        super(0);
    }

    public Boolean isEdgeLabel(E v1, E v2) throws Exception {
        if (islabelsGraph()) {
            return isEdge(getVertexLabel(v1), getVertexLabel(v2));
        } else {
            throw new LabelException("No se han asignado etiquetas a los vertices");
        }
    }

    public void addEdgeLabel(E v1, E v2, Float w) throws Exception {
        if (islabelsGraph()) {
            addEdge(getVertexLabel(v1), getVertexLabel(v2), w);
        } else {
            throw new LabelException("No se han asignado etiquetas a los vertices");
        }
    }

    public void addEdgeLabel(E v1, E v2) throws Exception {
        if (islabelsGraph()) {
            addEdge(getVertexLabel(v1), getVertexLabel(v2), 0.0f);
        } else {
            throw new LabelException("No se han asignado etiquetas a los vertices");
        }
    }

    public LinkedList<Adyacencia> adjacentsLabel(E v) throws Exception {
        if (islabelsGraph()) {
            return adjacents(getVertexLabel(v));
        } else {
            throw new LabelException("No se han asignado etiquetas a los vertices");
        }
    }

    public void labelVertex (Integer vertex, E label) {
        labels[vertex] = label;
        dicVertext.put(label, vertex);
    }

    public Boolean islabelsGraph () {
        Boolean band = true;
        for (int i = 1; i < labels.length; i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }
        }
        return band;
    }

    public Integer getVertexLabel (E label) {
        return dicVertext.get(label);
    }

    public E getLabel (Integer vertex) {
        return labels[vertex];
    }

    public void addEmptyLabels(E label) {
        for (int i = 1; i < labels.length; i++) {
            if (labels[i] == null) {
                labels[i] = label;
            }
        }
    }

    public String toJson() {
        String grafo = "{";
        try {
            grafo += "\"nodes\": [";
            for (int i = 1; i < this.nroVertex()+1; i++) {
                Estacion estacion = (Estacion) getLabel(i);
                grafo += String.format("{\"id\": %d, \"nombre\": \"%s\",", estacion.getId(), estacion.getNombre());
                grafo += "\"lat\":" + "\"" + estacion.getLatitud() + "\"" + ",";
                grafo += "\"long\":" + "\"" + estacion.getLongitud() + "\"" + "}";
                if (i < this.nroVertex()) {
                    grafo += ",";   
                }
            }
            grafo += "],";
            grafo += "\"edges\":[";
            for (int i = 1; i < this.nroVertex()+1; i++) {
                LinkedList<Adyacencia> listAdj = adjacents(i);
                if (!listAdj.isEmpty()) {
                    Adyacencia[] ady = listAdj.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        grafo += String.format("{\"from\": %d, \"to\": %d, \"weight\": \"%f\"}", i, ady[j].getDestino(), ady[j].getWeight());
                        if (i < this.nroVertex() || j < ady.length-1) {
                            grafo += ",";
                        }
                    }
                }
            }
            grafo += "]";
        } catch (Exception e) {
            //TODO: handle exception
        }
        grafo += "}";
        return grafo;
    }
public void saveGraph() throws Exception {
    String json = this.toJson();
    
    if (json == null || json.isEmpty()) {
        throw new Exception("Error: No se pudo convertir el objeto a JSON");
    }
    
    try (FileWriter file = new FileWriter("media/Graph.json")) {
        file.write(json);
        file.flush();
        System.out.println(" Archivo guardado correctamente en media/Graph.json");
    } catch (IOException e) {
        System.err.println(" Error al guardar el archivo JSON: " + e.getMessage());
        throw e; // Relanza la excepción para que el llamador lo maneje
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
    @Override
    public String toString(){
        String grafo = "";
        try {
            for (int i = 1; i < this.nroVertex(); i++) {
                grafo += "v" + i+"["+getLabel(i).toString()+"]"+ "\n";
                LinkedList<Adyacencia> listAdj = this.adjacents(i);
                if (!listAdj.isEmpty()) {
                    Adyacencia[] ady = listAdj.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        grafo += "ady = v " + ady[j].getDestino() + " peso " + ady[j].getWeight() +"["+getLabel(ady[j].getDestino()).toString()+"]"+ "\n";
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return grafo;
    }
    public String drawGraph() {
        String grafo = "var nodes = new vis.DataSet([" + "\n";
        try{ 
            for (int i = 0; i < nroVertex(); i++) {
                grafo += "{ id: " + i + ", label: "  + "\"" + getLabel(i).toString() +  "\"" + "}," + "\n";
            }
            grafo += "]);" + "\n";
            grafo += "var edges = new vis.DataSet([" + "\n";

            for (int i = 0; i < nroVertex(); i++) {
                LinkedList<Adyacencia> lista = adjacents(i);
                if(!lista.isEmpty()){
                    Adyacencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "{ from: " + i + ", to: " + a.getDestino() +  "}," + "\n";
                    }
                }
            }
            grafo += "]);" + "\n";
            grafo += "var container = document.getElementById(\"mynetwork\");" + "\n";
            grafo += "var data = {" + "\n";
            grafo += "nodes: nodes," + "\n";
            grafo += "edges: edges," + "\n";
            grafo += "};" + "\n";
            grafo += "var options = {};" + "\n";
            grafo += "var network = new vis.Network(container, data, options);" + "\n";
            FileWriter file = new FileWriter("src/main/resources/graph.js");
            file.write(grafo);
            file.flush();
            file.close();


        } catch (Exception e) {
            grafo = "Error al imprimir el grafo";
        }
        return grafo;
    }
    
}