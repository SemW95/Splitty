package client.scenes;

import client.utils.ScreenUtils;
import com.google.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.springframework.util.FileCopyUtils;

/**
 * Language Select pop-up screen.
 */
public class LanguageSelectCtrl implements Initializable {
    private final MainCtrl mainCtrl;
    private ResourceBundle resources;

    @FXML
    private Pane root;

    @Inject
    public LanguageSelectCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        root.addEventFilter(KeyEvent.KEY_PRESSED,
            ScreenUtils.exitHandler(resources, mainCtrl::closePopup));
    }

    public void setDutch() {
        mainCtrl.changeLanguage("nl");
    }

    public void setEnglish() {
        mainCtrl.changeLanguage("en");
    }

    public void setLith() {
        mainCtrl.changeLanguage("lt");
    }

    public void handleReturn() {
        mainCtrl.closePopup();
    }

    @FXML
    private void handleDownload() {
        File templateFile = new File("src/main/resources/template.properties");
        File newFile = mainCtrl.createSaveFile("template.properties");
        try {
            FileCopyUtils.copy(templateFile, newFile);
        } catch (IOException e) {
            System.err.println("Couldn't copy the template file");
        }
    }
}