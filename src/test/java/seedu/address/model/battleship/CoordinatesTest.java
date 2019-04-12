package seedu.address.model.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.cell.Coordinates;
import seedu.address.testutil.Assert;

public class CoordinatesTest {

    @Test
    public void isValidCoordinates() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Coordinates.isValidCoordinates(null));

        // invalid addresses
        assertFalse(Coordinates.isValidCoordinates("")); // empty string
        assertFalse(Coordinates.isValidCoordinates(" ")); // spaces only
        assertFalse(Coordinates.isValidCoordinates("*9")); // symbols
        assertFalse(Coordinates.isValidCoordinates("9")); // single digit
        assertFalse(Coordinates.isValidCoordinates("z")); // single alphabet
        assertFalse(Coordinates.isValidCoordinates("a0")); // invalid column
        assertFalse(Coordinates.isValidCoordinates("00")); // double digits

        // valid addresses
        assertTrue(Coordinates.isValidCoordinates("a1")); // starting coordinate
        assertTrue(Coordinates.isValidCoordinates("e5")); // in between coordinate
        assertTrue(Coordinates.isValidCoordinates("j9")); // extreme boundary
    }

    @Test
    public void testGetRow() {
        Coordinates coordinates = new Coordinates("b5");
        Index correctColIndex = Index.fromOneBased(5);
        assertEquals(coordinates.getColIndex(), correctColIndex);
    }

    @Test
    public void testGetCol() {
        Coordinates coordinates = new Coordinates("a1");
        Index correctColIndex = Index.fromOneBased(1);
        assertEquals(coordinates.getColIndex(), correctColIndex);
    }
}
