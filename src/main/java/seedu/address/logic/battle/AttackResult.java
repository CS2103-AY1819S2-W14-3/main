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

    protected AttackResult(Player attacker, Player target, Coordinates coords) {
        this.attacker = attacker;
        this.target = target;
        this.coords = coords;
    }

    /**
     * Returns the Player object of the attacker.
     */
    public Player getAttacker() {
        return attacker;
    }

    /**
     * Returns the Player object of the attacked.
     */
    public Player getTarget() {
        return target;
    }

    /**
     * Returns the coordinate of the cell which was attacked.
     */
    public Coordinates getCoords() {
        return coords;
    }

    /**
     * Checks if this AttackResult is a hit or a miss
     * (guess they never miss, huh?)
     */
    public abstract boolean isHit();

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
