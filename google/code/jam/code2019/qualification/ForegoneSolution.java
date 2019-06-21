package google.code.jam.code2019.qualification;

import java.util.Scanner;

/**
 * Created by Rene Argento on 05/04/19.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/0000000000051705/0000000000088231
public class ForegoneSolution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            String number = scanner.next();
            String numbers = getNumbers(number);
            System.out.println("Case #" + t + ": " + numbers);
        }
    }

    private static String getNumbers(String number) {
        StringBuilder complementNumber = new StringBuilder();

        for (int i = 0; i < number.length(); i++) {
            complementNumber.append('0');
        }

        StringBuilder newNumber = new StringBuilder();
        boolean isComplement0 = true;

        for (int i = 0; i < number.length(); i++) {
            char currentChar = number.charAt(i);
            if (currentChar == '4') {
                newNumber.append('3');
                complementNumber.replace(i, i + 1, "1");
                isComplement0 = false;
            } else {
                newNumber.append(currentChar);
            }
        }

        String firstNumber = newNumber.toString();
        String secondNumber;

        if (!isComplement0) {
            int startIndex = 0;

            for (int i = 0; i < complementNumber.length(); i++) {
                if (complementNumber.charAt(i) != '0') {
                    startIndex = i;
                    break;
                }
            }

            secondNumber = complementNumber.substring(startIndex);
        } else {
            secondNumber = "0";
        }

        return firstNumber + " " + secondNumber;
    }
}
