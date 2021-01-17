package uri.christmas.contest2020;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/12/20.
 */
// https://www.urionlinejudge.com.br/judge/pt/challenges/view/550/2
public class GruposDeTrabalhoDoNoel {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int elves = scanner.nextInt();
        scanner.nextLine();
        int[] totalTime = new int[4];

        for (int t = 0; t < elves; t++) {
            String[] data = scanner.nextLine().split(" ");
            int hours = Integer.parseInt(data[2]);
            switch (data[1]) {
                case "bonecos": totalTime[0] += hours; break;
                case "arquitetos": totalTime[1] += hours; break;
                case "musicos": totalTime[2] += hours; break;
                case "desenhistas": totalTime[3] += hours;
            }
        }

        int totalGifts = totalTime[0] / 8 + totalTime[1] / 4 + totalTime[2] / 6 + totalTime[3] / 12;
        System.out.println(totalGifts);
    }
}
