

![Set up Image](./images/setup.jpg)

## Description 

"Connect Four" is a two-player command-line application game where a player wins the game if they are able to connect four of their disks in a row in either (horizontal, vertical or diagnol) direction. It is built in Java using Maven for dependency management and project structure. 


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










 

