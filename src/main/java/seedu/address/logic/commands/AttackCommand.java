package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cell.Coordinates;

/**
 * Attacks a cell on the board.
 */
public class AttackCommand extends Command {

    public static final String COMMAND_WORD = "attack";
    public static final String COMMAND_ALIAS1 = "shoot";
    public static final String COMMAND_ALIAS2 = "hit";
    public static final String COMMAND_ALIAS3 = "fire";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Attacks the specified cell.\n"
        + "Parameters: "
        + "SQUARE (a letter followed by a positive integer)\n"
        + "Example: attack b5";

    private Coordinates coord;

    public AttackCommand(Coordinates coord) {
        this.coord = coord;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.updateUi();

        return new CommandResult(
                String.format("Attacked cell %s", coord.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof AttackCommand) {
            AttackCommand o = (AttackCommand) other;
            return (this == o) || (this.coord.equals(o.coord));
        } else {
            return false;
        }
    }
}
