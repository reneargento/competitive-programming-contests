package uri.christmas.contest2022;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by Rene Argento on 25/12/22.
 */
public class DividindoPresentes {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        OutputWriter outputWriter = new OutputWriter(System.out);
        BigInteger giftsProduced = scanner.nextBigInteger();
        BigInteger kids = scanner.nextBigInteger();

        if (giftsProduced.compareTo(kids) < 0) {
            BigInteger giftsNeeded = kids.subtract(giftsProduced);
            outputWriter.printLine(String.format("Ainda faltam %d presentes!", giftsNeeded));
        } else {
            BigInteger giftsLeftover = giftsProduced.mod(kids);
            outputWriter.printLine(String.format("Sobraram %d presentes!", giftsLeftover));
        }
        outputWriter.flush();
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
