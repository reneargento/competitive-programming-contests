package facebook.hacker.cup.year2023.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 24/09/23.
 */
public class TwoApplesADay {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2023/Qualification/Input - Output/";

    private static final String FILE_INPUT_PATH = PATH + "two_apples_a_day_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "two_apples_a_day_output.txt";

    public static void main(String[] args) {
//        test();
        compete();
    }

    private static long computeWeightNeeded(long[] weights) {
        if (weights.length == 1) {
            return 1;
        }
        int numberOfApples = weights.length;
        Arrays.sort(weights);

        long highestWeight = weights[numberOfApples - 1];
        long secondHighestWeight = weights[numberOfApples - 2];

        long targetSumWithoutFirst = weights[0] + secondHighestWeight;
        long appleWeight1 = computeAppleWeight(weights, targetSumWithoutFirst);
        if (appleWeight1 != -1) {
            return appleWeight1;
        }

        long targetSumWithoutMiddle = weights[0] + highestWeight;
        long appleWeight2 = computeAppleWeight(weights, targetSumWithoutMiddle);
        if (appleWeight2 != -1) {
            return appleWeight2;
        }

        long targetSumWithoutLast = weights[1] + highestWeight;
        long appleWeight3 = computeAppleWeight(weights, targetSumWithoutLast);
        if (appleWeight3 != -1) {
            return appleWeight3;
        }
        return -1;
    }

    private static long computeAppleWeight(long[] weights, long targetSum) {
        int skippedApple = computeSkippedApple(weights, targetSum);
        if (skippedApple != -1) {
            long appleNeeded = targetSum - weights[skippedApple];
            if (appleNeeded > 0) {
                return appleNeeded;
            }
        }
        return -1;
    }

    private static int computeSkippedApple(long[] weights, long targetSum) {
        int skippedAppleIndex = -1;
        int startIndex = 0;
        int endIndex = weights.length - 1;

        while (startIndex < endIndex) {
            long weightSum = weights[startIndex] + weights[endIndex];

            if (weightSum != targetSum) {
                if (skippedAppleIndex == -1) {
                    if (weightSum < targetSum) {
                        skippedAppleIndex = startIndex;
                        startIndex++;
                    } else {
                        skippedAppleIndex = endIndex;
                        endIndex--;
                    }
                } else {
                    return -1;
                }
            } else {
                startIndex++;
                endIndex--;
            }
        }

        if (skippedAppleIndex != -1) {
            return skippedAppleIndex;
        } else {
            return weights.length / 2;
        }
    }

    private static void test() {
        long[] weights1 = { 6, 3, 1, 2, 5 };
        long weight1 = computeWeightNeeded(weights1);
        System.out.println("Test 1: " + weight1 + " Expected: 4");

        long[] weights2 = { 7, 7, 7 };
        long weight2 = computeWeightNeeded(weights2);
        System.out.println("Test 2: " + weight2 + " Expected: 7");

        long[] weights3 = { 1 };
        long weight3 = computeWeightNeeded(weights3);
        System.out.println("Test 3: " + weight3 + " Expected: 1");

        long[] weights4 = { 1, 9, 1, 1, 4 };
        long weight4 = computeWeightNeeded(weights4);
        System.out.println("Test 4: " + weight4 + " Expected: -1");

        long[] weights5 = { 1, 9, 1, 1, 4, 9, 9 };
        long weight5 = computeWeightNeeded(weights5);
        System.out.println("Test 5: " + weight5 + " Expected: 6");

        long[] weights6 = { 1, 9, 10, 1, 4, 6, 9 };
        long weight6 = computeWeightNeeded(weights6);
        System.out.println("Test 6: " + weight6 + " Expected: -1");

        long[] weights7 = { 1000000000, 2, 10, 4, 999999994 };
        long weight7 = computeWeightNeeded(weights7);
        System.out.println("Test 7: " + weight7 + " Expected: 1000000002");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;

        for(int i = 2; i < lines.size(); i += 2) {
            String[] weightString = lines.get(i).split(" ");

            long[] weights = new long[weightString.length];
            for (int w = 0; w < weights.length; w++) {
                weights[w] = Long.parseLong(weightString[w]);
            }

            long smallestWeightNeeded = computeWeightNeeded(weights);

            String result = "Case #" + caseId + ": " + smallestWeightNeeded + "\n";
            writeFileOutput(FILE_OUTPUT_PATH, result);
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
