package facebook.hacker.cup.year2022.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 26/08/22.
 */
@SuppressWarnings("unchecked")
public class SecondFlight {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2022/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "second_flight_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_flight_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "second_flight_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_flight_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "second_flight_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "second_flight_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static long query(Map<Integer, Integer>[] adjacencyList, Map<Integer, Long>[] dp, int source,
                              int destination) {
        if (dp[source] == null) {
            dp[source] = new HashMap<>();
        }
        if (dp[destination] == null) {
            dp[destination] = new HashMap<>();
        }
        if (dp[source].containsKey(destination)) {
            return dp[source].get(destination);
        }

        if (adjacencyList[destination].size() < adjacencyList[source].size()) {
            int aux = source;
            source = destination;
            destination = aux;
        }

        long tourists = adjacencyList[source].getOrDefault(destination, 0) * 2;
        for (Integer neighbor : adjacencyList[source].keySet()) {
            if (adjacencyList[neighbor].containsKey(destination)) {
                Integer touristsOnEdge1 = adjacencyList[source].get(neighbor);
                Integer touristsOnEdge2 = adjacencyList[neighbor].get(destination);
                tourists += Math.min(touristsOnEdge1, touristsOnEdge2);
            }
        }
        dp[source].put(destination, tourists);
        dp[destination].put(source, tourists);
        return tourists;
    }

    private static Map<Integer, Integer>[] createAdjacencyList(int length) {
        Map<Integer, Integer>[] adjacencyList = new Map[length];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new HashMap<>();
        }
        return adjacencyList;
    }

    private static void test() {
        Map<Integer, Integer>[] adjacencyList1 = createAdjacencyList(5);
        adjacencyList1[1].put(2, 10);
        adjacencyList1[2].put(1, 10);
        adjacencyList1[1].put(3, 5);
        adjacencyList1[3].put(1, 5);
        adjacencyList1[2].put(3, 15);
        adjacencyList1[3].put(2, 15);
        adjacencyList1[2].put(4, 10);
        adjacencyList1[4].put(2, 10);
        adjacencyList1[3].put(4, 7);
        adjacencyList1[4].put(3, 7);
        Map<Integer, Long>[] dp1 = new Map[adjacencyList1.length];
        long tourists1 = query(adjacencyList1, dp1,1, 2);
        System.out.println("Tourists 1: " + tourists1 + " Expected: 25");
        long tourists2 = query(adjacencyList1, dp1,1, 3);
        System.out.println("Tourists 2: " + tourists2 + " Expected: 20");
        long tourists3 = query(adjacencyList1, dp1,2, 3);
        System.out.println("Tourists 3: " + tourists3 + " Expected: 42");
        long tourists4 = query(adjacencyList1, dp1,2, 4);
        System.out.println("Tourists 4: " + tourists4 + " Expected: 27");
        long tourists5 = query(adjacencyList1, dp1,3, 4);
        System.out.println("Tourists 5: " + tourists5 + " Expected: 24");
        long tourists6 = query(adjacencyList1, dp1,4, 1);
        System.out.println("Tourists 6: " + tourists6 + " Expected: 15");

        Map<Integer, Integer>[] adjacencyList2 = createAdjacencyList(5);
        adjacencyList2[1].put(2, 10);
        adjacencyList2[2].put(1, 10);
        adjacencyList2[2].put(3, 20);
        adjacencyList2[3].put(2, 20);
        adjacencyList2[3].put(1, 30);
        adjacencyList2[1].put(3, 30);
        Map<Integer, Long>[] dp2 = new Map[adjacencyList2.length];
        long tourists7 = query(adjacencyList2, dp2,1, 2);
        System.out.println("Tourists 7: " + tourists7 + " Expected: 40");
        long tourists8 = query(adjacencyList2, dp2,1, 3);
        System.out.println("Tourists 8: " + tourists8 + " Expected: 70");
        long tourists9 = query(adjacencyList2, dp2,1, 4);
        System.out.println("Tourists 9: " + tourists9 + " Expected: 0");
        long tourists10 = query(adjacencyList2, dp2,2, 3);
        System.out.println("Tourists 10: " + tourists10 + " Expected: 50");
        long tourists11 = query(adjacencyList2, dp2,2, 4);
        System.out.println("Tourists 11: " + tourists11 + " Expected: 0");
        long tourists12 = query(adjacencyList2, dp2,3, 4);
        System.out.println("Tourists 12: " + tourists12 + " Expected: 0");

        Map<Integer, Integer>[] adjacencyList3 = createAdjacencyList(5);
        adjacencyList3[1].put(2, 20);
        adjacencyList3[2].put(1, 20);
        adjacencyList3[2].put(3, 10);
        adjacencyList3[3].put(2, 10);
        adjacencyList3[3].put(4, 30);
        adjacencyList3[4].put(3, 30);
        Map<Integer, Long>[] dp3 = new Map[adjacencyList3.length];
        long tourists13 = query(adjacencyList3, dp3,1, 2);
        System.out.println("Tourists 13: " + tourists13 + " Expected: 40");
        long tourists14 = query(adjacencyList3, dp3,1, 3);
        System.out.println("Tourists 14: " + tourists14 + " Expected: 10");
        long tourists15 = query(adjacencyList3, dp3,1, 4);
        System.out.println("Tourists 15: " + tourists15 + " Expected: 0");
        long tourists16 = query(adjacencyList3, dp3,2, 3);
        System.out.println("Tourists 16: " + tourists16 + " Expected: 20");
        long tourists17 = query(adjacencyList3, dp3,2, 4);
        System.out.println("Tourists 17: " + tourists17 + " Expected: 10");
        long tourists18 = query(adjacencyList3, dp3,3, 4);
        System.out.println("Tourists 18: " + tourists18 + " Expected: 60");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;

        for(int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i++).split(" ");
            int airports = Integer.parseInt(data[0]);
            Map<Integer, Integer>[] adjacencyList = createAdjacencyList(airports + 1);
            int flightPaths = Integer.parseInt(data[1]);
            int days = Integer.parseInt(data[2]);

            for (int j = 0; j < flightPaths; j++, i++) {
                String[] flightPath = lines.get(i).split(" ");
                int source = Integer.parseInt(flightPath[0]);
                int destination = Integer.parseInt(flightPath[1]);
                int tourists = Integer.parseInt(flightPath[2]);

                adjacencyList[source].put(destination, tourists);
                adjacencyList[destination].put(source, tourists);
            }
            Map<Integer, Long>[] dp = new Map[adjacencyList.length];

            StringBuilder result = new StringBuilder();
            result.append("Case #").append(caseId).append(":");
            for (int j = 0; j < days; j++) {
                String[] query = lines.get(i).split(" ");
                int source = Integer.parseInt(query[0]);
                int destination = Integer.parseInt(query[1]);

                long tourists = query(adjacencyList, dp, source, destination);
                result.append(" ").append(tourists);

                if (j != days - 1) {
                    i++;
                }
            }
            writeFileOutput(FILE_OUTPUT_PATH, result.toString() + "\n");
            caseId++;
        }
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

    private static void writeFileOutput(String file, String data){
        byte[] dataBytes = data.getBytes();

        try {
            Files.write(Paths.get(file), dataBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
