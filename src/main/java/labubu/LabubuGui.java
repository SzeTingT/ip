package labubu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Provides a chatbot-style JavaFX interface for Labubu. */
public class LabubuGui extends Application {
    private final VBox conversation = new VBox(10);
    private final Labubu labubu = new Labubu("data/labubu.txt");

    /**
     * Builds and displays the Labubu window.
     *
     * @param stage JavaFX application stage.
     */
    @Override
    public void start(Stage stage) {
        conversation.setPadding(new Insets(16));
        conversation.setStyle("-fx-background-color: #fff7fb;");
        addMessage(new Ui().getIntro(), false);

        ScrollPane messages = new ScrollPane(conversation);
        messages.setFitToWidth(true);
        messages.vvalueProperty().bind(conversation.heightProperty());

        TextField input = new TextField();
        input.setPromptText("Type a command, e.g. list or todo read book");
        Button send = new Button("Send");
        Runnable submit = () -> {
            String command = input.getText().trim();
            if (command.isEmpty()) {
                return;
            }
            addMessage(command, true);
            addMessage(labubu.processGuiCommand(command), false);
            input.clear();
        };
        send.setOnAction(event -> submit.run());
        input.setOnAction(event -> submit.run());

        HBox composer = new HBox(8, input, send);
        composer.setPadding(new Insets(12));
        HBox.setHgrow(input, javafx.scene.layout.Priority.ALWAYS);
        BorderPane root = new BorderPane(messages, null, null, composer, null);
        root.setStyle("-fx-background-color: #fff7fb;");
        stage.setTitle("Labubu - Task Tracker");
        stage.setScene(new Scene(root, 700, 650));
        stage.show();
    }

    private void addMessage(String text, boolean fromUser) {
        Label bubble = new Label(text == null || text.isEmpty() ? "Done." : text);
        bubble.setWrapText(true);
        bubble.setMaxWidth(500);
        bubble.setPadding(new Insets(10, 14, 10, 14));
        bubble.setStyle(fromUser
                ? "-fx-background-color: #d9b8ff; -fx-background-radius: 16;"
                : "-fx-background-color: #ffffff; -fx-background-radius: 16;"
                        + "-fx-border-color: #edc6dc; -fx-border-radius: 16;");
        HBox row = new HBox(bubble);
        row.setAlignment(fromUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        conversation.getChildren().add(row);
    }
}
