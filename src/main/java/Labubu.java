import java.util.Scanner;
import java.util.*;

/**
 * Starts the Labubu chatbot application.
 */
public class Labubu {
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

        // Print introduction message
        System.out.println(intro);
        String userInput = ""; // To store user input
        Scanner scanner = new Scanner(System.in);

        while (true) { // Exit command
            // Take in user input
            System.out.print("> ");
            userInput = scanner.nextLine().trim();

            if (userInput.isEmpty()) {
                System.out.println("Invalid input.");
                continue;
            }

            String[] tokens = userInput.split("\\s+");

            if (userInput.equalsIgnoreCase("bye")) { // Terminate; exit command
                break;
            }
            else if (tokens[0].equalsIgnoreCase("mark") || // Marking functionality
                     tokens[0].equalsIgnoreCase("unmark")) {  // Unmarking functionality
                if (tokens.length < 2) {
                    System.out.println("Invalid input.");
                    continue;
                }
                try {
                    int idx = Integer.parseInt(tokens[1]);
                    if (idx <= 0 || idx > taskList.size()) {
                        System.out.println("Invalid input."); continue;
                    }
                    taskList.get(idx - 1).setDone(tokens[0].equalsIgnoreCase("mark"));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                }
            }
            else if (userInput.equalsIgnoreCase("list")) { // List task list
                for (int i = 0; i < taskList.size(); i++) {
                    Task task = taskList.get(i);
                    System.out.printf("%d.[%s][%s] %s%n", (i + 1), task.getMarker(),
                            (task.getDone() ? "X" : " "), task.getTaskDescription());
                }
            } else if (tokens[0].equalsIgnoreCase("todo")) {
                String taskTitle = userInput.substring(tokens[0].length()).trim();
                if (taskTitle.isEmpty()) {
                    System.out.println("Invalid input.");
                    continue;
                }
                taskList.add(new ToDo(taskTitle));
                System.out.println("Added: " + taskTitle);
            } else if (tokens[0].equalsIgnoreCase("deadline")) {
                String[] parts = userInput.substring(tokens[0].length()).trim().split("(?i)\\s+/by\\s+", -1);
                if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    System.out.println("Invalid input.");
                    continue;
                }
                Task task = new Deadline(parts[0].trim(), parts[1].trim());
                taskList.add(task);
                System.out.println("Added: " + task.getTaskDescription());
            } else if (tokens[0].equalsIgnoreCase("event")) {
                String[] parts = userInput.substring(tokens[0].length()).trim().split("(?i)\\s+/from\\s+", -1);
                if (parts.length != 2 || parts[0].trim().isEmpty()) {
                    System.out.println("Invalid input.");
                    continue;
                }
                String[] timing = parts[1].trim().split("(?i)\\s+/to\\s+", -1);
                if (timing.length != 2 || timing[0].trim().isEmpty() || timing[1].trim().isEmpty()) {
                    System.out.println("Invalid input.");
                    continue;
                }
                Task task = new Event(parts[0].trim(), timing[0].trim(), timing[1].trim());
                taskList.add(task);
                System.out.println("Added: " + task.getTaskDescription());
            } else { // Else, add input to task list
                System.out.println("Unrecognised command");
            }
        }
        System.out.println(exit);
    }
}
