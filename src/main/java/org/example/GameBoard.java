package org.example;

public class GameBoard {
    private String[][] board;

    public GameBoard() {
        board = new String[7][7];
        setUpBoard();
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public  void setUpBoard(){
        int num = 1;
        for(int i=0; i<board[0].length; i++){
            board[0][i] = Integer.toString(num);
            num += 1;
        }

        for(int r = 1; r< board.length; r++){
            for(int c = 0; c<board[0].length; c++){
                board[r][c] = "-";
            }
        }
        printBoard();


    }

    public void printBoard(){

        for(int r = 0; r< board.length; r++){
            for(int c = 0; c<board[0].length; c++){
                System.out.printf("%s \t",board[r][c]);
            }
            System.out.println();
        }

        System.out.println("Player 1: X");
        System.out.println("Player 2: O");
        System.out.println();

    }

    public boolean isFull(){

        for(int c=0; c<board[0].length; c++){
            if(board[1][c].equals("-")){
                return false;
            }
        }

        return true;
    }

    public int findEmptyRow(int col){

        int emptyRow = -1;

        for(int r=board.length-1; r >=1; r--){
            if(board[r][col].equals("-")){
                emptyRow = r;
                break;
            }
        }

        return emptyRow;

    }

    public  int findFloatingDiscRow(int col){

        int floatingRow = -1;
        for(int r=1; r<board.length-1; r++){
            if(board[r+1][col].equals("-") && !(board[r][col].equals("-"))) {
                floatingRow = r;
                break;
            }

        }

        return floatingRow;
    }


}
