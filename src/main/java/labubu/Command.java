package labubu;

/**
 * Represents the command flows supported by the parser.
 */
enum Command {
    EXIT,
    TASK_UPDATE,
    FIND,
    LIST,
    TODO,
    DEADLINE,
    EVENT,
    UNKNOWN;

    /**
     * Identifies the command flow for the given input.
     *
     * @param userInput Complete trimmed user input.
     * @param tokens User input split into whitespace-separated tokens.
     * @return Command flow represented by the input.
     */
    static Command identify(String userInput, String[] tokens) {
        if (userInput.equalsIgnoreCase("bye")
                || userInput.equalsIgnoreCase("exit")
                || userInput.equalsIgnoreCase("quit")) {
            return EXIT;
        }

        if (tokens[0].equalsIgnoreCase("mark")
                || tokens[0].equalsIgnoreCase("unmark")
                || tokens[0].equalsIgnoreCase("delete")) {
            return TASK_UPDATE;
        }

        if (tokens[0].equalsIgnoreCase("find")) {
            return FIND;
        }

        if (userInput.equalsIgnoreCase("list")) {
            return LIST;
        }

        if (tokens[0].equalsIgnoreCase("todo")) {
            return TODO;
        }

        if (tokens[0].equalsIgnoreCase("deadline")) {
            return DEADLINE;
        }

        if (tokens[0].equalsIgnoreCase("event")) {
            return EVENT;
        }

        return UNKNOWN;
    }
}
