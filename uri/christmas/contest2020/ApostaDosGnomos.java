package uri.christmas.contest2020;

import java.util.Scanner;

/**
 * Created by Rene Argento on 19/12/20.
 */
// https://www.urionlinejudge.com.br/judge/pt/challenges/view/550/7
public class ApostaDosGnomos {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int gifts = scanner.nextInt();
            int bet = scanner.nextInt();

            double probabilityError = scanner.nextInt() / 100.0;
            double probability = binomial(gifts, bet, probabilityError) * 100;

            System.out.printf("A chance de Basy acertar o numero no dia %d eh %.2f", t, probability);
            System.out.println("%");
        }
    }

    public static double binomial(int N, int k, double p) {
        double[][] binomial = new double[N+1][k+1];

        // base cases
        for (int i = 0; i <= N; i++) {
            binomial[i][0] = Math.pow(1.0 - p, i);
        }
        binomial[0][0] = 1.0;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= k; j++) {
                binomial[i][j] = p * binomial[i-1][j-1] + (1.0 - p) * binomial[i-1][j];
            }
        }
        return binomial[N][k];
    }
}
