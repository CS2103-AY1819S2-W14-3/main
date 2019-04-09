package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.battleship.AircraftCarrierBattleship;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.CruiserBattleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Name;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    private static final String INVALID_COORD_1 = "  ";
    private static final String INVALID_COORD_2 = "";
    private static final String INVALID_COORD_3 = "*9";
    private static final String INVALID_COORD_4 = "a0";

    private static final String VALID_COORD_1 = "a1";
    private static final String VALID_COORD_2 = "b3";

    private static final Set<Tag> emptySet = Collections.emptySet();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString((long) Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseCoordinates_invalidSpaces_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseCoordinates(INVALID_COORD_1);
    }

    @Test
    public void parseCoordinates_emptyString_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseCoordinates(INVALID_COORD_2);
    }

    @Test
    public void parseCoordinates_symbolString_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseCoordinates(INVALID_COORD_3);
    }

    @Test
    public void parseCoordinates_outOfBoundsToo_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseCoordinates(INVALID_COORD_4);
    }

    @Test
    public void parseCoordinates_validCoord_returnsNewCoordinates() throws Exception {
        Coordinates expectedCoordinates = new Coordinates(VALID_COORD_1);
        assertEquals(expectedCoordinates, ParserUtil.parseCoordinates(VALID_COORD_1));
    }

    @Test
    public void parseCoordinates_validCoord_returnsNewCoordinatesToo() throws Exception {
        Coordinates expectedCoordinates = new Coordinates(VALID_COORD_2);
        assertEquals(expectedCoordinates, ParserUtil.parseCoordinates(VALID_COORD_2));
    }

    @Test
    public void parseBattleship_validBattleship() throws Exception {
        Battleship expectedBattleshipOne = new AircraftCarrierBattleship(emptySet);
        Battleship expectedBattleshipTwo = new DestroyerBattleship(emptySet);
        Battleship expectedBattleshipThree = new CruiserBattleship(emptySet);

        assertEquals(expectedBattleshipOne, ParserUtil.parseBattleship(new Name("aircraft carrier"), emptySet));
        assertEquals(expectedBattleshipTwo, ParserUtil.parseBattleship(new Name("destroyer"), emptySet));
        assertEquals(expectedBattleshipThree, ParserUtil.parseBattleship(new Name("cruiser"), emptySet));
    }
}
