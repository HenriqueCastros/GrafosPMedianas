/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        Grafo grafo;// = new Grafo("instances/pmed1.txt");
        int kCenters;// = grafo.getKCenters();
        for (int i = 1; i<=40; i++) {
            grafo = new Grafo("instances/pmed"+i+".txt");
            kCenters = grafo.getKCenters();
        
            MSTSolver mst = new MSTSolver(grafo, kCenters);
            System.out.println("BestRadius '"+i+"': "+mst.findBestCenters().getValue());
        }
        
        // MSTSolver mst = new MSTSolver(grafo, kCenters);
        // System.out.println("BestRadius : "+mst.findBestCenters());
        // int minDist[][] = grafo.getMinDistanceMatrix();
        
        // System.out.print(Grafo.graphMatrixToString(minDist));
        
        // int[][] subgraphs = mst.buildMSForest().getMinDistanceMatrix();
        
        // System.out.println(Grafo.graphMatrixToString(mst.buildMSForest().getMinDistanceMatrix()));
        
        // BruteForceSolver bfSolver = new BruteForceSolver(grafo, grafo.getKCenters());
        
        // System.out.println("Best centers = " + bfSolver.findBestCenters());

    }
}