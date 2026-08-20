import java.util.Scanner;

/**
 * Starts the Labubu chatbot application.
 */
public class Labubu {
    public static void main(String[] args) {
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
        String userInput = "";
        while (!userInput.equalsIgnoreCase("bye")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("> ");
            userInput = scanner.nextLine();
            System.out.println(userInput);
        }
        System.out.println(exit);
    }
}
