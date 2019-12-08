package facebook.hacker.cup.year2019.round1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 29/06/19.
 */
// https://www.facebook.com/hackercup/problem/862237970786911/
public class GraphsAsAService {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2019/Round 1/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "graphs_service_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "graphs_service_sample_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "graphs_service_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "graphs_service_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static void test() {
        Edge[] edges1 = new Edge[1];
        edges1[0] = new Edge(2, 0, 5);
        EdgeWeightedGraph edgeWeightedGraph1 = buildGraph(3, edges1);
        boolean satisfyRequirements1 = satisfyRequirements(edgeWeightedGraph1, edges1);
        if (satisfyRequirements1) {
            printGraph(edgeWeightedGraph1);
        } else {
            System.out.println("Impossible");
        }
        System.out.println("Expected: 1 3 5");
        System.out.println();

        Edge[] edges2 = new Edge[3];
        edges2[0] = new Edge(0, 1, 1);
        edges2[1] = new Edge(1, 2, 1);
        edges2[2] = new Edge(0, 2, 100);
        EdgeWeightedGraph edgeWeightedGraph2 = buildGraph(3, edges2);
        boolean satisfyRequirements2 = satisfyRequirements(edgeWeightedGraph2, edges2);
        if (satisfyRequirements2) {
            printGraph(edgeWeightedGraph2);
        } else {
            System.out.println("Impossible");
        }
        System.out.println("Expected: Impossible");
        System.out.println();

        Edge[] edges3 = new Edge[3];
        edges3[0] = new Edge(0, 3, 5);
        edges3[1] = new Edge(1, 2, 5);
        edges3[2] = new Edge(3, 1, 4);
        EdgeWeightedGraph edgeWeightedGraph3 = buildGraph(4, edges3);
        boolean satisfyRequirements3 = satisfyRequirements(edgeWeightedGraph3, edges3);
        if (satisfyRequirements3) {
            printGraph(edgeWeightedGraph3);
        } else {
            System.out.println("Impossible");
        }
        System.out.println("Expected:\n" +
                "1 4 5\n" +
                "2 3 5\n" +
                "2 4 4");
        System.out.println();

        Edge[] edges4 = new Edge[10];
        edges4[0] = new Edge(1, 5, 16);
        edges4[1] = new Edge(6, 2, 6);
        edges4[2] = new Edge(4, 0, 11);
        edges4[3] = new Edge(6, 5, 12);
        edges4[4] = new Edge(7, 0, 8);
        edges4[5] = new Edge(5, 0, 18);
        edges4[6] = new Edge(0, 2, 12);
        edges4[7] = new Edge(2, 4, 9);
        edges4[8] = new Edge(5, 4, 15);
        edges4[9] = new Edge(6, 0, 6);
        EdgeWeightedGraph edgeWeightedGraph4 = buildGraph(8, edges4);
        boolean satisfyRequirements4 = satisfyRequirements(edgeWeightedGraph4, edges4);
        if (satisfyRequirements4) {
            printGraph(edgeWeightedGraph4);
        } else {
            System.out.println("Impossible");
        }
        System.out.println("Expected:\n" +
                "1 5 11\n" +
                "1 8 8\n" +
                "1 6 18\n" +
                "1 3 12\n" +
                "1 7 6\n" +
                "2 6 16\n" +
                "3 7 6\n" +
                "3 5 9\n" +
                "5 6 15\n" +
                "6 7 12");
        System.out.println();

        Edge[] edges5 = new Edge[10];
        edges5[0] = new Edge(5, 6, 7);
        edges5[1] = new Edge(7, 4, 3);
        edges5[2] = new Edge(2, 6, 7);
        edges5[3] = new Edge(9, 3, 24);
        edges5[4] = new Edge(5, 1, 8);
        edges5[5] = new Edge(7, 6, 19);
        edges5[6] = new Edge(2, 8, 22);
        edges5[7] = new Edge(4, 6, 17);
        edges5[8] = new Edge(8, 1, 12);
        edges5[9] = new Edge(1, 6, 2);
        EdgeWeightedGraph edgeWeightedGraph5 = buildGraph(10, edges5);
        boolean satisfyRequirements5 = satisfyRequirements(edgeWeightedGraph5, edges5);
        if (satisfyRequirements5) {
            printGraph(edgeWeightedGraph5);
        } else {
            System.out.println("Impossible");
        }
        System.out.println("Expected: Impossible");
    }

    private static void printGraph(EdgeWeightedGraph edgeWeightedGraph) {
        for (List<Edge> adjacent : edgeWeightedGraph.adjacent) {
            for (Edge edge : adjacent) {
                if (edge.vertex1 < edge.vertex2) {
                    System.out.println((edge.vertex1 + 1) + " " + (edge.vertex2 + 1) + " " + edge.weight);
                }
            }
        }
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] input = lines.get(i).split(" ");
            int vertices = Integer.parseInt(input[0]);
            int constraintsNumber = Integer.parseInt(input[1]);

