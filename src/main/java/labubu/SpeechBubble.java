package labubu;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/** Represents one user or Labubu message in the conversation. */
public class SpeechBubble extends HBox {
    private static final String IMAGE_PATH = "/images/labubuPic.png";

    /** Creates a speech bubble for a user or Labubu message. */
    public SpeechBubble(String text, boolean fromUser) {
        Label bubble = new Label(text == null || text.isEmpty() ? "Done." : text);
        bubble.setWrapText(true);
        bubble.getStyleClass().add(fromUser ? "user-bubble" : "bot-bubble");
        getStyleClass().add(fromUser ? "user-row" : "bot-row");
        getChildren().add(bubble);
        if (!fromUser) {
            ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream(IMAGE_PATH)));
            avatar.setFitWidth(80);
            avatar.setFitHeight(80);
            avatar.setPreserveRatio(true);
            getChildren().add(0, avatar);
        }
        setAlignment(fromUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
    }
}
