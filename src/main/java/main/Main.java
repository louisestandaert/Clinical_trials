package main;

import jdc.ConnectionManager;
import jdc.HospitalManager;
import jdc.PatientManager;

import java.util.Scanner;

import Pojos.Description;
import Pojos.Hospitals;
import jdc.DescriptionManager;

public class Main {
	public static void main(String[] args) {

		
		ConnectionManager cm = new ConnectionManager(); 
		DescriptionManager dm = new DescriptionManager(cm); 
		HospitalManager hm = new HospitalManager(cm.getConnection());
		
		//fake userinterface 
		
		System.out.println("Welcome to the Clinical Trials Database!");
		System.out.println("please choose an opcion:");
		System.out.println("1.probando patient manager");
		
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();

		switch (choice) {
		case 1:
			System.out.println("Testing Patient Manager...");
			PatientManager pm = new PatientManager(cm.getConnection());
			System.out.println(pm.getPatientById(1));
			//pm.insertPatient(3, "Alice", "Positive", 1, 1, 1);
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
		default:
			System.out.println("Invalid choice.");
		}
		
		
		
		/**Description d1 = new Description(1, "Female", "Asthma"); 
		Description d2 = new Description(2, "Male", "Diabetes"); 
		Description d3 = new Description(3, "Female", "Hypertension"); 
		
		Hospitals h1= new Hospitals(1, "Hospital A", "Madrid");
		Hospitals h2= new Hospitals(2, "Hospital B", "Barcelona");
		
		hm.insertHospital(h1.getHospitalName(), h1.getHospitalLocation());
		hm.insertHospital(h2.getHospitalName(), h2.getHospitalLocation());
		hm.showAllHospitals();
		hm.removeHospital(1);
		
		dm.insertDescription(d1,1); 
		dm.insertDescription(d2,2);
		dm.insertDescription(d3,3);
		**/

		
		cm.closeConnection();
		
		
	}
	
		

}
