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

        while (true) { // Exit command
            Scanner scanner = new Scanner(System.in);

            // Take in user input
            System.out.print("> ");
            userInput = scanner.nextLine();

            String[] tokens = userInput.split(" ");

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
                    System.out.printf("%d. [%s] %s%n", (i + 1), (task.getDone() ? "X" : " "), task.getTaskTitle());
                }
            } else { // Else, add input to task list
                taskList.add(new Task(userInput));
                System.out.println("Added: " + userInput);
            }
        }
        System.out.println(exit);
    }
}
