package movile.code.challenge3;

import java.util.*;

/**
 * Created by Rene Argento on 29/03/21.
 */
public class QuantidadePossivelDeSaquesMovilepay {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int targetExchange = scanner.nextInt();
        if (targetExchange <= 0 || targetExchange > 1000) {
            System.out.println("-1");
        } else {
            scanner.nextLine();
            String[] valuesString = scanner.nextLine().split(" ");
            Set<Integer> uniqueValues = new HashSet<>();
            for (int i = 0; i < valuesString.length; i++) {
                uniqueValues.add(Integer.parseInt(valuesString[i]));
            }

            int[] values = new int[uniqueValues.size()];
            int index = 0;
            for (int value : uniqueValues) {
                values[index++] = value;
            }

            long possibilities = calculateCoinChangePossibilities(values, targetExchange);
            System.out.println(possibilities);
        }
    }

    private static long calculateCoinChangePossibilities(int[] coins, int targetExchange) {
        long[] dp = new long[targetExchange + 1];

        // Base case - for 0 exchange, there is 1 solution (no coins)
        dp[0] = 1;

        for (int i = 0; i < coins.length; i++) {
            for(int j = coins[i]; j <= targetExchange; j++) {
                dp[j] += dp[j - coins[i]];
            }
        }
        return dp[targetExchange];
    }
}
