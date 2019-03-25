package seedu.address.model.cell;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents a Cell in the map grid.
 */
public class Cell {

    // Identity fields
    private Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private Optional<Battleship> battleship;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private boolean isHit = false;
    private Coordinates coordinates;

    /**
     * Every field must be present and not null.
     */
    public Cell(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.coordinates = new Coordinates("a1");
        this.battleship = Optional.empty();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Coordinates must be present and not null.
     */
    public Cell(Coordinates coordinates) {
        requireNonNull(coordinates);
        this.battleship = Optional.empty();
        this.coordinates = coordinates;
        this.name = new Name("This cell is empty");
        this.phone = new Phone("123");
        this.email = new Email("placeholder@gmail.com");
        this.address = new Address("placeholder");
    }
    /**
     * Constructor for cell that requires no arguments
     * To prepare with refactoring Cell to a cell
     */
    public Cell() {
        this.battleship = Optional.empty();
        this.coordinates = new Coordinates("a1");
        this.name = new Name("This cell is empty");
        this.phone = new Phone("123");
        this.email = new Email("placeholder@gmail.com");
        this.address = new Address("placeholder");
    }

    /**
     * Constructor to copy a given Cell.
     */
    public Cell(Cell newCell) {
        this.battleship = newCell.battleship;
        this.isHit = newCell.isHit;
        this.coordinates = newCell.coordinates;
        this.name = newCell.name;
        this.phone = new Phone("123");
        this.email = new Email("placeholder@gmail.com");
        this.address = new Address("placeholder");
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
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
        this.name = battleship.getName();
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
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Cell otherCell) {
        if (otherCell == this) {
            return true;
        }

        return otherCell != null
                && otherCell.getName().equals(getName())
                && (otherCell.getPhone().equals(getPhone()) || otherCell.getEmail().equals(getEmail()));
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
        return otherCell.getName().equals(getName())
                && otherCell.getPhone().equals(getPhone())
                && otherCell.getEmail().equals(getEmail())
                && otherCell.getAddress().equals(getAddress())
                && otherCell.getTags().equals(getTags())
                && otherCell.coordinates.equals(coordinates)
                && otherCell.battleship.isPresent() == battleship.isPresent()
                && (!otherCell.battleship.isPresent() || otherCell.battleship.get().equals(battleship.get()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(coordinates);
        return builder.toString();
    }
}
