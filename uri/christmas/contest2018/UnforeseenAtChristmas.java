package uri.christmas.contest2018;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/12/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/412/2
public class UnforeseenAtChristmas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int students = scanner.nextInt();
        int computers = scanner.nextInt();
        int burned = scanner.nextInt();
        int noCompiler = scanner.nextInt();

        int usable = computers - burned - noCompiler;

        if (students <= usable - 1) {
            System.out.println("Igor feliz!");
        } else {
            if (burned > noCompiler / 2) {
                System.out.println("Caio, a culpa eh sua!");
            } else {
                System.out.println("Igor bolado!");
            }
        }
    }
}