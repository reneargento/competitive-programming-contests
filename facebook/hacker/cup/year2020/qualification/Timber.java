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
// https://www.facebook.com/codingcompetitions/hacker-cup/2020/qualification-round/problems/C
public class Timber {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2020/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "timber_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "timber_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "timber_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "timber_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "timber_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "timber_output.txt";

    private static class Tree implements Comparable<Tree> {
        int position;
        int height;

        public Tree(int position, int height) {
            this.position = position;
            this.height = height;
        }

        @Override
        public int compareTo(Tree other) {
            return position - other.position;
        }
    }

    public static long getLongestCombinedTimberInterval(List<Tree> trees) {
        Collections.sort(trees);
        Map<Integer, Long> positionsWithCombinedIntervals = new HashMap<>();

        for (Tree tree : trees) {
            long combinedInterval = positionsWithCombinedIntervals.getOrDefault(tree.position, 0L);

            // Falling left
            int leftPosition = tree.position - tree.height;
            long intervalLeft = positionsWithCombinedIntervals.getOrDefault(leftPosition, 0L);
            long combinedIntervalLeft = intervalLeft + tree.height;

            if (combinedIntervalLeft > combinedInterval) {
                positionsWithCombinedIntervals.put(tree.position, combinedIntervalLeft);
            }

            // Falling right
            int rightPosition = tree.position + tree.height;
            long intervalRight = positionsWithCombinedIntervals.getOrDefault(rightPosition, 0L);
            long combinedIntervalRight = combinedInterval + tree.height;

            if (combinedIntervalRight > intervalRight) {
                positionsWithCombinedIntervals.put(rightPosition, combinedIntervalRight);
            }
        }

        long longestCombinedTimberInterval = 0;
        for (long combinedInterval : positionsWithCombinedIntervals.values()) {
            longestCombinedTimberInterval = Math.max(longestCombinedTimberInterval, combinedInterval);
        }

        return longestCombinedTimberInterval;
    }

    public static void main(String[] args) {
//        test();
        compete();
    }

    private static void test() {
        List<Tree> trees1 = new ArrayList<>();
        trees1.add(new Tree(0, 5));
        trees1.add(new Tree(5, 4));
        long longestCombinedTimberInterval1 = getLongestCombinedTimberInterval(trees1);
        System.out.println("Interval 1: " + longestCombinedTimberInterval1);
        System.out.println("Expected: 9");

        List<Tree> trees2 = new ArrayList<>();
        trees2.add(new Tree(0, 5));
        trees2.add(new Tree(9, 4));
        long longestCombinedTimberInterval2 = getLongestCombinedTimberInterval(trees2);
        System.out.println("\nInterval 2: " + longestCombinedTimberInterval2);
        System.out.println("Expected: 9");

        List<Tree> trees3 = new ArrayList<>();
        trees3.add(new Tree(0, 5));
        trees3.add(new Tree(9, 3));
        trees3.add(new Tree(2, 6));
        long longestCombinedTimberInterval3 = getLongestCombinedTimberInterval(trees3);
        System.out.println("\nInterval 3: " + longestCombinedTimberInterval3);
        System.out.println("Expected: 6");

        List<Tree> trees4 = new ArrayList<>();
        trees4.add(new Tree(3, 2));
        trees4.add(new Tree(2, 8));
        trees4.add(new Tree(-4, 5));
        trees4.add(new Tree(8, 5));
        trees4.add(new Tree(1, 4));
        long longestCombinedTimberInterval4 = getLongestCombinedTimberInterval(trees4);
        System.out.println("\nInterval 4: " + longestCombinedTimberInterval4);
        System.out.println("Expected: 12");

        List<Tree> trees5 = new ArrayList<>();
        trees5.add(new Tree(-15, 15));
        trees5.add(new Tree(-9, 9));
        trees5.add(new Tree(-3, 3));
        trees5.add(new Tree(5, 5));
        trees5.add(new Tree(9, 9));
        trees5.add(new Tree(18, 18));
        long longestCombinedTimberInterval5 = getLongestCombinedTimberInterval(trees5);
        System.out.println("\nInterval 5: " + longestCombinedTimberInterval5);
        System.out.println("Expected: 33");

        List<Tree> trees6 = new ArrayList<>();
        trees6.add(new Tree(10, 20));
        trees6.add(new Tree(20, 20));
        trees6.add(new Tree(30, 20));
        trees6.add(new Tree(40, 20));
        trees6.add(new Tree(50, 20));
        trees6.add(new Tree(60, 20));
        trees6.add(new Tree(70, 20));
        trees6.add(new Tree(80, 20));
        long longestCombinedTimberInterval6 = getLongestCombinedTimberInterval(trees6);
        System.out.println("\nInterval 6: " + longestCombinedTimberInterval6);
        System.out.println("Expected: 80");

        List<Tree> trees7 = new ArrayList<>();
        trees7.add(new Tree(13, 8));
        trees7.add(new Tree(-14, 5));
        trees7.add(new Tree(2, 19));
        trees7.add(new Tree(33, 10));
        trees7.add(new Tree(-31, 9));
        trees7.add(new Tree(15, 21));
        trees7.add(new Tree(5, 3));
        trees7.add(new Tree(-22, 16));
        trees7.add(new Tree(-6, 11));
        trees7.add(new Tree(25, 12));
        trees7.add(new Tree(-40, 24));
        trees7.add(new Tree(21, 18));
        long longestCombinedTimberInterval7 = getLongestCombinedTimberInterval(trees7);
        System.out.println("\nInterval 7: " + longestCombinedTimberInterval7);
        System.out.println("Expected: 56");

        List<Tree> trees8 = new ArrayList<>();
        trees8.add(new Tree(-500000000, 500000000));
        trees8.add(new Tree(500000000, 500000000));
        long longestCombinedTimberInterval8 = getLongestCombinedTimberInterval(trees8);
        System.out.println("\nInterval 8: " + longestCombinedTimberInterval8);
        System.out.println("Expected: 1000000000");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            int treesCount = Integer.parseInt(lines.get(i++));
            List<Tree> trees = new ArrayList<>();

            for (int tree = 0; tree < treesCount; tree++) {
                String[] treeInformation = lines.get(i).split(" ");
                int position = Integer.parseInt(treeInformation[0]);
                int height = Integer.parseInt(treeInformation[1]);
                trees.add(new Tree(position, height));

                if (tree != treesCount - 1) {
                    i++;
                }
            }

            long longestCombinedTimberInterval = getLongestCombinedTimberInterval(trees);
            output.add("Case #" + caseId + ": " + longestCombinedTimberInterval);
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
