package jdc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class HospitalManager {
	private Connection c;
	
	public HospitalManager(Connection c) {
		this.c=c;
	}
	
	public void insertHospital(String hospitalName, String location) {
		String sql= "INSERT INTO Hospitals (hospital_name, city) VALUES (?,?)";
		
		try {
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setString(1, hospitalName);
			ps.setString(2, location);
			
			ps.executeUpdate();
			System.out.println("The hospital has been inserted correctly.");
			ps.close();
		}catch(SQLException e){
			System.err.println("There has been an error while trying to insert the hospital:" + e.getMessage());
		}
		
	}

}
