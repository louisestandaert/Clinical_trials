package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import jdbc.ConnectionManager;
import jdbc.HospitalTrialManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveHospitalFromTrialController {

	@FXML private TextField hospitalIdField;
	@FXML private TextField trialIdField;
	@FXML private Label messageLabel;
	@FXML private Button removeButton;

	private ConnectionManager cm;
	private HospitalTrialManager htm;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		cm = new ConnectionManager();
		htm = new HospitalTrialManager(cm.getConnection());
	}

	@FXML
	private void handleRemoveHospitalFromTrial() {
		try {
			int hospitalId = Integer.parseInt(hospitalIdField.getText());
			int trialId = Integer.parseInt(trialIdField.getText());
			htm.removeTrialFromHospital(trialId, hospitalId);
			messageLabel.setText("Hospital removed from trial.");
			hospitalIdField.clear();
			trialIdField.clear();

		} catch (NumberFormatException e) {
			messageLabel.setText("Hospital ID and Trial ID must be numbers.");
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
			Stage stage = (Stage) removeButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			messageLabel.setText("Error opening window.");
			e.printStackTrace();
		}
	}
}
