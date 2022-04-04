package google.code.jam.code2022.qualification;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 02/04/22.
 */
public class d1000000 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[] sides = new int[scanner.nextInt()];
            for (int i = 0; i < sides.length; i++) {
                sides[i] = scanner.nextInt();
            }

            int longestDiceLength = getLongestDiceLength(sides);
            System.out.printf("Case #%d: %d\n", t, longestDiceLength);
        }
    }

    private static int getLongestDiceLength(int[] sides) {
        Arrays.sort(sides);
        int current = 0;

        for (int side : sides) {
            if (current < side) {
                current++;
            }
        }
        return current;
    }
}
