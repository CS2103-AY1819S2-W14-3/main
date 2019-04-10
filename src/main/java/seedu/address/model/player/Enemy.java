package seedu.address.model.player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BoundaryValueChecker;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Name;

import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;



/**
 * Represents an Enemy player in the game.
 * Enemy will always be a computerised player.
 */
public class Enemy extends Player {

    private static final Random randGen = new Random(1);
    private static final Random randGen2 = new Random(9);
    private static final Logger logger = LogsCenter.getLogger(Enemy.class);


    private static ArrayList<Coordinates> allPossibleTargets = new ArrayList<>(); //one-based
    private static ArrayList<Coordinates> allParityTargets = new ArrayList<>(); //one-based
    private static ArrayList<Coordinates> allPossiblePopulateCoords = new ArrayList<>(); //zero-based, two ints
    private Stack<Coordinates> watchlist = new Stack<>(); //one based
    private Status lastAttackStatus;
    private int mapSize = 0;
    private Coordinates lastCoordAttacked;

    /**
     * Default constructor with fleet size 8.
     */
    public Enemy() {
        super("EnemyPlayer", 5, 2, 1);
    }

    /**
     * Initialises and resets enemy's understanding of
     * its own and the player's map grids.
     * Used to inform the algorithms for populating enemy mapgrid and enemy shooting
     */
    public void prepEnemy() {
        mapSize = this.getMapGrid().getMapSize();
        logger.info(String.format("~~~~~~~~~~~~~~~~~~++++++++this.getMapGrid().getMapSize(): " + mapSize));

        fillWithAllCoords();
        populateEnemyMap();
    }
    /**************************************************
     * fills allPossibleTargets, allPossiblePopulateCoords, allParityTargets
     * to inform the shooting and population functions
     */
    private void fillWithAllCoords() {
        for (int row = 0; row < mapSize; row++) {
            for (int col = 0; col < mapSize; col++) {
                Coordinates createdCoord = new Coordinates(row, col);
                allPossibleTargets.add(createdCoord);
                allPossiblePopulateCoords.add(createdCoord);
                if (hasParity(row, col)) {
                    allParityTargets.add(createdCoord);
                }
            }
        }
        java.util.Collections.shuffle(allPossibleTargets, randGen);
        java.util.Collections.shuffle(allPossiblePopulateCoords, randGen2);
        java.util.Collections.shuffle(allParityTargets, randGen);
    }

    /**************************************************************
     * Draws and returns valid Coordinate to be shot at.
     * Adds Coordinates into targetHistory
     * Called by battle manager
     */
    public Coordinates enemyShootAt() {
        Coordinates newTarget;

        if (watchlist.isEmpty()) {
            if (!allParityTargets.isEmpty()) {
                newTarget = drawPartityTarget();
                logger.info(String.format("++++++++WATCHLIST EMPTY " + "enemy shoot parity: " + newTarget.toString()));
            } else {
                newTarget = drawFromAllTargets();
                logger.info(String.format("++++++++Partity EMPTY "));
            }
        } else {
            newTarget = drawFromWatchList();
            logger.info(String.format("++++++++WATCHLIST STUFFED " + "enemy shoot watched: " + newTarget.toString()));
        }
        modeCleanup(newTarget);

        this.addToTargetHistory(newTarget);
        return newTarget;

    }
    /************************************************
     * draws a valid Coord with parity filter on
     */
    private Coordinates drawPartityTarget() {
        java.util.Collections.shuffle(allParityTargets, randGen);
        return allParityTargets.get(0);
    }


    /************************************************
     * draws a valid Coord with from watchlist
     */
    private Coordinates drawFromWatchList() {
        return watchlist.pop();
    }

    /************************************************
     * draws a valid Coord with from allPossibleTargets,
     * when all Parity coordinates run out
     */
    private Coordinates drawFromAllTargets() {
        java.util.Collections.shuffle(allPossibleTargets, randGen);
        return allPossibleTargets.get(0);
    }


