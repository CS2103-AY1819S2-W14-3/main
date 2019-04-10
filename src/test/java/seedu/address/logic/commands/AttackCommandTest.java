package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.SizeTenMapGrid.initialisePlayerSizeTen;
import static seedu.address.testutil.SizeTenMapGrid.setUpAlmostDefeat;
import static seedu.address.testutil.SizeTenMapGrid.setUpAlmostDestroy;
import static seedu.address.testutil.SizeTenMapGrid.setUpSingleShip;

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
        model = new ModelManager(new BattleManager(player, enemy));
        model.setBattleState(BattleState.PLAYER_ATTACK);
    }

    /**
     * Scenario: <br />
     * <ul>
     *     <li>The player has already attacked A1</li>
     *     <li>The player tries to attack A1 again</li>
     * </ul>
     * Expected behaviour: <br />
     * <ul>
     *     <li>The attack fails immediately.</li>
     *     <li>The enemy is not called to attack.</li>
     *     <li>State remains at PLAYER_ATTACK.</li>
     * </ul>
     */
    @Test
    public void execute_duplicateAttack_fail() throws CommandException {
        player.addToTargetHistory(TypicalIndexes.COORDINATES_A1);
        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        try {
            cmd.execute(model, new CommandHistory());
            fail();
        } catch (CommandException ex) {
            assertTrue(ex.getMessage().contains(AttackCommand.MESSAGE_DUPLICATE));
            // assert the enemy is not called to attack
            assertFalse(enemy.isEnemyShootAtCalled());
            // assert correct state
            assertTrue(model.getBattleState() == BattleState.PLAYER_ATTACK);
        }
    }

    /**
     * Scenario: <br />
     * <ul>
     *     <li>Enemy has single ship on a1 vertical</li>
     *     <li>Player attacks a1</li>
     * </ul>
     *
     * Expected behaviour: <br />
     * <ul>
     *     <li>The attack hits the ship and decreases its life.</li>
     *     <li>The enemy is not called to attack.</li>
     *     <li>Statistics are updated.</li>
     *     <li>State remains at PLAYER_ATTACK.</li>
     * </ul>
     */
    @Test
    public void execute_attackHits_hitsUpdatesStats() throws CommandException {
        setUpSingleShip(enemy);

        int initialMissCount = model.getPlayerStats().getMissCount();
        int initialHitCount = model.getPlayerStats().getHitCount();

        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        CommandResult res = cmd.execute(model, new CommandHistory());

        // assert hit
        assertTrue(res.getFeedbackToUser().contains("hit"));
        // assert updates stats
        assertTrue(model.getPlayerStats().getMissCount() == initialMissCount);
        assertTrue(model.getPlayerStats().getHitCount() > initialHitCount);
        assertTrue(player.getTargetHistory().contains(TypicalIndexes.COORDINATES_A1));
        // assert the enemy is not called to attack
        assertFalse(enemy.isEnemyShootAtCalled());
        // assert correct state
        assertTrue(model.getBattleState() == BattleState.PLAYER_ATTACK);
    }

    /**
     * Scenario: <br />
     * <ul>
     *     <li>The user attacks j10, a cell with no ship.</li>
     * </ul>
     * Expected behaviour: <br>
     * <ul>
     *     <li>The attack misses.</li>
     *     <li>The enemy is called to attack.</li>
     *     <li>State remains at PLAYER_ATTACK.</li>
     * </ul>
     */
    @Test
    public void execute_missAttack_missesAndUpdatesStats() throws CommandException {
        int initialMissCount = model.getPlayerStats().getMissCount();
        int initialHitCount = model.getPlayerStats().getHitCount();
        enemy.prepEnemy();

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
        // assert correct state
        assertTrue(model.getBattleState() == BattleState.PLAYER_ATTACK);
    }

    /**
     * Scenario: <br />
     * <ul>
     *     <li>The enemy has one ship at a1 vertical with 1HP left</li>
     *     <li>The enemy has another ship somewhere at full HP</li>
     *     <li>The player attacks a1.</li>
     * </ul>
     * Expected behaviour: <br>
     * <ul>
     *     <li>The attack hits the ship and decreases its life to 0.</li>
     *     <li>The enemy is not called to attack.</li>
     *     <li>Statistics are updated.</li>
     *     <li>State remains at PLAYER_ATTACK.</li>
     * </ul>
     */
    @Test
    public void execute_attackDestroysShip_destroysAndUpdatesStats() throws CommandException {
        setUpAlmostDestroy(enemy);

        int initialDestroyedCount = model.getPlayerStats().getEnemyShipsDestroyed();

        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        CommandResult res = cmd.execute(model, new CommandHistory());

        // assert destroy
        assertTrue(res.getFeedbackToUser().contains("destroy"));
        // assert not win
        assertFalse(res.getFeedbackToUser().contains("won"));
        // assert updates stats
        assertTrue(model.getPlayerStats().enemyShipsDestroyed() > initialDestroyedCount);
        assertTrue(player.getTargetHistory().contains(TypicalIndexes.COORDINATES_A1));
        // assert correct state
        assertTrue(model.getBattleState() == BattleState.PLAYER_ATTACK);
    }

    /**
     * Scenario: <br />
     * <ul>
     *     <li>The enemy has one ship at a1 vertical with 1HP left</li>
     *     <li>The player attacks a1.</li>
     * </ul>
     * Expected behaviour: <br>
     * <ul>
     *     <li>The attack hits the ship and decreases its life to 0.</li>
     *     <li>The player wins..</li>
     *     <li>State changes to PLAYER_WIN.</li>
     * </ul>
     */
    @Test
    public void execute_destroyLastShip_playerWins() throws CommandException {
        setUpAlmostDefeat(enemy);

        int initialDestroyedCount = model.getPlayerStats().getEnemyShipsDestroyed();

        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        CommandResult res = cmd.execute(model, new CommandHistory());

        // assert win
        assertTrue(res.getFeedbackToUser().contains("won"));
        // assert updates stats
        assertTrue(model.getPlayerStats().enemyShipsDestroyed() > initialDestroyedCount);
        assertTrue(player.getTargetHistory().contains(TypicalIndexes.COORDINATES_A1));
        // assert correct state
        assertTrue(model.getBattleState() == BattleState.PLAYER_WIN);
    }

    /**
     * Scenario: <br />
     * <ul>
     *     <li>The player has one ship at a1 vertical with 1HP left</li>
     *     <li>The player attacks j10 and misses.</li>
     *     <li>The enemy attacks a1</li>
     * </ul>
     * Expected behaviour: <br>
     * <ul>
     *     <li>The attack hits the ship and decreases its life to 0.</li>
     *     <li>The enemy wins</li>
     *     <li>State changes to ENEMY_WIN.</li>
     * </ul>
     */
    @Test
    public void execute_enemyDestroysLastShip_playerWins() throws CommandException {
        setUpAlmostDefeat(player);
        enemy.prepEnemy();

        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_LAST_CELL);
        CommandResult res = cmd.execute(model, new CommandHistory());

        // assert win
        assertTrue(res.getFeedbackToUser().contains("lost"));
        // assert correct state
        assertTrue(model.getBattleState() == BattleState.ENEMY_WIN);
    }

    /**
     * Expected behaviour: <br />
     * <ul>
     *     <li>The attack fails to hit anything.</li>
     *     <li>The enemy may or may not be called to attack.</li>
     * </ul>
     */
    @Test
    public void execute_outOfBounds_fail() throws CommandException {
        Coordinates invalid = TypicalIndexes.INVALID_COORDINATE;

        AttackCommand cmd = new AttackCommand(invalid);
        CommandResult res = cmd.execute(model, new CommandHistory());
        assertTrue(res.getFeedbackToUser().contains("fail"));
    }

    /**
     * Scenario: <br />
     * <ul>
     *     <li>The command is executed in the PLAYER_PUT_SHIP state.</li>
     * </ul>
     * Expected behaviour: <br />
     * <ul>
     *     <li>The command fails immediately with an AssertionError.</li>
     * </ul>
     */
    @Test
    public void execute_invalidState_throwAssertionError() throws CommandException {
        thrown.expect(AssertionError.class);
        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        model.setBattleState(BattleState.PLAYER_PUT_SHIP);
        cmd.execute(model, new CommandHistory());
    }

    @Test
    public void equals() {
        AttackCommand cmd = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        AttackCommand cmd2 = new AttackCommand(TypicalIndexes.COORDINATES_A1);
        AttackCommand cmd3 = new AttackCommand(TypicalIndexes.COORDINATES_A2);

        // null fails
        assertFalse(cmd.equals(null));
        // wrong type
        assertFalse(cmd.equals(5));
        // same object
        assertTrue(cmd.equals(cmd));
        // same coordinate
        assertTrue(cmd.equals(cmd2));
        // different coordinate
        assertFalse(cmd.equals(cmd3));
    }
}
