package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import Pojos.DoctorSpecialty;
import jdbc.ConnectionManager;
import jdbc.DoctorManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddDoctorController {

	@FXML private TextField doctorIdField;
	@FXML private TextField doctorNameField;
	@FXML private TextField doctorGenderField;
	@FXML private ComboBox<DoctorSpecialty> specialtyComboBox;
	@FXML private TextField hospitalIdField;
	@FXML private TextField trialIdField;
	@FXML private Label messageLabel;
	@FXML private Button addDoctorButton;

	private ConnectionManager cm;
	private DoctorManager dm;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		specialtyComboBox.getItems().addAll(DoctorSpecialty.values());
		cm = new ConnectionManager();
		dm = new DoctorManager(cm.getConnection());
	}

	@FXML
	private void handleAddDoctor() {
		try {
			int doctorId = Integer.parseInt(doctorIdField.getText());
			String doctorName = doctorNameField.getText();
			String doctorGender = doctorGenderField.getText();
			DoctorSpecialty specialty = specialtyComboBox.getValue();
			int hospitalId = Integer.parseInt(hospitalIdField.getText());
			int trialId = Integer.parseInt(trialIdField.getText());

			if (doctorName.isEmpty() || doctorGender.isEmpty() || specialty == null) {
				messageLabel.setText("Please complete all fields.");
				return;
			}

			dm.insertDoctor(doctorId, doctorName, doctorGender, specialty, hospitalId, trialId);
			messageLabel.setText("Doctor added successfully.");

			doctorIdField.clear();
			doctorNameField.clear();
			doctorGenderField.clear();
			specialtyComboBox.setValue(null);
			hospitalIdField.clear();
			trialIdField.clear();

		} catch (NumberFormatException e) {
			messageLabel.setText("IDs must be numbers.");
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
			Stage stage = (Stage) addDoctorButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			messageLabel.setText("Error opening window.");
			e.printStackTrace();
		}
	}
}
