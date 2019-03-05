package seedu.address.model.player;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.regex.Pattern;

import seedu.address.model.MapGrid;

/**
 * Represents a player in the game.
 * Can be either a user or a computerised enemy.
 */
public class Player {

    private static final String VALID_NAME_REGEX = "^[a-zA-Z0-9]{3,16}$";
    private static final String MESSAGE_CONSTRAINTS = "Name should contain only alphanumerical characters,"
            + "with no whitespaces.\n"
            + "and be of length 3 to 16 characters, inclusive.\n";

    private final String name;
    private final int fleetSize;
    private final Fleet fleet;
    private final MapGrid mapGrid;

    /**
     * Constructor presented to user.
     */
    public Player(String name, int fleetSize) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);

        this.name = name;
        this.fleetSize = fleetSize;
        this.fleet = new Fleet(fleetSize);
        this.mapGrid = new MapGrid();
    }

    /**
     * Default constructor with name Player1 and fleet size 5.
     */
    public Player() {
        this("Player1", 5);
    }

    public String getName() {
        return this.name; }
    public int getFleetSize() {
        return this.fleetSize; }
    private Fleet getFleet() {
        return this.fleet; }
    public ArrayList getFleetContents() {
        return this.fleet.getFleetContents();
    }
    public MapGrid getMapGrid() {
        return this.mapGrid;
    }

    public static boolean isValidName(String name) {
        return Pattern.matches(VALID_NAME_REGEX, name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Name: ")
                .append(getName())
                .append(getFleet());
        return builder.toString();
    }
}
