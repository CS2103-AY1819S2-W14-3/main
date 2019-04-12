package seedu.address.model.cell;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;

/**
 * Represents a Cell's coordinate object in the map.
 * Guarantees: immutable; is valid as declared in {@link #isValidCoordinates(String)}
 */
public class Coordinates {

    private static final String SPECIAL_CHARACTERS = "!#$%&'*+/=?`{|}~^.-";
    public static final String MESSAGE_CONSTRAINTS = "Coordinates should be of the format row-column "
            + "and adhere to the following constraints:\n"
            + "1. The row should only contain alphabetical characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + "). "
            + "The alphabetical characters must be between [a-j] inclusive and be in lowercase.\n"
            + "2. This is followed by a column number. "
            + "The column number must be between [1-10] inclusive.\n";

    // alphanumeric and special characters
    private static final String ROW_PART_REGEX = "^([a-z]){1}";
    private static final String COL_PART_REGEX = "(\\d+){1}$";
    private static final String COL_PART_REGEX_NON_MATCH = "[a-z]0";

    private static final String VALIDATION_REGEX = ROW_PART_REGEX + COL_PART_REGEX;

    private final Index rowIndex;
    private final Index colIndex;

    private final Logger logger = LogsCenter.getLogger(Coordinates.class);

    /**
     * Constructs an {@code Coordinates}.
     *
     * @param coordinate A valid coordinate.
     */
    public Coordinates(String coordinate) throws NumberFormatException {
        requireNonNull(coordinate);
        checkArgument(isValidCoordinates(coordinate), MESSAGE_CONSTRAINTS);

        logger.info("COORDINATES INITIALISED.");
        String value = coordinate;

        // use regex to extract alphabetical row and numeric col
        Pattern rowRegex = Pattern.compile(ROW_PART_REGEX);
        Pattern colRegex = Pattern.compile(COL_PART_REGEX);

        Matcher rowMatch = rowRegex.matcher(value);
        Matcher colMatch = colRegex.matcher(value);

        rowMatch.find();
        colMatch.find();

        String row = rowMatch.group(0);
        String col = colMatch.group(0);

        int rowNum = convertAlphabetToNumber(row);
        int colNum = Integer.parseInt(col);

        this.rowIndex = Index.fromOneBased(rowNum);
        this.colIndex = Index.fromOneBased(colNum);
    }

    public Coordinates(int rowZeroBased, int colZeroBased) {
        logger.info("COORDINATES INITIALISED.");
        this.rowIndex = Index.fromZeroBased(rowZeroBased);
        this.colIndex = Index.fromZeroBased(colZeroBased);
    }

    public Coordinates(Index rowIndex, Index colIndex) {
        logger.info("COORDINATES INITIALISED.");
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    /**
     * Converts a string alphabet to its numerical equivalent.
     *
     * @param alphabet String of alphabet
     * @return integer offset from 'a', zero-based
     */
    public int convertAlphabetToNumber(String alphabet) {
        char alphabetChar = alphabet.charAt(0);
        return (int) alphabetChar - 'a' + 1;
    }

    /**
     * Returns Index for rowNum
     */
    public Index getRowIndex() {
        return this.rowIndex;
    }

    /**
     * Return Index for colNum
     */
    public Index getColIndex() {
        return this.colIndex;
    }

    /**
     * Returns if a given string is a valid coordinate.
     */
    public static boolean isValidCoordinates(String test) {
        return test.matches(VALIDATION_REGEX) && !test.matches(COL_PART_REGEX_NON_MATCH);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        char colChar = (char) (this.rowIndex.getZeroBased() + 'a');

        stringBuilder.append(colChar)
                .append(this.colIndex.getOneBased());

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Coordinates) // instanceof handles nulls
                && this.rowIndex.equals(((Coordinates) other).rowIndex)
                && this.colIndex.equals(((Coordinates) other).colIndex); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, colIndex);
    }

}
