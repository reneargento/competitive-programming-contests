package uri.christmas.contest2018;

import java.util.Scanner;

/**
 * Created by Rene Argento on 25/12/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/412/5
public class FinalThesis {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int deliveredDate = scanner.nextInt();
        int verificationDate = scanner.nextInt();

        if (deliveredDate > verificationDate) {
            System.out.println("Eu odeio a professora!");
        } else if (deliveredDate <= verificationDate - 3) {
            System.out.println("Muito bem! Apresenta antes do Natal!");
        } else {
            System.out.println("Parece o trabalho do meu filho!");

            if (deliveredDate + 2 >= 24) {
                System.out.println("Fail! Entao eh nataaaaal!");
            } else {
                System.out.println("TCC Apresentado!");
            }
        }
    }

}
