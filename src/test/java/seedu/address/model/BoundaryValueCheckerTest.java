
package seedu.address.model;

import static seedu.address.logic.commands.CommandTestUtil.VALID_HORIZONTAL_ORIENTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VERTICAL_ORIENTATION;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_A1;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_A10;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_A2;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_B1;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_J1;
import static seedu.address.testutil.TypicalIndexes.MAP_SIZE_TEN;

import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Cell;
import seedu.address.testutil.Assert;

/**
 * Contains integration tests (interaction with the Model).
 */
public class BoundaryValueCheckerTest {

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_battleshipAlreadyPresent_failure() {
        int mapSize = MAP_SIZE_TEN;
        Cell[][] cellGrid = new Cell[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        Battleship battleship = new DestroyerBattleship(new HashSet<>());

        model.getHumanMapGrid().initialise(cellGrid);

        Orientation orientation = new Orientation(VALID_HORIZONTAL_ORIENTATION);
        model.getHumanMapGrid().putShip(battleship, COORDINATES_A1, orientation);

        BoundaryValueChecker boundaryValueChecker = new BoundaryValueChecker(model.getHumanMapGrid(),
                battleship, COORDINATES_A1, orientation);

        Assert.assertThrows(CommandException.class, () -> boundaryValueChecker.performChecks());
    }

    @Test
    public void execute_putBattleshipVertical_failure() {
        int mapSize = MAP_SIZE_TEN;
        Cell[][] cellGrid = new Cell[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        Battleship battleship = new Battleship();

        model.getHumanMapGrid().initialise(cellGrid);

        Orientation orientation = new Orientation(VALID_VERTICAL_ORIENTATION);
        model.getHumanMapGrid().putShip(battleship, COORDINATES_B1, orientation);

        BoundaryValueChecker boundaryValueChecker = new BoundaryValueChecker(model.getHumanMapGrid(),
                battleship, COORDINATES_A1, orientation);

        Assert.assertThrows(CommandException.class, () -> boundaryValueChecker.performChecks());
    }

    @Test
    public void execute_putBattleshipHorizontal_failure() {
        int mapSize = MAP_SIZE_TEN;
        Cell[][] cellGrid = new Cell[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        Battleship battleship = new Battleship();

        model.getHumanMapGrid().initialise(cellGrid);

        Orientation orientation = new Orientation(VALID_HORIZONTAL_ORIENTATION);
        model.getHumanMapGrid().putShip(battleship, COORDINATES_A2, orientation);
        BoundaryValueChecker boundaryValueChecker = new BoundaryValueChecker(model.getHumanMapGrid(),
                battleship, COORDINATES_A1, orientation);

        Assert.assertThrows(CommandException.class, () -> boundaryValueChecker.performChecks());
    }

    @Test
    public void execute_battleshipTooHorizontal_failure() {
        int mapSize = MAP_SIZE_TEN;
        Cell[][] cellGrid = new Cell[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        model.getHumanMapGrid().initialise(cellGrid);
        Battleship battleship = new Battleship();

        Orientation orientation = new Orientation(VALID_HORIZONTAL_ORIENTATION);
        BoundaryValueChecker boundaryValueChecker = new BoundaryValueChecker(model.getHumanMapGrid(),
                battleship, COORDINATES_A10, orientation);

        Assert.assertThrows(CommandException.class, () -> boundaryValueChecker.performChecks());
    }

    @Test
    public void execute_battleshipTooVertical_failure() {
        int mapSize = MAP_SIZE_TEN;
        Cell[][] cellGrid = new Cell[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        model.getHumanMapGrid().initialise(cellGrid);
        Battleship battleship = new Battleship();

        Orientation orientation = new Orientation(VALID_VERTICAL_ORIENTATION);
        BoundaryValueChecker boundaryValueChecker = new BoundaryValueChecker(model.getHumanMapGrid(),
                battleship, COORDINATES_J1, orientation);

        Assert.assertThrows(CommandException.class, () -> boundaryValueChecker.performChecks());
    }
}
