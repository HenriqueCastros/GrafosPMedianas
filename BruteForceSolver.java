import java.util.ArrayList;
import java.util.Collections;
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

    public ArrayList<Integer> findBestKCenter() {
        ArrayList<Integer> bestCenters = null, toNodes = null;
        int minRadius = Integer.MAX_VALUE;

        for (int i = 0; i < grafo.getNumNodes(); i++) {
            for (int j = i+1; j < grafo.getNumNodes(); j++) {

                toNodes = new ArrayList<Integer>();
                toNodes.add(i);
                toNodes.add(j);

                HashMap<Integer, ArrayList<Integer>> map = this.distributeNodesToCenters(toNodes);
                map = this.getDistancesToCenters(map);

                int tmp = this.findMaxRadiusOfDistribution(map);
                if (minRadius > tmp)
                {
                    bestCenters = (ArrayList<Integer>) toNodes.clone();
                    minRadius = tmp;
                }
            }
        }

        return bestCenters;
    }

    public int findMaxRadiusOfDistribution(HashMap<Integer, ArrayList<Integer>> distributedNodes)
    {
        Integer maxEntry = null;
        int maxRadius = Integer.MIN_VALUE;

        for (Integer entry : distributedNodes.keySet())
        {
            ArrayList<Integer> distances = distributedNodes.get(entry);
            if (distances.size() > 0) {
                int tmp = Collections.max(distances);
                if (maxEntry == null || tmp > maxRadius)
                {
                    maxEntry = entry;
                    maxRadius = tmp;
                }
            }
        }
        return maxRadius;
    }

    public HashMap<Integer, ArrayList<Integer>> distributeNodesToCenters(ArrayList<Integer> centers)
    {
        HashMap<Integer, ArrayList<Integer>> distributedNodes = new HashMap<Integer, ArrayList<Integer>>(centers.size());

        for (int i = 0; i < centers.size(); i++) 
            distributedNodes.put(centers.get(i), new ArrayList<Integer>());

        for (int i = 0; i < this.grafo.getNumNodes(); i++) {
            int cheapestNode = this.getCheapestWithinFromNode(i, centers);
            distributedNodes.get(cheapestNode).add(i);
        }

        return distributedNodes;
    }    
    
    public HashMap<Integer, ArrayList<Integer>> getDistancesToCenters(HashMap<Integer, ArrayList<Integer>> distributedNodes)
    {
        HashMap<Integer, ArrayList<Integer>> distances = new HashMap<Integer, ArrayList<Integer>>(distributedNodes.size());
        
        for (Integer key : distributedNodes.keySet())
        {
            distances.put(key, new ArrayList<Integer>());
            for (Integer node : distributedNodes.get(key))
                distances.get(key).add(this.costMatrix[key][node]);
        }

        return distances;
    }

    /*
     * Returns the cheapest toNode, based on nodes passed as param
     */
    public int getCheapestWithinToNode(int fromNode, ArrayList<Integer> toNodes){
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

    /*
     * Returns the cheapest FromNode, based on nodes passed as param
     */
    public int getCheapestWithinFromNode(int toNode, ArrayList<Integer> fromNodes){
        int minDist, fromNode;

        minDist = Integer.MAX_VALUE;
        fromNode = fromNodes.get(0);

        for (int i = 0; i < fromNodes.size(); i++) {
            if(minDist > this.costMatrix[fromNodes.get(i)][toNode]) {
                minDist =  this.costMatrix[fromNodes.get(i)][toNode];
                fromNode = fromNodes.get(i);
            }
        }
        return fromNode;
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
