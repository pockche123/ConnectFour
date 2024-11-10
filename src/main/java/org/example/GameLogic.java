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



    public boolean checkConnect4(String disc, int[] lastCoordinates){
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

    public void checkForFloatingDiscs( int[]coordinate){

        int col = coordinate[1];


        int prevFloatingRow = -1;
        if(col-1 >= 0 ){
            prevFloatingRow = board.findFloatingDiscRow(col-1);
        }

        if(prevFloatingRow != -1){
            int prevEmptyRow = board.findEmptyRow(col-1);
            moveDownFloatingDiscs(prevFloatingRow,prevEmptyRow,col-1);
        }

        int floatingRow = board.findFloatingDiscRow(col);

        if(floatingRow != -1){
            int emptyRow = board.findEmptyRow(col);
            moveDownFloatingDiscs(floatingRow,emptyRow,col);
        }
        int nextFloatingRow = -1;
        if(col+1 < board.getBoard()[0].length ){
            nextFloatingRow = board.findFloatingDiscRow(col+1);
        }

        if(nextFloatingRow != -1){
            int nextEmptyRow = board.findEmptyRow(col+1);
            moveDownFloatingDiscs(nextFloatingRow,nextEmptyRow,col+1);
        }

    }


    public void moveDownFloatingDiscs(int floatingDiscRow, int emptyDiscRow, int col){

        for(int r = emptyDiscRow; r>0; r--){
            if(floatingDiscRow > 0){
                board.getBoard()[r][col] = board.getBoard()[floatingDiscRow][col];
            } else{
                board.getBoard()[r][col] = "-";
            }
            floatingDiscRow -=1;
            
        }
    }


    public void timeBombExplode(int[] coordinate){

        int r = coordinate[0];
        int c = coordinate[1];

        board.getBoard()[r][c] = "-";

        int maxRow = board.getBoard().length;
        int maxCol = board.getBoard()[0].length;

        int[][] directions = new int[][]{{1,0},{-1,0},{0,1},{0,-1},{-1,1},{1,-1},{-1,-1},{1,1}};

        for(int[] direction: directions){
            int newR = r + direction[0];
            int newC = c + direction[1];

            if(newR >=1 && newR < maxRow && newC >=0 && newC <maxCol){
                board.getBoard()[newR][newC] = "-";
            }
        }
        checkForFloatingDiscs(coordinate);
    }


    public boolean checkWinTimeBomb( String disc, int[] bombCoordinates){

        int r = bombCoordinates[0];
        int c = bombCoordinates[1];

        boolean leftCheck = checkConnect4(disc, new int[]{r,c-1});
        boolean leftUpCheck = checkConnect4(disc, new int[]{r-1,c-1});
        boolean leftDownCheck = checkConnect4(disc, new int[]{r+1,c-1});
        boolean check = checkConnect4(disc,new int[]{r,c});
        boolean upCheck = checkConnect4(disc, new int[]{r-1,c});
        boolean downCheck = checkConnect4(disc, new int[]{r+1,c});
        boolean rightCheck = checkConnect4(disc, new int[]{r,c+1});
        boolean rightUpCheck = checkConnect4(disc, new int[]{r-1,c+1});
        boolean rightDownCheck = checkConnect4(disc, new int[]{r+1,c+1});

        return leftCheck || leftUpCheck || leftDownCheck || check || upCheck || downCheck || rightCheck || rightUpCheck || rightDownCheck;

    }







}
