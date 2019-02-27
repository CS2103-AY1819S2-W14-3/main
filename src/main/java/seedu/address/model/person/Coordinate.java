package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Cell's coordinate object in the map.
 * Guarantees: immutable; is valid as declared in {@link #isValidCoordinate(String)}
 */
public class Coordinate {

    private static final String SPECIAL_CHARACTERS = "!#$%&'*+/=?`{|}~^.-";
    public static final String MESSAGE_CONSTRAINTS = "Coordinates should be of the format row-column "
            + "and adhere to the following constraints:\n"
            + "1. The row should only contain alphabetical characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + "). "
            + "The alphabetical characters must be between [a-j] inclusive and be in lowercase.\n"
            + "2. This is followed by a column number. "
            + "The column number must be between [1-10] inclusive.\n";

    // alphanumeric and special characters
    private static final String ROW_PART_REGEX = "^[a-j]{1}";
    private static final String COL_PART_REGEX = "(10|[1-9])$";

    public static final String VALIDATION_REGEX = ROW_PART_REGEX + COL_PART_REGEX;

    public final String value;

    /**
     * Constructs an {@code Coordinate}.
     *
     * @param coordinate A valid coordinate.
     */
    public Coordinate(String coordinate) {
        requireNonNull(coordinate);
        checkArgument(isValidCoordinate(coordinate), MESSAGE_CONSTRAINTS);
        value = coordinate;
    }

    /**
     * Returns if a given string is a valid coordinate.
     */
    public static boolean isValidCoordinate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Coordinate // instanceof handles nulls
                && value.equals(((Coordinate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
