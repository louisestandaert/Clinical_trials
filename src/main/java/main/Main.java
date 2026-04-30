package main;

import jdbc.ConnectionManager;
import jdbc.HospitalManager;
import jdbc.PatientManager;
import jdbc.DescriptionManager;
import jdbc.DoctorManager;
import jdbc.TrialManager;
import jpa.JPA_manager;

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
		System.out.println("6. probando jpa manager ");

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
			// esta programado diferente dejo alvaro hacerlo.
			break;

		case 5:
			System.out.println("Testing Doctor Manager...");
			DoctorManager dm1 = new DoctorManager(cm.getConnection());
			System.out.println(dm1.showAllDoctors());
			dm1.insertDoctor(2, "Dr. House", "male", DoctorSpecialty.CARDIOLOGY);
			System.out.println(dm1.findDoctorByGender("male"));
			System.out.println(dm1.sortDoctorBySpecialty(DoctorSpecialty.CARDIOLOGY));
			dm1.removeDoctor(2);
			break;
		case 6:
			System.out.println("Testing JPA Manager...");
			JPA_manager jpaManager = new JPA_manager();
			// Aquí puedes probar los métodos del JPA_manager
			// Por ejemplo:
			// jpaManager.createRole(new Role("Admin"));
			// List<Role> roles = jpaManager.getAllRoles();
			// System.out.println(roles);
			break;
		default:
			System.out.println("Invalid choice.");
		}

		cm.closeConnection();

	}

}
