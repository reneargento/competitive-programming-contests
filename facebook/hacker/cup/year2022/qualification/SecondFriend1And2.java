package facebook.hacker.cup.year2022.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 26/08/22.
 */
public class SecondFriend1And2 {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2022/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "second_friend_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_friend_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "second_friend_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_friend_validation_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "second_friend_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_friend_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "second_second_friend_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_second_friend_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "second_second_friend_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "second_second_friend_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "second_second_friend_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "second_second_friend_output.txt";

    private static class Cell {
        int row;
        int column;

        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return row == cell.row && column == cell.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    private static final int[] neighborRows = { -1, 0, 0, 1 };
    private static final int[] neighborColumns = { 0, -1, 1, 0 };

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static char[][] addTrees(char[][] painting) {
        if (isImpossible(painting)) {
            return null;
        }
        boolean[][] goodCells = initGoodCells(painting);
        LinkedList<Cell> queue = getInitialBadCells(goodCells);

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();

            for (int i = 0; i < neighborRows.length; i++) {
                int neighborRow = cell.row + neighborRows[i];
                int neighborColumn = cell.column + neighborColumns[i];

                if (isValid(goodCells, neighborRow, neighborColumn)
                        && goodCells[neighborRow][neighborColumn]) {
                    int goodNeighbors = 0;

                    for (int j = 0; j < neighborRows.length; j++) {
                        int nextNeighborRow = neighborRow + neighborRows[j];
                        int nextNeighborColumn = neighborColumn + neighborColumns[j];

                        if (isValid(goodCells, nextNeighborRow, nextNeighborColumn)
                                && goodCells[nextNeighborRow][nextNeighborColumn]) {
                            goodNeighbors++;
                        }
                    }

                    if (goodNeighbors < 2) {
                        queue.offer(new Cell(neighborRow, neighborColumn));
                        goodCells[neighborRow][neighborColumn] = false;
                    }
                }
            }
        }

        for (int row = 0; row < painting.length; row++) {
            for (int column = 0; column < painting[0].length; column++) {
                if (painting[row][column] == '^' && !goodCells[row][column]) {
                    return null;
                } else if (painting[row][column] == '.' && goodCells[row][column]) {
                    painting[row][column] = '^';
                }
            }
        }
        return painting;
    }

    private static boolean isImpossible(char[][] painting) {
        boolean hasTree = false;

        for (int row = 0; row < painting.length; row++) {
            for (int column = 0; column < painting[0].length; column++) {
                if (painting[row][column] == '^') {
                    hasTree = true;
                    break;
                }
            }
        }
        return (painting.length == 1 || painting[0].length == 1) && hasTree;
    }

    private static boolean[][] initGoodCells(char[][] painting) {
        boolean[][] goodCells = new boolean[painting.length][painting[0].length];

        for (int row = 0; row < painting.length; row++) {
            for (int column = 0; column < painting[0].length; column++) {
                if (painting[row][column] != '#') {
                    goodCells[row][column] = true;
                }
            }
        }
        return goodCells;
    }

    private static LinkedList<Cell> getInitialBadCells(boolean[][] goodCells) {
        LinkedList<Cell> queue = new LinkedList<>();

        for (int row = 0; row < goodCells.length; row++) {
            for (int column = 0; column < goodCells[0].length; column++) {
                int goodNeighbors = 0;

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];

                    if (isValid(goodCells, neighborRow, neighborColumn)
                            && goodCells[neighborRow][neighborColumn]) {
                        goodNeighbors++;
                    }
                }

                if (goodNeighbors < 2) {
                    goodCells[row][column] = false;
                    queue.offer(new Cell(row, column));
                }
            }
        }
        return queue;
    }

    private static boolean isValid(boolean[][] painting, int row, int column) {
        return row >= 0 && row < painting.length && column >= 0 && column < painting[0].length;
    }

    private static void printPainting(char[][] painting) {
        for (char[] rows : painting) {
            for (int column = 0; column < painting[0].length; column++) {
                System.out.print(rows[column]);
            }
            System.out.println();
        }
    }

    private static void test() {
        char[][] painting1 = {
                ".^.".toCharArray()
        };
        char[][] result1 = addTrees(painting1);
        System.out.print("Result 1: ");
        if (result1 == null) {
            System.out.println("Impossible");
        } else {
            System.out.println("Possible");
            printPainting(result1);
        }

        char[][] painting2 = {
                ".".toCharArray(),
                ".".toCharArray(),
                ".".toCharArray()
        };
        char[][] result2 = addTrees(painting2);
        System.out.print("Result 2: ");
        if (result2 == null) {
            System.out.println("Impossible");
        } else {
            System.out.println("Possible");
            printPainting(result2);
        }

        char[][] painting3 = {
                "..^.".toCharArray(),
                "..^.".toCharArray(),
                "....".toCharArray(),
                "...^".toCharArray()
        };
        char[][] result3 = addTrees(painting3);
        System.out.print("Result 3: ");
        if (result3 == null) {
            System.out.println("Impossible");
        } else {
            System.out.println("Possible");
            printPainting(result3);
        }

        char[][] painting4 = {
                ".".toCharArray(),
                "#".toCharArray(),
                "#".toCharArray()
        };
        char[][] result4 = addTrees(painting4);
        System.out.print("Result 4: ");
        if (result4 == null) {
            System.out.println("Impossible");
        } else {
            System.out.println("Possible");
            printPainting(result4);
        }

        char[][] painting5 = {
                "..^.".toCharArray(),
                ".#^#".toCharArray(),
                "....".toCharArray(),
                "...^".toCharArray()
        };
        char[][] result5 = addTrees(painting5);
        System.out.print("Result 5: ");
        if (result5 == null) {
            System.out.println("Impossible");
        } else {
            System.out.println("Possible");
            printPainting(result5);
        }
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i++).split(" ");
            int rows = Integer.parseInt(data[0]);
            int columns = Integer.parseInt(data[1]);
            char[][] painting = new char[rows][columns];

            for (int j = 0; j < rows; j++) {
                char[] row = lines.get(i).toCharArray();
                painting[j] = row;

                if (j != rows - 1) {
                    i++;
                }
            }
            char[][] result = addTrees(painting);
            String resultLine = "Case #" + caseId + ": ";
            if (result == null) {
                resultLine += "Impossible";
                output.add(resultLine);
            } else {
                resultLine += "Possible";
                output.add(resultLine);

                for (char[] rowsValue : painting) {
                    StringBuilder line = new StringBuilder();
                    for (int column = 0; column < painting[0].length; column++) {
                        line.append(rowsValue[column]);
                    }
                    output.add(line.toString());
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
