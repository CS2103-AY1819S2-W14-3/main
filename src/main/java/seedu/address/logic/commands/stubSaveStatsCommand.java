package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;

import seedu.address.model.Model;
import seedu.address.model.statistics.PlayerStatistics;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;


/**
 * Lists all the commands entered by user from the start of app launch.
 *
 */
public class stubSaveStatsCommand extends Command {

    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_SUCCESS = "Statistics Data Saved";
    private static PlayerStatistics playerStats;
    private Optional<PlayerStatistics> statisticsDataOptional;
    private PlayerStatistics oldPlayerStats;
    private Storage storage;

    public double getAccuracy(int hitCount, int missCount) {
        if (hitCount == 0 && missCount == 0) {
            return 0;
        }
        return (double) hitCount / (double) (hitCount + missCount);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);


        // get the CURRENT obj
        this.playerStats = model.getPlayerStats();

        // GET PAST STATS
        this.storage = this.playerStats.getStorage();
        try {
            System.out.println("Get previous data Optional");
            statisticsDataOptional = (this.storage).readStatisticsData();
            System.out.println("Successfuly get optional obj");
            if (!statisticsDataOptional.isPresent()) {
                System.out.println("No past statistics data found.");
                //logger.info("No past statistics data found.");
            }
        } catch (DataConversionException e) {
            System.out.println("Data file not in the correct format. Past statistics data will not be used");
            //logger.warning("Data file not in the correct format. Past statistics data will not be used");
        } catch (IOException e) {
            System.out.println("Problem while reading from the file. Past statistics data will not be used");
            //logger.warning("Problem while reading from the file. Past statistics data will not be used");
        }

        // READING OF VALUES FROM THE FILES

        int pastHit = (statisticsDataOptional.get()).getHitCount();
        int pastMiss = (statisticsDataOptional.get()).getMissCount();
        double pastAccuracy = getAccuracy(pastHit, pastMiss);

        System.out.println("Previous game accuracy: " + pastAccuracy);
        System.out.println("Current game accuracy " + this.playerStats.getAccuracy());

        // throw obj into storage
        try {
            this.playerStats.saveToStorage(this.playerStats);
            System.out.println("saveToStorage");
        }
        catch (IOException ioe) {
            System.out.println("IO Exception");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

}