package labubu;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    static void main(String[] args) {
        Application.launch(LabubuGui.class, args);
    }
}
