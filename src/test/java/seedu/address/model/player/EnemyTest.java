package seedu.address.model.player;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static seedu.address.testutil.SizeTenMapGrid.initialisePlayerSizeTen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;

public class EnemyTest {

    private Enemy testEnemy;
    private Player testPlayer;

    @Before
    public void readyEnemyForTesting() {

        testEnemy = new Enemy();
        testPlayer = new Player();
        initialisePlayerSizeTen(testEnemy);
        initialisePlayerSizeTen(testPlayer);
    }

    public static <T> boolean isSameListContents(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    //Lucy TO-FIX:
    @Test
    public void constructor_default() {
        //stub
    }

    @Test
    public void test_getName() {
        assertEquals("EnemyPlayer", testEnemy.getName());
    }

    @Test
    public void test_getFleetSize() {
        assertEquals(8, testEnemy.getFleetSize());
    }

    @Test
    public void test_getFleetContents() {
        assertEquals(testPlayer.getFleetContents(), testEnemy.getFleetContents());
    }

    @Test
    public void test_getMapGrid() {
        assertEquals(testPlayer.getMapGrid(), testEnemy.getMapGrid());
    }

    @Test public void test_getTargetHistory() {
        assertEquals(testPlayer.getTargetHistory(), testEnemy.getTargetHistory());
    }

    @Test public void test_getLastAttackStatus() {

        testEnemy.receiveStatus(Status.SHIP);

        assertThat(testEnemy.getLastAttackStatus(), is(Status.SHIP));
    }

    @Test public void test_getAllPossibleTargets() {
        assertTrue(testEnemy.getAllPossibleTargets() instanceof ArrayList);
    }

    @Test public void test_getAllPossiblePopulateCoords() {
        assertTrue(testEnemy.getAllPossiblePopulateCoords() instanceof ArrayList);
    }

    @Test public void test_getAllParityTargets() {
        assertTrue(testEnemy.getAllParityTargets() instanceof ArrayList);

    }


    /**
     * this method calls two other private methods:  fillWithAllCoords() and populateEnemyMap();
     * testing will be done in two phases, one for  fillWithAllCoords() and one for populateEnemyMap();
     *
     * Setup:
     *     Enemy should first have:
     *          empty lists: allPossibleTargets, allPossiblePopulateCoords, allParityTargets
     *          empty deployed fleet
     * Expected result:
     *     Enemy should have:
     *          filled allPossibleTargets, allPossiblePopulateCoords, allParityTargets
     *          allParityTargets should only contain coordinates that hasParity()
     *          allPossibleTargets must be size 100,
     *          allPossiblePopulateCoords must be < 100,
     *          allParityTargets must be 50
     *          allParityTargets should be proper subset of allPossibleTargets
     *          allPossiblePopulateCoords should be proper subset of allPossibleTargets
     *          filled deployed fleet with the correct number of battleships: 8 in total for  size 10 map
     *
     */
    @Test public void test_prepEnemy() {
        //tests for fillWithAllCoords()

        //first check empty lists: allPossibleTargets, allPossiblePopulateCoords, allParityTargets
        assertTrue(testEnemy.getAllPossibleTargets().isEmpty());
        assertTrue(testEnemy.getAllPossiblePopulateCoords().isEmpty());
        assertTrue(testEnemy.getAllParityTargets().isEmpty());

        //check that there are no deployed ships yet - deployedFleet is empty
        assertTrue(testEnemy.getFleet().getDeployedFleet().isEmpty());

        testEnemy.prepEnemy();

        //check that allPossibleTargets, allPossiblePopulateCoords, allParityTargets are not empty
        assertFalse(testEnemy.getAllPossibleTargets().isEmpty());
        assertFalse(testEnemy.getAllPossiblePopulateCoords().isEmpty());
        assertFalse(testEnemy.getAllParityTargets().isEmpty());

        //check that allParityTargets should only contain coordinates that hasParity()
        for (Coordinates parityElement : testEnemy.getAllParityTargets()) {
            assertTrue(testEnemy.hasParity(parityElement.getRowIndex().getZeroBased(),
                    parityElement.getColIndex().getZeroBased()));
        }

        //check sizes of allPossibleTargets, allPossiblePopulateCoords, allParityTargets
        assertEquals(testEnemy.getAllPossibleTargets().size(), 100);
        assertTrue(testEnemy.getAllPossiblePopulateCoords().size() < 100);
        assertEquals(testEnemy.getAllParityTargets().size(), 50);


        //check that allParityTargets should be proper subset of allPossibleTargets
        assertFalse(isSameListContents(testEnemy.getAllParityTargets(), testEnemy.getAllPossibleTargets()));
        assertTrue(testEnemy.getAllPossibleTargets().containsAll(testEnemy.getAllParityTargets()));

        //check that allPossiblePopulateCoords should be proper subset of allPossibleTargets
        assertFalse(isSameListContents(testEnemy.getAllPossiblePopulateCoords(), testEnemy.getAllPossibleTargets()));
        assertTrue(testEnemy.getAllPossibleTargets().containsAll(testEnemy.getAllPossiblePopulateCoords()));

        //check that the correct number of ships were deployed into deployedFleet
        assertEquals(testEnemy.getFleet().getSize(), 8);
    }


    /**
     *
     *
     *
     * Setup:
     *     Enemy should first have:
     *
     *
     * Expected result:
     *     Enemy should have:
     *
     *
     *
     *
     *
     *
     *
     */
    @Test public void test_enemyShootAt() {
        //stub
    }


    /**
     * Expected result:
     *  testEnemy should have its lastAttackStatus attribute be updated to
     *  the status passed into the receiveStatus method
     *
     */
    @Test public void test_receiveStatus() {

        Status giveStatus = Status.EMPTY;
        testEnemy.receiveStatus(giveStatus);

        assertThat(testEnemy.getLastAttackStatus(), is(giveStatus));
    }


}
