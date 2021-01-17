package uri.christmas.contest2020;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/12/20.
 */
// https://www.urionlinejudge.com.br/judge/pt/challenges/view/550/1
public class BolinhasDeNatal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int balls = scanner.nextInt();
        int target = scanner.nextInt() / 2;

        if (balls >= target) {
            System.out.println("Amelia tem todas bolinhas!");
        } else {
            int missing = target - balls;
            System.out.printf("Faltam %d bolinha(s)\n", missing);
        }
    }
}
