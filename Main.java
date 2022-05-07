import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        boolean isDirectional = true; 
        Grafo grafo = null;
        int pMedians = 0;

        try (Scanner scanner = new Scanner(new File("instances/test.txt"))) {
            int fromNode, toNode, weight;
            int numNodes = scanner.nextInt();
            int numEdges = scanner.nextInt();
            pMedians = scanner.nextInt();

            grafo = new Grafo(numEdges, numNodes);

            while(scanner.hasNextInt())
            {
                fromNode = scanner.nextInt();
                toNode = scanner.nextInt();
                weight = scanner.nextInt();
                grafo.setEdge(fromNode, toNode, weight);
                if (!isDirectional)
                    grafo.setEdge(toNode, fromNode, weight);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println((grafo));

        int minDist[][] = grafo.getMinDistanceMatrix();

        System.out.print(Grafo.graphMatrixToString(minDist));

        // for (int i = 0; i < grafo.getNumNodes(); i++) {
        //     int w = grafo.getEdgeWeight0Indexed(21, i);
        //     if (w < Integer.MAX_VALUE)
        //         System.out.println("fromNode: "+ i +", weight "+ w);
        // }
    }
}