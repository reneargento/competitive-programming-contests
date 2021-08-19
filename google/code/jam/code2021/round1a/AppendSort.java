package google.code.jam.code2021.round1a;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by Rene Argento on 26/03/21.
 */
public class AppendSort {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int numbers = scanner.nextInt();
            BigInteger previousNumber = BigInteger.valueOf(scanner.nextInt());
            long operations = 0;

            for (int i = 1; i < numbers; i++) {
                BigInteger number = BigInteger.valueOf(scanner.nextInt());
                int digitsAdded = 0;

                if (number.compareTo(previousNumber) <= 0) {
                    String numberStringValue = String.valueOf(number);
                    StringBuilder numberStringBuilder = new StringBuilder(numberStringValue);
                    String previousNumberString = String.valueOf(previousNumber);

                    boolean isStartEqual = previousNumberString.startsWith(numberStringValue);

                    while (numberStringBuilder.length() != previousNumberString.length()) {
                        if (isStartEqual) {
                            int index = numberStringBuilder.length();
                            numberStringBuilder.append(previousNumberString.charAt(index));
                        } else {
                            numberStringBuilder.append("0");
                        }
                        operations++;
                        digitsAdded++;
                    }

                    String numberString = numberStringBuilder.toString();
                    BigInteger newNumber = new BigInteger(numberString);

                    if (newNumber.compareTo(previousNumber) <= 0) {
                        boolean updated = false;

                        for (int j = 1; j <= digitsAdded; j++) {
                            int index = previousNumberString.length() - j;
                            char digit = previousNumberString.charAt(index);

                            if (digit != '9') {
                                int nextDigit = Character.getNumericValue(digit) + 1;
                                String nextNumberString = numberString.substring(0, index) + nextDigit +
                                        numberString.substring(index + 1);

                                BigInteger nextNumber = new BigInteger(nextNumberString);
                                if (nextNumber.compareTo(previousNumber) > 0) {
                                    numberString = nextNumberString;
                                    updated = true;
                                    break;
                                }
                            } else {
                                numberString = numberString.substring(0, index) + "0" +
                                        numberString.substring(index + 1);
                            }
                        }

                        if (!updated) {
                            numberString += "0";
                            operations++;
                        }
                    }
                    previousNumber = new BigInteger(numberString);
                } else {
                    previousNumber = number;
                }
            }
            System.out.printf("Case #%d: %d\n", t, operations);
        }
    }
}
