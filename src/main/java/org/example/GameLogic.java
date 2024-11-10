package org.example;

public class GameLogic {
    private GameBoard board;
    private GameState state;

    public GameLogic(GameBoard board, GameState state) {
        this.board = board;
        this.state = state;
    }

    public  int checkUniDirectionalCount(int r, int c, int rowStep, int colStep, String disc){
        int count = 0;
        int rowLength = board.getBoard().length;
        int colLength = board.getBoard()[0].length;

        for(int i=0; i<4; i++){
            int newR = r+ i*rowStep;
            int newC = c+ i*colStep;

            if(newR >=0 && newR < rowLength && newC >=0 && newC < colLength && board.getBoard()[newR][newC].equals(disc)){
                count += 1;
            }else{
                break;
            }
        }


        return count;

    }



    public boolean checkWin(String disc, int[] lastCoordinates){
        int r = lastCoordinates[0];
        int c = lastCoordinates[1];

        int upCount = checkUniDirectionalCount(r,c,-1,0,disc);
        int downCount = checkUniDirectionalCount(r,c,1,0,disc);

        int rightCount = checkUniDirectionalCount(r,c, 0,1,disc);
        int leftCount = checkUniDirectionalCount(r,c,0,-1,disc);


        int upRightCount = checkUniDirectionalCount(r,c,-1,1,disc);
        int downLeftCount = checkUniDirectionalCount(r,c,1,-1,disc);

        int upLeftCount = checkUniDirectionalCount(r,c,-1,-1,disc);
        int downRightCount = checkUniDirectionalCount(r,c,1,1,disc);

        int upDown = upCount + downCount;
        int rightLeft = rightCount + leftCount;
        int upRightDownLeft = upRightCount + downLeftCount;
        int upLeftDownRight = upLeftCount + downRightCount;


        return upDown >= 5 || rightLeft >= 5 || upRightDownLeft >= 5 || upLeftDownRight >= 5;

    }


    public void clearColumn(int column){

        for(int r=1; r<board.getBoard().length; r++){
            board.getBoard()[r][column] = "-";
        }

        board.printBoard();
    }

  





}
