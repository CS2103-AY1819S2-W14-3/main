package seedu.address.model.cell;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.battleship.Battleship;
/**
 * Represents a Cell in the map grid.
 */
public class Cell {

    // Data fields
    private Optional<Battleship> battleship;
    private boolean isHit = false;
    private Coordinates coordinates;

    /**
     * Coordinates must be present and not null.
     */
    public Cell(Coordinates coordinates) {
        requireNonNull(coordinates);
        this.battleship = Optional.empty();
        this.coordinates = coordinates;
    }

    /**
     * Constructor for cell that requires no arguments. Used for testing
     */
    public Cell() {
        this.battleship = Optional.empty();
        this.coordinates = new Coordinates("a1");
    }

    /**
     * Constructor to copy a given Cell.
     */
    public Cell(Cell newCell) {
        this.battleship = newCell.battleship;
        this.isHit = newCell.isHit;
        this.coordinates = newCell.coordinates;
    }

    /**
     * Returns true if this cell has a battleship, otherwise returns false
     */
    public boolean hasBattleShip() {
        return battleship.isPresent();
    }

    /**
     * Put a battleship in this cell. Battleship must be present and not null.
     */
    public void putShip(Battleship battleship) {
        requireNonNull(battleship);
        this.battleship = Optional.of(battleship);
    }

    /**
     * Returns the {@code Status} of this cell
     */
    public Status getStatus() {
        if (battleship.isPresent()) {
            if (battleship.get().isDestroyed()) {
                return Status.DESTROYED;
            } else if (this.isHit) {
                return Status.SHIPHIT;
            } else {
                return Status.SHIP;
            }
        } else {
            if (isHit) {
                return Status.EMPTYHIT;
            } else {
                return Status.EMPTY;
            }
        }
    }

    /**
     * Performs an attack on this current cell.
     * @return <code>true</code> if the attack hit a ship, <code>false</code> otherwise.
     */
    public boolean receiveAttack() {
        this.isHit = true;
        if (battleship.isPresent()) {
            battleship.ifPresent(Battleship::reduceLife);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns the ship in this cell.
     */
    public Optional<Battleship> getBattleship() {
        return battleship;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Cell)) {
            return false;
        }

        Cell otherCell = (Cell) other;
        return
                otherCell.coordinates.equals(coordinates)
                && otherCell.isHit == isHit
                && otherCell.battleship.isPresent() == battleship.isPresent()
                && (!otherCell.battleship.isPresent() || otherCell.battleship.get().equals(battleship.get()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(coordinates);
        return builder.toString();
    }
}
