package seedu.address.logic.battle;

import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;

/**
 * BattleManager is the implementor of the Battle interface.
 * In this game, we implement the case of one human player and N AI players (here N=1).
 */
public class BattleManager implements Battle {
    private static final Logger logger = LogsCenter.getLogger(BattleManager.class);
    /**
     * The human player
     */
    private Player humanPlayer;
    /**
     * The enemy player
     */
    private Player aiPlayer;

    private BooleanProperty isPlayerTurn;

    public BattleManager(Player humanPlayer, Player aiPlayer) {
        this.humanPlayer = humanPlayer;
        this.aiPlayer = aiPlayer;
        isPlayerTurn = new SimpleBooleanProperty(true);
    }

    @Override
    public void beginGame() {
        return;
    }

    @Override
    public AttackResult humanPerformAttack(Coordinates coord) {
        return performAttack(humanPlayer, aiPlayer, coord);
    }

    /**
     * A Player attacks another Player.
     */
    private AttackResult performAttack(Player attacker, Player target, Coordinates coord) {
        logger.info(String.format(AttackResult.ATTACK, attacker.getName(), coord, target.getName()));
        try {
            Cell cell = target.getMapGrid().getCell(coord);
            if (cell.receiveAttack()) {
                // we hit a ship
                Battleship hitShip = cell.getBattleship().get();
                if (hitShip.isDestroyed()) {
                    return new AttackDestroyedShip(attacker, target, coord, hitShip);
                } else {
                    return new AttackHit(attacker, target, coord);
                }
            } else {
                return new AttackMissed(attacker, target, coord);
            }
        } catch (IndexOutOfBoundsException ioobe) {
            return new AttackFailed(attacker, target, coord, "coordinates out of bounds");
        } catch (Exception ex) {
            return new AttackFailed(attacker, target, coord, ex.getMessage());
        }
    }

    @Override
    public AttackResult humanEndTurn() {
        isPlayerTurn.setValue(false);

        // AI takes its turn

        isPlayerTurn.setValue(true);

        return null;
    }

    /**
     * Returns the human player in the game.
     */
    public Player getHumanPlayer() {
        return humanPlayer;
    }

    /**
     * Returns the computer player.
     */
    public Player getEnemyPlayer() {
        return aiPlayer;
    }
}