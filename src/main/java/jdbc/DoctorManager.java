package jdbc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Pojos.Doctors;
import Pojos.DoctorSpecialty;


public class DoctorManager {
	private Connection doctorConnection;
	
	public DoctorManager(Connection doctorConnection) {
		this.doctorConnection=doctorConnection;
	}
	
	public void insertDoctor(int doctorId, String doctorName, String doctorGender, DoctorSpecialty doctorSpecialty) {
		String sql= "INSERT INTO Doctors (doctor_id, doctor_name, doctor_gender, doctor_specialty) VALUES (?,?,?,?)";
		
		try {
			PreparedStatement ps=doctorConnection.prepareStatement(sql);
            ps.setInt(1, doctorId); 
            ps.setString(2, doctorName);
            ps.setString(3, doctorGender);
            ps.setString(4, doctorSpecialty.name());
            ps.executeUpdate();
            System.out.println("The doctor has been inserted correctly.");
		}catch(SQLException e) {
			System.err.println("There has been an error while trying to insert the doctor:" + e.getMessage());
		}
		
	}
	
	public void removeDoctor(int doctorId) {
		String sql= "DELETE FROM Doctors WHERE doctor_id=?";
		try {
			PreparedStatement ps=doctorConnection.prepareStatement(sql);
			ps.setInt(1, doctorId);
			
			int row=ps.executeUpdate();
			if(row>0) {
                System.out.println("The doctor has been removed correctly.");
			}else {
				System.out.println("No doctor has the ID given: " + doctorId);
			}
		}catch(SQLException e) {
			System.err.println("There has been an error while trying to remove the doctor:" + e.getMessage());
            e.printStackTrace();
		}
	}
	
	//Enseña todos los doctores registrados en la base de datos independientemente de si estan asignados a algun hospital o a algun trial
	public List<Doctors> showAllDoctors() {
		List<Doctors> doctorsList = new ArrayList<>();
		String sql="SELECTO * FROM Doctors";
		try {
			PreparedStatement ps= doctorConnection.prepareStatement(sql);
			ResultSet rs= ps.executeQuery();
			
            while(rs.next()) {
            	DoctorSpecialty specialty=DoctorSpecialty.valueOf(rs.getString("doctor_specialty"));
            	Doctors doctor = new Doctors(
            			  rs.getInt("doctor_id"),
                          rs.getString("doctor_name"),
                          rs.getString("doctor_gender"),
                          specialty);
              
                doctorsList.add(doctor);
                }
            
		}catch(SQLException e) {
			System.err.println("There has been an error while trying to show every doctor:" + e.getMessage());
			e.printStackTrace();
		}
		return doctorsList;
	}
	
	public List<Doctors> findDoctorByGender(String doctorGender) {
		List<Doctors> doctorsByGender = new ArrayList<>();
		String sql= "SELECT * FROM Doctors WHERE doctor_gender= ?";
		
		try {
			PreparedStatement ps= doctorConnection.prepareStatement(sql);
			ps.setString(1, doctorGender);
			ResultSet rs= ps.executeQuery();
			
			
			while(rs.next()) {
				DoctorSpecialty specialty=DoctorSpecialty.valueOf(rs.getString("doctor_specialty"));
				Doctors doctor= new Doctors(
						rs.getInt("doctor_id"),
		                rs.getString("doctor_name"),
		                rs.getString("doctor_gender"),
		                specialty
		                );
          
                doctorsByGender.add(doctor);
                
               }
		}catch(SQLException e) {
			System.err.println("There has been an error while filtering the doctors by gender:" + e.getMessage());
			e.printStackTrace();
		}
		return doctorsByGender;
	}
	
	public List<Doctors> sortDoctorBySpecialty(DoctorSpecialty doctorSpecialty){
		List<Doctors> doctorsBySpecialty = new ArrayList<>();
		String sql="SELECT * FROM Doctors WHERE doctor_specialty= ?";
		try {
			PreparedStatement ps= doctorConnection.prepareStatement(sql);
            ps.setString(1, doctorSpecialty.name());
            ResultSet rs= ps.executeQuery();
            while(rs.next()) {
            	DoctorSpecialty specialty=DoctorSpecialty.valueOf(rs.getString("doctor_specialty"));
            	Doctors doctor= new Doctors(
            			rs.getInt("doctor_id"),
                        rs.getString("doctor_name"),
                        rs.getString("doctor_gender"),
                        specialty
                        );
            	doctorsBySpecialty.add(doctor);
		    }
            
	   }catch(SQLException e) {
          System.err.println("There has been an error while sorting the doctors by specialty:" + e.getMessage());
           e.printStackTrace(); 
	   }
		return doctorsBySpecialty;
    }
}
	
	
	
	
	
	
	
	
