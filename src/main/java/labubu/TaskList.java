package labubu;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> taskList = new ArrayList<>();

    public TaskList() {}

    public TaskList(List<Task> tasks) {
        taskList.addAll(tasks);
    }

    public List<String> toSaveFormat() {
        List<String> lines = new ArrayList<>();
        for (Task task : taskList) {
            lines.add(task.toSaveFormat());
        }
        return lines;
    }

    public int getTaskListSize() {
        return taskList.size();
    }

    public Task getTask(int index) {
        return taskList.get(index);
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public Task removeTask(int index) {
        Task task = taskList.get(index);
        taskList.remove(index);
        return task;
    }
}