            Edge[] requirementEdges = new Edge[constraintsNumber];

            for (int c = 0; c < constraintsNumber; c++) {
                i++;
                String[] constraint = lines.get(i).split(" ");
                int vertex1 = Integer.parseInt(constraint[0]) - 1;
                int vertex2 = Integer.parseInt(constraint[1]) - 1;
                int distance = Integer.parseInt(constraint[2]);
                requirementEdges[c] = new Edge(vertex1, vertex2, distance);
            }

            EdgeWeightedGraph edgeWeightedGraph = buildGraph(vertices, requirementEdges);
            boolean satisfyRequirements = satisfyRequirements(edgeWeightedGraph, requirementEdges);

            StringBuilder currentOutput = new StringBuilder("Case #" + caseId + ": ");

            if (!satisfyRequirements) {
                currentOutput.append("Impossible");
            } else {
                currentOutput.append(edgeWeightedGraph.edges);
                for (List<Edge> adjacent : edgeWeightedGraph.adjacent) {
                    for (Edge edge : adjacent) {
                        if (edge.vertex1 < edge.vertex2) {
                            currentOutput.append("\n").append(edge.vertex1 + 1).append(" ")
                                    .append(edge.vertex2 + 1).append(" ").append(edge.weight);
                        }
                    }
                }
            }
            output.add(currentOutput.toString());
            caseId++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static EdgeWeightedGraph buildGraph(int vertices, Edge[] edges) {
        EdgeWeightedGraph edgeWeightedGraph = new EdgeWeightedGraph(vertices);
        for (Edge edge : edges) {
            edgeWeightedGraph.addEdge(edge.vertex1, edge.vertex2, edge.weight);
        }
        return edgeWeightedGraph;
    }

    private static boolean satisfyRequirements(EdgeWeightedGraph edgeWeightedGraph, Edge[] requirementEdges) {
        int[][] shortestDistances = floydWarshall(edgeWeightedGraph);
        for (Edge edge : requirementEdges) {
            int vertex1 = edge.vertex1;
            int vertex2 = edge.vertex2;
            if (shortestDistances[vertex1][vertex2] < edge.weight()) {
                return false;
            }
        }
        return true;
    }

    private static int[][] floydWarshall(EdgeWeightedGraph edgeWeightedGraph) {
        int vertices = edgeWeightedGraph.vertices;
        int[][] distances = new int[vertices][vertices];

        for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                if (vertex1 != vertex2) {
                    distances[vertex1][vertex2] = 100000000;
                }
            }
        }

        for (int vertex = 0; vertex < edgeWeightedGraph.vertices; vertex++) {
            if (edgeWeightedGraph.adjacent[vertex] != null) {
                for (Edge edge : edgeWeightedGraph.adjacent[vertex]) {
                    int vertex1 = edge.vertex1;
                    int vertex2 = edge.vertex2;
                    distances[vertex1][vertex2] = (int) edge.weight;
                    distances[vertex2][vertex1] = (int) edge.weight;
                }
            }
        }

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                    }
                }
            }
        }
        return distances;
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private static void writeDataOnFile(String file, List<String> data){
        for(String line : data) {
            writeFileOutput(file, line + "\n");
        }
    }

    private static void writeFileOutput(String file, String data){
        byte[] dataBytes = data.getBytes();

        try {
            Files.write(Paths.get(file), dataBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Edge {
        private final int vertex1;
        private final int vertex2;
        private final long weight;

        public Edge(int vertex1, int vertex2, long weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public double weight() {
            return weight;
        }

        public int either() {
            return vertex1;
        }

        public int other(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            } else if (vertex == vertex2) {
                return vertex1;
            } else {
                throw new RuntimeException("Inconsistent edge");
            }
        }
    }

    private static class EdgeWeightedGraph {
        private final int vertices;
        private int edges;
        private List<Edge>[] adjacent;

        public EdgeWeightedGraph(int vertices) {
            this.vertices = vertices;
            adjacent = (List<Edge>[]) new List[vertices];

            for(int vertex = 0; vertex < vertices; vertex++) {
                adjacent[vertex] = new ArrayList<>();
            }
        }

        public int vertices() {
            return vertices;
        }

        public int edgesCount() {
            return edges;
        }

        public void addEdge(int vertex1, int vertex2, long distance) {
            Edge edge1 = new Edge(vertex1, vertex2, distance);
            Edge edge2 = new Edge(vertex2, vertex1, distance);

            adjacent[vertex1].add(edge1);
            adjacent[vertex2].add(edge2);
            edges++;
        }

        public Iterable<Edge> adjacent(int vertex) {
            return adjacent[vertex];
        }
    }
}
