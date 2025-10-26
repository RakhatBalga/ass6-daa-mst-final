package com.blablabla.algorithms;

import com.blablabla.graph.Edge;
import com.blablabla.graph.Graph;
import com.blablabla.util.PerformanceTracker;
import java.util.*;

public class PrimMST implements MSTAlgorithm {

    @Override
    public MSTResult run(Graph graph, PerformanceTracker tracker) {
        tracker.startTimer();
        int n = graph.vertexCount();
        if (n == 0) {
            tracker.stopTimer();
            return new MSTResult(Collections.emptyList(), 0.0, 0, 0,
                    tracker.getElapsedTimeMillis(),
                    tracker.getComparisonCount(), tracker.getUnionOperations(),
                    tracker.getFindOperations(), tracker.getEdgeProcessingCount());
        }

        boolean[] inMST = new boolean[n];
        double[] key = new double[n];
        Edge[] parentEdge = new Edge[n];
        Arrays.fill(key, Double.POSITIVE_INFINITY);
        key[0] = 0.0;

        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingDouble(i -> key[i]));
        pq.add(0);

        while (!pq.isEmpty()) {
            int u = pq.poll();
            if (inMST[u]) continue;
            inMST[u] = true;

            for (Edge e : graph.adjOf(u)) {
                int v = (e.u == u) ? e.v : e.u;
                tracker.incrementEdgeProcessing();
                tracker.incrementComparisons(1);
                if (!inMST[v] && e.weight < key[v]) {
                    key[v] = e.weight;
                    parentEdge[v] = e;
                    pq.add(v);
                }
            }
        }

        List<Edge> mstEdges = new ArrayList<>();
        double total = 0.0;
        for (int i = 0; i < n; i++) {
            if (parentEdge[i] != null) {
                mstEdges.add(parentEdge[i]);
                total += parentEdge[i].weight;
            }
        }

        tracker.stopTimer();
        return new MSTResult(mstEdges, total, graph.vertexCount(), graph.edgeCount(),
                tracker.getElapsedTimeMillis(),
                tracker.getComparisonCount(), tracker.getUnionOperations(),
                tracker.getFindOperations(), tracker.getEdgeProcessingCount());
    }
}