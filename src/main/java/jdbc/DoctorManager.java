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
	
	public void insertDoctor(int doctorId, String doctorName, String doctorGender, DoctorSpecialty doctorSpecialty, int hospitalId, int trialId) {
		String sql= "INSERT INTO Doctors (doctor_id, doctor_name, doctor_gender, doctor_specialty, hospital_id, trial_id) VALUES (?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps=doctorConnection.prepareStatement(sql);
            ps.setInt(1, doctorId); 
            ps.setString(2, doctorName);
            ps.setString(3, doctorGender);
            ps.setString(4, doctorSpecialty.name());
            ps.setInt(5, hospitalId);
            ps.setInt(6, trialId);
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
	
	
	public List<Doctors> showAllDoctors() {
		List<Doctors> doctorsList = new ArrayList<>();
		String sql="SELECT * FROM Doctors";
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
	
	
	public void assignDoctorToHospital(int doctorId, int newhospitalId) {
		String sql= "UPDATE Doctors SET hospital_id= ? WHERE doctor_id= ?";
		try {
			PreparedStatement ps= doctorConnection.prepareStatement(sql);
			ps.setInt(1, newhospitalId);
			ps.setInt(2, doctorId);
			
			ps.executeUpdate();
			System.out.println("The doctor has been assigned to another hospital");
		}catch(SQLException e) {
			System.err.println("There has been an error while assigning the doctor to the new hospital:" + e.getMessage());
		}
	}
	
	
}
	
	
	
	
	
	
	
	
