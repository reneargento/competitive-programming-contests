package uri.christmas.contest2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/18.
 */
// https://www.urionlinejudge.com.br/judge/pt/challenges/view/412/9
// TODO
public class NoelAndHisReindeer {

    private static class FastReader {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        /** Call this method to initialize reader for InputStream */
        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        /** Get next word */
        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        //Used to check EOF
        //If getLine() == null, it is a EOF
        //Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int numbersSize = FastReader.nextInt();
        int[] numbers = new int[numbersSize];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = FastReader.nextInt();
        }

        int sequenceSize = longestIncreasingSubsequence(numbers);
        System.out.println(sequenceSize);
    }

    // Binary search (note boundaries in the caller)
    // A[] is ceilIndex in the caller
    private static int CeilIndex(int A[], int l, int r, int key) {
        while (r - l > 1) {
            int m = l + (r - l) / 2;
            if (A[m] >= key)
                r = m;
            else
                l = m;
        }

        return r;
    }

    private static int longestIncreasingSubsequence(int array[]) {
        // Add boundary case, when array size is one

        int[] tailTable = new int[array.length];
        int length; // always points empty slot

        tailTable[0] = array[0];
        length = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < tailTable[0]) {
                // new smallest value
                tailTable[0] = array[i];
            } else if (array[i] == tailTable[length - 1] + 1) {
                // A[i] wants to extend largest subsequence
                tailTable[length++] = array[i];
            } else {
                // A[i] wants to be current end candidate of an existing
                // subsequence. It will replace ceil value in tailTable
                tailTable[CeilIndex(tailTable, -1, length - 1, array[i])] = array[i];
            }
        }

        boolean shouldAddExtra = false;

        for (int i = 1; i < array.length; i++) {
            if (array[i] + 1 == tailTable[length - 1] + 1) {
                shouldAddExtra = true;
            }
        }

        if (shouldAddExtra) {
            length++;
        }

        return length;
    }

    private static class LIS {
        // Binary search (note boundaries in the caller)
        // A[] is ceilIndex in the caller
        private static int CeilIndex(int A[], int l, int r, int key) {
            while (r - l > 1) {
                int m = l + (r - l) / 2;
                if (A[m] >= key)
                    r = m;
                else
                    l = m;
            }

            return r;
        }

        private static int longestIncreasingSubsequenceLength(int array[]) {
            // Add boundary case, when array size is one

            int[] tailTable = new int[array.length];
            int length; // always points empty slot

            tailTable[0] = array[0];
            length = 1;
            for (int i = 1; i < array.length; i++) {
                if (array[i] < tailTable[0]) {
                    // new smallest value
                    tailTable[0] = array[i];
                } else if (array[i] > tailTable[length - 1]) {
                    // A[i] wants to extend largest subsequence
                    tailTable[length++] = array[i];
                } else {
                    // A[i] wants to be current end candidate of an existing
                    // subsequence. It will replace ceil value in tailTable
                    tailTable[CeilIndex(tailTable, -1, length - 1, array[i])] = array[i];
                }

            }

            return length;
        }

        // Driver program to test above function
        public static void main(String[] args) {
            int A[] = { 2, 5, 3, 7, 11, 8, 10, 13, 6 };
            System.out.println("Length of Longest Increasing Subsequence is " + longestIncreasingSubsequenceLength(A));
        }
    }

}