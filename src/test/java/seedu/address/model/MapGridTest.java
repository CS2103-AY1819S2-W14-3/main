package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.SizeTenMapGrid.getSizeTenMapGrid;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;

public class MapGridTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final MapGrid mapGrid = new MapGrid();

    @Test
    public void putShipTest() {
        MapGrid sizeTenMap = getSizeTenMapGrid();
        Battleship battleship = new Battleship();
        Orientation orientation = new Orientation("vertical");

        sizeTenMap.putShip(battleship, new Coordinates("a1"), orientation);

        assertEquals(sizeTenMap.get2dArrayMapGridCopy()[0][0].getBattleship().get(), battleship);
    }

    @Test
    public void attackCellTest() {
        MapGrid sizeTenmap = getSizeTenMapGrid();
        Battleship battleship = new Battleship();
        Orientation orientation = new Orientation("vertical");
        assertFalse(sizeTenmap.attackCell(new Coordinates("a1")));

        sizeTenmap.putShip(battleship, new Coordinates("a1"), orientation);
        assertTrue(sizeTenmap.attackCell(new Coordinates("a1")));
    }

    @Test
    public void equals() {
        MapGrid firstMapGrid = getSizeTenMapGrid();
        MapGrid sameMapGrid = new MapGrid(firstMapGrid);

        assertEquals(firstMapGrid, sameMapGrid);

        sameMapGrid.putShip(new Battleship(), new Coordinates(0, 0), new Orientation("vertical"));
        assertNotEquals(firstMapGrid, sameMapGrid);
    }

    @Test
    public void getPlayerMapView() {
        MapGrid mapGrid = getSizeTenMapGrid();
        Coordinates a1 = new Coordinates("a1");
        mapGrid.putShip(new Battleship(), a1, new Orientation("vertical"));

        Status[][] playerMapView = mapGrid.getPlayerMapView();
        assertEquals(playerMapView[0][0], mapGrid.getCellStatus(a1));
    }

    @Test
    public void getEnemyMapView() {
        MapGrid mapGrid = getSizeTenMapGrid();
        Coordinates a1 = new Coordinates("a1");
        mapGrid.putShip(new Battleship(), a1, new Orientation("vertical"));

        // Status hidden
        Status[][] enemyMapView = mapGrid.getEnemyMapView();
        assertEquals(enemyMapView[0][0], Status.HIDDEN);

        // Cell attacked
        mapGrid.attackCell(a1);
        enemyMapView = mapGrid.getEnemyMapView();
        assertEquals(enemyMapView[0][0], Status.SHIPHIT);
    }
}
