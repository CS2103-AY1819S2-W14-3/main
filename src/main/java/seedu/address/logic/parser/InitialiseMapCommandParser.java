package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.InitialiseMapCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new InitialiseMapCommand object
 */
public class InitialiseMapCommandParser implements Parser<InitialiseMapCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the InitialiseMapCommand
     * and returns an InitialiseMapCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InitialiseMapCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        int mapSize;
        try {
            mapSize = ParserUtil.parseMapSize(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    InitialiseMapCommand.MESSAGE_USAGE), pe);
        }


        return new InitialiseMapCommand(mapSize);
    }

}
