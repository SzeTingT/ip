package labubu;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Starts the Labubu chatbot application.
 */
public class Labubu {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Creates Labubu and loads tasks from the specified save file.
     *
     * @param filePath Path to the save file.
     */
    public Labubu(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (IOException | IllegalArgumentException e) {
            System.out.printf("Save file corrupted or not found. Creating new save...");
            tasks = new TaskList();
        }
    }

    /**
     * Runs the Labubu command-line application.
     *
     */
    public void run() {
        System.out.println(ui.getIntro());

        boolean[] terminateFlag = {false};
        Scanner scanner = new Scanner(System.in);

        Parser parser = new Parser(scanner, storage, tasks);
        while (!terminateFlag[0]) {
            parser.parse(terminateFlag);
        }

        System.out.println(ui.getExit());
    }

    /**
     * Starts the Labubu application using its default save file.
     *
     * @param args Command-line arguments, which are ignored.
     */
    public static void main(String[] args) {
        new Labubu("data/labubu.txt").run();
    }
}
