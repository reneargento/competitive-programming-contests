package uri.christmas.contest2022;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/22.
 */
public class EnfeitandoASala {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int magazines = FastReader.nextInt();
        char[] counts = new char[256];

        for (int i = 0; i < magazines; i++) {
            int lines = FastReader.nextInt();
            for (int line = 0; line < lines; line++) {
                String characters = FastReader.getLine().toUpperCase();
                for (char character : characters.toCharArray()) {
                    counts[character]++;
                }
            }
        }

        String target = "MERY CHISTA";
        int totalMessageCount = Integer.MAX_VALUE;
        for (char character : target.toCharArray()) {
            int count = counts[character];
            if (character == 'R') {
                count /= 3;
            }
            if (character == 'S' || character == 'M') {
                count /= 2;
            }
            totalMessageCount = Math.min(totalMessageCount, count);
        }
        outputWriter.printLine(totalMessageCount);
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
