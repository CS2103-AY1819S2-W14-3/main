package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.AttackCommandParser.MESSAGE_INVALID_SQUARE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AttackCommand;
import seedu.address.model.cell.Coordinates;

/**
 * Test scope:
 *
 *
 */
public class AttackCommandParserTest {
    public static final String VALID_COORDINATES = "a1";
    public static final String VALID_WEIRD_COORDINATES = "z1312";
    public static final String INVALID_COLUMN = "$1";
    public static final String INVALID_ROW = "bb";
    public static final String INVALID_ROW_COLUMN = "7c";

    private AttackCommandParser parser = new AttackCommandParser();

    @Test
    public void parse_validArgs_returnsAttackCommand() {
        assertParseSuccess(parser,
            VALID_COORDINATES, new AttackCommand(new Coordinates(VALID_COORDINATES)));
    }

    @Test
    public void parse_weirdValidArgs_returnsAttackCommand() {
        assertParseSuccess(parser,
            VALID_WEIRD_COORDINATES, new AttackCommand(new Coordinates(VALID_WEIRD_COORDINATES)));
    }

    @Test
    public void parse_invalidColumn_throwsParseException() {
        assertParseFailure(parser,
            INVALID_COLUMN, String.format(MESSAGE_INVALID_SQUARE, INVALID_COLUMN));
    }

    @Test
    public void parse_invalidRow_throwsParseException() {
        assertParseFailure(parser,
            INVALID_ROW, String.format(MESSAGE_INVALID_SQUARE, INVALID_ROW));
    }

    @Test
    public void parse_invalidRowColumn_throwsParseException() {
        assertParseFailure(parser,
            INVALID_ROW_COLUMN, String.format(MESSAGE_INVALID_SQUARE, INVALID_ROW_COLUMN));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser,
            "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttackCommand.MESSAGE_USAGE));
    }
}
