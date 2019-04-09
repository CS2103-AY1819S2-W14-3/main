package seedu.address.testutil;

import java.util.Collections;

import seedu.address.commons.core.index.Index;
import seedu.address.model.battleship.AircraftCarrierBattleship;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.CruiserBattleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;
import seedu.address.model.player.Enemy;

/**
 * Stub for the Enemy class, to aid in testing BeginCommand and AttackCommand.
 */
public class InterceptedEnemy extends Enemy {
    private boolean isPrepCalled;
    private boolean isReceiveStatusCalled;
    private boolean isEnemyShootAtCalled;

    private int lastX = 0;
    private int lastY = 0;

    public InterceptedEnemy() {
        super();
        isPrepCalled = false;
        isReceiveStatusCalled = false;
    }

    /**
     * Dummy behaviour for prepEnemy() which places all ships vertically side by side.
     */
    @Override
    public void prepEnemy() {
        isPrepCalled = true;
        int row = 0;
        int column = 0;
        // put aircraft carriers
        while (getFleet().getNumAircraftCarrierLeft() > 0) {
            Battleship cv = new AircraftCarrierBattleship(Collections.emptySet());
            Coordinates toPut = new Coordinates(Index.fromZeroBased(row), Index.fromZeroBased(column));
            this.getFleet().deployOneBattleship(cv, toPut, new Orientation("v"));
            this.getMapGrid().putShip(cv, toPut, new Orientation("v"));
            column++;
        }
        // put destroyers
        while (getFleet().getNumAircraftCarrierLeft() > 0) {
            Battleship dd = new DestroyerBattleship(Collections.emptySet());
            Coordinates toPut = new Coordinates(Index.fromZeroBased(row), Index.fromZeroBased(column));
            this.getFleet().deployOneBattleship(dd, toPut, new Orientation("v"));
            this.getMapGrid().putShip(dd, toPut, new Orientation("v"));
            column++;
        }
        // put aircraft carriers
        while (getFleet().getNumAircraftCarrierLeft() > 0) {
            Battleship cl = new CruiserBattleship(Collections.emptySet());
            Coordinates toPut = new Coordinates(Index.fromZeroBased(row), Index.fromZeroBased(column));
            this.getFleet().deployOneBattleship(cl, toPut, new Orientation("v"));
            this.getMapGrid().putShip(cl, toPut, new Orientation("v"));
            column++;
        }
    }

    /**
     * enemyShootAt() with dummy behaviour
     */
    @Override
    public Coordinates enemyShootAt() {
        isEnemyShootAtCalled = true;
        Coordinates toShoot = new Coordinates(Index.fromZeroBased(lastX), Index.fromZeroBased(lastY));
        lastY++;
        if (lastY == this.getMapGrid().getMapSize()) {
            lastY = 0;
            lastX++;
        }
        return toShoot;
    }

    /**
     * Interceptor for receiveStatus().
     */
    @Override
    public void receiveStatus(Status s) {
        isReceiveStatusCalled = true;
    }

    public boolean isPrepCalled() {
        return isPrepCalled;
    }

    public boolean isReceiveStatusCalled() {
        return isReceiveStatusCalled;
    }

    public boolean isEnemyShootAtCalled() {
        return isEnemyShootAtCalled;
    }
}
