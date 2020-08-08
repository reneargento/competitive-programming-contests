package facebook.hacker.cup.year2020.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 29/06/19.
 */
// https://www.facebook.com/codingcompetitions/hacker-cup/2020/qualification-round/problems/A
public class TravelRestrictions {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2020/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "travel_restrictions_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "travel_restrictions_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "travel_restrictions_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "travel_restrictions_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "travel_restrictions_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "travel_restrictions_output.txt";

    private static class Digraph {
        private final int vertices;
        private int edges;
        private List<Integer>[] adjacent;
        private int[] indegrees;
        private int[] outdegrees;

        public Digraph(int vertices) {
            this.vertices = vertices;
            this.edges = 0;

            indegrees = new int[vertices];
            outdegrees = new int[vertices];

            adjacent = (ArrayList<Integer>[]) new ArrayList[vertices];

            for(int vertex = 0; vertex < vertices; vertex++) {
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

            outdegrees[vertex1]++;
            indegrees[vertex2]++;
        }

        public List<Integer>[] getAdjacencyList() {
            return adjacent;
        }

        public Iterable<Integer> adjacent(int vertex) {
            return adjacent[vertex];
        }

        public int indegree(int vertex) {
            return indegrees[vertex];
        }

        public int outdegree(int vertex) {
            return outdegrees[vertex];
        }

        public Digraph reverse() {
            Digraph reverse = new Digraph(vertices);

            for(int vertex = 0; vertex < vertices; vertex++) {
                for(int neighbor : adjacent(vertex)) {
                    reverse.addEdge(neighbor, vertex);
                }
            }
            return reverse;
        }
    }

    public static String getTrips(int countries, String incomingRestrictions, String outgoingRestrictions) {
        Digraph digraph = new Digraph(countries);

        for (int i = 1; i < countries; i++) {
            if (incomingRestrictions.charAt(i) == 'Y' && outgoingRestrictions.charAt(i - 1) == 'Y') {
                digraph.addEdge(i - 1, i);
            }
            if (incomingRestrictions.charAt(i - 1) == 'Y' && outgoingRestrictions.charAt(i) == 'Y') {
                digraph.addEdge(i, i - 1);
            }
        }

        StringBuilder trips = new StringBuilder();
        for (int country1 = 0; country1 < countries; country1++) {
            Set<Integer> countriesReached = getCountriesReached(digraph, country1);

            for (int country2 = 0; country2 < countries; country2++) {
                if (countriesReached.contains(country2)) {
                    trips.append("Y");
                } else {
                    trips.append("N");
                }
            }
            if (country1 != countries - 1) {
                trips.append("\n");
            }
        }
        return trips.toString();
    }

    private static Set<Integer> getCountriesReached(Digraph digraph, int country) {
        Set<Integer> countriesReached = new HashSet<>();
        boolean[] visited = new boolean[digraph.vertices];
        getCountriesReached(digraph, country, countriesReached, visited);
        return countriesReached;
    }

    private static void getCountriesReached(Digraph digraph, int country, Set<Integer> countriesReached, boolean[] visited) {
        countriesReached.add(country);
        visited[country] = true;

        for (int neighbor : digraph.adjacent(country)) {
            if (!visited[neighbor]) {
                getCountriesReached(digraph, neighbor, countriesReached, visited);
            }
        }
    }

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static void test() {
        String trips1 = getTrips(2, "YY", "YY");
        System.out.print("Trips 1:\n" + trips1);
        System.out.println("Expected: \nYY\n" +
                "YY");

        String trips2 = getTrips(2, "NY", "YY");
        System.out.print("\nTrips 2:\n" + trips2);
        System.out.println("Expected: \nYY\n" +
                "NY");

        String trips3 = getTrips(2, "NN", "YY");
        System.out.print("\nTrips 3:\n" + trips3);
        System.out.println("Expected: \nYN\n" +
                "NY");

        String trips4 = getTrips(5, "YNNYY", "YYYNY");
        System.out.print("\nTrips 4:\n" + trips4);
        System.out.println("Expected: \nYNNNN\n" +
                "YYNNN\n" +
                "NNYYN\n" +
                "NNNYN\n" +
                "NNNYY");

        String trips5 = getTrips(10, "NYYYNNYYYY", "YYNYYNYYNY");
        System.out.print("\nTrips 5:\n" + trips5);
        System.out.println("Expected: \nYYYNNNNNNN\n" +
                "NYYNNNNNNN\n" +
                "NNYNNNNNNN\n" +
                "NNYYNNNNNN\n" +
                "NNYYYNNNNN\n" +
                "NNNNNYNNNN\n" +
                "NNNNNNYYYN\n" +
                "NNNNNNYYYN\n" +
                "NNNNNNNNYN\n" +
                "NNNNNNNNYY");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            int countries = Integer.parseInt(lines.get(i++));
            String incomingRestrictions = lines.get(i++);
            String outgoingRestrictions = lines.get(i);

            String trips = getTrips(countries, incomingRestrictions, outgoingRestrictions);
            output.add("Case #" + caseId + ":\n" + trips);
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
