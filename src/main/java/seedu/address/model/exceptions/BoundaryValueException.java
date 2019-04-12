package seedu.address.model.exceptions;

/**
 * Represents an insufficient number of battleships exception.
 */
public class BoundaryValueException extends Exception {
    public BoundaryValueException(String message) {
        super(message);
    }
}
