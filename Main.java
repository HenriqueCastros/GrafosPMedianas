import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        Grafo grafo = null;
        int pMedians = 0;

        try (Scanner scanner = new Scanner(new File("instances/pmed01.txt"))) {
            int numNodes = scanner.nextInt();
            int numEdges = scanner.nextInt();
            pMedians = scanner.nextInt();

            grafo = new Grafo(numEdges, numNodes);

            while(scanner.hasNextInt())
                grafo.setEdge(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < grafo.getNumNodes(); i++) {
            int w = grafo.getEdgeWeight0Indexed(21, i);
            if (w < Integer.MAX_VALUE)
                System.out.println("fromNode: "+ i +", weight "+ w);
        }
    }
}