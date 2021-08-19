package movile.code.challenge3;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 29/03/21.
 */
public class ReunioesMovilepay {

    private static class Person {
        Meeting[] meetings;

        public Person(Meeting[] meetings) {
            this.meetings = meetings;
        }

        boolean isAvailable(int start, int length) {
            for (int i = 0; i < meetings.length; i++) {
                if (start <= meetings[i].start && start + length >= meetings[i].end) {
                    return false;
                }
                if (start > meetings[i].start && start + length <= meetings[i].end) {
                    return false;
                }
                if (start <= meetings[i].start && start + length >= meetings[i].start) {
                    return false;
                }
                if (start < meetings[i].end && start + length >= meetings[i].end) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class Meeting implements Comparable<Meeting> {
        int start;
        int end;

        public Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Meeting other) {
            return Integer.compare(start, other.start);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int peopleNumber = scanner.nextInt();
        int hours = scanner.nextInt() - 1;
        scanner.nextLine();
        Person[] people = new Person[peopleNumber];

        for (int i = 0; i < people.length; i++) {
            String[] meetingsForPerson = scanner.nextLine().split(" ");
            Meeting[] meetings = new Meeting[meetingsForPerson.length];

            for (int m = 0; m < meetingsForPerson.length; m++) {
                String[] hoursTime = meetingsForPerson[m].split(",");
                int start = Integer.parseInt(hoursTime[0]);
                int end = Integer.parseInt(hoursTime[1]);
                meetings[m] = new Meeting(start, end);
            }
            people[i] = new Person(meetings);
        }

        for (int i = 0; i < people.length; i++) {
            Arrays.sort(people[i].meetings);
        }

        boolean foundTime = false;
        for (int i = 0; i < 24 - hours; i++) {
            boolean possible = true;

            for (Person person : people) {
                if (!person.isAvailable(i, hours)) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                foundTime = true;
                System.out.printf("O primeiro horario possivel para a reuniao eh das %dh00 as %dh00\n", i, i + hours);
                break;
            }
        }

        if (!foundTime) {
            System.out.println("Nao existe horario no dia para marcar a reuniao");
        }
    }
}
