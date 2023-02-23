package game;
import algorithms.usingTheAlgorithms;

import java.util.Scanner;

public class Playing {

    private Board board;
    private Scanner sc = new Scanner(System.in);

    private void printWinner () {
        Board.State winner = board.GetWinner();

        System.out.println("\n" + board + "\n");

        if (winner == Board.State.Blank) {
            System.out.println("The TicTacToe is a Draw.");
        } else {
            System.out.println("Player " + winner.toString() + " wins!");
        }
    }



    private void printGameStatus () {
        System.out.println("\n" + board + "\n");
        System.out.println(board.GetTurn().name() + "'s turn.");
    }

    private void getPlayerMove () {
        System.out.print("Index of move: ");

        int move = sc.nextInt();

        if (move < 0 || move >= 9) {
            System.out.println("\nInvalid move.");
            System.out.println("\nThe index of the move must be between 0 and "
                    + (Board.BOARD_WIDTH * Board.BOARD_WIDTH - 1) + ", inclusive.");
        } else if (!board.Move(move)) {
            System.out.println("\nInvalid move.");
            System.out.println("\nThe selected index must be blank.");
        }
    }



    private Playing() {
        board = new Board();
    }


    private void playMove (int x) {

        if (board.GetTurn() == Board.State.X) {
            getPlayerMove();

        } else if (x==1) {
            usingTheAlgorithms.alphaBetaPruning(board);
        }
        else if(x==2){
            usingTheAlgorithms.miniMax(board);
        }
    }
    private void play () {

        System.out.println("Starting a new game.");
        System.out.println("Choose algorithm:  1.Alpha Beta Pruning       2.MinMax  \n Enter");

        int x= sc.nextInt();

        while (true) {
            printGameStatus();
            playMove(x);

            if (board.IsGameOver()) {
                printWinner();
                break;

            }
        }
    }



    public static void main(String[] args) {
        Playing ticTacToe = new Playing();
        ticTacToe.play();
    }



}
