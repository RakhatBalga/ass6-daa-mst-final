package com.blablabla.util;

import com.blablabla.algorithms.MSTResult;
import com.blablabla.graph.Edge;
import com.blablabla.graph.Graph;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JSONHandler {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<GraphWrapper> readGraphs(File f) throws Exception {
        JsonNode root = mapper.readTree(f);
        JsonNode graphs = root.get("graphs");
        List<GraphWrapper> list = new ArrayList<>();
        if (graphs != null && graphs.isArray()) {
            for (JsonNode gnode : graphs) {
                int id = gnode.get("id").asInt();
                var nodes = gnode.get("nodes");
                Graph g = new Graph(nodes.size());
                for (int i = 0; i < nodes.size(); i++) {
                    g.setLabel(i, nodes.get(i).asText());
                }
                var edges = gnode.get("edges");
                for (JsonNode e : edges) {
                    g.addEdgeByLabels(
                            e.get("from").asText(),
                            e.get("to").asText(),
                            e.get("weight").asDouble()
                    );
                }
                list.add(new GraphWrapper(id, g));
            }
        }
        return list;
    }

    public void writeResults(File outFile, List<ResultBundle> results) throws Exception {
        ObjectNode root = mapper.createObjectNode();
        ArrayNode arr = mapper.createArrayNode();

        for (ResultBundle r : results) {
            ObjectNode obj = mapper.createObjectNode();
            obj.put("graph_id", r.graphId);

            ObjectNode stats = mapper.createObjectNode();
            stats.put("vertices", r.vertices);
            stats.put("edges", r.edges);
            obj.set("input_stats", stats);

            obj.set("prim", toAlgoNode(r.prim));
            obj.set("kruskal", toAlgoNode(r.kruskal));
            arr.add(obj);
        }

        root.set("results", arr);
        mapper.writerWithDefaultPrettyPrinter().writeValue(outFile, root);
    }

    private ObjectNode toAlgoNode(MSTResult res) {
        ObjectNode node = mapper.createObjectNode();
        ArrayNode edges = mapper.createArrayNode();

        for (Edge e : res.mstEdges) {
            ObjectNode en = mapper.createObjectNode();
            en.put("from", e.uLabel);
            en.put("to", e.vLabel);
            en.put("weight", e.weight);
            edges.add(en);
        }

        node.set("mst_edges", edges);
        node.put("total_cost", res.totalCost);

        // ✅ Исправление: заменено getTotalOps() на подсчёт вручную
        int totalOps = res.comparisons + res.unions + res.finds + res.edgeProcessed;
        node.put("operations_count", totalOps);

        node.put("execution_time_ms", res.executionTimeMs);
        return node;
    }

    public static class GraphWrapper {
        public final int id;
        public final Graph graph;
        public GraphWrapper(int id, Graph graph) {
            this.id = id;
            this.graph = graph;
        }
    }

    public static class ResultBundle {
        public final int graphId;
        public final int vertices;
        public final int edges;
        public final MSTResult prim;
        public final MSTResult kruskal;

        public ResultBundle(int graphId, int vertices, int edges, MSTResult prim, MSTResult kruskal) {
            this.graphId = graphId;
            this.vertices = vertices;
            this.edges = edges;
            this.prim = prim;
            this.kruskal = kruskal;
        }
    }
}