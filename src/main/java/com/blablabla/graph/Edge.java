package com.blablabla.graph;

public class Edge implements Comparable<Edge> {
    public final int u;
    public final int v;
    public final double weight;
    public final String uLabel;
    public final String vLabel;

    public Edge(int u, int v, double weight, String uLabel, String vLabel) {
        this.u = u;
        this.v = v;
        this.weight = weight;
        this.uLabel = uLabel;
        this.vLabel = vLabel;
    }

    @Override
    public int compareTo(Edge o) {
        return Double.compare(this.weight, o.weight);
    }

    @Override
    public String toString() {
        return String.format("%s-%s(%.2f)", uLabel, vLabel, weight);
    }
}