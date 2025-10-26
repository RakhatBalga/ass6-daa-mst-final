package com.blablabla.util;

import com.blablabla.graph.Graph;

import java.util.ArrayDeque;
import java.util.Queue;

public class InputValidator {

    public static boolean isConnected(Graph g) {
        int n = g.vertexCount();
        if (n == 0) return true;
        boolean[] seen = new boolean[n];
        Queue<Integer> q = new ArrayDeque<>();
        q.add(0);
        seen[0] = true;
        int count = 1;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (var e : g.adjOf(u)) {
                int v = (e.u == u) ? e.v : e.u;
                if (!seen[v]) {
                    seen[v] = true;
                    q.add(v);
                    count++;
                }
            }
        }
        return count == n;
    }
}