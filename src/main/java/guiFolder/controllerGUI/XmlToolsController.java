package guiFolder.controllerGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import Pojos.Patients;
import Pojos.Trial;
import jdbc.ConnectionManager;
import jdbc.DescriptionManager;
import jdbc.DoctorManager;
import jdbc.HospitalManager;
import jdbc.HospitalTrialManager;
import jdbc.PatientManager;
import jdbc.TrialManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import xml.ClinicalTrialsXMLDataBase;
import xml.XmlManager;

public class XmlToolsController {

	@FXML private TextField patientIdField;
	@FXML private TextField trialIdField;
	@FXML private TextField importPathField;
	@FXML private TextArea outputArea;
	@FXML private Label messageLabel;
	@FXML private Button backButton;

	private ConnectionManager cm;
	private TrialManager tm;
	private PatientManager pm;
	private DoctorManager dm;
	private HospitalManager hm;
	private DescriptionManager dpm;
	private HospitalTrialManager htm;
	private XmlManager xmlManager;

	@FXML
	private void initialize() {
		messageLabel.setText("");
		outputArea.setEditable(false);
		outputArea.setWrapText(true);

		cm = new ConnectionManager();
		tm = new TrialManager(cm);
		pm = new PatientManager(cm.getConnection());
		dm = new DoctorManager(cm.getConnection());
		hm = new HospitalManager(cm.getConnection());
		dpm = new DescriptionManager(cm.getConnection());
		htm = new HospitalTrialManager(cm.getConnection());
		xmlManager = new XmlManager();
	}

	@FXML
	private void handleExportDatabase() {
		ClinicalTrialsXMLDataBase database = new ClinicalTrialsXMLDataBase();
		database.setPatients(new ArrayList<>(pm.getAllPatients()));
		database.setTrials(tm.getAllTrials());
		database.setDescriptions(dpm.showAllDescriptions());
		database.setDoctors(dm.showAllDoctors());
		database.setHospitals(hm.getAllHospitals());
		database.setHospitalTrials(htm.showAllHospitalTrials());

		xmlManager.marshalDatabase(database, "xmlFiles/clinical_trials_database.xml");
		messageLabel.setText("Database exported to xmlFiles/clinical_trials_database.xml");
	}

	@FXML
	private void handleImportDatabase() {
		ClinicalTrialsXMLDataBase database = xmlManager.unmarshalDatabase("xmlFiles/clinical_trials_database.xml");

		if (database == null) {
			outputArea.clear();
			messageLabel.setText("Database XML could not be imported.");
			return;
		}

		outputArea.setText("Doctors: " + database.getDoctors() + "\n\n"
				+ "Hospitals: " + database.getHospitals() + "\n\n"
				+ "Patients: " + database.getPatients() + "\n\n"
				+ "Descriptions: " + database.getDescriptions() + "\n\n"
				+ "Trials: " + database.getTrials() + "\n\n"
				+ "Hospital-Trial relations: " + database.getHospitalTrials());
		messageLabel.setText("Database XML imported and shown.");
	}

	@FXML
	private void handleExportPatient() {
		try {
			int patientId = Integer.parseInt(patientIdField.getText());
			Patients patient = pm.getPatientById(patientId);

			if (patient == null) {
				messageLabel.setText("Patient not found.");
				return;
			}

			String filePath = "xmlFiles/patient_" + patientId + ".xml";
			xmlManager.marshalPatient(patient, filePath);
			messageLabel.setText("Patient exported to " + filePath);

		} catch (NumberFormatException e) {
			messageLabel.setText("Patient ID must be a number.");
		}
	}

	@FXML
	private void handleImportPatient() {
		String filePath = importPathField.getText();

		if (filePath.isEmpty()) {
			messageLabel.setText("Please enter an XML file path.");
			return;
		}

		Patients patient = xmlManager.unmarshalPatient(filePath);
		outputArea.setText(String.valueOf(patient));
		messageLabel.setText(patient == null ? "Patient XML could not be imported." : "Patient XML imported.");
	}

	@FXML
	private void handleExportTrial() {
		try {
			int trialId = Integer.parseInt(trialIdField.getText());
			Trial trial = tm.viewTrial(trialId);

			if (trial == null) {
				messageLabel.setText("Trial not found.");
				return;
			}

			String filePath = "xmlFiles/trial_" + trialId + ".xml";
			xmlManager.marshalTrial(trial, filePath);
			messageLabel.setText("Trial exported to " + filePath);

		} catch (NumberFormatException e) {
			messageLabel.setText("Trial ID must be a number.");
		}
	}

	@FXML
	private void handleImportTrial() {
		String filePath = importPathField.getText();

		if (filePath.isEmpty()) {
			messageLabel.setText("Please enter an XML file path.");
			return;
		}

		Trial trial = xmlManager.unmarshalTrial(filePath);
		outputArea.setText(String.valueOf(trial));
		messageLabel.setText(trial == null ? "Trial XML could not be imported." : "Trial XML imported.");
	}

	@FXML
	private void handleTransformToHtml() {
		xmlManager.transformXMLToHTML("xmlFiles/clinical_trials_database.xml",
				"xmlFiles/clinical_trials_database.xsl",
				"xmlFiles/clinical_trials_database.html");
		messageLabel.setText("HTML created in xmlFiles/clinical_trials_database.html");
	}

	@FXML
	private void handleBack() {
		openWindow("/guiFolder/viewGUI/TrialManagerMenu.fxml");
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
