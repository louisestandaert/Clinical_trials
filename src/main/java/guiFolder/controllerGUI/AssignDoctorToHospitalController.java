package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import jdbc.ConnectionManager;
import jdbc.DoctorManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AssignDoctorToHospitalController {

	@FXML private TextField doctorIdField;
	@FXML private TextField hospitalIdField;
	@FXML private Label messageLabel;
	@FXML private Button assignButton;

	private ConnectionManager cm;
	private DoctorManager dm;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		cm = new ConnectionManager();
		dm = new DoctorManager(cm.getConnection());
	}

	@FXML
	private void handleAssignDoctorToHospital() {
		try {
			int doctorId = Integer.parseInt(doctorIdField.getText());
			int hospitalId = Integer.parseInt(hospitalIdField.getText());
			dm.assignDoctorToHospital(doctorId, hospitalId);
			messageLabel.setText("Doctor assigned to hospital.");
			doctorIdField.clear();
			hospitalIdField.clear();

		} catch (NumberFormatException e) {
			messageLabel.setText("Doctor ID and Hospital ID must be numbers.");
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
