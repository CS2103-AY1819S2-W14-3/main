package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.model.cell.Coordinates;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PERSON = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PERSON = Index.fromOneBased(3);

    public static final Coordinates COORDINATES_FIRST_CELL = new Coordinates("a1");
    public static final Coordinates COORDINATES_LAST_CELL = new Coordinates("j10");
}
