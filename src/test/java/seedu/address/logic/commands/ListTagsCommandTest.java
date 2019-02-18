package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Email;
import seedu.address.model.person.Address;

import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListTagsCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    private Set<Tag> testTags = new HashSet<>();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        testTags.add(new Tag("testTag1"));
        testTags.add(new Tag("testTag2"));

        Person testPerson = new Person(
                new Name("Test Person"),
                new Phone("000"),
                new Email("test@test.com"),
                new Address("Test Avenue"),
                testTags);

        model.addPerson(testPerson);
    }

    @Test
    public void execute_testTags_showsSame() {
        Set<Tag> modelTags = model.getAllTags();

        boolean allTagsContained = true;

        for (Tag testTag : testTags) {
            if (!modelTags.contains(testTag)) {
                allTagsContained = false;
            }

        }

        assertEquals(allTagsContained, true);
    }

}
