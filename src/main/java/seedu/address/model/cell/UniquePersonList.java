package seedu.address.model.cell;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cell.exceptions.DuplicatePersonException;
import seedu.address.model.cell.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A cell is considered unique by comparing using {@code Cell#isSamePerson(Cell)}. As such, adding and updating of
 * persons uses Cell#isSamePerson(Cell) for equality so as to ensure that the cell being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a cell uses Cell#equals(Object) so
 * as to ensure that the cell with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Cell#isSamePerson(Cell)
 */
public class UniquePersonList implements Iterable<Cell> {

    private final ObservableList<Cell> internalList = FXCollections.observableArrayList();
    private final ObservableList<Cell> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent cell as the given argument.
     */
    public boolean contains(Cell toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a cell to the list.
     * The cell must not already exist in the list.
     */
    public void add(Cell toAdd) {
        requireNonNull(toAdd);
        /**
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
         */
        internalList.add(toAdd);
    }

    /**
     * Replaces the cell {@code target} in the list with {@code editedCell}.
     * {@code target} must exist in the list.
     * The cell identity of {@code editedCell} must not be the same as another existing cell in the list.
     */
    public void setPerson(Cell target, Cell editedCell) {
        requireAllNonNull(target, editedCell);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedCell) && contains(editedCell)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedCell);
    }

    /**
     * Removes the equivalent cell from the list.
     * The cell must exist in the list.
     */
    public void remove(Cell toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code cells}.
     * {@code cells} must not contain duplicate cells.
     */
    public void setPersons(List<Cell> cells) {
        requireAllNonNull(cells);

        /**
        if (!personsAreUnique(cells)) {
            throw new DuplicatePersonException();
        }
        */
        internalList.setAll(cells);
    }

    /**
     * Returns the map size
     */
    public int getMapSize() {
        return internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Cell> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Cell> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code cells} contains only unique cells.
     */
    private boolean personsAreUnique(List<Cell> cells) {
        for (int i = 0; i < cells.size() - 1; i++) {
            for (int j = i + 1; j < cells.size(); j++) {
                if (cells.get(i).isSamePerson(cells.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
