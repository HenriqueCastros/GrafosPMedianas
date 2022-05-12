import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Grafo {
    private boolean isDirectional = false;
    private int numEdges;
    private int numNodes;
    private int kCenters;
    public int edgesWeights[][];
    
    public Grafo(int numNodes) {
        this(numNodes, false);
    }
    
    public Grafo(int numNodes, boolean isDirectional) {
        this.numEdges = 0;
        this.numNodes = numNodes;
        this.isDirectional = isDirectional;
        this.edgesWeights = new int[numNodes][numNodes];

        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                this.edgesWeights[i][j] = i != j ? Integer.MAX_VALUE : 0;
            }
        }
    }

    public Grafo(String path) {
        numEdges = 0;
        isDirectional = false;

        try (Scanner scanner = new Scanner(new File(path))) {
            int fromNode, toNode, weight;
            numNodes = scanner.nextInt();
            
            edgesWeights = new int[numNodes][numNodes];

            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    this.edgesWeights[i][j] = i != j ? Integer.MAX_VALUE : 0;
                }
            }
            
            /* int numEdges = */scanner.nextInt();
            kCenters = scanner.nextInt();
            
            while (scanner.hasNextInt()) {
                fromNode = scanner.nextInt();
                toNode = scanner.nextInt();
                weight = scanner.nextInt();
                this.setEdge1Indexed(fromNode, toNode, weight);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * Calculates the minimal distance between nodes
     * 
     * @return matrix [x][y] where:
     * x -> fromNode
     * y -> toNode
     */
    public int[][] getMinDistanceMatrix() {
        int minPath[][] = this.edgesWeights.clone();
        
        for (int k = 0; k < this.getNumNodes(); k++) {
            for (int i = 0; i < this.getNumNodes(); i++) {
                for (int j = 0; j < this.getNumNodes(); j++) {
                    if (minPath[i][k] == Integer.MAX_VALUE ||
                            minPath[k][j] == Integer.MAX_VALUE)
                        minPath[i][j] = minPath[i][j];
                    else if (minPath[i][j] == Integer.MAX_VALUE)
                        minPath[i][j] = minPath[i][k] + minPath[k][j];
                    else if (minPath[i][j] > minPath[i][k] + minPath[k][j])
                        minPath[i][j] = minPath[i][k] + minPath[k][j];
                }
            }
        }
        return minPath;
    }
    
    public void setEdge(int fromNode, int toNode, int weight) {
        // se for adicionar uma aresta
        if (weight != Integer.MAX_VALUE && getEdgeWeight(fromNode, toNode) == Integer.MAX_VALUE) 
            this.numEdges ++;
        // se for remover uma aresta
        else if (weight == Integer.MAX_VALUE && getEdgeWeight(fromNode, toNode) != Integer.MAX_VALUE)
            this.numEdges --;
        
        this.edgesWeights[fromNode][toNode] = weight;
        if (!this.isDirectional)
            this.edgesWeights[toNode][fromNode] = weight;
    }

    public void setEdge1Indexed(int fromNode, int toNode, int weight) {
        this.setEdge(fromNode - 1, toNode - 1, weight);
    }

    public int getEdgeWeight(int fromNode, int toNode) {
        return this.edgesWeights[fromNode][toNode];
    }

    public int getEdgeWeight1Indexed(int fromNode, int toNode) {
        return this.getEdgeWeight(fromNode-1, toNode-1);
    }

    public int getNumEdges() {
        return numEdges;
    }

    public void setNumEdges(int numEdges) {
        this.numEdges = numEdges;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }

    public boolean isDirectional() {
        return this.isDirectional;
    }

    public int getKCenters() {
        return this.kCenters;
    }

    public void setKCenters(int kCenters) {
        this.kCenters = kCenters;
    }

    /**
     * Retorna todos os vértices do grafo em um Array<(from, to), Weight>
     */
    public ArrayList<Edge> getAllEdges() {
        ArrayList<Edge> returnValue = new ArrayList<Edge>();
        int start;

        for (int i = 0; i < numNodes; i++) {
            start = isDirectional ? 0 : i + 1;
            for (int j = start; j < numNodes; j++) {
                if (i != j && edgesWeights[i][j] != Integer.MAX_VALUE) {
                    returnValue.add(new Edge(i, j, edgesWeights[i][j]));
                }
            }
        }
        
        return returnValue;
    }

    public ArrayList<Edge> getAllEdgesFromNode(int node) {
        ArrayList<Edge> returnValue = new ArrayList<Edge>();
        
        for (int i = 0; i < numNodes; i++) {
            if (i != node && edgesWeights[node][i] != Integer.MAX_VALUE) {
                returnValue.add(new Edge(node, i, edgesWeights[node][i]));
            }
        }

        return returnValue;
    }

    public int calculateExentricity(int node) {
        PriorityQueue<ComparableTuple<Edge, Integer>> border = new PriorityQueue<ComparableTuple<Edge, Integer>>();
        int dist[] = new int[numNodes];
        int excentricity = 0;
        
        for (int i = 0; i < numNodes; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[node] = 0;
        
        for (Edge e : getAllEdgesFromNode(node)) {
            border.add(new ComparableTuple<Edge, Integer>(e, e.weight));
        }
        
        while (!border.isEmpty()) {
            ComparableTuple<Edge, Integer> tuple = border.remove();
            Edge e = tuple.getKey();
            int distance = tuple.getValue();
            
            if (dist[e.to] == Integer.MAX_VALUE) {
                dist[e.to] = distance;

                for (Edge ed : getAllEdgesFromNode(e.to)) {
                    border.add(new ComparableTuple<Edge, Integer>(ed, ed.weight + distance));
                }
            }
        }
        
        for (int i = 0; i < numNodes; i++) {
            excentricity = dist[i] != Integer.MAX_VALUE && excentricity < dist[i] ? dist[i] : excentricity;
        }
        
        return excentricity;
    }

    @Override
    public String toString() {
        String strRepresentation = "";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int num = this.edgesWeights[i][j];
                if (num == Integer.MAX_VALUE)
                    strRepresentation += "inf";
                else
                    strRepresentation += num;
                strRepresentation += "\t";
            }
            strRepresentation += "\n";
        }
        return ("Grafo [numEdges=" +
                numEdges +
                ", numNodes=" +
                numNodes +
                "]\n" +
                strRepresentation);
    }

    public boolean isReachableFrom(int origin, int objective) {
        boolean[] visited = new boolean[numNodes];
        ArrayList<Integer> visitQueue = new ArrayList<>();
        visitQueue.add(origin);

        for (int i = 0; i < numNodes; i++)
            visited[i] = false;
        
        while (visitQueue.size() > 0) {
            int node = visitQueue.remove(0);
            if (visited[node]) continue;

            for (int i = 0; i < numNodes; i++) {
                if (edgesWeights[node][i] != Integer.MAX_VALUE) {
                    if (i == objective) {
                        return true;
                    } else if (i != node && !visited[i]) {
                        visitQueue.add(i);
                    }
                }
            }
            visited[node] = true;
        }
        
        return false;
    }

    public static String graphMatrixToString(int weigths[][]) {
        String strRepresentation = "";
        for (int i = 0; i < weigths.length; i++) {
            for (int j = 0; j < weigths[i].length; j++) {
                int num = weigths[i][j];
                if (num == Integer.MAX_VALUE)
                    strRepresentation += "inf";
                else
                    strRepresentation += num;
                strRepresentation += "\t";
            }
            strRepresentation += "\n";
        }
        return strRepresentation;
    }
}
