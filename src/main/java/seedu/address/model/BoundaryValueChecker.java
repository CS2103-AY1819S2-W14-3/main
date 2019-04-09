package seedu.address.model;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class BoundaryValueChecker {
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Put ship in cell: %1$s";
    public static final String MESSAGE_BATTLESHIP_PRESENT = "There is already a ship on the coordinate.";
    public static final String MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL =
            "There is already a ship along the vertical coordinates";
    public static final String MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL =
            "There is already a ship along the horizontal coordinates";
    public static final String MESSAGE_OUT_OF_BOUNDS = "Out of bounds";

    private final MapGrid mapGrid;
    private final Battleship battleship;
    private final Coordinates coordinates;
    private final Orientation orientation;

    public BoundaryValueChecker(MapGrid mapGrid, Battleship battleship,
                                Coordinates coordinates, Orientation orientation) {
        this.mapGrid = mapGrid;
        this.battleship = battleship;
        this.coordinates = coordinates;
        this.orientation = orientation;
    }

    /**
     * Performs all the relevant checks.
     * @throws CommandException when a check fails
     */
    public void performChecks() throws CommandException {
        if (!this.isHeadWithinBounds()) {
            throw new CommandException(MESSAGE_OUT_OF_BOUNDS);
        } else if (this.orientation.isHorizontal()) {
            if (!this.isBattleshipAbsent()) {
                throw new CommandException(MESSAGE_BATTLESHIP_PRESENT);
            } else if (!this.isBodyWithinHorizontalBounds()) {
                throw new CommandException(Messages.MESSAGE_BODY_LENGTH_TOO_LONG);
            } else if (!this.isHorizontalClear()) {
                throw new CommandException(MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL);
            }
        } else if (this.orientation.isVertical()) {
            if (!this.isBattleshipAbsent()) {
                throw new CommandException(MESSAGE_BATTLESHIP_PRESENT);
            } else if (!this.isBodyWithinVerticalBounds()) {
                throw new CommandException(Messages.MESSAGE_BODY_LENGTH_TOO_LONG);
            } else if (!this.isVerticalClear()) {
                throw new CommandException(MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL);
            }
        }
    }

    /**
     * Checks if the head of a battleship is within bounds.
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
     * Checks if the body length of a battleship is within bounds.
     * Check is horizontal.
     */
    public boolean isBodyWithinHorizontalBounds() {
        Index colIndex = coordinates.getColIndex();

        int length = battleship.getLength();

        if (colIndex.getZeroBased() + length > mapGrid.getMapSize()) {
            return false;
        }

        return true;
    }

    /**
     * Checks if the body of a battleship is within bounds.
     * Check is vertical.
     */
    public boolean isBodyWithinVerticalBounds() {
        Index rowIndex = coordinates.getRowIndex();

        int length = battleship.getLength();

        if (rowIndex.getZeroBased() + length > mapGrid.getMapSize()) {
            return false;
        }

        return true;
    }

    /**
     * Checks if there is no battleship on the grids.
     */
    public boolean isBattleshipAbsent() {
        Status status = mapGrid.getCellStatus(coordinates);

        if (status == Status.EMPTY) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the vertical does not have any other battleships.
     */
    public boolean isVerticalClear() {
        int length = battleship.getLength();

        for (int i = 1; i < length; i++) {
            Coordinates cellCoords = new Coordinates(
                    coordinates.getRowIndex().getZeroBased() + i,
                    coordinates.getColIndex().getZeroBased());
            Status status = mapGrid.getCellStatus(cellCoords);

            if (status == Status.SHIP) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the horizontal does not have any other battleships.
     */
    public boolean isHorizontalClear() {
        int length = battleship.getLength();

        for (int i = 1; i < length; i++) {
            Coordinates cellCoords = new Coordinates(
                    coordinates.getRowIndex().getZeroBased(),
                    coordinates.getColIndex().getZeroBased() + i);
            Status status = mapGrid.getCellStatus(cellCoords);

            if (status == Status.SHIP) {
                return false;
            }
        }

        return true;
    }
}
