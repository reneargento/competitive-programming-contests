package google.code.jam.code2022.qualification;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/04/22.
 */
public class PunchedCards {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int rows = FastReader.nextInt();
            int columns = FastReader.nextInt();
            outputWriter.printLine(String.format("Case #%d:", t));
            printPunchedCard(rows, columns, outputWriter);
        }
        outputWriter.flush();
    }

    private static void printPunchedCard(int rows, int columns, OutputWriter outputWriter) {
        // First line
        outputWriter.print("..+");
        for (int i = 0; i < columns - 1; i++) {
            outputWriter.print("-+");
        }
        outputWriter.printLine();

        // Second line
        outputWriter.print(".");
        for (int i = 0; i < columns; i++) {
            outputWriter.print(".|");
        }
        outputWriter.printLine();

        // Other lines
        printSeparator(columns, outputWriter);
        for (int i = 0; i < rows - 1; i++) {
            printRow(columns, outputWriter);
            printSeparator(columns, outputWriter);
        }
    }

    private static void printSeparator(int columns, OutputWriter outputWriter) {
        outputWriter.print("+");
        for (int i = 0; i < columns; i++) {
            outputWriter.print("-+");
        }
        outputWriter.printLine();
    }

    private static void printRow(int columns, OutputWriter outputWriter) {
        outputWriter.print("|");
        for (int i = 0; i < columns; i++) {
            outputWriter.print(".|");
        }
        outputWriter.printLine();
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
