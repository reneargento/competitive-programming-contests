package google.code.jam.code2022.round1a;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 02/04/22.
 */
public class EqualSum {

    private static class Sum {
        long value;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();
        List<Long> powersOf2 = getPowersOf2();

        for (int t = 1; t <= tests; t++) {
            int numbers = FastReader.nextInt();
            if (numbers == -1) {
                return;
            }
            Sum set1Sum = new Sum();
            Sum set2Sum = new Sum();
            List<Long> numbersList = new ArrayList<>();

            // Print numbers
            printNumbers(powersOf2, numbers, set1Sum, set2Sum, numbersList, outputWriter);

            // Read numbers
            for (int i = 0; i < numbers; i++) {
                long number = FastReader.nextInt();
                if (number == -1) {
                    return;
                }

                if (set1Sum.value <= set2Sum.value) {
                    numbersList.add(number);
                    set1Sum.value += number;
                } else {
                    set2Sum.value += number;
                }
            }

            for (int i = powersOf2.size() - 1; i >= 0; i--) {
                long powerOf2 = powersOf2.get(i);
                if (set1Sum.value <= set2Sum.value) {
                    set1Sum.value += powerOf2;
                    numbersList.add(powerOf2);
                } else {
                    set2Sum.value += powerOf2;
                }
            }

            outputWriter.print(String.format("%d", numbersList.get(0)));
            for (int i = 1; i < numbersList.size(); i++) {
                outputWriter.print(String.format(" %d", numbersList.get(i)));
            }
            outputWriter.printLine();
            outputWriter.flush();
        }
    }

    private static void printNumbers(List<Long> powersOf2, int numbers, Sum set1Sum, Sum set2Sum,
                                     List<Long> numbersList, OutputWriter outputWriter) {
        StringJoiner line = new StringJoiner(" ");

        for (long powerOf2 : powersOf2) {
            line.add(String.valueOf(powerOf2));
        }

        Set<Long> powersOf2Set = new HashSet<>(powersOf2);
        long number = 3;
        for (int i = powersOf2.size(); i < numbers; i++) {
            while (powersOf2Set.contains(number)) {
                number++;
            }
            line.add(String.valueOf(number));

            if (set1Sum.value <= set2Sum.value) {
                numbersList.add(number);
                set1Sum.value += number;
            } else {
                set2Sum.value += number;
            }
            number++;
        }
        outputWriter.printLine(line.toString());
        outputWriter.flush();
    }

    private static List<Long> getPowersOf2() {
        List<Long> powersOf2 = new ArrayList<>();
        powersOf2.add(1L);
        long powerOf2 = 2;

        for (int i = 0; i < 29; i++) {
            powersOf2.add(powerOf2);
            powerOf2 *= 2;
        }
        return powersOf2;
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
