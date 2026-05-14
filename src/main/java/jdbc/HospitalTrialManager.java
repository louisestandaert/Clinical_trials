package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Pojos.HospitalTrial;

public class HospitalTrialManager {
	private Connection c;
	
	public HospitalTrialManager(Connection c) {
		this.c = c;
	}
	
	public void assignTrialToHospital(int trialId, int hospitalId) {
		String sql = "INSERT INTO Trial_Hospital (trial_id, hospital_id) VALUES (?, ?)";

		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, trialId);
			ps.setInt(2, hospitalId);

			ps.executeUpdate();
			System.out.println("Trial assigned to hospital correctly.");
			ps.close();
		} catch (SQLException e) {
			System.err.println("Error assigning trial to hospital: " + e.getMessage());
		}
	}
	
	public void removeTrialFromHospital(int trialId, int hospitalId) {
		String sql = "DELETE FROM Trial_Hospital WHERE trial_id=? AND hospital_id=?";

		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, trialId);
			ps.setInt(2, hospitalId);

			int rowsAffected = ps.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Trial removed from hospital correctly");
			} else {
				System.out.println("No assignment found for the given trial and hospital IDs: " + trialId + ", " + hospitalId);
			}
			ps.close();
		} catch (SQLException e) {
			System.err.println("Error removing trial from hospital: " + e.getMessage());
		}
	}
	
	public ArrayList<HospitalTrial> showAllHospitalTrials() {
		ArrayList<HospitalTrial> hospitalTrialsList = new ArrayList<>();
		String sql = "SELECT * FROM Trial_Hospital";

		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int trialId = rs.getInt("trial_id");
				int hospitalId = rs.getInt("hospital_id");
				hospitalTrialsList.add(new HospitalTrial(trialId, hospitalId));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("Error retrieving hospital-trial assignments: " + e.getMessage());
		}
		return hospitalTrialsList;
	}
	
	//checks if the trial is already assigned to the hospital
	public boolean isTrialAssignedToHospital(int trialId, int hospitalId) {
		String sql = "SELECT COUNT(*) FROM Trial_Hospital WHERE trial_id=? AND hospital_id=?";

		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, trialId);
			ps.setInt(2, hospitalId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0; 
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("Error checking trial assignment: " + e.getMessage());
		}
		return false; 
	}
	
	//finds trials assigned to a hospital
	public ArrayList<Integer> findTrialsByHospitalId(int hospitalId) {
		ArrayList<Integer> trialIds = new ArrayList<>();
		String sql = "SELECT trial_id FROM Trial_Hospital WHERE hospital_id=?";

		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, hospitalId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				trialIds.add(rs.getInt("trial_id"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("Error finding trials by hospital ID: " + e.getMessage());
		}
		return trialIds;
	}
	
	
}
