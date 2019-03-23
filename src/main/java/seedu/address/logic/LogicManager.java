package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
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
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.cell.Cell;
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
    private boolean addressBookModified;

    public LogicManager(Model model, Storage storage) {

        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser();
        this.statistics = new PlayerStatistics();


        // Set addressBookModified to true whenever the models' address book is modified.
        model.getAddressBook().addListener(observable -> addressBookModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        addressBookModified = false;
        boolean validCommand = false;
        CommandResult commandResult;
        try {
            Command command = addressBookParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
            addToStatistics(commandText);
            validCommand = true;
        } finally {
            if (validCommand) {
                history.add(commandText);
            }
        }

        if (addressBookModified) {
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
        // check if it is AttackCommand
        //System.out.println("Commandtext is : " + commandText);
        String commandKeyword = commandText.split(" ")[0]; // Take first word
        //System.out.println("Command keyword is : " + commandKeyword);
        if (commandKeyword.equals("attack")) {
            int numMovesLeft = statistics.minusMove();
            statistics.addAttack();
            // System.out.println("Moves Left: " + numMovesLeft);
            // System.out.println("Stats Moves Left: " + statistics.getMovesLeft());
        }
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Cell> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
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
    public ReadOnlyProperty<Cell> selectedPersonProperty() {
        return model.selectedPersonProperty();
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

    @Override
    public void setSelectedPerson(Cell cell) {
        model.setSelectedPerson(cell);
    }
}
