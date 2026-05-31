package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import Pojos.Trial;
import jdbc.ConnectionManager;
import jdbc.TrialManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TrialManagerViewAllTrialsController {

	@FXML private TextArea trialsArea;
	@FXML private Label messageLabel;
	@FXML private Button refreshButton;
	@FXML private Button backButton;

	private ConnectionManager cm;
	private TrialManager tm;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		trialsArea.setEditable(false);
		trialsArea.setWrapText(true);
		cm = new ConnectionManager();
		tm = new TrialManager(cm);
		loadTrials();
	}

	@FXML
	private void handleRefreshTrials() {
		loadTrials();
	}

	private void loadTrials() {
		List<Trial> trials = tm.getAllTrials();

		if (trials == null || trials.isEmpty()) {
			trialsArea.setText("");
			messageLabel.setText("No trials found.");
			return;
		}

		StringBuilder text = new StringBuilder();
		for (Trial trial : trials) {
			text.append(trial.toString());
			text.append("-----------------------------\n");
		}

		trialsArea.setText(text.toString());
		messageLabel.setText("Trials loaded successfully.");
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
