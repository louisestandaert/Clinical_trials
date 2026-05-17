package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;

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
import javafx.stage.Stage;


public class GuestViewAllTrialsController {
	
	@FXML
    private TextArea trialsArea;

    @FXML
    private Label messageLabel;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    private ConnectionManager cm;
    private TrialManager tm;
    
    @FXML
    private void initialize() {
        messageLabel.setText("");
        trialsArea.setEditable(false);
        trialsArea.setWrapText(true);

        cm = new ConnectionManager();
        tm = new TrialManager(cm);

        loadTrials();
    }

    @FXML
    private void handleRefreshTrials() {
        loadTrials();
    }

    private void loadTrials() {
        try {
            List<Trial> trials = tm.getAllTrials();

            if (trials == null || trials.isEmpty()) {
                trialsArea.setText("");
                messageLabel.setText("No trials found.");
                return;
            }

            StringBuilder text = new StringBuilder();

            for (Trial trial : trials) {
                text.append("Trial ID: ").append(trial.getTrialId()).append("\n");
                text.append("Trial Name: ").append(trial.getTrialName()).append("\n");
                text.append("More details are hidden for guests.").append("\n");
                text.append("-----------------------------").append("\n");
            }

            trialsArea.setText(text.toString());
            messageLabel.setText("Trials loaded successfully.");

        } catch (Exception e) {
            trialsArea.setText("");
            messageLabel.setText("Error loading trials.");
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
