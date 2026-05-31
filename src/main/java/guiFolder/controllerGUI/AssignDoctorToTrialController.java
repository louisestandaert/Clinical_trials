package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import jdbc.ConnectionManager;
import jdbc.TrialManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AssignDoctorToTrialController {

	@FXML private TextField doctorIdField;
	@FXML private TextField trialIdField;
	@FXML private Label messageLabel;
	@FXML private Button assignButton;

	private ConnectionManager cm;
	private TrialManager tm;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		cm = new ConnectionManager();
		tm = new TrialManager(cm);
	}

	@FXML
	private void handleAssignDoctorToTrial() {
		try {
			int doctorId = Integer.parseInt(doctorIdField.getText());
			int trialId = Integer.parseInt(trialIdField.getText());
			boolean assigned = tm.assignDoctorToTrial(doctorId, trialId);

			if (assigned) {
				messageLabel.setText("Doctor assigned to trial.");
				doctorIdField.clear();
				trialIdField.clear();
			} else {
				messageLabel.setText("Doctor could not be assigned.");
			}

		} catch (NumberFormatException e) {
			messageLabel.setText("Doctor ID and Trial ID must be numbers.");
		}
	}

	@FXML
	private void handleBack() {
		openWindow("/guiFolder/viewGUI/TrialManagerMenu.fxml");
	}

	private void openWindow(String fxmlPath) {
		try {
			URL fxmlUrl = getClass().getResource(fxmlPath);
			Parent root = FXMLLoader.load(fxmlUrl);
			Stage stage = (Stage) assignButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			messageLabel.setText("Error opening window.");
			e.printStackTrace();
		}
	}
}
