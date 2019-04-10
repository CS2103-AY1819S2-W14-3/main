package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;
import java.util.List;

import javafx.stage.Stage;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.AttackResult;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Player;
import seedu.address.ui.StatisticView;

/**
 * Attacks a cell on the board.
 */
public class AttackCommand extends Command {

    public static final String COMMAND_WORD = "attack";
    public static final String COMMAND_ALIAS1 = "shoot";
    public static final String COMMAND_ALIAS2 = "hit";
    public static final String COMMAND_ALIAS3 = "fire";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Attacks the specified cell.\n"
        + "Parameters: "
        + "SQUARE (a letter followed by a positive integer)\n"
        + "Example: attack b5";
    public static final String MESSAGE_DUPLICATE = "You have already attacked cell ";
    public static final String MESSAGE_ANOTHER_TURN = "Take another turn.";
    public static final String MESSAGE_TRY_AGAIN = "Please select another cell to attack.";
    public static final String MESSAGE_PLAYER_WIN = "You won. Congratulations!\n"
        + "Start another game with 'init', or enter 'exit' to quit.";
    public static final String MESSAGE_PLAYER_LOSE = "You lost... maybe you'll do better next time!\n"
        + "Start another game with 'init', or enter 'exit' to quit.";

    private Coordinates coord;

    public AttackCommand(Coordinates coord) {
        requireNonNull(coord);
        this.coord = coord;
        setPermissibleStates(EnumSet.of(BattleState.PLAYER_ATTACK));
    }

    /**
     * Executes an attack command on a cell.
     * If the player hits a ship, they can take another turn. If not, then
     * the AI takes their turn(s) immediately, then the player takes their turn.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert canExecuteIn(model.getBattleState());
        Player human = model.getHumanPlayer();
        AttackResult res;

        // check if the coordinate is already hit, prevent duplicate attacks
        if (human.addToTargetHistory(coord)) {
            res = model.getBattle().humanPerformAttack(coord);
        } else {
            throw new CommandException(MESSAGE_DUPLICATE + coord);
        }
        model.getPlayerStats().addResultToStats(res);
        model.updateUi();

        if (!res.isSuccessful()) {
            // the player didn't hit a cell successfully
            return new CommandResult(res.formatAsUserAttack() + "\n" + MESSAGE_TRY_AGAIN);
        } else if (res.isHit()) {
            if (res.isWin()) {
                // Player wins!
                model.setBattleState(BattleState.PLAYER_WIN);
                new StatisticView(new Stage(), model.getPlayerStats().generateData()).show();
                return new CommandResult(MESSAGE_PLAYER_WIN + "\n"
                    + new SaveCommand().execute(model, history).getFeedbackToUser());
            } else {
                return new CommandResult(res.formatAsUserAttack() + "\n" + MESSAGE_ANOTHER_TURN);
            }
        } else {
            // immediately, AI takes its turn
            model.setBattleState(BattleState.ENEMY_ATTACK);
            List<AttackResult> enemyResList = model.getBattle().takeComputerTurns();

            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append(res.formatAsUserAttack());
            for (AttackResult enemyRes: enemyResList) {
                resultBuilder.append("\n");
                resultBuilder.append(enemyRes.formatAsEnemyAttack());
            }
            if (enemyResList.get(enemyResList.size() - 1).isWin()) {
                // Oh no... the enemy won
                model.setBattleState(BattleState.ENEMY_WIN);
                resultBuilder.append("\n");
                resultBuilder.append(MESSAGE_PLAYER_LOSE);
            } else {
                model.setBattleState(BattleState.PLAYER_ATTACK);
            }
            return new CommandResult(resultBuilder.toString());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AttackCommand) {
            AttackCommand o = (AttackCommand) other;
            return (this == o) || (this.coord.equals(o.coord));
        } else {
            return false;
        }
    }
}
