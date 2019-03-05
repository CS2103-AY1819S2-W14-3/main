package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_FIRST_CELL;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_LAST_CELL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.MapGrid;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.cell.Coordinates;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class PutShipCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_battleshipAlreadyPresent_failure() {
        Battleship battleship = new Battleship();
        model.putShip(COORDINATES_FIRST_CELL, battleship);

        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_FIRST_CELL, battleship);

        Model expectedModel = new ModelManager(new MapGrid(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandFailure(putShipCommand, model, commandHistory, PutShipCommand.MESSAGE_BATTLESHIP_PRESENT);
    }

    @Test
    public void equals() {
        final PutShipCommand standardCommand = new PutShipCommand(COORDINATES_FIRST_CELL, new Battleship());

        // same values -> returns true
        PutShipCommand commandWithSameValues = new PutShipCommand(new Coordinates("a1"), new Battleship());
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different coordinates -> returns false
        assertFalse(standardCommand.equals(new PutShipCommand(COORDINATES_LAST_CELL, new Battleship())));

    }

}
