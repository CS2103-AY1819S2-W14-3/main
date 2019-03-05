package seedu.address.model.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;



public class FleetTest {

    private final Fleet testFleet = new Fleet();

    @Test
    public void constructor_default() {
        Fleet f = new Fleet();
        assertEquals(5, f.getSize());
    }

    @Test
    public void constructor_customArg() {
        Fleet f = new Fleet(4);
        assertEquals(4, f.getSize());
    }

    @Test(
            expected =
                    IllegalArgumentException.class
        ) public void constructor_invalidInput_throwsIllegalArgumentException() {
        new Fleet(0);
    }

    @Test
    public void testGetSize() {
        assertEquals(5, testFleet.getSize());
    }

    @Test
    public void testGetFleetContents() {
        assertEquals(new Fleet(5).getFleetContents(), testFleet.getFleetContents());
    }
}
