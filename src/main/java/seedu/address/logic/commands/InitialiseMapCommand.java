package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAP_SIZE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Initialise map to size input by user.
 */
public class InitialiseMapCommand extends Command {

    public static final String COMMAND_WORD = "init";
    public static final String COMMAND_ALIAS = "i";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_MAP_SIZE + "MAPSIZE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MAP_SIZE + "8 ";

    public static final String MESSAGE_SUCCESS = "Map initialised to size: %d";

    private final Person toAdd;
    private final int mapSize;

    /**
     * Initialise map command
     */
    public InitialiseMapCommand(Person person, int size) {
        requireNonNull(person);
        mapSize = size;
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        /**
        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }*/

        model.addPerson(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, mapSize));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InitialiseMapCommand // instanceof handles nulls
                && toAdd.equals(((InitialiseMapCommand) other).toAdd));
    }
}
