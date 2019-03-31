package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import javafx.stage.Stage;
import seedu.address.logic.CommandHistory;

import seedu.address.model.Model;
import seedu.address.model.statistics.PlayerStatistics;
import seedu.address.ui.StatisticView;


/**
 * Lists all the commands entered by user from the start of app launch.
 *
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_SUCCESS = "CURRENT STATISTICS:\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "There are no statistics to show";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);
        ArrayList<String> outputStatistics = new ArrayList<>();

        PlayerStatistics playerStats = model.getPlayerStats();
        String attacksMade = "Attacks Made : " + playerStats.getAttacksMade();
        String movesLeft = "Moves Left : " + playerStats.getMovesLeft();
        String hitCount = "Successful Hit : " + playerStats.getHitCount();
        String missCount = "Misses : " + playerStats.getMissCount();
        String shipsDestroyed = "Enemy Ships Destroyed : " + playerStats.getEnemyShipsDestroyed();
        String accuracy  = "Accuracy : " + playerStats.getAccuracy();

        // Group data together in a list
        outputStatistics.add(movesLeft);
        outputStatistics.add(attacksMade);
        outputStatistics.add(hitCount);
        outputStatistics.add(missCount);
        outputStatistics.add(shipsDestroyed);
        outputStatistics.add(accuracy);

        new StatisticView(new Stage(), playerStats.generateData()).show();

        // to change command message into log?

        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", outputStatistics)));
    }

}
