package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GuestMenuController {
	
	@FXML
	private Button viewAllTrialsButton;
	
	@FXML
	private Button viewTrialDetailsButton;

	@FXML
	private Button backButton;

	@FXML
	private void handleOpenViewAllTrials() {
		openWindow("/guiFolder/viewGUI/GuestViewAllTrials.fxml");
	}

	@FXML
	private void handleOpenViewTrialDetails() {
		openWindow("/guiFolder/viewGUI/GuestViewTrialDetails.fxml");
	}
	
	@FXML
	private void handleBack() {
		openWindow("/guiFolder/viewGUI/Welcome.fxml");
	}

	private void openWindow(String fxmlPath) {
		try {
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                System.out.println("FXML not found: " + fxmlPath);
                return;
            }

            Parent root = FXMLLoader.load(fxmlUrl);

            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root, 800, 500);

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.out.println("Error opening window: " + fxmlPath);
            e.printStackTrace();
        }
    }

}
