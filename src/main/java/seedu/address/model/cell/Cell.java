package seedu.address.model.cell;

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
 * Guarantees: details are present and not null, field values are validated.
 */
public class Cell {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private Optional<Battleship> battleship;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private Status status;

    /**
     * Every field must be present and not null.
     */
    public Cell(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.battleship = Optional.empty();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for cell that requires no arguments
     * To prepare with refactoring Cell to a cell
     */
    public Cell() {
        this.battleship = Optional.empty();
        this.name = new Name("This cell is empty");
        this.phone = new Phone("123");
        this.email = new Email("placeholder@gmail.com");
        this.address = new Address("placeholder");
        this.status = Status.EMPTY;
    }
    /**
     * Constructor for cell that contains battleship
     */
    public Cell(Battleship battleship) {
        this.battleship = Optional.of(battleship);
        this.name = new Name("This has a hidden ship");
        this.phone = new Phone("123");
        this.email = new Email("placeholder@gmail.com");
        this.address = new Address("placeholder");
        this.status = Status.HIDDEN;
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
        if (battleship.isPresent()) {
            return true;
        }
        return false;
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
                && otherCell.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
