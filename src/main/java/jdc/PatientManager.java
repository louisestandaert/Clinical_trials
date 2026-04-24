package jdc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Pojos.Patients;


public class PatientManager {

	private Connection c;

	public PatientManager(Connection c) {
		this.c = c;
	}

	public void insertPatient(int patientId, String patientName, String results, int trialId, int hospitalId, int descriptionId) {
        
		String sql = "INSERT INTO Patients (patients_id, patient_name, results, trial_id, hospital_id, description_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, patientId);
            ps.setString(2, patientName);
            ps.setString(3, results);
            ps.setInt(4, trialId);
            ps.setInt(5, hospitalId);
            ps.setInt(6, descriptionId);
            
            ps.executeUpdate();
            System.out.println("Patient inserted correctly.");
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error inserting patient: " + e.getMessage());
		}

	}
	
	public void removePatient(int patientId) {
		String sql = "DELETE FROM Patients WHERE patients_id=?";

		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, patientId);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Patient removed correctly");
			} else {
				System.out.println("No patient has the ID given: " + patientId);
			}

			ps.close();
		} catch (SQLException e) {
			System.err.println("Error trying to remove the patient: " + e.getMessage());
		}
	}
	
	public Patients getPatientById(int patientId) {
		String sql = "SELECT * FROM Patients WHERE patients_id=?";
		Patients patient = null;

		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, patientId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				patient = new Patients(rs.getInt("patients_id"), rs.getString("patient_name"), rs.getString("results"),
						rs.getInt("trial_id"), rs.getInt("hospital_id"), rs.getInt("description_id"));
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("Error retrieving patient: " + e.getMessage());
		}

		return patient;
	}
}
