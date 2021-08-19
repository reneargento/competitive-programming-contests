package google.code.jam.code2021.round1c;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 26/03/21.
 */
public class ClosestPick {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int ticketsNumber = scanner.nextInt();
            int limit = scanner.nextInt();

            int[] tickets = new int[ticketsNumber + 2];
            tickets[0] = 0;
            tickets[1] = limit + 1;
            for (int i = 0; i < ticketsNumber; i++) {
                tickets[i + 2] = scanner.nextInt();
            }

            double maxProbability = getHighestProbability(tickets, limit);
            System.out.printf("Case #%d: %.6f\n", t, maxProbability);
        }
    }

    private static double getHighestProbability(int[] tickets, int limit) {
        Arrays.sort(tickets);
        long maxUncoveredRange1 = 0;
        long maxUncoveredRange2 = 0;

        for (int i = 1; i < tickets.length; i++) {
            int uncoveredRange = Math.max(tickets[i] - tickets[i - 1] - 1, 0);
            int complementRange = 0;

            if (i != 1 && i != tickets.length - 1) {
                if (uncoveredRange % 2 == 0) {
                    uncoveredRange /= 2;
                    complementRange = uncoveredRange;
                } else {
                    int covered = (uncoveredRange / 2) + 1;
                    complementRange = uncoveredRange - covered;
                    uncoveredRange = covered;
                }
            }

            if (uncoveredRange > maxUncoveredRange1) {
                if (maxUncoveredRange1 > maxUncoveredRange2) {
                    maxUncoveredRange2 = maxUncoveredRange1;
                }

                maxUncoveredRange1 = uncoveredRange;
                if (complementRange > maxUncoveredRange2) {
                    maxUncoveredRange2 = complementRange;
                }
            } else if (uncoveredRange > maxUncoveredRange2) {
                maxUncoveredRange2 = uncoveredRange;
            }
        }
        return (maxUncoveredRange1 + maxUncoveredRange2) / (double) limit;
    }
}
