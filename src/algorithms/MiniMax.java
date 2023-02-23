package algorithms;
import game.Board;

public class MiniMax {

    private static double MaxDepth;
    private MiniMax() {}
    static void run (Board.State player, Board board, double MaxDepth) {
        if (MaxDepth < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }

        MiniMax.MaxDepth = MaxDepth;
        miniMax(player, board, 0);
    }


    private static int miniMax (Board.State player, Board board, int CurrentDepth) {
        if (CurrentDepth++ == MaxDepth || board.IsGameOver()) {
            return score(player, board);
        }

        if (board.GetTurn() == player) {
            return getMax(player, board, CurrentDepth);
        } else {
            return getMin(player, board, CurrentDepth);
        }

    }


    private static int getMax (Board.State player, Board board, int CurrentDepth) {
        double bestScore = Double.NEGATIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer Move : board.GetAvailableMoves()) {

            Board modifiedBoard = board.GetCopy();
            modifiedBoard.Move(Move);

            int score = miniMax(player, modifiedBoard, CurrentDepth);

            if (score >= bestScore) {
                bestScore = score;
                indexOfBestMove = Move;
            }

        }

        board.Move(indexOfBestMove);
        return (int)bestScore;
    }


    private static int getMin (Board.State player, Board board, int CurrentDepth) {
        double bestScore = Double.POSITIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer Move : board.GetAvailableMoves()) {

            Board modifiedBoard = board.GetCopy();
            modifiedBoard.Move(Move);

            int score = miniMax(player, modifiedBoard, CurrentDepth);

            if (score <= bestScore) {
                bestScore = score;
                indexOfBestMove = Move;
            }

        }

        board.Move(indexOfBestMove);
        return (int)bestScore;
    }


    private static int score (Board.State player, Board board) {
        if (player == Board.State.Blank) {
            throw new IllegalArgumentException("Player must be X or O.");
        }

        Board.State opponent = (player == Board.State.X) ? Board.State.O : Board.State.X;

        if (board.IsGameOver() && board.GetWinner() == player) {
            return 10;
        } else if (board.IsGameOver() && board.GetWinner() == opponent) {
            return -10;
        } else {
            return 0;
        }
    }


}
