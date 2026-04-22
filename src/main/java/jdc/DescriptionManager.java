package jdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Pojos.Description;
import jdc.ConnectionManager;

public class DescriptionManager {
	private ConnectionManager connectionManager;
	
	public  DescriptionManager (ConnectionManager connectionManager) {
		this.connectionManager = connectionManager; 
	}
	
	public void insertDescription(Description d, int patientId) {
		String sql = "INSERT into Description (description_id, gender, cause) VALUES (?, ?, ?) "; 
		
			try {
				Connection conn = connectionManager.getConnection(); 
				PreparedStatement pst = conn.prepareStatement(sql); 
				
				pst.setInt (1, d.getDescription_id()); 
				pst.setString(2, d.getGender());
				pst.setString(3, d.getCause()); 
				pst.setInt(4, patientId); 
						
				pst.executeUpdate();
				System.out.println("Descripción insertada correctamente"); 
						
				pst.close(); 
				
			} catch (SQLException e) {
				System.err.println("Error al nsertar descripción" + e.getMessage()); 
				e.printStackTrace(); 
			}			
	}

}
