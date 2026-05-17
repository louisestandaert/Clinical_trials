package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DoctorMenuController {
	
	@FXML
    private Button backButton;

    @FXML
    private void handleOpenAddPatient() {
        openWindow("/guiFolder/viewGUI/AddPatient.fxml");
    }

    @FXML
    private void handleOpenAssignPatientToTrial() {
        openWindow("/guiFolder/viewGUI/AssignPatientToTrial.fxml");
    }

    @FXML
    private void handleOpenViewPatientById() {
        openWindow("/guiFolder/viewGUI/ViewPatientById.fxml");
    }

    @FXML
    private void handleOpenViewAllPatients() {
        openWindow("/guiFolder/viewGUI/ViewAllPatients.fxml");
    }

    @FXML
    private void handleOpenRemovePatient() {
        openWindow("/guiFolder/viewGUI/RemovePatient.fxml");
    }

    @FXML
    private void handleOpenAddPatientDescription() {
        openWindow("/guiFolder/viewGUI/AddPatientDescription.fxml");
    }

    @FXML
    private void handleOpenUpdatePatientDescription() {
        openWindow("/guiFolder/viewGUI/UpdatePatientDescription.fxml");
    }

    @FXML
    private void handleOpenViewPatientDescription() {
        openWindow("/guiFolder/viewGUI/ViewPatientDescriptionById.fxml");
    }
    @FXML
    private void handleOpenViewPatientDescriptionById() {
        openWindow("/guiFolder/viewGUI/ViewPatientDescriptionById.fxml");
    }

    @FXML
    private void handleViewPatientDescription() {
        openWindow("/guiFolder/viewGUI/ViewPatientDescriptionById.fxml");
    }


    @FXML
    private void handleOpenRemovePatientDescription() {
        openWindow("/guiFolder/viewGUI/RemovePatientDescription.fxml");
    }

    @FXML
    private void handleOpenFemalePatientsCount() {
        openWindow("/guiFolder/viewGUI/GetFemalePatientsCount.fxml");
    }

    @FXML
    private void handleOpenMalePatientsCount() {
        openWindow("/guiFolder/viewGUI/GetMalePatientsCount.fxml");
    }

    @FXML
    private void handleOpenPatientsWithSameCause() {
        openWindow("/guiFolder/viewGUI/PatientsWithTheSameCause.fxml");
    }

    @FXML
    private void handleOpenTrialsByHospital() {
        openWindow("/guiFolder/viewGUI/ViewAllTrialsInHospital.fxml");
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
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.out.println("Error opening window: " + fxmlPath);
            e.printStackTrace();
        }
    }
}