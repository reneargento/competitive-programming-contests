package uri.christmas.contest2020;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 19/12/20.
 */
// https://www.urionlinejudge.com.br/judge/pt/challenges/view/550/4
public class OsPresentesDoNoel {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int kids = scanner.nextInt();
        int[] goodDeeds = new int[kids];

        for (int t = 0; t < kids; t++) {
            goodDeeds[t] = scanner.nextInt();
        }

        Arrays.sort(goodDeeds);
        int totalGifts = 0;
        int gifts = 1;

        for (int i = 0; i < goodDeeds.length; i++) {
            if (i == 0) {
                totalGifts += gifts;
            } else {
                if (goodDeeds[i] != goodDeeds[i - 1]) {
                    gifts++;
                }
                totalGifts += gifts;
            }
        }
        System.out.println(totalGifts);
    }
}
