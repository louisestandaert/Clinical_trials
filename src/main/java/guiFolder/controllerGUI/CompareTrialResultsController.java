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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CompareTrialResultsController {

	@FXML private TextField trialIdField;
	@FXML private TextArea resultArea;
	@FXML private Label messageLabel;
	@FXML private Button compareButton;
	@FXML private Button backButton;

	private ConnectionManager cm;
	private TrialManager tm;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		resultArea.setEditable(false);
		resultArea.setWrapText(true);
		cm = new ConnectionManager();
		tm = new TrialManager(cm);
	}

	@FXML
	private void handleCompareResults() {
		try {
			int trialId = Integer.parseInt(trialIdField.getText());
			resultArea.setText(tm.resultsComparation(trialId));
			messageLabel.setText("Results compared successfully.");
		} catch (NumberFormatException e) {
			resultArea.clear();
			messageLabel.setText("Trial ID must be a number.");
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
