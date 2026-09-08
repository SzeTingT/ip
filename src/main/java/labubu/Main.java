package labubu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Starts the Labubu JavaFX application. */
public class Main extends Application {
    /** Displays the reusable main window component. */
    @Override
    public void start(Stage stage) {
        MainWindow mainWindow = new MainWindow(new Labubu("data/labubu.txt"));
        Scene scene = new Scene(mainWindow, 700, 650);
        scene.getStylesheets().add(getClass().getResource("/css/main-window.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/input-field.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/speech-bubble.css").toExternalForm());
        stage.setTitle("Labubu - Task Tracker");
        stage.setScene(scene);
        stage.setMinHeight(220);
        stage.setMinWidth(417);
        stage.show();
    }
}
