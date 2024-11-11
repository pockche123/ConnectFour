package org.example;

public class GameState {
    public boolean timeBombActivated;
    public int timeBombCount;
    int[] savedCoordinate;

    public GameState(){}

    public GameState(boolean timeBombActivated, int timeBombCount, int[] savedCoordinate) {
        this.timeBombActivated = timeBombActivated;
        this.timeBombCount = timeBombCount;
        this.savedCoordinate = savedCoordinate;
    }
}
