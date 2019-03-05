package seedu.address.model.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PlayerTest {

    public final Player testPlayer = new Player("Alice", 7);

    @Test
    public void constructor_default() {
        Player newPlayer1 = new Player();
        Player newPlayer2 = new Player("Player1", 5);
        assertEquals(newPlayer1, newPlayer2);
    }

    @Test
    public void isValidName() {
        //invalid names
        assertFalse(Player.isValidName("")); // empty string
        assertFalse(Player.isValidName("Ab")); // under min 3 chars
        assertFalse(Player.isValidName("AbCdEfGhIjklmnoPqrst")); // over max 16 chars
        assertFalse(Player.isValidName("John Doe")); // contains whitespace
        assertFalse(Player.isValidName("John!@#$")); // Contains invalid characters !#$%&'*+/=?`{|}~^.-
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
        assertEquals(7, testPlayer.getFleetSize());
    }

    @Test
    public void test_getFleetContents() {
        Player newPlayer1 = new Player("Bob", 7);
        assertEquals(newPlayer1.getFleetContents(), testPlayer.getFleetContents());
    }

    @Test
    public void test_getMapGrid() {
        Player newPlayer1 = new Player("Bob", 7);
        assertEquals(newPlayer1.getMapGrid(), testPlayer.getMapGrid());
    }

}
