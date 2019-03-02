package seedu.address.model.player;

import java.util.ArrayList;
import java.util.HashSet;

import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Name;


/**
 * Represents a list of individual battleships
 * a Player can have
 */
public class Fleet {

    private final int size;
    private ArrayList<Battleship> fleetContents;

    /**
     * Default constructor for a fleet with placeholder ship names.
     */
    public Fleet(int size) {
        this.size = size;

        for (int i = 1; i <= size; i++) {
            HashSet tags = new HashSet();
            Name shipName = new Name("ship" + i);
            Battleship newShip = new Battleship(shipName, tags);
            this.fleetContents.add(newShip);
        }
    }

    public int getSize() {
        return this.size; }
    public ArrayList getFleetContents() {
        return this.fleetContents; }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getSize())
                .append(" Fleet size: ")
                .append(getSize())
                .append(" Fleet contents: ")
                .append(getFleetContents());
        return builder.toString();
    }

}
