package google.code.jam.code2022.qualification;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 02/04/22.
 */
public class ChainReactions {

    private static class Vertex {
        int funFactor;
        List<Vertex> adjacencyList;

        public Vertex() {
            adjacencyList = new ArrayList<>();
        }
    }

    private static class ComputedFun {
        long fun;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        int tests = FastReader.nextInt();

        for (int t = 1; t <= tests; t++) {
            int nodes = FastReader.nextInt();
            boolean[] isPointedTo = new boolean[nodes];
            Vertex[] vertices = new Vertex[nodes];
            for (int i = 0; i < vertices.length; i++) {
                vertices[i] = new Vertex();
            }

            for (Vertex vertex : vertices) {
                vertex.funFactor = FastReader.nextInt();
            }

            for (int i = 0; i < vertices.length; i++) {
                Vertex vertex = vertices[i];
                int neighbor = FastReader.nextInt();

                if (neighbor != 0) {
                    vertices[neighbor - 1].adjacencyList.add(vertex);
                    isPointedTo[i] = true;
                }
            }

            long maxFun = computeMaxFun(vertices, isPointedTo);
            System.out.printf("Case #%d: %d\n", t, maxFun);
        }
        outputWriter.flush();
    }

    private static long computeMaxFun(Vertex[] vertices, boolean[] isPointedTo) {
        List<Integer> lastVertices = computeLastVertices(isPointedTo);
        ComputedFun computedFun = new ComputedFun();

        for (int lastVertex : lastVertices) {
            getFun(vertices[lastVertex], true, computedFun);
        }
        return computedFun.fun;
    }

    private static List<Integer> computeLastVertices(boolean[] isPointedTo) {
        List<Integer> lastVertices = new ArrayList<>();
        for (int i = 0; i < isPointedTo.length; i++) {
            if (!isPointedTo[i]) {
                lastVertices.add(i);
            }
        }
        return lastVertices;
    }

    private static long getFun(Vertex vertex, boolean isRoot, ComputedFun computedFun) {
        long minChildrenFun = Long.MAX_VALUE;
        List<Long> funFromChildren = new ArrayList<>();

        for (Vertex neighbor : vertex.adjacencyList) {
            long funFromChild = getFun(neighbor, false, computedFun);
            funFromChildren.add(funFromChild);
            minChildrenFun = Math.min(minChildrenFun, funFromChild);
        }
        Collections.sort(funFromChildren);

        for (int i = 1; i < funFromChildren.size(); i++) {
            computedFun.fun += funFromChildren.get(i);
        }

        if (isRoot) {
            if (funFromChildren.isEmpty()) {
                computedFun.fun += vertex.funFactor;
            } else {
                computedFun.fun += Math.max(vertex.funFactor, funFromChildren.get(0));
            }
        }

        if (funFromChildren.isEmpty()) {
            return vertex.funFactor;
        } else {
            return Math.max(vertex.funFactor, minChildrenFun);
        }
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

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }
    }
}
