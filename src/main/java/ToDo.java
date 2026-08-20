public class ToDo extends Task {
    public ToDo(String taskTitle) {
        super(taskTitle);
    }

    @Override
    public String getMarker() {
        return "T";
    }
}
