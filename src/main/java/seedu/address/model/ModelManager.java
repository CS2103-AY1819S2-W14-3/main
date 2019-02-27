package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Cell;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedMapGrid versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Cell> filteredCells;
    private final SimpleObjectProperty<Cell> selectedPerson = new SimpleObjectProperty<>();

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
    }

    public ModelManager() {
        this(new MapGrid(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

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

    //=========== MapGrid ================================================================================

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

    @Override
    public Set<Tag> getAllTags() {
        return versionedAddressBook.getAllTags();
    }

    @Override
    public String getAllTagsString() {
        Set<Tag> tags = this.getAllTags();
        List<String> tagsString = new ArrayList<>();

        for (Tag tag : tags) {
            tagsString.add(tag.getTagName());
        }

        return String.join(", ", tagsString);
    }

    @Override
    public int countTags() {
        Set<Tag> tags = this.getAllTags();
        return tags.size();
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
