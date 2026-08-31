package labubu;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void testAdd() {
        TaskList taskList = new TaskList();
        ToDo todo = new ToDo("task");

        taskList.addTask(todo);

        assertEquals(new ArrayList<>(List.of("T| |task")), taskList.toSaveFormat());
    }

    @Test
    public void testDelete() {
        TaskList taskList = new TaskList();
        ToDo todo = new ToDo("task");

        taskList.addTask(todo);
        taskList.removeTask(0);

        assertEquals(new ArrayList<>(), taskList.toSaveFormat());
    }
}
