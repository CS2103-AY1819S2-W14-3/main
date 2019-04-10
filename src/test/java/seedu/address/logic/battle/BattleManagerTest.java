package seedu.address.logic.battle;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.SizeTenMapGrid.initialisePlayerSizeTen;
import static seedu.address.testutil.SizeTenMapGrid.setUpAlmostDefeat;
import static seedu.address.testutil.SizeTenMapGrid.setUpAlmostDestroy;
import static seedu.address.testutil.SizeTenMapGrid.setUpSingleShip;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.battleship.Battleship;
import seedu.address.model.player.Player;
import seedu.address.testutil.InterceptedEnemy;
import seedu.address.testutil.TypicalIndexes;

public class BattleManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Player player;
    private InterceptedEnemy enemy;
    private BattleManager batMan;

    @Before
    public void prepareModel() {
        player = new Player();
        enemy = new InterceptedEnemy();
        initialisePlayerSizeTen(player);
        initialisePlayerSizeTen(enemy);
        batMan = new BattleManager(player, enemy);
        batMan.setDelay(0);
    }

    /**
     * Expected result:
     *     beginGame() calls the prepEnemy() method of Enemy.
     */
    @Test
    public void beginGame_callsPrepEnemy_success() {
        batMan.beginGame();
        assertTrue(enemy.isPrepCalled());
    }

    /**
     * Setup:
     *     Enemy has one ship vertically, on a1
     *     Human attacks A1
     * Expected result:
     *     Human attack hits ship at A1
     *     Ship HP decreases
     *     a Hit AttackResult is returned
     */
    @Test
    public void humanPerformAttack_hit_hits() {
        Battleship ship = setUpSingleShip(enemy);
        int initialLife = ship.getLife();

        AttackResult res = batMan.humanPerformAttack(TypicalIndexes.COORDINATES_A1);
        assertTrue(ship.getLife() < initialLife);
        assertTrue(res.isHit());
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
    @Test
    public void humanPerformAttack_destroyShip_destroys() {
        Battleship ship1 = setUpAlmostDestroy(enemy);

        AttackResult res = batMan.humanPerformAttack(TypicalIndexes.COORDINATES_A1);
        assertTrue(ship1.isDestroyed());
        assertTrue(res.isDestroy());
    }

    /**
     * Setup:
     *     Enemy has one ship vertically, on a1, with 1HP remaining
     *     Human attacks A1
     * Expected result:
     *     Human attack hits ship at A1
     *     Ship HP decreases
     *     a Hit AttackResult is returned
     */
    @Test
    public void humanPerformAttack_destroyLastShip_wins() {
        Battleship ship1 = setUpAlmostDefeat(enemy);

        AttackResult res = batMan.humanPerformAttack(TypicalIndexes.COORDINATES_A1);
        assertTrue(ship1.isDestroyed());
        assertTrue(res instanceof AttackDefeatedEnemy);
    }
    /**
     * Setup:
     *     No ships placed
     *     Human attacks on J10
     * Expected result:
     *     Human attack misses at J10
     *     A non-Hit AttackResult is returned
     */
    @Test
    public void humanPerformAttack_miss_misses() {
        AttackResult res = batMan.humanPerformAttack(TypicalIndexes.COORDINATES_LAST_CELL);
        assertTrue(res instanceof AttackMissed);
    }

    /**
     * Setup:
     *     No ships placed
     *     Enemy begins attacking horizontally from a1
     * Expected result:
     *     Enemy attack misses at A1
     *     A singleton list containing a non-Hit AttackResult is returned
     */
    @Test
    public void takeComputerTurn_miss_takesOneTurn() {
        enemy.prepEnemy();

        List<AttackResult> res = batMan.takeComputerTurns();
        assertTrue(res.size() == 1);
        assertFalse(res.get(0).isHit());
    }

    /**
     * Setup:
     *     One ship vertically, on a1
     *     Enemy begins attacking horizontally from a1
     * Expected result:
     *     Enemy attack hits at A1 and misses at A2
     *     A singleton list containing a non-Hit AttackResult is returned
     */
    @Test
    public void takeComputerTurn_oneHit_takesTwoTurns() {
        enemy.prepEnemy();

        setUpSingleShip(player);

        List<AttackResult> res = batMan.takeComputerTurns();
        assertTrue(res.size() == 2);
        assertTrue(res.get(0) instanceof AttackHit);
        assertTrue(res.get(1) instanceof AttackMissed);
    }

    /**
     * Setup:
     *     One ship on a1 vertical, with 1 HP left
     *     One ship on j1 horizontal, full HP
     *     Enemy begins attacking horizontally from a1
     * Expected result:
     *     Enemy attack hits a1, destroys the entire ship, then hits a2 for a miss
     *     A doubleton list containing a AttackDestroyedShip and an AttackMissed is returned
     *     Enemy does not win the game
     */
    @Test
    public void takeComputerTurn_destroysShip_returnsDestroyResult() {
        enemy.prepEnemy();

        Battleship ship1 = setUpAlmostDestroy(player);

        List<AttackResult> res = batMan.takeComputerTurns();
        assertTrue(ship1.isDestroyed());
        assertTrue(res.size() == 2);
        assertTrue(res.get(0) instanceof AttackDestroyedShip);
        assertTrue(res.get(1) instanceof AttackMissed);
    }

    /**
     * Setup:
     *     One ship horizontally on a1 with 1HP
     *     Enemy begins attacking horizontally from a1
     * Expected result:
     *     Enemy attack hits horizontally, destroys the entire ship, then hits the next cell for a miss
     *     A singleton list containing a non-Hit AttackResult is returned
     *     Enemy does not win the game
     */
    @Test
    public void takeComputerTurn_winsGame_returnsWinResult() {
        enemy.prepEnemy();

        Battleship ship1 = setUpAlmostDefeat(player);

        List<AttackResult> res = batMan.takeComputerTurns();
        assertTrue(res.size() == 1);
        assertTrue(res.get(0) instanceof AttackDefeatedEnemy);
    }
}
