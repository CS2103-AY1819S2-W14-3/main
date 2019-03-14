package seedu.address.model.player;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import seedu.address.model.MapGrid;
import seedu.address.model.cell.Coordinates;

/**
 * Represents a player in the game.
 * Is a user-controlled Player and superclass for Enemy
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
    private Set targetHistory = new HashSet();

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


    /**
     * Attempts to add targeted coordinates to the Player targetHistory
     * Checks for duplicates.
     * Returns False if adding failed (duplicate found)
     * Returns True if adding succeeded (coordinate added to targetHistory)
     */
    public boolean addToTargetHistory(Coordinates target) {
        return this.targetHistory.add(target);
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
    public Set getTargetHistory() {
        return this.targetHistory;
    }
    public static boolean isValidName(String name) {
        return Pattern.matches(VALID_NAME_REGEX, name) && !name.equals("Enemy");
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Name: ")
                .append(getName())
                .append(getFleet())
                .append(Arrays.toString(targetHistory.toArray()));
        return builder.toString();
    }
}
