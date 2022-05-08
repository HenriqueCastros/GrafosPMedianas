import java.util.ArrayList;
import java.util.HashMap;

public class BruteForceSolver {
    private Grafo grafo;
    private int kCentros;
    private int[][] costMatrix;

    public BruteForceSolver(Grafo grafo, int kCentros) {
        this.grafo = grafo;
        this.kCentros = kCentros;

        this.costMatrix = grafo.getMinDistanceMatrix();
    }

    public int findBestCenter() {
        int centerRadius, center = 0;

        centerRadius = Integer.MAX_VALUE;

        for (int i = 0; i < this.grafo.getNumNodes(); i++) {
            int maxDist = Integer.MIN_VALUE;
            for (int j = 0; j < this.grafo.getNumNodes(); j++) {
                if (maxDist < this.costMatrix[i][j])
                    maxDist = this.costMatrix[i][j];
            }
            if (centerRadius > maxDist) {
                center = i;
                centerRadius = maxDist;
            }
        }
        return center;
    }

    public HashMap<Integer, ArrayList<Integer>> distributeNodesToCenters(ArrayList<Integer> centers)
    {
        HashMap<Integer, ArrayList<Integer>> distributedNodes = new HashMap<Integer, ArrayList<Integer>>(centers.size());

        for (int i = 0; i < centers.size(); i++) {
            distributedNodes.put(centers.get(i), new ArrayList<Integer>());
        }

        for (int i = 0; i < this.grafo.getNumNodes(); i++) {
            int cheapestNode = this.getCheapestWithinNode(i, centers);
            distributedNodes.get(cheapestNode).add(i);
        }

        return distributedNodes;
    }

    /*
     * Returns the cheapest toNode, based on nodes passed as param
     */
    public int getCheapestWithinNode(int fromNode, ArrayList<Integer> toNodes){
        int minDist, toNode;

        minDist = Integer.MAX_VALUE;
        toNode = toNodes.get(0);

        for (int i = 0; i < toNodes.size(); i++) {
            if(minDist > this.costMatrix[fromNode][toNodes.get(i)]) {
                minDist =  this.costMatrix[fromNode][toNodes.get(i)];
                toNode = toNodes.get(i);
            }
        }
        return toNode;
    }

    int getCheapestNode(int fromNode) {
        int minDist, toNode;

        minDist = this.costMatrix[fromNode][0];
        toNode = 0;

        for (int i = 1; i < this.grafo.getNumNodes(); i++) {
            if(minDist > this.costMatrix[fromNode][i]) {
                minDist = this.costMatrix[fromNode][i];
                toNode = i;
            }
        }
        return toNode;
    }
}
