package labubu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Scanner;

/**
 * Parses user commands and applies them to Labubu's task list.
 */
public class Parser {
    private static final DateTimeFormatter formatter =
            new DateTimeFormatterBuilder()
                    .appendPattern("dd/MM/yyyy")
                    .optionalStart()
                    .appendPattern(" HH:mm")
                    .optionalEnd()
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 23)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
                    .toFormatter(); // Custom date time formatter

    private final Scanner scanner;
    private final Storage storage;
    private final TaskList tasks;

    /**
     * Creates a parser using the given input source and task data.
     *
     * @param scanner Source of user commands.
     * @param storage Storage used to persist tasks.
     * @param tasks Task list to modify.
     */
    public Parser(Scanner scanner, Storage storage, TaskList tasks) {
        this.scanner = scanner;
        this.storage = storage;
        this.tasks = tasks;
    }

    /**
     * Reads and processes one command from the user.
     *
     * @param terminateFlag Mutable flag set to true when the user exits.
     */
    public void parse(boolean[] terminateFlag) {
        System.out.print("> ");
        String userInput = scanner.nextLine().trim();

        try {
            if (userInput.isEmpty()) {
                throw new InvalidTaskInputException();
            }

            String[] tokens = userInput.split("\\s+");

            if (userInput.equalsIgnoreCase("bye") || userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("quit")) {
                storage.saveTasks(tasks);
                terminateFlag[0] = true;
                return;
            } else if (tokens[0].equalsIgnoreCase("mark")
                    || tokens[0].equalsIgnoreCase("unmark")
                    || tokens[0].equalsIgnoreCase("delete")) {
                if (tokens.length < 2) {
                    throw new InvalidTaskNumberException();
                }
                try {
                    int index = Integer.parseInt(tokens[1]) - 1;
                    if (index < 0 || index >= tasks.getTaskListSize()) {
                        throw new InvalidTaskNumberException();
                    }
                    if (tokens[0].equalsIgnoreCase("delete")) {
                        Task task = tasks.removeTask(index);
                        System.out.println("Noted. I've removed this task:");
                        System.out.printf("  [%s][%s] %s%n", task.getMarker(),
                                task.getStatusIndicator(), task.getTaskDescription());
                        System.out.println("Now you have " + tasks.getTaskListSize() + " tasks in the list.");
                    } else {
                        tasks.getTask(index).setStatus(tokens[0].equalsIgnoreCase("mark")
                                ? Task.Status.COMPLETED : Task.Status.INCOMPLETE);
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidTaskNumberException();
                }
            } else if (userInput.equalsIgnoreCase("list")) {
                for (int i = 0; i < tasks.getTaskListSize(); i++) {
                    Task task = tasks.getTask(i);
                    System.out.printf("%d.[%s][%s] %s%n", (i + 1), task.getMarker(),
                            task.getStatusIndicator(), task.getTaskDescription());
                }
            } else if (tokens[0].equalsIgnoreCase("todo")) {
                String taskTitle = userInput.substring(tokens[0].length()).trim();
                if (taskTitle.isEmpty()) {
                    throw new InvalidTaskInputException();
                }
                tasks.addTask(new ToDo(taskTitle));
                System.out.println("Added: " + taskTitle);
            } else if (tokens[0].equalsIgnoreCase("deadline")) {
                String[] parts = userInput.substring(tokens[0].length()).trim()
                        .split("(?i)\\s+/by\\s+", -1);
                if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    throw new InvalidTaskInputException();
                }
                Task task = new Deadline(parts[0].trim(), LocalDateTime.parse(parts[1].trim(), formatter));
                tasks.addTask(task);
                System.out.println("Added: " + task.getTaskDescription());
            } else if (tokens[0].equalsIgnoreCase("event")) {
                String[] parts = userInput.substring(tokens[0].length()).trim()
                        .split("(?i)\\s+/from\\s+", -1);
                if (parts.length != 2 || parts[0].trim().isEmpty()) {
                    throw new InvalidTaskInputException();
                }
                String[] timing = parts[1].trim().split("(?i)\\s+/to\\s+", -1);
                if (timing.length != 2 || timing[0].trim().isEmpty() || timing[1].trim().isEmpty()) {
                    throw new InvalidTaskInputException();
                }
                Task task = new Event(parts[0].trim(), LocalDateTime.parse(timing[0].trim(), formatter), LocalDateTime.parse(timing[1].trim(), formatter));
                tasks.addTask(task);
                System.out.println("Added: " + task.getTaskDescription());
            } else { // Else, reject an unsupported command
                throw new UnrecognisedCommandException();
            }
        } catch (InvalidTaskInputException | InvalidTaskNumberException
                 | UnrecognisedCommandException e) {
            System.out.println(e.getMessage());
        }
        storage.saveTasks(tasks);
    }
}
