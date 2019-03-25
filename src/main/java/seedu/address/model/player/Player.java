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

    private static final int DEFAULT_NUM_AIRCRAFT_CARRIER = 1;
    private static final int DEFAULT_NUM_CRUISER = 2;
    private static final int DEFAULT_NUM_DESTROYER = 5;
    private static final String VALID_NAME_REGEX = "^[a-zA-Z0-9]{3,16}$";
    private static final String MESSAGE_CONSTRAINTS = "Name should contain only alphanumerical characters,"
            + "with no whitespaces.\n"
            + "and be of length 3 to 16 characters, inclusive.\n";

    private final String name;
    private final int fleetSize;
    private final Fleet fleet;
    private final MapGrid mapGrid;
    private Set<Coordinates> targetHistory = new HashSet<>();

    /**
     * Constructor presented to user.
     */
    public Player(String name, int numDestroyer, int numCruiser, int numAircraftCarrier) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);

        this.name = name;
        this.fleetSize = numDestroyer + numCruiser + numAircraftCarrier;
        this.fleet = new Fleet(DEFAULT_NUM_DESTROYER, DEFAULT_NUM_CRUISER, DEFAULT_NUM_AIRCRAFT_CARRIER);
        this.mapGrid = new MapGrid();
    }

    /**
     * Default constructor with name Player1 and fleet size 5.
     */
    public Player() {
        this("Player1",
                DEFAULT_NUM_DESTROYER,
                DEFAULT_NUM_CRUISER,
                DEFAULT_NUM_AIRCRAFT_CARRIER);
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
        return this.name;
    }

    public int getFleetSize() {
        return this.fleetSize;
    }

    public Fleet getFleet() {
        return this.fleet;
    }

    public ArrayList getFleetContents() {
        return this.fleet.getDeployedFleet();
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
