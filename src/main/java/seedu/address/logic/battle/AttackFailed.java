package seedu.address.logic.battle;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * Represents the result of an invalid attack (e.g. to a non-existent player, or an invalid coordinate)
 */
public class AttackFailed extends AttackResult {
    private String reason;
    private String enemyName;

    public AttackFailed(Player attacker, String target, Coordinates cell, String reason) {
        super(attacker, null, cell);
        this.reason = reason;
        this.enemyName = target;
    }

    public AttackFailed(Player attacker, Player target, Coordinates cell, String reason) {
        super(attacker, null, cell);
        this.reason = reason;
        this.target = target;
        this.enemyName = target.getName();
    }

    /**
     * Checks if this AttackResult is a hit or a miss
     * (guess they never miss, huh?)
     */
    public boolean isHit() {
        return false;
    }

    /**
     * Returns a String representation of the attack result.
     */
    @Override
    public String resultString() {
        return String.format("failed: %s", reason);
    }
}
