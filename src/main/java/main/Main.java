package main;

import jdc.ConnectionManager;
import Pojos.Description; 
import jdc.DescriptionManager; 


public class Main {
	public static void main(String[] args) {
		
		ConnectionManager cm = new ConnectionManager(); 
		DescriptionManager dm = new DescriptionManager(cm); 
		
		Description d1 = new Description(1, "Female", "Asthma"); 
		Description d2 = new Description(2, "Male", "Diabetes"); 
		Description d3 = new Description(3, "Female", "Hypertension"); 
		
		dm.insertDescription(d1,1); 
		dm.insertDescription(d2,2);
		dm.insertDescription(d3,3);
		
		cm.closeConnection();
		
		
		
		
		
		
	}

}
