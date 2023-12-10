package facebook.hacker.cup.year2023.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created by Rene Argento on 23/09/23.
 */
public class CheeseburgerCorollary1 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2023/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "cheeseburger_corollary_1_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "cheeseburger_corollary_1_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "cheeseburger_corollary_1_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "cheeseburger_corollary_1_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "cheeseburger_corollary_1_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "cheeseburger_corollary_1_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static boolean canBuildKDecker(int singles, int doubles, int deckerSize) {
        int buns = singles * 2 + doubles * 2;
        int cheesePatties = singles + doubles * 2;

        int bunsNeeded = deckerSize + 1;
        int cheesePattiesNeeded = deckerSize;

        return bunsNeeded <= buns && cheesePattiesNeeded <= cheesePatties;
    }

    private static void test() {
        boolean canBuild1 = canBuildKDecker(1, 1, 3);
        System.out.println("Test 1: " + canBuild1 + " Expected: true");

        boolean canBuild2 = canBuildKDecker(0, 2, 4);
        System.out.println("Test 2: " + canBuild2 + " Expected: false");

        boolean canBuild3 = canBuildKDecker(5, 5, 1);
        System.out.println("Test 3: " + canBuild3 + " Expected: true");

        boolean canBuild4 = canBuildKDecker(0, 1, 1);
        System.out.println("Test 4: " + canBuild4 + " Expected: true");

        boolean canBuild5 = canBuildKDecker(1, 1, 2);
        System.out.println("Test 5: " + canBuild5 + " Expected: true");

        boolean canBuild6 = canBuildKDecker(97, 1, 99);
        System.out.println("Test 6: " + canBuild6 + " Expected: true");

        boolean canBuild7 = canBuildKDecker(97, 1, 100);
        System.out.println("Test 7: " + canBuild7 + " Expected: false");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;

        for(int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(" ");
            int singles = Integer.parseInt(data[0]);
            int doubles = Integer.parseInt(data[1]);
            int deckerSize = Integer.parseInt(data[2]);

            boolean canBuildDecker = canBuildKDecker(singles, doubles, deckerSize);
            String resultValue = canBuildDecker ? "YES" : "NO";

            String result = "Case #" + caseId + ": " + resultValue + "\n";
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
