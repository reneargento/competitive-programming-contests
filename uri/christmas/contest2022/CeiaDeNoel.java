package uri.christmas.contest2022;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 25/12/22.
 */
public class CeiaDeNoel {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        String line = FastReader.getLine();
        Set<String> ingredients = new HashSet<>();

        while (line != null) {
            int quantity = Integer.parseInt(line);
            for (int i = 0; i < quantity; i++) {
                line = FastReader.getLine();
                String[] data = line.split(" ");
                ingredients.add(data[0]);
            }
            line = FastReader.getLine();
        }

        List<String> sortedIngredients = new ArrayList<>(ingredients);
        Collections.sort(sortedIngredients);
        for (String ingredient : sortedIngredients) {
            outputWriter.printLine(ingredient);
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
