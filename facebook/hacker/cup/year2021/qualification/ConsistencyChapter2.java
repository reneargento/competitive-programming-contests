package facebook.hacker.cup.year2021.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 28/08/21.
 */
@SuppressWarnings("unchecked")
public class ConsistencyChapter2 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2021/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "consistency_chapter_2_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "consistency_chapter_2_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "consistency_chapter_2_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "consistency_chapter_2_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "consistency_chapter_2_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "consistency_chapter_2_output.txt";

    private static class BFSResult {
        Set<Integer> verticesReached;
        int seconds;

        public BFSResult(Set<Integer> verticesReached, int seconds) {
            this.verticesReached = verticesReached;
            this.seconds = seconds;
        }
    }

    public static long countChanges(String string, List<String> replacements) {
        Set<Integer> verticesSet = getVerticesSet(string);
        Map<Integer, Integer> frequencyMap = createFrequencyMap(string);
        Digraph digraph = createGraph(replacements);

        Set<Integer> allPossibleVertices = new HashSet<>(verticesSet);
        for (String replacement : replacements) {
            int vertex1 = replacement.charAt(0) - 'A';
            int vertex2 = replacement.charAt(1) - 'A';
            allPossibleVertices.add(vertex1);
            allPossibleVertices.add(vertex2);
        }

        Set<Integer> visited = new HashSet<>();
        long changes = Integer.MAX_VALUE;

        for (int vertex : allPossibleVertices) {
            if (visited.contains(vertex)) {
                continue;
            }

            BFSResult bfsResult = runBFS(digraph, vertex, frequencyMap);
            if (containsSet(bfsResult.verticesReached, verticesSet)) {
                changes = Math.min(changes, bfsResult.seconds);
            }
            visited.add(vertex);
        }

        if (changes == Integer.MAX_VALUE) {
            return -1;
        }
        return changes;
    }

    private static BFSResult runBFS(Digraph digraph, int vertex, Map<Integer, Integer> frequencyMap) {
        Set<Integer> verticesReached = new HashSet<>();
        verticesReached.add(vertex);
        int steps = 0;

        int[] distances = new int[digraph.vertices];
        distances[vertex] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(vertex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            if (digraph.adjacent[currentVertex] == null) {
                continue;
            }

            for (int neighbor : digraph.adjacent(currentVertex)) {
                if (!verticesReached.contains(neighbor)) {
                    queue.offer(neighbor);
                    distances[neighbor] = distances[currentVertex] + 1;
                    steps += frequencyMap.getOrDefault(neighbor, 0) * distances[neighbor];
                    verticesReached.add(neighbor);
                }
            }
        }
        return new BFSResult(verticesReached, steps);
    }

    private static Set<Integer> getVerticesSet(String string) {
        Set<Integer> verticesSet = new HashSet<>();
        for (char character : string.toCharArray()) {
            verticesSet.add(character - 'A');
        }
        return verticesSet;
    }

    private static Digraph createGraph(List<String> replacements) {
        Digraph digraph = new Digraph(30);

        for (String replacement : replacements) {
            int vertex1 = replacement.charAt(0) - 'A';
            int vertex2 = replacement.charAt(1) - 'A';
            digraph.addEdge(vertex2, vertex1);
        }
        return digraph;
    }

    private static Map<Integer, Integer> createFrequencyMap(String string) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (char character : string.toCharArray()) {
            int vertex = character - 'A';
            int frequency = frequencyMap.getOrDefault(vertex, 0);
            frequencyMap.put(vertex, frequency + 1);
        }
        return frequencyMap;
    }

    private static boolean containsSet(Set<Integer> set1, Set<Integer> set2) {
        for (int element : set2) {
            if (!set1.contains(element)) {
                return false;
            }
        }
        return true;
    }

    private static class Digraph {
        private final int vertices;
        private int edges;
        private final List<Integer>[] adjacent;

        public Digraph(int vertices) {
            this.vertices = vertices;
            this.edges = 0;

            adjacent = (ArrayList<Integer>[]) new ArrayList[vertices];

            for (int vertex = 0; vertex < vertices; vertex++) {
                adjacent[vertex] = new ArrayList<>();
            }
        }

        public int vertices() {
            return vertices;
        }

        public int edges() {
            return edges;
        }

        public void addEdge(int vertex1, int vertex2) {
            adjacent[vertex1].add(vertex2);
            edges++;
        }

        public Iterable<Integer> adjacent(int vertex) {
            return adjacent[vertex];
        }
    }

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static void test() {
        List<String> replacements1 = new ArrayList<>();
        replacements1.add("BA");
        replacements1.add("CA");
        long seconds1 = countChanges("ABC", replacements1);
        System.out.println("Seconds 1: " + seconds1 + " Expected: 2");

        List<String> replacements2 = new ArrayList<>();
        replacements2.add("AB");
        replacements2.add("AC");
        long seconds2 = countChanges("ABC", replacements2);
        System.out.println("Seconds 2: " + seconds2 + " Expected: -1");

        List<String> replacements3 = new ArrayList<>();
        long seconds3 = countChanges("F", replacements3);
        System.out.println("Seconds 3: " + seconds3 + " Expected: 0");

        List<String> replacements4 = new ArrayList<>();
        replacements4.add("AB");
        replacements4.add("AN");
        replacements4.add("BA");
        replacements4.add("NA");
        long seconds4 = countChanges("BANANA", replacements4);
        System.out.println("Seconds 4: " + seconds4 + " Expected: 3");

        List<String> replacements5 = new ArrayList<>();
        replacements5.add("FB");
        replacements5.add("BF");
        replacements5.add("HC");
        replacements5.add("CH");
        long seconds5 = countChanges("FBHC", replacements5);
        System.out.println("Seconds 5: " + seconds5 + " Expected: -1");

        List<String> replacements6 = new ArrayList<>();
        replacements6.add("NI");
        replacements6.add("OE");
        replacements6.add("NX");
        replacements6.add("EW");
        replacements6.add("OI");
        replacements6.add("FE");
        replacements6.add("FN");
        replacements6.add("XW");
        long seconds6 = countChanges("FOXEN", replacements6);
        System.out.println("Seconds 6: " + seconds6 + " Expected: 8");

        List<String> replacements7 = new ArrayList<>();
        replacements7.add("AB");
        replacements7.add("BC");
        replacements7.add("CD");
        replacements7.add("DE");
        replacements7.add("EF");
        replacements7.add("FG");
        replacements7.add("GH");
        replacements7.add("HI");
        replacements7.add("IJ");
        replacements7.add("JK");
        replacements7.add("KL");
        replacements7.add("LM");
        replacements7.add("MN");
        replacements7.add("NO");
        replacements7.add("OP");
        replacements7.add("PQ");
        replacements7.add("QR");
        replacements7.add("RS");
        replacements7.add("ST");
        replacements7.add("TU");
        replacements7.add("UV");
        replacements7.add("VW");
        replacements7.add("WX");
        replacements7.add("XY");
        replacements7.add("YZ");
        replacements7.add("ZA");
        long seconds7 = countChanges("CONSISTENCY", replacements7);
        System.out.println("Seconds 7: " + seconds7 + " Expected: 100");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String string = lines.get(i++);
            int replacementsNumber = Integer.parseInt(lines.get(i));
            if (replacementsNumber > 0) {
                i++;
            }
            List<String> replacements = new ArrayList<>();

            for (int j = 0; j < replacementsNumber; j++) {
                String replacement = lines.get(i);
                replacements.add(replacement);

                if (j != replacementsNumber - 1) {
                    i++;
                }
            }

            long seconds = countChanges(string, replacements);
            output.add("Case #" + caseId + ": " + seconds);
            caseId++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
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
}
