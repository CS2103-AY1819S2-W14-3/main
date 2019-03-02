package seedu.address.model.player;

import java.util.ArrayList;

/**
 * Represents a player in the game.
 * Can be either a user or a computerised enemy.
 */
public class Player {

    private final String name;
    private final int fleetSize;
    private final Fleet fleet;

    /**
     * Constructor presented to user.
     */
    public Player(String name, int fleetSize) {
        this.name = name;
        this.fleetSize = fleetSize;
        this.fleet = new Fleet(fleetSize);
    }

    /**
     * Default constructor for Player with provided name and fleet size 5.
     */
    public Player(String name) {
        this(name, 5);
    }

    public String getName() {
        return this.name; }
    public int getFleetSize() {
        return this.fleetSize; }
    private Fleet getFleet() {
        return this.fleet; }
    public ArrayList getFleetContents() {
        return this.fleet.getFleetContents(); }

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
