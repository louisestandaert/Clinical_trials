package main;

import jdc.ConnectionManager;
import jdc.HospitalManager;
import jdc.PatientManager;
import Pojos.Description; 
import Pojos.Hospitals;
import jdc.DescriptionManager; 


public class Main {
	public static void main(String[] args) {
		
		ConnectionManager cm = new ConnectionManager(); 
		DescriptionManager dm = new DescriptionManager(cm); 
		HospitalManager hm = new HospitalManager(cm.getConnection());
		
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
		
		PatientManager pm = new PatientManager(cm.getConnection());
		//pm.insertPatient(1, "Alice", "Positive", 1, 2, 1);
		System.out.println(pm.getPatientById(1));
		
		
		
		cm.closeConnection();
		
		
	}

}
