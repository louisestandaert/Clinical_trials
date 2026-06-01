package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import Pojos.Description;
import Pojos.Hospitals;
import jdbc.ConnectionManager;
import jdbc.HospitalManager;
import jdbc.PatientManager;
import jdbc.TrialManager;
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
    private Label messageLabel;

    @FXML
    private Button addPatientButton;

    @FXML
    private Button backButton;

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

            if (!hospitalExists(hospitalId)) {
                messageLabel.setText("Hospital does not exist.");
                return;
            }

            int descriptionId = getNextDescriptionId();
            dm.insertDescription(descriptionId, "Unknown", "Unknown", patientId);

            if (dm.findDescriptionByID(descriptionId) == null) {
                messageLabel.setText("The initial description could not be created.");
                return;
            }

            pm.insertPatient(patientId, patientName, testResult, trialId, hospitalId, descriptionId);

            if (pm.getPatientById(patientId) == null) {
                messageLabel.setText("Patient could not be added.");
                return;
            }

            messageLabel.setText("Patient added successfully. Initial description ID: " + descriptionId);

            patientIdField.clear();
            patientNameField.clear();
            testResultField.clear();
            trialIdField.clear();
            hospitalIdField.clear();

        } catch (NumberFormatException e) {
            messageLabel.setText("Patient ID, Trial ID and Hospital ID must be numbers.");
        } catch (Exception e) {
            messageLabel.setText("Error adding patient.");
            e.printStackTrace();
        }
    }

    private int getNextDescriptionId() {
        int maxId = 0;

        for (Description description : dm.showAllDescriptions()) {
            if (description.getDescription_id() > maxId) {
                maxId = description.getDescription_id();
            }
        }

        return maxId + 1;
    }

    private boolean hospitalExists(int hospitalId) {
        for (Hospitals hospital : hm.getAllHospitals()) {
            if (hospital.getHospitalId() == hospitalId) {
                return true;
            }
        }

        return false;
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
            Scene scene = new Scene(root); //800 500

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            messageLabel.setText("Error opening window.");
            e.printStackTrace();
        }
    }

}
