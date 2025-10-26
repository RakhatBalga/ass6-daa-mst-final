package com.blablabla.algorithms;

import com.blablabla.graph.Edge;
import java.util.List;

public class MSTResult {
    public final List<Edge> mstEdges;
    public final double totalCost;
    public final long vertices;
    public final long edges;
    public final double executionTimeMs;
    public final int comparisons;
    public final int unions;
    public final int finds;
    public final int edgeProcessed;

    public MSTResult(List<Edge> mstEdges, double totalCost,
                     long vertices, long edges,
                     double executionTimeMs,
                     int comparisons, int unions, int finds, int edgeProcessed) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.vertices = vertices;
        this.edges = edges;
        this.executionTimeMs = executionTimeMs;
        this.comparisons = comparisons;
        this.unions = unions;
        this.finds = finds;
        this.edgeProcessed = edgeProcessed;
    }
}