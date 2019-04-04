package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.model.cell.Cell;

/**
 * A utility class for Cell.
 */
public class PersonUtil {

    /**
     * Returns the part of command string for the given {@code cell}'s details.
     */
    public static String getPersonDetails(Cell cell) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + cell.getName().fullName + " ");
        sb.append(PREFIX_PHONE + cell.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + cell.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + cell.getAddress().value + " ");
        cell.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

}
