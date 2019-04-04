
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HORIZONTAL_ORIENTATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VERTICAL_ORIENTATION;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_A1;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_A10;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_A2;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_B1;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_J1;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_LAST_CELL;
import static seedu.address.testutil.TypicalIndexes.MAP_SIZE_TEN;
import static seedu.address.testutil.TypicalPersons.getEmptyMapGrid;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.battleship.AircraftCarrierBattleship;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model).
 */
public class PutShipCommandTest {

    private Model model = new ModelManager(getEmptyMapGrid(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private final Set<Tag> emptySet = new HashSet<>();

    @Test
    public void execute_battleshipAlreadyPresent_failure() {
        int mapSize = MAP_SIZE_TEN;
        Cell[][] cellGrid = new Cell[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        Battleship battleship = new DestroyerBattleship(emptySet);

        model.getHumanMapGrid().initialise(cellGrid);
        model.getHumanMapGrid().getCell(COORDINATES_A1).putShip(battleship);

        Orientation orientation = new Orientation(VALID_HORIZONTAL_ORIENTATION);

        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_A1, battleship, orientation);

        assertCommandFailure(putShipCommand, model, commandHistory,
                PutShipCommand.MESSAGE_BATTLESHIP_PRESENT);
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
        model.getHumanMapGrid().getCell(COORDINATES_B1).putShip(battleship);

        Orientation orientation = new Orientation(VALID_VERTICAL_ORIENTATION);
        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_A1, battleship, orientation);

        assertCommandFailure(putShipCommand, model, commandHistory,
                PutShipCommand.MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL);
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
        model.getHumanMapGrid().getCell(COORDINATES_A2).putShip(battleship);

        Orientation orientation = new Orientation(VALID_HORIZONTAL_ORIENTATION);
        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_A1, battleship, orientation);

        assertCommandFailure(putShipCommand, model, commandHistory,
                PutShipCommand.MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL);
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
        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_A10, battleship, orientation);

        assertCommandFailure(putShipCommand, model, commandHistory,
                Messages.MESSAGE_BODY_LENGTH_TOO_LONG);
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
        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_J1, battleship, orientation);

        assertCommandFailure(putShipCommand, model, commandHistory,
                Messages.MESSAGE_BODY_LENGTH_TOO_LONG);
    }

    @Test
    public void execute_testPutHorizontal_success() {
        int mapSize = MAP_SIZE_TEN;
        Cell[][] cellGrid = new Cell[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        model.getHumanMapGrid().initialise(cellGrid);
        Battleship battleship = new AircraftCarrierBattleship(emptySet);

        Orientation orientation = new Orientation(VALID_HORIZONTAL_ORIENTATION);
        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_A1, battleship, orientation);

        try {
            putShipCommand.execute(model, commandHistory);

            // Test length of battleship
            for (int i = 0; i < battleship.getLength(); i++) {
                Cell cellToCheck = model.getHumanMapGrid().getCell(
                        COORDINATES_A1.getRowIndex().getZeroBased(),
                        COORDINATES_A1.getColIndex().getZeroBased() + i);
                assertTrue(cellToCheck.hasBattleShip());
            }
        } catch (CommandException ce) {
            throw new AssertionError("Test should not fail.");
        }
    }

    @Test
    public void execute_testPutVertical_success() {
        int mapSize = MAP_SIZE_TEN;
        Cell[][] cellGrid = new Cell[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        model.getHumanMapGrid().initialise(cellGrid);
        Battleship battleship = new AircraftCarrierBattleship(emptySet);

        Orientation orientation = new Orientation(VALID_VERTICAL_ORIENTATION);
        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_A1, battleship, orientation);

        try {
            putShipCommand.execute(model, commandHistory);

            // Test length of battleship
            for (int i = 0; i < battleship.getLength(); i++) {
                Cell cellToCheck = model.getHumanMapGrid().getCell(
                        COORDINATES_A1.getRowIndex().getZeroBased() + i,
                        COORDINATES_A1.getColIndex().getZeroBased());
                assertTrue(cellToCheck.hasBattleShip());
            }
        } catch (CommandException ce) {
            throw new AssertionError("Test should not fail.");
        }
    }

    @Test
    public void execute_notEnoughBattleships_failure() {

        int mapSize = MAP_SIZE_TEN;
        Cell[][] cellGrid = new Cell[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        model.getHumanMapGrid().initialise(cellGrid);
        Battleship battleship = new AircraftCarrierBattleship(emptySet);
        Orientation orientation = new Orientation(VALID_VERTICAL_ORIENTATION);

        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_A1, battleship, orientation);

        model.getHumanMapGrid().initialise(cellGrid);
        model.getHumanMapGrid().getCell(COORDINATES_A2).putShip(battleship);
        model.deployBattleship(battleship, COORDINATES_A2, orientation);

        assertCommandFailure(putShipCommand, model, commandHistory,
                "Not enough aircraft carriers.");

    }

    @Test
    public void equals() {
        final PutShipCommand standardCommand = new PutShipCommand(COORDINATES_A1,
                new Battleship(), new Orientation(VALID_HORIZONTAL_ORIENTATION));

        // same values -> returns true
        PutShipCommand commandWithSameValues = new PutShipCommand(
                new Coordinates("a1"), new Battleship(), new Orientation(VALID_HORIZONTAL_ORIENTATION));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different coordinates -> returns false
        assertFalse(standardCommand.equals(new PutShipCommand(
                COORDINATES_LAST_CELL, new Battleship(), new Orientation(VALID_HORIZONTAL_ORIENTATION))));

    }

}
