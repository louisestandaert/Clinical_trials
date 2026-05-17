package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import jpa.JPA_manager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {
	@FXML
	private TextField usernameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private ComboBox<String> roleComboBox;
	
	@FXML
	private Button createUserButton;
	
	@FXML
	private Label messageLabel;
	
	private JPA_manager jpaManager = new JPA_manager();
	
	@FXML
	private void initialize() {
		messageLabel.setText("ready");
		roleComboBox.getItems().add("Trial Manager");
		roleComboBox.getItems().add("Doctor");
		roleComboBox.getItems().add("Patient");
		
		jpaManager.createRole("Trial Manager");
		jpaManager.createRole("Doctor");
		jpaManager.createRole("Patient");
	}
	
	@FXML
	private void handleCreateUser() {
		System.out.println("CREATE USER BUTTON CLICKED");
		String username = usernameField.getText();
		String password = passwordField.getText();
		String role = roleComboBox.getValue();

		if (username.isEmpty() || password.isEmpty() || role == null) {
			messageLabel.setText("Please fill in all fields.");
			return;
		}
		
		if (jpaManager.findUserByUsername(username) != null) {
			messageLabel.setText("Username already exists. Please choose another.");
			return;
		}
		
		jpaManager.createUser(username, password, role);
		messageLabel.setText("User created successfully! You can now log in.");
		
		usernameField.clear();
		passwordField.clear();
		roleComboBox.setValue(null);
	}
	
	@FXML
	private void handleBack() {
		openWindow("/guiFolder/viewGUI/Welcome.fxml");
	}
		
	private void openWindow(String fxmlPath) {
		try {
			URL fxmlURL = getClass().getResource(fxmlPath);
			if (fxmlURL == null) {
				messageLabel.setText("Error: FXML file not found.");
				System.err.println("FXML file not found: " + fxmlPath);
				return;
			}
			Parent root = FXMLLoader.load(fxmlURL);
			Stage stage = (Stage) createUserButton.getScene().getWindow();
			Scene scene = new Scene(root, 800, 500);
			
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
			
		} catch (IOException e) {
			messageLabel.setText("Error loading the window.");
			e.printStackTrace();
		}
	}
	
}
