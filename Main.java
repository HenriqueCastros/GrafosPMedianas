import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 * Main
 */
public class Main {
    static RandomAccessFile csvWriter;
    static String executor = "Leo";

    public static void main(String[] args) throws IOException {
        File f = new File("results.csv");
        if (!f.exists()) {
            csvWriter = new RandomAccessFile("results.csv", "rw");

            csvWriter.seek(0);

            csvWriter.writeUTF(
                    " , Grafo #, Algoritmo, Centros, Raio, Tempo (ms), Calculador\n");

            csvWriter.close();
        }

        Grafo grafo;
        int kCenters;
        Entry<ArrayList<Integer>, Integer> result;
        long time;
        String algorithim;

        // algorithim = "MST V1";
        // for (int i = 1; i <= 40; i++) {
        //     grafo = new Grafo("instances/pmed" + i + ".txt");
        //     kCenters = grafo.getKCenters();
            
        //     MSTSolver solver = new MSTSolver(grafo, kCenters);

        //     result = solver.findBestCentersV1();
        //     time = solver.getRunTime();

        //     csvWriter = new RandomAccessFile("results.csv", "rw");
        //     csvWriter.seek(csvWriter.length());
        //     csvWriter.writeUTF(
        //             " , " + i + "," + algorithim + "," + result.getKey().toString().replace(", ", "|") + ","
        //                     + result.getValue() + "," + time + "," + executor + "\n");
        //     csvWriter.close();

        //     System.out.println("BestRadius '" + i + "': " + result.getValue());
        // }
        
        // algorithim = "MST V2";
        // for (int i = 1; i <= 40; i++) {
        //     grafo = new Grafo("instances/pmed" + i + ".txt");
        //     kCenters = grafo.getKCenters();
            
        //     MSTSolver solver = new MSTSolver(grafo, kCenters);

        //     result = solver.findBestCenters();
        //     time = solver.getRunTime();

        //     csvWriter = new RandomAccessFile("results.csv", "rw");
        //     csvWriter.seek(csvWriter.length());
        //     csvWriter.writeUTF(
        //             " , " + i + "," + algorithim + "," + result.getKey().toString().replace(", ", "|") + ","
        //                     + result.getValue() + "," + time + "," + executor + "\n");
        //     csvWriter.close();
            
        //     System.out.println("BestRadius '" + i + "': " + result.getValue());
        // }
        
        algorithim = "Brute Force";
        for (int i = 1; i <= 40; i++) {
            grafo = new Grafo("instances/pmed" + i + ".txt");
            kCenters = grafo.getKCenters();

            BruteForceSolver solver = new BruteForceSolver(grafo, kCenters);

            result = solver.findBestCenters();
            time = solver.getRunTime();

            csvWriter = new RandomAccessFile("results.csv", "rw");
            csvWriter.seek(csvWriter.length());
            if (time > BruteForceSolver.TIMEOUT) {
                csvWriter.writeUTF(
                        " , " + i + "," + algorithim + "," + result.getKey().toString().replace(", ", "|") + ","
                                + result.getValue() + "," + time + " (TIMEOUT)," + executor + "\n");
            } else {
                csvWriter.writeUTF(
                        " , " + i + "," + algorithim + "," + result.getKey().toString().replace(", ", "|") + ","
                                + result.getValue() + "," + time + "," + executor + "\n");
            }

            csvWriter.close();

            System.out.println("BestRadius '" + i + "': " + result.getValue());
        }
    }
}