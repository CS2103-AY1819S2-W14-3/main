package seedu.address.model.battleship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a battleship in a map.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Battleship {

    // Identity fields
    private static int id;
    private final Name name;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final int length;
    private final int life;

    /**
     * Every field must be present and not null.
     */
    public Battleship(Name name, int length, int life, Set<Tag> tags) {
        this.id = ++id;
        this.name = name;
        this.length = length;
        this.life = life;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for Battleship with only name and tags.
     * Default size is length = 2, life = 1
     */
    public Battleship(Name name, Set<Tag> tags) {
        this(name, 2, 1, tags);
    }

    /**
     * Constructor for Battleship without arguments.
     * To prepare with refactoring Battleship to a Cell.
     */
    public Battleship() {
        this(new Name("placeholder"), 2, 1, new HashSet<Tag>());
    }

    public Name getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public int getLength() {
        return this.length;
    }

    public int getLife() {
        return this.life;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both battleships of the same name have the same name.
     * This defines a weaker notion of equality between two battleships.
     */
    public boolean isSameBattleship(Battleship otherBattleship) {
        if (otherBattleship == this) {
            return true;
        }

        return otherBattleship != null
                && otherBattleship.getName().equals(getName());
    }

    /**
     * Returns true if both battleships have the same identity fields.
     * This defines a stronger notion of equality between two battleships.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Battleship)) {
            return false;
        }

        Battleship otherBattleship = (Battleship) other;
        return otherBattleship.getName().equals(getName())
                && otherBattleship.getId() == getId();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, id, length, life, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Name: ")
                .append(getName())
                .append(" Id: ")
                .append(getId())
                .append(" Length: ")
                .append(getLength())
                .append(" Life: ")
                .append(getLife())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
