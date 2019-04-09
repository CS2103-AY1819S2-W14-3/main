package seedu.address.logic.battle;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.SizeTenMapGrid.initialisePlayerSizeTen;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.player.Player;
import seedu.address.testutil.InterceptedEnemy;
import seedu.address.testutil.TypicalIndexes;

public class BattleManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Player player;
    private InterceptedEnemy enemy;
    private Battle batMan;

    @Before
    public void prepareModel() {
        player = new Player();
        enemy = new InterceptedEnemy();
        initialisePlayerSizeTen(player);
        initialisePlayerSizeTen(enemy);
        enemy.prepEnemy();
        batMan = new BattleManager(player, enemy);
    }

    @Test
    public void beginGame_callsPrepEnemy_success() {
        batMan.beginGame();
        assertTrue(enemy.isPrepCalled());
    }

    @Test
    public void humanPerformAttack_hit_hits() {
        /**
         * Expected result:
         * Human attack hits ship at A1
         * Ship HP decreases
         * a Hit AttackResult is returned
         */
        Battleship ship = new DestroyerBattleship(Collections.emptySet());
        int initialLife = ship.getLife();
        enemy.getMapGrid().putShip(ship, TypicalIndexes.COORDINATES_A1, new Orientation("v"));

        AttackResult res = batMan.humanPerformAttack(TypicalIndexes.COORDINATES_A1);
        assertTrue(ship.getLife() < initialLife);
        assertTrue(res.isHit());
    }

    @Test
    public void humanPerformAttack_miss_misses() {
        /**
         * Expected result:
         * Human attack misses at J10
         * A non-Hit AttackResult is returned
         */

        AttackResult res = batMan.humanPerformAttack(TypicalIndexes.COORDINATES_LAST_CELL);
        assertFalse(res.isHit());
    }

    @Test
    public void takeComputerTurn_miss_takesOneTurn() {
        /**
         * Expected result:
         * Enemy attack misses at A1
         * A singleton list containing a non-Hit AttackResult is returned
         */
        List<AttackResult> res = batMan.takeComputerTurns();
        assertTrue(res.size() == 1);
        assertFalse(res.get(0).isHit());
    }

    @Test
    public void takeComputerTurn_oneHit_takesTwoTurns() {
        /**
         * Expected result:
         * Enemy attack hits at A1 and misses at A2
         * A singleton list containing a non-Hit AttackResult is returned
         */
        Battleship ship = new DestroyerBattleship(Collections.emptySet());
        player.getMapGrid().putShip(ship, TypicalIndexes.COORDINATES_A1, new Orientation("v"));

        List<AttackResult> res = batMan.takeComputerTurns();
        assertTrue(res.size() == 2);
        assertTrue(res.get(0).isHit());
        assertFalse(res.get(1).isHit());
    }

    @Test
    public void takeComputerTurn_destroysShip_takesNplusoneTurns() {
        /**
         * Expected result:
         * Enemy attack hits horizontally, destroys the entire ship, then hits the next cell for a miss
         * A singleton list containing a non-Hit AttackResult is returned
         */
        Battleship ship = new DestroyerBattleship(Collections.emptySet());
        int initialLife = ship.getLife();
        player.getMapGrid().putShip(ship, TypicalIndexes.COORDINATES_A1, new Orientation("h"));

        List<AttackResult> res = batMan.takeComputerTurns();
        assertTrue(res.size() == initialLife + 1);
        for (int i = 0; i < initialLife; i++) {
            assertTrue(res.get(i).isHit());
        }
        assertFalse(res.get(initialLife).isHit());
    }
}
