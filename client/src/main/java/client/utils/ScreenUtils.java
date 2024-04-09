package client.utils;

import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Utils for screens.
 */
public class ScreenUtils {
    /**
     * Creates an exit handler which call onClose after esc is pressed.
     *
     * @param resources resource bundle for translations
     * @param onClose   will be called if the user confirms the exit
     * @return the event handler
     */
    public static EventHandler<KeyEvent> exitHandler(ResourceBundle resources,
                                                     Runnable onClose) {
        return (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                // Creating a confirmation dialog
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle(resources.getString("utils.confirmation"));
                confirmAlert.setHeaderText(null); // Optional: No header
                confirmAlert.setContentText(resources.getString("utils.confirmation-content"));

                // This will show the dialog and wait for the user response
                Optional<ButtonType> result = confirmAlert.showAndWait();

                // Checking the user's decision
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    onClose.run();
                }
                event.consume(); // Prevents the event from propagating further
            }
        };
    }
}
