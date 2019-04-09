package seedu.address.logic;

import java.util.logging.Logger;

import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.MapGrid;
import seedu.address.model.Model;
import seedu.address.model.statistics.PlayerStatistics;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final PlayerStatistics statistics;

    public LogicManager(Model model, Storage storage) {

        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        this.statistics = model.getPlayerStats();

        // Set addressBookModified to true whenever the models' address book is modified.
        this.statistics.setStorage(storage);
        model.getAddressBook().addListener(observable -> addressBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        boolean validCommand = false;
        CommandResult commandResult;
        try {
            Command command = addressBookParser.parseCommand(commandText);
            if (command.canExecuteIn(model.getBattleState())) {
                commandResult = command.execute(model, history);
                addToStatistics(commandText);
                validCommand = true;
            } else {
                commandResult = new CommandResult("Cannot perform command while "
                    + model.getBattleState().getDescription().toLowerCase());
            }
        } finally {
            if (validCommand) {
                history.add(commandText);
            }
        }
        // STATS STORAGE upon modified, will save into StatisticData via saveAddressBook
        if (addressBookModified) {
            //logger.info("Detected Game End. Saving statistics...")
            logger.info("Address book modified, saving to file.");
            try {
                storage.saveAddressBook(model.getAddressBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    /**
     * keeps track of specific commands for statistics (eg. attack).
     */
    public void addToStatistics (String commandText) {
        String commandKeyword = commandText.split(" ")[0]; // Take first word
        if (commandKeyword.matches("attack|shoot|fire|hit  ")) {
            int numMovesLeft = statistics.addMove();
            statistics.addAttack();
        }
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableBooleanValue getHumanMapObservable() {
        return model.getHumanMapObservable();
    }

    @Override
    public ObservableBooleanValue getEnemyMapObservable() {
        return model.getEnemyMapObservable();
    }

    @Override
    public MapGrid getHumanMapGrid() {
        return model.getHumanMapGrid();
    }

    @Override
    public MapGrid getEnemyMapGrid() {
        return model.getEnemyMapGrid();
    }
}
