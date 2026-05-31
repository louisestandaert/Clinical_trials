package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import jdbc.ConnectionManager;
import jdbc.HospitalManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddHospitalController {

	@FXML private TextField hospitalIdField;
	@FXML private TextField hospitalNameField;
	@FXML private TextField hospitalCityField;
	@FXML private Label messageLabel;
	@FXML private Button addHospitalButton;

	private ConnectionManager cm;
	private HospitalManager hm;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		cm = new ConnectionManager();
		hm = new HospitalManager(cm.getConnection());
	}

	@FXML
	private void handleAddHospital() {
		try {
			int hospitalId = Integer.parseInt(hospitalIdField.getText());
			String hospitalName = hospitalNameField.getText();
			String hospitalCity = hospitalCityField.getText();

			if (hospitalName.isEmpty() || hospitalCity.isEmpty()) {
				messageLabel.setText("Please complete all fields.");
				return;
			}

			hm.insertHospital(hospitalId, hospitalName, hospitalCity);
			messageLabel.setText("Hospital added successfully.");
			hospitalIdField.clear();
			hospitalNameField.clear();
			hospitalCityField.clear();

		} catch (NumberFormatException e) {
			messageLabel.setText("Hospital ID must be a number.");
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
			Stage stage = (Stage) addHospitalButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			messageLabel.setText("Error opening window.");
			e.printStackTrace();
		}
	}
}
