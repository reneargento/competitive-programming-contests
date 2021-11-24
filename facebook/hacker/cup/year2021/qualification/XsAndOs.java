package facebook.hacker.cup.year2021.qualification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 28/08/21.
 */
public class XsAndOs {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2021/Qualification/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "xs_and_os_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "xs_and_os_sample_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "xs_and_os_validation_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "xs_and_os_validation_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "xs_and_os_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "xs_and_os_output.txt";

    private static class Moves {
        int minimumMoves;
        int setsOfMoves;

        public Moves(int minimumMoves, int setsOfMoves) {
            this.minimumMoves = minimumMoves;
            this.setsOfMoves = setsOfMoves;
        }
    }

    private static class MoveCheckResult {
        List<Set<Cell>> setsOfMoves;
        int minimumMoves;

        public MoveCheckResult(List<Set<Cell>> setsOfMoves, int minimumMoves) {
            this.setsOfMoves = setsOfMoves;
            this.minimumMoves = minimumMoves;
        }
    }

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

    public static Moves checkMoves(char[][] board) {
        int minimumMoves = Integer.MAX_VALUE;
        List<Set<Cell>> setsOfMoves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            Set<Cell> cellsMarkedRow = countMovesOnRow(board, i);
            MoveCheckResult moveCheckResultRow = checkResult(minimumMoves, setsOfMoves, cellsMarkedRow);
            minimumMoves = moveCheckResultRow.minimumMoves;
            setsOfMoves = moveCheckResultRow.setsOfMoves;

            Set<Cell> cellsMarkedColumn = countMovesOnColumn(board, i);
            MoveCheckResult moveCheckResultColumn = checkResult(minimumMoves, setsOfMoves, cellsMarkedColumn);
            minimumMoves = moveCheckResultColumn.minimumMoves;
            setsOfMoves = moveCheckResultColumn.setsOfMoves;
        }

