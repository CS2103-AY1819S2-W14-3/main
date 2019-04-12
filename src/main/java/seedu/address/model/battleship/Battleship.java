package seedu.address.model.battleship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;

/**
 * Represents a battleship in a map.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Battleship {

    // Static constants
    public static final String AIRCRAFT_CARRIER_STRING = "aircraft carrier";
    public static final String DESTROYER_STRING = "destroyer";
    public static final String CRUISER_STRING = "cruiser";
    public static final Name AIRCRAFT_CARRIER_NAME = new Name(AIRCRAFT_CARRIER_STRING);
    public static final Name DESTROYER_NAME = new Name(DESTROYER_STRING);
    public static final Name CRUISER_NAME = new Name(CRUISER_STRING);
    public static final int AIRCRAFT_CARRIER_LENGTH = 5;
    public static final int DESTROYER_LENGTH = 3;
    public static final int CRUISER_LENGTH = 2;
    public static final String MESSAGE_CONSTRAINTS = "Name must be "
            + AIRCRAFT_CARRIER_STRING + ", "
            + DESTROYER_STRING + " or "
            + CRUISER_STRING + ".";

    // Default fields
    protected static final int DEFAULT_LENGTH = 2;
    protected static final int DEFAULT_LIFE = 2;

    // Identity fields
    protected static int counter = 0;
    protected final int id;
    protected final Name name;

    // Data fields
    protected final Set<Tag> tags = new HashSet<>();
    protected final int length;
    protected int life;

    // Logger
    private final Logger logger = LogsCenter.getLogger(Battleship.class);

    /**
     * Every field must be present and not null.
     */
    public Battleship(Name name, int length, int life, Set<Tag> tags) {
        logger.info("BATTLESHIP INITIALISED.");
        this.id = counter++;
        this.name = name;
        this.length = length;
        this.life = life;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for Battleship with only name.
     */
    public Battleship(Name name) {
        this(name, DEFAULT_LENGTH, DEFAULT_LIFE, new HashSet<Tag>());
    }

    /**
     * Constructor for Battleship with only name and tags.
     * Default size is length = 2, life = 1
     */
    public Battleship(Name name, Set<Tag> tags) {
        this(name, DEFAULT_LENGTH, DEFAULT_LIFE, tags);
    }


    /**
     * Constructor Battleship with only name, length and size.
     */
    public Battleship(Name name, int length, int life) {
        this(name, length, life, new HashSet<>());
    }

    /**
     * Constructor for Battleship without arguments.
     * To prepare with refactoring Battleship to a Cell.
     */
    public Battleship() {
        this(new Name("placeholder"), DEFAULT_LENGTH, DEFAULT_LIFE, new HashSet<Tag>());
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
     * Reduces the life of the battleship by one unit.
     */
    public void reduceLife() {
        this.life--;
    }

    /**
     * Checks if life of the battleship is equal to zero.
     * That is, if it is destroyed.
     */
    public boolean isDestroyed() {
        return this.life == 0;
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
                && otherBattleship.getName().equals(getName())
                && otherBattleship.getId() == getId();
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
        return otherBattleship.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, id, length, life, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
