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
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);
        ArrayList<String> outputStatistics = new ArrayList<>();

        PlayerStatistics playerStats = model.getPlayerStats();
        String attacksMade = "Attacks Made : " + playerStats.getAttacksMade();
        String movesMade = "Moves Made : " + playerStats.getMovesMade();
        String hitCount = "Successful Hit : " + playerStats.getHitCount();
        String missCount = "Misses : " + playerStats.getMissCount();
        String shipsDestroyed = "Enemy Ships Destroyed : " + playerStats.getEnemyShipsDestroyed();
        String accuracy = "Accuracy : " + (int) (playerStats.getAccuracy() * 100) + "%";

        // Group data together in a list
        outputStatistics.add(movesMade);
        outputStatistics.add(attacksMade);
        outputStatistics.add(hitCount);
        outputStatistics.add(missCount);
        outputStatistics.add(shipsDestroyed);
        outputStatistics.add(accuracy);

        new StatisticView(new Stage(), playerStats.generateData()).show();
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", outputStatistics)));
    }

}
