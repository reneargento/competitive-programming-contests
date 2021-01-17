package uri.christmas.contest2020;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

/**
 * Created by Rene Argento on 19/12/20.
 */
// https://www.urionlinejudge.com.br/judge/pt/challenges/view/550/6
public class EstrelaDeNatal {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int revolutions = scanner.nextInt();

        LocalDate initialDate = getLocalDate(21, 12, 2020);

        LocalDate futureJupiterDate = initialDate;
        long totalJupiterDays = 0;
        if (revolutions != 0) {
            futureJupiterDate = getFutureDate(revolutions, 11.9);
            totalJupiterDays = numberOfDays(futureJupiterDate.getDayOfMonth(), futureJupiterDate.getMonthValue(), futureJupiterDate.getYear());
        }

        System.out.println("Dias terrestres para Jupiter = " + totalJupiterDays);
        System.out.println("Data terrestre para Jupiter: " + futureJupiterDate.format(dateTimeFormatter));

        LocalDate futureSaturnDate = initialDate;
        long totalSaturnDays = 0;
        if (revolutions != 0) {
            futureSaturnDate = getFutureDate(revolutions, 29.6);
            totalSaturnDays = numberOfDays(futureSaturnDate.getDayOfMonth(), futureSaturnDate.getMonthValue(), futureSaturnDate.getYear());
        }

        System.out.println("Dias terrestres para Saturno = " + totalSaturnDays);
        System.out.println("Data terrestre para Saturno: " + futureSaturnDate.format(dateTimeFormatter));
    }

    private static LocalDate getFutureDate(int revolutions, double rate) {
        LocalDate date = LocalDate.parse("2020-12-21", dateTimeFormatter);
        int days = (int) (365.25 * rate * revolutions);
        date = date.plusDays(days);
        return date;
    }

    private static LocalDate getLocalDate(int day, int month, int year) {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String date = year + "-" + formattedMonth + "-" + formattedDay;
        return LocalDate.parse(date, dateTimeFormatter);
    }

    private static long numberOfDays(int day, int month, int year) {
        LocalDate date1 = LocalDate.parse("2020-12-21", dateTimeFormatter);
        LocalDate date2 = getLocalDate(day, month, year);
        return ChronoUnit.DAYS.between(date1, date2);
    }
}
