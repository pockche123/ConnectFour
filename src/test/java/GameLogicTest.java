
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
    void testCheckWinTimeBomb_returnsFalse_whenNoConnect4(){
        assertFalse(logic.checkWinTimeBomb("X",new int[]{2,2}));
        assertFalse(logic.checkWinTimeBomb("O",new int[]{3,3}));
    }

    @Test
    void testCheckWinTimeBomb_returnsTrue_whenConnect4(){
        gameBoard.getBoard()[2][2] = "X";
        gameBoard.getBoard()[2][3] = "X";
        gameBoard.getBoard()[2][4] = "X";
        gameBoard.getBoard()[2][5] = "X";

        gameBoard.getBoard()[3][2] = "O";
        gameBoard.getBoard()[3][3] = "O";
        gameBoard.getBoard()[3][4] = "O";
        gameBoard.getBoard()[3][5] = "O";

        assertTrue(logic.checkWinTimeBomb("X", new int[]{2,2}));
        assertTrue(logic.checkWinTimeBomb("O", new int[]{3,2}));
    }

    @Test
    void testTimeBombCountEqualsZeroWin(){

        assertEquals(-1, logic.timeBombCountEqualsZeroWin(new int[]{2,2}));
    }


//    @Test
//    void takeTurn_returnWhenNoWin(){
//        assertEquals(-1, logic.takeTurn(1,state));
//        assertEquals(-1, logic.takeTurn(2,state));
//    }

    @Test
    void testPickCoordinates_WhenInvalidColumn(){
        int[] result = new int[]{-1,-1};
        int[] compare1 = logic.pickCoordinates("- ","X");
        int[] compare2 = logic.pickCoordinates("123213", "X");
        int[] compare3 = logic.pickCoordinates("-2","X");

        String oneStr = compare1[0] + ", " + compare1[1];
        String twoStr = compare2[0] + ", " + compare2[1];
        String threeStr = compare3[0] + ", " + compare3[1];
        assertEquals(result[0] + ", " + result[1], oneStr);
        assertEquals(result[0] + ", " + result[1], twoStr);
        assertEquals(result[0] + ", " + result[1], threeStr);
    }


    @Test
    void testPickCoordinates_WhenValidColumn(){
        String res = "6, 1";
        int[] arr = logic.pickCoordinates("2","X");
        String actual = arr[0] + ", " + arr[1];

        assertEquals(res, actual);
    }

    @Test
    void testValidIsNumber(){
        assertTrue(logic.isNumber("2"));
        assertTrue(logic.isNumber("4"));
        assertTrue(logic.isNumber("5"));
    }

    @Test
    void testInValidIsNumber(){
        assertFalse(logic.isNumber("-"));
        assertFalse(logic.isNumber(""));
        assertFalse(logic.isNumber("]"));

    }






















}
