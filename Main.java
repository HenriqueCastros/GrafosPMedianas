import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        Grafo grafo = null;
        int kCenters = 0;

        try (Scanner scanner = new Scanner(new File("instances/testLeo.txt"))) {
            int fromNode, toNode, weight;
            int numNodes = scanner.nextInt();
            int numEdges = scanner.nextInt();
            kCenters = scanner.nextInt();
            
            grafo = new Grafo(numNodes);

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
        
        // int minDist[][] = grafo.getMinDistanceMatrix();
        
        // System.out.print(Grafo.graphMatrixToString(minDist));

        MSTSolver mst = new MSTSolver(grafo, kCenters);
        System.out.println(mst.buildMST().getAllEdges());
        
        // BruteForceSolver bfSolver = new BruteForceSolver(grafo, kCenters);

        // System.out.println("Best centers = " + bfSolver.findBestCenters());

    }
}