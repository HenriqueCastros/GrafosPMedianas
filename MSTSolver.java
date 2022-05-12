
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.PriorityQueue;

public class MSTSolver {
    private Grafo grafo;
    private int kCentros;

    public MSTSolver(Grafo grafo, int kCentros) {
        this.grafo = grafo;
        this.kCentros = kCentros;
    }

    public Grafo buildMST() {
        Grafo g = new Grafo(grafo.getNumNodes(), grafo.isDirectional());
        ArrayList<Edge> edges = grafo.getAllEdges();
        Collections.sort(edges);
        Edge e;

        int i = 0;
        while (i < edges.size() && g.getNumEdges() < (g.getNumNodes() - 1)) {
            e = edges.get(i);
            if (!g.isReachableFrom(e.from, e.to)) {
                g.setEdge(e.from, e.to, e.weight);
            }
            i++;
        }

        return g;
    }

    public Grafo buildMSForest() {
        Grafo g = new Grafo(grafo.getNumNodes(), grafo.isDirectional());
        ArrayList<Edge> edges = grafo.getAllEdges();
        Collections.sort(edges);
        Edge e;

        int i = 0;
        while (i < edges.size() && g.getNumEdges() < (g.getNumNodes() - kCentros)) {
            e = edges.get(i);
            if (!g.isReachableFrom(e.from, e.to)) {
                g.setEdge(e.from, e.to, e.weight);
            }
            i++;
        }

        return g;
    }

    public Grafo buildMSForestWithPriorityQueueV1() {
        Grafo g = new Grafo(grafo.getNumNodes(), grafo.isDirectional());
        PriorityQueue<ComparableTuple<Edge, Integer>> edges = new PriorityQueue<ComparableTuple<Edge, Integer>>();

        for (Edge e : grafo.getAllEdges()) {
            edges.add(new ComparableTuple<Edge, Integer>(e, e.weight));
        }

        while (!edges.isEmpty() && g.getNumEdges() < (g.getNumNodes() - kCentros)) {
            ComparableTuple<Edge, Integer> tuple = edges.remove();
            Edge e = tuple.getKey();

            if (!g.isReachableFrom(e.from, e.to)) {
                g.setEdge(e.from, e.to, e.weight);
                int exFrom = g.calculateExentricity(e.from);
                int exTo = g.calculateExentricity(e.to);
                int ex = exFrom < exTo ? exFrom : exTo;
                if (ex > edges.peek().getValue() && ex > tuple.getValue()) {
                    g.setEdge(e.from, e.to, Integer.MAX_VALUE);
                    tuple.setValue(ex);
                    edges.add(tuple);
                }
            }
        }

        return g;
    }

    public static int calculateComponentRadius(int[][] graphMatrix, int node) {
        ArrayList<Integer> vertices = new ArrayList<>();
        int subgraphRadius = Integer.MAX_VALUE;

        for (int i = 0; i < graphMatrix[node].length; i++) {
            if (graphMatrix[node][i] != Integer.MAX_VALUE) {
                vertices.add(i);
            }
        }

        for (int n : vertices) {
            int excentricity = 0;
            for (int j : vertices) {
                excentricity = excentricity < graphMatrix[n][j] ? graphMatrix[n][j] : excentricity;
            }

            if (excentricity < subgraphRadius) {
                subgraphRadius = excentricity;
            }
        }

        return subgraphRadius;
    }
    
    public Grafo buildMSForestWithPriorityQueue() {
        Grafo g = new Grafo(grafo.getNumNodes(), grafo.isDirectional());
        PriorityQueue<ComparableTuple<Edge, Integer>> edges = new PriorityQueue<ComparableTuple<Edge, Integer>>();

        for (Edge e : grafo.getAllEdges()) {
            edges.add(new ComparableTuple<Edge, Integer>(e, e.weight));
        }

        while (!edges.isEmpty() && g.getNumEdges() < (g.getNumNodes() - kCentros)) {
            ComparableTuple<Edge, Integer> tuple = edges.remove();
            Edge e = tuple.getKey();
            
            if (!g.isReachableFrom(e.from, e.to)) {
                g.setEdge(e.from, e.to, e.weight);
                ArrayList<Integer> reachableNodes = g.getReachableNodes(e.from);
                
                int ex = Integer.MAX_VALUE;
                for (int n : reachableNodes) {
                    int thisex = g.calculateExentricity(n);
                    ex = ex < thisex ? ex : thisex;
                }
                
                if (ex > edges.peek().getValue() && ex > tuple.getValue()) {
                    g.setEdge(e.from, e.to, Integer.MAX_VALUE);
                    tuple.setValue(ex);
                    edges.add(tuple);
                }
            }
        }

        return g;
    }

    @SuppressWarnings("unchecked")
    public Map.Entry<ArrayList<Integer>, Integer> findBestCenters() {
        int[][] subgraphs = this.buildMSForestWithPriorityQueue().getMinDistanceMatrix();
        int[][] originalGraph = this.grafo.getMinDistanceMatrix();
        boolean[] visited = new boolean[subgraphs.length];
        ArrayList<Integer>[] verticesBySubgraph = new ArrayList[kCentros];
        ArrayList<Integer> centres = new ArrayList<Integer>();
        int radius = 0;
        int k = 0;

        for (int i = 0; i < verticesBySubgraph.length; i++)
            verticesBySubgraph[i] = new ArrayList<>();

        for (int i = 0; i < visited.length; i++)
            visited[i] = false;

        for (int i = 0; i < subgraphs.length; i++) {
            if (!visited[i]) {
                for (int j = 0; j < subgraphs[i].length; j++) {
                    if (subgraphs[i][j] != Integer.MAX_VALUE) {
                        verticesBySubgraph[k].add(j);
                        visited[j] = true;
                    }
                }
                k++;
            }
        }

        for (int i = 0; i < verticesBySubgraph.length; i++) {
            int subgraphRadius = Integer.MAX_VALUE;
            int subgraphCentre = 0;
            for (int n : verticesBySubgraph[i]) {
                int excentricity = 0;
                for (int j : verticesBySubgraph[i]) {
                    excentricity = originalGraph[n][j] != Integer.MAX_VALUE && excentricity < originalGraph[n][j]
                            ? originalGraph[n][j]
                            : excentricity;
                }

                if (excentricity < subgraphRadius) {
                    subgraphRadius = excentricity;
                    subgraphCentre = n;
                }
            }
            centres.add(subgraphCentre);
            radius = radius < subgraphRadius ? subgraphRadius : radius;
        }

        return new AbstractMap.SimpleEntry<ArrayList<Integer>, Integer>(centres, radius);
    }
}
