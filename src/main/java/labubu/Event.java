package labubu;

/**
 * Represents a task that happens within a specified period.
 */
public class Event extends Task {
    private String from;
    private String to;

    /**
     * Creates an event task with the given title and time period.
     *
     * @param taskTitle Title of the task.
     * @param from Start time of the event.
     * @param to End time of the event.
     */
    public Event(String taskTitle, String from, String to) {
        super(taskTitle);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getTaskDescription() {
        return taskTitle + " (from: " + from + ", to: " + to + ")";
    }

    @Override
    public String getMarker() {
        return "E";
    }
}
