package google.code.jam.code2021.qualification;

import java.util.*;

/**
 * Created by Rene Argento on 26/03/21.
 */
public class ReversortEngineering {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int arrayLength = scanner.nextInt();
            int cost = scanner.nextInt();

            int minCost = arrayLength - 1;
            int maxCost = (arrayLength + 2) * arrayLength / 2;

            System.out.printf("Case #%d: ", t);

             if (cost < minCost || cost > maxCost) {
                 System.out.println("IMPOSSIBLE");
             } else {
                 int[] values = getArrangement(arrayLength, cost);
                 if (values == null) {
                     System.out.println("IMPOSSIBLE");
                 } else {
                     for (int i = 0; i < values.length; i++) {
                         System.out.print(values[i]);

                         if (i != values.length - 1) {
                             System.out.print(" ");
                         }
                     }
                     System.out.println();
                 }
             }
        }
    }

    private static int[] getArrangement(int arrayLength, int targetCost) {
        int requiredReverses = targetCost - (arrayLength - 1);
        int[] values = new int[arrayLength];

        int leftIndex = 0;
        int rightIndex = values.length - 1;

        int possibleReverses = arrayLength - 1;
        boolean shouldBeOnLeft = true;

        for (int currentNumber = 1; currentNumber <= arrayLength; currentNumber++) {
            if (requiredReverses != 0 && possibleReverses <= requiredReverses) {
                if (shouldBeOnLeft) {
                    values[rightIndex] = currentNumber;
                    rightIndex--;
                } else {
                    values[leftIndex] = currentNumber;
                    leftIndex++;
                }
                requiredReverses -= possibleReverses;
                shouldBeOnLeft = !shouldBeOnLeft;
            } else {
                if (shouldBeOnLeft) {
                    values[leftIndex] = currentNumber;
                    leftIndex++;
                } else {
                    values[rightIndex] = currentNumber;
                    rightIndex--;
                }
            }

            possibleReverses--;
        }

        int[] copyArray = new int[values.length];
        System.arraycopy(values, 0, copyArray, 0, copyArray.length);

        int actualCost = computeCost(copyArray);
        if (actualCost != targetCost) {
            return null;
        }
        return values;
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
