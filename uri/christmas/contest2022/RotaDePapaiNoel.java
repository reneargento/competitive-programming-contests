package uri.christmas.contest2022;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/22.
 */
public class RotaDePapaiNoel {

    private static class Kid implements Comparable<Kid> {
        String name;
        String region;
        int distance;

        public Kid(String name, String region, int distance) {
            this.name = name;
            this.region = region;
            this.distance = distance;
        }

        @Override
        public int compareTo(Kid other) {
            if (!region.equals(other.region)) {
                return region.compareTo(other.region);
            }
            if (distance != other.distance) {
                return Integer.compare(distance, other.distance);
            }
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        Kid[] kids = new Kid[FastReader.nextInt()];

        for (int i = 0; i < kids.length; i++) {
            kids[i] = new Kid(FastReader.next(), FastReader.next(), FastReader.nextInt());
        }
        Arrays.sort(kids);

        for (Kid kid : kids) {
            outputWriter.printLine(kid.name);
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
