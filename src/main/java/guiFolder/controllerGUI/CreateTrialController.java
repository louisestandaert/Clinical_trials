package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

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

public class CreateTrialController {

	@FXML private TextField trialIdField;
	@FXML private TextField trialNameField;
	@FXML private TextField startingDateField;
	@FXML private TextField durationDaysField;
	@FXML private TextField budgetField;
	@FXML private TextField targetPatientsField;
	@FXML private Label messageLabel;
	@FXML private Button createTrialButton;

	private ConnectionManager cm;
	private TrialManager tm;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		cm = new ConnectionManager();
		tm = new TrialManager(cm);
	}

	@FXML
	private void handleCreateTrial() {
		try {
			int trialId = Integer.parseInt(trialIdField.getText());
			String trialName = trialNameField.getText();
			LocalDate startingDate = LocalDate.parse(startingDateField.getText());
			int durationDays = Integer.parseInt(durationDaysField.getText());
			double budget = Double.parseDouble(budgetField.getText());
			int targetPatients = Integer.parseInt(targetPatientsField.getText());

			if (trialName.isEmpty()) {
				messageLabel.setText("Please complete the trial name.");
				return;
			}

			boolean created = tm.addTrial(trialId, trialName, startingDate, durationDays, budget, targetPatients);

			if (created) {
				messageLabel.setText("Trial created successfully.");
				trialIdField.clear();
				trialNameField.clear();
				startingDateField.clear();
				durationDaysField.clear();
				budgetField.clear();
				targetPatientsField.clear();
			} else {
				messageLabel.setText("Trial could not be created.");
			}

		} catch (NumberFormatException e) {
			messageLabel.setText("IDs, duration, budget and target patients must be numbers.");
		} catch (Exception e) {
			messageLabel.setText("Date must be YYYY-MM-DD.");
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
			Stage stage = (Stage) createTrialButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			messageLabel.setText("Error opening window.");
			e.printStackTrace();
		}
	}
}
