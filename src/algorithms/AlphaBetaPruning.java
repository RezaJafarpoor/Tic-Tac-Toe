package algorithms;
import game.Board;

public class AlphaBetaPruning {


    private static double MaxDepth;
    private AlphaBetaPruning () {}
    static void run (Board.State player, Board board, double MaxDepth) {
        if (MaxDepth < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }

        AlphaBetaPruning.MaxDepth = MaxDepth;
        alphaBetaPruning(player, board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
    }


    private static int alphaBetaPruning (Board.State player, Board board, double alpha, double beta, int CurrentDepth) {
        if (CurrentDepth++ == MaxDepth || board.IsGameOver()) {
            return score(player, board);
        }

        if (board.GetTurn() == player) {
            return getMax(player, board, alpha, beta, CurrentDepth);
        } else {
            return getMin(player, board, alpha, beta, CurrentDepth);
        }
    }


    private static int getMax (Board.State player, Board board, double alpha, double beta, int CurrentDepth) {
        int indexOfBestMove = -1;

        for (Integer Move : board.GetAvailableMoves()) {

            Board modifiedBoard = board.GetCopy();
            modifiedBoard.Move(Move);
            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, CurrentDepth);

            if (score > alpha) {
                alpha = score;
                indexOfBestMove = Move;
            }

            // Pruning.
            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.Move(indexOfBestMove);
        }
        return (int)alpha;
    }


    private static int getMin (Board.State player, Board board, double alpha, double beta, int CurrentDepth) {
        int indexOfBestMove = -1;

        for (Integer Move : board.GetAvailableMoves()) {

            Board modifiedBoard = board.GetCopy();
            modifiedBoard.Move(Move);

            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, CurrentDepth);

            if (score < beta) {
                beta = score;
                indexOfBestMove = Move;
            }

            // Pruning.
            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.Move(indexOfBestMove);
        }
        return (int)beta;
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
