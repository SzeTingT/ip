package labubu;

/**
 * Represents a task that must be completed by a specified time.
 */
public class Deadline extends Task {
    private String by;

    /**
     * Creates a deadline task with the given title and deadline.
     *
     * @param taskTitle Title of the task.
     * @param by Deadline of the task.
     */
    public Deadline(String taskTitle, String by) {
        super(taskTitle);
        this.by = by;
    }

    @Override
    public String getTaskDescription() {
        return taskTitle + " (by: " + by + ")";
    }

    @Override
    public String getMarker() {
        return "D";
    }
}
