package jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Pojos.Trials; 

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
	 public Trials seeTrial(int trialId) {
	        String sql = "SELECT * FROM trial WHERE trial_id = ?";

	        try (Connection connection = connectionManager.getConnection();
	             PreparedStatement ps = connection.prepareStatement(sql)) {

	            ps.setInt(1, trialId);

	            try (ResultSet resultSet = ps.executeQuery()) {
	                if (resultSet.next()) {
	                    return buildTrial(resultSet);
	                }
	            }

	        } catch (SQLException e) {
	            System.err.println("Error getting trial: " + e.getMessage());
	        }

	        return null;
	    }
	 
	 //remove trial 6
	 public boolean removeTrial(int trialId) {
		 String checkDoctorSql = "SELECT COUNT (*) FROM doctor WHERE trial_id = ?";
		 String checkPatientSql = "SELECT COUNT(*) FROM patient WHERE trial_id = ?";
		 String deleteTrialSql = "DELETE FROM trials WHERE trial_id = ?";
		 
		 try (Connection connection = connectionManager.getConnection()) {
			 
			 connection.setAutoCommit(false);
			 
			 try(PreparedStatement psDoctor = connection.prepareStatement(checkDoctorSql);
					 PreparedStatement psPatient = connection.prepareStatement(checkPatientSql);
					 PreparedStatement psDelete = connection.prepareStatement(deleteTrialSql)){
				 
				 psDoctor.setInt(1, trialId);
				 ResultSet doctorResult = psDoctor.executeQuery();
				 
				 if(doctorResult.next() && doctorResult.getInt(1)>0) {
					 System.err.println("Trial removal went wrong. There are Doctors assigned to this trial");
					 connection.rollback();
					 return false;
				 }
				 
				 psPatient.setInt(1, trialId);
				 ResultSet patientResult = psPatient.executeQuery();
				 
				 if(patientResult.next() && patientResult.getInt(1)>0) {
					 System.err.println("Trial removal went wrong. There are Patients enrolled to this trial");
					 connection.rollback();
					 return false;
					 
				 }
				 
				 psDelete.setInt(1, trialId);
				 int rowsAffected = psDelete.executeUpdate();
				 connection.commit();
		         return rowsAffected > 0;
				 
			 }catch (SQLException e) {
		            connection.rollback();
		            System.err.println("Error removing trial: " + e.getMessage());
		            return false;	 
			 }finally{
	            connection.setAutoCommit(true);
	         }
			 
	    } catch (SQLException e) {
	        System.err.println("Database error removing trial: " + e.getMessage());
	        return false;
	    }  
	 }
	 
	 //calcular media duracion 7
	 public double calculateAverageDuration() {
	        String sql = "SELECT AVG(duration_days) AS avg_duration FROM trial"; //añado el apodo AS para lgo recuperarlo por el nombre y no la posicion
	        
	        try(Connection connection = connectionManager.getConnection();
	        	PreparedStatement ps = connection.prepareStatement(sql);
	        	ResultSet resultSet = ps.executeQuery()){
	        		
	        		if(resultSet.next()) {
	        			return resultSet.getDouble("avg_duration");
	        		}
	        		
	        } catch (SQLException e) {
	        	System.err.println("Error calculating average duration: " + e.getMessage());
	        }
	        return 0.0;
	 }
	 
	//calcular media presupuesto 8
	    public double calculateAverageBudget() {
	        String sql = "SELECT AVG(budget) AS avg_budget FROM trial";

	        try (Connection connection = connectionManager.getConnection();
	             PreparedStatement ps = connection.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                return rs.getDouble("avg_budget");
	            }

	        } catch (SQLException e) {
	            System.err.println("Error calculating average budget: " + e.getMessage());
	        }

	        return 0.0;
	    }
	    //hola
	    
	    
}


	

	


				
			

		
		
	
	



