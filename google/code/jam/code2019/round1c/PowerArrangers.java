package google.code.jam.code2019.round1c;

import java.util.*;

// https://codingcompetitions.withgoogle.com/codejam/round/00000000000516b9/0000000000134e91
public class PowerArrangers {

    private static class Figure {
        char value;
        int index;

        Figure(char value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    private static final int TOTAL_FIGURES = 595;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        int figures = scanner.nextInt(); // Not used
        scanner.nextLine();

        for (int t = 0; t < tests; t++) {
            String missingSet = getMissingFigureSet();
            System.out.println(missingSet);
            String result = scanner.nextLine();
            if (result.charAt(0) == 'N') {
                return;
            }
        }
    }

    private static String getMissingFigureSet() {
        StringBuilder missingSet = new StringBuilder();
        List<Figure> nextSetToCheck = null;
        Set<Character> uniqueCharacters = getUniqueCharacters();

        // Find first figure
        List<List<Figure>> setList = getFigures();

        for (List<Figure> figureSets : setList) {
            if (figureSets.size() == 23) {
                nextSetToCheck = figureSets;
                char figure = nextSetToCheck.get(0).value;
                missingSet.append(figure);
                uniqueCharacters.remove(figure);
            }
        }

        // Find second figure
        setList = getFiguresBasedOnSets(nextSetToCheck);

        for (List<Figure> figureSets : setList) {
            if (figureSets.size() == 5) {
                nextSetToCheck = figureSets;
                char figure = nextSetToCheck.get(0).value;
                missingSet.append(figure);
                uniqueCharacters.remove(figure);
            }
        }

        // Find third figure
        setList = getFiguresBasedOnSets(nextSetToCheck);
        char lastFigure = 'A';

        for (List<Figure> figureSets : setList) {
            if (figureSets.size() == 1) {
                char figure = figureSets.get(0).value;
                missingSet.append(figure);
                uniqueCharacters.remove(figure);

                // Find fifth figure
                int lastFigureIndex = figureSets.get(0).index + 1;
                System.out.println(lastFigureIndex);
                String value = scanner.nextLine();
                lastFigure = value.charAt(0);
                uniqueCharacters.remove(lastFigure);
            }
        }

        // Find fourth figure
        if (uniqueCharacters.contains('A')) {
            missingSet.append('A');
        } else if (uniqueCharacters.contains('B')) {
            missingSet.append('B');
        } else if (uniqueCharacters.contains('C')) {
            missingSet.append('C');
        } else if (uniqueCharacters.contains('D')) {
            missingSet.append('D');
        } else if (uniqueCharacters.contains('E')) {
            missingSet.append('E');
        }

        missingSet.append(lastFigure);

        return missingSet.toString();
    }

    private static Set<Character> getUniqueCharacters() {
        Set<Character> uniqueCharacters = new HashSet<>();
        uniqueCharacters.add('A');
        uniqueCharacters.add('B');
        uniqueCharacters.add('C');
        uniqueCharacters.add('D');
        uniqueCharacters.add('E');
        return uniqueCharacters;
    }

    private static List<List<Figure>> getFigures() {
        List<List<Figure>> setList = new ArrayList<>();
        List<Figure> aSet = new ArrayList<>();
        List<Figure> bSet = new ArrayList<>();
        List<Figure> cSet = new ArrayList<>();
        List<Figure> dSet = new ArrayList<>();
        List<Figure> eSet = new ArrayList<>();

        for (int index = 1; index <= TOTAL_FIGURES; index += 5) {
            System.out.println(index);
            String value = scanner.nextLine();
            checkAndAddFigure(value.charAt(0), index, aSet, bSet, cSet, dSet, eSet);
        }

        setList.add(aSet);
        setList.add(bSet);
        setList.add(cSet);
        setList.add(dSet);
        setList.add(eSet);
        return setList;
    }

    private static List<List<Figure>> getFiguresBasedOnSets(List<Figure> nextSetToCheck) {
        List<List<Figure>> setList = new ArrayList<>();
        List<Figure> aSet = new ArrayList<>();
        List<Figure> bSet = new ArrayList<>();
        List<Figure> cSet = new ArrayList<>();
        List<Figure> dSet = new ArrayList<>();
        List<Figure> eSet = new ArrayList<>();

        for (Figure figure : nextSetToCheck) {
            int index = figure.index + 1;
            System.out.println(index);
            String value = scanner.nextLine();
            checkAndAddFigure(value.charAt(0), index, aSet, bSet, cSet, dSet, eSet);
        }

        setList.add(aSet);
        setList.add(bSet);
        setList.add(cSet);
        setList.add(dSet);
        setList.add(eSet);
        return setList;
    }

    private static void checkAndAddFigure(char value, int index, List<Figure> aSet, List<Figure> bSet, List<Figure> cSet,
                                          List<Figure> dSet, List<Figure> eSet) {
        Figure figure = new Figure(value, index);
        if (value == 'A') {
            aSet.add(figure);
        } else if (value == 'B') {
            bSet.add(figure);
        } else if (value == 'C') {
            cSet.add(figure);
        } else if (value == 'D') {
            dSet.add(figure);
        } else if (value == 'E') {
            eSet.add(figure);
        }
    }

}
