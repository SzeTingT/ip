package labubu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    List<Task> taskList = new ArrayList<>();

    public TaskList() {};

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

    /**
     * Returns a list of tasks with keyword in their task titles.
     *
     * @param keyword Word to match against.
     * @return List of tasks that contain keyword.
     */
    public List<Task> findTaskByKeyword(String keyword) {
        List<Task> matchedTasks = new ArrayList<>();

        for (Task task : taskList) {
            if (task.getTaskTitle().contains(keyword)) {
                matchedTasks.add(task);
            }
        }

        return matchedTasks;
    }
}
