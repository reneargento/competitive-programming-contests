package facebook.hacker.cup.year2019.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 15/06/19.
 */
// https://www.facebook.com/hackercup/problem/589264531559040/
public class MrX {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2019/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "mrx_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "mrx_sample_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "mrx_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "mrx_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static void test() {
        int count1 = countModifications("X");
        System.out.println("Count: " + count1 + " Expected: 1");

        int count2 = countModifications("0");
        System.out.println("Count: " + count2 + " Expected: 0");

        int count3 = countModifications("(x|1)");
        System.out.println("Count: " + count3 + " Expected: 0");

        int count4 = countModifications("((1^(X&X))|x)");
        System.out.println("Count: " + count4 + " Expected: 1");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String expression = lines.get(i);
            int modifications = countModifications(expression);

            output.add("Case #" + caseId + ": " + modifications);
            caseId++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static int countModifications(String expression) {
        int result0 = getResult(expression, 0);
        int result1 = getResult(expression, 1);
        return result0 == result1 ? 0 : 1;
    }

    private static int getResult(String expression, int xValue) {
        char[] chars = expression.toCharArray();
        char[] parcialResult = new char[chars.length];
        int index = 0;

        for (char character : chars) {
            switch (character) {
                case ')':
                    index -= 3;
                    parcialResult[index - 1] = evaluate(parcialResult[index] - '0', parcialResult[index + 1], parcialResult[index + 2] - '0');
                    break;
                case 'x': parcialResult[index++] = xValue == 0 ? '0' : '1'; break;
                case 'X': parcialResult[index++] = xValue == 0 ? '1' : '0'; break;
                default: parcialResult[index++] = character; break;
            }
        }
        return parcialResult[0] - '0';
    }

    private static char evaluate(int value1, char expression, int value2) {
        int result;
        if (expression == '|') {
            result = value1 | value2;
        } else if (expression == '&') {
            result = value1 & value2;
        } else {
            result = value1 ^ value2;
        }
        return result == 0 ? '0' : '1';
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
