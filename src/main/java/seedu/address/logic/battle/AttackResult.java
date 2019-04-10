package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;
import seedu.address.model.player.Player;

/**
 * An AttackResult represents the end result of an attack.
 */
public abstract class AttackResult {
    public static final String ATTACK = "Attack by player %s on cell %s of player %s ";

    protected Player attacker;
    protected Player target;
    protected Coordinates coords;
    // refactoring the way of handling attributes to enable a declarative style
    protected boolean succeeds;
    protected boolean hitsShip;
    protected boolean destroysShip;
    protected boolean winsGame;

    protected AttackResult(Player attacker, Player target, Coordinates coords) {
        this.attacker = attacker;
        this.target = target;
        this.coords = coords;
    }

    /**
     * Checks if this AttackResult actually succeeds in hitting a cell
     */
    public final boolean isSuccessful() {
        return succeeds;
    }

    /**
     * Checks if this AttackResult is a hit or a miss
     * (guess they never miss, huh?)
     */
    public final boolean isHit() {
        return hitsShip;
    }

    /**
     * Checks if this AttackResult is of the destruction of a ship
     */
    public final boolean isDestroy() {
        return destroysShip;
    }

    /**
     * Checks if this AttackResult is of a winning attack
     */
    public final boolean isWin() {
        return winsGame;
    }

    public Status getStatus() {
        return attacker.getMapGrid().getCellStatus(coords);
    }

    /**
     * Returns a short form of the attack result, without the front portion
     * "Player __ attacked __ __"
     */
    public abstract String resultString();

    /**
     * Returns a String representation of the attack result.
     */
    @Override
    public String toString() {
        return String.format(ATTACK, attacker.getName(), coords, target.getName()) + resultString();
    }

    /**
     * Formats the string as if the user attacked.
     */
    public String formatAsUserAttack() {
        return String.format("You attacked %s and ", coords) + resultString();
    }

    /**
     * Formats the string as if the enemy attacked.
     */
    public String formatAsEnemyAttack() {
        return String.format("Enemy attacked %s and ", coords) + resultString();
    }
}
