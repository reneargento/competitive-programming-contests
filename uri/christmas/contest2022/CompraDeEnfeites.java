package uri.christmas.contest2022;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/22.
 */
public class CompraDeEnfeites {

    private static class Ornament implements Comparable<Ornament> {
        String name;
        int price;
        int approval;

        public Ornament(String name, int price, int approval) {
            this.name = name;
            this.price = price;
            this.approval = approval;
        }

        @Override
        public int compareTo(Ornament other) {
            if (price != other.price) {
                return Integer.compare(price, other.price);
            }
            return Integer.compare(other.approval, approval);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Ornament[] ornaments = new Ornament[FastReader.nextInt()];
        int budget = FastReader.nextInt();

        for (int i = 0; i < ornaments.length; i++) {
            ornaments[i] = new Ornament(FastReader.next(), FastReader.nextInt(), FastReader.nextInt());
        }
        Arrays.sort(ornaments);

        int moneySpent = 0;
        for (Ornament ornament : ornaments) {
            moneySpent += ornament.price;
            if (moneySpent <= budget) {
                outputWriter.printLine(ornament.name);
            } else {
                break;
            }
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
