package seedu.address.model.cell;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cell.exceptions.PersonNotFoundException;

/**
 * A list of rows that forms the map grid
 * Supports a minimal set of list operations.
 *
 */
public class RowList implements Iterable<Row> {

    private final ObservableList<Row> internalList = FXCollections.observableArrayList();
    private final ObservableList<Row> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a row to the row list
     */
    public void add(Row toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces a cell in the map grid.
     * Replaces a targetCell with an editedCell within a target row
     */
    public void setCell(Row target, Cell targetCell, Cell editedCell) {
        requireAllNonNull(target, editedCell);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        internalList.get(index).setPerson(targetCell, editedCell);
    }

    /**
     * Removes the equivalent row from the list
     * The row must exist in the list.
     */
    public void remove(Row toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(RowList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code cells}.
     * {@code cells} must not contain duplicate cells.
     */
    public void setPersons(List<Row> rows) {
        requireAllNonNull(rows);
        internalList.setAll(rows);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Row> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Row> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
