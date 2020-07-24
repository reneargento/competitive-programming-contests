package google.code.jam.code2020.qualification;

import java.util.Scanner;

/**
 * Created by Rene Argento on 03/04/20.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/000000000019fd27/0000000000209a9f
public class NestingDepth {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            String string = scanner.next();
            String nestedString = getNestedString(string);
            System.out.println("Case #" + t + ": " + nestedString);
        }
    }

    private static String getNestedString(String string) {
        StringBuilder nestedString = new StringBuilder();
        int previousNumber = 0;
        int currentNumber = 0;

        for (int i = 0; i < string.length(); i++) {
            currentNumber = Character.getNumericValue(string.charAt(i));

            if (currentNumber > previousNumber) {
                int difference = currentNumber - previousNumber;

                for (int s = 0; s < difference; s++) {
                    nestedString.append("(");
                }
            } else if (currentNumber < previousNumber) {
                int difference = previousNumber - currentNumber;

                for (int s = 0; s < difference; s++) {
                    nestedString.append(")");
                }
            }
            previousNumber = currentNumber;
            nestedString.append(currentNumber);
        }

        for (int s = 0; s < currentNumber; s++) {
            nestedString.append(")");
        }
        return nestedString.toString();
    }

}
