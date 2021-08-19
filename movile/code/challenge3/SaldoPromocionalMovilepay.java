package movile.code.challenge3;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 29/03/21.
 */
public class SaldoPromocionalMovilepay {

    private static class Deposit implements Comparable<Deposit> {
        int value;
        int daysBeforeExpire;

        public Deposit(int value, int daysBeforeExpire) {
            this.value = value;
            this.daysBeforeExpire = daysBeforeExpire;
        }

        @Override
        public int compareTo(Deposit other) {
            if (daysBeforeExpire != other.daysBeforeExpire) {
                return Integer.compare(daysBeforeExpire, other.daysBeforeExpire);
            }
            return Integer.compare(value, other.value);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int depositsNumber = scanner.nextInt();

        Deposit[] deposits = new Deposit[depositsNumber];
        for (int i = 0; i < deposits.length; i++) {
            int value = scanner.nextInt();
            int daysBeforeExpire = scanner.nextInt();
            deposits[i] = new Deposit(value, daysBeforeExpire);
        }
        Arrays.sort(deposits);

        int purchases = scanner.nextInt();
        int currentDepositIndex = 0;

        for (int i = 0; i < purchases; i++) {
            int targetValue = scanner.nextInt();

            if (canPurchase(deposits, currentDepositIndex, targetValue)) {
                for (int j = currentDepositIndex; j < deposits.length; j++) {
                    int currentValue = deposits[j].value;
                    deposits[j].value = Math.max(0, deposits[j].value - targetValue);
                    targetValue -= currentValue;

                    if (targetValue <= 0) {
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < deposits.length; i++) {
            System.out.println(deposits[i].value);
        }
    }

    private static boolean canPurchase(Deposit[] deposits, int index, int targetValue) {
        int sum = 0;

        for (int i = index; i < deposits.length; i++) {
            sum += deposits[i].value;
            if (sum >= targetValue) {
                return true;
            }
        }
        return false;
    }

}
