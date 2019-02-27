package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CoordinateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidCoordinate() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Coordinate.isValidCoordinate("")); // empty string
        assertFalse(Coordinate.isValidCoordinate(" ")); // spaces only
        assertFalse(Coordinate.isValidCoordinate("z9")); // out of bounds row
        assertFalse(Coordinate.isValidCoordinate("a0")); // out of bounds column

        // valid addresses
        assertTrue(Coordinate.isValidCoordinate("a1")); // starting coordinate
        assertTrue(Coordinate.isValidCoordinate("e5")); // in between coordinate
        assertTrue(Coordinate.isValidCoordinate("j9")); // extreme boundary
    }
}
