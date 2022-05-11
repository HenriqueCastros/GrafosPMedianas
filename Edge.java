import javax.print.attribute.standard.MediaSize.Other;

public class Edge implements Comparable<Edge>{
    public int from;
    public int to;
    public int weight;

    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "( " + from + " , " + to + " ) -> " + weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}