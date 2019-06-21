package google.code.jam.code2019.qualification;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rene Argento on 06/04/19.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/0000000000051705/000000000008830b
public class Cryptopangrams {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            BigInteger maxPrime = new BigInteger(scanner.next());
            int listLength = scanner.nextInt();
            BigInteger[] numbers = new BigInteger[listLength];

            for (int i = 0; i < listLength; i++) {
                numbers[i] = new BigInteger(scanner.next());
            }

            String text = getPlainText(numbers);
            System.out.println("Case #" + t + ": " + text);
        }
    }

    private static String getPlainText(BigInteger[] numbers) {
        BigInteger[] output = new BigInteger[numbers.length + 1];
        BigInteger[] sortedOutput = new BigInteger[numbers.length + 1];

        // Find place to unzip
        int unzipIndex = -1;
        BigInteger factor = BigInteger.ONE;

        for (int i = 1; i < numbers.length; i++) {
            factor = gcd(numbers[i - 1], numbers[i]);

            if (!numbers[i].equals(numbers[i - 1]) && !factor.equals(BigInteger.ONE)) {
                unzipIndex = i;
                break;
            }
        }

        output[unzipIndex] = factor;
        sortedOutput[unzipIndex] = factor;

        unzipValuesLeft(numbers, output, sortedOutput, unzipIndex - 1);
        unzipValuesRight(numbers, output, sortedOutput, unzipIndex);

        Arrays.sort(sortedOutput);

        Map<BigInteger, Character> mapToLetters = new HashMap<>();
        int charIndex = 0;

        for (int i = 0; i < sortedOutput.length; i++) {
            if (mapToLetters.containsKey(sortedOutput[i])) {
                continue;
            }

            char charValue = (char) (charIndex + 'a');
            mapToLetters.put(sortedOutput[i], charValue);
            charIndex++;
        }

        StringBuilder text = new StringBuilder();

        for (BigInteger number : output) {
            char charValue = mapToLetters.get(number);
            text.append(charValue);
        }

        return text.toString().toUpperCase();
    }

    private static BigInteger gcd(BigInteger number1, BigInteger number2) {
        while (number2.compareTo(BigInteger.ZERO) > 0) {
            BigInteger temp = number2;
            number2 = number1.mod(number2);
            number1 = temp;
        }
        return number1;
    }

    private static void unzipValuesLeft(BigInteger[] numbers, BigInteger[] output, BigInteger[] sortedOutput, int index) {
        for (int i = index; i >= 0; i--) {
            BigInteger nextValue = numbers[i].divide(output[i + 1]);
            output[i] = nextValue;
            sortedOutput[i] = nextValue;
        }
    }

    private static void unzipValuesRight(BigInteger[] numbers, BigInteger[] output, BigInteger[] sortedOutput, int index) {
        for (int i = index; i < numbers.length; i++) {
            BigInteger nextValue = numbers[i].divide(output[i]);
            output[i + 1] = nextValue;
            sortedOutput[i + 1] = nextValue;
        }
    }
}
