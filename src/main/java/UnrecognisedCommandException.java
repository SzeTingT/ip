/**
 * Signals a command that Labubu does not support.
 */
public class UnrecognisedCommandException extends Exception {
    /**
     * Creates an exception with the message shown for unknown commands.
     */
    public UnrecognisedCommandException() {
        super("Unrecognised command.");
    }
}
