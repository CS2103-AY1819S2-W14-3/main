package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    private Set<Tag> tagSet;

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } else {
            tagSet = new HashSet<>();
        }

        return (new ListCommand(this.tagSet));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}