package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import jdbc.ConnectionManager;
import jdbc.PatientManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GetMalePatientsCountController {
	public class MalePatientsCountController {

	    @FXML
	    private Label resultLabel;

	    @FXML
	    private Label messageLabel;

	    @FXML
	    private Button calculateButton;

	    @FXML
	    private Button backButton;

	    private ConnectionManager cm;
	    private PatientManager pm;

	    @FXML
	    private void initialize() {
	        messageLabel.setText("");
	        resultLabel.setText("-");

	        cm = new ConnectionManager();
	        pm = new PatientManager(cm.getConnection());
	    }

	    @FXML
	    private void handleCalculateMaleCount() {
	        try {
	            int maleCount = pm.getMalePatientsCount();

	            resultLabel.setText(String.valueOf(maleCount));
	            messageLabel.setText("Male patients count calculated successfully.");

	        } catch (Exception e) {
	            resultLabel.setText("-");
	            messageLabel.setText("Error calculating male patients count.");
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

	            Stage stage = (Stage) backButton.getScene().getWindow();
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

}
