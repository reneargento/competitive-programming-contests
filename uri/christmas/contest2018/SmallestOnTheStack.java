package uri.christmas.contest2018;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Rene Argento on 25/12/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/412/4
public class SmallestOnTheStack {

    public static class FastReader {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

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

        int operations = FastReader.nextInt();
        Deque<Integer> stack = new ArrayDeque<>();
        Deque<Integer> lowestValuesStack = new ArrayDeque<>();

        for (int i = 0; i < operations; i++) {
            String operation = FastReader.next();

            if (operation.equals("PUSH")) {
                int value = FastReader.nextInt();
                stack.push(value);

                if (lowestValuesStack.isEmpty() || lowestValuesStack.peek() >= value) {
                    lowestValuesStack.push(value);
                }
            } else if (operation.equals("POP")) {
                if (stack.isEmpty()) {
                    System.out.println("EMPTY");
                } else {
                    int valueToRemove = stack.peek();

                    if (lowestValuesStack.peek() == valueToRemove) {
                        lowestValuesStack.pop();
                    }

                    stack.pop();
                }
            } else {
                if (stack.isEmpty()) {
                    System.out.println("EMPTY");
                } else {
                    System.out.println(lowestValuesStack.peek());
                }
            }
        }
    }

}
