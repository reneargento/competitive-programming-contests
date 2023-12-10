package uri.christmas.contest2022;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/22.
 */
public class UmJogoND {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int rounds = FastReader.nextInt();

        long[] sums = new long[1000001];
        for (int i = 1; i < sums.length; i++) {
            sums[i] = sums[i - 1] + i;
        }

        for (int i = 0; i < rounds; i++) {
            int dimension = FastReader.nextInt();
            int dimensionSquared = dimension * dimension;
            long result = dimensionSquared * sums[dimensionSquared];
            outputWriter.printLine(result);
        }
        outputWriter.flush();
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static String getLine() throws IOException {
            return reader.readLine();
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

        public void flush() {
            writer.flush();
        }
    }
}
