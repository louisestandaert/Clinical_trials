package main;

import jdbc.ConnectionManager;
import jdbc.HospitalManager;
import jdbc.PatientManager;
import jdbc.DescriptionManager;
import jdbc.TrialManager;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		ConnectionManager cm = new ConnectionManager();

		// fake userinterface

		System.out.println("Welcome to the Clinical Trials Database!");
		System.out.println("please choose an opcion:");
		System.out.println("1.probando patient manager");
		System.out.println("2.probando hospital manager");
		System.out.println("3.probando description manager");
		System.out.println("4.probando trial manager");

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
			DescriptionManager dm = new DescriptionManager(cm);
			// esta programado diferente dejo mencia hacerlo.
			break;
		case 4:
			System.out.println("Testing Trial Manager...");
			TrialManager tm = new TrialManager(cm);
			//esta programado diferente dejo alvaro hacerlo. 
			break;

		default:
			System.out.println("Invalid choice.");
		}

		/**
		 * las cosas de mencia Description d1 = new Description(1, "Female", "Asthma");
		 * Description d2 = new Description(2, "Male", "Diabetes"); Description d3 = new
		 * Description(3, "Female", "Hypertension");
		 * 
		 * Hospitals h1= new Hospitals(1, "Hospital A", "Madrid"); Hospitals h2= new
		 * Hospitals(2, "Hospital B", "Barcelona");
		 * 
		 * hm.insertHospital(h1.getHospitalName(), h1.getHospitalLocation());
		 * hm.insertHospital(h2.getHospitalName(), h2.getHospitalLocation());
		 * hm.showAllHospitals(); hm.removeHospital(1);
		 * 
		 * dm.insertDescription(d1,1); dm.insertDescription(d2,2);
		 * dm.insertDescription(d3,3);
		 **/

		cm.closeConnection();

	}

}
