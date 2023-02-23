package game;

import java.util.HashSet;

public class Board {

    static final int BOARD_WIDTH = 3;
    public enum State {Blank, X, O}
    private State[][] board;
    private State playersTurn;
    private State Winner;
    private HashSet<Integer> MovesAvailable;
    private int MoveCount;
    private boolean GameOver;
    public Board() {
        board = new State[BOARD_WIDTH][BOARD_WIDTH];
        MovesAvailable = new HashSet<>();
        Reset();
    }

    private void Start () {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = State.Blank;
            }
        }

        MovesAvailable.clear();

        for (int i = 0; i < BOARD_WIDTH*BOARD_WIDTH; i++) {
            MovesAvailable.add(i);
        }
    }

    public void Reset () {
        MoveCount = 0;
        GameOver = false;
        playersTurn = State.X;
        Winner = State.Blank;
        Start();
    }

    public boolean Move (int index) {
        return Move(index% BOARD_WIDTH, index/ BOARD_WIDTH);
    }

    private boolean Move (int x, int y) {

        if (GameOver) {
            throw new IllegalStateException("Game over");
        }

        if (board[y][x] == State.Blank) {
            board[y][x] = playersTurn;
        } else {
            return false;
        }

        MoveCount++;
        MovesAvailable.remove(y * BOARD_WIDTH + x);


        if (MoveCount == BOARD_WIDTH * BOARD_WIDTH) {
            Winner = State.Blank;
            GameOver = true;
        }


        CheckRow(y);
        CheckColumn(x);
        CheckDiagonalTopLeft(x, y);
        CheckDiagonalTopRight(x, y);

        playersTurn = (playersTurn == State.X) ? State.O : State.X;
        return true;
    }


    public boolean IsGameOver () {
        return GameOver;
    }

    public State GetTurn () {
        return playersTurn;
    }

    public State GetWinner () {
        if (!GameOver) {
            throw new IllegalStateException("The Game is not over yet.");
        }
        return Winner;
    }


    public HashSet<Integer> GetAvailableMoves () {
        return MovesAvailable;
    }


    private void CheckRow (int row) {
        for (int i = 1; i < BOARD_WIDTH; i++) {
            if (board[row][i] != board[row][i-1]) {
                break;
            }
            if (i == BOARD_WIDTH -1) {
                Winner = playersTurn;
                GameOver = true;
            }
        }
    }


    private void CheckColumn (int column) {
        for (int i = 1; i < BOARD_WIDTH; i++) {
            if (board[i][column] != board[i-1][column]) {
                break;
            }
            if (i == BOARD_WIDTH -1) {
                Winner = playersTurn;
                GameOver = true;
            }
        }
    }


    private void CheckDiagonalTopLeft (int x, int y) {
        if (x == y) {
            for (int i = 1; i < BOARD_WIDTH; i++) {
                if (board[i][i] != board[i-1][i-1]) {
                    break;
                }
                if (i == BOARD_WIDTH -1) {
                    Winner = playersTurn;
                    GameOver = true;
                }
            }
        }
    }


    private void CheckDiagonalTopRight (int x, int y) {
        if (BOARD_WIDTH -1-x == y) {
            for (int i = 1; i < BOARD_WIDTH; i++) {
                if (board[BOARD_WIDTH -1-i][i] != board[BOARD_WIDTH -i][i-1]) {
                    break;
                }
                if (i == BOARD_WIDTH -1) {
                    Winner = playersTurn;
                    GameOver = true;
                }
            }
        }
    }
    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < BOARD_WIDTH; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {

                if (board[y][x] == State.Blank) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != BOARD_WIDTH -1) {
                sb.append("\n");
            }
        }

        return new String(sb);
    }


    public Board GetCopy () {
        Board board  = new Board();

        for (int i = 0; i < board.board.length; i++) {
            board.board[i] = this.board[i].clone();
        }

        board.playersTurn = this.playersTurn;
        board.Winner = this.Winner;
        board.MovesAvailable = new HashSet<>();
        board.MovesAvailable.addAll(this.MovesAvailable);
        board.MoveCount = this.MoveCount;
        board.GameOver = this.GameOver;
        return board;
    }

}
