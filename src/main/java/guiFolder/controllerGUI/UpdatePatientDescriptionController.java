package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import Pojos.Description;
import Pojos.Patients;
import jdbc.ConnectionManager;
import jdbc.DescriptionManager;
import jdbc.PatientManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdatePatientDescriptionController {
	
	@FXML
    private TextField patientIdField;

    @FXML
    private TextField descriptionIdField;

    @FXML
    private TextField newGenderField;

    @FXML
    private TextField newCauseField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button updateDescriptionButton;

    private ConnectionManager cm;
    private PatientManager pm;
    private DescriptionManager dpm;
    
    @FXML
    private void initialize() {
        messageLabel.setText("");

        cm = new ConnectionManager();
        pm = new PatientManager(cm.getConnection());
        dpm = new DescriptionManager(cm.getConnection());
    }

    @FXML
    private void handleUpdateDescription() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());
            int descriptionId = Integer.parseInt(descriptionIdField.getText());

            String newGender = newGenderField.getText();
            String newCause = newCauseField.getText();

            if (newGender.isEmpty() || newCause.isEmpty()) {
                messageLabel.setText("Please complete new gender and new cause.");
                return;
            }

            Patients patient = pm.getPatientById(patientId);

            if (patient == null) {
                messageLabel.setText("Patient does not exist.");
                return;
            }

            Description description = dpm.findDescriptionByID(descriptionId);

            if (description == null) {
                messageLabel.setText("Description does not exist.");
                return;
            }

            if (description.getPatient_id() != patientId) {
                messageLabel.setText("This description does not belong to this patient.");
                return;
            }

            dpm.updateDescription(descriptionId, newGender, newCause, patientId);

            messageLabel.setText("Description updated successfully.");

            patientIdField.clear();
            descriptionIdField.clear();
            newGenderField.clear();
            newCauseField.clear();

        } catch (NumberFormatException e) {
            messageLabel.setText("Patient ID and Description ID must be numbers.");
        } catch (Exception e) {
            messageLabel.setText("Error updating description.");
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

            Stage stage = (Stage) updateDescriptionButton.getScene().getWindow();
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
