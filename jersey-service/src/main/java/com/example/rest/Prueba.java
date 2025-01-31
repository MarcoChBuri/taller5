package com.example.rest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.json.simple.JSONObject;

import com.example.controls.tda.Graph.GraphLabelNotDirect;
import com.example.controls.dao.GraphDao;
import com.example.controls.dao.services.GraphService;

public class Prueba {

    public static void main(String[] args) throws Exception {
        // Crear el servicio del grafo
        GraphService graphService = new GraphService();
        GraphDao ps = new GraphDao();
        
        // Crear el grafo
        ps.createGraph();
        
        // Obtener el grafo creado
        GraphLabelNotDirect<String> graph = graphService.getGraph();

        // Guardar el grafo en un archivo JSON
        saveGraphToFile(graphService.graphJson(), "graph.json");
        
        // Mostrar el número de nodos en el grafo
        System.out.println("Número de nodos en el grafo: " + graph.nroVertex());

        // Medir tiempos de ejecución para los algoritmos de Floyd y Bellman-Ford
        measureExecutionTime(graphService, "floyd");
        measureExecutionTime(graphService, "bellman");
    }

    private static void saveGraphToFile(JSONObject graphJson, String fileName) {
        // Guardar el JSON del grafo en un archivo
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(graphJson.toJSONString());
            System.out.println("Grafo guardado en " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void measureExecutionTime(GraphService graphService, String algorithm) throws Exception {
        GraphLabelNotDirect<String> graph = graphService.getGraph();
        int numNodes = graph.nroVertex();  // Obtener el número de nodos en el grafo
        System.out.println("\nMedición de tiempos para el algoritmo " + algorithm);
        System.out.println("Tamaño | Tiempo (ms)");
        System.out.println("-------------------");

        for (int size = 1; size <= numNodes; size++) {
            Integer origen = 1, destino = size; // Usar nodos dentro del rango

            long startTime = System.nanoTime();
            
            // Ejecutar el algoritmo de camino más corto
            Integer[] path = graphService.getMinPath(algorithm, origen, destino);
            
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000; // Convertir a milisegundos

            System.out.printf("%6d | %6d ms%n", size, duration);
            System.out.println("Camino más corto de " + origen + " a " + destino + ": " + Arrays.toString(path));
        }
    }
}
