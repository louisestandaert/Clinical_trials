package jdc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Pojos.Trials;
import jdbc.ConnectionManager; 

public class TrialManager {
	private ConnectionManager connectionManager;
	
	//constr
	public  TrialManager (ConnectionManager connectionManager) {
		this.connectionManager = connectionManager; 
	}
	
	//metodo auxiliar
	private Trials buildTrial(ResultSet rs) throws SQLException {
	        Trials trial = new Trials();

	        trial.setTrialId(rs.getInt("trial_id"));
	        trial.setTrialName(rs.getString("trial_name"));
	        trial.setDurationDays(rs.getInt("duration_days"));
	        trial.setBudget(rs.getDouble("budget"));
	        trial.setTargetPatients(rs.getInt("target_patients"));

	        java.sql.Date sqlDate = rs.getDate("starting_date");
	        if (sqlDate != null) {
	            trial.setStartingDate(sqlDate.toLocalDate());
	        }

	        return trial;
	    }

	
	//add ensayo clinico 1
	public boolean addTrial(Trials trial) {
		String sql = "INSERT INTO trial (trialId, trialDate, startingDate, durationDays, budget, targetPatients) VALUES (?, ?, ?, ?, ?, ?)";
		if (trial==null) {
			System.err.println("Error: Trial is null");
			return false;
		}
		try (Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)){	
			ps.setInt(1, trial.getTrialId());
			ps.setString(2, trial.getTrialName());
			ps.setDate(3, java.sql.Date.valueOf(trial.getStartingDate()));
			ps.setInt(4, trial.getDurationDays());
			ps.setDouble(5, trial.getBudget());
			ps.setInt(6, trial.getTargetPatients());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error inserting trial: " + e.getMessage());
            return false;
        }
	}
	
	//Select all 
	public List<Trials> getAllTrials(){
		List<Trials> trialsList = new ArrayList<>();
		String sql = "SELECT * FROM trials";
		
		try(Connection connection = connectionManager.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet resultSet = ps.executeQuery()) {					//!!!
			while (resultSet.next()) {
				trialsList.add(buildTrial(resultSet));
			}	
		} catch (SQLException e) {
            System.err.println("Error getting all trials: " + e.getMessage());
        }
		return trialsList;
	}
	
	//asignar medico a ensayo 2
	public boolean assignDoctorToTrial (int doctorId, int trialId) {
		 String sql = "UPDATE doctor SET trial_id = ? WHERE doctor_id = ?";

	        try (Connection connection = connectionManager.getConnection();
	             PreparedStatement ps = connection.prepareStatement(sql)) {

	            ps.setInt(1, trialId);
	            ps.setInt(2, doctorId);

	            int rowsAffected = ps.executeUpdate();
	            return rowsAffected > 0;

	        } catch (SQLException e) {
	            System.err.println("Error assigning doctor to trial: " + e.getMessage());
	            return false;
	        }
	    }
	//Inscribir paciente de ensayo 3
	public boolean enrollPatientInTrial(int patientId, int trialId) {
        String sql = "UPDATE patient SET trial_id = ? WHERE patient_id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, trialId);
            ps.setInt(2, patientId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error enrolling patient in trial: " + e.getMessage());
            return false;
        }
    }
	
	//eliminar paciente de ensayo 4
	public boolean quitPatientFromTrial(int patientId) {
        String sql = "UPDATE patient SET trial_id = NULL WHERE patient_id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error quiting patient from trial: " + e.getMessage());
            return false;
        }
    }
	
	// See del ensayo 5
	
}

	

	


				
			
		
		
	
	



