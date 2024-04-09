package client.utils;

import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
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

    /**
     * Creates an undo handler.
     *
     * @param onUndo a runnable which is called when the undo shortcut is press
     * @return the event handler
     */
    public static EventHandler<KeyEvent> undoHandler(Runnable onUndo) {
        AtomicBoolean undoHeld = new AtomicBoolean(false);
        return (KeyEvent e) -> {
            if (e.isControlDown() && e.getCode().equals(KeyCode.Z)) {
                if (e.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                    // Check if it is already pressed
                    if (undoHeld.get()) {
                        // It was pressed, therefore it is being held. So ignore it
                        return;
                    }
                    undoHeld.set(true);
                    onUndo.run();
                } else if (e.getEventType().equals(KeyEvent.KEY_RELEASED)) {
                    undoHeld.set(false);
                }
            }
        };
    }
}
