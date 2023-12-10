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
public class CheeseburgerCorollary2 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2023/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "cheeseburger_corollary_2_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "cheeseburger_corollary_2_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "cheeseburger_corollary_2_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "cheeseburger_corollary_2_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "cheeseburger_corollary_2_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "cheeseburger_corollary_2_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static long computeMaxDecker(long singlePrice, long doublePrice, long money) {
        long onlySinglesQuantity = money / singlePrice;
        long maxDeckerOnlySingles = computeMaxDecker(onlySinglesQuantity, 0);

        long onlyDoubleQuantity = money / doublePrice;
        long maxDeckerOnlyDoubles = computeMaxDecker(0, onlyDoubleQuantity);

        long doublesQuantity1Single = (money - singlePrice) / doublePrice;
        long maxDecker1Single = 0;
        if (money >= singlePrice) {
            maxDecker1Single = computeMaxDecker(1, doublesQuantity1Single);
        }

        long doublesQuantity2Singles = (money - (2 * singlePrice)) / doublePrice;
        long maxDecker2Singles = 0;
        if (money >= 2 * singlePrice) {
            maxDecker2Singles = computeMaxDecker(2, doublesQuantity2Singles);
        }

        long maxDecker1 = Math.max(maxDeckerOnlySingles, maxDeckerOnlyDoubles);
        long maxDecker2 = Math.max(maxDecker1Single, maxDecker2Singles);
        return Math.max(maxDecker1, maxDecker2);
    }

    private static long computeMaxDecker(long singles, long doubles) {
        if (singles == 0 && doubles == 0) {
            return 0;
        }
        long buns = (singles + doubles) * 2;
        long patties = singles + 2 * doubles;
        return Math.min(buns - 1, patties);
    }

    private static void test() {
        long maxDecker1 = computeMaxDecker(2, 3, 5);
        System.out.println("Test 1: " + maxDecker1 + " Expected: 3");

        long maxDecker2 = computeMaxDecker(2, 3, 2);
        System.out.println("Test 2: " + maxDecker2 + " Expected: 1");

        long maxDecker3 = computeMaxDecker(2, 3, 1);
        System.out.println("Test 3: " + maxDecker3 + " Expected: 0");

        long maxDecker4 = computeMaxDecker(5, 1, 100);
        System.out.println("Test 4: " + maxDecker4 + " Expected: 199");

        long maxDecker5 = computeMaxDecker(1, 3, 100);
        System.out.println("Test 5: " + maxDecker5 + " Expected: 100");

        long maxDecker6 = computeMaxDecker(1, 1, 1000000000000L);
        System.out.println("Test 6: " + maxDecker6 + " Expected: 1999999999999");

        long maxDecker7 = computeMaxDecker(2, 2, 0);
        System.out.println("Test 7: " + maxDecker7 + " Expected: 0");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;

        for(int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(" ");
            long singlePrice = Long.parseLong(data[0]);
            long doublePrice = Long.parseLong(data[1]);
            long money = Long.parseLong(data[2]);

            long maxDecker = computeMaxDecker(singlePrice, doublePrice, money);

            String result = "Case #" + caseId + ": " + maxDecker + "\n";
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
