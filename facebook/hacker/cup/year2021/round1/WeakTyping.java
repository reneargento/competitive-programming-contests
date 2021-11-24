package facebook.hacker.cup.year2021.round1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 11/09/21.
 */
public class WeakTyping {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2021/Round 1/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "weak_typing_chapter_1_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "weak_typing_chapter_1_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "weak_typing_chapter_1_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "weak_typing_chapter_1_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "weak_typing_chapter_1_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "weak_typing_chapter_1_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static int countHandChanges(String word) {
        boolean started = false;
        boolean isUsingLeftHand = false;

        int handChanges = 0;

        for (char character : word.toCharArray()) {
            if (character == 'O') {
                if (!started) {
                    started = true;
                    isUsingLeftHand = false;
                } else {
                    if (isUsingLeftHand) {
                        handChanges++;
                    }
                    isUsingLeftHand = false;
                }
            } else if (character == 'X') {
                if (!started) {
                    started = true;
                    isUsingLeftHand = true;
                } else {
                    if (!isUsingLeftHand) {
                        handChanges++;
                    }
                    isUsingLeftHand = true;
                }
            }
        }
        return handChanges;
    }

    private static void test() {
        long handChanges1 = countHandChanges("O");
        System.out.println("Hand changes 1: " + handChanges1 + " Expected: 0");

        long handChanges2 = countHandChanges("XFO");
        System.out.println("Hand changes 2: " + handChanges2 + " Expected: 1");

        long handChanges3 = countHandChanges("FFOFF");
        System.out.println("Hand changes 3: " + handChanges3 + " Expected: 0");

        long handChanges4 = countHandChanges("FXXFXFOOXF");
        System.out.println("Hand changes 4: " + handChanges4 + " Expected: 2");

        long handChanges5 = countHandChanges("XFOFXFOFXFOFX");
        System.out.println("Hand changes 5: " + handChanges5 + " Expected: 6");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String stringLength = lines.get(i++); // not used
            String string = lines.get(i);

            long handChanges = countHandChanges(string);
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
