package seedu.address.model.player;

import static org.assertj.core.api.Assertions.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.SizeTenMapGrid.initialisePlayerSizeTen;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.cell.Status;

public class EnemyTest {

    private Enemy testEnemy;
    private Player testPlayer;

    @Before
    public void readyEnemyForTesting() {

        testEnemy = new Enemy();
        initialisePlayerSizeTen(testEnemy);
        testPlayer = new Player("Bob", 5, 2, 1);


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

        testEnemy.receiveStatus(Status.SHIPHIT);

        assertThat(testEnemy.getLastAttackStatus(), is(Status.SHIPHIT));
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

    @Test public void test_enemyShootAt() {
        //stub
    }


    /**
     * Expected result:
     *
     *  testEnemy should have its lastAttackStatus attribute be updated to
     *  the status passed into the receiveStatus method
     *
     */
    @Test public void test_receiveStatus() {

        Status giveStatus = Status.EMPTY;
        testEnemy.receiveStatus(giveStatus);

        assertThat(testEnemy.getLastAttackStatus(), is(giveStatus));
        assertThat(testEnemy.getLastAttackStatus(), is(not(Status.DESTROYED)));

    }

}
