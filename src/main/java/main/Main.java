package main;

import java.time.LocalDate;
import jdbc.ConnectionManager;
import jdbc.HospitalManager;
import jdbc.PatientManager;
import jdbc.DescriptionManager;
import jdbc.DoctorManager;
import jdbc.TrialManager;
import jpa.JPA_manager;
import xml.XmlManager;
import java.util.List;
import jdbc.HospitalTrialManager;
import java.util.Scanner;
import Pojos.DoctorSpecialty;
import Pojos.Trial;
import Pojos.User;
import Pojos.HospitalTrial;
import Pojos.Patients;
import Pojos.Description;
import Pojos.Hospitals;
import Pojos.Doctors;
import xml.XmlManager;
import xml.ClinicalTrialsXMLDataBase;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {

		int choice;
		Scanner scanner = new Scanner(System.in);
		JPA_manager jpaManager = new JPA_manager();
		ConnectionManager cm = new ConnectionManager();
		TrialManager tm = new TrialManager(cm);
		DoctorManager dm = new DoctorManager(cm.getConnection());
		DescriptionManager dpm = new DescriptionManager(cm.getConnection());
		PatientManager pm = new PatientManager(cm.getConnection());
		HospitalManager hm = new HospitalManager(cm.getConnection());
		HospitalTrialManager htm = new HospitalTrialManager(cm.getConnection());

		do {
			System.out.println();
			System.out.println("=========================================");
			System.out.println("Welcome to the Clinical Trials Database!");
			System.out.println("=========================================");
			System.out.println("Please select an option:");
			System.out.println("1. Login");
			System.out.println("2. Enter as Guest");
			System.out.println("3. Sign up");
			System.out.println("4. Edit User");
			System.out.println("5. Exit");

			choice = scanner.nextInt();
			scanner.nextLine();
			String username = null;
			String password = null;
			String role = null;

			switch (choice) {
			case 1:
				System.out.println("Logging in...");

				System.out.println("Enter username: ");
				username = scanner.nextLine();

				System.out.println("Enter password: ");
				password = scanner.nextLine();

				try {
					jpaManager.login(username, password);
				} catch (Exception e) {
					System.out.println("Login failed inside the catch: " + e.getMessage());
					break;

				}

				System.out.println("Retrieving user role...");

				role = jpaManager.getRoleByUser(jpaManager.findUserByUsername(username));

				switch (role) {

				case "Trial Manager":
					System.out.println("Welcome, Trial Manager! You have full access to the system.");
					trialManagerFunctions(scanner, tm, pm, dm, hm, htm, dpm);

					break;
				case "Doctor":
					System.out.println("Welcome, doctor! You can manage your trials and patients.");
					doctorFunctions(scanner, dm, dpm, tm, pm, htm);
					break;
				case "Patient":
					System.out.println("Welcome, patient! You can view your trial information and results.");
					patientFunctions(scanner, pm, jpaManager.findUserByUsername(username));
					break;
				default:
					System.out.println("Role not recognized. Please try again.");
					break;
				}
				break;
			case 2:
				System.out.println("Entering as Guest...");
				guestFunctions(scanner, tm);
				break;
			case 3:
				System.out.println("Signing up...");

				//in case the roles are not created yet.
				// jpaManager.createRole("Trial Manager");
				// jpaManager.createRole("Doctor");
				// jpaManager.createRole("Patient");
				// jpaManager.createRole("Guest");

				System.out.println("Enter username: ");
				username = scanner.nextLine();

				System.out.println("Enter password: ");
				password = scanner.nextLine();

				System.out.println("Select a role for the user:");
				role = scanner.nextLine();

				if (role == null || (!role.equalsIgnoreCase("Trial Manager") && !role.equalsIgnoreCase("Doctor")
						&& !role.equalsIgnoreCase("Patient") && !role.equalsIgnoreCase("Guest"))) {
					System.out.println(
							"Invalid role selected. Please choose from: Trial Manager, Doctor, Patient, Guest.");
					break;
				}

				// the security measures to ensure not everyone can create a Trial Manager or Doctor user.
				if (role.equalsIgnoreCase("Trial Manager")) {
					System.out.println("Please enter the admin password to create a Trial Manager user:");
					String adminPassword = scanner.nextLine();
					if (!adminPassword.equals("admin123")) {
						System.out.println("Incorrect admin password. Only admins can create Trial Manager users.");
						break;
					}

				}

				if (role.equalsIgnoreCase("Doctor")) {
					System.out.println("Please enter the admin password to create a Doctor user:");
					String adminPassword = scanner.nextLine();
					if (!adminPassword.equals("admin123")) {
						System.out.println("Incorrect admin password. Only admins can create Doctor users.");
						break;
					}

				}

				jpaManager.createUser(username, password, role);

				System.out.println("User created successfully. You can now log in.");

				break;
			case 4:
				editUserFunctions(scanner, jpaManager);
				break;
			case 5:
				System.out.println("Exiting the application. Goodbye!");
				break;
			default:
				System.out.println("Invalid choice.");
				break;
			}
		} while (choice != 5);

	}

	private static void editUserFunctions(Scanner scanner, JPA_manager jpaManager) {
		
		int editChoice;
		do {
		
		System.out.println("Editing user...");
		System.out.println("chose an opcion: ");
		System.out.println("1. Update password");
		System.out.println("2. Delete user");
		System.out.println("3. View my users");
		System.out.println("4. Exit");
		editChoice = scanner.nextInt();
		scanner.nextLine();
		switch (editChoice) {
		case 1:
			System.out.println("Enter your username:");
			String usernameToUpdate = scanner.nextLine();
			System.out.println("Enter your current password:");
			String currentPassword = scanner.nextLine();
			try {
				jpaManager.login(usernameToUpdate, currentPassword);
				System.out.println("Enter your new password:");
				String newPassword = scanner.nextLine();
				jpaManager.updatePassword(usernameToUpdate, newPassword);
				System.out.println("Password updated successfully.");
			} catch (Exception e) {
				System.out.println("Error updating password: " + e.getMessage());
			}
			break;
		case 2:
			System.out.println("please login first:");
			System.out.println("Enter your username:");
			String usernameToDelete = scanner.nextLine();
			System.out.println("Enter your password:");
			String passwordToDelete = scanner.nextLine();
			try {
				jpaManager.login(usernameToDelete, passwordToDelete);
			} catch (Exception e) {
				System.out.println("Login failed: " + e.getMessage());
				break;
			}
			System.out.println("Are you sure you want to delete your user? This action cannot be undone. (yes/no)");
			boolean confirmDeletion = scanner.nextLine().equalsIgnoreCase("yes");
			if (!confirmDeletion) {
				System.out.println("User deletion cancelled.");
				break;
			}

			jpaManager.deleteUser(usernameToDelete);
			System.out.println("User deleted successfully.");
			break;
		case 3:
			System.out.println("Viewing my user...");

			System.out.println("please login first");
			System.out.println("Enter your username:");
			String usernameToView = scanner.nextLine();
			System.out.println("Enter your password:");
			String passwordToView = scanner.nextLine();
			try {
				jpaManager.login(usernameToView, passwordToView);
				User user = jpaManager.findUserByUsername(usernameToView);
				if (user != null) {
					System.out.println("Username: " + user.getUsername());
					System.out.println("Stored password: " + user.getPassword());
					if (user.getRole() != null) {
						System.out.println("Role: " + user.getRole().getRole());
					} else {
						System.out.println("Role: null");
					}
				} else {
					System.out.println("User not found.");
				}
			} catch (Exception e) {
				System.out.println("Login failed: " + e.getMessage());
			}
			break;
		case 4:
			System.out.println("Exiting user edit...");
			break;
		default:
			System.out.println("Invalid choice.");
			break;
		}
		
		} while (editChoice != 4);

	}

	private static void trialManagerFunctions(Scanner scanner, TrialManager tm, PatientManager pm, DoctorManager dm,
			HospitalManager hm, HospitalTrialManager htm, DescriptionManager dpm) {

		int trialChoice;
		do {
			System.out.println("Please choose an option:");

			System.out.println("Handle trials:");
			System.out.println("1. Create a new trial");
			System.out.println("2. View all trials");
			System.out.println("3. View trial details");
			System.out.println("4. Compare trial results");
			System.out.println("5. Calculate average trial duration");
			System.out.println("6. Calculate average trial budget");
			System.out.println("7. Remove trial");
			System.out.println("8. Predict how many new patients are required");
			System.out.println("9. Get the number of positive results from patients");

			System.out.println("Handle doctors:");
			System.out.println("10. Add doctor");
			System.out.println("11. Assign doctor to trial");
			System.out.println("12. Assign doctor to hospital");
			System.out.println("13. Remove doctor");

			System.out.println("Handle hospitals:");
			System.out.println("14. Add hospital");
			System.out.println("15. Assign hospital to trial");
			System.out.println("16. Remove hospital from trial");

			System.out.println("17. Insert/Export XML");

			System.out.println("00. Exit - return to login");

			trialChoice = scanner.nextInt();

			switch (trialChoice) {
			case 1:
				System.out.println("Creating a new trial...");
				System.out.println("Please enter trial details:");
				System.out.println("Trial ID:");
				int trialId = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Trial Name:");
				String trialName = scanner.nextLine();
				System.out.println("Starting Date (YYYY-MM-DD):");
				LocalDate startingDateStr = LocalDate.parse(scanner.nextLine());
				System.out.println("Duration (days):");
				int durationDays = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Budget:");
				double budget = scanner.nextDouble();
				scanner.nextLine();
				System.out.println("Target Patients:");
				int targetPatients = scanner.nextInt();
				scanner.nextLine();

				tm.addTrial(trialId, trialName, startingDateStr, durationDays, budget, targetPatients);
				break;
			case 2:
				System.out.println("Viewing all trials");
				System.out.println("All trials in the system:" + tm.getAllTrials());
				break;
			case 3:
				System.out.println("Viewing trial details...");
				System.out.println("Enter trial ID:");
				int viewTrialId = scanner.nextInt();
				scanner.nextLine();
				System.out.println(tm.viewTrial(viewTrialId));
				break;
			case 4: 
				System.out.println("Comparing trial results...");
				System.out.println("Enter trial ID:");
				int compareTrialId = scanner.nextInt();
				scanner.nextLine();
				System.out.println(tm.resultsComparation(compareTrialId));
				break;
			case 5: 
				System.out.println("Calculating average trial duration...");
				System.out.println("The average trial duration is " + tm.calculateAverageDuration());
				break;
			case 6:
				System.out.println("Calculating average trial budget...");
				System.out.println("The average trial budget is " + tm.calculateAverageBudget());
				break;
			case 7: 
				System.out.println("Removing trial...");
				System.out.println("Enter trial ID:");
				int removeTrialId = scanner.nextInt();
				scanner.nextLine();
				try {
					tm.removeTrial(removeTrialId);
				} catch (Exception e) {
					System.out.println("Error while trying to remove the trial: " + e.getMessage());
					break;
				}
				break;
			case 8: 
				System.out.println("Predicting how many new patients are required...");
				System.out.println("Enter trial ID:");
				int predictTrialId = scanner.nextInt();
				scanner.nextLine();
				System.out.println("There are " + tm.predictHowManyNewPatientsRequired(predictTrialId)
						+ " New patients required for the trial");
				break;
			case 9: 
				System.out.println("Getting the number of positive results from patients...");
				System.out.println(pm.getPatientCountOfPositiveResults());
				break;
			case 10: 
				System.out.println("Adding doctor...");
				System.out.println("Enter doctor ID:");
				int doctorIdToAdd = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Enter doctor name:");
				String doctorName = scanner.nextLine();
				System.out.println("Enter doctor gender:");
				String doctorGender = scanner.nextLine();
				System.out.println(
						"Enter doctor specialty (CARDIOLOGY, NEUROLOGY, ONCOLOGY, PEDIATRICS, GENERAL_PRACTICE):");
				String specialtyInput = scanner.nextLine();
				DoctorSpecialty doctorSpecialty;
				try {
					doctorSpecialty = DoctorSpecialty.valueOf(specialtyInput.toUpperCase());
				} catch (IllegalArgumentException e) {
					System.out.println(
							"Invalid specialty. Please enter one of the following: CARDIOLOGY, NEUROLOGY, ONCOLOGY, PEDIATRICS, GENERAL_PRACTICE.");
					break;
				}

				System.out.println("Which trial do you want to assign the doctor to? (enter ID):");
				int trialIdForDoctor = scanner.nextInt();

				System.out.println("Which hospital do you want to assign the doctor to? (enter ID):");
				int hospitalIdForDoctor = scanner.nextInt();

				dm.insertDoctor(doctorIdToAdd, doctorName, doctorGender, doctorSpecialty, trialIdForDoctor,
						hospitalIdForDoctor);
				break;
			case 11: 
				System.out.println("Assigning doctor to trial...");
				System.out.println("Enter doctor ID:");
				int doctorId = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Enter trial ID:");
				int assignTrialId = scanner.nextInt();
				scanner.nextLine();
				tm.assignDoctorToTrial(doctorId, assignTrialId);
				System.out.println("Doctor assigned to trial correctly.");
				break;
			case 12: 
				System.out.println("Assigning doctor to hospital...");
				System.out.println("Enter doctor ID:");
				int doctorIdForHospital = scanner.nextInt();
				System.out.println("Enter hospital ID:");
				int hospitalId = scanner.nextInt();
				dm.assignDoctorToHospital(doctorIdForHospital, hospitalId);
				System.out.println("Doctor assigned to hospital correctly.");
				break;
			case 13: 
				System.out.println("Removing doctor...");
				System.out.println("Enter doctor ID:");
				int doctorIdToRemove = scanner.nextInt();
				dm.removeDoctor(doctorIdToRemove);
				break;
			case 14: 
				System.out.println("Adding hospital...");
				System.out.println("Enter hospital ID:");
				int hospitalIdToAdd = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Enter hospital name:");
				String hospitalName = scanner.nextLine();
				System.out.println("Enter hospital location:");
				String hospitalLocation = scanner.nextLine();
				hm.insertHospital(hospitalIdToAdd, hospitalName, hospitalLocation);
				break;
			case 15: 
				System.out.println("Assigning hospital to trial...");
				System.out.println("Enter hospital ID:");
				int hospitalIdForTrial = scanner.nextInt();
				System.out.println("Enter trial ID:");
				int trialIdForHospital = scanner.nextInt();
				htm.assignTrialToHospital(trialIdForHospital, hospitalIdForTrial);
				System.out.println("Hospital assigned to trial correctly.");
				break;
			case 16: 
				System.out.println("Removing hospital from trial...");
				System.out.println("Enter hospital ID:");
				int hospitalIdToRemoveFromTrial = scanner.nextInt();
				System.out.println("Enter trial ID:");
				int trialIdToRemoveHospital = scanner.nextInt();
				htm.removeTrialFromHospital(trialIdToRemoveHospital, hospitalIdToRemoveFromTrial);
				break;
			case 17: 
				System.out.println("Ready to export/import XML:");
				xmlFunctions(scanner, tm, pm, dm, hm, dpm, htm);
				break;
			case 00:
				System.out.println("Exiting to login...");
				break;
			default:
				System.out.println("Invalid choice.");
				break;
			}

		} while (trialChoice != 00);

	}

	private static void doctorFunctions(Scanner scanner, DoctorManager dm, DescriptionManager dpm, TrialManager tm,
			PatientManager pm, HospitalTrialManager htm) {

		int doctorChoice;

		do {

			System.out.println("Please choose an option:");
			System.out.println("1. Add patient");
			System.out.println("2. Assign patient to existing trial");
			System.out.println("3. View patient by id");
			System.out.println("4. View all patients");

			System.out.println("5. Add patient description");
			System.out.println("6. Update patient description");
			System.out.println("7. View patient description by patient id");
			System.out.println("8. Remove patient description");

			System.out.println("9. Get female count of patients");
			System.out.println("10. Get male count of patients");
			System.out.println("11. Get list of patients with the same cause");
			System.out.println("12. View all trials in given hospital");
			System.out.println("13. Remove patient");

			System.out.println("00. Exit - return to login");
			doctorChoice = scanner.nextInt();
			scanner.nextLine();

			switch (doctorChoice) {
			case 1: // add patient
				System.out.println("Adding patient...");

				System.out.println("Enter patient ID:");
				int patientIdToAdd = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Enter patient name:");
				String patientName = scanner.nextLine();

				System.out.println("Enter patient test result (Positive/Negative):");
				String testResult = scanner.nextLine();

				System.out.println("Enter trial ID:");
				int trialIdForPatient = scanner.nextInt();

				System.out.println("Enter hospital ID:");
				int hospitalIdForPatient = scanner.nextInt();

				System.out.println("Enter description ID:");
				int descriptionIdForPatient = scanner.nextInt();
				scanner.nextLine();

				pm.insertPatient(patientIdToAdd, patientName, testResult, trialIdForPatient, hospitalIdForPatient,
						descriptionIdForPatient);
				break;
			case 2: // assign patient to existing trial
				System.out.println("Adding patient to existing trial...");
				System.out.println("Enter patient ID:");
				int patientId = scanner.nextInt();
				System.out.println("Enter trial ID:");
				int trialId = scanner.nextInt();
				scanner.nextLine();
				boolean assigned = tm.enrollPatientInTrial(patientId, trialId);

				if (assigned) {
					System.out.println("Patient assigned to trial successfully.");
				} else {
					System.out.println("Failed to assign patient to trial. Please check the IDs and try again.");
				}
				break;
			case 3: // view patient by id
				System.out.println("Viewing patient by ID...");
				System.out.println("Enter patient ID:");
				int patientIdToView = scanner.nextInt();
				scanner.nextLine();
				System.out.println(pm.getPatientById(patientIdToView));
				break;
			case 4: // view all patients
				System.out.println("Viewing all patients...");
				System.out.println("All patients in the system:");
				System.out.println(pm.getAllPatients());
				break;
			case 5: // add patient description
				System.out.println("Adding patient description...");
				System.out.println("Enter patient ID:");
				int patientIdForDescription = scanner.nextInt();

				System.out.println("Please add the description of the patient:");
				System.out.println("Description ID:");
				int descriptionId = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Gender:");
				String gender = scanner.nextLine();

				System.out.println("Cause:");
				String cause = scanner.nextLine();

				dpm.insertDescription(descriptionId, gender, cause, patientIdForDescription);
				break;
			case 6: // update patient description
				System.out.println("Updating patient description...");
				System.out.println("Enter patient ID of the description you want to change:");
				int patientIdForNewDescription = scanner.nextInt();

				System.out.println("Please add the description of the patient:");
				System.out.println("Description ID:");
				int newDescriptionId = scanner.nextInt();
				scanner.nextLine();

				System.out.println("Gender:");
				String newGender = scanner.nextLine();

				System.out.println("Cause:");
				String newCause = scanner.nextLine();
				dpm.updateDescription(newDescriptionId, newGender, newCause, patientIdForNewDescription);
				break;
			case 7: // view patient description by patient id
				System.out.println("Viewing patient description by patient ID...");
				System.out.println("Enter patient ID:");
				int patientIdForDescriptionView = scanner.nextInt();
				scanner.nextLine();
				System.out.println(pm.getDescriptionOfPatientById(patientIdForDescriptionView));
				break;
			case 8: // remove patient description
				System.out.println("Removing patient description...");
				System.out.println("Enter patient name of the description you want to remove:");
				String patientNameForDescriptionRemoval = scanner.nextLine();
				dpm.removeDescriptionByPatientName(patientNameForDescriptionRemoval);
				System.out.println("Description removed correctly");
				break;
			case 9: // get female count of patients
				System.out.println("Calculating the number of female patients...");
				System.out.println(pm.getFemalePatientsCount());
				break;
			case 10: // get male count of patients
				System.out.println("Calculating the number of male patients...");
				System.out.println(pm.getMalePatientsCount());
				break;
			case 11: // get list a patients with the same cause
				System.out.println("Getting list of patients with the same cause...");
				System.out.println("Enter cause:");
				String causeForPatientsList = scanner.nextLine();
				System.out.println(pm.getListOfPatientsWithSameCause(causeForPatientsList));
				break;
			case 12: // view all trials in given hospital
				System.out.println("Viewing all trials in given hospital...");
				System.out.println("Enter hospital ID:");
				int hospitalIdForTrialsView = scanner.nextInt();
				scanner.nextLine();
				System.out.println(htm.findTrialsByHospitalId(hospitalIdForTrialsView));
				break;
			case 13: // remove patient
				System.out.println("Removing patient...");
				System.out.println("Enter patient ID:");
				int patientIdToRemove = scanner.nextInt();
				scanner.nextLine();
				pm.removePatient(patientIdToRemove);
				break;
			case 00:
				System.out.println("Exiting to login...");
				break;
			default:
				System.out.println("Invalid choice.");
				break;
			}

		} while (doctorChoice != 00);

	}

	private static void patientFunctions(Scanner scanner, PatientManager pm, User user) {
		int patientChoice;

		do {
			System.out.println("Please choose an option:");
			System.out.println("1. View my trial");
			System.out.println("2. View patient description");
			System.out.println("00. Exit - return to login");

			patientChoice = scanner.nextInt();
			scanner.nextLine();

			switch (patientChoice) {
			case 1:
				System.out.println("Viewing patient by ID...");
				String patientNameToView = user.getUsername();
				System.out.println(pm.getPatientByName(patientNameToView));
				break;
			case 2:
				System.out.println("Viewing patient description...");
				String patientName = user.getUsername();
				pm.getPatientDescription(patientName);
				break;
			case 00:
				System.out.println("Exiting to login...");
				break;

			default:
				System.out.println("Invalid choice.");
				break;
			}

		} while (patientChoice != 00);

	}

	private static void guestFunctions(Scanner scanner, TrialManager tm) {
		int guestChoice;

		do {
			System.out.println("Please choose an option:");
			System.out.println("1. View all trials");
			System.out.println("2. View trial details");
			System.out.println("00. Exit - return to login");

			guestChoice = scanner.nextInt();
			scanner.nextLine();

			switch (guestChoice) {
			case 1:
				System.out.println("Viewing all trials...");
				System.out.println("All trials in the system:");

				for (Trial trial : tm.getAllTrials()) {
					System.out.println("Trial ID: " + trial.getTrialId());
					System.out.println("Trial Name: " + trial.getTrialName());
					System.out.println("More details are hidden.");
					System.out.println("------------------------");
				}
				break;

			case 2:
				System.out.println("Viewing trial details...");
				System.out.println("Enter trial ID:");
				int trialIdToView = scanner.nextInt();
				scanner.nextLine();

				System.out.println(tm.viewTrial(trialIdToView));
				break;

			case 00:
				System.out.println("Exiting to login...");
				break;

			default:
				System.out.println("Invalid choice.");
				break;
			}

		} while (guestChoice != 00);

	}



	private static void xmlFunctions(Scanner scanner, TrialManager tm, PatientManager pm, DoctorManager dm,
			HospitalManager hm, DescriptionManager dpm, HospitalTrialManager htm) {

		int xmlChoice;
		XmlManager xmlManager = new XmlManager();

		do {
			System.out.println("Please choose an XML option:");
			System.out.println("1. Export whole database to XML");
			System.out.println("2. Import whole database from XML");
			System.out.println("3. Export one patient to XML");
			System.out.println("4. Import one patient from XML");
			System.out.println("5. Export one trial to XML");
			System.out.println("6. Import one trial from XML");
			System.out.println("7. Transform XML databse to HTML");
			System.out.println("00. Exit - return to login");

			xmlChoice = scanner.nextInt();
			scanner.nextLine();

			switch (xmlChoice) {
			case 1:
				System.out.println("Exporting whole database to XML...");

				String databaseFilePath = "xmlFiles/clinical_trials_database.xml";

				ClinicalTrialsXMLDataBase database = new ClinicalTrialsXMLDataBase();

				database.setPatients(new ArrayList<>(pm.getAllPatients()));
				database.setTrials(tm.getAllTrials());
				database.setDescriptions(dpm.showAllDescriptions());
				database.setDoctors(dm.showAllDoctors());
				database.setHospitals(hm.getAllHospitals());
				database.setHospitalTrials(htm.showAllHospitalTrials());
				xmlManager.marshalDatabase(database, databaseFilePath);

				break;
			case 2:
				System.out.println("Importing whole database from XML...");

				String importDatabaseFilePath = "xmlFiles/clinical_trials_database.xml";

				ClinicalTrialsXMLDataBase importedDatabase = xmlManager.unmarshalDatabase(importDatabaseFilePath);

				if (importedDatabase != null) {
					System.out.println("Database imported correctly from XML.");
					System.out.println("Doctors: " + importedDatabase.getDoctors());
					System.out.println("Hospitals: " + importedDatabase.getHospitals());
					System.out.println("Patients: " + importedDatabase.getPatients());
					System.out.println("Descriptions: " + importedDatabase.getDescriptions());
					System.out.println("Trials: " + importedDatabase.getTrials());
					System.out.println("Hospital-Trial relations: " + importedDatabase.getHospitalTrials());
				} else {
					System.out.println("The database could not be imported from XML.");
				}

				break;
			case 3:
				System.out.println("Exporting one patient to XML...");
				System.out.println("Enter patient ID:");
				int patientId = scanner.nextInt();
				scanner.nextLine();
				String patientFilePath = "xmlFiles/patient_" + patientId + ".xml";
				xmlManager.marshalPatient(pm.getPatientById(patientId), patientFilePath);

				break;
			case 4:
				System.out.println("Importing one patient from XML...");
				System.out.println("Enter XML file path:");
				String patientImportPath = scanner.nextLine();
				System.out.println(xmlManager.unmarshalPatient(patientImportPath));

				break;
			case 5:
				System.out.println("Exporting one trial to XML...");
				System.out.println("Enter trial ID:");
				int trialId = scanner.nextInt();
				scanner.nextLine();
				String trialFilePath = "xmlFiles/trial_" + trialId + ".xml";
				xmlManager.marshalTrial(tm.viewTrial(trialId), trialFilePath);

				break;
			case 6:
				System.out.println("Importing one trial from XML...");
				System.out.println("Enter XML file path:");
				String trialImportPath = scanner.nextLine();

				System.out.println(xmlManager.unmarshalTrial(trialImportPath));

				break;
				
			case 7: 
				System.out.println("Transforming XML database to HTML...");

			    xmlManager.transformXMLToHTML(
			        "xmlFiles/clinical_trials_database.xml",
			        "xmlFiles/clinical_trials_database.xsl",
			        "xmlFiles/clinical_trials_database.html"
			    );
			    break;

			case 00:
				System.out.println("Exiting XML menu...");
				break;

			default:
				System.out.println("Invalid choice.");
				break;
			}

		} while (xmlChoice != 00);
	}

}
