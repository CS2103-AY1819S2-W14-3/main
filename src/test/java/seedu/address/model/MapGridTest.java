package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class MapGridTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final MapGrid mapGrid = new MapGrid();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), mapGrid.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        mapGrid.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        MapGrid newData = getTypicalAddressBook();
        mapGrid.resetData(newData);
        assertEquals(newData, mapGrid);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        mapGrid.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(mapGrid.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        mapGrid.addPerson(ALICE);
        assertTrue(mapGrid.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        mapGrid.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(mapGrid.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        mapGrid.getPersonList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        mapGrid.addListener(listener);
        mapGrid.addPerson(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        mapGrid.addListener(listener);
        mapGrid.removeListener(listener);
        mapGrid.addPerson(ALICE);
        assertEquals(0, counter.get());
    }

    @Test
    public void removeTagFromPerson_nonExistentTag_samePerson() throws Exception {
        mapGrid.addPerson(ALICE);
        mapGrid.removeTag(new Tag(VALID_TAG_UNUSED), ALICE);

        MapGrid expectedMapGrid = new AddressBookBuilder().withPerson(ALICE).build();

        assertEquals(expectedMapGrid, mapGrid);

    }

    @Test
    public void deleteTag_nonExistentTag_forAll() throws Exception {
        Tag unusedTag = new Tag(VALID_TAG_UNUSED);
        for (Person person : mapGrid.getPersonList()) {
            Set<Tag> newTags = person.getTags();
            newTags.add(unusedTag);
            Person editedPerson = new Person(
                    person.getName(),
                    person.getPhone(),
                    person.getEmail(),
                    person.getAddress(),
                    newTags
            );
            mapGrid.setPerson(person, editedPerson);
        }

        mapGrid.deleteTag(unusedTag);
        assertEquals(mapGrid.getAllTags().contains(unusedTag), false);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
