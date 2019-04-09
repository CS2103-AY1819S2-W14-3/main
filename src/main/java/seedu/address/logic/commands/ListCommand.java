package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.battle.state.BattleState;
import seedu.address.model.Model;
import seedu.address.model.battleship.Name;
import seedu.address.model.player.Fleet;
import seedu.address.model.tag.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    private Optional<Set<Tag>> optionalTagSet;
    private Optional<Name> optionalName;

    public ListCommand(Optional<Set<Tag>> optionalTagSet, Optional<Name> optionalName) {
        this.optionalTagSet = optionalTagSet;
        this.optionalName = optionalName;
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

        Fleet fleet = model.getHumanPlayer().getFleet();

        if (fleet.getDeployedFleet().size() <= 0) {
            return new CommandResult("No ships put down.");
        }

        StringBuilder builder = new StringBuilder();
        Set<Fleet.FleetEntry> fleetResult = new HashSet<>();

        if (!optionalTagSet.isPresent() && !optionalName.isPresent()) {
            // list all battleships
            for (int i = 0; i < fleet.getDeployedFleet().size(); i++) {
                builder.append(fleet.getDeployedFleet().get(i))
                        .append("\n");
            }
            return new CommandResult(builder.toString());

        } else if (optionalName.isPresent() && optionalTagSet.isPresent()) {
            fleetResult.addAll(fleet.getDeployedFleet().stream()
                    .filter(fleetEntry -> fleetEntry.getBattleship().getName().equals(optionalName.get()))
                    .filter(fleetEntry -> fleetEntry.getBattleship().getTags().containsAll(optionalTagSet.get()))
                    .collect(Collectors.toList())
            );

        } else if (optionalName.isPresent()) {
            fleetResult.addAll(fleet.getByName(optionalName.get()));
        } else {
            fleetResult.addAll(fleet.getByTags(optionalTagSet.get()));
        }

        for (Fleet.FleetEntry fleetEntry : fleetResult) {
            builder.append(fleetEntry)
                    .append("\n");
        }

        if (fleetResult.isEmpty()) {
            return new CommandResult("There are no results.");
        }
        return new CommandResult(builder.toString());
    }

    public Optional<Name> getOptionalName() {
        return optionalName;
    }

    public Optional<Set<Tag>> getOptionalTagSet() {
        return optionalTagSet;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand) // instanceof handles nulls
                && this.getOptionalName().equals(((ListCommand) other).getOptionalName())
                && this.getOptionalTagSet().equals(((ListCommand) other).getOptionalTagSet());
    }
}
