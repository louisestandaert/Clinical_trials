package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

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
import javafx.stage.Stage;

public class ViewAllPatientsController {
	
	@FXML
    private TextArea patientsArea;

    @FXML
    private Label messageLabel;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    private ConnectionManager cm;
    private PatientManager pm;

    @FXML
    private void initialize() {
        messageLabel.setText("");
        patientsArea.setEditable(false);
        patientsArea.setWrapText(true);

        cm = new ConnectionManager();
        pm = new PatientManager(cm.getConnection());

        loadPatients();
    }

    @FXML
    private void handleRefreshPatients() {
        loadPatients();
    }

    private void loadPatients() {
        try {
            Set<Patients> patients = pm.getAllPatients();

            if (patients == null || patients.isEmpty()) {
                patientsArea.setText("");
                messageLabel.setText("No patients found.");
                return;
            }

            StringBuilder text = new StringBuilder();

            for (Patients patient : patients) {
                text.append(patient.toString());
                text.append("\n-----------------------------\n");
            }

            patientsArea.setText(text.toString());
            messageLabel.setText("Patients loaded successfully.");

        } catch (Exception e) {
            patientsArea.setText("");
            messageLabel.setText("Error loading patients.");
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


