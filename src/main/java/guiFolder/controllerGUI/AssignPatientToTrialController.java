package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import jdbc.ConnectionManager;
import jdbc.PatientManager;
import jdbc.TrialManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AssignPatientToTrialController {
	
	@FXML
    private TextField patientIdField;

    @FXML
    private TextField trialIdField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button assignPatientButton;

    private ConnectionManager cm;
    private TrialManager tm;
    private PatientManager pm;

    @FXML
    private void initialize() {
        messageLabel.setText("");
        messageLabel.setPrefWidth(400);
        messageLabel.setWrapText(true);

        cm = new ConnectionManager();
        tm = new TrialManager(cm);
        pm = new PatientManager(cm.getConnection());
    }

    @FXML
    private void handleAssignPatientToTrial() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());
            int trialId = Integer.parseInt(trialIdField.getText());

            if (pm.getPatientById(patientId) == null) {
                messageLabel.setText("Patient does not exist.");
                return;
            }

            boolean assigned = tm.enrollPatientInTrial(patientId, trialId);

            if (assigned) {
                messageLabel.setText("Patient assigned to trial successfully.");

                patientIdField.clear();
                trialIdField.clear();

            } else {
                messageLabel.setText("Failed to assign patient. Check the IDs.");
            }

        } catch (NumberFormatException e) {
            messageLabel.setText("Patient ID and Trial ID must be numbers.");
        } catch (Exception e) {
            messageLabel.setText("Error assigning patient to trial.");
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

            Stage stage = (Stage) assignPatientButton.getScene().getWindow();
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


