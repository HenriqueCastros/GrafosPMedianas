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

        // for (int i = 0; i < 5; i++) {   
        //     for (int j = 0; j < 5; j++) {
        //         int num = grafo.getEdgeWeight0Indexed(i, j);
        //         if (num == Integer.MAX_VALUE)
        //             System.out.printf("inf\t");
        //         else
        //             System.out.printf("%d\t", num);
        //     }
        //     System.out.println("");
        // }

        int minDist[][] = grafo.getMinDistanceMatrix();
        for (int i = 0; i < 5; i++) {   
            for (int j = 0; j < 5; j++) {
                System.out.printf("%d\t", minDist[i][j]);
            }
            System.out.println("");
        }

        // for (int i = 0; i < grafo.getNumNodes(); i++) {
        //     int w = grafo.getEdgeWeight0Indexed(21, i);
        //     if (w < Integer.MAX_VALUE)
        //         System.out.println("fromNode: "+ i +", weight "+ w);
        // }
        
        // int minPath[][] = grafo.edgesWeights.clone();
        // for (int k = 1; k <= grafo.getNumNodes(); k++) {
        //     for (int i = 1; i <= grafo.getNumNodes(); i++) {
        //         for (int j = 1; j <= grafo.getNumNodes(); j++) {
        //             if(minPath[i][j] > minPath[i][k] + minPath[k][j])
        //                 minPath[i][j] = minPath[i][k] + minPath[k][j];
        //         }
        //     }
        // }
        
        // int minPath[][] = {
        //     {0, 3, 8, Integer.MAX_VALUE, -4},
        //     {Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 1, 7},
        //     {Integer.MAX_VALUE, 4, 0, Integer.MAX_VALUE, Integer.MAX_VALUE},
        //     {2, Integer.MAX_VALUE, -5, 0, Integer.MAX_VALUE},
        //     {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 6, 0},
        // };
        // for (int k = 0; k < 5; k++) {
        //     for (int i = 0; i < 5; i++) {
        //         for (int j = 0; j < 5; j++) {
        //             if(minPath[i][k] == Integer.MAX_VALUE || minPath[k][j] == Integer.MAX_VALUE)
        //                 minPath[i][j] = minPath[i][j];
        //             else if (minPath[i][j] == Integer.MAX_VALUE)
        //                 minPath[i][j] = minPath[i][k] + minPath[k][j];
        //             else if(minPath[i][j] > minPath[i][k] + minPath[k][j])
        //                 minPath[i][j] = minPath[i][k] + minPath[k][j];
        //         }
        //     }
        // }
        // for (int i = 0; i < 5; i++) {   
        //     for (int j = 0; j < 5; j++) {
        //         System.out.printf("%d ", minPath[i][j]);
        //     }
        //     System.out.println("");
        // }
    }
}