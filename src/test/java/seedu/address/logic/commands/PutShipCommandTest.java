
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

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.battleship.AircraftCarrierBattleship;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model).
 */
public class PutShipCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();
    private final Set<Tag> emptySet = new HashSet<>();

    @Before
    public void setState() {
        model.setBattleState(BattleState.PLAYER_PUT_SHIP);
    }

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
        Orientation orientation = new Orientation(VALID_HORIZONTAL_ORIENTATION);

        model.getHumanMapGrid().putShip(battleship, COORDINATES_A1, orientation);
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
        Orientation orientation = new Orientation(VALID_VERTICAL_ORIENTATION);

        model.getHumanMapGrid().putShip(battleship, COORDINATES_B1, orientation);
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
        Orientation orientation = new Orientation(VALID_HORIZONTAL_ORIENTATION);

        model.getHumanMapGrid().putShip(battleship, COORDINATES_A2, orientation);
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
                Coordinates cellCoords = new Coordinates(
                        COORDINATES_A1.getRowIndex().getZeroBased(),
                        COORDINATES_A1.getColIndex().getZeroBased() + i);
                Status status = model.getHumanMapGrid().getCellStatus(cellCoords);

                assertTrue(status == Status.SHIP);
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
                Coordinates cellCoords = new Coordinates(
                        COORDINATES_A1.getRowIndex().getZeroBased() + i,
                        COORDINATES_A1.getColIndex().getZeroBased());
                Status status = model.getHumanMapGrid().getCellStatus(cellCoords);
                assertTrue(status == Status.SHIP);
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
        model.getHumanMapGrid().putShip(battleship, COORDINATES_A2, orientation);
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
        assertFalse(standardCommand.equals(new BeginCommand()));

        // different coordinates -> returns false
        assertFalse(standardCommand.equals(new PutShipCommand(
                COORDINATES_LAST_CELL, new Battleship(), new Orientation(VALID_HORIZONTAL_ORIENTATION))));

    }

    @Test
    public void execute_invalidState_throwAssertionError() throws CommandException {
        thrown.expect(AssertionError.class);
        PutShipCommand cmd = new PutShipCommand(
            COORDINATES_LAST_CELL, new Battleship(), new Orientation(VALID_HORIZONTAL_ORIENTATION));
        model.setBattleState(BattleState.PLAYER_ATTACK);
        cmd.execute(model, new CommandHistory());
    }
}
