package com.blablabla.algorithms;

import com.blablabla.graph.Graph;
import com.blablabla.util.PerformanceTracker;

public interface MSTAlgorithm {
    MSTResult run(Graph graph, PerformanceTracker tracker);
}