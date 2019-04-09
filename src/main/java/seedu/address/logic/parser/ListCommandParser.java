package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.battleship.Name;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    private Optional<Set<Tag>> tagSet = Optional.empty();
    private Optional<Name> name = Optional.empty();

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_NAME);

        if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            tagSet = Optional.of(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            name = Optional.of(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        return (new ListCommand(this.tagSet, this.name));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
