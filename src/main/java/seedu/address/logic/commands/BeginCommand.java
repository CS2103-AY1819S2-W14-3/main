package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Ends the ship-placing phase and begins the attacking phase.
 */
public class BeginCommand extends Command {
    public static final String COMMAND_WORD = "begin";
    public static final String COMMAND_ALIAS1 = "start";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Finishes placing ships and starts the game..\n";
    public static final String MESSAGE_BEGIN_SUCCESS = "Game has started";

    public BeginCommand() {
        setPermissibleStates(EnumSet.of(BattleState.PLAYER_PUT_SHIP));
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert canExecuteIn(model.getBattleState());

        model.setBattleState(BattleState.ENEMY_PUT_SHIP);
        model.getBattle().beginGame();
        model.setBattleState(BattleState.PLAYER_ATTACK);
        return new CommandResult(MESSAGE_BEGIN_SUCCESS);
    }
}
