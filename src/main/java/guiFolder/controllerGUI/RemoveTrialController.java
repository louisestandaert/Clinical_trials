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

public class RemoveTrialController {

	@FXML private TextField trialIdField;
	@FXML private Label messageLabel;
	@FXML private Button removeButton;
	@FXML private Button backButton;

	private ConnectionManager cm;
	private TrialManager tm;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		cm = new ConnectionManager();
		tm = new TrialManager(cm);
	}

	@FXML
	private void handleRemoveTrial() {
		try {
			int trialId = Integer.parseInt(trialIdField.getText());
			boolean removed = tm.removeTrial(trialId);

			if (removed) {
				messageLabel.setText("Trial removed successfully.");
				trialIdField.clear();
			} else {
				messageLabel.setText("Trial could not be removed.");
			}

		} catch (NumberFormatException e) {
			messageLabel.setText("Trial ID must be a number.");
		} catch (Exception e) {
			messageLabel.setText("Error removing trial.");
			e.printStackTrace();
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
