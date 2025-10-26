package com.blablabla.benchmark;

import com.blablabla.algorithms.*;
import com.blablabla.graph.Graph;
import com.blablabla.util.JSONHandler;
import com.blablabla.util.PerformanceTracker;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class BenchmarkRunner {

    private static final String INPUT_PATH = "src/main/resources/input/";
    private static final String OUTPUT_RESULTS_PATH = "src/main/resources/output/results/";
    private static final String OUTPUT_BENCHMARK_PATH = "src/main/resources/output/benchmarks/";

    public static void main(String[] args) throws Exception {
        runAll();
    }

    public static void runAll() throws Exception {
        String[] inputFiles = {"small.json", "medium.json", "large.json", "extra.json"};
        JSONHandler jsonHandler = new JSONHandler();

        System.out.println("=== MST Benchmark Console Test ===");
        System.out.println("Comparing Prim and Kruskal performance");
        System.out.println("--------------------------------------");
        System.out.printf("%-12s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s%n",
                "Dataset", "Prim(ms)", "Kruskal(ms)", "PrimCost", "KruskalCost", "PrimOps", "KruskalOps");
        System.out.println("---------------------------------------------------------------------------------------------");

        for (String file : inputFiles) {
            File inputFile = new File(INPUT_PATH + file);
            if (!inputFile.exists()) {
                System.out.printf("%-12s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s%n",
                        file, "-", "-", "-", "-", "-", "-");
                continue;
            }

            List<JSONHandler.GraphWrapper> graphs = jsonHandler.readGraphs(inputFile);
            List<JSONHandler.ResultBundle> jsonResults = new ArrayList<>();
            List<BenchmarkResult> csvResults = new ArrayList<>();

            for (JSONHandler.GraphWrapper gw : graphs) {
                Graph g = gw.graph;

                // Prim
                PerformanceTracker primTracker = new PerformanceTracker();
                MSTAlgorithm prim = new PrimMST();
                MSTResult primResult = prim.run(g, primTracker);

                // Kruskal
                PerformanceTracker kruskalTracker = new PerformanceTracker();
                MSTAlgorithm kruskal = new KruskalMST();
                MSTResult kruskalResult = kruskal.run(g, kruskalTracker);

                jsonResults.add(new JSONHandler.ResultBundle(
                        gw.id, g.vertexCount(), (int) g.edgeCount(), primResult, kruskalResult));

                csvResults.add(new BenchmarkResult(
                        file.replace(".json", ""),
                        primResult.executionTimeMs,
                        kruskalResult.executionTimeMs,
                        primTracker.getTotalOperations(),
                        kruskalTracker.getTotalOperations(),
                        primResult.totalCost,
                        kruskalResult.totalCost
                ));

                // ✅ консольный вывод
                System.out.printf("%-12s | %-10.2f | %-10.2f | %-10.2f | %-10.2f | %-10d | %-10d%n",
                        file.replace(".json", ""),
                        primResult.executionTimeMs,
                        kruskalResult.executionTimeMs,
                        primResult.totalCost,
                        kruskalResult.totalCost,
                        primTracker.getTotalOperations(),
                        kruskalTracker.getTotalOperations());
            }

            // write JSON and CSV
            File jsonOut = new File(OUTPUT_RESULTS_PATH + file.replace(".json", "_results.json"));
            jsonHandler.writeResults(jsonOut, jsonResults);

            File csvOut = new File(OUTPUT_BENCHMARK_PATH + file.replace(".json", "_benchmark.csv"));
            writeCSV(csvOut, csvResults);
        }

        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("✅ Benchmark completed successfully.");
    }

    private static void writeCSV(File file, List<BenchmarkResult> results) throws Exception {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Graph,PrimCost,KruskalCost,PrimTime,KruskalTime,PrimOps,KruskalOps\n");
            for (BenchmarkResult r : results) {
                writer.write(String.format("%s,%.2f,%.2f,%.3f,%.3f,%d,%d\n",
                        r.datasetName, r.primCost, r.kruskalCost,
                        r.primTime, r.kruskalTime, r.primOps, r.kruskalOps));
            }
        }
    }
}