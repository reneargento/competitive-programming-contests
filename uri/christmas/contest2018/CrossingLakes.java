package uri.christmas.contest2018;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/12/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/412/3
public class CrossingLakes {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int linesCount = scanner.nextInt();
        scanner.nextLine();

        String[] lines = new String[linesCount];

        for (int i = 0; i < linesCount; i++) {
            lines[i] = scanner.nextLine();
        }

        int jumps = 0;
        boolean possible = true;

        for (int i = 0; i < linesCount; i++) {
            if (i + 1 < linesCount && lines[i + 1].charAt(0) == '.') {
                jumps++;

                i++;
                if (i + 1 < linesCount && lines[i + 1].charAt(0) == '.') {
                    i++;
                } if (i + 1 < linesCount && lines[i + 1].charAt(0) == '.') {
                    possible = false;
                    break;
                }
            }
        }

        if (possible) {
            System.out.println(jumps);
        } else {
            System.out.println("N");
        }
    }

}