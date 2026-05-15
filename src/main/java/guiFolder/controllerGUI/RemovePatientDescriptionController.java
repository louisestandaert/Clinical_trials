package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import Pojos.Description;
import jdbc.ConnectionManager;
import jdbc.DescriptionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemovePatientDescriptionController {
	
	@FXML
    private TextField descriptionIdField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button removeDescriptionButton;

    private ConnectionManager cm;
    private DescriptionManager dpm;

    @FXML
    private void initialize() {
        messageLabel.setText("");
        cm = new ConnectionManager();
        dpm = new DescriptionManager(cm.getConnection());
    }

    @FXML
    private void handleRemoveDescription() {
        try {
            int descriptionId = Integer.parseInt(descriptionIdField.getText());

            Description description = dpm.findDescriptionByID(descriptionId);

            if (description == null) {
                messageLabel.setText("Description does not exist.");
                return;
            }

            dpm.removeDescription(descriptionId);

            messageLabel.setText("Description removed successfully.");

            descriptionIdField.clear();

        } catch (NumberFormatException e) {
            messageLabel.setText("Description ID must be a number.");
        } catch (Exception e) {
            messageLabel.setText("Error removing description.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack() {
        openWindow("/guiFolder/viewGUI/DoctorMenu.fxml");
    }

    private void openWindow(String fxmlPath) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                messageLabel.setText("FXML not found: " + fxmlPath);
                return;
            }

            Parent root = FXMLLoader.load(fxmlUrl);

            Stage stage = (Stage) removeDescriptionButton.getScene().getWindow();
            Scene scene = new Scene(root, 800, 500);

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            messageLabel.setText("Error opening window.");
            e.printStackTrace();
        }
    }

}
