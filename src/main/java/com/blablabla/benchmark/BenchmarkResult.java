package com.blablabla.benchmark;

public class BenchmarkResult {
    public final String datasetName;
    public final double primTime;
    public final double kruskalTime;
    public final int primOps;
    public final int kruskalOps;
    public final double primCost;
    public final double kruskalCost;

    public BenchmarkResult(String datasetName,
                           double primTime, double kruskalTime,
                           int primOps, int kruskalOps,
                           double primCost, double kruskalCost) {
        this.datasetName = datasetName;
        this.primTime = primTime;
        this.kruskalTime = kruskalTime;
        this.primOps = primOps;
        this.kruskalOps = kruskalOps;
        this.primCost = primCost;
        this.kruskalCost = kruskalCost;
    }
}