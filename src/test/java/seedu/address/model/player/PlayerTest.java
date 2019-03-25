package seedu.address.model.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.cell.Coordinates;
import seedu.address.testutil.TypicalIndexes;

public class PlayerTest {

    public final Player testPlayer = new Player("Alice", 5, 2, 1);

    //Lucy TO-FIX:
    /*  @Test

    public void constructor_default() {
        Player newPlayer1 = new Player();
        Player newPlayer2 = new Player("Player1", 5);
        assertEquals(newPlayer1, newPlayer2);
    }
    */

    @Test
    public void isValidName() {
        //invalid names
        assertFalse(Player.isValidName("")); // empty string
        assertFalse(Player.isValidName("Ab")); // under min 3 chars
        assertFalse(Player.isValidName("AbCdEfGhIjklmnoPqrst")); // over max 16 chars
        assertFalse(Player.isValidName("John Doe")); // contains whitespace
        assertFalse(Player.isValidName("John!@#$")); // Contains invalid characters !#$%&'*+/=?`{|}~^.-
        assertFalse(Player.isValidName("Enemy")); // Contains saved name "Enemy" for computer enemy player.
        //valid names
        assertTrue(Player.isValidName("123AbCde123")); //Allows upper and lower case alphanumerical chars
        assertTrue(Player.isValidName("bob")); //satisfies min 3 char required
        assertTrue(Player.isValidName("AbCdEfGhIjklmnoP")); //at max 16 char limit
    }

    @Test
    public void test_getName() {
        assertEquals("Alice", testPlayer.getName());
    }

    @Test
    public void test_getFleetSize() {
        assertEquals(8, testPlayer.getFleetSize());
    }

    @Test
    public void test_getFleetContents() {
        Player newPlayer1 = new Player("Bob", 5, 2, 1);
        assertEquals(newPlayer1.getFleetContents(), testPlayer.getFleetContents());
    }

    @Test
    public void test_getMapGrid() {
        Player newPlayer1 = new Player("Bob", 5, 2, 1);
        assertEquals(newPlayer1.getMapGrid(), testPlayer.getMapGrid());
    }

    @Test
    public void test_getTargetHistory() {
        Player newPlayer1 = new Player("Bob", 5, 2, 1);
        assertEquals(newPlayer1.getTargetHistory(), testPlayer.getTargetHistory());
    }

    @Test
    public void addToTargetHistory_addDistinct_returnsTrue() {
        Player newPlayer1 = new Player();
        assertTrue(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_A1));
        assertTrue(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_A2));
        assertTrue(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_B1));
        assertTrue(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_J1));
    }

    @Test
    public void addToTargetHistory_addDuplicate_returnsFalse() {
        Player newPlayer1 = new Player();
        assertTrue(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_A1));
        assertTrue(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_A2));
        assertTrue(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_B1));
        assertTrue(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_J1));
        assertFalse(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_A1));
        assertFalse(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_A2));
        assertFalse(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_B1));
        assertFalse(newPlayer1.addToTargetHistory(TypicalIndexes.COORDINATES_J1));
    }
}
