package seedu.address.model.cell;

/**
 * Enum that has different statuses a cell can have
 */
public enum Status {
    /**
     * This cell has not been hit. Used when showing the map from the enemy's perspective.
     */
    HIDDEN,
    /**
     * This cell is empty and has not been hit.
     */
    EMPTY,
    /**
     * This cell has been hit before, and is empty;
     */
    EMPTYHIT,
    /**
     * This cell has a ship in it.
     */
    SHIP,
    /**
     * This cell has been hit before, and there is a damaged ship here.
     */
    SHIPHIT,
    /**
     * This cell has been hit before, and the ship here has been destroyed.
     */
    DESTROYED
}
