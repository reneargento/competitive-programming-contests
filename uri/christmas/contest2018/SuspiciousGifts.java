package uri.christmas.contest2018;

import java.util.*;

/**
 * Created by Rene Argento on 25/12/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/412/6
public class SuspiciousGifts {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int combinations = scanner.nextInt();
            int kids = scanner.nextInt();
            scanner.nextLine();

            Map<String, Set<String>> gifts = new HashMap<>();

            for (int c = 0; c < combinations; c++) {
                String desired = scanner.nextLine();
                int suspiciousCount = Integer.parseInt(scanner.nextLine());

                Set<String> suspiciousList = new HashSet<>();

                for (int s = 0; s < suspiciousCount; s++) {
                    suspiciousList.add(scanner.nextLine());
                }

                gifts.put(desired, suspiciousList);
            }

            for (int kid = 0; kid < kids; kid++) {
                String[] line = scanner.nextLine().split(";");
                String desired = line[0];
                String received = line[1];

                if (gifts.containsKey(desired) && gifts.get(desired).contains(received)) {
                    System.out.println("Y");
                } else {
                    System.out.println("N");
                }
            }
        }
    }
}
