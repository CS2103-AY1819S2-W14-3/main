package seedu.address.model.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EnemyTest {

    private final Enemy testPlayer = new Enemy();

    //Lucy TO-FIX:
    @Test
    public void constructor_default() {
        //stub
    }

    @Test
    public void test_getName() {
        assertEquals("EnemyPlayer", testPlayer.getName());
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

    @Test public void test_getTargetHistory() {
        Player newPlayer1 = new Player("Bob", 5, 2, 1);
        assertEquals(newPlayer1.getTargetHistory(), testPlayer.getTargetHistory());
    }

    @Test public void test_prepEnemy() {
        //stub
    }

    @Test public void test_fillWithAllCoords() {
        //stub
    }

    @Test public void test_enemyShootAt() {
        //stub
    }

    @Test public void test_drawParityTargets() {
        //stub
    }

    @Test public void test_drawFromWatchList() {
        //stub
    }

    @Test public void test_drawFromAllTargets() {
        //stub
    }

    @Test public void test_populateEnemyMap() {
        //stub
    }

    @Test public void test_placeAirCraftCarrier() {
        //stub
    }

    @Test public void test_placeMultipleDestroyerAndCruiser() {
        //stub
    }

    @Test public void test_generateBattleships() {
        //stub
    }

    @Test public void test_markAsOccupied() {
        //stub
    }

    @Test public void test_justifyCoord() {
        //stub
    }

    @Test public void test_generateOrientation() {
        //stub
    }

    @Test public void test_receiveStatus() {
        //stub
    }

    @Test public void test_updateWatchlist() {
        //stub
    }

    @Test public void test_isValidCardinal() {
        //stub
    }

    @Test public void test_modeCleanup() {
        //stub
    }

    @Test public void test_hasParity() {
        //stub
    }

    @Test public void test_cleanseWatchlist() {
        //stub
    }
}
