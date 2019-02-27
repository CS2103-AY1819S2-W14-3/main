package seedu.address.model;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Cell;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Cell> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the MapGrid */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a cell with the same identity as {@code cell} exists in the address book.
     */
    boolean hasPerson(Cell cell);

    /**
     * Deletes the given cell.
     * The cell must exist in the address book.
     */
    void deletePerson(Cell target);

    /**
     * Adds the given cell.
     * {@code cell} must not already exist in the address book.
     */
    void addPerson(Cell cell);

    /**
     * Replaces the given cell {@code target} with {@code editedCell}.
     * {@code target} must exist in the address book.
     * The cell identity of {@code editedCell} must not be the same as another existing cell in the address book.
     */
    void setPerson(Cell target, Cell editedCell);

    /**
     * Gets all tags from the {@code persons} in the {@code MapGrid}.
     */
    Set<Tag> getAllTags();

    /**
     * Counts number of tags in {@code MapGrid}.
     */
    int countTags();

    /**
     * Creates string from existing all tags in MapGrid.
     */
    String getAllTagsString();

    /** Returns an unmodifiable view of the filtered cell list */
    ObservableList<Cell> getFilteredPersonList();

    /**
     * Updates the filter of the filtered cell list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Cell> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Selected cell in the filtered cell list.
     * null if no cell is selected.
     */
    ReadOnlyProperty<Cell> selectedPersonProperty();

    /**
     * Returns the selected cell in the filtered cell list.
     * null if no cell is selected.
     */
    Cell getSelectedPerson();

    /**
     * Returns the map size
     */
    int getMapSize();

    /**
     * Sets the selected cell in the filtered cell list.
     */
    void setSelectedPerson(Cell cell);
}
