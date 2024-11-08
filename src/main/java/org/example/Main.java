package org.example;

import java.util.Scanner;


public class Main {

    private static Scanner stdin = new Scanner(System.in);
    private static final String[][] grid = new String[7][7];
    public static void main(String[] args) {

        setUpGrid(grid);
        printGrid(grid);

        grid[4][1] = "X";

        printGrid(grid);







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
}