package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.battle.Battle;
import seedu.address.logic.battle.BattleManager;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Enemy;
import seedu.address.model.player.Fleet;
import seedu.address.model.player.Player;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final MapGrid mapGrid;
    private final UserPrefs userPrefs;
    private final SimpleObjectProperty<Cell> selectedPerson = new SimpleObjectProperty<>();
    private PlayerStatistics playerStats;
    private BattleManager batMan;
    private BattleState state;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(MapGrid addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        mapGrid = new MapGrid();
        this.userPrefs = new UserPrefs(userPrefs);

        // Initialize new statistics
        this.playerStats = new PlayerStatistics();

        batMan = new BattleManager(new Player(), new Enemy());
        state = BattleState.PRE_BATTLE;
    }

    public ModelManager() {
        this(new MapGrid(), new UserPrefs());
    }

    public ModelManager(BattleManager batMan) {
        this();
        this.batMan = batMan;
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
    public Enemy getEnemyPlayer() {
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
        return mapGrid.equals(other.mapGrid)
                && userPrefs.equals(other.userPrefs)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get());
    }

}
