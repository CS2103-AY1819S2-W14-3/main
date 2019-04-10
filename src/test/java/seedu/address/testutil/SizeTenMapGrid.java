package seedu.address.testutil;

import java.util.Collections;

import seedu.address.model.MapGrid;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
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

    /**
     * Set up the following scenario:
     *     Player has one ship at a1 vertical with 1 HP
     * @param p The player to set up
     * @return the ship that is about to be destroyed
     */
    public static Battleship setUpAlmostDefeat(Player p) {
        initialisePlayerSizeTen(p);

        Battleship ship1 = new DestroyerBattleship(Collections.emptySet());
        do {
            ship1.reduceLife();
        } while (ship1.getLife() != 1);
        Orientation vertical = new Orientation("v");
        p.getFleet().deployOneBattleship(ship1, TypicalIndexes.COORDINATES_A1, vertical);
        p.getMapGrid().putShip(ship1, TypicalIndexes.COORDINATES_A1, vertical);

        return ship1;
    }

    /**
     * Set up the following scenario:
     *     Player has one ship at a1 vertical with full HP
     * @param p The player to set up
     * @return The ship that was placed.
     */
    public static Battleship setUpSingleShip(Player p) {
        initialisePlayerSizeTen(p);

        Battleship ship = new DestroyerBattleship(Collections.emptySet());
        Orientation vertical = new Orientation("v");
        p.getFleet().deployOneBattleship(ship, TypicalIndexes.COORDINATES_A1, vertical);
        p.getMapGrid().putShip(ship, TypicalIndexes.COORDINATES_A1, vertical);

        return ship;
    }

    /**
     * Set up the following scenario:
     *     Player has one ship at a1 vertical with 1 HP
     *     Player has one ship at j1 horizontal with full HP
     * @param p The player to set up
     * @return the ship that is about to be destroyed
     */
    public static Battleship setUpAlmostDestroy(Player p) {
        initialisePlayerSizeTen(p);

        Battleship ship1 = new DestroyerBattleship(Collections.emptySet());
        do {
            ship1.reduceLife();
        } while (ship1.getLife() != 1);
        Orientation vertical = new Orientation("v");
        p.getFleet().deployOneBattleship(ship1, TypicalIndexes.COORDINATES_A1, vertical);
        p.getMapGrid().putShip(ship1, TypicalIndexes.COORDINATES_A1, vertical);

        Battleship ship2 = new DestroyerBattleship(Collections.emptySet());
        Orientation horizontal = new Orientation("h");
        p.getFleet().deployOneBattleship(ship2, TypicalIndexes.COORDINATES_J1, horizontal);
        p.getMapGrid().putShip(ship2, TypicalIndexes.COORDINATES_J1, horizontal);

        return ship1;
    }
}
