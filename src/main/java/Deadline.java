public class Deadline extends Task {
    String by;

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
