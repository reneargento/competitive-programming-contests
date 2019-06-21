package google.code.jam.code2019.round1b;

import java.util.*;

/**
 * Created by Rene Argento on 28/04/19.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/0000000000051706/000000000012295c
public class ManhattanCrepeCart {

    private static class Tuple implements Comparable<Tuple> {
        int coordinate;
        int peopleFacingSide1;
        int peopleFacingSide2;

        Tuple(int coordinate, int peopleFacingSide1, int peopleFacingSide2) {
            this.coordinate = coordinate;
            this.peopleFacingSide1 = peopleFacingSide1;
            this.peopleFacingSide2 = peopleFacingSide2;
        }

        @Override
        public int compareTo(Tuple other) {
            return coordinate - other.coordinate;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int people = scanner.nextInt();
            int size = scanner.nextInt();

            // 1 - Pre-process data
            Map<Integer, Tuple> horizontalInformation = new HashMap<>();
            Map<Integer, Tuple> verticalInformation = new HashMap<>();

            int totalPeopleFacingWest = 0;
            int totalPeopleFacingSouth = 0;

            for (int i = 0; i < people; i++) {
                int column = scanner.nextInt();
                int row = scanner.nextInt();
                String direction = scanner.next();

                if (!verticalInformation.containsKey(row)) {
                    verticalInformation.put(row, new Tuple(row, 0, 0));
                }
                if (!horizontalInformation.containsKey(column)) {
                    horizontalInformation.put(column, new Tuple(column, 0, 0));
                }

                int peopleFacingNorth = verticalInformation.get(row).peopleFacingSide1;
                int peopleFacingSouth = verticalInformation.get(row).peopleFacingSide2;
                int peopleFacingEast = horizontalInformation.get(column).peopleFacingSide1;
                int peopleFacingWest = horizontalInformation.get(column).peopleFacingSide2;

                switch (direction) {
                    case "N":
                        peopleFacingNorth++;
                        break;
                    case "S":
                        peopleFacingSouth++;
                        totalPeopleFacingSouth++;
                        break;
                    case "E":
                        peopleFacingEast++;
                        break;
                    case "W":
                        peopleFacingWest++;
                        totalPeopleFacingWest++;
                        break;
                }

                horizontalInformation.put(column, new Tuple(column, peopleFacingEast, peopleFacingWest));
                verticalInformation.put(row, new Tuple(row, peopleFacingNorth, peopleFacingSouth));
            }

            List<Tuple> horizontalList = new ArrayList<>();
            List<Tuple> verticalList = new ArrayList<>();

            for (int coordinate : horizontalInformation.keySet()) {
                Tuple tuple = horizontalInformation.get(coordinate);
                horizontalList.add(tuple);
            }

            for (int coordinate : verticalInformation.keySet()) {
                Tuple tuple = verticalInformation.get(coordinate);
                verticalList.add(tuple);
            }

            Collections.sort(horizontalList);
            Collections.sort(verticalList);

            // 2- Check horizontal cells
            int targetColumn = getTarget(horizontalList, size, horizontalInformation, totalPeopleFacingWest);

            // 3- Check vertical cells
            int targetRow = getTarget(verticalList, size, verticalInformation, totalPeopleFacingSouth);

            System.out.println("Case #" + t + ": " + targetColumn + " " + targetRow);
        }
    }

    private static int getTarget(List<Tuple> tupleList, int size, Map<Integer, Tuple> coordinateInformation,
                                 int initialPeopleFacingDirection) {
        int maxVotes;
        int currentVotes = initialPeopleFacingDirection;
        int target = 0;

        if (coordinateInformation.containsKey(0)) {
            currentVotes = currentVotes - coordinateInformation.get(0).peopleFacingSide2;
        }
        maxVotes = currentVotes;

        for (Tuple tuple : tupleList) {
            if (tuple.coordinate == size) break;
            int nextCoordinate = tuple.coordinate + 1;

            currentVotes = currentVotes + tuple.peopleFacingSide1 - tuple.peopleFacingSide2;

            if (coordinateInformation.containsKey(nextCoordinate)) {
                int votes = currentVotes - coordinateInformation.get(nextCoordinate).peopleFacingSide2;
                if (votes > maxVotes) {
                    maxVotes = votes;
                    target = nextCoordinate;
                }
            } else {
                if (currentVotes > maxVotes) {
                    maxVotes = currentVotes;
                    target = nextCoordinate;
                }
            }
        }

        return target;
    }

}