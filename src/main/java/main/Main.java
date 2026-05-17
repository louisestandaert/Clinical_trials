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
			System.out.println("Welcome to the Clinical Trials Database!");
			System.out.println("please select an option:");
			System.out.println("1. Login");
			System.out.println("2. Enter as Guest");
			System.out.println("3. Sign up");
			System.out.println("5. comprobaciones"); // esto luego se borrará, es solo para probar los managers y el jpa
			System.out.println("4. Exit");

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
					System.out.println("Login failed dentro de catch: " + e.getMessage());
					break;

				}

				System.out.println("Retrieving user role...");

				role = jpaManager.getRoleByUser(jpaManager.findUserByUsername(username));

				switch (role) {

				case "Trial Manager":
					System.out.println("Welcome, Trial Manager! You have full access to the system.");
					trialManagerFunctions(scanner, tm, pm, dm, hm, htm);

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
					System.out.println("Role not recognized. please try again.");
					break;
				}
				break;
			case 2:
				System.out.println("Entering as Guest...");
				guestFunctions(scanner, tm);
				break;
			case 3:
				System.out.println("Signing up...");

				// jpaManager.createRole("Trial Manager");
				// jpaManager.createRole("Doctor");
				// jpaManager.createRole("Patient");
				// jpaManager.createRole("Guest");

				System.out.println("Enter username: ");
				username = scanner.nextLine();

				System.out.println("Enter password: ");
				password = scanner.nextLine();

				System.out.println("select a role for the user:");
				role = scanner.nextLine();

				if (role == null || (!role.equalsIgnoreCase("Trial Manager") && !role.equalsIgnoreCase("Doctor")
						&& !role.equalsIgnoreCase("Patient") && !role.equalsIgnoreCase("Guest"))) {
					System.out.println(
							"Invalid role selected. Please choose from: Trial Manager, Doctor, Patient, Guest.");
					break;
				}

				// la seguridad de asignacion de roles.
				if (role.equalsIgnoreCase("Trial Manager")) {
					System.out.println("please enter the admin password to create a Trial Manager user:");
					String adminPassword = scanner.nextLine();
					if (!adminPassword.equals("admin123")) {
						System.out.println("Incorrect admin password. Only admins can create Trial Manager users.");
						break;
					}

				}

				if (role.equalsIgnoreCase("Doctor")) {
					System.out.println("please enter the admin password to create a Doctor user:");
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
				System.out.println("Exiting the application. Goodbye!");
				break;
			case 5:
				comprobaciones();
				break;
			default:
				System.out.println("Invalid choice.");
				break;
			}
		} while (choice != 4);

	}

	private static void trialManagerFunctions(Scanner scanner, TrialManager tm, PatientManager pm, DoctorManager dm,
			HospitalManager hm, HospitalTrialManager htm) {

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
			System.out.println("10. add doctor");
			System.out.println("11. Assign doctor to trial");
			System.out.println("12. Assign doctor to hospital");
			System.out.println("13. remove doctor");

			System.out.println("Handle hospitals:");
			System.out.println("14. add hospital");
			System.out.println("15. Assign hospital to trial");
			System.out.println("16. Remove hospital from trial");

			System.out.println("00. Exit - volver al login");

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
				System.out.println("viewing all trials");
				System.out.println("All trials in the system:" + tm.getAllTrials());
				break;
			case 3:
				// quiza seria mejor hacerlo con el nombre del trial en vez del id.
				System.out.println("Viewing trial details...");
				System.out.println("Enter trial ID:");
				int viewTrialId = scanner.nextInt();
				scanner.nextLine();
				System.out.println(tm.viewTrial(viewTrialId));
				break;
			case 4: // compare trial results
				System.out.println("Comparing trial results...");
				System.out.println("Enter trial ID:");
				int compareTrialId = scanner.nextInt();
				scanner.nextLine();
				System.out.println(tm.resultsComparation(compareTrialId));
				break;
			case 5: // calculate average trial duration
				System.out.println("Calculating average trial duration...");
				System.out.println("the averge trial duration is" + tm.calculateAverageDuration());
				break;
			case 6:// calculate average trial budget
				System.out.println("Calculating average trial budget...");
				System.out.println("the average trial budget is" + tm.calculateAverageBudget());
				break;
			case 7: // remove trial
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
			case 8: // predict how many new patients are required
				System.out.println("Predicting how many new patients are required...");
				System.out.println("Enter trial ID:");
				int predictTrialId = scanner.nextInt();
				scanner.nextLine();
				System.out.println("there are " + tm.predictHowManyNewPatientsRequired(predictTrialId)
						+ " new patients required for the trial");
				break;
			case 9: // get the number of positive results from patients
				System.out.println("Getting the number of positive results from patients...");
				System.out.println(pm.getPatientCountOfPositiveResults());
				break;
			case 10: // add doctor
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

				System.out.println("which trial do you want to assign the doctor to? (enter id:");
				int trialIdForDoctor = scanner.nextInt();

				System.out.println("which hospital do you want to assign the doctor to? (enter id:");
				int hospitalIdForDoctor = scanner.nextInt();

				dm.insertDoctor(doctorIdToAdd, doctorName, doctorGender, doctorSpecialty, trialIdForDoctor,
						hospitalIdForDoctor);
				break;
			case 11: // assign doctor to trial
				System.out.println("Assigning doctor to trial...");
				System.out.println("Enter doctor ID:");
				int doctorId = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Enter trial ID:");
				int assignTrialId = scanner.nextInt();
				scanner.nextLine();
				tm.assignDoctorToTrial(doctorId, assignTrialId);
				System.out.println("Doctor assigned to trial successfully.");
				break;
			case 12: // assign doctor to hospital
				System.out.println("Assigning doctor to hospital...");
				System.out.println("Enter doctor ID:");
				int doctorIdForHospital = scanner.nextInt();
				System.out.println("Enter hospital ID:");
				int hospitalId = scanner.nextInt();
				dm.assignDoctorToHospital(doctorIdForHospital, hospitalId);
				System.out.println("Doctor assigned to hospital correctly");
				break;
			case 13: // remove doctor
				System.out.println("removing doctor...");
				System.out.println("Enter doctor ID:");
				int doctorIdToRemove = scanner.nextInt();
				dm.removeDoctor(doctorIdToRemove);
				break;
			case 14: // add hospital
				System.out.println("adding hospital...");
				System.out.println("Enter hospital ID:");
				int hospitalIdToAdd = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Enter hospital name:");
				String hospitalName = scanner.nextLine();
				System.out.println("Enter hospital location:");
				String hospitalLocation = scanner.nextLine();
				hm.insertHospital(hospitalIdToAdd, hospitalName, hospitalLocation);
				break;
			case 15: // assign hospital to trial
				System.out.println("assigning hospital to trial...");
				System.out.println("Enter hospital ID:");
				int hospitalIdForTrial = scanner.nextInt();
				System.out.println("Enter trial ID:");
				int trialIdForHospital = scanner.nextInt();
				htm.assignTrialToHospital(trialIdForHospital, hospitalIdForTrial);
				System.out.println("Hospital assigned to trial correctly");
				break;
			case 16: // remove hospital from trial
				System.out.println("removing hospital from trial...");
				System.out.println("Enter hospital ID:");
				int hospitalIdToRemoveFromTrial = scanner.nextInt();
				System.out.println("Enter trial ID:");
				int trialIdToRemoveHospital = scanner.nextInt();
				htm.removeTrialFromHospital(trialIdToRemoveHospital, hospitalIdToRemoveFromTrial);
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
			System.out.println("1. add patient");
			System.out.println("2. Assign patient to existing trial");
			System.out.println("3. View patient by id"); // seria mejor hacerlo por nombre pero bueno
			System.out.println("4. View all patients");

			System.out.println("5. add patient description");
			System.out.println("6. update patient description");
			System.out.println("7. View patient description by patient id");
			System.out.println("8. Remove patient decription");

			System.out.println("9. Get female count of patients");
			System.out.println("10. Get male count of patients");
			System.out.println("11. Get list a patients with the same cause");
			System.out.println("12. View all trials in given hospital");
			System.out.println("13. remove patient");

			System.out.println("00. Exit - volver al login");
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
				System.out.println("Description id:");
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
				System.out.println("description id:");
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
				System.out.println("Description removed"); 
				break;
			case 9: // get female count of patients
				System.out.println("calculating the number of female patients...");
				System.out.println(pm.getFemalePatientsCount());
				break;
			case 10: // get male count of patients
				System.out.println("calculating the number of male patients...");
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
			System.out.println("00. Exit - volver al login");

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
			System.out.println("00. Exit - volver al login");

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

	public static void comprobaciones() {

		ConnectionManager cm = new ConnectionManager();

		System.out.println("please choose an option:");
		System.out.println("1.probando patient manager");
		System.out.println("2.probando hospital manager");
		System.out.println("3.probando description manager");
		System.out.println("4.probando trial manager");
		System.out.println("5.probando doctor manager");
		System.out.println("6. probando jpa manager ");
		System.out.println("7. probando el xml");
		System.out.println("8. probando hospital trial manager");
		System.out.println("9. probando meotdo get all users del jpa manager");
		System.out.println("10. view all doctors ");
		System.out.println("11. view all hospitals ");

		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();

		switch (choice) {
		case 1:
			System.out.println("Testing Patient Manager...");
			PatientManager pm = new PatientManager(cm.getConnection());
			// System.out.println(pm.getPatientById(1));
			// pm.insertPatient(3, "Alice", "Positive", 1, 1, 1);
			// System.out.println(pm.getPatientById(2));
			// System.out.println(pm.getPatientById(3));
			pm.removePatient(2);
			System.out.println(pm.getAllPatients());
			pm.getPatientDescription("Alice");
			System.out.println(pm.getPatientCountOfPositiveResults());
			System.out.println(pm.getFemalePatientsCount());
			System.out.println(pm.getMalePatientsCount());
			System.out.println(pm.getListOfPatientsWithSameCause("cause A"));
			break;

		case 2:
			System.out.println("Testing Hospital Manager...");
			HospitalManager hm = new HospitalManager(cm.getConnection());
			// hm.insertHospital(2, "Hospital C", "Valencia");
			hm.showAllHospitals();
			hm.removeHospital(2);
			hm.showAllHospitals();
			break;

		case 3:
			System.out.println("Testing Description Manager...");
			DescriptionManager dm = new DescriptionManager(cm.getConnection());

			dm.insertDescription(4, "Female", "Asthma", 1);
			dm.insertDescription(5, "Male", "Diabetes", 2);
			dm.insertDescription(6, "Female", "Hypertension", 3);

			System.out.println(dm.showAllDescriptions());

			System.out.println(dm.findDescriptionByID(4));
			System.out.println(dm.findDescriptionByGender("Female"));
			System.out.println(dm.findDescriptionByCause("Asthma"));
			System.out.println(dm.findDescriptionByPatientId(2));
			dm.removeDescription(6);

			System.out.println(dm.showAllDescriptions());

			break;

		case 4:
			System.out.println("Testing Trial Manager...");

			TrialManager tm = new TrialManager(cm);

			int testTrialId = (int) (System.currentTimeMillis() % 1000000);
			int removeTrialId = testTrialId + 1;
			Trial trial1 = new Trial();
			trial1.setTrialId(testTrialId);
			trial1.setTrialName("Cancer Research");
			trial1.setStartingDate(LocalDate.of(2026, 5, 1));
			trial1.setDurationDays(90);
			trial1.setBudget(15000.0);
			trial1.setTargetPatients(20);
			// boolean added = tm.addTrial(trial1);
			// System.out.println("1. addTrial -> " + added);
			System.out.println("2. getAllTrials -> ");
			System.out.println(tm.getAllTrials());
			System.out.println("3. seeTrial -> ");
			System.out.println(tm.viewTrial(testTrialId));
			boolean doctorAssigned = tm.assignDoctorToTrial(1, testTrialId);
			System.out.println("4. assignDoctorToTrial -> " + doctorAssigned);
			boolean patientEnrolled = tm.enrollPatientInTrial(1, testTrialId);
			System.out.println("5. enrollPatientInTrial -> " + patientEnrolled);
			System.out.println("6. resultsComparation -> ");
			System.out.println(tm.resultsComparation(testTrialId));
			System.out.println("7. predictHowManyNewPatientsRequired -> ");
			System.out.println(tm.predictHowManyNewPatientsRequired(testTrialId));
			System.out.println("8. calculateAverageDuration -> ");
			System.out.println(tm.calculateAverageDuration());
			System.out.println("9. calculateAverageBudget -> ");
			System.out.println(tm.calculateAverageBudget());
			boolean patientRemoved = tm.removePatientFromTrial(1);
			System.out.println("10. quitPatientFromTrial -> " + patientRemoved);

			tm.assignDoctorToTrial(1, 1);

			// 11. removeTrial
			Trial trialToRemove = new Trial();
			trialToRemove.setTrialId(removeTrialId);
			trialToRemove.setTrialName("Temporary Trial");
			trialToRemove.setStartingDate(LocalDate.of(2026, 6, 1));
			trialToRemove.setDurationDays(30);
			trialToRemove.setBudget(5000.0);
			trialToRemove.setTargetPatients(5);

			// tm.addTrial(trialToRemove);

			//boolean removed = tm.removeTrial(removeTrialId);
			//System.out.println("11. removeTrial -> " + removed);

			break;

		case 5:
			System.out.println("Testing Doctor Manager...");
			DoctorManager dm1 = new DoctorManager(cm.getConnection());
			System.out.println(dm1.showAllDoctors());
			System.out.println(dm1.findDoctorByGender("male"));
			System.out.println(dm1.sortDoctorBySpecialty(DoctorSpecialty.CARDIOLOGY));
			dm1.removeDoctor(2);
			break;

		case 6:
			System.out.println("Testing JPA Manager...");

			JPA_manager jpaManager = new JPA_manager();

			// Create roles
			jpaManager.createRole("default");
			jpaManager.createRole("admin");
			jpaManager.createRole("doctor");

			// Create user with role default
			jpaManager.createUser("testEncryptedUser", "testPassword123", "default");

			// jpaManager.login("testEncryptedUser", "testPassword123");

			// jpaManager.login("testEncryptedUser", "wrongPassword");

			List<User> users = jpaManager.findAllUsers();

			if (users == null || users.isEmpty()) {
				System.out.println("No users found.");
			} else {
				System.out.println("Users found:");

				for (User user : users) {
					System.out.println("ID: " + user.getId());
					System.out.println("Username: " + user.getUsername());
					System.out.println("Stored password: " + user.getPassword());

					if (user.getRole() != null) {
						System.out.println("Role: " + user.getRole().getRole());
					} else {
						System.out.println("Role: null");
					}

					System.out.println("------------------------");
				}
			}

			// Test find users by role
			System.out.println("Finding users with role 'default':");

			List<User> usersByRole = jpaManager.findUserByRole("default");

			if (usersByRole == null || usersByRole.isEmpty()) {
				System.out.println("No users with role 'default' found.");
			} else {
				for (User user : usersByRole) {
					System.out.println("ID: " + user.getId());
					System.out.println("Username: " + user.getUsername());

					// Normalmente no se muestra la contraseña porque esta encriptada y aparece
					// código, si quereis la quitamos
					System.out.println("Stored password: " + user.getPassword());

					if (user.getRole() != null) {
						System.out.println("Role: " + user.getRole().getRole());
					} else {
						System.out.println("Role: null");
					}

					System.out.println("------------------------");
				}
			}

			// test find user by username
			System.out.println("Finding user with username 'testEncryptedUser':");
			User foundUser = jpaManager.findUserByUsername("testEncryptedUser");
			if (foundUser == null) {
				System.out.println("User not found.");
			} else {
				System.out.println("User found:");
				System.out.println("ID: " + foundUser.getId());
				System.out.println("Username: " + foundUser.getUsername());
				System.out.println("Password: " + foundUser.getPassword());
			}

			// test update password
			System.out.println("Updating password for user 'testEncryptedUser'...");
			jpaManager.updatePassword("testEncryptedUser", "newpassword");

			// Login with old password after update
			System.out.println("\nTesting login with old password after update:");
			// jpaManager.login("testEncryptedUser", "testPassword123");

			// Login with new password after update
			System.out.println("\nTesting login with new password after update:");
			// jpaManager.login("testEncryptedUser", "newpassword");

			// Check updated user
			System.out.println("\nChecking user after password update:");

			User updatedUser = jpaManager.findUserByUsername("testEncryptedUser");

			if (updatedUser == null) {
				System.out.println("User not found after update.");
			} else {
				System.out.println("Updated user:");
				System.out.println("ID: " + updatedUser.getId());
				System.out.println("Username: " + updatedUser.getUsername());
				System.out.println("Stored password: " + updatedUser.getPassword());
			}

			break;

		case 7:
			System.out.println("Testing XML...");

			// creando los paths de los archivos XML para cada entidad
			String doctorsPath = "xmlFiles/doctors.xml";
			String hospitalsPath = "xmlFiles/hospitals.xml";
			String descriptionsPath = "xmlFiles/descriptions.xml";
			String trialsPath = "xmlFiles/trials.xml";
			String hospitalTrialsPath = "xmlFiles/hospitalTrials.xml";
			String patientsPath = "xmlFiles/patients.xml";

			try {
				XmlManager xmlManager = new XmlManager();
				
				//PATIENT  
				Patients patient = new Patients(4, "Juan", "Negative", 1, 1, 1);
				xmlManager.marshalPatient(patient, patientsPath);
				Patients patientFromXML = xmlManager.unmarshalPatient(patientsPath);
				System.out.println("Patient read from XML successfully: " + patientFromXML);
				
				//HOSPITAL 
				Hospitals hospital = new Hospitals("Hospital D", "Seville", 1);
				xmlManager.marshalHospital(hospital, hospitalsPath);
				Hospitals hospitalFromXML = xmlManager.unmarshalHospital(hospitalsPath);
				System.out.println("Hospital read from XML successfully: " + hospitalFromXML);
				
				//DESCRIPTION
				Description description = new Description(1, "Female", "Asthma", 4);
				xmlManager.marshalDescription(description, descriptionsPath);
				Description descriptionFromXML = xmlManager.unmarshalDescription(descriptionsPath);
				System.out.println("Description read from XML successfully: " + descriptionFromXML);
				
				//TRIAL 
				Trial trial = new Trial(1, "Diabetes Research", LocalDate.of(2026, 7, 1), 60, 10000.0, 15);
				xmlManager.marshalTrial(trial, trialsPath);
				Trial trialFromXML = xmlManager.unmarshalTrial(trialsPath);
				System.out.println("Trial read from XML successfully: " + trialFromXML);
				
				 //DOCTOR
				Doctors doctor = new Doctors(3, "Dra Mencia", "Female", DoctorSpecialty.NEUROLOGY, 1, 3);
				xmlManager.marshalDoctor(doctor, doctorsPath);
				Doctors doctorFromXML = xmlManager.unmarshalDoctor(doctorsPath);
				System.out.println("Doctor read from XML successfully: " + doctorFromXML);
				
				//HOSPITAL-TRIAL RELATION
				HospitalTrial hospitalTrial = new HospitalTrial(1, 1);
				xmlManager.marshalHospitalTrial(hospitalTrial, hospitalTrialsPath);
				HospitalTrial hospitalTrialFromXML = xmlManager.unmarshalHospitalTrial(hospitalTrialsPath); 
				System.out.println("Hospital-Trial relation read from XML successfully: " + hospitalTrialFromXML);
				
				System.out.println("All XML operations completed successfully.");
				
			
			} catch (Exception e) {
				System.err.println("Error writing to XML: " + e.getMessage());
				e.printStackTrace();
			}
			break;

		case 8:
			System.out.println("Testing Hospital Trial Manager...");
			int testTrialIDForHT = 1;
			int testHospitalIDForHT = 1;
			HospitalTrialManager htm = new HospitalTrialManager(cm.getConnection());
			System.out.println("Checking if the trial is already assigned to the hospital:");
			boolean isAssigned = htm.isTrialAssignedToHospital(testTrialIDForHT, testHospitalIDForHT);
			if (isAssigned) {
				System.out.println(
						"Trial " + testTrialIDForHT + " is already assigned to hospital " + testHospitalIDForHT);
			} else {
				System.out.println("Trial " + testTrialIDForHT + " is not assigned to hospital " + testHospitalIDForHT);
			}

			// Assign hospital to trial
			System.out.println("Assigning hospital to trial...");
			if (!isAssigned) {
				htm.assignTrialToHospital(testTrialIDForHT, testHospitalIDForHT);
			} else {
				System.out.println("Trial is already assigned to the hospital, skipping assignment.");
			}

			// Check if now the trial is assigned to the hospital
			System.out.println("Checking if the trial is assigned to the hospital after assignment:");
			boolean isAssignedAfter = htm.isTrialAssignedToHospital(testTrialIDForHT, testHospitalIDForHT);
			if (isAssignedAfter) {
				System.out.println("Trial " + testTrialIDForHT + " is now assigned to hospital " + testHospitalIDForHT);
			} else {
				System.out.println(
						"Trial " + testTrialIDForHT + " is still not assigned to hospital " + testHospitalIDForHT);
			}

			// show all relations
			System.out.println("Showing all hospital-trial assignments:");
			System.out.println(htm.showAllHospitalTrials());

			// Show trials assigned to a hospital
			System.out.println("Showing trials assigned to hospital " + testHospitalIDForHT + ":");
			System.out.println(htm.findTrialsByHospitalId(testHospitalIDForHT));

			// remove trial from hospital
			System.out.println("Removing trial from hospital...");
			htm.removeTrialFromHospital(testTrialIDForHT, testHospitalIDForHT);
			// checking if the relation has been removed
			System.out.println("Checking if the trial is assigned to the hospital after removal:");
			boolean isAssignedAfterRemoval = htm.isTrialAssignedToHospital(testTrialIDForHT, testHospitalIDForHT);
			if (isAssignedAfterRemoval) {
				System.out
						.println("Trial " + testTrialIDForHT + " is still assigned to hospital " + testHospitalIDForHT);
			} else {
				System.out.println(
						"Trial " + testTrialIDForHT + " is no longer assigned to hospital " + testHospitalIDForHT);
			}

			break;
		case 9:
			System.out.println("Testing getAllUsers method of JPA Manager...");
			JPA_manager jpaManager2 = new JPA_manager();
			List<User> allUsers = jpaManager2.findAllUsers();
			System.out.println("All users in the system:" + allUsers);

			break;
		case 10:
			System.out.println("view all doctors");
			DoctorManager dm2 = new DoctorManager(cm.getConnection());
			System.out.println(dm2.showAllDoctors());
			break;
		case 11:
			System.out.println("view all hospitals");
			HospitalManager hm2 = new HospitalManager(cm.getConnection());
			hm2.showAllHospitals();	
		default:
			System.out.println("Invalid choice.");
		}

		cm.closeConnection();

	}

}
