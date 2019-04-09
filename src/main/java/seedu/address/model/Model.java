package seedu.address.model;

import java.nio.file.Path;

import javafx.beans.value.ObservableBooleanValue;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.battle.Battle;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.player.Enemy;
import seedu.address.model.player.Fleet;
import seedu.address.model.player.Player;
import seedu.address.model.statistics.PlayerStatistics;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Update the UI
     */
    void updateUi();

    /**
     * Returns the observable value in the human map. Used by the Ui to listen to for changes.
     */
    ObservableBooleanValue getHumanMapObservable();

    /**
     * Returns the observable value in the human map. Used by the Ui to listen to for changes.
     */
    ObservableBooleanValue getEnemyMapObservable();

    /**
     * Returns the player map grid
     */
    MapGrid getHumanMapGrid();

    /**
     * Returns the enemy map grid
     */
    MapGrid getEnemyMapGrid();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the map size
     */
    int getMapSize();

    PlayerStatistics getPlayerStats();

    Fleet getFleet();

    /**
     * Deploys a battleship.
     */
    void deployBattleship(Battleship battleship, Coordinates coordinates, Orientation orientation);

    /**
     * Checks number of battleships available.
     */
    boolean isEnoughBattleships(Battleship battleship, int numBattleship);

    /**
     * Returns the human player in the game.
     */
    Player getHumanPlayer();

    /**
     * Returns the computer player.
     */
    Enemy getEnemyPlayer();

    /**
     * Retrieves the Battle API.
     */
    Battle getBattle();

    /**
     * Retrieves the current state of the battle.
     */
    void setBattleState(BattleState battleState);

    /**
     * Retrieves the current state of the battle.
     */
    BattleState getBattleState();
}
