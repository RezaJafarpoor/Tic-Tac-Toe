package algorithms;
import game.Board;

public class Move {

    private Move () {}


    static void run (Board board) {
        int[] moves = new int[board.GetAvailableMoves().size()];
        int index = 0;

        for (Integer item : board.GetAvailableMoves()) {
            moves[index++] = item;
        }

        int randomMove = moves[new java.util.Random().nextInt(moves.length)];
        board.Move(randomMove);
    }


}
