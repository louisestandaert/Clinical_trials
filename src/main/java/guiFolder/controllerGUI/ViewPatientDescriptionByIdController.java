package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import Pojos.Description;
import jdbc.ConnectionManager;
import jdbc.PatientManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewPatientDescriptionByIdController {
	@FXML
    private TextField patientIdField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Label messageLabel;

    @FXML
    private Button searchDescriptionButton;

    @FXML
    private Button backButton;

    private ConnectionManager cm;
    private PatientManager pm;

    @FXML
    private void initialize() {
        messageLabel.setText("");

        descriptionArea.setEditable(false);
        descriptionArea.setWrapText(true);

        cm = new ConnectionManager();
        pm = new PatientManager(cm.getConnection());
    }

    @FXML
    private void handleSearchDescription() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());

            Description description = pm.getDescriptionOfPatientById(patientId);

            if (description == null) {
                descriptionArea.clear();
                messageLabel.setText("Description not found for this patient.");
                return;
            }

            descriptionArea.setText(description.toString());
            messageLabel.setText("Description loaded successfully.");

        } catch (NumberFormatException e) {
            descriptionArea.clear();
            messageLabel.setText("Patient ID must be a number.");
        } catch (Exception e) {
            descriptionArea.clear();
            messageLabel.setText("Error loading description.");
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
