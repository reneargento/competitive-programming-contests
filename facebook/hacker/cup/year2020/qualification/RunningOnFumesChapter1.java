package facebook.hacker.cup.year2020.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 29/06/19.
 */
// https://www.facebook.com/codingcompetitions/hacker-cup/2020/qualification-round/problems/D1
public class RunningOnFumesChapter1 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2020/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "running_on_fumes_chapter_1_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "running_on_fumes_chapter_1_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "running_on_fumes_chapter_1_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "running_on_fumes_chapter_1_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "running_on_fumes_chapter_1_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "running_on_fumes_chapter_1_output.txt";

    public static long getMinimumCost(List<Integer> costs, int gallons) {
        long[] minimumCosts = new long[costs.size()];
        Arrays.fill(minimumCosts, Long.MAX_VALUE);
        int targetCity = costs.size() - 1;
        minimumCosts[0] = 0;

        for (int i = 0; i < costs.size(); i++) {
            if (minimumCosts[i] == Long.MAX_VALUE) {
                continue;
            }

            int lastIndex = Math.min(i + gallons, minimumCosts.length - 1);
            long minCostFromCurrentCity = Long.MAX_VALUE;

            for (int nextCity = lastIndex; nextCity > i; nextCity--) {
                if (costs.get(nextCity) == 0 && nextCity != targetCity) {
                    continue;
                }

                long currentCost = minimumCosts[nextCity];
                long cityCost;
                if (nextCity == targetCity) {
                    cityCost = 0;
                } else {
                    cityCost = costs.get(nextCity);
                }

                long newCost = minimumCosts[i] + cityCost;
                if (currentCost > newCost && newCost < minCostFromCurrentCity) {
                    minimumCosts[nextCity] = newCost;
                    minCostFromCurrentCity = newCost;
                }
            }
        }

        if (minimumCosts[targetCity] == Long.MAX_VALUE) {
            return -1;
        } else {
            return minimumCosts[targetCity];
        }
    }

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static void test() {
        List<Integer> costs1 = new ArrayList<>();
        costs1.add(0);
        costs1.add(20);
        costs1.add(30);
        costs1.add(0);
        costs1.add(10);
        long cost1 = getMinimumCost(costs1, 3);
        System.out.println("Minimum cost 1: " + cost1);
        System.out.println("Expected: 20");

        long cost2 = getMinimumCost(costs1, 2);
        System.out.println("\nMinimum cost 2: " + cost2);
        System.out.println("Expected: 30");

        long cost3 = getMinimumCost(costs1, 1);
        System.out.println("\nMinimum cost 3: " + cost3);
        System.out.println("Expected: -1");

        List<Integer> costs4 = new ArrayList<>();
        costs4.add(99);
        costs4.add(88);
        costs4.add(77);
        costs4.add(66);
        long cost4 = getMinimumCost(costs4, 1);
        System.out.println("\nMinimum cost 4: " + cost4);
        System.out.println("Expected: 165");

        long cost5 = getMinimumCost(costs4, 4);
        System.out.println("\nMinimum cost 5: " + cost5);
        System.out.println("Expected: 0");

        List<Integer> costs6 = new ArrayList<>();
        costs6.add(0);
        costs6.add(0);
        costs6.add(20);
        costs6.add(30);
        costs6.add(0);
        costs6.add(10);
        long cost6 = getMinimumCost(costs6, 2);
        System.out.println("\nMinimum cost 6: " + cost6);
        System.out.println("Expected: 50");

        List<Integer> costs7 = new ArrayList<>();
        costs7.add(0);
        costs7.add(1);
        costs7.add(4);
        costs7.add(7);
        costs7.add(0);
        costs7.add(5);
        costs7.add(9);
        costs7.add(8);
        costs7.add(0);
        costs7.add(3);
        costs7.add(0);
        costs7.add(6);
        long cost7 = getMinimumCost(costs7, 3);
        System.out.println("\nMinimum cost 7: " + cost7);
        System.out.println("Expected: 19");

        List<Integer> costs8 = new ArrayList<>();
        costs8.add(0);
        costs8.add(0);
        costs8.add(0);
        costs8.add(0);
        costs8.add(0);
        costs8.add(0);
        costs8.add(0);
        costs8.add(0);
        costs8.add(0);
        costs8.add(0);
        long cost8 = getMinimumCost(costs8, 9);
        System.out.println("\nMinimum cost 8: " + cost8);
        System.out.println("Expected: 0");

        List<Integer> costs9 = new ArrayList<>();
        costs9.add(999999994);
        costs9.add(999999993);
        costs9.add(999999996);
        costs9.add(999999995);
        costs9.add(999999991);
        costs9.add(999999999);
        costs9.add(999999992);
        costs9.add(999999997);
        costs9.add(999999996);
        long cost9 = getMinimumCost(costs9, 2);
        System.out.println("\nMinimum cost 9: " + cost9);
        System.out.println("Expected: 2999999979");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] cityInformation = lines.get(i++).split(" ");
            int citiesCount = Integer.parseInt(cityInformation[0]);
            int gallons = Integer.parseInt(cityInformation[1]);

            List<Integer> costs = new ArrayList<>();

            for (int city = 0; city < citiesCount; city++) {
                int cost = Integer.parseInt(lines.get(i));
                costs.add(cost);

                if (city != citiesCount - 1) {
                    i++;
                }
            }

            long minimumCost = getMinimumCost(costs, gallons);
            output.add("Case #" + caseId + ": " + minimumCost);
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
