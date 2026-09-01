package labubu;

import java.io.IOException;
import java.util.Scanner;

/**
 * Starts the Labubu chatbot application.
 */
public class Labubu {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Labubu(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.loadTasks());
        } catch (IOException | IllegalArgumentException e) {
            System.out.printf("Save file corrupted or not found. Creating new save...");
            loadedTasks = new TaskList();
        }
        tasks = loadedTasks;
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

    public static void main(String[] args) {
        new Labubu("data/labubu.txt").run();
    }
}
