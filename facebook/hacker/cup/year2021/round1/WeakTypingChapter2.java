package facebook.hacker.cup.year2021.round1;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 11/09/21.
 */
public class WeakTypingChapter2 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2021/Round 1/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "weak_typing_chapter_2_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "weak_typing_chapter_2_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "weak_typing_chapter_2_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "weak_typing_chapter_2_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "weak_typing_chapter_2_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "weak_typing_chapter_2_output.txt";

    private static final BigInteger MOD = BigInteger.valueOf(1000000007);

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static BigInteger countHandChanges(String word) {
        BigInteger handChanges = BigInteger.ZERO;
        int previousXIndex = -1;
        int previousOIndex = -1;

        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);

            if (character == 'O') {
                if (previousXIndex != -1) {
                    BigInteger handChangesInSubstring = BigInteger.valueOf(previousXIndex + 1)
                            .multiply(BigInteger.valueOf(word.length() - i)).mod(MOD);
                    handChanges = handChanges.add(handChangesInSubstring).mod(MOD);
                    previousXIndex = -1;
                }
                previousOIndex = i;
            } else if (character == 'X') {
                if (previousOIndex != -1) {
                    BigInteger handChangesInSubstring = BigInteger.valueOf(previousOIndex + 1)
                            .multiply(BigInteger.valueOf(word.length() - i)).mod(MOD);
                    handChanges = handChanges.add(handChangesInSubstring).mod(MOD);
                    previousOIndex = -1;
                }
                previousXIndex = i;
            }
        }
        return handChanges;
    }

    private static void test() {
        BigInteger handChanges1 = countHandChanges("O");
        System.out.println("Hand changes 1: " + handChanges1 + " Expected: 0");

        BigInteger handChanges2 = countHandChanges("XFO");
        System.out.println("Hand changes 2: " + handChanges2 + " Expected: 1");

        BigInteger handChanges3 = countHandChanges("FFOFF");
        System.out.println("Hand changes 3: " + handChanges3 + " Expected: 0");

        BigInteger handChanges4 = countHandChanges("FXXFXFOOXF");
        System.out.println("Hand changes 4: " + handChanges4 + " Expected: 36");

        BigInteger handChanges5 = countHandChanges("XFOFXFOFXFOFX");
        System.out.println("Hand changes 5: " + handChanges5 + " Expected: 146");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String stringLength = lines.get(i++); // not used
            String string = lines.get(i);

            BigInteger handChanges = countHandChanges(string);
            output.add("Case #" + caseId + ": " + handChanges);
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