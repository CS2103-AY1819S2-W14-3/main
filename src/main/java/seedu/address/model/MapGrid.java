package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Row;
import seedu.address.model.cell.exceptions.DuplicatePersonException;
import seedu.address.model.cell.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class MapGrid implements ReadOnlyAddressBook {

    private Cell[][] cellGrid;
    private int size;
    private final Row persons;
    private BooleanProperty uiUpdateSwitch = new SimpleBooleanProperty();
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new Row();
    }

    public MapGrid() {
        this.size = 0;
        cellGrid = new Cell[0][0];
    }

    /**
     * Creates an MapGrid using the Persons in the {@code toBeCopied}
     */
    public MapGrid(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Initialises the 2D Map from the given 2D Cell array
     */
    public void initialise(Cell[][] map) {
        this.size = map.length;
        cellGrid = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            cellGrid[i] = map[i].clone();
        }
    }

    /**
     * Returns the cell in the given coordinates
     */
    public Cell getCell(Coordinates coordinates) {
        return cellGrid[coordinates.getRowIndex().getZeroBased()][coordinates.getColIndex().getZeroBased()];
    }

    public Cell getCell(int row, int column) {
        return cellGrid[row][column];
    }

    /**
     * Used to Update the UI.
     * A listener will be added to this observable value in the UI.
     * Once this value changes the UI will be updated.
     */
    public ObservableBooleanValue getObservableValue() {
        return uiUpdateSwitch;
    }

    /**
     * Change the ObservableValue to trigger the UI change
     */
    public void updateUi() {
        if (uiUpdateSwitch.getValue() == false) {
            uiUpdateSwitch.setValue(true);
        } else {
            uiUpdateSwitch.setValue(false);
        }
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

    /**
     * Returns map size
     */
    public int getMapSize() {
        return this.size;
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

    /**
     * Put battleship in the given coordinates
     */
    public void putShip(Coordinates coordinates, Battleship battleship) {
        persons.putShipAtIndex(coordinates.getRowIndex(), battleship);
    }
}
