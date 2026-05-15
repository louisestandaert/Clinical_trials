package guiFolder.controllerGUI;
import java.io.IOException;
import java.net.URL;

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


public class AddPatientDescriptionController {
	@FXML
	private TextField patientIdField;

	@FXML
	private TextField descriptionIdField;
	
	@FXML
	private TextField genderField;
	
	@FXML
	private TextField causeField;

	@FXML
	private Button addDescriptionButton;

	@FXML
	private Label messageLabel;
	
	private ConnectionManager connectionManager;
	private DescriptionManager descriptionManager;
	private PatientManager patientManager;
	
	@FXML
	public void initialize() {
		messageLabel.setText("");
		connectionManager = new ConnectionManager();
		descriptionManager = new DescriptionManager(connectionManager.getConnection());
		patientManager = new PatientManager(connectionManager.getConnection());
	}

	@FXML
	private void handleAddDescription() {
		try {
            int patientId = Integer.parseInt(patientIdField.getText());
            int descriptionId = Integer.parseInt(descriptionIdField.getText());
            String gender= genderField.getText();
            String cause = causeField.getText();
            
            if(gender.isEmpty() || cause.isEmpty()) {
            	messageLabel.setText("Please fill in all fields.");
            	return;
            }
            
            Patients patient = patientManager.getPatientById(patientId);
            if(patient == null) {
            	messageLabel.setText("Patient with ID " + patientId + " does not exist.");
            	return;
            }
            
            if(descriptionManager.findDescriptionByID(descriptionId) != null) {
            	messageLabel.setText("Description with ID " + descriptionId + " already exists.");
            	return;
            }
            
            if(descriptionManager.findDescriptionByPatientId(patientId) != null) {
            	messageLabel.setText("Patient with ID " + patientId + " already has a description.");
            	return;
            }
            
            descriptionManager.insertDescription(descriptionId,gender,cause,patientId);
            messageLabel.setText("Description added successfully.");
            
            patientIdField.clear();
            descriptionIdField.clear();
            genderField.clear();
            causeField.clear();
            
		} catch (NumberFormatException e) {
			messageLabel.setText("Please enter valid numeric values for Patient ID and Description ID.");
		} catch (Exception e) {
			messageLabel.setText("An error occurred while adding the description. Please try again.");
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleBack(){
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

            Stage stage = (Stage) addDescriptionButton.getScene().getWindow();
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
