package guiFolder.controllerGUI;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WelcomeController {
	@FXML
	private Button loginButton;
	
	@FXML
	private void handleLogin() {
		openWindow("/guiFolder/viewGUI/Login.fxml");
	}
	
	@FXML
	private void handleGuest() {
		openWindow("/guiFolder/viewGUI/Guest.fxml");
	}
	
	@FXML
	private void handleSingUp() {
		openWindow("/guiFolder/viewGUI/SignUp.fxml");
	}
	
	@FXML
	private void handleExit() {
		Stage stage = (Stage) loginButton.getScene().getWindow();
		stage.close();
	}
	
	private void openWindow(String fxmlPath) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
			Stage stage = (Stage) loginButton.getScene().getWindow(); 
			Scene scene = new Scene(root);

			stage.setTitle("Clinical Trials Database");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
