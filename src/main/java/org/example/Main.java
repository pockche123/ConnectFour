package org.example;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class Main {
// checking if the boxes are full
//  for player 2 functionality of the bomb.
//    infinite bombs for each player?
    private static Scanner stdin = new Scanner(System.in);
    private static final String[][] grid = new String[7][7];
    public static void main(String[] args) {


        System.out.println();
        System.out.println("Welcome to the game of Connect Four. Please start the game with player 1 by picking a column.");
        System.out.println();
        setUpGrid(grid);

        boolean player1Turn = true;
        boolean player2Turn = false;
        boolean player1Win = false;
        boolean player2Win = false;


        boolean timeBombActivated = false;
        int timeBombCount = 4;
        int[] savedCoordinate = new int[]{-1,-1};


        while(true){

            {

                int[][]  coordinates = handleCoordinates(1,grid,savedCoordinate);
                int[] coordinate = coordinates[0];
                int[] timeBombCoordinate  = coordinates[1];

                player1Win = checkWin(grid,"X",coordinate);
                if(player1Win){
                    System.out.println("Game over. Player 1 has won the game.");
                    break;
                }
                if(!(Arrays.equals(timeBombCoordinate,new int[]{-1,-1}))){
                    timeBombActivated = true;
                    savedCoordinate = new int[]{timeBombCoordinate[0], timeBombCoordinate[1]};

                }
                if(timeBombActivated){
                    timeBombCount -=1;
                }
                System.out.println(timeBombCount);
                if(timeBombCount == 0){
                    System.out.println(savedCoordinate[0] + ", " +  savedCoordinate[1]);
                    boolean winCheck = timeBombCountEqualsZeroWin(savedCoordinate,grid);
                    printGrid(grid);
                    if(winCheck){
                        break;
                    }
                    timeBombActivated = false;
                    timeBombCount = 4;
                    savedCoordinate = new int[]{-1,-1};
                }



            }



            {
                int[][] coordinates = handleCoordinates(2, grid,savedCoordinate);
                int[] coordinate = coordinates[0];
                int [] timeBombCoordinate = coordinates[1];

                player2Win = checkWin(grid, "O", coordinate);
                if (player2Win) {
                    System.out.println("Game over. Player 2 has won the game.");
                    break;
                }

                if(!(Arrays.equals(timeBombCoordinate,new int[]{-1,-1}))){
                    timeBombActivated = true;
                    System.out.println(savedCoordinate[0] + ", " +  savedCoordinate[1]);
                    savedCoordinate = new int[]{timeBombCoordinate[0], timeBombCoordinate[1]};

                }
                if(timeBombActivated){
                    timeBombCount -=1;
                }
                System.out.println(timeBombCount);
                if(timeBombCount == 0){
                    System.out.println(savedCoordinate[0] + ", " +  savedCoordinate[1]);
                    boolean winCheck = timeBombCountEqualsZeroWin(savedCoordinate,grid);
                    printGrid(grid);
                    if(winCheck){
                        break;
                    }
                    timeBombActivated = false;
                    timeBombCount = 4;
                    savedCoordinate = new int[]{-1,-1};
                }
            }

        }



    }



    private static int[][] handleCoordinates(int player, String[][] grid, int[] savedCoordinate){
            int[] coordinate = new int[] {-1,-1};
            int[] timeBombCoordinate = new int[] {-1,-1};
            boolean bombPressed = false;


            System.out.printf("Player %d, please pick a suitable column: \n", player);
            while(Arrays.equals(coordinate, new int[]{-1, -1})){
                String input = stdin.nextLine();
                if(input.equalsIgnoreCase("b") || input.equals("*")){
                    if(input.equalsIgnoreCase("b")) {
                        coordinate = pickColumn(stdin.nextLine(), grid, "-");
                        bombPressed = true;
                    }
                    if(input.equals("*")){
                        if(Arrays.equals(savedCoordinate, new int[]{-1,-1})) {
                            coordinate = pickColumn(stdin.nextLine(), grid, "*");
                            timeBombCoordinate = new int[]{coordinate[0], coordinate[1]};
                        } else{
                            System.err.println("Error: A new time bomb cannot be placed until the current one has detonated.");

                        }
                    }

                } else{
                    if(player == 1) {
                        coordinate = pickColumn(input, grid, "X");
                    } else {
                        coordinate = pickColumn(input, grid, "O");
                    }
                }

            }

            if(bombPressed){
                System.out.println(coordinate[1]);
                clearColumn(coordinate[1],grid);
            }


            System.out.printf("Player %d has picked the column %d. \n", player, coordinate[1]+1);
            printGrid(grid);
            return new int[][]{coordinate, timeBombCoordinate};

    }




    public static boolean timeBombCountEqualsZeroWin(int[] timeBombCoordinate, String[][] grid){

        if(!Arrays.equals(timeBombCoordinate, new int[]{-1, -1})) {
            timeBombExplode(timeBombCoordinate,grid);
            checkForFloatingDiscs(grid, timeBombCoordinate);
            boolean player1Win = checkWinTimeBomb(grid,"X",timeBombCoordinate);
            boolean player2Win = checkWinTimeBomb(grid, "O",timeBombCoordinate);

            if(player1Win && player2Win){
                System.out.println("Game over. Draw!");
                return true;

            } else if(player1Win){
                System.out.println("Game over. Player 1 has won the game.");
                return true;

            } else if(player2Win){
                System.out.println("Game over. Player 2 has won the game.");
                return true;

            }else {
                return false;
            }

        }
        return false;
    }

    public static boolean isNumber(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;

        }
    }



    public static void setUpGrid(String[][] grid){
        int num = 1;
        for(int i=0; i<grid[0].length; i++){
            grid[0][i] = Integer.toString(num);
            num += 1;
        }

        for(int r = 1; r< grid.length; r++){
            for(int c = 0; c<grid[0].length; c++){
                    grid[r][c] = "-";
                }
        }
        printGrid(grid);


    }



    public static void printGrid(String[][]grid){

        for(int r = 0; r< grid.length; r++){
            for(int c = 0; c<grid[0].length; c++){
                System.out.printf("%s \t",grid[r][c]);
            }
            System.out.println();
        }

        System.out.println("Player 1: X");
        System.out.println("Player 2: O");
        System.out.println();

    }

    public static int[] pickColumn(String input, String[][]grid, String disc){

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



        for(int r = grid.length - 1; r >= 0; r--) {
            if (grid[r][col - 1].equals("-")) {
                grid[r][col - 1] = disc;
                return new int[]{r, col - 1};
            }
        }


            System.out.printf("Column %d is full. Please pick another column. \n", col);
            return new int[]{-1, -1};


    }

    public static boolean checkWinTimeBomb(String[][] grid, String disc, int[] bombCoordinates){

        int r = bombCoordinates[0];
        int c = bombCoordinates[1];

        boolean leftCheck = checkWin(grid, disc, new int[]{r,c-1});
        boolean leftUpCheck = checkWin(grid, disc, new int[]{r-1,c-1});
        boolean leftDownCheck = checkWin(grid, disc, new int[]{r+1,c-1});
        boolean check = checkWin(grid, disc,new int[]{r,c});
        boolean upCheck = checkWin(grid, disc, new int[]{r-1,c});
        boolean downCheck = checkWin(grid, disc, new int[]{r+1,c});
        boolean rightCheck = checkWin(grid, disc, new int[]{r,c+1});
        boolean rightUpCheck = checkWin(grid, disc, new int[]{r-1,c+1});
        boolean rightDownCheck = checkWin(grid, disc, new int[]{r+1,c+1});

        return leftCheck || leftUpCheck || leftDownCheck || check || upCheck || downCheck || rightCheck || rightUpCheck || rightDownCheck;

    }

    public static boolean checkWin(String[][] grid, String disc, int[] lastCoordinates){
        int r = lastCoordinates[0];
        int c = lastCoordinates[1];

        int upCount = checkUniDirectionalCount(r,c,-1,0,disc,grid);
        int downCount = checkUniDirectionalCount(r,c,1,0,disc,grid);

        int rightCount = checkUniDirectionalCount(r,c, 0,1,disc,grid);
        int leftCount = checkUniDirectionalCount(r,c,0,-1,disc,grid);


        int upRightCount = checkUniDirectionalCount(r,c,-1,1,disc,grid);
        int downLeftCount = checkUniDirectionalCount(r,c,1,-1,disc,grid);

        int upLeftCount = checkUniDirectionalCount(r,c,-1,-1,disc,grid);
        int downRightCount = checkUniDirectionalCount(r,c,1,1,disc,grid);

        int upDown = upCount + downCount;
        int rightLeft = rightCount + leftCount;
        int upRightDownLeft = upRightCount + downLeftCount;
        int upLeftDownRight = upLeftCount + downRightCount;


        return upDown >= 5 || rightLeft >= 5 || upRightDownLeft >= 5 || upLeftDownRight >= 5;

    }



    public static int checkUniDirectionalCount(int r, int c, int rowStep, int colStep, String disc, String[][] grid){
        int count = 0;
        int rowLength = grid.length;
        int colLength = grid[0].length;

        for(int i=0; i<4; i++){
            int newR = r+ i*rowStep;
            int newC = c+ i*colStep;

            if(newR >=0 && newR < rowLength && newC >=0 && newC < colLength && grid[newR][newC].equals(disc)){
                count += 1;
            }else{
                break;
            }
        }


        return count;

    }

    public static void clearColumn(int column, String[][] grid){

        for(int r=1; r<grid.length; r++){
            grid[r][column] = "-";
        }

        printGrid(grid);
    }

    public static void timeBombExplode(int[] coordinate, String[][] grid){

        int r = coordinate[0];
        int c = coordinate[1];

        grid[r][c] = "-";

        int maxRow = grid.length;
        int maxCol = grid[0].length;

        int[][] directions = new int[][]{{1,0},{-1,0},{0,1},{0,-1},{-1,1},{1,-1},{-1,-1},{1,1}};

        for(int[] direction: directions){
            int newR = r + direction[0];
            int newC = c + direction[1];

            if(newR >=1 && newR < maxRow && newC >=0 && newC <maxCol){
                grid[newR][newC] = "-";
            }
        }
        checkForFloatingDiscs(grid,coordinate);
    }

    public static void checkForFloatingDiscs(String[][] grid, int[]coordinate){

            int col = coordinate[1];


            int prevFloatingRow = -1;
            if(col-1 >= 0 ){
                prevFloatingRow = findFloatingDiscRow(col-1, grid);
            }

            if(prevFloatingRow != -1){
                int prevEmptyRow = findEmptyRow(col-1,grid);
                moveDownFloatingDiscs(prevFloatingRow,prevEmptyRow,col-1,grid);
            }

            int floatingRow = findFloatingDiscRow(col, grid);

            if(floatingRow != -1){
                int emptyRow = findEmptyRow(col,grid);
                moveDownFloatingDiscs(floatingRow,emptyRow,col,grid);
            }
            int nextFloatingRow = -1;
            if(col+1 < grid[0].length ){
                nextFloatingRow = findFloatingDiscRow(col+1, grid);
            }

            if(nextFloatingRow != -1){
                int nextEmptyRow = findEmptyRow(col+1,grid);
                moveDownFloatingDiscs(nextFloatingRow,nextEmptyRow,col+1,grid);
            }

    }


    public static int findEmptyRow(int col, String[][] grid){

        int emptyRow = -1;

        for(int r=grid.length-1; r >=1; r--){
            if(grid[r][col].equals("-")){
                emptyRow = r;
                break;
            }
        }

        return emptyRow;

    }

    public static int findFloatingDiscRow(int col, String[][]grid){

        int floatingRow = -1;
        for(int r=1; r<grid.length-1; r++){
            if(grid[r+1][col].equals("-") && !(grid[r][col].equals("-"))) {
                floatingRow = r;
                break;
            }

        }

        return floatingRow;
    }

    public static void moveDownFloatingDiscs(int floatingDiscRow, int emptyDiscRow, int col, String[][] grid){

            for(int r = emptyDiscRow; r>0; r--){
                if(floatingDiscRow > 0){
                    grid[r][col] = grid[floatingDiscRow][col];
                } else{
                    grid[r][col] = "-";
                }
                floatingDiscRow -=1;


            }
    }





}