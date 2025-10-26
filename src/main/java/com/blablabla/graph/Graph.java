package com.blablabla.graph;

import java.util.*;

public class Graph {
    private final int n;
    private final List<List<Edge>> adj;
    private final Map<String, Integer> labelToIndex = new LinkedHashMap<>();
    private final List<String> indexToLabel = new ArrayList<>();
    private final List<Edge> edgeList = new ArrayList<>();

    public Graph(int n) {
        this.n = n;
        adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n; i++) indexToLabel.add(null);
    }

    public int vertexCount() { return n; }

    public void setLabel(int idx, String label) {
        if (idx < 0 || idx >= n) throw new IllegalArgumentException("Index out of range");
        labelToIndex.put(label, idx);
        indexToLabel.set(idx, label);
    }

    public void addEdgeByLabels(String a, String b, double w) {
        Integer u = labelToIndex.get(a);
        Integer v = labelToIndex.get(b);
        if (u == null || v == null) throw new IllegalArgumentException("Unknown label");
        addEdge(u, v, w);
    }

    public void addEdge(int u, int v, double w) {
        String lu = indexToLabel.get(u);
        String lv = indexToLabel.get(v);
        Edge e = new Edge(u, v, w, lu, lv);
        adj.get(u).add(e);
        adj.get(v).add(e);
        edgeList.add(e);
    }

    public List<Edge> adjOf(int u) { return Collections.unmodifiableList(adj.get(u)); }
    public List<Edge> edges() { return Collections.unmodifiableList(edgeList); }
    public long edgeCount() { return edgeList.size(); }
    public String labelOf(int i) { return indexToLabel.get(i); }
}