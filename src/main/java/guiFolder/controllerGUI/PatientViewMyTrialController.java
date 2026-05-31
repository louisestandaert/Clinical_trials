package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import Pojos.Patients;
import Pojos.Trial;
import Pojos.User;
import jdbc.ConnectionManager;
import jdbc.PatientManager;
import jdbc.TrialManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PatientViewMyTrialController {

    @FXML
    private TextArea trialArea;

    @FXML
    private Label messageLabel;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    private ConnectionManager cm;
    private PatientManager pm;
    private TrialManager tm;

    @FXML
    private void initialize() {
        messageLabel.setText("");
        trialArea.setEditable(false);
        trialArea.setWrapText(true);

        cm = new ConnectionManager();
        pm = new PatientManager(cm.getConnection());
        tm = new TrialManager(cm);

        loadMyTrial();
    }

    @FXML
    private void handleRefreshTrial() {
        loadMyTrial();
    }

    private void loadMyTrial() {
        User currentUser = Sesion.getCurrentUser();

        if (currentUser == null) {
            trialArea.clear();
            messageLabel.setText("No active user session.");
            return;
        }

        Patients patient = pm.getPatientByName(currentUser.getUsername());

        if (patient == null) {
            trialArea.clear();
            messageLabel.setText("No patient found for this user.");
            return;
        }

        Trial trial = tm.viewTrial(patient.getTrialId());

        if (trial == null) {
            trialArea.setText(patient.toString());
            messageLabel.setText("Patient found, but no trial details were found.");
            return;
        }

        trialArea.setText("Patient:\n" + patient + "\nTrial:\n" + trial);
        messageLabel.setText("Trial loaded successfully.");
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
