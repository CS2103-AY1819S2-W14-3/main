package seedu.address.model;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;
import seedu.address.model.exceptions.BoundaryValueException;

/**
 * Represents a boundary value checker for battleships.
 */
public class BoundaryValueChecker {
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Put ship in cell: %1$s";
    public static final String MESSAGE_BATTLESHIP_PRESENT = "There is already a ship on the coordinate.";
    public static final String MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL =
            "There is already a ship along the vertical coordinates";
    public static final String MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL =
            "There is already a ship along the horizontal coordinates";
    public static final String MESSAGE_BODY_LENGTH_TOO_LONG = "The battleship cannot fit into the space provided.";
    public static final String MESSAGE_OUT_OF_BOUNDS = "The ship falls out of bounds.";


    private static final Logger logger = LogsCenter.getLogger(BoundaryValueChecker.class);

    private final MapGrid mapGrid;
    private final Battleship battleship;
    private final Coordinates coordinates;
    private final Orientation orientation;

    /**
     * Default constructor method.
     *
     * @param mapGrid map grid of the game.
     * @param battleship battleship to be put on the map grid.
     * @param coordinates coordinates of the battleship on the map grid.
     * @param orientation orientation of the battleship on the map grid.
     */
    public BoundaryValueChecker(MapGrid mapGrid, Battleship battleship,
                                Coordinates coordinates, Orientation orientation) {
        this.mapGrid = mapGrid;
        this.battleship = battleship;
        this.coordinates = coordinates;
        this.orientation = orientation;
    }

    /**
     * Performs all the relevant checks.
     *
     * @throws CommandException when a check fails
     */
    public void performChecks() throws BoundaryValueException {
        if (!this.isHeadWithinBounds()) {
            throw new BoundaryValueException(MESSAGE_OUT_OF_BOUNDS);
        } else if (this.orientation.isHorizontal()) {
            if (!this.isBattleshipAbsent()) {
                logger.info("BATTLESHIP ABSENT. Throwing BoundaryValueException.");
                throw new BoundaryValueException(MESSAGE_BATTLESHIP_PRESENT);
            } else if (!this.isBodyWithinBounds(coordinates.getColIndex())) {
                logger.info("BATTLESHIP NOT WITHIN HORIZONTAL BOUNDS. Throwing BoundaryValueException.");
                throw new BoundaryValueException(MESSAGE_BODY_LENGTH_TOO_LONG);
            } else if (!this.isClear(orientation)) {
                logger.info("HORIZONTAL BOUNDS NOT CLEAR. Throwing BoundaryValueException.");
                throw new BoundaryValueException(MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL);
            }
        } else if (this.orientation.isVertical()) {
            if (!this.isBattleshipAbsent()) {
                logger.info("BATTLESHIP ABSENT. Throwing BoundaryValueException.");
                throw new BoundaryValueException(MESSAGE_BATTLESHIP_PRESENT);
            } else if (!this.isBodyWithinBounds(coordinates.getRowIndex())) {
                logger.info("BATTLESHIP NOT WITHIN VERTICAL BOUNDS. Throwing BoundaryValueException.");
                throw new BoundaryValueException(MESSAGE_BODY_LENGTH_TOO_LONG);
            } else if (!this.isClear(orientation)) {
                logger.info("BATTLESHIP NOT WITHIN VERTICAL BOUNDS. Throwing BoundaryValueException.");
                throw new BoundaryValueException(MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL);
            }
        }
    }

    /**
     * Checks if the head of a battleship is within bounds.
     *
     * @return boolean of whether the battleship head falls within bounds.
     */
    public boolean isHeadWithinBounds() {
        Index rowIndex = coordinates.getRowIndex();
        Index colIndex = coordinates.getColIndex();

        if ((rowIndex.getZeroBased() >= mapGrid.getMapSize())
                || colIndex.getZeroBased() >= mapGrid.getMapSize()) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the body of a battleship is within bounds.
     *
     * @return boolean of whether the battleship body falls within bounds.
     */
    public boolean isBodyWithinBounds(Index index) {
        if (index.getZeroBased() + battleship.getLength() > mapGrid.getMapSize()) {
            return false;
        }

        return true;
    }

    /**
     * Checks if there is no battleship on the grids.
     *
     * @return boolean of whether the battleship head is absent.
     */
    public boolean isBattleshipAbsent() {
        Status status = mapGrid.getCellStatus(coordinates);

        if (status == Status.EMPTY) {
            return true;
        }

        return false;
    }

    /**
     * Check if the body of the battleship does not collide into another battleship.
     *
     * @return boolean of whether battleship body collides into another battleship.
     */
    public boolean isClear(Orientation orientation) {
        int row = coordinates.getRowIndex().getZeroBased();
        int col = coordinates.getColIndex().getZeroBased();

        for (int i = 1; i < battleship.getLength(); i++) {
            if (orientation.isHorizontal()) {
                col++;
            } else {
                row++;
            }

            Coordinates cellCoords = new Coordinates(row, col);

            if (mapGrid.getCellStatus(cellCoords) == Status.SHIP) {
                return false;
            }
        }

        return true;
    }
}
