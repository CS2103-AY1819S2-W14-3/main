package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListTagsCommand extends Command {

    public static final String COMMAND_WORD = "listTags";

    public static final String MESSAGE_SUCCESS = "List all tags: ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS + model.getAllTagsString());
    }
}
