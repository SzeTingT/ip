public abstract class Task {
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

    /**
     * Returns the task description with any subtype-specific details.
     *
     * @return task description for display in the task list
     */
    public String getTaskDescription() {
        return taskTitle;
    }

    public abstract String getMarker();
}
