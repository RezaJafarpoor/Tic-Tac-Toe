package algorithms;
import game.Board;

public class usingTheAlgorithms {

    private usingTheAlgorithms() {
    }


    public static void random(Board board) {
        Move.run(board);
    }


    public static void miniMax(Board board) {
        MiniMax.run(board.GetTurn(), board, Double.POSITIVE_INFINITY);
    }



    public static void alphaBetaPruning(Board board) {
        AlphaBetaPruning.run(board.GetTurn(), board, Double.POSITIVE_INFINITY);
    }


}