    /**********************************************************************
     * initialises the enemy mapGrid with its own ships randomly
     * is part of enemy constructor
     */
    private void populateEnemyMap() {

        int numDestroyer = this.getFleet().getNumDestroyerLeft();
        int numCruiser = this.getFleet().getNumCruiserLeft();

        logger.info(String.format("~~~~~~~~~~~~~~~~~~++++++++this.getFleet().getNumDestroyerLeft(): " + numDestroyer));
        logger.info(String.format("~~~~~~~~~~~~~~~~~~++++++++this.getFleet().getNumCruiserLeft(): " + numCruiser));

        placeAirCraftCarrier();
        placeMultipleDestroyerAndCruiser(numDestroyer, "Destroyer", 3);
        placeMultipleDestroyerAndCruiser(numCruiser, "Cruiser", 2);
    }

    /***********************************************************************
     * pulls a head coord from allPossiblePopulateCoords
     * and justifies the coord before using it as a head for the aircraftCarrier
     * before placing the aircraftCarrier onto enemy map
     */
    private void placeAirCraftCarrier() {
        Orientation useOrientation = generateOrientation();
        java.util.Collections.shuffle(allPossiblePopulateCoords, randGen);
        Coordinates useCoord = allPossiblePopulateCoords.get(0);
        Index row = useCoord.getRowIndex();
        Index col = useCoord.getColIndex();
        int justifiedCoord; //zero-based
        Coordinates currentBattleshipHead;

        if (useOrientation.isHorizontal()) { //rectify X coord of head, Y doesn't matter (all Y will work)
            justifiedCoord = justifyCoord(col.getZeroBased()); //col is justified
            currentBattleshipHead = new Coordinates(row.getZeroBased(), justifiedCoord);
        } else {
            justifiedCoord = justifyCoord(row.getZeroBased()); //row gets justified
            currentBattleshipHead = new Coordinates(justifiedCoord, col.getZeroBased());
        }
        Name currentBattleshipName = new Name("enemyAircraftCarrier");
        Battleship currentBattleship = new Battleship(currentBattleshipName, 5, 5);
        // public Battleship(Name name, int length, int life, Set<Tag> tags) {
        //should have no error, since it is the first ship placed, and all map sizes have a max of 1 aircraft carrier
        //AircraftCarrierBattleship x = new AircraftCarrierBattleship(currentBattleshipName);
        this.getFleet().deployOneBattleship(currentBattleship, currentBattleshipHead, useOrientation);
        this.getMapGrid().putShip(currentBattleship, currentBattleshipHead, useOrientation);
        markAsOccupied(currentBattleshipHead, 5, useOrientation);
        logger.info(String.format("++++++++POPULATED aircraft carrier at " + currentBattleshipHead.toString()
                + " orientation is " + useOrientation.toString()));
    }

    /*****************************************************************************
     * places all destroyers and cruisers onto enemy map
     * and marks those occupied cells in allPossiblePopulateCoords
     */
    private void placeMultipleDestroyerAndCruiser(int numShips, String shipType, int shipSize) {
        Orientation useOrientation = new Orientation("v");
        Coordinates useCoord = new Coordinates(0, 0);
        ArrayList<Battleship> preppedShips = generateBattleships(numShips, shipType, shipSize);
        Battleship useShip = new Battleship();
        while (!preppedShips.isEmpty()) {
            try {
                useOrientation = generateOrientation();
                java.util.Collections.shuffle(allPossiblePopulateCoords, randGen2);
                useCoord = allPossiblePopulateCoords.get(0);
                useShip = preppedShips.get(0);
                BoundaryValueChecker boundaryValueChecker = new BoundaryValueChecker(this.getMapGrid(), useShip,
                        useCoord, useOrientation);
                boundaryValueChecker.performChecks();
                this.getFleet().deployOneBattleship(useShip, useCoord, useOrientation);
                this.getMapGrid().putShip(useShip, useCoord, useOrientation);
                preppedShips.remove(0);
                markAsOccupied(useCoord, shipSize, useOrientation);
                logger.info(String.format("++++++++POPULATED " + useShip.getName() + " at "
                        + useCoord.toString()
                        + " orientation is " + useOrientation.toString()));
            } catch (ArrayIndexOutOfBoundsException aIoObEx) {
                //TODO log the error later from putship
            } catch (CommandException cmdEx) {
                logger.info(String.format("++++++++REJECTED POPULATING " + useShip.getName())
                        + " at " + useCoord + " orientation: " + useOrientation);
            }
        }

    }


