import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        Grafo grafo = new Grafo("instances/pmed40.txt");
        int kCenters = grafo.getKCenters();
        
        // int minDist[][] = grafo.getMinDistanceMatrix();
        
        // System.out.print(Grafo.graphMatrixToString(minDist));
        
        MSTSolver mst = new MSTSolver(grafo, kCenters);
        System.out.println(mst.findBestCenters());
        // int[][] subgraphs = mst.buildMSForest().getMinDistanceMatrix();
        
        // System.out.println(Grafo.graphMatrixToString(mst.buildMSForest().getMinDistanceMatrix()));
        
        // BruteForceSolver bfSolver = new BruteForceSolver(grafo, grafo.getKCenters());
        
        // System.out.println("Best centers = " + bfSolver.findBestCenters());

    }
}