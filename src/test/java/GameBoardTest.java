
import org.example.GameBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {

    private GameBoard gameBoard;

    @BeforeEach
    void setUp(){
        gameBoard = new GameBoard();
    }

    @Test
    void testSetUpBoard() {
        // Expected top row: ["1", "2", "3", "4", "5", "6", "7"]
        String[][] board = gameBoard.getBoard();

        // Check first row numbers
        for (int i = 0; i < 7; i++) {
            assertEquals(String.valueOf(i + 1), board[0][i], "Top row should have numbers 1 to 7");
        }

        // Check that the rest of the rows are filled with "-"
        for (int r = 1; r < 7; r++) {
            for (int c = 0; c < 7; c++) {
                assertEquals("-", board[r][c], "Cells should be initialized to '-' in rows 1 to 7");
            }
        }
    }

    @Test
    void testIsFullWhenEmpty(){
        assertFalse(gameBoard.isFull(), "Board is not full.");
    }

    @Test
    void testIsFullWhenFull(){

        String[][] board = gameBoard.getBoard();
        // setup board to be full
        for(int c=0; c<7; c++){
            board[1][c] = "X";
        }
        gameBoard.setBoard(board);
        assertTrue(gameBoard.isFull(), "Board is full.");
    }

    @Test
    void testFindEmptyRowReturn6WhenEmptyBoard(){
        assertEquals(gameBoard.findEmptyRow(1),6);
    }

    @Test
    void testFindFloatingDiscRowWhenNoFloatingDisc(){

        for (int r = 1; r < 7; r++) {
            for (int c = 0; c < 7; c++) {
                gameBoard.getBoard()[r][c] = "X";
            }
        }

        assertEquals(gameBoard.findFloatingDiscRow(1),-1);

    }

    @Test
    void testFindFloatingDiscRowWithFloatingDisc(){
        gameBoard.getBoard()[3][3] = "X";
        assertEquals(gameBoard.findFloatingDiscRow(3),3);

    }

    @AfterEach
    void tearDown(){
        gameBoard = null;
    }






}