    /************************************************************************
     * creates list of a certain battleship type
     * to be put on map
     */
    private ArrayList<Battleship> generateBattleships (int numShips, String battleshipType, int shipSize) {
        ArrayList<Battleship> preppedFleet = new ArrayList<>();
        for (int i = 1; i <= numShips; i++) {
            Name currentBattleshipName = new Name("enemy" + battleshipType + i);
            Battleship currentBattleship = new Battleship(currentBattleshipName, shipSize, shipSize);
            preppedFleet.add(currentBattleship);
        }
        logger.info(String.format("++++++++GENERATED: " + preppedFleet.toString()));
        return preppedFleet;
    }
    /***************************************************************
     * removes all the occupied Coords from allPossiblePopulateCoords
     * when a new ship is placed
     */
    private void markAsOccupied(Coordinates head, int shipSize, Orientation useOrientation) {

        //increase col
        if (useOrientation.isHorizontal()) { //row stays the same
            int colStart = head.getColIndex().getZeroBased();
            for (int i = 0; i < shipSize; i++) {
                Coordinates markedCoord = new Coordinates(head.getRowIndex().getZeroBased(), colStart + i);
                allPossiblePopulateCoords.remove(markedCoord);
                logger.info(String.format("++++++++MARK_AS_OCCUPIED: " + markedCoord.toString()));
            }
        } else { //increase row
            for (int i = 0; i < shipSize; i++) { //col stays the same
                int rowStart = head.getRowIndex().getZeroBased();
                Coordinates markedCoord = new Coordinates(rowStart + i, head.getColIndex().getZeroBased());
                allPossiblePopulateCoords.remove(markedCoord);
                logger.info(String.format("++++++++MARK_AS_OCCUPIED: " + markedCoord.toString()));

            }
        }
    }

    /*****************************************************************
     * justifies the head coord given to ensure
     * ship to be placed can fit the map boundaries
     */
    private int justifyCoord(int coordToJustify) {
        int lowerHalfceiling = (mapSize / 2) - 1;
        switch (mapSize) {
        case 6: //ceiling = 0 to 2, 3
            if (coordToJustify <= lowerHalfceiling) {
                coordToJustify = 0;
            } else {
                coordToJustify = 1;
            }
            break;
        case 10: //ceiling = 0 to 5, 6
            lowerHalfceiling += 1;
            if (coordToJustify > lowerHalfceiling) {
                coordToJustify -= 4;
            }
            break;
        default:
            coordToJustify %= lowerHalfceiling + 1;
        }
        return coordToJustify;
    }


    /********************************************************************************
     * randomly generates either a horizontal or vertical orientation
     */
    private Orientation generateOrientation() {
        int seed = randGen2.nextInt();
        int decision = seed % 2;
        if (decision == 0) {
            return new Orientation("horizontal");
        } else {
            return new Orientation("vertical");
        }
    }

    /*********************************************************************
     * receives status of an attacked cell from Battle manager
     */
    public void receiveStatus(Status latestStatusInfo) {
        lastAttackStatus = latestStatusInfo;
        logger.info(String.format("++++++++RECEIVE_STATUS: " + lastCoordAttacked.toString()
                + " status: " + latestStatusInfo.toString()));
        updateWatchlist(lastCoordAttacked);
    }


