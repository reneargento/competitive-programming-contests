package google.code.jam.code2020.round1a;

import java.util.Scanner;

/**
 * Created by Rene Argento on 03/04/20.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/000000000019fd74/00000000002b1353
public class PascalWalk {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        int[][] triangle = computeTriangle();

        for (int t = 1; t <= tests; t++) {
            int sum = scanner.nextInt();
            String directions = getDirections(triangle, sum);
            System.out.println("Case #" + t + ":\n" + directions);
        }
    }

    private static String getDirections(int[][] triangle, int sum) {
        if (sum <= 30) {
            return get30OrLessEdgeCase(sum);
        }

        int sumLess30 = sum - 30;
        String reverseBinary = getReverseBinary(sumLess30);
        String directions = getDirections(triangle, sum, reverseBinary);
        return directions.substring(0, directions.length() - 1);
    }

    private static int[][] computeTriangle() {
        int[][] triangle = new int[500][];

        for (int i = 0; i < triangle.length; i++) {
            triangle[i] = new int[i + 1];

            for (int j = 0; j < triangle[i].length; j++) {
                int value;
                if (j == 0 || j == triangle[i].length - 1) {
                    value = 1;
                } else {
                    value = triangle[i - 1][j - 1] + triangle[i - 1][j];
                }
                triangle[i][j] = value;
            }
        }
        return triangle;
    }

    private static String get30OrLessEdgeCase(int sum) {
        StringBuilder directions = new StringBuilder();

        for (int i = 1; i <= sum; i++) {
            directions.append(i).append(" ").append("1");

            if (i != sum) {
                directions.append("\n");
            }
        }

        return directions.toString();
    }

    private static String getReverseBinary(int number) {
        StringBuilder binary = new StringBuilder();

        while (number > 0) {
            int modBy2Result = number % 2;
            number /= 2;
            binary.append(modBy2Result);
        }

        return binary.toString();
    }

    private static String getDirections(int[][] triangle, int sum, String reverseBinary) {
        StringBuilder directions = new StringBuilder();
        int currentSum = 0;

        boolean isLeft = true;

        for (int i = 0; i < reverseBinary.length(); i++) {
            int bit = reverseBinary.charAt(i);

            if (bit == '0') {
                directions.append((i + 1)).append(" ");
                if (isLeft) {
                    directions.append("1");
                } else {
                    directions.append(triangle[i].length);
                }
                directions.append("\n");

                // Skip total line sum, but still add 1. This is why we reduce 30 in the beginning.
                currentSum++;
            } else {
                if (isLeft) {
                    for (int column = 0; column < triangle[i].length; column++) {
                        currentSum += triangle[i][column];
                        directions.append((i + 1)).append(" ").append(column + 1).append("\n");
                    }
                } else {
                    for (int column = triangle[i].length - 1; column >= 0; column--) {
                        currentSum += triangle[i][column];
                        directions.append((i + 1)).append(" ").append(column + 1).append("\n");
                    }
                }
                isLeft = !isLeft;
            }
        }

        if (currentSum < sum) {
            int row = reverseBinary.length();
            int difference = sum - currentSum;

            for (int i = 0; i < difference; i++) {
                directions.append((row + 1)).append(" ");

                if (isLeft) {
                    directions.append("1");
                } else {
                    directions.append(triangle[row].length);
                }
                directions.append("\n");
                row++;
            }
        }

        return directions.toString();
    }

}
