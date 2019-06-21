package google.code.jam.code2019.qualification;

import java.util.*;

/**
 * Created by Rene Argento on 06/04/19.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/0000000000051705/00000000000881da
public class YouCanGoYourOwnWay {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int gridDimension = scanner.nextInt(); // not used
            String pathTaken = scanner.next();
            String path = getPath(pathTaken);
            System.out.println("Case #" + t + ": " + path);
        }
    }

    private static String getPath(String pathTaken) {
        StringBuilder path = new StringBuilder();

        for (int i = 0; i < pathTaken.length(); i++) {
            if (pathTaken.charAt(i) == 'S') {
                path.append('E');
            } else {
                path.append('S');
            }
        }

        return path.toString();
    }
}
