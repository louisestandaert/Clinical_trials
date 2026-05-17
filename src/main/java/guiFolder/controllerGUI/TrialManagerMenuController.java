package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TrialManagerMenuController {
	
	@FXML
	private Button backButton;
	
	//falta definir todos los botones en el scene builder y luego declararlos aqui
	
	@FXML
    private void handleOpenCreateTrial() {
        openWindow("/guiFolder/viewGUI/CreateTrial.fxml");
    }

    @FXML
    private void handleOpenViewAllTrials() {
        openWindow("/guiFolder/viewGUI/TrialManagerViewAllTrials.fxml");
    }

    @FXML
    private void handleOpenViewTrialDetails() {
        openWindow("/guiFolder/viewGUI/TrialManagerViewTrialDetails.fxml");
    }

    @FXML
    private void handleOpenCompareTrialResults() {
        openWindow("/guiFolder/viewGUI/CompareTrialResults.fxml");
    }

    @FXML
    private void handleOpenAverageTrialDuration() {
        openWindow("/guiFolder/viewGUI/AverageTrialDuration.fxml");
    }

    @FXML
    private void handleOpenAverageTrialBudget() {
        openWindow("/guiFolder/viewGUI/AverageTrialBudget.fxml");
    }

    @FXML
    private void handleOpenRemoveTrial() {
        openWindow("/guiFolder/viewGUI/RemoveTrial.fxml");
    }

    @FXML
    private void handleOpenPredictNewPatients() {
        openWindow("/guiFolder/viewGUI/PredictNewPatients.fxml");
    }

    @FXML
    private void handleOpenPositiveResultsCount() {
        openWindow("/guiFolder/viewGUI/PositiveResultsCount.fxml");
    }

  
    
    

    @FXML
    private void handleOpenAddDoctor() {
        openWindow("/guiFolder/viewGUI/AddDoctor.fxml");
    }

    @FXML
    private void handleOpenAssignDoctorToTrial() {
        openWindow("/guiFolder/viewGUI/AssignDoctorToTrial.fxml");
    }

    @FXML
    private void handleOpenAssignDoctorToHospital() {
        openWindow("/guiFolder/viewGUI/AssignDoctorToHospital.fxml");
    }

    @FXML
    private void handleOpenRemoveDoctor() {
        openWindow("/guiFolder/viewGUI/RemoveDoctor.fxml");
    }

  
    
    

    @FXML
    private void handleOpenAddHospital() {
        openWindow("/guiFolder/viewGUI/AddHospital.fxml");
    }

    @FXML
    private void handleOpenAssignHospitalToTrial() {
        openWindow("/guiFolder/viewGUI/AssignHospitalToTrial.fxml");
    }

    @FXML
    private void handleOpenRemoveHospitalFromTrial() {
        openWindow("/guiFolder/viewGUI/RemoveHospitalFromTrial.fxml");
    }


    
    
    

    @FXML
    private void handleBack() {
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
