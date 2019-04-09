package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class InitialiseMapCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private CommandHistory history;

    @Before
    public void setUp() {
        model = new ModelManager();
        history = new CommandHistory();
        model.setBattleState(BattleState.PRE_BATTLE);
    }

    @Test
    public void execute_initialisemap_success() throws Exception {
        int validSize = InitialiseMapCommand.MINIMUM_MAP_SIZE;

        InitialiseMapCommand initialiseMapCommand = new InitialiseMapCommand(validSize);
        CommandResult commandResult = initialiseMapCommand.execute(model, history);

        assertEquals(String.format(InitialiseMapCommand.MESSAGE_SUCCESS, validSize), commandResult.getFeedbackToUser());
        assertEquals(validSize, model.getMapSize());
    }

    @Test
    public void execute_initialiseMapWithSizeMoreThanMax_throwsCommandException() throws Exception {
        int invalidSize = InitialiseMapCommand.MAXIMUM_MAP_SIZE + 1;

        InitialiseMapCommand initialiseMapCommand = new InitialiseMapCommand(invalidSize);


        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(InitialiseMapCommand.MESSAGE_INVALID_MAP_SIZE,
                InitialiseMapCommand.MINIMUM_MAP_SIZE, InitialiseMapCommand.MAXIMUM_MAP_SIZE));
        initialiseMapCommand.execute(model, history);
    }

    @Test
    public void execute_initialisemapWithSizeLessThanMin_throwsCommandException() throws Exception {
        int invalidSize = InitialiseMapCommand.MINIMUM_MAP_SIZE - 1;

        InitialiseMapCommand initialiseMapCommand = new InitialiseMapCommand(invalidSize);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(InitialiseMapCommand.MESSAGE_INVALID_MAP_SIZE,
                InitialiseMapCommand.MINIMUM_MAP_SIZE, InitialiseMapCommand.MAXIMUM_MAP_SIZE));
        initialiseMapCommand.execute(model, history);
    }
}
