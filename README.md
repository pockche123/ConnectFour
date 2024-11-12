

![Set up Image](./images/setup.jpg)

## Description 

"Connect Four" is a command-line Java application. It is built in using Maven for dependency management and project structure. 


## Table of Contents

- [Prerequisites](#prerequisites)
- [How to Set Up and Run](#how-to-set-up-and-run)
  - [Cloning the Repository](#cloning-the-repository)
  - [Building the Project](#building-the-project)
  - [Running the Application](#running-the-application)
- [Testing](#testing)
- [Instructions](#instuctions)


## Prerequisites 

Please ensure you have the following installed to setup and run the application

- Java 21+
- Mavan 3.9.9+

## How to set up and run

### Cloning the repository 

To clone the repository, use the following command: 

 ```bash
  git clone https://github.com/pockche123/ConnectFour.git
  cd <ConnectFourDirectory>
  ```

### Building the project 

To buid the project using maven, use the following command: 

 ```bash
  mvn clean install 
  ```

### Running the application 

To run the game after it has been successfully built, use the following command: 

 ```bash
  java -jar target/ConnectFour-1.0-SNAPSHOT.jar
  ```

## Testing 

To run the unit tests, use the following command: 

 ```bash
  mvn test
  ```

## Instructions 

The objective of the game is to place a disc (either X or O) in four in a row (either horizontally, vertically or diagnolly). The player can start the game by choosing a column which will then drop the disc at the bottom of the column.

![Winner](./images/winner.png)

However, there are additional features. 

### Additional features 

Additional features include clear-column bomb and a time-bomb. 
A clear-columb bomb will clear an entire column. To place a clear-bomb, you simply press a "b", then press "Enter", then press the column you want to clear. 
 
| Before Bomb | After Bomb |
|-------------|------------|
| ![Before bomb](./images/beforeBomb.png) | ![After bomb](./images/bombAftermath.png) |



To retract from pressing the bomb, after you have pressed a "b", you have to press "Enter" twice. 


A time-bomb will clear the coordinates at which it was placed in and all of its neighbours in either direction (horizontally, vertically or diagnolly) after the opponent has made 2 moves. After the bomb clears, the discs at the top will fall down the column. Disc falling down and resulting in a connect four will make a winner. 

![Time bomb](./images/timebomb.png)


Simiar to clear-column bomb, to retract from pressing the time-bomb, after you have pressed a "*", you have to press "Enter" twice. 

Either a clear-column bomb or a time-bomb can be placed at a time.












 

