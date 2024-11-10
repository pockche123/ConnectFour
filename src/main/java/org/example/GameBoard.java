package org.example;

public class GameBoard {
    private String[][] grid;

    public GameBoard() {
        grid = new String[7][7];
        setUpBoard(grid);
    }

    public static void setUpBoard(String[][]grid){
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



}
