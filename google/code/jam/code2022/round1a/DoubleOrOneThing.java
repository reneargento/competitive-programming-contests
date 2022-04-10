package google.code.jam.code2022.round1a;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/04/22.
 */
public class DoubleOrOneThing {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            String string = FastReader.getLine();
            String firstAlphabeticalString = getFirstAlphabeticalString(string);
            outputWriter.printLine(String.format("Case #%d: %s", t, firstAlphabeticalString));
        }
        outputWriter.flush();
    }

    private static String getFirstAlphabeticalString(String string) {
        StringBuilder firstAlphabeticalString = new StringBuilder();
        for (int i = 0; i < string.length() - 1; i++) {
            char character = string.charAt(i);
            firstAlphabeticalString.append(character);

            Character nextDifferentCharacter = null;
            for (int j = i + 1; j < string.length(); j++) {
                if (string.charAt(j) != character) {
                    nextDifferentCharacter = string.charAt(j);
                    break;
                }
            }

            if (nextDifferentCharacter != null && nextDifferentCharacter > character) {
                firstAlphabeticalString.append(character);
            }
        }
        firstAlphabeticalString.append(string.charAt(string.length() - 1));
        return firstAlphabeticalString.toString();
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
