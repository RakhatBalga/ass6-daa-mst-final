package com.blablabla.cli;

import com.blablabla.benchmark.BenchmarkRunner;
import com.blablabla.algorithms.*;
import com.blablabla.util.JSONHandler;
import com.blablabla.util.PerformanceTracker;
import com.blablabla.graph.Graph;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {

    private static final String INPUT_PATH = "src/main/resources/input/";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Minimum Spanning Tree CLI ===");
        System.out.println("1 - Run all benchmarks");
        System.out.println("2 - Run single file");
        System.out.print("Choose option: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            BenchmarkRunner.runAll();
        } else if (choice == 2) {
            System.out.print("Enter file name (small.json / medium.json / ...): ");
            String name = sc.nextLine();
            runSingle(name);
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static void runSingle(String fileName) throws Exception {
        File input = new File(INPUT_PATH + fileName);
        if (!input.exists()) {
            System.out.println("File not found: " + input.getAbsolutePath());
            return;
        }

        JSONHandler handler = new JSONHandler();
        List<JSONHandler.GraphWrapper> graphs = handler.readGraphs(input);

        for (JSONHandler.GraphWrapper gw : graphs) {
            Graph g = gw.graph;
            System.out.printf("\nGraph %d: vertices=%d, edges=%d\n",
                    gw.id, g.vertexCount(), g.edgeCount());

            PerformanceTracker primTracker = new PerformanceTracker();
            MSTAlgorithm prim = new PrimMST();
            MSTResult primResult = prim.run(g, primTracker);

            PerformanceTracker krTracker = new PerformanceTracker();
            MSTAlgorithm kr = new KruskalMST();
            MSTResult krResult = kr.run(g, krTracker);

            System.out.println("\nPrim MST:");
            primResult.mstEdges.forEach(System.out::println);
            System.out.printf("Total cost: %.2f | Time: %.3f ms | Operations: %d\n",
                    primResult.totalCost, primResult.executionTimeMs, primTracker.getTotalOperations());

            System.out.println("\nKruskal MST:");
            krResult.mstEdges.forEach(System.out::println);
            System.out.printf("Total cost: %.2f | Time: %.3f ms | Operations: %d\n",
                    krResult.totalCost, krResult.executionTimeMs, krTracker.getTotalOperations());
        }
    }
}