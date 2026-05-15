package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import Pojos.Patients;
import Pojos.Trial;
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


public class AddPatientToTrialController {
	@FXML
	private TextField patientIdField;

	@FXML
	private TextField trialIdField;

	@FXML
	private Button addButton;

	@FXML
	private Label messageLabel;
	
	private ConnectionManager connectionManager;
	private TrialManager trialManager;
	private PatientManager patientManager;
	
	@FXML
	public void initialize() {
		messageLabel.setText("");
		connectionManager = new ConnectionManager();
		trialManager = new TrialManager(connectionManager);
		patientManager = new PatientManager(connectionManager.getConnection());
	}

	@FXML
	private void handleAddPatientToTrial() {
		try {
			int patientId= Integer.parseInt(patientIdField.getText());
			int trialId= Integer.parseInt(trialIdField.getText());
			
			Patients patient=patientManager.getPatientById(patientId);
			
			if (patient == null) {
				messageLabel.setText("Patient with ID " + patientId + " does not exist.");
				return;
			}
			
			Trial trial=trialManager.viewTrial(trialId);
			
			if (trial == null) {
				messageLabel.setText("Trial with ID " + trialId + " does not exist.");
				return;
			}
			
			if (patient.getTrialId() != 0) {
				messageLabel.setText("Patient is already enrolled in a trial.");
				return;
			}
			
			boolean enrolled= trialManager.enrollPatientInTrial(patientId, trialId);
		
			if(enrolled) {
			messageLabel.setText("Patient added to trial successfully.");
			patientIdField.clear();
			trialIdField.clear();
		} else {
			messageLabel.setText("Failed to add patient to trial. Please check the IDs and try again.");
		}

			
		} catch (NumberFormatException e) {
			messageLabel.setText("Please enter valid numeric IDs.");
		} catch (Exception e) {
			messageLabel.setText("Error adding patient to trial: ");
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

            Stage stage = (Stage) addButton.getScene().getWindow();
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
