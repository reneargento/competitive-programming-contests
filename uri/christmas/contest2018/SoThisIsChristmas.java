package uri.christmas.contest2018;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/12/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/412/1
public class SoThisIsChristmas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int repeats = scanner.nextInt();
        StringBuilder letters = new StringBuilder();

        for (int i = 0; i < repeats; i++) {
            letters.append('a');
        }

        String lettersString = letters.toString();
        String phrase = "Ent" + lettersString + "o eh N" + lettersString + "t" + lettersString + "l!";
        System.out.println(phrase);
    }

}
