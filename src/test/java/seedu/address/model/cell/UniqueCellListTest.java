package seedu.address.model.cell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.cell.exceptions.DuplicatePersonException;
import seedu.address.model.cell.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueCellListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Row row = new Row();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        row.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(row.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        row.add(ALICE);
        assertTrue(row.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        row.add(ALICE);
        Cell editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(row.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        row.add(null);
    }

    /**
    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        row.add(ALICE);
        thrown.expect(DuplicatePersonException.class);
        row.add(ALICE);
    }
    */

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        row.setPerson(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        row.setPerson(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        row.setPerson(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        row.add(ALICE);
        row.setPerson(ALICE, ALICE);
        Row expectedRow = new Row();
        expectedRow.add(ALICE);
        assertEquals(expectedRow, row);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        row.add(ALICE);
        Cell editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        row.setPerson(ALICE, editedAlice);
        Row expectedRow = new Row();
        expectedRow.add(editedAlice);
        assertEquals(expectedRow, row);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        row.add(ALICE);
        row.setPerson(ALICE, BOB);
        Row expectedRow = new Row();
        expectedRow.add(BOB);
        assertEquals(expectedRow, row);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        row.add(ALICE);
        row.add(BOB);
        thrown.expect(DuplicatePersonException.class);
        row.setPerson(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        row.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        row.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        row.add(ALICE);
        row.remove(ALICE);
        Row expectedRow = new Row();
        assertEquals(expectedRow, row);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        row.setPersons((Row) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        row.add(ALICE);
        Row expectedRow = new Row();
        expectedRow.add(BOB);
        row.setPersons(expectedRow);
        assertEquals(expectedRow, row);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        row.setPersons((List<Cell>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        row.add(ALICE);
        List<Cell> cellList = Collections.singletonList(BOB);
        row.setPersons(cellList);
        Row expectedRow = new Row();
        expectedRow.add(BOB);
        assertEquals(expectedRow, row);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        row.asUnmodifiableObservableList().remove(0);
    }
}
