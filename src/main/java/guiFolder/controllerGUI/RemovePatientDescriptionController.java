package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;


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
    private TextField patientNameField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button removeDescriptionButton;
    
    @FXML
    private Button backButton;

    private ConnectionManager cm;
    private DescriptionManager dpm;

    @FXML
    private void initialize() {
        messageLabel.setText("");
        messageLabel.setPrefWidth(400);
        messageLabel.setWrapText(true);
        cm = new ConnectionManager();
        dpm = new DescriptionManager(cm.getConnection());
    }

    @FXML
    private void handleRemoveDescription() {
    	try {
            String patientName = patientNameField.getText();

            if (patientName.isEmpty()) {
                messageLabel.setText("Please enter the patient name.");
                return;
            }

            dpm.removeDescriptionByPatientName(patientName);

            messageLabel.setText("Description removed successfully.");
            patientNameField.clear();

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
            Scene scene = new Scene(root); // 800 500

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            messageLabel.setText("Error opening window.");
            e.printStackTrace();
        }
    }

}
