package guiFolder.controllerGUI;
import java.io.IOException;
import java.net.URL;

import Pojos.User;
import jpa.JPA_manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class LoginController {
	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button loginButton;

	@FXML
	private Label messageLabel;

	private JPA_manager jpaManager = new JPA_manager();
	
	@FXML
	private void initialize() {
		messageLabel.setText("");
	}

	@FXML
	private void handleLogin() {
		String username = usernameField.getText();
		String password = passwordField.getText();

		if (username.isEmpty() || password.isEmpty()) {
			messageLabel.setText("Please enter both username and password.");
			return;
		}
		
		boolean correctLogin=jpaManager.checkLogin(username, password);
		
		if (!correctLogin) {
			messageLabel.setText("Incorrect username or password.");
			return;
		}
		
		User user = jpaManager.findUserByUsername(username);
		
		if (user == null) {
			messageLabel.setText("User not found.");
			return;
		}
		String roleName = jpaManager.getRoleByUser(user);
		if (roleName == null) {
			messageLabel.setText("User role not found.");
			return;
		}
		
		switch (roleName) {
		case "Trial Manager":
			openWindow("/guiFolder/viewGUI/TrialManagerMenu.fxml");
			break;
		case "Doctor":
			openWindow("/guiFolder/viewGUI/DoctorMenu.fxml");
			break;
		case "patient":
		case "Patient":
			openWindow("/guiFolder/viewGUI/PatientMenu.fxml");
			break;
		default:
			messageLabel.setText("Role not recognized: ");
			break;
		}
		
	}
	
	@FXML
	private void handleBack() {
		openWindow("/guiFolder/viewGUI/Welcome.fxml");
	}
	

	private void openWindow(String fxmlPath) {
		try {
			URL fxmlURL = getClass().getResource(fxmlPath);
			if (fxmlURL == null) {
				messageLabel.setText("FXML not found:" + fxmlPath);
				System.out.println("FXML not found: " + fxmlPath);
				return;
			}
			Parent root = FXMLLoader.load(fxmlURL);
			Stage stage = (Stage) loginButton.getScene().getWindow();
			Scene scene = new Scene(root,800,500);

			stage.setTitle("Clinical Trials Database");
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			messageLabel.setText("Error loading window: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
