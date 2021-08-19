package google.code.jam.code2021.qualification;

import java.util.Scanner;

/**
 * Created by Rene Argento on 26/03/21.
 */
// Based on https://stackoverflow.com/questions/67240898/codejam-2021-qualifier-round-moons-and-umbrellas-algorithm-explanation
public class MoonsAndUmbrellas {

    private static final int C = 0;
    private static final int J = 1;
    private static final int INFINITE = 1000000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int cjCost = scanner.nextInt();
            int jcCost = scanner.nextInt();
            String mural = scanner.next();

            long minimumCost = computeCost(mural, cjCost, jcCost);
            System.out.printf("Case #%d: %d\n", t, minimumCost);
        }
    }

    private static long computeCost(String mural, int cjCost, int jcCost) {
        long[] costs = new long[2];
        Character previous = null;

        for (char symbol : mural.toCharArray()) {
            long[] newCosts = new long[2];
            computeCost(symbol, costs, newCosts, C, J, 'C', 'J', jcCost, previous);
            computeCost(symbol, costs, newCosts, J, C, 'J', 'C', cjCost, previous);
            costs = newCosts;
            previous = symbol;
        }
        return Math.min(costs[C], costs[J]);
    }

    private static void computeCost(char symbol, long[] costs, long[] newCosts, int costIndex1, int costIndex2,
                                    char symbol1, char symbol2, int cost, Character previous) {
        if (symbol == symbol2) {
            newCosts[costIndex1] = INFINITE;
        } else if (previous == null) {
            newCosts[costIndex1] = 0;
        } else if (previous == symbol1) {
            newCosts[costIndex1] = costs[costIndex1];
        } else if (previous == symbol2) {
            newCosts[costIndex1] = costs[costIndex2] + cost;
        } else if (previous == '?') {
            newCosts[costIndex1] = Math.min(costs[costIndex1], costs[costIndex2] + cost);
        }
    }
}
