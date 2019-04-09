package seedu.address.testutil;

import seedu.address.model.MapGrid;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * A utility class that returns an empty size 10 grid
 */
public class SizeTenMapGrid {
    private static final int SIZE_TEN = 10;

    private static Cell[][] getSizeTenCellGrid() {
        Cell[][] cellGrid = new Cell[SIZE_TEN][SIZE_TEN];
        char row = 'a';
        for (int i = 0; i < SIZE_TEN; i++) {
            for (int j = 0; j < SIZE_TEN; j++) {
                cellGrid[i][j] = new Cell(new Coordinates(i, j));
            }
        }

        return cellGrid;
    }
    /**
     * Returns a size ten map
     */
    public static MapGrid getSizeTenMapGrid() {
        Cell[][] cellGrid = getSizeTenCellGrid();
        MapGrid newMap = new MapGrid();
        newMap.initialise(cellGrid);
        return newMap;
    }

    /**
     * Initialises a Player with a size 10 map grid.
     */
    public static void initialisePlayerSizeTen(Player player) {
        Cell[][] cellGrid = getSizeTenCellGrid();
        player.getMapGrid().initialise(cellGrid);
    }
}
