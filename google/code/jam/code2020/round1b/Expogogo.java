package google.code.jam.code2020.round1b;

import java.util.Scanner;

/**
 * Created by Rene Argento on 03/04/20.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/000000000019fef2/00000000002d5b62
public class Expogogo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int targetX = scanner.nextInt();
            int targetY = scanner.nextInt();
            String moves = getMovesWithTarget(targetX, targetY);
            System.out.println("Case #" + t + ": " + moves);
        }
    }

    private static String getMovesWithTarget(long targetX, long targetY) {
        if ((targetX + targetY) % 2 == 0) {
            return "IMPOSSIBLE";
        }
        return getMoves(targetX, targetY);
    }

    private static String getMoves(long targetX, long targetY) {
        // Base cases
        if (targetX == 0 && targetY == 0) {
            return "";
        } else if (targetX == 1 && targetY == 0) {
            return "E";
        } else if (targetX == -1 && targetY == 0) {
            return "W";
        } else if (targetX == 0 && targetY == 1) {
            return "N";
        } else if (targetX == 0 && targetY == -1) {
            return "S";
        }

        char movement = ' ';

        if (Math.abs(targetX % 2) == 1) {
            if (Math.abs(((targetX + 1 + targetY) / 2)) % 2 == 1) {
                movement = 'W';
                targetX = (targetX + 1) / 2;
            } else {
                movement = 'E';
                targetX = (targetX - 1) / 2;
            }
            targetY /= 2;
        } else {
            if (Math.abs(((targetY + 1 + targetX) / 2)) % 2 == 1) {
                movement = 'S';
                targetY = (targetY + 1) / 2;
            } else {
                movement = 'N';
                targetY = (targetY - 1) / 2;
            }
            targetX /= 2;
        }
        return movement + getMoves(targetX, targetY);
    }

}
