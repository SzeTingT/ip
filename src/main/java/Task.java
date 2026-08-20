public class Task {
    String taskTitle;
    boolean done;

    public Task(String taskTitle) {
        this.taskTitle = taskTitle;
        this.done = false;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean getDone() {
        return this.done;
    }
}
