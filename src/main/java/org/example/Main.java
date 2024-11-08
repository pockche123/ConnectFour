package org.example;

import java.util.Scanner;


public class Main {

    private static Scanner stdin = new Scanner(System.in);
    private static final String[][] grid = new String[7][7];
    public static void main(String[] args) {

        setUpGrid(grid);
        printGrid(grid);

        pickColumn(1,grid,"X");
        printGrid(grid);
        pickColumn(2,grid,"X");
        printGrid(grid);
        pickColumn(3,grid,"X");
        printGrid(grid);
        pickColumn(4,grid,"X");
        printGrid(grid);
        pickColumn(5,grid,"X");
        printGrid(grid);
        pickColumn(6,grid,"X");
        printGrid(grid);
        pickColumn(7,grid,"X");
        printGrid(grid);
        // Create a diagonal line of 'X's from (1,1) to (4,4)
        grid[4][1] = "X";
        grid[3][2] = "X";
        grid[2][3] = "X";
        grid[1][4] = "X";

        printGrid(grid);

        System.out.println(checkWin(grid, "X", new int[]{1,4}));

        clearColumn(2,grid);
        printGrid(grid);

        System.out.println(checkWin(grid, "X", new int[]{1,4}));

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

    public static int[] pickColumn(int col, String[][]grid, String disc){
        if(col <= 0 || col > 7){
            System.out.println("The column has to be within the range of 1 to 7.");
            return new int[]{-1,-1};
        }

        for(int r = grid.length - 1; r >= 0; r--) {
            if (grid[r][col - 1].equals("-")) {
                grid[r][col - 1] = disc;
                return new int[]{r, col - 1};
            }
        }

            System.out.printf("The column %d is full. Please select another column. \n", col);
            return new int[]{-1, -1};


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
            grid[r][column-1] = "-";
        }
    }


}