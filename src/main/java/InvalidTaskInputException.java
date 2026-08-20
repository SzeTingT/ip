/**
 * Signals a task command with missing or malformed task details.
 */
public class InvalidTaskInputException extends Exception {
    /**
     * Creates an exception with the message shown for invalid task input.
     */
    public InvalidTaskInputException() {
        super("Invalid task details.");
    }
}
