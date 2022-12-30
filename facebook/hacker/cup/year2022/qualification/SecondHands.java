package facebook.hacker.cup.year2022.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 26/08/22.
 */
public class SecondHands {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2022/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "second_hands_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_hands_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "second_hands_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_hands_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "second_hands_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "second_hands_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static String checkPossibility(int[] styles, int capacity) {
        if (styles.length > capacity * 2) {
            return "NO";
        }
        int[] stylesSeen = new int[101];
        for (int style : styles) {
            stylesSeen[style]++;
            if (stylesSeen[style] > 2) {
                return "NO";
            }
        }
        return "YES";
    }

    private static void test() {
        int[] styles1 = { 1, 2, 2 };
        String possible1 = checkPossibility(styles1, 2);
        System.out.println("Possible 1: " + possible1 + " Expected: YES");

        int[] styles2 = { 1, 2, 3, 3, 1 };
        String possible2 = checkPossibility(styles2, 3);
        System.out.println("Possible 2: " + possible2 + " Expected: YES");

        int[] styles3 = { 1, 2, 3, 4, 5 };
        String possible3 = checkPossibility(styles3, 2);
        System.out.println("Possible 3: " + possible3 + " Expected: NO");

        int[] styles4 = { 1, 1, 2, 2, 1 };
        String possible4 = checkPossibility(styles4, 5);
        System.out.println("Possible 4: " + possible4 + " Expected: NO");

        int[] styles5 = { 1 };
        String possible5 = checkPossibility(styles5, 1);
        System.out.println("Possible 5: " + possible5 + " Expected: YES");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i++).split(" ");
            String[] stylesString = lines.get(i).split(" ");
            int[] styles = new int[Integer.parseInt(data[0])];
            int capacity = Integer.parseInt(data[1]);

            for (int j = 0; j < stylesString.length; j++) {
                styles[j] = Integer.parseInt(stylesString[j]);
            }
            String possible = checkPossibility(styles, capacity);
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
