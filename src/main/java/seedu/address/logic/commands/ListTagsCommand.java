package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class ListTagsCommand extends Command {

    public static final String COMMAND_WORD = "listTags";

    public static final String MESSAGE_SUCCESS = "List all tags: ";

    public ListTagsCommand() {
        setPermissibleStates(EnumSet.of(
            BattleState.PLAYER_PUT_SHIP,
            BattleState.ENEMY_PUT_SHIP,
            BattleState.PLAYER_ATTACK,
            BattleState.ENEMY_ATTACK));
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        assert canExecuteIn(model.getBattleState());

        StringBuilder stringBuilder = new StringBuilder();

        Set<Tag> tagSet = model.getHumanPlayer().getFleet().getAllTags();
        if (tagSet.isEmpty()) {
            stringBuilder.append("no tags found");
        } else {
            stringBuilder.append(tagSet);
        }

        return new CommandResult(MESSAGE_SUCCESS + stringBuilder.toString());
    }
}
