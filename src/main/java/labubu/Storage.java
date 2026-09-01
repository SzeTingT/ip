package labubu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving tasks in the application's save file.
 */
public class Storage {
    private final Path filePath;


    /**
     * Creates a storage handler for the specified file.
     *
     * @param filePath Path to the save file.
     */
    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    /**
     * Saves the taskList variable as a file.
     *
     * @param taskList Task list.
     */
    public void saveTasks(TaskList taskList) {
        List<String> lines = taskList.toSaveFormat();

        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.println("Unable to save tasks.");
        }
    }
    
    /**
     * Loads tasks from the save file.
     *
     * @return Tasks reconstructed from the save file.
     * @throws IOException If the save file cannot be read.
     * @throws IllegalArgumentException If the save file contains invalid data.
     */
    public List<Task> loadTasks() throws IOException, IllegalArgumentException {
        if (Files.notExists(filePath)) { // If save file doesn't exist
            System.out.println("Save file not found. Starting with an empty task list.");
            return new ArrayList<>();
        }

        List<String> lines;

        try { // If save file cannot be read
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.out.println("Unable to load the save file. Starting with an empty task list.");
            return new ArrayList<>();
        }

        List<Task> tasks = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split("\\|", -1);
            Task.Status status = Task.Status.valueOf(parts[1]);
            Task task;

            try {
                switch (parts[0]) {
                    case "T":
                        task = new ToDo(parts[2]);
                        break;

                    case "D":
                        task = new Deadline(parts[2], LocalDateTime.parse(parts[3]));
                        break;

                    case "E":
                        task = new Event(
                                parts[2],
                                LocalDateTime.parse(parts[3]),
                                LocalDateTime.parse(parts[4])
                        );
                        break;

                    default:
                        Files.deleteIfExists(filePath); // Delete corrupted save file
                        throw new IllegalArgumentException("Unknown task marker. Resetting save file.");
                }
            } catch (DateTimeParseException e) { // Delete corrupted save file
                Files.deleteIfExists(filePath);
                throw new IllegalArgumentException("Invalid date/time format. Resetting save file.", e);
            }

            task.setStatus(status);
            tasks.add(task);
        }

        if (!tasks.isEmpty()) {
            System.out.println("Successfully loaded save file.");
        }

        return tasks;
    }
}
