package labubu;

/**
 * Provides the user-facing introduction and exit messages for Labubu.
 */
public class Ui {
    private final String intro =
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
                    + "Enter dates in the following format: dd/MM/yyyy <optional>HH:mm</optional>  "
                    + "e.g: 06/07/2026 18:30\n";

    private final String exit =
            "____________________________________________________________\n"
                    + "Bye. Hope to see you again soon!\n"
                    + "____________________________________________________________\n";

    /**
     * Returns the application's introduction message.
     *
     * @return Introduction message.
     */
    public String getIntro() {
        return intro;
    }

    /**
     * Returns the application's exit message.
     *
     * @return Exit message.
     */
    public String getExit() {
        return exit;
    }
}
