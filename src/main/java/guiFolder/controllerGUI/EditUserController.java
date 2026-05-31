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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditUserController {

	@FXML private TextField usernameField;
	@FXML private PasswordField currentPasswordField;
	@FXML private PasswordField newPasswordField;
	@FXML private TextField confirmDeleteField;
	@FXML private TextArea userInfoArea;
	@FXML private Label messageLabel;
	@FXML private Button updatePasswordButton;
	@FXML private Button backButton;

	private JPA_manager jpaManager = new JPA_manager();

	@FXML
	private void initialize() {
		messageLabel.setText("");
		userInfoArea.setEditable(false);
		userInfoArea.setWrapText(true);
	}

	@FXML
	private void handleUpdatePassword() {
		String username = usernameField.getText();
		String currentPassword = currentPasswordField.getText();
		String newPassword = newPasswordField.getText();

		if (username.isEmpty() || currentPassword.isEmpty() || newPassword.isEmpty()) {
			messageLabel.setText("Please complete username, current password and new password.");
			return;
		}

		try {
			jpaManager.login(username, currentPassword);
			jpaManager.updatePassword(username, newPassword);
			messageLabel.setText("Password updated successfully.");
			currentPasswordField.clear();
			newPasswordField.clear();
		} catch (Exception e) {
			messageLabel.setText("Username or current password is incorrect.");
		}
	}

	@FXML
	private void handleDeleteUser() {
		String username = usernameField.getText();
		String currentPassword = currentPasswordField.getText();
		String confirmation = confirmDeleteField.getText();

		if (username.isEmpty() || currentPassword.isEmpty()) {
			messageLabel.setText("Please enter username and password first.");
			return;
		}

		if (!confirmation.equalsIgnoreCase("yes")) {
			messageLabel.setText("Type yes in the confirmation field to delete the user.");
			return;
		}

		try {
			jpaManager.login(username, currentPassword);
			jpaManager.deleteUser(username);
			messageLabel.setText("User deleted successfully.");
			usernameField.clear();
			currentPasswordField.clear();
			newPasswordField.clear();
			confirmDeleteField.clear();
			userInfoArea.clear();
		} catch (Exception e) {
			messageLabel.setText("Username or password is incorrect.");
		}
	}

	@FXML
	private void handleViewUser() {
		String username = usernameField.getText();
		String currentPassword = currentPasswordField.getText();

		if (username.isEmpty() || currentPassword.isEmpty()) {
			messageLabel.setText("Please enter username and password first.");
			return;
		}

		try {
			jpaManager.login(username, currentPassword);
			User user = jpaManager.findUserByUsername(username);

			if (user == null) {
				userInfoArea.clear();
				messageLabel.setText("User not found.");
				return;
			}

			String role = "No role";
			if (user.getRole() != null) {
				role = user.getRole().getRole();
			}

			userInfoArea.setText("Username: " + user.getUsername() + "\nRole: " + role);
			messageLabel.setText("User loaded successfully.");

		} catch (Exception e) {
			userInfoArea.clear();
			messageLabel.setText("Username or password is incorrect.");
		}
	}

	@FXML
	private void handleBack() {
		openWindow("/guiFolder/viewGUI/Welcome.fxml");
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
