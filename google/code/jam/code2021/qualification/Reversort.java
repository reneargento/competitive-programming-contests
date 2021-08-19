package google.code.jam.code2021.qualification;

import java.util.Scanner;

/**
 * Created by Rene Argento on 26/03/21.
 */
public class Reversort {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int arrayLength = scanner.nextInt();
            int[] values = new int[arrayLength];

            for (int i = 0; i < values.length; i++) {
                values[i] = scanner.nextInt();
            }

            int cost = computeCost(values);
            System.out.printf("Case #%d: %d\n", t, cost);
        }
    }

    private static int computeCost(int[] values) {
        int cost = 0;

        for (int i = 0; i < values.length - 1; i++) {
            int minValue = values[i];
            int minValueIndex = i;

            for (int j = i; j < values.length; j++) {
                if (values[j] < minValue) {
                    minValue = values[j];
                    minValueIndex = j;
                }
            }
            cost += minValueIndex - i + 1;
            reverseValues(values, i, minValueIndex);
        }
        return cost;
    }

    private static void reverseValues(int[] values, int startIndex, int endIndex) {
        while (startIndex < endIndex) {
            int aux = values[startIndex];
            values[startIndex] = values[endIndex];
            values[endIndex] = aux;

            startIndex++;
            endIndex--;
        }
    }
}
