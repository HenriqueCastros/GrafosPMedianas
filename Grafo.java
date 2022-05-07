public class Grafo {
    private int numEdges;
    private int numNodes;
    public int edgesWeights[][];

    public Grafo(int numEdges, int numNodes) {
        this.numEdges = numEdges;
        this.numNodes = numNodes;
        this.edgesWeights = new int[numNodes][numNodes];

        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                this.edgesWeights[i][j] = i != j ? Integer.MAX_VALUE : 0;
            }
        }
    }

    public int[][] getMinDistanceMatrix() {
        int minPath[][] = this.edgesWeights.clone();

        for (int k = 0; k < this.getNumNodes(); k++) {
            for (int i = 0; i < this.getNumNodes(); i++) {
                for (int j = 0; j < this.getNumNodes(); j++) {
                    if(minPath[i][k] == Integer.MAX_VALUE || minPath[k][j] == Integer.MAX_VALUE)
                        minPath[i][j] = minPath[i][j];
                    else if (minPath[i][j] == Integer.MAX_VALUE)
                        minPath[i][j] = minPath[i][k] + minPath[k][j];
                    else if(minPath[i][j] > minPath[i][k] + minPath[k][j])
                        minPath[i][j] = minPath[i][k] + minPath[k][j];
                }
            }
        }
        return minPath;
    }

    public void setEdge0Indexed(int fromNode, int toNode, int weight) {
        this.edgesWeights[fromNode][toNode] = weight;
    }

    public void setEdge(int fromNode, int toNode, int weight) {
        this.setEdge0Indexed(fromNode-1, toNode-1, weight);
    }

    public int getEdgeWeight0Indexed(int fromNode, int toNode) {
        return this.edgesWeights[fromNode][toNode];
    }

    public int getEdgeWeight(int fromNode, int toNode) {
        return this.getEdgeWeight0Indexed(fromNode, toNode);
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
}
