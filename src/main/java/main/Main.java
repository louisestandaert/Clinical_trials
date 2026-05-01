package main;

import jdbc.ConnectionManager;
import jdbc.HospitalManager;
import jdbc.PatientManager;
import jdbc.DescriptionManager;
import jdbc.DoctorManager;
import jdbc.TrialManager;

import java.util.Scanner;

import Pojos.DoctorSpecialty;

public class Main {
	public static void main(String[] args) {

		ConnectionManager cm = new ConnectionManager();

		// fake userinterface

		System.out.println("Welcome to the Clinical Trials Database!");
		System.out.println("please choose an option:");
		System.out.println("1.probando patient manager");
		System.out.println("2.probando hospital manager");
		System.out.println("3.probando description manager");
		System.out.println("4.probando trial manager");
		System.out.println("5.probando doctor manager");

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

			    // 1. addTrial
			    Trial trial1 = new Trial();
			    trial1.setTrialId(testTrialId);
			    trial1.setTrialName("Cancer Research");
			    trial1.setStartingDate(LocalDate.of(2026, 5, 1));
			    trial1.setDurationDays(90);
			    trial1.setBudget(15000.0);
			    trial1.setTargetPatients(20);

			    boolean added = tm.addTrial(trial1);
			    System.out.println("1. addTrial -> " + added);

			    // 2. getAllTrials
			    System.out.println("2. getAllTrials -> ");
			    System.out.println(tm.getAllTrials());

			    // 3. seeTrial
			    System.out.println("3. seeTrial -> ");
			    System.out.println(tm.seeTrial(testTrialId));

			    // 4. assignDoctorToTrial
			    boolean doctorAssigned = tm.assignDoctorToTrial(1, testTrialId);
			    System.out.println("4. assignDoctorToTrial -> " + doctorAssigned);

			    // 5. enrollPatientInTrial
			    boolean patientEnrolled = tm.enrollPatientInTrial(1, testTrialId);
			    System.out.println("5. enrollPatientInTrial -> " + patientEnrolled);

			    // 6. resultsComparation
			    System.out.println("6. resultsComparation -> ");
			    System.out.println(tm.resultsComparation(testTrialId));

			    // 7. predictHowManyNewPatientsRequired
			    System.out.println("7. predictHowManyNewPatientsRequired -> ");
			    System.out.println(tm.predictHowManyNewPatientsRequired(testTrialId));

			    // 8. calculateAverageDuration
			    System.out.println("8. calculateAverageDuration -> ");
			    System.out.println(tm.calculateAverageDuration());

			    // 9. calculateAverageBudget
			    System.out.println("9. calculateAverageBudget -> ");
			    System.out.println(tm.calculateAverageBudget());

			    // 10. quitPatientFromTrial
			    boolean patientRemoved = tm.quitPatientFromTrial(1);
			    System.out.println("10. quitPatientFromTrial -> " + patientRemoved);

			    // Vuelvo a asignar el médico al trial inicial para poder eliminar el trial de prueba
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
			DoctorManager dm1= new DoctorManager(cm.getConnection());
			System.out.println(dm1.showAllDoctors());
			dm1.insertDoctor(2, "Dr. House", "male", DoctorSpecialty.CARDIOLOGY);
			System.out.println(dm1.findDoctorByGender("male"));
			System.out.println(dm1.sortDoctorBySpecialty(DoctorSpecialty.CARDIOLOGY));
			dm1.removeDoctor(2);
			break;
		default:
			System.out.println("Invalid choice.");
		}

		cm.closeConnection();

	}

}
