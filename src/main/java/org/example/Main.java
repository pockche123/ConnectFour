package org.example;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {


        System.out.println("Welcome to Connect Four!");

        GameBoard gameBoard = new GameBoard();
        GameState gameState = new GameState(false, 4, new int[]{-1, -1});
        GameLogic gameLogic = new GameLogic(gameBoard, gameState);

        while (true) {

            int turn1 = gameLogic.takeTurn(1, gameState);
            if (turn1 != -1) {
                gameLogic.printOutcome(turn1);
                break;
            }

            if (gameBoard.isFull()) {
                System.out.println("Game over. Draw!");
                break;
            }
            int turn2 = gameLogic.takeTurn(2, gameState);
            if (turn2 != -1) {
                gameLogic.printOutcome(turn2);
                break;
            }

            if (gameBoard.isFull()) {
                System.out.println("Game over. Draw!");
                break;
            }
        }


    }
}



