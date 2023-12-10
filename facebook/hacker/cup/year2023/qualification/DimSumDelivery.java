package facebook.hacker.cup.year2023.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created by Rene Argento on 24/09/23.
 */
public class DimSumDelivery {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2023/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "dim_sum_delivery_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "dim_sum_delivery_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "dim_sum_delivery_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "dim_sum_delivery_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "dim_sum_delivery_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "dim_sum_delivery_output.txt";

    public static void main(String[] args) {
//        test();
        compete();
    }

    private static String hasGuaranteedWin(int targetRow, int targetColumn) {
        return targetRow > targetColumn ? "YES" : "NO";
    }

    private static void test() {
        String hasWin1 = hasGuaranteedWin(2, 2);
        System.out.println("Test 1: " + hasWin1 + " Expected: NO");

        String hasWin2 = hasGuaranteedWin(5, 2);
        System.out.println("Test 2: " + hasWin2 + " Expected: YES");

        String hasWin3 = hasGuaranteedWin(4, 4);
        System.out.println("Test 3: " + hasWin3 + " Expected: NO");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;

        for(int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(" ");
            int targetRow = Integer.parseInt(data[0]);
            int targetColumn = Integer.parseInt(data[1]);

            String hasWin = hasGuaranteedWin(targetRow, targetColumn);

            String result = "Case #" + caseId + ": " + hasWin + "\n";
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
