package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.person.Cell;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class MapGrid implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public MapGrid() {}

    /**
     * Creates an MapGrid using the Persons in the {@code toBeCopied}
     */
    public MapGrid(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the cell list with {@code cells}.
     * {@code cells} must not contain duplicate cells.
     */
    public void setPersons(List<Cell> cells) {
        this.persons.setPersons(cells);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code MapGrid} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// cell-level operations

    /**
     * Returns true if a cell with the same identity as {@code cell} exists in the address book.
     */
    public boolean hasPerson(Cell cell) {
        requireNonNull(cell);
        return persons.contains(cell);
    }

    /**
     * Adds a cell to the address book.
     * The cell must not already exist in the address book.
     */
    public void addPerson(Cell p) {
        persons.add(p);
        indicateModified();
    }

    /**
     * Replaces the given cell {@code target} in the list with {@code editedCell}.
     * {@code target} must exist in the address book.
     * The cell identity of {@code editedCell} must not be the same as another existing cell in the address book.
     */
    public void setPerson(Cell target, Cell editedCell) {
        requireNonNull(editedCell);

        persons.setPerson(target, editedCell);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code MapGrid}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Cell key) {
        persons.remove(key);
        indicateModified();
    }

    /**
     * Removes {@code tag} from {@code cell} in this {@code MapGrid}.
     */
    public void removeTag(Tag tag, Cell cell) throws DuplicatePersonException,
            PersonNotFoundException {
        Set<Tag> newTags = new HashSet<>(cell.getTags());
        newTags.remove(tag);

        Cell editedCell = new Cell(
                cell.getName(),
                cell.getPhone(),
                cell.getEmail(),
                cell.getAddress(),
                newTags);
        persons.setPerson(cell, editedCell);
    }

    /**
     * Remove {@code tag} from app {@code cell}s in this {@code MapGrid}.
     */
    public void deleteTag(Tag tag) {
        for (Cell cell : this.getPersonList()) {
            this.removeTag(tag, cell);
        }
    }

    /**
     * Show all tags that have been used in the address book.
     * Returns a hash set of the tags.
     */
    public Set<Tag> getAllTags() {
        Set<Tag> tags = new HashSet<>();
        for (Cell cell : persons) {
            for (Tag t : cell.getTags()) {
                tags.add(t);
            }
        }

        return tags;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Cell> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapGrid // instanceof handles nulls
                && persons.equals(((MapGrid) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