        if (minimumMoves != Integer.MAX_VALUE) {
            return new Moves(minimumMoves, setsOfMoves.size());
        }
        return null;
    }

    private static MoveCheckResult checkResult(int minimumMoves, List<Set<Cell>> setsOfMoves,
                                               Set<Cell> cellsMarked) {
        if (cellsMarked != null) {
            if (cellsMarked.size() == minimumMoves) {
                if (isNewSet(cellsMarked, setsOfMoves)) {
                    setsOfMoves.add(cellsMarked);
                }
            } else if (cellsMarked.size() < minimumMoves) {
                minimumMoves = cellsMarked.size();
                setsOfMoves = new ArrayList<>();
                setsOfMoves.add(cellsMarked);
            }
        }
        return new MoveCheckResult(setsOfMoves, minimumMoves);
    }

    private static boolean isNewSet(Set<Cell> cellsMarked, List<Set<Cell>> setsOfMoves) {
        for (Set<Cell> set : setsOfMoves) {
            if (set.containsAll(cellsMarked)) {
                return false;
            }
        }
        return true;
    }

    private static Set<Cell> countMovesOnRow(char[][] board, int row) {
        Set<Cell> cellsMarked = new HashSet<>();

        for (int column = 0; column < board[0].length; column++) {
            if (board[row][column] == 'O') {
                return null;
            } else if (board[row][column] == '.') {
                cellsMarked.add(new Cell(row, column));
            }
        }
        return cellsMarked;
    }

    private static Set<Cell> countMovesOnColumn(char[][] board, int column) {
        Set<Cell> cellsMarked = new HashSet<>();

        for (int row = 0; row < board.length; row++) {
            if (board[row][column] == 'O') {
                return null;
            } else if (board[row][column] == '.') {
                cellsMarked.add(new Cell(row, column));
            }
        }
        return cellsMarked;
    }

    public static void main(String[] args) {
        test();
//        compete();
    }

    private static void test() {
        char[][] board1 = new char[2][2];
        board1[0] = "XO".toCharArray();
        board1[1] = "..".toCharArray();
        Moves moves1 = checkMoves(board1);
        System.out.println("Moves 1: " + getResult(moves1) + " Expected: 1 1");

        char[][] board2 = new char[2][2];
        board2[0] = "X.".toCharArray();
        board2[1] = ".O".toCharArray();
        Moves moves2 = checkMoves(board2);
        System.out.println("Moves 2: " + getResult(moves2) + " Expected: 1 2");

        char[][] board3 = new char[3][3];
        board3[0] = "...".toCharArray();
        board3[1] = "...".toCharArray();
        board3[2] = "...".toCharArray();
        Moves moves3 = checkMoves(board3);
        System.out.println("Moves 3: " + getResult(moves3) + " Expected: 3 6");

        char[][] board4 = new char[3][3];
        board4[0] = ".OX".toCharArray();
        board4[1] = "X..".toCharArray();
        board4[2] = "..O".toCharArray();
        Moves moves4 = checkMoves(board4);
        System.out.println("Moves 4: " + getResult(moves4) + " Expected: 2 2");

        char[][] board5 = new char[3][3];
        board5[0] = "OXO".toCharArray();
        board5[1] = "X.X".toCharArray();
        board5[2] = "OXO".toCharArray();
        Moves moves5 = checkMoves(board5);
        System.out.println("Moves 5: " + getResult(moves5) + " Expected: 1 1");

        char[][] board6 = new char[3][3];
        board6[0] = ".XO".toCharArray();
        board6[1] = "O.X".toCharArray();
        board6[2] = "XO.".toCharArray();
        Moves moves6 = checkMoves(board6);
        System.out.println("Moves 6: " + getResult(moves6) + " Expected: Impossible");

        char[][] board7 = new char[4][4];
        board7[0] = "X...".toCharArray();
        board7[1] = ".O.O".toCharArray();
        board7[2] = ".XX.".toCharArray();
        board7[3] = "O.XO".toCharArray();
        Moves moves7 = checkMoves(board7);
        System.out.println("Moves 7: " + getResult(moves7) + " Expected: 2 2");

        char[][] board8 = new char[5][5];
        board8[0] = "OX.OO".toCharArray();
        board8[1] = "X.XXX".toCharArray();
        board8[2] = "OXOOO".toCharArray();
        board8[3] = "OXOOO".toCharArray();
        board8[4] = "XXXX.".toCharArray();
        Moves moves8 = checkMoves(board8);
        System.out.println("Moves 8: " + getResult(moves8) + " Expected: 1 2");

        char[][] board9 = new char[11][11];
        board9[0] = "XXOO.O.XOX.".toCharArray();
        board9[1] = ".XOOOO.X..O".toCharArray();
        board9[2] = "XXOOO..XOO.".toCharArray();
        board9[3] = "XXO...OXOOO".toCharArray();
        board9[4] = "XX.OO..XOO.".toCharArray();
        board9[5] = "XX..O..XO.O".toCharArray();
        board9[6] = "XXXXXXX.XXX".toCharArray();
        board9[7] = "XXOOOO.X...".toCharArray();
        board9[8] = "XX.OO..X...".toCharArray();
        board9[9] = "XOOOOO.X.X.".toCharArray();
        board9[10] = "XO..O..X...".toCharArray();
        Moves moves9 = checkMoves(board9);
        System.out.println("Moves 9: " + getResult(moves9) + " Expected: 1 2");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size(); i++) {
            int size = Integer.parseInt(lines.get(i++));
            char[][] board = new char[size][size];

            for (int j = 0; j < size; j++) {
                String row = lines.get(i);
                board[j] = row.toCharArray();

                if (j != size - 1) {
                    i++;
                }
            }

            Moves moves = checkMoves(board);
            output.add("Case #" + caseId + ": " + getResult(moves));
            caseId++;
        }
        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static String getResult(Moves moves) {
        String result = "Impossible";
        if (moves != null) {
            result = moves.minimumMoves + " " + moves.setsOfMoves;
        }
        return result;
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
