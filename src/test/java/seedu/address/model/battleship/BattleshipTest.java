package seedu.address.model.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Objects;

import java.util.Set;

import org.junit.Test;

import seedu.address.model.tag.Tag;

public class BattleshipTest {

    private final Set<Tag> emptySet = new HashSet<>();

    @Test
    public void testDefaultConstructors() {
        Battleship battleshipOne = new Battleship();
        Battleship battleshipTwo = new Battleship(new Name("destroyer"), new HashSet<>());
        Battleship battleshipThree = new Battleship(new Name("destroyer"), 2, 2);
        Battleship battleshipFour = new Battleship(new Name("destroyer"));

        HashSet<Tag> emptySet = new HashSet<>();
        Battleship battleshipOneShadow = new Battleship(new Name("placeholder"), 2, 2, emptySet);
        Battleship battleshipTwoShadow = new Battleship(new Name("destroyer"), 2, 2, emptySet);
        Battleship battleshipThreeShadow = new Battleship(new Name("destroyer"), 2, 2, emptySet);
        Battleship battleshipFourShadow = new Battleship(new Name("destroyer"), 2, 2, emptySet);

        assertEquals(battleshipOne, battleshipOneShadow);
        assertEquals(battleshipTwo, battleshipTwoShadow);
        assertEquals(battleshipThree, battleshipThreeShadow);
        assertEquals(battleshipFour, battleshipFourShadow);
    }

    @Test
    public void testToString() {
        Battleship battleship = new Battleship();
        assertEquals(battleship.toString(), "placeholder");
    }

    @Test
    public void testHash() {
        Battleship battleship = new Battleship();
        assertEquals(battleship.hashCode(), Objects.hash(
                battleship.getName(),
                battleship.getId(),
                battleship.getLength(),
                battleship.getLife(),
                battleship.getTags()
        ));
    }

    @Test
    public void testIsSameBattleship() {
        // create variables
        Battleship battleshipOne = new DestroyerBattleship(emptySet);
        Battleship battleshipTwo = new DestroyerBattleship(emptySet);
        Battleship battleshipThree = battleshipOne;

        // test equality
        assertFalse(battleshipOne.isSameBattleship(battleshipTwo));
        assertTrue(battleshipOne.isSameBattleship(battleshipThree));
    }

    @Test
    public void testDifferentNameDifferentBattleship() {

        // create variables
        Battleship battleshipOne = new DestroyerBattleship(emptySet);;
        Battleship battleshipTwo = new CruiserBattleship(emptySet);

        // test equality
        assertFalse(battleshipOne.equals(battleshipTwo));
    }

    @Test
    public void testSameNameDifferentBattleship() {

        // create variables
        Battleship battleshipOne = new DestroyerBattleship(emptySet);
        Battleship battleshipTwo = new DestroyerBattleship(emptySet);

        // test equality
        assertTrue(battleshipOne.equals(battleshipTwo));
    }

    @Test
    public void testSameNameSameBattleship() {

        // create variables
        Battleship battleshipOne = new DestroyerBattleship(emptySet);
        Battleship battleshipTwo = battleshipOne;

        // test equality
        assertTrue(battleshipOne.equals(battleshipTwo));
    }

    @Test
    public void testBattleshipLength() {
        // create variables
        Battleship battleshipOne = new DestroyerBattleship(emptySet);
        assertEquals(battleshipOne.getLength(), 3);
    }

    @Test
    public void testBattleshipLife() {
        // create variables
        Battleship battleshipOne = new DestroyerBattleship(emptySet);
        assertEquals(battleshipOne.getLife(), 3);
    }

    @Test
    public void testBattleshipReduceLife() {
        // create variables
        Battleship battleshipOne = new DestroyerBattleship(emptySet);
        int initialLife = battleshipOne.getLife();

        battleshipOne.reduceLife();

        assertEquals(battleshipOne.getLife(), initialLife - 1);
    }

    @Test
    public void testBattleshipDestroyed() {
        // create variables
        Battleship battleshipOne = new DestroyerBattleship(emptySet);
        int initialLife = battleshipOne.getLife();

        for (int i = 0; i < initialLife; i++) {
            battleshipOne.reduceLife();
        }

        assertEquals(battleshipOne.isDestroyed(), true);
    }

    @Test
    public void testAicraftCarrier() {
        Battleship aircraftCarrier = new AircraftCarrierBattleship(emptySet);
        assertEquals(aircraftCarrier.getLength(), 5);
    }

    @Test
    public void testDestroyer() {
        Battleship destroyerBattleship = new DestroyerBattleship(emptySet);
        assertEquals(destroyerBattleship.getLength(), 3);
    }

    @Test
    public void testCruiser() {
        Battleship cruiserBattleship = new CruiserBattleship(emptySet);
        assertEquals(cruiserBattleship.getLength(), 2);
    }
}
