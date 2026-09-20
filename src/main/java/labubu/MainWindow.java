package labubu;

import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/** Composes the conversation area and command input into the main window. */
public class MainWindow extends BorderPane {
    private final VBox conversation = new VBox(10);
    private final ScrollPane messages = new ScrollPane(conversation);
    private final VBox commandArea = new VBox();
    private final InputField inputField = new InputField(this::handleCommand);
    private final Labubu labubu;
    private HelpPanel helpPanel;

    /** Creates the main window for a Labubu instance. */
    public MainWindow(Labubu labubu) {
        this.labubu = labubu;
        conversation.getStyleClass().add("conversation");
        messages.setFitToWidth(true);
        setCenter(messages);
        commandArea.getChildren().add(inputField);
        setBottom(commandArea);
        addMessage(new Ui().getIntro(), false);
    }

    private void handleCommand(String command) {
        addMessage(command, true);
        if (command.equalsIgnoreCase("help")) {
            toggleHelpPanel();
        } else {
            addMessage(labubu.processGuiCommand(command), false);
            if (isExitCommand(command)) {
                Platform.exit();
            }
        }
    }

    private void addMessage(String text, boolean fromUser) {
        conversation.getChildren().add(new SpeechBubble(text, fromUser));
        Platform.runLater(() -> messages.setVvalue(1.0));
    }

    private boolean isExitCommand(String command) {
        return command.equalsIgnoreCase("bye")
                || command.equalsIgnoreCase("quit")
                || command.equalsIgnoreCase("exit");
    }

    private void toggleHelpPanel() {
        if (helpPanel == null) {
            helpPanel = new HelpPanel(new Ui().getHelp(), this::hideHelpPanel);
            helpPanel.prefWidthProperty().bind(commandArea.widthProperty());
            commandArea.getChildren().add(0, helpPanel);
        } else {
            hideHelpPanel();
        }
    }

    private void hideHelpPanel() {
        commandArea.getChildren().remove(helpPanel);
        helpPanel = null;
    }
}
