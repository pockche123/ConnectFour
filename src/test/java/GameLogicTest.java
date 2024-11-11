
import org.example.GameBoard;
import org.example.GameLogic;
import org.example.GameState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTest {

    private GameBoard gameBoard;
    private GameLogic logic;
    private GameState state;

    @BeforeEach
    void setUp(){
        gameBoard = new GameBoard();
        state = new GameState();
        logic = new GameLogic(gameBoard,state);
    }


    @Test
    void testClearColumn(){
        // initialise board to be full
        for(int r=1; r<7;r++){
            for(int c =0; c<7;c++){
                gameBoard.getBoard()[r][c] = "X";
            }
        }
        logic.clearColumn(1);
        for (int r = 1; r < gameBoard.getBoard().length; r++) {
            assertEquals("-", gameBoard.getBoard()[r][1]);
        }
    }

    @Test
    void testCheckUniDirectionalCountonUpTraversal(){

        gameBoard.getBoard()[2][0] = "X";
        int upCount = logic.checkUniDirectionalCount(2, 0, -1, 0, "X");
        int downCount = logic.checkUniDirectionalCount(3, 3, 1, 0, "X");

        assertEquals(upCount, 1);
        assertEquals(downCount, 0);
    }

    @Test
    void testCheckConnect4ReturnsFalse(){
        boolean isFalse = logic.checkConnect4("X",new int[]{2,2});
        assertFalse(isFalse);
    }

    @Test
    void testCheckConnect4ReturnsTrue(){
        gameBoard.getBoard()[2][5] = "X";
        gameBoard.getBoard()[2][4] = "X";
        gameBoard.getBoard()[2][3] = "X";
        gameBoard.getBoard()[2][2] = "X";


        boolean isTrue = logic.checkConnect4("X", new int[]{2,3});
        assertTrue(isTrue);

    }

    @Test
    void testHandleFloatingDiscs_whenNoFloatingDisc(){
        logic.handleFloatingDiscs(new int[]{1,1});
        for(int r=1;r< gameBoard.getBoard().length; r++){
            assertEquals("-", gameBoard.getBoard()[r][1]);
        }
    }

    @Test
    void testHandleFloatingDiscs_whenFloatingDiscFound(){
        gameBoard.getBoard()[2][1] = "X";
        logic.handleFloatingDiscs(new int[]{2,1});
        assertEquals("-",gameBoard.getBoard()[2][1]);
        assertEquals("X", gameBoard.getBoard()[6][1]);
    }

    @Test
    void testMoveDownFloatingDiscs(){
        gameBoard.getBoard()[2][1] = "X";

        logic.moveDownFloatingDiscs(2,6,1);
        assertEquals("-",gameBoard.getBoard()[2][1]);
        assertEquals("-",gameBoard.getBoard()[3][1]);
        assertEquals("X", gameBoard.getBoard()[6][1]);
    }

    @Test
    void testTimeBombExplode(){
        int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, 1}, {1, -1}, {-1, -1}, {1, 1}};
        int r = 2;
        int c = 2;

        gameBoard.getBoard()[r][c] = "X";
         for (int[] direction : directions) {
            int newR = r + direction[0];
            int newC = c + direction[1];

            if (newR >= 1 && newR < 7 && newC >= 0 && newC < 7) {
                gameBoard.getBoard()[newR][newC] = "X";
            }
        }

        logic.timeBombExplode(new int[]{r,c});

        for (int[] direction : directions) {
            int newR = r + direction[0];
            int newC = c + direction[1];

            if (newR >= 1 && newR < 7 && newC >= 0 && newC < 7) {
                assertEquals("-",gameBoard.getBoard()[newR][newC]);
            }
        }

        assertEquals("-", gameBoard.getBoard()[r][c]);
    }

    @Test
    void testCheckWinTimeBomb


















}
