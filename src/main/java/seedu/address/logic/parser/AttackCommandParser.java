package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AttackCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cell.Coordinates;

/**
 * Parses attack commands.
 */
public class AttackCommandParser implements Parser<AttackCommand> {
    public static final String MESSAGE_INVALID_SQUARE = "%s is not a valid square.\n"
        + "A square must be written as a letter followed by a positive integer.";
    @Override
    public AttackCommand parse(String userInput) throws ParseException {
        String uInput = userInput.trim();
        if (uInput.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttackCommand.MESSAGE_USAGE));
        } else if (Coordinates.isValidCoordinates(uInput)) {
            return new AttackCommand(new Coordinates(uInput));
        } else {
            throw new ParseException(
                String.format(MESSAGE_INVALID_SQUARE, uInput));
        }
    }
}
