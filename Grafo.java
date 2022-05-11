import java.util.ArrayList;

public class Grafo {
    private boolean isDirectional = false;
    private int numEdges;
    private int numNodes;
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
        if (getEdgeWeight(fromNode, toNode) == Integer.MAX_VALUE)
            this.numEdges ++;
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

    /**
     * Retorna todos os vértices do grafo em um Array<(from, to), Weight>
     */
    public ArrayList<Edge> getAllEdges() {
        int start;
        ArrayList<Edge> returnValue = new ArrayList<Edge>();

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
