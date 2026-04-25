package jdbc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class HospitalManager {
	private Connection c;
	
	public HospitalManager(Connection c) {
		this.c=c;
	}
	
	public void insertHospital(int hospital_id,String hospitalName, String city) {
		String sql= "INSERT INTO Hospitals (hospital_id, hospital_name, city) VALUES (?,?,?)";
		
		try {
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, hospital_id);
			ps.setString(2, hospitalName);
			ps.setString(3, city);
			
			ps.executeUpdate();
			System.out.println("The hospital has been inserted correctly.");
			ps.close();
		}catch(SQLException e){
			System.err.println("There has been an error while trying to insert the hospital:" + e.getMessage());
		}
		
	}
	
	
	public void removeHospital(int hospitalId) {
		String sql="DELETE FROM Hospitals WHERE hospital_id=?";
		try {
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1,hospitalId);
			
			int rowsAffected=ps.executeUpdate();
			if(rowsAffected>0) {
				System.out.println("Hospital has been removed correctly");
			}else {
				System.out.println("No hospital has the ID given: " + hospitalId);
			}
			
			ps.close();
			
		}catch(SQLException e) {
			System.err.println("There has been an error trying to remove the hospital: " + e.getMessage());
		}
	}

	
	public void showAllHospitals() {
		String sql="SELECT * FROM Hospitals";
		
		try {
			PreparedStatement ps= c.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			
			while(rs.next()) {
				int hospitalId= rs.getInt("hospital_id");
                String hospitalName= rs.getString("hospital_name");
                String hospitalCity= rs.getString("city");
                
                System.out.println("Hospital ID: " + hospitalId + ", Name: " + hospitalName + ", City: " + hospitalCity);
			}
			rs.close();
			ps.close();
			
			
		}catch(SQLException e) {
			System.err.println("There has been an error while trying to show all hospitals:" + e.getMessage());
		}
	}
}
