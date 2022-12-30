package facebook.hacker.cup.year2022.round1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 26/08/22.
 */
public class ConsecutiveCuts1And2 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2022/Round 1/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "consecutive_cuts_chapter_1_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "consecutive_cuts_chapter_1_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "consecutive_cuts_chapter_1_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "consecutive_cuts_chapter_1_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "consecutive_cuts_chapter_2_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "consecutive_cuts_chapter_2_sample_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "consecutive_cuts_chapter_2_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "consecutive_cuts_chapter_2_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static boolean isPossibleToCut(String originalCards, String targetCards, int cuts) {
        if (cuts == 0) {
            return originalCards.equals(targetCards);
        }
        if (originalCards.length() == 3) {
            if (originalCards.charAt(0) == targetCards.charAt(0)
                    && originalCards.charAt(0) == targetCards.charAt(2)) {
                return true;
            }
            return (originalCards.charAt(0) != targetCards.charAt(0) && cuts % 2 == 1)
                    || (originalCards.charAt(0) == targetCards.charAt(0) && cuts % 2 == 0) ;
        }

        String concatenatedCards = originalCards + " " + originalCards;
        if (cuts == 1) {
            concatenatedCards = concatenatedCards.substring(1, concatenatedCards.length() - 1);
        }
        KnuthMorrisPratt knuthMorrisPratt = new KnuthMorrisPratt(targetCards);
        return knuthMorrisPratt.search(concatenatedCards) < originalCards.length();
    }

    private static class KnuthMorrisPratt {
        private final String pattern;
        private final int[] next; // prefix

        public KnuthMorrisPratt(String pattern) {
            // Build NFA from pattern
            this.pattern = pattern;
            int patternLength = pattern.length();

            next = new int[patternLength];

            int j = -1;

            for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                // Compute next[patternIndex]

                if (patternIndex == 0) {
                    next[patternIndex] = -1;
                } else if (pattern.charAt(patternIndex) != pattern.charAt(j)) {
                    next[patternIndex] = j;
                } else {
                    next[patternIndex] = next[j];
                }

                while (j >= 0 && pattern.charAt(patternIndex) != pattern.charAt(j)) {
                    j = next[j];
                }
                j++;
            }
        }

        // Search for pattern in text.
        // Returns the index of the first occurrence of the pattern in the text or textLength if no such match.
        public int search(String text) {
            int textIndex;
            int patternIndex;
            int textLength = text.length();
            int patternLength = pattern.length();

            for (textIndex = 0, patternIndex = 0; textIndex < textLength && patternIndex < patternLength; textIndex++) {
                while (patternIndex >= 0 && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                    patternIndex = next[patternIndex];
                }
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                return textIndex - patternLength; // found
            } else {
                return textLength;                // not found
            }
        }
    }

    private static void test() {
        String originalCards1 = "5 1 2 4 3";
        String targetCards1 = "2 4 3 5 1";
        String result1 = isPossibleToCut(originalCards1, targetCards1, 1) ? "YES" : "NO";
        System.out.println("Result 1: " + result1 + " Expected: YES");

        String originalCards2 = "3 1 4 2";
        String targetCards2 = "1 2 3 4";
        String result2 = isPossibleToCut(originalCards2, targetCards2, 10) ? "YES" : "NO";
        System.out.println("Result 2: " + result2 + " Expected: NO");

        String originalCards3 = "3 1 4 2";
        String targetCards3 = "2 3 1 4";
        String result3 = isPossibleToCut(originalCards3, targetCards3, 0) ? "YES" : "NO";
        System.out.println("Result 3: " + result3 + " Expected: NO");

        String originalCards4 = "3 2 1";
        String targetCards4 = "1 3 2";
        String result4 = isPossibleToCut(originalCards4, targetCards4, 3) ? "YES" : "NO";
        System.out.println("Result 4: " + result4 + " Expected: YES");

        String originalCards5 = "5 1 2 2 3";
        String targetCards5 = "2 2 3 5 1";
        String result5 = isPossibleToCut(originalCards5, targetCards5, 1) ? "YES" : "NO";
        System.out.println("Result 5: " + result5 + " Expected: YES");

        String originalCards6 = "10 10 9 10 9";
        String targetCards6 = "10 10 10 9 9";
        String result6 = isPossibleToCut(originalCards6, targetCards6, 3) ? "YES" : "NO";
        System.out.println("Result 6: " + result6 + " Expected: NO");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i++).split(" ");
            int cuts = Integer.parseInt(data[1]);
            String originalCards = lines.get(i++);
            String targetCards = lines.get(i);

            boolean possible = isPossibleToCut(originalCards, targetCards, cuts);
            String resultLine = "Case #" + caseId + ": ";
            if (possible) {
                resultLine += "YES";
            } else {
                resultLine += "NO";
            }
            output.add(resultLine);
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
