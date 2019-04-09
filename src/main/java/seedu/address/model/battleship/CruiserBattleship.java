package seedu.address.model.battleship;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a battleship in a map.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CruiserBattleship extends Battleship {

    /**
     * Constructor for Battleship with only name and tags.
     * Default size is length = 2, life = 1
     */
    public CruiserBattleship(Set<Tag> tagSet) {
        super(Battleship.CRUISER_NAME, Battleship.CRUISER_LENGTH, Battleship.CRUISER_LENGTH, tagSet);
    }

}
