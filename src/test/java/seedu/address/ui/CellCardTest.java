package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Cell;
import seedu.address.testutil.PersonBuilder;

public class CellCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Cell cellWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(cellWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, cellWithNoTags, 1);

        // with tags
        Cell cellWithTags = new PersonBuilder().build();
        personCard = new PersonCard(cellWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, cellWithTags, 2);
    }

    @Test
    public void equals() {
        Cell cell = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(cell, 0);

        // same cell, same index -> returns true
        PersonCard copy = new PersonCard(cell, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different cell, same index -> returns false
        Cell differentCell = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentCell, 0)));

        // same cell, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(cell, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedCell} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Cell expectedCell, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify cell details are displayed correctly
        assertCardDisplaysPerson(expectedCell, personCardHandle);
    }
}
