package uri.christmas.contest2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rene Argento on 19/12/20.
 */
// https://www.urionlinejudge.com.br/judge/pt/challenges/view/550/3
public class TimeDeDuendes {

    private static class Elf implements Comparable<Elf> {
        String name;
        int age;

        public Elf(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Elf other) {
            if (other.age != age) {
                return other.age - age;
            }
            return name.compareTo(other.name);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int elvesCount = scanner.nextInt();
        Elf[] elves = new Elf[elvesCount];

        for (int t = 0; t < elvesCount; t++) {
            String name = scanner.next();
            int age = scanner.nextInt();
            elves[t] = new Elf(name, age);
        }
        Arrays.sort(elves);

        List<Elf>[] teams = new ArrayList[elvesCount / 3];
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new ArrayList<>();
        }
        int currentTeam = 0;

        for (int i = 0; i < elves.length; i++) {
            teams[currentTeam].add(elves[i]);
            currentTeam = (currentTeam + 1) % teams.length;
        }

        for (int i = 0; i < teams.length; i++) {
            System.out.println("Time " + (i + 1));
            List<Elf> team = teams[i];
            for (int e = 0; e < 3; e++) {
                System.out.println(team.get(e).name + " " + team.get(e).age);
            }
            System.out.println();
        }
    }
}
