package google.code.jam.code2022.round1b;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/04/22.
 */
public class ControlledInflation {

    private static class CustomerProduct {
        int minPressure;
        int maxPressure;

        public CustomerProduct(int minPressure, int maxPressure) {
            this.minPressure = minPressure;
            this.maxPressure = maxPressure;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int customers = FastReader.nextInt();
            int products = FastReader.nextInt();

            CustomerProduct[] customerProducts = new CustomerProduct[customers];

            for (int i = 0; i < customerProducts.length; i++) {
                int minPressure = Integer.MAX_VALUE;
                int maxPressure = Integer.MIN_VALUE;

                for (int j = 0; j < products; j++) {
                    int pressureTarget = FastReader.nextInt();
                    minPressure = Math.min(minPressure, pressureTarget);
                    maxPressure = Math.max(maxPressure, pressureTarget);
                }
                customerProducts[i] = new CustomerProduct(minPressure, maxPressure);
            }
            long payingCustomers = countButtonPresses(customerProducts);
            outputWriter.printLine(String.format("Case #%d: %d", t, payingCustomers));
            outputWriter.flush();
        }
    }

    private static long countButtonPresses(CustomerProduct[] customerProducts) {
        long[][] dp = new long[customerProducts.length + 1][2];
        long lastPressureAscending = 0;
        long lastPressureDescending = 0;

        for (int i = 1; i < dp.length; i++) {
            CustomerProduct customerProduct = customerProducts[i - 1];
            long maxDifference = customerProduct.maxPressure - customerProduct.minPressure;
            // Ascending pressure
            long ascending1 = dp[i - 1][0] + Math.abs(lastPressureAscending - customerProduct.minPressure)
                    + maxDifference;
            long ascending2 = dp[i - 1][1] + Math.abs(lastPressureDescending - customerProduct.minPressure)
                    + maxDifference;
            dp[i][0] = Math.min(ascending1, ascending2);

            // Descending pressure
            long descending1 = dp[i - 1][0] + Math.abs(lastPressureAscending - customerProduct.maxPressure)
                    + maxDifference;
            long descending2 = dp[i - 1][1] + Math.abs(lastPressureDescending - customerProduct.maxPressure)
                    + maxDifference;
            dp[i][1] = Math.min(descending1, descending2);

            lastPressureAscending = customerProduct.maxPressure;
            lastPressureDescending = customerProduct.minPressure;
        }
        return Math.min(dp[dp.length - 1][0], dp[dp.length - 1][1]);
    }

    private static  class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
