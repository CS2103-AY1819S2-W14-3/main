package seedu.address.model.player;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.battleship.AircraftCarrierBattleship;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.CruiserBattleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.tag.Tag;


/**
 * Represents a list of individual battleships
 */
public class Fleet {


    private ArrayList<FleetEntry> deployedFleet;

    private int numDestroyer;
    private int numCruiser;
    private int numAircraftCarrier;

    /**
     * Default constructor for a fleet of size 8 with placeholder ship names.
     */
    public Fleet() {
        this(5, 2, 1);
    }

    /**
     * Constructor using formula according to mapSize.
     */
    public Fleet (int mapSize) {
        this(1, mapSize - 5, (mapSize + 2) / 5);
    }

    /**
     * Constructor for a fleet with placeholder ship names.
     */
    public Fleet(int numDestroyer, int numCruiser, int numAircraftCarrier)
            throws IllegalArgumentException {

        if (numDestroyer + numCruiser + numAircraftCarrier <= 0) {
            throw new IllegalArgumentException();
        }

        this.numDestroyer = numDestroyer;
        this.numCruiser = numCruiser;
        this.numAircraftCarrier = numAircraftCarrier;

        this.deployedFleet = new ArrayList<>();
    }

    public int getSize() {
        return this.numDestroyer + this.numCruiser + this.numAircraftCarrier;
    }

    public ArrayList<FleetEntry> getDeployedFleet() {
        return this.deployedFleet;
    }

    /**
     * Resets the fleet.
     */
    public void resetFleet(int mapSize) {
        this.numDestroyer = (mapSize + 2) / 5;
        this.numCruiser = mapSize - 5;
        this.numAircraftCarrier = 1;
        this.deployedFleet = new ArrayList<>();
    }

    /**
     * Deploys one battleship. Checks class of battleship.
     */
    public void deployOneBattleship(Battleship battleship, Coordinates coordinates, Orientation orientation) {
        if (battleship instanceof DestroyerBattleship) {
            this.numDestroyer--;
            this.deployedFleet.add(new FleetEntry(
                    battleship,
                    coordinates,
                    orientation
            ));
        } else if (battleship instanceof CruiserBattleship) {
            this.numCruiser--;
            this.deployedFleet.add(new FleetEntry(
                    battleship,
                    coordinates,
                    orientation
            ));
        } else if (battleship instanceof AircraftCarrierBattleship) {
            this.numAircraftCarrier--;
            this.deployedFleet.add(new FleetEntry(
                    battleship,
                    coordinates,
                    orientation
            ));
        }
    }

    /**
     * Checks if there are enough battleships. Returns true or false.
     * @return whether there are enough battleships.
     */
    public boolean isEnoughBattleship(Battleship battleship, int numBattleship) {
        if (battleship instanceof DestroyerBattleship) {
            return numBattleship <= this.getNumDestroyer();
        } else if (battleship instanceof CruiserBattleship) {
            return numBattleship <= this.getNumCruiser();
        } else if (battleship instanceof AircraftCarrierBattleship) {
            return numBattleship <= this.getNumAircraftCarrier();
        }

        return false;
    }

    public List<FleetEntry> getListOfDestroyerBattleship() {
        return this.deployedFleet
                .stream()
                .filter(entry -> entry.getBattleship() instanceof DestroyerBattleship)
                .collect(Collectors.toList());
    }

    public List<FleetEntry> getListOfCruiserBattleship() {
        return this.deployedFleet
                .stream()
                .filter(entry -> entry.getBattleship() instanceof CruiserBattleship)
                .collect(Collectors.toList());
    }

    public List<FleetEntry> getListOfAircraftCarrierBattleship() {
        return this.deployedFleet
                .stream()
                .filter(entry -> entry.getBattleship() instanceof AircraftCarrierBattleship)
                .collect(Collectors.toList());
    }

    public int getNumDestroyer() {
        return this.numDestroyer;
    }

    public int getNumCruiser() {
        return this.numCruiser;
    }

    public int getNumAircraftCarrier() {
        return this.numAircraftCarrier;
    }

    public Set<Tag> getAllTags() {
        Set<Tag> tagSet = new HashSet<>();

        for (FleetEntry fleetEntry : this.getDeployedFleet()) {
            for (Tag tag : fleetEntry.getBattleship().getTags()) {
                tagSet.add(tag);
            }
        }

        return tagSet;
    }

    public ArrayList<FleetEntry> getByTags(Set<Tag> tagSet) {
        ArrayList<FleetEntry> battleshipTagSet = new ArrayList<>();
        for (FleetEntry fleetEntry : this.getDeployedFleet()) {
            if (fleetEntry.getBattleship().getTags().containsAll(tagSet)) {
                battleshipTagSet.add(fleetEntry);
            }
        }

        return battleshipTagSet;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getSize())
                .append(" Fleet size: ")
                .append(getSize())
                .append(" Fleet contents: ")
                .append(getDeployedFleet());
        return builder.toString();
    }

    /**
     * Represents an entry in the fleet. To describe the orientation and coordinates
     * of a given battleship.
     */
    public class FleetEntry {
        private final Battleship battleship;
        private final Orientation orientation;
        private final Coordinates coordinates;

        public FleetEntry(Battleship battleship, Coordinates coordinates, Orientation orientation) {
            this.battleship = battleship;
            this.coordinates = coordinates;
            this.orientation = orientation;
        }

        public Battleship getBattleship() {
            return battleship;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public Orientation getOrientation() {
            return orientation;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.getBattleship())
                    .append(" (")
                    .append(this.getBattleship().getLife())
                    .append("/")
                    .append(this.getBattleship().getLength())
                    .append(")")
                    .append(" at ")
                    .append(this.getCoordinates())
                    .append(" ")
                    .append(this.getOrientation())
                    .append(" ");
            this.getBattleship().getTags().forEach(builder::append);
            return builder.toString();
        }
    }
}
