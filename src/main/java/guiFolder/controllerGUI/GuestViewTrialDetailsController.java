package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import Pojos.Trial;
import jdbc.ConnectionManager;
import jdbc.TrialManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuestViewTrialDetailsController {
	
	@FXML
    private TextField trialIdField;

    @FXML
    private TextArea trialDetailsArea;

    @FXML
    private Label messageLabel;

    @FXML
    private Button searchButton;

    @FXML
    private Button backButton;

    private ConnectionManager cm;
    private TrialManager tm;

    @FXML
    private void initialize() {
        messageLabel.setText("");
        trialDetailsArea.setEditable(false);
        trialDetailsArea.setWrapText(true);

        cm = new ConnectionManager();
        tm = new TrialManager(cm);
    }

    @FXML
    private void handleSearchTrial() {
        try {
            int trialId = Integer.parseInt(trialIdField.getText());

            Trial trialDetails = tm.viewTrial(trialId);

            if (trialDetails == null) {
                trialDetailsArea.clear();
                messageLabel.setText("Trial with the ID given not found.");
                return;
            }

            trialDetailsArea.setText(trialDetails.toString());
            messageLabel.setText("Trial details loaded successfully.");

        } catch (NumberFormatException e) {
            trialDetailsArea.clear();
            messageLabel.setText("Trial ID must be a number.");
        } catch (Exception e) {
            trialDetailsArea.clear();
            messageLabel.setText("Error loading trial details.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        openWindow("/guiFolder/viewGUI/GuestMenu.fxml");
    }

    private void openWindow(String fxmlPath) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                messageLabel.setText("FXML not found: " + fxmlPath);
                return;
            }

            Parent root = FXMLLoader.load(fxmlUrl);

            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root, 800, 500);

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            messageLabel.setText("Error opening window.");
            e.printStackTrace();
        }
}


}
