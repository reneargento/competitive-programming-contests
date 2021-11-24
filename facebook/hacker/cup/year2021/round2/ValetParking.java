package facebook.hacker.cup.year2021.round2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 11/09/21.
 */
public class ValetParking {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2021/Round 2/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "valet_parking_chapter_1_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "valet_parking_chapter_1_sample_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "valet_parking_chapter_1_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "valet_parking_chapter_1_output.txt";

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static int countMoves(char[][] garage, int targetRow) {
        int rows = garage.length - 2;
        int columns = garage[0].length;
        int[] carsPerRow = new int[garage.length];

        for (int column = 0; column < columns; column++) {
            int cars = 0;
            for (int row = 1; row <= rows; row++) {
                if (garage[row][column] == 'X') {
                    cars++;
                }
            }

            int currentCarsInRow = 0;
            for (int row = 0; row <= rows + 1; row++) {
                boolean addsCar = 1 <= row && row <= rows && garage[row][column] == 'X';
                if (addsCar) {
                    currentCarsInRow++;
                }
                if (addsCar ||
                        currentCarsInRow > targetRow - 1 ||
                        cars - currentCarsInRow > rows - targetRow) {
                    carsPerRow[row]++;
                }
            }
        }

        int bestMoves = Integer.MAX_VALUE;
        for (int row = 0; row <= rows + 1; row++) {
            int moves = Math.abs(row - targetRow) + carsPerRow[row];
            bestMoves = Math.min(bestMoves, moves);
        }
        return bestMoves;
    }

    private static void test() {
        char[][] garage1 = {
                ".".toCharArray(),
                "X".toCharArray(),
                ".".toCharArray()
        };
        int targetRow1 = 1;
        int moves1 = countMoves(garage1, targetRow1);
        System.out.println("Moves 1: " + moves1 + " Expected: 1");

        char[][] garage2 = {
                "...".toCharArray(),
                "XXX".toCharArray(),
                "...".toCharArray(),
                "XXX".toCharArray(),
                "...".toCharArray()
        };
        int targetRow2 = 2;
        int moves2 = countMoves(garage2, targetRow2);
        System.out.println("Moves 2: " + moves2 + " Expected: 0");

        char[][] garage3 = {
                "...".toCharArray(),
                "XXX".toCharArray(),
                ".X.".toCharArray(),
                "XXX".toCharArray(),
                "...".toCharArray()
        };
        int targetRow3 = 1;
        int moves3 = countMoves(garage3, targetRow3);
        System.out.println("Moves 3: " + moves3 + " Expected: 2");

        char[][] garage4 = {
                "...".toCharArray(),
                "X..".toCharArray(),
                ".XX".toCharArray(),
                "..X".toCharArray(),
                "XXX".toCharArray(),
                "...".toCharArray()
        };
        int targetRow4 = 4;
        int moves4 = countMoves(garage4, targetRow4);
        System.out.println("Moves 4: " + moves4 + " Expected: 1");

        char[][] garage5 = {
                ".....".toCharArray(),
                ".X.X.".toCharArray(),
                ".X.X.".toCharArray(),
                "X.X.X".toCharArray(),
                "X.X.X".toCharArray(),
                ".....".toCharArray()
        };
        int targetRow5 = 3;
        int moves5 = countMoves(garage5, targetRow5);
        System.out.println("Moves 5: " + moves5 + " Expected: 2");

        char[][] garage6 = {
                "........".toCharArray(),
                "..XX.X.X".toCharArray(),
                "X..X.X..".toCharArray(),
                "XX....XX".toCharArray(),
                ".XXX.XX.".toCharArray(),
                "X...XX.X.".toCharArray(),
                "..X.....".toCharArray(),
                ".X..X.X.".toCharArray(),
                "........".toCharArray()
        };
        int targetRow6 = 4;
        int moves6 = countMoves(garage6, targetRow6);
        System.out.println("Moves 6: " + moves6 + " Expected: 4");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i++).split(" ");
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);
            int targetRow = Integer.parseInt(data[2]);

            char[][] garage = new char[rows + 2][columns];

            for (int row = 1; row <= rows; row++) {
                garage[row] = lines.get(i).toCharArray();

                if (row != rows) {
                    i++;
                }
            }
            int moves = countMoves(garage, targetRow);
            output.add("Case #" + caseId + ": " + moves);
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
