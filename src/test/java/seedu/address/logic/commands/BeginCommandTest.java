package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.SizeTenMapGrid.initialisePlayerSizeTen;

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
import seedu.address.model.player.Player;
import seedu.address.testutil.InterceptedEnemy;

public class BeginCommandTest {
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
    }

    @Test
    public void execute_userPutShipState_callsEnemyPrepAndSetsState() throws CommandException {
        model.setBattleState(BattleState.PLAYER_PUT_SHIP);
        CommandResult res = new BeginCommand().execute(model, new CommandHistory());
        assertTrue(enemy.isPrepCalled());
        assertEquals(model.getBattleState(), BattleState.PLAYER_ATTACK);
        assertEquals(res.getFeedbackToUser(), BeginCommand.MESSAGE_BEGIN_SUCCESS);
    }

    @Test
    public void execute_invalidState_throwAssertionError() throws CommandException {
        thrown.expect(AssertionError.class);
        BeginCommand cmd = new BeginCommand();
        model.setBattleState(BattleState.PLAYER_ATTACK);
        cmd.execute(model, new CommandHistory());
    }
}
