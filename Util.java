import java.util.ArrayList;

public class Util {

    private static void getCombs(ArrayList<int[]> combinations, int[] data, int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            getCombs(combinations, data, start + 1, end, index + 1);
            getCombs(combinations, data, start + 1, end, index);
        }
    }
    public static ArrayList<int[]> generateCombinatios(int n, int r) {    
        ArrayList<int[]> combinations = new ArrayList<int[]>();
        getCombs(combinations, new int[r], 0, n-1, 0);
        return combinations;
    }
}
