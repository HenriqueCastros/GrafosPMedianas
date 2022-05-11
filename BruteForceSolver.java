import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BruteForceSolver {
    private Grafo grafo;
    private int kCentros;
    private int[][] costMatrix;

    public BruteForceSolver(Grafo grafo, int kCentros) {
        this.grafo = grafo;
        this.kCentros = kCentros;
        
        this.costMatrix = grafo.getMinDistanceMatrix();
    }

    @SuppressWarnings("unchecked")
    public Map.Entry<ArrayList<Integer>, Integer> findBestCenters() {
        Map.Entry<ArrayList<Integer>, Integer> centers = null;
        ArrayList<Integer> bestCenters = null;
        int minRadius = Integer.MAX_VALUE;
        
        for (int i = 1; i <= this.kCentros; i++) {
            centers = this.findBestCenterForNIterative(i);
            
            int tmp = centers.getValue();

            if (minRadius > centers.getValue())
            {
                bestCenters = (ArrayList<Integer>) centers.getKey().clone();
                minRadius = tmp;
            }
        }
        
        return new AbstractMap.SimpleEntry<ArrayList<Integer>, Integer>(bestCenters, minRadius);
    }
    
    @SuppressWarnings("unchecked")
    public Map.Entry<ArrayList<Integer>, Integer> findBestCenterForNIterative(int n) {
        int r = grafo.getNumNodes();
        int[] combination = new int[n];

        ArrayList<Integer> bestCenters = null, toNodes = null;
        int minRadius = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++)
            combination[i] = i;

        while (combination[n - 1] < r){
            toNodes = new ArrayList<>(Arrays.stream(combination).boxed().collect(Collectors.toList()));
            HashMap<Integer, ArrayList<Integer>> map = this.distributeNodesToCenters(toNodes);
            map = this.getDistancesToCenters(map);

            int tmp = this.findMaxRadiusOfDistribution(map);
            if (minRadius > tmp)
            {
                bestCenters = (ArrayList<Integer>) toNodes.clone();
                minRadius = tmp;
            }

            int t = n - 1;
            while (t != 0 && combination[t] == r - n + t)
                t--;
            combination[t]++;
            for (int i = t + 1; i < n; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }
        return new AbstractMap.SimpleEntry<ArrayList<Integer>, Integer>(bestCenters, minRadius);
    }
    
    @SuppressWarnings("unchecked")
    public Map.Entry<ArrayList<Integer>, Integer> findBestCenterForN(int n) {
        ArrayList<Integer> bestCenters = null, toNodes = null;
        ArrayList<int[]> combinations = null;
        int minRadius = Integer.MAX_VALUE;

        combinations = Util.generateCombinatios(grafo.getNumNodes(), n);

        for (int[] combination : combinations){
            toNodes = new ArrayList<>(Arrays.stream(combination).boxed().collect(Collectors.toList()));
            
            HashMap<Integer, ArrayList<Integer>> map = this.distributeNodesToCenters(toNodes);
            map = this.getDistancesToCenters(map);

            int tmp = this.findMaxRadiusOfDistribution(map);
            if (minRadius > tmp)
            {
                bestCenters = (ArrayList<Integer>) toNodes.clone();
                minRadius = tmp;
            }
        }
        return new AbstractMap.SimpleEntry<ArrayList<Integer>, Integer>(bestCenters, minRadius);
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
