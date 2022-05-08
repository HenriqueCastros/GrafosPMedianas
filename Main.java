import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        Grafo grafo = null;
        int kCenters = 0;

        try (Scanner scanner = new Scanner(new File("instances/test.txt"))) {
            int fromNode, toNode, weight;
            int numNodes = scanner.nextInt();
            int numEdges = scanner.nextInt();
            kCenters = scanner.nextInt();

            grafo = new Grafo(numEdges, numNodes, true);

            while(scanner.hasNextInt())
            {
                fromNode = scanner.nextInt();
                toNode = scanner.nextInt();
                weight = scanner.nextInt();
                grafo.setEdge(fromNode, toNode, weight);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int minDist[][] = grafo.getMinDistanceMatrix();

        System.out.print(Grafo.graphMatrixToString(minDist));

        BruteForceSolver bfSolver = new BruteForceSolver(grafo, kCenters);
        System.out.println();
        System.out.println(bfSolver.findBestCenter());

        ArrayList<Integer> toNodes = new ArrayList<Integer>();
        toNodes.add(0);
        toNodes.add(1);
        // System.out.println(bfSolver.getCheapestWithinNode(3, toNodes));

        HashMap<Integer, ArrayList<Integer>> map = bfSolver.distributeNodesToCenters(toNodes);
        for (Integer key : map.keySet()) {
            System.out.println(key+" "+ map.get(key));
        }
    }
}