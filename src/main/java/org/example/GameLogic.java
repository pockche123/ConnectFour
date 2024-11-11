package org.example;

import java.util.Arrays;
import java.util.Scanner;


public class GameLogic {
    private final GameBoard board;
    private final GameState state;
    private static final Scanner stdin = new Scanner(System.in);


    public GameLogic(GameBoard board, GameState state) {
        this.board = board;
        this.state = state;
    }

    public int checkUniDirectionalCount(int r, int c, int rowStep, int colStep, String disc) {
        int count = 0;
        int rowLength = board.getBoard().length;
        int colLength = board.getBoard()[0].length;

        for (int i = 0; i < 4; i++) {
            int newR = r + i * rowStep;
            int newC = c + i * colStep;

            if (newR >= 0 && newR < rowLength && newC >= 0 && newC < colLength && board.getBoard()[newR][newC].equals(disc)) {
                count += 1;
            } else {
                break;
            }
        }
        return count;
    }


    public boolean checkConnect4(String disc, int[] lastCoordinates) {
        int r = lastCoordinates[0];
        int c = lastCoordinates[1];

        int upCount = checkUniDirectionalCount(r, c, -1, 0, disc);
        int downCount = checkUniDirectionalCount(r, c, 1, 0, disc);

        int rightCount = checkUniDirectionalCount(r, c, 0, 1, disc);
        int leftCount = checkUniDirectionalCount(r, c, 0, -1, disc);


        int upRightCount = checkUniDirectionalCount(r, c, -1, 1, disc);
        int downLeftCount = checkUniDirectionalCount(r, c, 1, -1, disc);

        int upLeftCount = checkUniDirectionalCount(r, c, -1, -1, disc);
        int downRightCount = checkUniDirectionalCount(r, c, 1, 1, disc);

        int upDown = upCount + downCount;
        int rightLeft = rightCount + leftCount;
        int upRightDownLeft = upRightCount + downLeftCount;
        int upLeftDownRight = upLeftCount + downRightCount;


        return upDown >= 5 || rightLeft >= 5 || upRightDownLeft >= 5 || upLeftDownRight >= 5;

    }


    public void clearColumn(int column) {

        for (int r = 1; r < board.getBoard().length; r++) {
            board.getBoard()[r][column] = "-";
        }

    }

    public void handleFloatingDiscs(int[] coordinate) {

        int col = coordinate[1];

        int prevFloatingRow = -1;
        if (col - 1 >= 0) {
            prevFloatingRow = board.findFloatingDiscRow(col - 1);
        }

        if (prevFloatingRow != -1) {
            int prevEmptyRow = board.findEmptyRow(col - 1);
            moveDownFloatingDiscs(prevFloatingRow, prevEmptyRow, col - 1);
        }

        int floatingRow = board.findFloatingDiscRow(col);

        if (floatingRow != -1) {
            int emptyRow = board.findEmptyRow(col);
            moveDownFloatingDiscs(floatingRow, emptyRow, col);
        }
        int nextFloatingRow = -1;
        if (col + 1 < board.getBoard()[0].length) {
            nextFloatingRow = board.findFloatingDiscRow(col + 1);
        }

        if (nextFloatingRow != -1) {
            int nextEmptyRow = board.findEmptyRow(col + 1);
            moveDownFloatingDiscs(nextFloatingRow, nextEmptyRow, col + 1);
        }

    }


    public void moveDownFloatingDiscs(int floatingDiscRow, int emptyDiscRow, int col) {

        for (int r = emptyDiscRow; r > 0; r--) {
            if (floatingDiscRow > 0) {
                board.getBoard()[r][col] = board.getBoard()[floatingDiscRow][col];
            } else {
                board.getBoard()[r][col] = "-";
            }
            floatingDiscRow -= 1;

        }
    }


