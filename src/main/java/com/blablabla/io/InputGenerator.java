package com.blablabla.io;

import com.blablabla.graph.Graph;
import com.blablabla.graph.GraphGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;
import java.io.File;
import java.util.Random;

public class InputGenerator {

    public static void main(String[] args) throws Exception {
        generateInput("src/main/resources/input/small.json", 5, 0.6);
        generateInput("src/main/resources/input/medium.json", 10, 0.5);
        generateInput("src/main/resources/input/large.json", 20, 0.4);
        generateInput("src/main/resources/input/extra.json", 30, 0.3);
        System.out.println("✅ Graph inputs generated in src/main/resources/input/");
    }

    private static void generateInput(String path, int vertices, double density) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        ArrayNode graphsArray = mapper.createArrayNode();

        ObjectNode graphNode = mapper.createObjectNode();
        graphNode.put("id", 1);

        // create nodes
        ArrayNode nodesArray = mapper.createArrayNode();
        for (int i = 0; i < vertices; i++) {
            nodesArray.add("V" + i);
        }
        graphNode.set("nodes", nodesArray);

        // create random edges
        ArrayNode edgesArray = mapper.createArrayNode();
        Random rnd = new Random();
        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                if (rnd.nextDouble() < density) {
                    ObjectNode edge = mapper.createObjectNode();
                    edge.put("from", "V" + i);
                    edge.put("to", "V" + j);
                    edge.put("weight", 1 + rnd.nextInt(20));
                    edgesArray.add(edge);
                }
            }
        }

        graphNode.set("edges", edgesArray);
        graphsArray.add(graphNode);
        root.set("graphs", graphsArray);

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), root);
    }
}