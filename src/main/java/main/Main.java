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
import Pojos.User;

import java.util.Scanner;

import Pojos.DoctorSpecialty;
import Pojos.Trial;

public class Main {
	public static void main(String[] args) {

		int choice;

		do {
			System.out.println("Welcome to the Clinical Trials Database!");
			System.out.println("please select an option:");
			System.out.println("1. Login");
			System.out.println("2. Enter as Guest");
			System.out.println("3. Sign up");
			System.out.println("5. comprobaciones"); // esto luego se borrará, es solo para probar los managers y el jpa
			System.out.println("4. Exit");

			Scanner scanner = new Scanner(System.in);
			choice = scanner.nextInt();
			JPA_manager jpaManager = new JPA_manager();
			String username = null;
			String password = null;
			String role = null;

			switch (choice) {
			case 1:
				System.out.println("Logging in...");

				Scanner loginScanner = new Scanner(System.in);

				System.out.println("Enter username: ");
				username = loginScanner.nextLine();

				System.out.println("Enter password: ");
				password = loginScanner.nextLine();

				jpaManager.login(username, password);
				role = jpaManager.getUserRole(username);

				switch (role) {

				case "Trial Manager":
					System.out.println("Welcome, Trial Manager! You have full access to the system.");
					trialManagerFunctions();
			
					break;
				case "Doctor":
					System.out.println("Welcome, doctor! You can manage your trials and patients.");
					doctorFunctions();
					break;
				case "patient":
					System.out.println("Welcome, patient! You can view your trial information and results.");
					patientFunctions();
					break;
				default:
					System.out.println("Role not recognized. please try again.");
					break;
				}

				break;
			case 2:
				System.out.println("Entering as Guest...");
				guestFunctions();
				break;
			case 3:
				System.out.println("Signing up...");

				Scanner signupScanner = new Scanner(System.in);

				System.out.println("Enter username: ");
				username = signupScanner.nextLine();

				System.out.println("Enter password: ");
				password = signupScanner.nextLine();

				System.out.println("select a role for the user:");
				role = signupScanner.nextLine();

				jpaManager.createUser(username, password, role);

				System.out.println("User created successfully. You can now log in.");

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
	
	
	

	private static void trialManagerFunctions() {
		
		ConnectionManager cm = new ConnectionManager();
		TrialManager tm = new TrialManager(cm);

		System.out.println("Please choose an option:");
		System.out.println("1. Create a new trial");
		System.out.println("2. View all trials");
		System.out.println("3. View trial details");
		System.out.println("4. Assign doctor to trial");
		System.out.println("5. Enroll patient in trial");
		System.out.println("6. Compare trial results");
		System.out.println("7. Predict how many new patients are required");
		System.out.println("8. Calculate average trial duration");
		System.out.println("9. Calculate average trial budget");
		System.out.println("10. Remove patient from trial");
		
		Scanner signupScanner = new Scanner(System.in);
		int trialChoice = signupScanner.nextInt();
		
		switch(trialChoice) {
		case 1:
			System.out.println("Creating a new trial...");
			//aqui esta algo incoorecto, falta un constructor que crear un trialcon todos los parametros 
			//creo que es la funcion buildtrial pero no entiendo el parametro de entrada. 
			tm.addTrial(new Trial());
			break;
		case 2: 
			System.out.println("viewing all trials");
			// tienes que crear una lista para despues imprimir la 
			tm.getAllTrials();
			
		}	

	}
	
	
	private static void doctorFunctions() {
		
	}
	
	
	private static void patientFunctions() {
		
	}
	
	
	private static void guestFunctions() {
		
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

		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();

		switch (choice) {
		case 1:
			System.out.println("Testing Patient Manager...");
			PatientManager pm = new PatientManager(cm.getConnection());
			System.out.println(pm.getPatientById(1));
			// pm.insertPatient(3, "Alice", "Positive", 1, 1, 1);
			System.out.println(pm.getPatientById(2));
			System.out.println(pm.getPatientById(3));
			pm.removePatient(2);
			System.out.println(pm.getAllPatients());
			pm.getPatientDescription("Alice");
			System.out.println(pm.getPatientCountOfPositiveResults());
			System.out.println(pm.getFemalePatientsCount());
			System.out.println(pm.getMalePatientsCount());
			System.out.println(pm.getListOfPatientsWithDescription("cause A"));
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
			boolean added = tm.addTrial(trial1);
			System.out.println("1. addTrial -> " + added);
			System.out.println("2. getAllTrials -> ");
			System.out.println(tm.getAllTrials());
			System.out.println("3. seeTrial -> ");
			System.out.println(tm.seeTrial(testTrialId));
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
			boolean patientRemoved = tm.quitPatientFromTrial(1);
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

			tm.addTrial(trialToRemove);

			boolean removed = tm.removeTrial(removeTrialId);
			System.out.println("11. removeTrial -> " + removed);

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

			jpaManager.login("testEncryptedUser", "testPassword123");

			jpaManager.login("testEncryptedUser", "wrongPassword");

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
			jpaManager.login("testEncryptedUser", "testPassword123");

			// Login with new password after update
			System.out.println("\nTesting login with new password after update:");
			jpaManager.login("testEncryptedUser", "newpassword");

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

			// creando un patient para poder mandarlo al xml
			PatientManager pm1 = new PatientManager(cm.getConnection());
			// pm1.insertPatient(4, "Juan", "Negative", 1, 1, 1);

			String filePath = "xmlFiles/patients.xml";

			try {
				XmlManager xmlManager = new XmlManager();
				// xmlManager.marshal(pm1.getPatientById(4), filePath);
				System.out.println("Patients written to XML successfully.");
			} catch (Exception e) {
				System.err.println("Error writing to XML: " + e.getMessage());
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Invalid choice.");
		}

		cm.closeConnection();

	}

}
