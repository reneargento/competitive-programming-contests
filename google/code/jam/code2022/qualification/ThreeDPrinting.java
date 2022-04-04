package google.code.jam.code2022.qualification;

import java.util.Scanner;

/**
 * Created by Rene Argento on 02/04/22.
 */
public class ThreeDPrinting {

    private static class Color {
        int[] inkUsed;

        Color() {
            inkUsed = new int[4];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int[][] printers = new int[3][4];
            for (int i = 0; i < printers.length; i++) {
                for (int c = 0; c < printers[i].length; c++) {
                    printers[i][c] = scanner.nextInt();
                }
            }

            Color color = getColor(printers);
            System.out.printf("Case #%d: ", t);
            if (color == null) {
                System.out.println("IMPOSSIBLE");
            } else {
                System.out.printf("%d %d %d %d\n", color.inkUsed[0], color.inkUsed[1], color.inkUsed[2],
                        color.inkUsed[3]);
            }
        }
    }

    private static Color getColor(int[][] printers) {
        int targetInk = 1000000;
        int[] minimumAvailableInk = new int[4];
        for (int i = 0; i < minimumAvailableInk.length; i++) {
            minimumAvailableInk[i] = getMinimumInk(printers, i);
        }

        Color color = new Color();
        int totalUsedInk = 0;
        for (int i = 0; i < minimumAvailableInk.length; i++) {
            if (totalUsedInk < targetInk) {
                int inkNeeded = targetInk - totalUsedInk;
                int inkToUse = Math.min(inkNeeded, minimumAvailableInk[i]);
                totalUsedInk += inkToUse;
                color.inkUsed[i] = inkToUse;
            }
        }

        if (totalUsedInk != targetInk) {
            return null;
        }
        return color;
    }

    private static int getMinimumInk(int[][] printers, int index) {
        int minimumInk = Integer.MAX_VALUE;
        for (int[] printer : printers) {
            minimumInk = Math.min(minimumInk, printer[index]);
        }
        return minimumInk;
    }
}
