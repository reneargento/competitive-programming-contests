package movile.code.challenge3;

import java.util.*;

/**
 * Created by Rene Argento on 29/03/21.
 */
public class PromobombMovilepayiFood {

    private static class Action implements Comparable<Action> {
        int index;
        int profit;

        public Action(int index, int impact) {
            this.index = index;
            int revenue = 4 * (impact * impact);
            int cost = impact * 100;
            profit = revenue - cost;
        }

        @Override
        public int compareTo(Action other) {
            if (profit != other.profit) {
                return Integer.compare(profit, other.profit);
            }
            return Integer.compare(other.index, index);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int actionsNumber = scanner.nextInt();
        scanner.nextLine();
        List<Action> actions = new ArrayList<>();

        for (int i = 0; i < actionsNumber; i++) {
            String[] line = scanner.nextLine().split(" ");
            int value = Integer.parseInt(line[line.length - 1]);
            actions.add(new Action(i + 1, value));
        }

        actions.sort(Collections.reverseOrder());
        for (int i = 0; i < 3; i++) {
            System.out.println(actions.get(i).index);
        }
    }
}
