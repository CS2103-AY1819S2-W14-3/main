package seedu.address.model.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.model.battleship.AircraftCarrierBattleship;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.CruiserBattleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.tag.Tag;


public class FleetTest {

    private final Fleet testFleet = new Fleet();
    private final Set<Tag> emptySet = new HashSet<>();

    @Test
    public void constructor_default() {
        Fleet f = new Fleet();
        assertEquals(8, f.getSize());
    }

    @Test
    public void constructor_customArg() {
        Fleet f = new Fleet(10, 10, 10);
        assertEquals(30, f.getSize());
    }

    @Test(
            expected = IllegalArgumentException.class
    )
    public void constructor_invalidInput_throwsIllegalArgumentException() {
        new Fleet(0, 0, 0);
    }

    @Test
    public void testGetSize() {
        assertEquals(8, testFleet.getSize());
    }

    @Test
    public void testGetFleetContents() {
        assertEquals(new Fleet(5, 2, 1).getDeployedFleet(),
                testFleet.getDeployedFleet());
    }

    @Test
    public void testGetDestroyerBattleship() {
        Fleet destroyerOnlyFleet = new Fleet(5, 0, 0);
        assertEquals(destroyerOnlyFleet.getDeployedFleet(),
                testFleet.getListOfDestroyerBattleship());
    }

    @Test
    public void testGetCruiserBattleship() {
        Fleet cruiserOnlyFleet = new Fleet(0, 2, 0);
        assertEquals(cruiserOnlyFleet.getDeployedFleet(),
                testFleet.getListOfCruiserBattleship());
    }

    @Test
    public void testGetAircraftCarrierBattleship() {
        Fleet aircraftCarrierOnlyFleet = new Fleet(0, 0, 1);
        assertEquals(aircraftCarrierOnlyFleet.getDeployedFleet(),
                testFleet.getListOfAircraftCarrierBattleship());
    }

    @Test
    public void testConstructorFail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Fleet(0, 0, 0); });
    }

    @Test
    public void testDeployBattleships() {
        Fleet testFleet = new Fleet(1, 1, 1);

        Coordinates testCoordinates = new Coordinates("a1");
        Orientation testOrientation = new Orientation("vertical");

        testFleet.deployOneBattleship(new DestroyerBattleship(emptySet), testCoordinates, testOrientation);
        testFleet.deployOneBattleship(new CruiserBattleship(emptySet), testCoordinates, testOrientation);
        testFleet.deployOneBattleship(new AircraftCarrierBattleship(emptySet), testCoordinates, testOrientation);

        assertEquals(testFleet.getNumDestroyer(), 0);
        assertEquals(testFleet.getNumCruiser(), 0);
        assertEquals(testFleet.getNumAircraftCarrier(), 0);
    }

    @Test
    public void testIsEnoughBattleships() {
        Fleet testFleet = new Fleet(1, 1, 1);

        Battleship testDestroyer = new DestroyerBattleship(emptySet);
        Battleship testCruiser = new CruiserBattleship(emptySet);
        Battleship testAircraftCarrier = new AircraftCarrierBattleship(emptySet);

        assertTrue(testFleet.isEnoughBattleship(testDestroyer, 1));
        assertTrue(testFleet.isEnoughBattleship(testCruiser, 1));
        assertTrue(testFleet.isEnoughBattleship(testAircraftCarrier, 1));

        Coordinates testCoordinates = new Coordinates("a1");
        Orientation testOrientation = new Orientation("vertical");

        testFleet.deployOneBattleship(new DestroyerBattleship(emptySet), testCoordinates, testOrientation);
        testFleet.deployOneBattleship(new CruiserBattleship(emptySet), testCoordinates, testOrientation);
        testFleet.deployOneBattleship(new AircraftCarrierBattleship(emptySet), testCoordinates, testOrientation);

        assertFalse(testFleet.isEnoughBattleship(testDestroyer, 1));
        assertFalse(testFleet.isEnoughBattleship(testCruiser, 1));
        assertFalse(testFleet.isEnoughBattleship(testAircraftCarrier, 1));

        Battleship testInvalidBattleship = new Battleship();
        assertFalse(testFleet.isEnoughBattleship(testInvalidBattleship, 1));
    }

    @Test
    public void testToString() {
        Fleet testFleet = new Fleet(1, 1, 1);
        final StringBuilder builder = new StringBuilder();

        builder.append(testFleet.getSize())
                .append(" Fleet size: ")
                .append(testFleet.getSize())
                .append(" Fleet contents: ")
                .append(testFleet.getDeployedFleet());

        assertEquals(testFleet.toString(), builder.toString());
    }

    @Test
    public void testGetAttributes() {
        Fleet testFleet = new Fleet(1, 1, 1);
        assertEquals(testFleet.getNumDestroyer(), 1);
        assertEquals(testFleet.getNumCruiser(), 1);
        assertEquals(testFleet.getNumAircraftCarrier(), 1);
    }

    @Test
    public void testResetFleet() {
        Fleet testFleet = new Fleet(6);
        assertEquals(testFleet.getNumDestroyer(), 1);
        assertEquals(testFleet.getNumCruiser(), 1);
        assertEquals(testFleet.getNumAircraftCarrier(), 1);

        Battleship testDestroyer = new DestroyerBattleship(emptySet);
        Coordinates testCoordinates = new Coordinates("a1");
        Orientation testOrientation = new Orientation("vertical");

        testFleet.deployOneBattleship(testDestroyer, testCoordinates, testOrientation);
        assertEquals(testFleet.getDeployedFleet().isEmpty(), false);

        testFleet.resetFleet(6);
        assertEquals(testFleet.getDeployedFleet().isEmpty(), true);
    }
}