    /*************************************************************************
     * Add coords of the cardinal positions to the last attacked cell
     * to the watchlist
     */
    private void updateWatchlist(Coordinates lastCoordAttacked) {
        logger.info(String.format("++++++++BEFORE_UPDATE_WATCHLIST_STATUS_CHECK " + lastCoordAttacked.toString()
                + " status: " + lastAttackStatus.toString()));
        if (lastAttackStatus == Status.SHIPHIT) {
            int oldRow = lastCoordAttacked.getRowIndex().getZeroBased();
            int oldCol = lastCoordAttacked.getColIndex().getZeroBased();
            Coordinates updatedCoord;

            //ADD CARDINAL DIRECTIONS TO WATCHLIST.
            //WATCHLIST COORDS IN FORM a1

            if (oldRow - 1 >= 0) {
                //add cardinal NORTH to watchlist. ROW MINUS ONE
                updatedCoord = new Coordinates(oldRow - 1, oldCol);
                if (isValidCardinal(updatedCoord)) {
                    watchlist.push(updatedCoord);
                    modeCleanup(updatedCoord);
                }
            }
            if (oldRow + 1 < mapSize) {
                //add cardinal SOUTH to watchlist  ROW PLUS ONE
                updatedCoord = new Coordinates(oldRow + 1, oldCol);
                if (isValidCardinal(updatedCoord)) {
                    watchlist.push(updatedCoord);
                    modeCleanup(updatedCoord);
                }
            }
            if (oldCol - 1 >= 0) {
                //add cardinal WEST to watchlist   COL MINUS ONE
                updatedCoord = new Coordinates(oldRow, oldCol - 1);
                if (isValidCardinal(updatedCoord)) {
                    watchlist.push(updatedCoord);
                    modeCleanup(updatedCoord);
                }
            }
            if (oldCol + 1 < mapSize) {
                //add cardinal EAST to watchlist   COL PLUS ONE
                updatedCoord = new Coordinates(oldRow, oldCol + 1);
                if (isValidCardinal(updatedCoord)) {
                    watchlist.push(updatedCoord);
                    modeCleanup(updatedCoord);
                }
            }
            logger.info(String.format("++++++++WATCHLIST UPDATING:\n" + watchlist.toString()));
            logger.info(String.format("++++++++WATCHLIST SIZE:\n" + watchlist.size()));
        }
        if (lastAttackStatus == Status.DESTROYED) {
            cleanseWatchlist();
            logger.info(String.format("++++++++WATCHLIST CLEANSED:\n" + watchlist.toString()));
            logger.info(String.format("++++++++WATCHLIST SIZE:\n" + watchlist.size()));
        }


    }

    /******************************************************8
     * Checks that the cardinal coordinate has never been hit before
     */
    private boolean isValidCardinal(Coordinates useCoord) {
        if (!this.getTargetHistory().contains(useCoord)) {
            //logger.info(String.format("++++++++CHECKING ISVALID(): " + useCoord.toString()));
            //logger.info(String.format("++++++++allPossibleTargets: " + allPossibleTargets.toString()));
            //logger.info(String.format("++++++++allParityTargets: " + allParityTargets.toString()));
            return allPossibleTargets.contains(useCoord) || allParityTargets.contains(useCoord); //return true
        } else {
            return false;
        }
    }

    /******************************************************8
     * Remove the last used coord
     * from allParityTargets and allPossibleTargets
     */
    private void modeCleanup(Coordinates usedCoord) {
        // logger.info(String.format("++++++++BEFORE allParityTargets: " + allParityTargets.toString()));
        allParityTargets.remove(usedCoord);
        // logger.info(String.format("++++++++AFTER allParityTargets: " + allParityTargets.toString()));
        // logger.info(String.format("++++++++BEFORE allPossibleTargets: " + allPossibleTargets.toString()));
        allPossibleTargets.remove(usedCoord);
        // logger.info(String.format("++++++++AFTER allPossibleTargets: " + allPossibleTargets.toString()));

        lastCoordAttacked = usedCoord;
    }

    /*************************************************************
     * checks that coord is made up of one odd and one even x-y coordinate pair
     * and returns true if so
     */
    private boolean hasParity (int row, int col) {

        int parity = (row % 2) + (col % 2);
        return (parity == 1);
    }

    /*************************************************************
     *  puts everything back from the stack to the drawing bags
     *  Idea here is that once something is destroyed,
     *  everything on the stack should have been adj to the destroyed ship
     */
    private void cleanseWatchlist () {
        while (!watchlist.empty()) {
            Coordinates useCoord = watchlist.pop();
            if (hasParity(useCoord.getRowIndex().getZeroBased(), useCoord.getColIndex().getZeroBased())) {
                allParityTargets.add(useCoord);
            }
            allPossibleTargets.add(useCoord);
            logger.info(String.format("++++++++RELEASE ON PAROLE COORD:\n" + useCoord.toString()));

        }
    }
}
