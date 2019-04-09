package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;

/**
 * Initialise map to size input by user.
 */
public class InitialiseMapCommand extends Command {

    public static final String COMMAND_WORD = "init";
    public static final String COMMAND_ALIAS = "i";

    public static final int MAXIMUM_MAP_SIZE = 10;
    public static final int MINIMUM_MAP_SIZE = 6;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Initialise the map to specified size. "
            + "Parameters: "
            + "MAPSIZE (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + "8 ";

    public static final String MESSAGE_SUCCESS = "Map initialised to size: %d";
    public static final String MESSAGE_INVALID_MAP_SIZE = "Map size must be between %d to %d, inclusive.";

    private final int mapSize;

    /**
     * Initialise map command
     */
    public InitialiseMapCommand(int size) {
        mapSize = size;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert canExecuteIn(model.getBattleState());

        if (mapSize > MAXIMUM_MAP_SIZE || mapSize < MINIMUM_MAP_SIZE) {
            throw new CommandException(String.format(MESSAGE_INVALID_MAP_SIZE, MINIMUM_MAP_SIZE, MAXIMUM_MAP_SIZE));
        }

        Cell[][] cellGrid = initialise2dArray(mapSize);

        model.getHumanMapGrid().initialise(cellGrid);
        model.getEnemyMapGrid().initialise(cellGrid);

        model.getHumanPlayer().resetFleet(mapSize);
        model.getEnemyPlayer().resetFleet(mapSize);

        model.getHumanPlayer().resetTargetHistory();
        model.getEnemyPlayer().resetTargetHistory();

        model.setBattleState(BattleState.PLAYER_PUT_SHIP);

        return new CommandResult(String.format(MESSAGE_SUCCESS, mapSize));
    }

    /**
     * Initialise the 2D array given the map size
     */
    private Cell[][] initialise2dArray(int mapSize) {
        Cell[][] cellGrid = new Cell[mapSize][mapSize];
        char row = 'a';

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {

                cellGrid[i][j] = new Cell(new Coordinates(String.format("%c%d", row + i, j + 1)));
            }
        }
        return cellGrid;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InitialiseMapCommand); // instanceof handles nulls
                //&& toAdd.equals(((InitialiseMapCommand) other).toAdd));
    }
}
