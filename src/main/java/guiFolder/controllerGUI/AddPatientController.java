package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import jdbc.ConnectionManager;
import jdbc.PatientManager;
import jdbc.TrialManager;
import jdbc.HospitalManager;
import jdbc.DescriptionManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPatientController {
	
	@FXML
    private TextField patientIdField;

    @FXML
    private TextField patientNameField;

    @FXML
    private TextField testResultField;

    @FXML
    private TextField trialIdField;

    @FXML
    private TextField hospitalIdField;

    @FXML
    private TextField descriptionIdField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button addPatientButton;

    private ConnectionManager cm;
    private PatientManager pm;
    private TrialManager tm;
    private HospitalManager hm;
    private DescriptionManager dm;

    @FXML
    private void initialize() {
        messageLabel.setText("");

        cm = new ConnectionManager();
        pm = new PatientManager(cm.getConnection());
        tm = new TrialManager(cm);
        hm = new HospitalManager(cm.getConnection());
        dm = new DescriptionManager(cm.getConnection());
    }

    @FXML
    private void handleAddPatient() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());
            String patientName = patientNameField.getText();
            String testResult = testResultField.getText();
            int trialId = Integer.parseInt(trialIdField.getText());
            int hospitalId = Integer.parseInt(hospitalIdField.getText());
            int descriptionId = Integer.parseInt(descriptionIdField.getText());

            if (patientName.isEmpty() || testResult.isEmpty()) {
                messageLabel.setText("Please complete patient name and test result.");
                return;
            }

            if (!testResult.equalsIgnoreCase("Positive") && !testResult.equalsIgnoreCase("Negative")) {
                messageLabel.setText("Test result must be Positive or Negative.");
                return;
            }

            if (pm.getPatientById(patientId) != null) {
                messageLabel.setText("A patient with this ID already exists.");
                return;
            }

            if (tm.viewTrial(trialId) == null) {
                messageLabel.setText("Trial does not exist.");
                return;
            }

            if (dm.findDescriptionByID(descriptionId) == null) {
                messageLabel.setText("Description does not exist.");
                return;
            }

            pm.insertPatient(patientId, patientName, testResult, trialId, hospitalId, descriptionId);

            messageLabel.setText("Patient added successfully.");

            patientIdField.clear();
            patientNameField.clear();
            testResultField.clear();
            trialIdField.clear();
            hospitalIdField.clear();
            descriptionIdField.clear();

        } catch (NumberFormatException e) {
            messageLabel.setText("Patient ID, Trial ID, Hospital ID and Description ID must be numbers.");
        } catch (Exception e) {
            messageLabel.setText("Error adding patient.");
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

            Stage stage = (Stage) addPatientButton.getScene().getWindow();
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
