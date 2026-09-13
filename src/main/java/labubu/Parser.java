package labubu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Parses user commands and applies them to Labubu's task list.
 */
public class Parser {
    private static final DateTimeFormatter FORMATTER =
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
     * @return True if the user requests application termination.
     */
    public boolean parse() {
        System.out.print("> ");
        String userInput = scanner.nextLine().trim();

        try {
            if (userInput.isEmpty()) {
                throw new InvalidTaskInputException();
            }

            String[] tokens = userInput.split("\\s+");

            Command command = Command.identify(userInput, tokens);
            switch (command) {
            case EXIT:
                storage.saveTasks(tasks);
                return true;
            case TASK_UPDATE:
                handleTaskUpdate(tokens);
                break;
            case FIND:
                handleFind(tokens);
                break;
            case LIST:
                printTaskList();
                break;
            case TODO:
                handleToDo(userInput, tokens[0]);
                break;
            case DEADLINE:
                handleDeadline(userInput, tokens[0]);
                break;
            case EVENT:
                handleEvent(userInput, tokens[0]);
                break;
            case HELP:
                System.out.println(new Ui().getHelp());
                break;
            case UNKNOWN:
                throw new UnrecognisedCommandException();
            default:
                throw new AssertionError("Unhandled command: " + command);
            }
        } catch (InvalidTaskInputException | InvalidTaskNumberException
                 | UnrecognisedCommandException e) {
            System.out.println(e.getMessage());
        }
        storage.saveTasks(tasks);
        return false;
    }

    private void handleTaskUpdate(String[] tokens) throws InvalidTaskNumberException {
        if (tokens.length < 2) {
            throw new InvalidTaskNumberException();
        }

        try {
            int index = Integer.parseInt(tokens[1]) - 1;
            if (index < 0 || index >= tasks.getTaskListSize()) {
                throw new InvalidTaskNumberException();
            }

            if (tokens[0].equalsIgnoreCase("delete")) {
                handleDelete(index);
            } else {
                updateTaskStatus(tokens[0], index);
            }
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException();
        }
    }

    private void handleDelete(int index) {
        Task task = tasks.removeTask(index);
        System.out.println("Noted. I've removed this task:");
        System.out.printf("  [%s][%s] %s%n", task.getMarker(),
                task.getStatusIndicator(), task.getTaskDescription());
        System.out.println("Now you have " + tasks.getTaskListSize() + " tasks in the list.");
    }

    private void updateTaskStatus(String command, int index) {
        Task.Status status = command.equalsIgnoreCase("mark")
                ? Task.Status.COMPLETED : Task.Status.INCOMPLETE;
        tasks.getTask(index).setStatus(status);
    }

    private void handleFind(String[] tokens) throws InvalidTaskInputException {
        if (tokens.length < 2) {
            throw new InvalidTaskInputException();
        }

        String keyword = String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length));
        List<Task> matchedTasks = tasks.findTaskByKeyword(keyword);

        System.out.printf("____________________________________________________________%n");
        System.out.printf("These are the matching tasks:%n");
        for (int i = 0; i < matchedTasks.size(); i++) {
            Task task = matchedTasks.get(i);
            System.out.printf("%d. [%s][%s] %s%n", i + 1, task.getMarker(),
                    task.getStatusIndicator(), task.getTaskDescription());
        }
        System.out.printf("____________________________________________________________%n");
    }

    private void printTaskList() {
        for (int i = 0; i < tasks.getTaskListSize(); i++) {
            Task task = tasks.getTask(i);
            System.out.printf("%d. [%s][%s] %s%n", i + 1, task.getMarker(),
                    task.getStatusIndicator(), task.getTaskDescription());
        }
    }

    private void handleToDo(String userInput, String command) throws InvalidTaskInputException {
        String taskTitle = userInput.substring(command.length()).trim();
        if (taskTitle.isEmpty()) {
            throw new InvalidTaskInputException();
        }

        tasks.addTask(new ToDo(taskTitle));
        System.out.println("Added: " + taskTitle);
    }

    private void handleDeadline(String userInput, String command) throws InvalidTaskInputException {
        String[] parts = userInput.substring(command.length()).trim()
                .split("(?i)\\s+/by\\s+", -1);
        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new InvalidTaskInputException();
        }

        Task task = new Deadline(parts[0].trim(), LocalDateTime.parse(parts[1].trim(), FORMATTER));
        tasks.addTask(task);
        System.out.println("Added: " + task.getTaskDescription());
    }

    private void handleEvent(String userInput, String command) throws InvalidTaskInputException {
        String[] parts = userInput.substring(command.length()).trim()
                .split("(?i)\\s+/from\\s+", -1);
        if (parts.length != 2 || parts[0].trim().isEmpty()) {
            throw new InvalidTaskInputException();
        }

        String[] timing = parts[1].trim().split("(?i)\\s+/to\\s+", -1);
        if (timing.length != 2 || timing[0].trim().isEmpty() || timing[1].trim().isEmpty()) {
            throw new InvalidTaskInputException();
        }

        Task task = new Event(parts[0].trim(),
                LocalDateTime.parse(timing[0].trim(), FORMATTER),
                LocalDateTime.parse(timing[1].trim(), FORMATTER));
        tasks.addTask(task);
        System.out.println("Added: " + task.getTaskDescription());
    }
}
