package labubu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Starts the Labubu chatbot application.
 */
public class Labubu {
    /**
     * Runs the Labubu command-line application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        List<Task> taskList = new ArrayList<>();

        String intro =
                  "____________________________________________________________\n"
                + "██╗      █████╗ ██████╗ ██╗   ██╗██████╗ ██╗   ██╗\n"
                + "██║     ██╔══██╗██╔══██╗██║   ██║██╔══██╗██║   ██║\n"
                + "██║     ███████║██████╔╝██║   ██║██████╔╝██║   ██║\n"
                + "██║     ██╔══██║██╔══██╗██║   ██║██╔══██╗██║   ██║\n"
                + "███████╗██║  ██║██████╔╝╚██████╔╝██████╔╝╚██████╔╝\n"
                + "╚══════╝╚═╝  ╚═╝╚═════╝  ╚═════╝ ╚═════╝  ╚═════╝ \n"
                + "Hello! I'm Labubu.\n"
                + "What can I do for you?\n";
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

                if (userInput.equalsIgnoreCase("bye")) {
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
                    Task task = new Deadline(parts[0].trim(), parts[1].trim());
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
                    Task task = new Event(parts[0].trim(), timing[0].trim(), timing[1].trim());
                    taskList.add(task);
                    System.out.println("Added: " + task.getTaskDescription());
                } else { // Else, reject an unsupported command
                    throw new UnrecognisedCommandException();
                }
            } catch (InvalidTaskInputException | InvalidTaskNumberException
                     | UnrecognisedCommandException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(exit);
    }
}
