package labubu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Starts the Labubu chatbot application.
 */
public class Labubu {
    private static final Path SAVE_FILE = Path.of("data", "labubu.txt");
    private static final DateTimeFormatter formatter =
            new DateTimeFormatterBuilder()
                    .appendPattern("dd/MM/yyyy")
                    .optionalStart()
                    .appendPattern(" HH:mm")
                    .optionalEnd()
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 23)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
                    .toFormatter(); // Custom date time formatter


    /**
     * Saves the taskList variable as a file.
     *
     * @param taskList Task list.
     */
    private static void saveTasks(List<Task> taskList) {
        List<String> lines = new ArrayList<>();

        for (Task task : taskList) {
            lines.add(task.toSaveFormat());
        }

        try {
            Files.createDirectories(SAVE_FILE.getParent());
            Files.write(SAVE_FILE, lines, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.println("Unable to save tasks.");
        }
    }

    /**
     * Loads the save file in the relative path and returns.
     *
     * @return Loaded save file as List<Task>.
     */
    private static List<Task> loadTasks() throws IOException {
        if (Files.notExists(SAVE_FILE)) { // If save file doesn't exist
            System.out.println("Save file not found. Starting with an empty task list.");
            return new ArrayList<>();
        }

        List<String> lines;

        try { // If save file cannot be read
            lines = Files.readAllLines(SAVE_FILE, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.println("Unable to load the save file. Starting with an empty task list.");
            return new ArrayList<>();
        }

        List<Task> tasks = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split("\\|", -1);
            Task.Status status = Task.Status.valueOf(parts[1]);
            Task task;

            try {
                switch (parts[0]) {
                    case "T":
                        task = new ToDo(parts[2]);
                        break;

                    case "D":
                        task = new Deadline(parts[2], LocalDateTime.parse(parts[3]));
                        break;

                    case "E":
                        task = new Event(
                                parts[2],
                                LocalDateTime.parse(parts[3]),
                                LocalDateTime.parse(parts[4])
                        );
                        break;

                    default:
                        Files.deleteIfExists(SAVE_FILE); // Delete corrupted save file
                        throw new IllegalArgumentException("Unknown task marker. Resetting save file.");
                }
            } catch (DateTimeParseException e) { // Delete corrupted save file
                Files.deleteIfExists(SAVE_FILE);
                throw new IllegalArgumentException("Invalid date/time format. Resetting save file.", e);
            }

            task.setStatus(status);
            tasks.add(task);
        }

        if (!tasks.isEmpty()) {
            System.out.println("Successfully loaded save file.");
        }

        return tasks;
    }

    /**
     * Runs the Labubu command-line application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) throws IOException {
        List<Task> taskList = loadTasks();
        String intro =
                  "____________________________________________________________\n"
                + "██╗      █████╗ ██████╗ ██╗   ██╗██████╗ ██╗   ██╗\n"
                + "██║     ██╔══██╗██╔══██╗██║   ██║██╔══██╗██║   ██║\n"
                + "██║     ███████║██████╔╝██║   ██║██████╔╝██║   ██║\n"
                + "██║     ██╔══██║██╔══██╗██║   ██║██╔══██╗██║   ██║\n"
                + "███████╗██║  ██║██████╔╝╚██████╔╝██████╔╝╚██████╔╝\n"
                + "╚══════╝╚═╝  ╚═╝╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ \n"
                + "Hello! I'm Labubu. A task tracker bot.\n\n"
                + "Tasks available: \n"
                + "To-do: todo [task-title] \n"
                + "Deadline: deadline [task-title] /by [date-time] \n"
                + "Event: event [task-title] /from [date-time] /to [date-time] \n"
                + "Enter dates in the following format: dd/MM/yyyy <optional>HH:mm</optional>  e.g: 06/07/2026 18:30\n";
        String exit =
                "____________________________________________________________\n"
              + "Bye. Hope to see you again soon!\n"
              + "____________________________________________________________\n";

        System.out.println(intro);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine().trim();

            try {
                if (userInput.isEmpty()) {
                    throw new InvalidTaskInputException();
                }

                String[] tokens = userInput.split("\\s+");

                if (userInput.equalsIgnoreCase("bye") || userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("quit")) {
                    saveTasks(taskList);
                    break;
                } else if (tokens[0].equalsIgnoreCase("mark")
                        || tokens[0].equalsIgnoreCase("unmark")
                        || tokens[0].equalsIgnoreCase("delete")) {
                    if (tokens.length < 2) {
                        throw new InvalidTaskNumberException();
                    }
                    try {
                        int index = Integer.parseInt(tokens[1]);
                        if (index <= 0 || index > taskList.size()) {
                            throw new InvalidTaskNumberException();
                        }
                        if (tokens[0].equalsIgnoreCase("delete")) {
                            Task task = taskList.remove(index - 1);
                            System.out.println("Noted. I've removed this task:");
                            System.out.printf("  [%s][%s] %s%n", task.getMarker(),
                                    task.getStatusIndicator(), task.getTaskDescription());
                            System.out.println("Now you have " + taskList.size() + " tasks in the list.");
                        } else {
                            taskList.get(index - 1).setStatus(tokens[0].equalsIgnoreCase("mark")
                                    ? Task.Status.COMPLETED : Task.Status.INCOMPLETE);
                        }
                    } catch (NumberFormatException e) {
                        throw new InvalidTaskNumberException();
                    }
                } else if (userInput.equalsIgnoreCase("list")) {
                    for (int i = 0; i < taskList.size(); i++) {
                        Task task = taskList.get(i);
                        System.out.printf("%d.[%s][%s] %s%n", (i + 1), task.getMarker(),
                                task.getStatusIndicator(), task.getTaskDescription());
                    }
                } else if (tokens[0].equalsIgnoreCase("todo")) {
                    String taskTitle = userInput.substring(tokens[0].length()).trim();
                    if (taskTitle.isEmpty()) {
                        throw new InvalidTaskInputException();
                    }
                    taskList.add(new ToDo(taskTitle));
                    System.out.println("Added: " + taskTitle);
                } else if (tokens[0].equalsIgnoreCase("deadline")) {
                    String[] parts = userInput.substring(tokens[0].length()).trim()
                            .split("(?i)\\s+/by\\s+", -1);
                    if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new InvalidTaskInputException();
                    }
                    Task task = new Deadline(parts[0].trim(), LocalDateTime.parse(parts[1].trim(), formatter));
                    taskList.add(task);
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
                    taskList.add(task);
                    System.out.println("Added: " + task.getTaskDescription());
                } else { // Else, reject an unsupported command
                    throw new UnrecognisedCommandException();
                }
            } catch (InvalidTaskInputException | InvalidTaskNumberException
                     | UnrecognisedCommandException e) {
                System.out.println(e.getMessage());
            }
            saveTasks(taskList);
        }
        System.out.println(exit);
    }
}
