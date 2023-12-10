package uri.christmas.contest2022;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/22.
 */
public class PapaiNoelEmHogwarts {

    public static void main(String[] args) throws IOException {
        FastReader.init();
        OutputWriter outputWriter = new OutputWriter(System.out);
        char[][] board = new char[8][8];
        for (int i = 0; i < board.length; i++) {
            board[i] = FastReader.next().toCharArray();
        }
        countAdjacentBombs(board);

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                outputWriter.print(board[row][column]);
            }
            outputWriter.printLine();
        }
        outputWriter.flush();
    }

    private static void countAdjacentBombs(char[][] board) {
        int[] neighborRows = { -2, -2, -1, -1, 1, 1, 2, 2 };
        int[] neighborColumns = { -1, 1, -2, 2, -2, 2, -1, 1 };

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == '*' || board[row][column] == 'K') {
                    continue;
                }
                int adjacentBombs = 0;

                for (int i = 0; i < neighborRows.length; i++) {
                    int neighborRow = row + neighborRows[i];
                    int neighborColumn = column + neighborColumns[i];
                    if (isValid(board, neighborRow, neighborColumn)
                            && board[neighborRow][neighborColumn] == '*') {
                        adjacentBombs++;
                    }
                }
                board[row][column] = String.valueOf(adjacentBombs).charAt(0);
            }
        }
    }

    private static boolean isValid(char[][] grid, int row, int column) {
        return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }
    }

    private static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void flush() {
            writer.flush();
        }
    }
}
