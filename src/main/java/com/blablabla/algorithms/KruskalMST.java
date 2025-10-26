package com.blablabla.algorithms;

import com.blablabla.graph.Edge;
import com.blablabla.graph.Graph;
import com.blablabla.util.PerformanceTracker;
import com.blablabla.util.UnionFind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST implements MSTAlgorithm {

    @Override
    public MSTResult run(Graph graph, PerformanceTracker tracker) {
        tracker.startTimer();

        List<Edge> edges = new ArrayList<>(graph.edges());
        Collections.sort(edges); // сортировка по весу
        int n = graph.vertexCount();
        UnionFind uf = new UnionFind(n);

        List<Edge> mst = new ArrayList<>();
        double total = 0.0;

        for (Edge e : edges) {
            tracker.incrementEdgeProcessing();
            tracker.incrementComparisons(1);
            int ru = uf.find(e.u);
            int rv = uf.find(e.v);
            tracker.incrementFindOperations();
            tracker.incrementFindOperations();

            if (ru != rv) {
                uf.union(ru, rv);
                tracker.incrementUnionOperations();
                mst.add(e);
                total += e.weight;
            }
            if (mst.size() == n - 1) break;
        }

        tracker.stopTimer();
        return new MSTResult(mst, total, graph.vertexCount(), graph.edgeCount(),
                tracker.getElapsedTimeMillis(),
                tracker.getComparisonCount(), tracker.getUnionOperations(),
                tracker.getFindOperations(), tracker.getEdgeProcessingCount());
    }
}