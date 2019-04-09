package seedu.address.model.battleship;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents the orientation of a battleship on the map.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrientation(String)}
 */
public class Orientation {

    public static final String MESSAGE_CONSTRAINTS =
            "Orientation should either be horizontal or vertical, case-insensitive";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(horizontal|vertical|h|v)$";
    private static final String VALIDATION_HORIZONTAL_REGEX = "horizontal|h";
    private static final String VALIDATION_VERTICAL_REGEX = "vertical|v";

    public static final Pattern VALIDATION_PATTERN = Pattern.compile(
            VALIDATION_REGEX, Pattern.CASE_INSENSITIVE);
    public static final Pattern VALIDATION_PATTERN_HORIZONTAL = Pattern.compile(
            VALIDATION_HORIZONTAL_REGEX, Pattern.CASE_INSENSITIVE);
    public static final Pattern VALIDATION_PATTERN_VERTICAL = Pattern.compile(
            VALIDATION_VERTICAL_REGEX, Pattern.CASE_INSENSITIVE);

    public final String orientation;

    private final Logger logger = LogsCenter.getLogger(Orientation.class);

    /**
     * Constructs a {@code Name}.
     *
     * @param orientation A valid name.
     */
    public Orientation(String orientation) {
        requireNonNull(orientation);
        checkArgument(isValidOrientation(orientation), MESSAGE_CONSTRAINTS);

        if (orientation.toLowerCase().equals("h")
                || orientation.toLowerCase().equals("horizontal")) {
            this.orientation = "horizontal";
        } else if (orientation.toLowerCase().equals("v")
                || orientation.toLowerCase().equals("vertical")) {
            this.orientation = "vertical";
        } else {
            this.orientation = orientation;
        }

        logger.fine("Created orientation");
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidOrientation(String test) {
        Matcher matcher = VALIDATION_PATTERN.matcher(test);
        return matcher.find();
    }

    /**
     * Checks if orientation is left
     */
    public boolean isHorizontal() {
        return VALIDATION_PATTERN_HORIZONTAL.matcher(this.orientation).find();
    }

    /**
     * Checks if orientation is right
     */
    public boolean isVertical() {
        return VALIDATION_PATTERN_VERTICAL.matcher(this.orientation).find();
    }

    @Override
    public String toString() {
        return this.orientation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Orientation // instanceof handles nulls
                && orientation.equals(((Orientation) other).orientation)); // state check
    }

    @Override
    public int hashCode() {
        return orientation.hashCode();
    }

}
