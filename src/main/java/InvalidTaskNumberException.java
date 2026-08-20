/**
 * Signals a mark or unmark command with an invalid task number.
 */
public class InvalidTaskNumberException extends Exception {
    /**
     * Creates an exception with the message shown for invalid task numbers.
     */
    public InvalidTaskNumberException() {
        super("Invalid task index.");
    }
}
