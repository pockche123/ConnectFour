package org.example;

public class Player {
    private int playerId;
    private GameBoard grid;

    public Player(int playerId, GameBoard grid){
        this.playerId = playerId;
        this.grid = grid;
    }

    public static int[] pickCoordinates(String input, String[][]grid, String disc){

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

    public static boolean isNumber(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;

        }
    }



}
