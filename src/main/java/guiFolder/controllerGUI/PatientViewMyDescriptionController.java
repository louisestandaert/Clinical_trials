package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import Pojos.Description;
import Pojos.Patients;
import Pojos.User;
import jdbc.ConnectionManager;
import jdbc.DescriptionManager;
import jdbc.PatientManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PatientViewMyDescriptionController {

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Label messageLabel;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    private ConnectionManager cm;
    private PatientManager pm;
    private DescriptionManager dm;

    @FXML
    private void initialize() {
        messageLabel.setText("");
        descriptionArea.setEditable(false);
        descriptionArea.setWrapText(true);

        cm = new ConnectionManager();
        pm = new PatientManager(cm.getConnection());
        dm = new DescriptionManager(cm.getConnection());

        loadMyDescription();
    }

    @FXML
    private void handleRefreshDescription() {
        loadMyDescription();
    }

    private void loadMyDescription() {
        User currentUser = Sesion.getCurrentUser();

        if (currentUser == null) {
            descriptionArea.clear();
            messageLabel.setText("No active user session.");
            return;
        }

        Patients patient = pm.getPatientByName(currentUser.getUsername());

        if (patient == null) {
            descriptionArea.clear();
            messageLabel.setText("No patient found for this user.");
            return;
        }

        Description description = dm.findDescriptionByPatientId(patient.getPatientsId());

        if (description == null) {
            descriptionArea.setText(patient.toString());
            messageLabel.setText("Patient found, but no description was found.");
            return;
        }

        descriptionArea.setText("Patient:\n" + patient + "\nDescription:\n" + description);
        messageLabel.setText("Description loaded successfully.");
    }

    @FXML
    private void handleBack() {
        openWindow("/guiFolder/viewGUI/PatientMenu.fxml");
    }

    private void openWindow(String fxmlPath) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                messageLabel.setText("FXML not found: " + fxmlPath);
                return;
            }

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
