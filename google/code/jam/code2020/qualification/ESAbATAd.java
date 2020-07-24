package google.code.jam.code2020.qualification;

import java.util.Scanner;

/**
 * Created by Rene Argento on 03/04/20.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/000000000019fd27/0000000000209a9e
public class ESAbATAd {

    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        int bitsSize = scanner.nextInt();
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            String bits = getBits(bitsSize);
            System.out.println(bits);
            String result = scanner.nextLine();
            if (result.charAt(0) == 'N') {
                return;
            }
        }
    }

    public static String getBits(int bitsSize) {
        int[] bits = new int[bitsSize];
        int finalBit = bitsSize / 2;

        int complementPairStart = -1;
        int reversePairStart = -1;

        int queryNumber = 0;
        int bitIndex = 0;

        while (bitIndex < finalBit){

            if (queryNumber > 0 && queryNumber % 10 == 0) {
                checkQuantumFluctuations(bits, complementPairStart, reversePairStart);
            } else {
                int reverseIndex = bitsSize - bitIndex - 1;

                int bit = getBit(bitIndex + 1);
                int reverseIndexBit = getBit(reverseIndex + 1);

                bits[bitIndex] = bit;
                bits[reverseIndex] = reverseIndexBit;

                if (complementPairStart == -1 && bits[bitIndex] == bits[reverseIndex]) {
                    complementPairStart = bitIndex;
                }

                if (reversePairStart == -1 && bits[bitIndex] != bits[reverseIndex]) {
                    reversePairStart = bitIndex;
                }
                bitIndex++;
            }
            queryNumber += 2;
        }

        return getBitString(bits);
    }

    private static int getBit(int index) {
        System.out.println(index);
        int bit = scanner.nextInt();
        scanner.nextLine();
        return bit;
    }

    private static void checkQuantumFluctuations(int[] bits, int complementPairStart, int reversePairStart) {
        boolean isComplement = false;
        boolean isReverse = false;

        if (complementPairStart >= 0) {
            if (isComplement(bits, complementPairStart)) {
                isComplement = true;
            }
        } else {
            getBit(1);
        }

        if (reversePairStart >= 0) {
            if (isReverse(bits, reversePairStart, isComplement)) {
                isReverse = true;
            }
        } else {
            getBit(1);
        }

        if (isComplement) {
            complementBits(bits);
        }
        if (isReverse) {
            reverseBits(bits);
        }
    }

    private static boolean isComplement(int[] bits, int startIndex) {
        int newValue = getBit(startIndex + 1);

        int complementValue = bits[startIndex] ^ 1;
        return newValue == complementValue;
    }

    private static boolean isReverse(int[] bits, int startIndex, boolean complementHappened) {
        int newValue = getBit(startIndex + 1);

        return ((complementHappened && bits[startIndex] == newValue)
                || (!complementHappened && bits[startIndex] != newValue));
    }

    private static void complementBits(int[] bits) {
        for (int i = 0; i < bits.length; i++) {
            bits[i] ^= 1;
        }
    }

    private static void reverseBits(int[] bits) {
        int middleIndex = bits.length / 2;

        for (int i = 0; i < middleIndex; i++) {
            int reverseIndex = bits.length - i - 1;
            int reverseValue = bits[reverseIndex];
            bits[reverseIndex] = bits[i];
            bits[i] = reverseValue;
        }
    }

    private static String getBitString(int[] bits) {
        StringBuilder bitString = new StringBuilder();
        for (int bit : bits) {
            bitString.append(bit);
        }
        return bitString.toString();
    }
}
