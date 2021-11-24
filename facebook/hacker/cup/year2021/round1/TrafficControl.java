package facebook.hacker.cup.year2021.round1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 11/09/21.
 */
public class TrafficControl {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2021/Round 1/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "traffic_control_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "traffic_control_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "traffic_control_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "traffic_control_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "traffic_control_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "traffic_control_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static int[][] computeDurations(int rows, int columns, int durationA, int durationB) {
        int[][] durations = new int[rows][columns];

        int intersectionsToTraverse = columns + (rows - 1);
        if (intersectionsToTraverse > durationA || intersectionsToTraverse > durationB) {
            return null;
        }

        fillDurations(durations);
        int timeOnTopLeftCell = durationA - intersectionsToTraverse + 1;
        int timeOnTopRightCell = durationB - intersectionsToTraverse + 1;
        durations[0][0] = timeOnTopLeftCell;
        durations[0][columns - 1] = timeOnTopRightCell;
        return durations;
    }

    private static void fillDurations(int[][] durations) {
        for (int[] duration : durations) {
            Arrays.fill(duration, 1);
        }
    }

    private static void test() {
        int[][] durations1 = computeDurations(2, 2, 999, 999);
        if (durations1 == null) {
            System.out.println("Durations 1: Impossible");
        } else {
            System.out.println("Durations 1: Possible");
            printDurations(durations1);
        }

        int[][] durations2 = computeDurations(2, 3, 12, 11);
        if (durations2 == null) {
            System.out.println("Durations 2: Impossible");
        } else {
            System.out.println("Durations 2: Possible");
            printDurations(durations2);
        }

        int[][] durations3 = computeDurations(4, 3, 6, 6);
        if (durations3 == null) {
            System.out.println("Durations 3: Impossible");
        } else {
            System.out.println("Durations 3: Possible");
            printDurations(durations3);
        }

        int[][] durations4 = computeDurations(50, 50, 1, 1);
        if (durations4 == null) {
            System.out.println("Durations 4: Impossible");
        } else {
            System.out.println("Durations 4: Possible");
            printDurations(durations4);
        }
        System.out.println("Expected: Impossible");
    }

    private static void printDurations(int[][] durations) {
        for (int row = 0; row < durations.length; row++) {
            for (int column = 0; column < durations[0].length; column++) {
                System.out.print(durations[row][column]);

                if (column != durations[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(" ");
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);
            int durationA = Integer.parseInt(data[2]);
            int durationB = Integer.parseInt(data[3]);

            int[][] durations = computeDurations(rows, columns, durationA, durationB);
            if (durations == null) {
                output.add("Case #" + caseId + ": Impossible");
            } else {
                output.add("Case #" + caseId + ": Possible");

                for (int row = 0; row < durations.length; row++) {
                    StringBuilder rowDescription = new StringBuilder();
                    for (int column = 0; column < durations[0].length; column++) {
                        rowDescription.append(durations[row][column]);

                        if (column != durations[0].length - 1) {
                            rowDescription.append(" ");
                        }
                    }
                    output.add(rowDescription.toString());
                }
            }
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
