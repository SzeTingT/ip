import java.util.Scanner;
import java.util.*;

/**
 * Starts the Labubu chatbot application.
 */
public class Labubu {
    public static void main(String[] args) {
        List<String> taskList = new ArrayList<>();

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

            if (userInput.equalsIgnoreCase("bye")) { // Terminate; exit command
                break;
            }
            else if (userInput.equalsIgnoreCase("list")) { // List task list
                for (int i = 0; i < taskList.size(); i++) {
                    System.out.println((i + 1) + ". " + taskList.get(i));
                }
            } else { // Else, add input to task list
                taskList.add(userInput);
                System.out.println("Added: " + userInput);
            }
        }
        System.out.println(exit);
    }
}
