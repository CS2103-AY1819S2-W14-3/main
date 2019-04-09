package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.SizeTenMapGrid.initialisePlayerSizeTen;

import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.BattleManager;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;
import seedu.address.testutil.InterceptedEnemy;
import seedu.address.testutil.TypicalIndexes;

public class AttackCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Player player;
    private InterceptedEnemy enemy;
    private Model model;

    @Before
    public void setUp() {
        player = new Player();
        enemy = new InterceptedEnemy();
        initialisePlayerSizeTen(player);
        initialisePlayerSizeTen(enemy);
        enemy.prepEnemy();
        model = new ModelManager(new BattleManager(player, enemy));
        model.setBattleState(BattleState.PLAYER_ATTACK);
    }

    @Test
    public void execute_duplicateAttack_fail() throws CommandException {
        /*
         * Expected behaviour: <br>
         * <ul>
         *     <li>The attack fails immediately.</li>
         *     <li>The enemy is not called to attack.</li>
         * </ul>
         */
        player.addToTargetHistory(TypicalIndexes.COORDINATES_A1);
        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        try {
            cmd.execute(model, new CommandHistory());
        } catch (CommandException ex) {
            assertTrue(ex.getMessage().contains(AttackCommand.MESSAGE_DUPLICATE));
            assertFalse(enemy.isEnemyShootAtCalled());
        }
    }

    @Test
    public void execute_attackHits_hitsUpdatesStats() throws CommandException {
        /*
         * Expected behaviour: <br>
         * <ul>
         *     <li>The attack hits the ship and decreases its life.</li>
         *     <li>The enemy is not called to attack.</li>
         *     <li>Statistics are updated.</li>
         * </ul>
         */
        Battleship ship = new DestroyerBattleship(Collections.emptySet());
        int initialLife = ship.getLife();
        enemy.getMapGrid().putShip(ship, TypicalIndexes.COORDINATES_A1, new Orientation("v"));

        int initialMissCount = model.getPlayerStats().getMissCount();
        int initialHitCount = model.getPlayerStats().getHitCount();

        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        CommandResult res = cmd.execute(model, new CommandHistory());

        // assert hit
        assertTrue(res.getFeedbackToUser().contains("hit"));
        assertTrue(ship.getLife() < initialLife);
        // assert updates stats
        assertTrue(model.getPlayerStats().getMissCount() == initialMissCount);
        assertTrue(model.getPlayerStats().getHitCount() > initialHitCount);
        assertTrue(player.getTargetHistory().contains(TypicalIndexes.COORDINATES_A1));
        // assert the enemy is not called to attack
        assertFalse(enemy.isEnemyShootAtCalled());
    }

    @Test
    public void execute_missAttack_missesAndUpdatesStats() throws CommandException {
        /*
         * Expected behaviour: <br>
         * <ul>
         *     <li>The attack misses.</li>
         *     <li>The enemy is called to attack.</li>
         * </ul>
         */
        int initialMissCount = model.getPlayerStats().getMissCount();
        int initialHitCount = model.getPlayerStats().getHitCount();

        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_LAST_CELL);
        CommandResult res = cmd.execute(model, new CommandHistory());

        // assert miss
        assertTrue(res.getFeedbackToUser().contains("miss"));
        // assert updates stats
        assertTrue(model.getPlayerStats().getMissCount() > initialMissCount);
        assertTrue(model.getPlayerStats().getHitCount() == initialHitCount);
        assertTrue(player.getTargetHistory().contains(TypicalIndexes.COORDINATES_LAST_CELL));
        // assert that enemy is called
        assertTrue(enemy.isEnemyShootAtCalled());
    }

    @Test
    public void execute_attackDestroysShip_destroysAndUpdatesStats() throws CommandException {
        /*
         * Expected behaviour: <br>
         * <ul>
         *     <li>The attack hits the ship and decreases its life to 0.</li>
         *     <li>The enemy is not called to attack.</li>
         *     <li>Statistics are updated.</li>
         * </ul>
         */
        Battleship ship = new DestroyerBattleship(Collections.emptySet());
        enemy.getMapGrid().putShip(ship, TypicalIndexes.COORDINATES_A1, new Orientation("v"));

        int initialDestroyedCount = model.getPlayerStats().getEnemyShipsDestroyed();

        // attack all the cells of the ship except one
        int shipLength = ship.getLength();
        for (int i = 1; i < shipLength; i++) {
            new AttackCommand(new Coordinates(i, 0)).execute(model, new CommandHistory());
        }

        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        CommandResult res = cmd.execute(model, new CommandHistory());

        // assert destroy
        assertTrue(res.getFeedbackToUser().contains("destroy"));
        assertTrue(ship.isDestroyed());
        // assert updates stats
        assertTrue(model.getPlayerStats().enemyShipsDestroyed() > initialDestroyedCount);
        assertTrue(player.getTargetHistory().contains(TypicalIndexes.COORDINATES_A1));
    }

    @Test
    public void execute_outOfBounds_fail() throws CommandException {
        /*
         * Expected behaviour: <br>
         * <ul>
         *     <li>The attack fails to hit anything..</li>
         *     <li>The enemy may or may not be called to attack.</li>
         * </ul>
         */
        Coordinates invalid = TypicalIndexes.INVALID_COORDINATE;

        AttackCommand cmd = new AttackCommand(invalid);
        CommandResult res = cmd.execute(model, new CommandHistory());
        assertTrue(res.getFeedbackToUser().contains("fail"));
    }

    @Test
    public void execute_invalidState_throwAssertionError() throws CommandException {
        /*
         * Expected behaviour: <br>
         * <ul>
         *     <li>The command fails immediately with an AssertionError.</li>
         * </ul>
         */
        thrown.expect(AssertionError.class);
        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        model.setBattleState(BattleState.PLAYER_PUT_SHIP);
        cmd.execute(model, new CommandHistory());
    }
}
