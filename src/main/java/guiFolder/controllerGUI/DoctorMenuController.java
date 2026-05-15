package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DoctorMenuController {
	@FXML
	private Button doctorMenuButton;

	@FXML
	private void handleOpenAddPatientToTrial() {
		openWindow("/guiFolder/viewGUI/AddPatientToTrial.fxml");
	}
	
	@FXML
	private void handleOpenAddPatientDescription() {
		openWindow("/guiFolder/viewGUI/AddPatientDescription.fxml");
	}
	
	@FXML
	private void handleOpenUpdatePatientDescription() {
		openWindow("/guiFolder/viewGUI/UpdatePatientDescription.fxml");
	}
	
	@FXML
	private void handleOpenRemovePatientDescription() {
		openWindow("/guiFolder/viewGUI/RemovePatientDescription.fxml");
	}
	
	@FXML
	private void handleBack() {
		openWindow("/guiFolder/viewGUI/Login.fxml");
	}

	private void openWindow(String fxmlPath) {
		try {
			URL resource = getClass().getResource(fxmlPath);
			if (resource == null) {
				System.err.println("FXML file not found: " + fxmlPath);
				return;
			}
			Parent root = FXMLLoader.load(resource);
			Stage stage = (Stage) doctorMenuButton.getScene().getWindow();
			Scene scene = new Scene(root, 800, 500);


			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
