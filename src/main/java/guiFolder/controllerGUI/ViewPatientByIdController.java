package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import Pojos.Patients;
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

public class ViewPatientByIdController {
	@FXML
    private TextField patientIdField;

    @FXML
    private TextArea patientInfoArea;

    @FXML
    private Label messageLabel;

    @FXML
    private Button searchPatientButton;

    @FXML
    private Button backButton;

    private ConnectionManager cm;
    private PatientManager pm;

    @FXML
    private void initialize() {
        messageLabel.setText("");
        patientInfoArea.setEditable(false);

        cm = new ConnectionManager();
        pm = new PatientManager(cm.getConnection());
    }

    @FXML
    private void handleSearchPatient() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());

            Patients patient = pm.getPatientById(patientId);

            if (patient == null) {
                patientInfoArea.clear();
                messageLabel.setText("Patient not found.");
                return;
            }

            patientInfoArea.setText(patient.toString());
            messageLabel.setText("Patient found.");

        } catch (NumberFormatException e) {
            patientInfoArea.clear();
            messageLabel.setText("Patient ID must be a number.");
        } catch (Exception e) {
            patientInfoArea.clear();
            messageLabel.setText("Error searching patient.");
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


