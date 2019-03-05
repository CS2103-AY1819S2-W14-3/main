package seedu.address.model.player;

import java.util.ArrayList;
import java.util.HashSet;

import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Name;
import seedu.address.model.tag.Tag;


/**
 * Represents a list of individual battleships
 * a Player can have
 */
public class Fleet {

    private int size;
    private ArrayList<Battleship> fleetContents;
    /**
     * Default constructor for a fleet of size 5 with placeholder ship names.
     */
    public Fleet() {
        new Fleet(5);
    }
    /**
     * Constructor for a fleet with placeholder ship names.
     */
    public Fleet(int size) throws IllegalArgumentException {

        if (size <= 0) {
            throw new IllegalArgumentException();
        }

        this.size = size;

        for (int i = 1; i <= size; i++) {
            HashSet<Tag> tags = new HashSet<>();
            Name shipName = new Name("ship" + i);
            Battleship newShip = new Battleship(shipName, tags);
            this.fleetContents.add(newShip);
        }
    }

    public int getSize() {
        return this.size;
    }

    public ArrayList getFleetContents() {
        return this.fleetContents;
    }

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
