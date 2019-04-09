package seedu.address.model.cell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.battleship.Battleship;

public class CellTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void status() {
        Cell cell = new Cell();
        assertEquals(cell.getStatus(), Status.EMPTY);

        cell.receiveAttack();
        assertEquals(cell.getStatus(), Status.EMPTYHIT);

        Cell battleShipCell = new Cell();
        battleShipCell.putShip(new Battleship());
        assertEquals(battleShipCell.getStatus(), Status.SHIP);

        battleShipCell.receiveAttack();
        assertEquals(battleShipCell.getStatus(), Status.SHIPHIT);

        battleShipCell.receiveAttack();
        assertEquals(battleShipCell.getStatus(), Status.DESTROYED);
    }

    @Test
    public void hasBattleship() {
        Cell cell = new Cell();
        assertFalse(cell.hasBattleShip());

        cell.putShip(new Battleship());
        assertTrue(cell.hasBattleShip());
    }

    @Test
    public void copyConstructor() {
        Cell emptyCell = new Cell();
        Cell copyCell = new Cell(emptyCell);
        assertEquals(emptyCell.getBattleship(), copyCell.getBattleship());

        Cell battleShipCell = new Cell();
        battleShipCell.putShip(new Battleship());
        copyCell = new Cell(battleShipCell);

        assertEquals(battleShipCell.getBattleship(), copyCell.getBattleship());
    }

    @Test
    public void equals() {
        // Exact same cell
        Cell firstCell = new Cell(new Coordinates(0, 0));
        assertEquals(firstCell, firstCell);

        // Compare with null
        assertNotEquals(firstCell, null);

        // Same coordinates
        Cell secondCell = new Cell(new Coordinates(0, 0));
        assertEquals(firstCell, secondCell);

        // Ship one one cell. Both cell same coord
        secondCell.putShip(new Battleship());
        assertNotEquals(firstCell, secondCell);

        // Same coord. Same ship
        firstCell.putShip(new Battleship());
        assertEquals(firstCell, secondCell);

        // Not equals, different coordinates
        Cell diffCoordCell = new Cell(new Coordinates(0, 1));
        assertNotEquals(firstCell, diffCoordCell);
    }

    @Test
    public void toStringTest() {
        Cell cell = new Cell(new Coordinates("a1"));
        assertEquals(cell.toString(), "a1");
    }
}
