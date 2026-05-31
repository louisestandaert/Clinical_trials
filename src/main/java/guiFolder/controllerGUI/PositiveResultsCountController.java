package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import jdbc.ConnectionManager;
import jdbc.PatientManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PositiveResultsCountController {

	@FXML private Label resultLabel;
	@FXML private Label messageLabel;
	@FXML private Button countButton;
	@FXML private Button backButton;

	private ConnectionManager cm;
	private PatientManager pm;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		resultLabel.setText("");
		cm = new ConnectionManager();
		pm = new PatientManager(cm.getConnection());
	}

	@FXML
	private void handleCountPositiveResults() {
		resultLabel.setText("Positive results: " + pm.getPatientCountOfPositiveResults());
		messageLabel.setText("Positive results counted.");
	}

	@FXML
	private void handleBack() {
		openWindow("/guiFolder/viewGUI/TrialManagerMenu.fxml");
	}

	private void openWindow(String fxmlPath) {
		try {
			URL fxmlUrl = getClass().getResource(fxmlPath);
			Parent root = FXMLLoader.load(fxmlUrl);
			Stage stage = (Stage) backButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			messageLabel.setText("Error opening window.");
			e.printStackTrace();
		}
	}
}
