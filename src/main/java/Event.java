public class Event extends Task {
    String from, to;

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
