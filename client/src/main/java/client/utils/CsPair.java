package client.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * A controller and scene pair.
 */
public class CsPair<C> {
    public C ctrl;
    public Scene scene;

    public CsPair(C ctrl, Scene scene) {
        this.ctrl = ctrl;
        this.scene = scene;
    }

    public CsPair(C ctrl, Parent parent) {
        this.ctrl = ctrl;
        this.scene = new Scene(parent);
    }
}
