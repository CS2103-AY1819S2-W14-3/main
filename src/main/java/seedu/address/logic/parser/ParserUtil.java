package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.battleship.AircraftCarrierBattleship;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.CruiserBattleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Name;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_SIZE = "Map size is not a non-zero unsigned integer";

    /**
     * Parses mapSize into an int and returns it.
     * Leading and trailing whitespaces will be trimmed
     * @param mapSize
     * @throws ParseException if the specified mapSize is invalid (not non-zero unsigned integer)
     */
    public static int parseMapSize(String mapSize) throws ParseException {
        String trimmedSize = mapSize.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedSize)) {
            throw new ParseException(MESSAGE_INVALID_SIZE);
        }
        return Integer.parseInt(trimmedSize);
    }

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Name name} into a {@code Battleship}.
     * @throws ParseException if the given (@code name} is invalid.
     */
    public static Battleship parseBattleship(Name name, Set<Tag> tagSet) throws ParseException {
        requireNonNull(name);

        Battleship battleship;

        if (name.fullName.toLowerCase().equals("destroyer")) {
            battleship = new DestroyerBattleship(tagSet);
        } else if (name.fullName.toLowerCase().equals("cruiser")) {
            battleship = new CruiserBattleship(tagSet);
        } else if (name.fullName.toLowerCase().equals("aircraft carrier")) {
            battleship = new AircraftCarrierBattleship(tagSet);
        } else {
            throw new ParseException(Battleship.MESSAGE_CONSTRAINTS);
        }

        return battleship;
    }

    /**
     * Parses a {@code String coordinate} into a {@code Coordinate}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code coordinate} is invalid.
     */
    public static Coordinates parseCoordinates(String coordinate) throws ParseException {
        requireNonNull(coordinate);

        String trimmedCoordinate = coordinate.trim();
        if (!Coordinates.isValidCoordinates(trimmedCoordinate)
            || !StringUtil.isNonZeroUnsignedInteger(trimmedCoordinate.substring(1))) {
            throw new ParseException(Coordinates.MESSAGE_CONSTRAINTS);
        }

        return new Coordinates(trimmedCoordinate);
    }

    /**
     * Parses a {@code String orientation} into a {@code Orientation}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException
     */
    public static Orientation parseOrientation(String orientation) throws ParseException {
        requireNonNull(orientation);
        String trimmedOrientation = orientation.trim();
        if (!Orientation.isValidOrientation(trimmedOrientation)) {
            throw new ParseException(Orientation.MESSAGE_CONSTRAINTS);
        }
        return new Orientation(trimmedOrientation);
    }


    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
