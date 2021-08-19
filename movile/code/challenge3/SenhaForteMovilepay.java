package movile.code.challenge3;

import java.util.Scanner;

/**
 * Created by Rene Argento on 29/03/21.
 */
public class SenhaForteMovilepay {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();
        scanner.nextLine();
        String password = scanner.nextLine();

        int charactersNeeded = 0;
        boolean hasDigit = false;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
            if (Character.isDigit(character)) {
                hasDigit = true;
            }
            if (Character.isUpperCase(character)) {
                hasUppercase = true;
            }
            if (Character.isLowerCase(character)) {
                hasLowercase = true;
            }
            if (character == '!' || character == '@' || character == '#'
                    || character == '$' || character == '%' || character == '^'
                    || character == '&' || character == '*' || character == '('
                    || character == ')' || character == '-' || character == '+' ) {
                hasSpecial = true;
            }
        }

        if (!hasDigit) {
            charactersNeeded++;
        }
        if (!hasUppercase) {
            charactersNeeded++;
        }
        if (!hasLowercase) {
            charactersNeeded++;
        }
        if (!hasSpecial) {
            charactersNeeded++;
        }

        if (password.length() + charactersNeeded < 6) {
            charactersNeeded += 6 - (password.length() + charactersNeeded);
        }
        System.out.println(charactersNeeded);
    }
}
