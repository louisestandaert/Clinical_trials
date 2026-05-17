package guiFolder.controllerGUI;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import jdbc.ConnectionManager;
import jdbc.HospitalTrialManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewAllTrialsInHospitalController {
	@FXML
    private TextField hospitalIdField;

    @FXML
    private TextArea trialsArea;

    @FXML
    private Label messageLabel;

    @FXML
    private Button searchTrialsButton;

    @FXML
    private Button backButton;

    private ConnectionManager cm;
    private HospitalTrialManager htm;

    @FXML
    private void initialize() {
        messageLabel.setText("");

        trialsArea.setEditable(false);
        trialsArea.setWrapText(true);

        cm = new ConnectionManager();
        htm = new HospitalTrialManager(cm.getConnection());
    }

    @FXML
    private void handleSearchTrials() {
        try {
            int hospitalId = Integer.parseInt(hospitalIdField.getText());

            List<Integer> trials = htm.findTrialsByHospitalId(hospitalId);

            if (trials == null || trials.isEmpty()) {
                trialsArea.clear();
                messageLabel.setText("No trials found for this hospital.");
                return;
            }

            StringBuilder text = new StringBuilder();

            for (Integer trialId : trials) {
                text.append("Trial ID: ").append(trialId).append("\n");
            }

            trialsArea.setText(text.toString());
            messageLabel.setText("Trials loaded successfully.");

        } catch (NumberFormatException e) {
            trialsArea.clear();
            messageLabel.setText("Hospital ID must be a number.");
        } catch (Exception e) {
            trialsArea.clear();
            messageLabel.setText("Error loading trials.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        openWindow("/guiFolder/viewGUI/DoctorMenu.fxml");
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
