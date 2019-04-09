package seedu.address.logic.battle;

import java.util.List;

import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Enemy;
import seedu.address.model.player.Player;

/**
 * API of the Battle component
 *
 * The Battle component manages the battle between a human player and an enemy player.
 */
public interface Battle {
    /**
     * Begins the game and gives all players a Map View.
     */
    public void beginGame();

    /**
     * Handles the human player attacking another player.
     * @return Result of the player attack.
     */
    public AttackResult humanPerformAttack(Coordinates coord);

    /**
     * Ends the player turn and causes the AI to make its attack.
     */
    public List<AttackResult> takeComputerTurns();

    /**
     * Returns the human player.
     */
    Player getHumanPlayer();

    /**
     * Returns the computer player.
     */
    Enemy getEnemyPlayer();
}
