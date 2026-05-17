package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class PatientMenuController {
	
	@FXML
    private Button viewMyTrialButton;

    @FXML
    private Button viewMyDescriptionButton;

    @FXML
    private Button backButton;

    @FXML
    private void handleOpenViewMyTrial() {
        openWindow("/guiFolder/viewGUI/PatientViewMyTrial.fxml");
    }

    @FXML
    private void handleOpenViewMyDescription() {
        openWindow("/guiFolder/viewGUI/PatientViewMyDescription.fxml");
    }

    @FXML
    private void handleBack() {
        Sesion.clearSession();
        openWindow("/guiFolder/viewGUI/Login.fxml");
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
