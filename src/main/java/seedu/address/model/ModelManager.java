package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.battle.Battle;
import seedu.address.logic.battle.BattleManager;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.exceptions.PersonNotFoundException;
import seedu.address.model.player.Fleet;
import seedu.address.model.player.Player;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedMapGrid versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Cell> filteredCells;
    private final SimpleObjectProperty<Cell> selectedPerson = new SimpleObjectProperty<>();
    private PlayerStatistics playerStats;
    private BattleManager batMan;
    private BattleState state;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedMapGrid(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCells = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredCells.addListener(this::ensureSelectedPersonIsValid);

        // Initialize new statistics
        this.playerStats = new PlayerStatistics();

        Player humanPlayer = new Player();
        humanPlayer.getMapGrid().resetData(addressBook);
        batMan = new BattleManager(humanPlayer, humanPlayer);
        state = BattleState.PRE_BATTLE;
    }

    public ModelManager() {
        this(new MapGrid(), new UserPrefs());
    }

    //=========== MapGrid ================================================================================
    @Override
    public MapGrid getHumanMapGrid() {
        return getHumanPlayer().getMapGrid();
    }

    @Override
    public MapGrid getEnemyMapGrid() {
        return getEnemyPlayer().getMapGrid();
    }

    @Override
    public int getMapSize() {
        return getHumanPlayer().getMapGrid().getMapSize();
    }

    @Override
    public void updateUi() {
        getHumanMapGrid().updateUi();
        getEnemyMapGrid().updateUi();
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ObservableBooleanValue getHumanMapObservable() {
        return getHumanMapGrid().getObservableValue();
    }

    @Override
    public ObservableBooleanValue getEnemyMapObservable() {
        return getEnemyMapGrid().getObservableValue();
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasPerson(Cell cell) {
        requireNonNull(cell);
        return versionedAddressBook.hasPerson(cell);
    }

    @Override
    public void deletePerson(Cell target) {
        versionedAddressBook.removePerson(target);
    }

    @Override
    public void addPerson(Cell cell) {
        versionedAddressBook.addPerson(cell);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Cell target, Cell editedCell) {
        requireAllNonNull(target, editedCell);

        versionedAddressBook.setPerson(target, editedCell);
    }

    //=========== Battleship ===============================================================================

    @Override
    public Fleet getFleet() {
        return getHumanPlayer().getFleet();
    }

    @Override
    public void deployBattleship(Battleship battleship, Coordinates coordinates, Orientation orientation) {
        getFleet().deployOneBattleship(battleship, coordinates, orientation);
    }

    @Override
    public boolean isEnoughBattleships(Battleship battleship, int numBattleship) {
        return getFleet().isEnoughBattleship(battleship, numBattleship);
    }

    //=========== Statistics ===============================================================================
    @Override
    public PlayerStatistics getPlayerStats() {
        return this.playerStats;
    }
    //=========== Filtered Cell List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Cell} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Cell> getFilteredPersonList() {
        return filteredCells;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Cell> predicate) {
        requireNonNull(predicate);
        filteredCells.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Selected cell ===========================================================================

    @Override
    public ReadOnlyProperty<Cell> selectedPersonProperty() {
        return selectedPerson;
    }

    @Override
    public Cell getSelectedPerson() {
        return selectedPerson.getValue();
    }

    @Override
    public void setSelectedPerson(Cell cell) {
        if (cell != null && !filteredCells.contains(cell)) {
            throw new PersonNotFoundException();
        }
        selectedPerson.setValue(cell);
    }

    /**
     * Ensures {@code selectedPerson} is a valid cell in {@code filteredCells}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Cell> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected cell, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the cell that came before it in the list,
                // or clear the selection if there is no such cell.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    //========== Battle manager ==========================================================================

    /**
     * Returns the human player in the game.
     */
    @Override
    public Player getHumanPlayer() {
        return batMan.getHumanPlayer();
    }

    /**
     * Returns the computer player.
     */
    @Override
    public Player getEnemyPlayer() {
        return batMan.getEnemyPlayer();
    }

    /**
     * Retrieves the Battle API.
     */
    @Override
    public Battle getBattle() {
        return batMan;
    }

    /**
     * Retrieves the current state of the battle.
     */
    @Override
    public BattleState getBattleState() {
        return state;
    }

    /**
     * Sets the current state of the battle.
     */
    public void setBattleState(BattleState newState) {
        requireNonNull(newState);
        this.state = newState;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredCells.equals(other.filteredCells)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
