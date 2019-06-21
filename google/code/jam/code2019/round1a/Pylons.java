package google.code.jam.code2019.round1a;

import java.util.*;

/**
 * Created by Rene Argento on 13/04/19.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/0000000000051635/0000000000104e03
public class Pylons {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();

            String[] path = getPath(rows, columns);

            if (path != null) {
                System.out.println("Case #" + t + ": POSSIBLE");
                for (int i = 0; i < path.length; i++) {
                    System.out.println(path[i]);
                }
            } else {
                System.out.println("Case #" + t + ": IMPOSSIBLE");
            }
        }
    }

    private static class Cell {
        int row;
        int column;

        Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Cell)) {
                return false;
            }

            Cell otherCell = (Cell) object;
            return (this.row == otherCell.row) && (this.column == otherCell.column);
        }

        @Override
        public int hashCode() {
            return row * 31 + column;
        }
    }

    private static String[] getPath(int rows, int columns) {
        int cells = rows * columns;
        Random random = new Random();

        for (int attempt = 0; attempt < 10000; attempt++) {
            int visits = 0;
            String[] path = new String[cells];
            Set<Cell> visitedCells = new HashSet<>();

            int currentRow = random.nextInt(rows);
            int currentColumn = random.nextInt(columns);
            Cell currentCell = new Cell(currentRow, currentColumn);

            while (visits < cells) {
                visitedCells.add(currentCell);

                path[visits++] = String.valueOf(currentCell.row + 1) + " " + String.valueOf(currentCell.column + 1);

                List<Cell> validCells = getValidCells(currentCell, visitedCells, rows, columns);

                if (validCells.isEmpty()) {
                    break;
                }

                int randomId = random.nextInt(validCells.size());
                currentCell = validCells.get(randomId);
            }

            if (visits == cells) {
                return path;
            }
        }

        return null;
    }

    private static List<Cell> getValidCells(Cell currentCell, Set<Cell> visitedCells, int maxRow, int maxColumn) {
        List<Cell> validCells = new ArrayList<>();

        for (int row = 0; row < maxRow; row++) {
            for (int column = 0; column < maxColumn; column++) {
                if (row != currentCell.row
                        && column != currentCell.column
                        && (currentCell.row - currentCell.column != row - column)
                        && (currentCell.row + currentCell.column != row + column)) {
                    Cell candidateCell = new Cell(row, column);

                    if (!visitedCells.contains(candidateCell)) {
                        validCells.add(candidateCell);
                    }
                }
            }
        }

        return validCells;
    }

}