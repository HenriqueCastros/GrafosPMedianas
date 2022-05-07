public class Grafo {
    private int numEdges;
    private int numNodes;
    private int edgesWeights[][];

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

    public void setEdge0Indexed(int fromNode, int toNode, int weight) {
        this.edgesWeights[fromNode][toNode] = weight;
        this.edgesWeights[toNode][fromNode] = weight;
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
