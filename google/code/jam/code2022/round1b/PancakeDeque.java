package google.code.jam.code2022.round1b;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/04/22.
 */
public class PancakeDeque {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int pancakesNumber = FastReader.nextInt();
            Deque<Integer> pancakes = new ArrayDeque<>();

            for (int i = 0; i < pancakesNumber; i++) {
                pancakes.addLast(FastReader.nextInt());
            }
            int payingCustomers = countPayingCustomers(pancakes);
            outputWriter.printLine(String.format("Case #%d: %d", t, payingCustomers));
            outputWriter.flush();
        }
    }

    private static int countPayingCustomers(Deque<Integer> pancakes) {
        int payingCustomers = 0;
        int highestDeliciousnessLevel = 0;

        while (!pancakes.isEmpty()) {
            int highestLevel;

            if (pancakes.size() == 1) {
                highestLevel = pancakes.peek();
                pancakes.removeLast();
            } else {
                int leftLevel = pancakes.getFirst();
                int rightLevel = pancakes.getLast();

                if (leftLevel <= rightLevel) {
                    highestLevel = leftLevel;
                    pancakes.removeFirst();
                } else {
                    highestLevel = rightLevel;
                    pancakes.removeLast();
                }
            }

            if (highestLevel >= highestDeliciousnessLevel) {
                payingCustomers++;
            }
            highestDeliciousnessLevel = Math.max(highestDeliciousnessLevel, highestLevel);
        }
        return payingCustomers;
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
