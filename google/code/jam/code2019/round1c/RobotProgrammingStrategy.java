package google.code.jam.code2019.round1c;

import java.util.*;

/**
 * Created by Rene Argento on 28/04/19.
 */
// https://codingcompetitions.withgoogle.com/codejam/round/00000000000516b9/0000000000134c90
public class RobotProgrammingStrategy {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int t = 1; t <= tests; t++) {
            int opponents = scanner.nextInt();
            List<String> opponentMovesList = new ArrayList<>();

            for (int i = 0; i < opponents; i++) {
                opponentMovesList.add(scanner.next());
            }

            String moves = buildMoves(opponentMovesList);

            if (moves == null) {
                moves = "IMPOSSIBLE";
            }
            System.out.println("Case #" + t + ": " + moves);
        }
    }

    private static String buildMoves(List<String> opponentMovesList) {
        StringBuilder moves = new StringBuilder();
        int numberOfOpponents = opponentMovesList.size();
        Set<Integer> defeatedOpponents = new HashSet<>();

        while (defeatedOpponents.size() < numberOfOpponents) {
            Set<Character> movesUsed = new HashSet<>();
            int nextMoveIndex = moves.length();

            for (int i = 0; i < numberOfOpponents; i++) {
                if (defeatedOpponents.contains(i)) {
                    continue;
                }

                String opponentMoves = opponentMovesList.get(i);
                movesUsed.add(opponentMoves.charAt(nextMoveIndex % opponentMoves.length()));
            }

            char nextMove = 'R';

            switch (movesUsed.size()) {
                case 1:
                    if (movesUsed.contains('R')) {
                        nextMove = 'P';
                    } else if (movesUsed.contains('P')) {
                        nextMove = 'S';
                    } else {
                        nextMove = 'R';
                    }
                    moves.append(nextMove);
                    return moves.toString();
                case 2:
                    if (movesUsed.contains('R') && movesUsed.contains('P')) {
                        nextMove = 'P';
                    } else if (movesUsed.contains('R') && movesUsed.contains('S')) {
                        nextMove = 'R';
                    } else {
                        nextMove = 'S';
                    }
                    moves.append(nextMove);
                    break;
                case 3:
                    return null;
            }

            Set<Integer> newDefeatedOpponents = playMove(opponentMovesList, nextMove, nextMoveIndex, defeatedOpponents);
            defeatedOpponents.addAll(newDefeatedOpponents);
        }

        return moves.toString();
    }

    private static Set<Integer> playMove(List<String> opponentMovesList, char move, int moveIndex,
                                         Set<Integer> defeatedOpponents) {
        Set<Integer> newDefeatedOpponents = new HashSet<>();

        for (int i = 0; i < opponentMovesList.size(); i++) {
            if (defeatedOpponents.contains(i)) {
                continue;
            }

            String opponentMoves = opponentMovesList.get(i);
            char opponentMove = opponentMoves.charAt(moveIndex % opponentMoves.length());

            if ((opponentMove == 'R' && move == 'P')
                    || (opponentMove == 'P' && move == 'S')
                    || (opponentMove == 'S' && move == 'R')) {
                newDefeatedOpponents.add(i);
            }
        }

        return newDefeatedOpponents;
    }
}