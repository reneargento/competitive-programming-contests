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
// https://www.facebook.com/codingcompetitions/hacker-cup/2020/qualification-round/problems/B
public class Alchemy {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2020/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "alchemy_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "alchemy_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "alchemy_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "alchemy_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "alchemy_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "alchemy_output.txt";

    public static String checkStoneConstruction(String shards) {
        int auburnCount = 0;
        int blackCount = 0;

        for (int i = 0; i < shards.length(); i++) {
            if (shards.charAt(i) == 'A') {
                auburnCount++;
            } else {
                blackCount++;
            }
        }

        int halfSize = shards.length() / 2;
        String possible;
        if ((auburnCount == halfSize && blackCount == (halfSize + 1))
                || (blackCount == halfSize && auburnCount == (halfSize + 1))) {
            possible = "Y";
        } else {
            possible = "N";
        }
        return possible;
    }

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static void test() {
        String possible1 = checkStoneConstruction("BAB");
        System.out.println("Possible 1: " + possible1);
        System.out.println("Expected: Y");

        String possible2 = checkStoneConstruction("BBB");
        System.out.println("\nPossible 2: " + possible2);
        System.out.println("Expected: N");

        String possible3 = checkStoneConstruction("AABBA");
        System.out.println("\nPossible 3: " + possible3);
        System.out.println("Expected: Y");

        String possible4 = checkStoneConstruction("BAAABAA");
        System.out.println("\nPossible 4: " + possible4);
        System.out.println("Expected: N");

        String possible5 = checkStoneConstruction("BBBAABAAAAB");
        System.out.println("\nPossible 5: " + possible5);
        System.out.println("Expected: Y");

        String possible6 = checkStoneConstruction("BABBBABBABB");
        System.out.println("\nPossible 6: " + possible6);
        System.out.println("Expected: N");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            int shardsNumber = Integer.parseInt(lines.get(i++)); // unused
            String shards = lines.get(i);

            String possible = checkStoneConstruction(shards);
            output.add("Case #" + caseId + ": " + possible);
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
