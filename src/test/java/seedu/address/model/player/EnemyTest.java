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


    /**
     * Setup:
     *     Enemy has one ship vertically, on a1, with 1HP remaining
     *     Enemy has another ship somewhere
     *     Human attacks A1
     * Expected result:
     *     Human attack hits ship at A1
     *     Ship HP decreases
     *     a Hit AttackResult is returned
     */
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


    /**
     * Setup:
     *     Watchlist is not empty
     *     A status of DESTROYED was detected before this method call
     * Expected result:
     *     All elements in the watchlist are put in allPossibleTargets
     *     and also in allParityTargets if it satisfied the hasParity() constraint
     *     this method returns void
     */
    @Test public void test_cleanseWatchlist() {
        //stub
        //provide non empty watchlist
        //copy this to watchlistCopy
        //run cleanse watchlist
        //check that watchlist is now empty
        //check that all parity targets have parity
        //allParityTargets is subset of allPossibleTargets
        //allPossibleTargets should be super set of watchlistCopy
    }
}
