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
public class GoldMineChapter1 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2021/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "gold_mine_chapter_1_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "gold_mine_chapter_1_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "gold_mine_chapter_1_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "gold_mine_chapter_1_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "gold_mine_chapter_1_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "gold_mine_chapter_1_output.txt";

    private static long getGold(List<Integer>[] adjacencyList, long[] nodesGold) {
        boolean[] visited = new boolean[adjacencyList.length];
        visited[1] = true;
        List<Long> goldFromTunnels = new ArrayList<>();

        for (int child : adjacencyList[1]) {
            visited[child] = true;
            long gold = drillTunnels(child, adjacencyList, nodesGold, visited);
            goldFromTunnels.add(gold);
        }
        goldFromTunnels.sort(Collections.reverseOrder());

        if (goldFromTunnels.isEmpty()) {
            return nodesGold[1];
        }
        if (goldFromTunnels.size() == 1) {
            return nodesGold[1] + goldFromTunnels.get(0);
        }
        return nodesGold[1] + goldFromTunnels.get(0) + goldFromTunnels.get(1);
    }

    private static long drillTunnels(int node, List<Integer>[] adjacencyList, long[] nodesGold,
                                     boolean[] visited) {
        List<Long> goldFromTunnels = new ArrayList<>();
        for (int child : adjacencyList[node]) {
            if (!visited[child]) {
                visited[child] = true;

                long gold = drillTunnels(child, adjacencyList, nodesGold, visited);
                goldFromTunnels.add(gold);
            }
        }

        if (goldFromTunnels.isEmpty()) {
            return nodesGold[node];
        }
        goldFromTunnels.sort(Collections.reverseOrder());
        return nodesGold[node] + goldFromTunnels.get(0);
    }

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static void test() {
        List<Integer>[] adjacencyList1 = initAdjacencyList(3);
        adjacencyList1[1].add(2);
        adjacencyList1[2].add(1);
        long[] gold1 = { -1, 10, 20 };
        System.out.println("Gold 1: " + getGold(adjacencyList1, gold1) + " Expected: 30");

        List<Integer>[] adjacencyList2 = initAdjacencyList(5);
        adjacencyList2[1].add(2);
        adjacencyList2[2].add(1);
        adjacencyList2[4].add(2);
        adjacencyList2[2].add(4);
        adjacencyList2[1].add(3);
        adjacencyList2[3].add(1);
        long[] gold2 = { -1, 1, 1, 1, 1 };
        System.out.println("Gold 2: " + getGold(adjacencyList2, gold2) + " Expected: 4");

        List<Integer>[] adjacencyList3 = initAdjacencyList(5);
        adjacencyList3[1].add(2);
        adjacencyList3[2].add(1);
        adjacencyList3[4].add(1);
        adjacencyList3[1].add(4);
        adjacencyList3[1].add(3);
        adjacencyList3[3].add(1);
        long[] gold3 = { -1, 1, 1, 1, 1 };
        System.out.println("Gold 3: " + getGold(adjacencyList3, gold3) + " Expected: 3");

        List<Integer>[] adjacencyList4 = initAdjacencyList(7);
        adjacencyList4[1].add(5);
        adjacencyList4[5].add(1);
        adjacencyList4[5].add(4);
        adjacencyList4[4].add(5);
        adjacencyList4[5].add(3);
        adjacencyList4[3].add(5);
        adjacencyList4[5].add(2);
        adjacencyList4[2].add(5);
        adjacencyList4[6].add(3);
        adjacencyList4[3].add(6);
        long[] gold4 = { -1, 5, 4, 1, 3, 2, 4 };
        System.out.println("Gold 4: " + getGold(adjacencyList4, gold4) + " Expected: 12");

        List<Integer>[] adjacencyList5 = initAdjacencyList(10);
        adjacencyList5[4].add(5);
        adjacencyList5[5].add(4);
        adjacencyList5[6].add(7);
        adjacencyList5[7].add(6);
        adjacencyList5[8].add(9);
        adjacencyList5[9].add(8);
        adjacencyList5[1].add(3);
        adjacencyList5[3].add(1);
        adjacencyList5[6].add(8);
        adjacencyList5[8].add(6);
        adjacencyList5[2].add(4);
        adjacencyList5[4].add(2);
        adjacencyList5[4].add(1);
        adjacencyList5[1].add(4);
        adjacencyList5[1].add(8);
        adjacencyList5[8].add(1);
        long[] gold5 = { -1, 2, 14, 7, 6, 11, 3, 6, 1, 8 };
        System.out.println("Gold 5: " + getGold(adjacencyList5, gold5) + " Expected: 32");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            int nodes = Integer.parseInt(lines.get(i++));
            List<Integer>[] adjacencyList = initAdjacencyList(nodes + 1);
            long[] nodesGold = new long[nodes + 1];

            String[] goldValues = lines.get(i).split(" ");
            if (goldValues.length > 1) {
                i++;
            }

            for (int j = 1; j <= goldValues.length; j++) {
                nodesGold[j] = Long.parseLong(goldValues[j - 1]);
            }

            for (int j = 0; j < nodes - 1; j++) {
                String[] row = lines.get(i).split(" ");
                int node1 = Integer.parseInt(row[0]);
                int node2 = Integer.parseInt(row[1]);
                adjacencyList[node1].add(node2);
                adjacencyList[node2].add(node1);

                if (j != nodes - 2) {
                    i++;
                }
            }

            long gold = getGold(adjacencyList, nodesGold);
            output.add("Case #" + caseId + ": " + gold);
            caseId++;
        }
        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static List<Integer>[] initAdjacencyList(int size) {
        List<Integer>[] adjacencyList = new List[size];

        for (int i = 0; i < size; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        return adjacencyList;
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
