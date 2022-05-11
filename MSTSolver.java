import java.util.ArrayList;
import java.util.Collections;

public class MSTSolver {
    private Grafo grafo;
    private int kCentros;

    public MSTSolver(Grafo grafo, int kCentros) {
        this.grafo = grafo;
        this.kCentros = kCentros;
    }

    public Grafo buildMST() {
        Grafo g = new Grafo(grafo.getNumNodes(), grafo.isDirectional());
        Edge e;

        ArrayList<Edge> edges = grafo.getAllEdges();
        Collections.sort(edges);

        int i = 0;
        while (i < edges.size() && g.getNumEdges() < (g.getNumNodes() - 1)) {
            // tem alguma coisa dando errado aqui depois da 1a iteração
            e = edges.get(i);
            if (!g.isReachableFrom(e.from, e.to)) {
                g.setEdge(e.from, e.to, e.weight);
            }
            i++;
        }

        return g;
    }
}
