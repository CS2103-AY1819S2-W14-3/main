package seedu.address.testutil;

import seedu.address.model.MapGrid;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code MapGrid ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private MapGrid mapGrid;

    public AddressBookBuilder() {
        mapGrid = new MapGrid();
    }

    public AddressBookBuilder(MapGrid mapGrid) {
        this.mapGrid = mapGrid;
    }

    /**
     * Adds a new {@code Person} to the {@code MapGrid} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        mapGrid.addPerson(person);
        return this;
    }

    public MapGrid build() {
        return mapGrid;
    }
}