    public void timeBombExplode(int[] coordinate) {

        int r = coordinate[0];
        int c = coordinate[1];

        board.getBoard()[r][c] = "-";

        int maxRow = board.getBoard().length;
        int maxCol = board.getBoard()[0].length;

        int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, 1}, {1, -1}, {-1, -1}, {1, 1}};

        for (int[] direction : directions) {
            int newR = r + direction[0];
            int newC = c + direction[1];

            if (newR >= 1 && newR < maxRow && newC >= 0 && newC < maxCol) {
                board.getBoard()[newR][newC] = "-";
            }
        }
        handleFloatingDiscs(coordinate);
    }


    public boolean checkWinTimeBomb(String disc, int[] bombCoordinates) {

        int r = bombCoordinates[0];
        int c = bombCoordinates[1];

        boolean leftCheck = checkConnect4(disc, new int[]{r, c - 1});
        boolean leftUpCheck = checkConnect4(disc, new int[]{r - 1, c - 1});
        boolean leftDownCheck = checkConnect4(disc, new int[]{r + 1, c - 1});
        boolean check = checkConnect4(disc, new int[]{r, c});
        boolean upCheck = checkConnect4(disc, new int[]{r - 1, c});
        boolean downCheck = checkConnect4(disc, new int[]{r + 1, c});
        boolean rightCheck = checkConnect4(disc, new int[]{r, c + 1});
        boolean rightUpCheck = checkConnect4(disc, new int[]{r - 1, c + 1});
        boolean rightDownCheck = checkConnect4(disc, new int[]{r + 1, c + 1});

        return leftCheck || leftUpCheck || leftDownCheck || check || upCheck || downCheck || rightCheck || rightUpCheck || rightDownCheck;

    }


    public int timeBombCountEqualsZeroWin(int[] timeBombCoordinate) {

        if (!Arrays.equals(timeBombCoordinate, new int[]{-1, -1})) {
            timeBombExplode(timeBombCoordinate);
            handleFloatingDiscs(timeBombCoordinate);
            boolean player1Win = checkWinTimeBomb("X", timeBombCoordinate);
            boolean player2Win = checkWinTimeBomb("O", timeBombCoordinate);

            if (player1Win && player2Win) {

                return 0;

            } else if (player1Win) {

                return 1;

            } else if (player2Win) {

                return 2;

            } else {
                return -1;
            }

        }
        return -1;
    }

    public int takeTurn( int player, GameState state){

        int[] coordinate = new int[]{-1,-1};
        while(Arrays.equals(coordinate, new int[]{-1,-1})) {
            coordinate = handleCoordinates(player);
        }
        String disc = "X";
        if(player == 2){
            disc = "O";
        }

        boolean playerWin = checkConnect4(disc, coordinate);
        if (playerWin) {
            return player;
        }
        handleTimeBomb(state);
        if (state.timeBombCount == 0) {
            int winner = timeBombCountEqualsZeroWin(state.savedCoordinate);
            board.printBoard();
            resetTimer();
            if(coordinate[1] != -1) {
                System.out.printf("Player %d has picked the column %d. \n", player, coordinate[1] + 1);
            }
            return winner;

        }

        return -1;

    }

    private int[] handleCoordinates(int player){

        System.out.printf("Player %d, please pick a suitable column: \n", player);

        int[] coordinate = handleInput(player);
        board.printBoard();
        if(coordinate[1] != -1) {
            System.out.printf("Player %d has picked the column %d. \n", player, coordinate[1] + 1);
        }
        return coordinate;

    }

    private int[] handleInput(int player){

        int[] coordinate = new int[]{-1,-1};

        while(Arrays.equals(coordinate, new int[]{-1, -1})){
            String input = stdin.nextLine();
            if(input.equalsIgnoreCase("b") || input.equals("*")){
                if(input.equalsIgnoreCase("b")) {
                    if(!state.timeBombActivated) {
                        coordinate = pickCoordinates(stdin.nextLine(), "-");
                        if(coordinate[1] != -1){
                        clearColumn(coordinate[1]);
                        }
                    }else{
                        System.err.println("Error: Bomb cannot be detonated until the time bomb has detonated.");
                        return new int[]{-1,-1};
                    }
                }
                if(input.equals("*")){
                    if(Arrays.equals(state.savedCoordinate, new int[]{-1,-1})) {
                        coordinate = pickCoordinates(stdin.nextLine(), "*");
                        state.savedCoordinate = new int[]{coordinate[0], coordinate[1]};
                        state.timeBombActivated = true;
                    } else{
                        System.err.println("Error: A new time bomb cannot be placed until the current one has detonated.");
                        return new int[]{-1,-1};
                    }
                }

            } else{
                if(player == 1) {
                    coordinate = pickCoordinates(input, "X");
                } else {
                    coordinate = pickCoordinates(input,  "O");
                }
            }

        }

        return coordinate;


    }



    public int[] pickCoordinates(String input, String disc){

        boolean inputIsNumber = isNumber(input);
        if(!inputIsNumber ){
            System.err.println("Column has to be a number within the range of 1 to 7");
            return new int[]{-1,-1};
        }
        int col = Integer.parseInt(input);

        if(col <= 0 || col > 7){
            System.err.println("Please pick the column within the range of 1 to 7.");
            return new int[]{-1,-1};
        }

        for(int r = board.getBoard().length - 1; r >= 0; r--) {
            if (board.getBoard()[r][col - 1].equals("-")) {
                board.getBoard()[r][col - 1] = disc;
                return new int[]{r, col - 1};
            }
        }


        System.out.printf("Column %d is full. Please pick another column. \n", col);
        return new int[]{-1, -1};


    }

    public static boolean isNumber(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;

        }
    }





    public void handleTimeBomb(GameState state){
        if (state.timeBombActivated) {
            state.timeBombCount -= 1;
        }
    }

    private void resetTimer(){

        state.timeBombActivated = false;
        state.timeBombCount = 4;
        state.savedCoordinate = new int[]{-1, -1};

    }

    private void printOutcome(int player) {
        if(player == 1){
            System.out.println("Game over. Player 1 is the winner!");
        }else if(player == 2){
            System.out.println("Game over. Player 2 is the winner!");
        } else if (player == 0){
            System.out.println("Game over. Draw!");
        }
    }




}

