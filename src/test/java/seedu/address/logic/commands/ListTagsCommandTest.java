package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.DestroyerBattleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListTagsCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    private Set<Tag> testTags1 = new HashSet<>();
    private Set<Tag> testTags2 = new HashSet<>();

    private Tag testTag1 = new Tag("testTag1");
    private Tag testTag2 = new Tag("testTag2");
    private Tag testTag3 = new Tag("testTag3");

    private Battleship battleshipOne;
    private Battleship battleshipTwo;

    @Before
    public void setUp() {
        model = new ModelManager();
        testTags1.add(testTag1);
        testTags1.add(testTag2);
        testTags2.add(testTag3);

        battleshipOne = new DestroyerBattleship(testTags1);
        battleshipTwo = new DestroyerBattleship(testTags2);

        model.deployBattleship(battleshipOne, new Coordinates("a1"), new Orientation("v"));
        model.deployBattleship(battleshipTwo, new Coordinates("a2"), new Orientation("v"));
    }

    @Test
    public void execute_testTags_showsSame() {
        Set<Tag> modelTags = model.getHumanPlayer().getFleet().getAllTags();

        boolean allTagsContained = true;

        for (Tag testTag : testTags1) {
            if (!modelTags.contains(testTag)) {
                allTagsContained = false;
            }
        }

        for (Tag testTag : testTags2) {
            if (!modelTags.contains(testTag)) {
                allTagsContained = false;
            }
        }

        assertEquals(allTagsContained, true);
    }

    @Test
    public void execute_testTags_fail() {
        Set<Tag> tags = model.getHumanPlayer().getFleet().getAllTags();

        assertFalse(tags.contains(new Tag("nonExistentTag")));
    }

    @Test
    public void execute_invalidState_throwAssertionError() throws CommandException {
        thrown.expect(AssertionError.class);
        ListTagsCommand cmd = new ListTagsCommand();
        model.setBattleState(BattleState.PRE_BATTLE);
        cmd.execute(model, new CommandHistory());
    }
}
