package com.blablabla.graph;

import java.util.Random;

public class GraphGenerator {

    public static Graph randomGraph(int vertices, double density) {
        Graph g = new Graph(vertices);
        for (int i = 0; i < vertices; i++) {
            g.setLabel(i, "V" + i);
        }
        Random rnd = new Random();
        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                if (rnd.nextDouble() < density) {
                    double w = 1 + rnd.nextInt(100);
                    g.addEdge(i, j, w);
                }
            }
        }
        return g;
    }
}