package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.List;

import Pojos.Description; 

public class DescriptionManager {
	private Connection descriptionConnection;
	
	public  DescriptionManager (Connection descriptionConnection) {
		this.descriptionConnection = descriptionConnection; 
	}
	
	public void insertDescription (int descriptionId, String gender, String cause, int patientId) {
		String sql = "INSERT into Descriptions (description_id, gender, cause, patient_id) VALUES (?, ?, ?, ? ) "; 
		
			try { 
				PreparedStatement pst = descriptionConnection.prepareStatement(sql); 
				
				pst.setInt (1, descriptionId); 
				pst.setString(2, gender);
				pst.setString(3, cause); 
				pst.setInt(4, patientId); 
						
				pst.executeUpdate();
				
				System.out.println("DThe description has been inserted correctly."); 
						
				
			} catch (SQLException e) {
				System.err.println("There has been an error while trying to insert the description: " + e.getMessage()); 
			}			
		}
	
	public void removeDescription (int descriptionId) {
		String sql = "DELETE FROM Descriptions WHERE description_id=?"; 
		
		try { 
			PreparedStatement pst = descriptionConnection.prepareStatement(sql); 
			
			pst.setInt(1, descriptionId); 
			
			int row = pst.executeUpdate();
			
			if (row > 0) {
				System.out.println("The description has been removed correctly."); 
			} else {
				System.out.println("No description has the ID given: " + descriptionId); 
			}
			
			
		} catch (SQLException e) {
			System.err.println("There has been an error while trying to remove the description: " + e.getMessage());
			 e.printStackTrace();
		}
	}
		
	
	public List<Description> showAllDescriptions() {
		List<Description> descriptionList = new ArrayList<>();
		
		String sql = "SELECT * FROM Descriptions"; 
		
		try { 
			PreparedStatement pst = descriptionConnection.prepareStatement(sql); 
			ResultSet rs = pst.executeQuery(); 
			
			while (rs.next()) {
				Description description = new Description(
						rs.getInt("description_id"),
						rs.getString("gender"),
						rs.getString("cause"),
						rs.getInt("patient_id")
						);
				descriptionList.add(description);
			}
			
			} catch (SQLException e) {
				System.err.println("There has been an error while trying to show all descriptions: " + e.getMessage());
				e.printStackTrace();
			}
		return descriptionList;
	}
	
	public List<Description> findDescriptionByGender(String gender){
		List<Description> descriptionsByGender = new ArrayList<>();
		
		String sql = "SELECT * FROM Descriptions WHERE gender = ?";
		
		try {
			PreparedStatement pst = descriptionConnection.prepareStatement(sql);
			
			pst.setString(1, gender);
			
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				Description description = new Description(
						rs.getInt("description_id"),
						rs.getString("gender"),
						rs.getString("cause"),
						rs.getInt("patient_id")
						);
				descriptionsByGender.add(description);
			}
			} catch (SQLException e) {
				System.err.println("There has been an error while trying to find descriptions by gender" + e.getMessage());
				e.printStackTrace();
			}
		return descriptionsByGender;
	}
	
	
	
	
}
		
		

