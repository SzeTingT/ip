public abstract class Task {
    /**
     * Represents a task's progress state.
     */
    public enum Status {
        INCOMPLETE,
        IN_PROGRESS,
        COMPLETED
    }

    String taskTitle;
    Status status;

    public Task(String taskTitle) {
        this.taskTitle = taskTitle;
        this.status = Status.INCOMPLETE;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    /**
     * Returns the symbol used to display this task's progress state.
     *
     * @return a single-character status indicator
     */
    public String getStatusIndicator() {
        if (status == Status.COMPLETED) {
            return "X";
        } else if (status == Status.IN_PROGRESS) {
            return "-";
        }
        return " ";
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
