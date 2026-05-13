package jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import Pojos.Trial; 
import java.time.LocalDate;


public class TrialManager {
	private ConnectionManager connectionManager;
	
	public  TrialManager (ConnectionManager connectionManager) {
		this.connectionManager = connectionManager; 
	}
	
	private Trial buildTrial(ResultSet rs) throws SQLException {
	    Trial trial = new Trial();

	    trial.setTrialId(rs.getInt("trial_id"));
	    trial.setTrialName(rs.getString("trial_name"));
	    trial.setDurationDays(rs.getInt("duration_days"));
	    trial.setBudget(rs.getDouble("budget"));
	    trial.setTargetPatients(rs.getInt("target_patients"));

	    String dateText = rs.getString("starting_date");

	    if (dateText != null && !dateText.isEmpty()) {
	        if (dateText.contains("-")) {
	            trial.setStartingDate(LocalDate.parse(dateText.substring(0, 10)));
	        } else {
	            trial.setStartingDate(
	                java.time.Instant.ofEpochSecond(Long.parseLong(dateText))
	                    .atZone(java.time.ZoneId.systemDefault())
	                    .toLocalDate()
	            );
	        }
	    }

	    return trial;
	}
	
	//add ensayo clinico
	public boolean addTrial(int trialId, String trialName, LocalDate startingDate, int durationDays, double budget,
			int targetPatients) {
		String sql = "INSERT INTO Trials (trial_id, trial_name, starting_date, duration_days, budget, target_patients) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			Connection connection = connectionManager.getConnection();

			try (PreparedStatement ps = connection.prepareStatement(sql)) {

				ps.setInt(1, trialId);
				ps.setString(2, trialName);
				ps.setDate(3, java.sql.Date.valueOf(startingDate));
				ps.setInt(4, durationDays);
				ps.setDouble(5, budget);
				ps.setInt(6, targetPatients);

				int rowsAffected = ps.executeUpdate();
				return rowsAffected > 0;
			}

		} catch (SQLException e) {
			System.err.println("Error inserting trial: " + e.getMessage());
			return false;
		}
		
		
    }		
	
	//Select all 
	public List<Trial> getAllTrials(){
		List<Trial> trialsList = new ArrayList<>();
		String sql = "SELECT * FROM Trials";

	    try {
	        Connection connection = connectionManager.getConnection();

	        try (PreparedStatement ps = connection.prepareStatement(sql);
	             ResultSet resultSet = ps.executeQuery()) {

	            while (resultSet.next()) {
	                trialsList.add(buildTrial(resultSet));
	            }
	        }
		} catch (SQLException e) {
            System.err.println("Error getting all trials: " + e.getMessage());
        }
		return trialsList;
	}
	
	//COMPROBADO 
	public boolean assignDoctorToTrial (int doctorId, int trialId) {
		 String sql = "UPDATE Doctors SET trial_id = ? WHERE doctor_id = ?";

		 try {
			 Connection connection = connectionManager.getConnection();

		        try (PreparedStatement ps = connection.prepareStatement(sql)) {
		            ps.setInt(1, trialId);
		            ps.setInt(2, doctorId);

		            int rowsAffected = ps.executeUpdate();
		            return rowsAffected > 0;
		        }

	        } catch (SQLException e) {
	            System.err.println("Error assigning doctor to trial: " + e.getMessage());
	            return false;
	        }
	    }
	//Inscribir paciente de ensayo 3
	public boolean enrollPatientInTrial(int patientId, int trialId) {
        String sql = "UPDATE Patients SET trial_id = ? WHERE patients_id = ?";

        try {
            Connection connection = connectionManager.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, trialId);
                ps.setInt(2, patientId);

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error enrolling patient in trial: " + e.getMessage());
            return false;
        }
    }
	
	//eliminar paciente de ensayo 4
	public boolean quitPatientFromTrial(int patientId) {
		 String sql = "UPDATE Patients SET trial_id = 0 WHERE patients_id = ?";
		 //para no hacer null, lo pongo a 0, que no existe en la tabla de ensayos. ID 0 = sin ensayo asignado.
		 try {
		        Connection connection = connectionManager.getConnection();

		        try (PreparedStatement ps = connection.prepareStatement(sql)) {
		            ps.setInt(1, patientId);

		            int rowsAffected = ps.executeUpdate();
		            return rowsAffected > 0;
		        }


        } catch (SQLException e) {
            System.err.println("Error quiting patient from trial: " + e.getMessage());
            return false;
        }
    }
	
	// See del ensayo 5
	 public Trial seeTrial(int trialId) {
	        String sql = "SELECT * FROM Trials WHERE trial_id = ?";

	        try {
	            Connection connection = connectionManager.getConnection();

	            try (PreparedStatement ps = connection.prepareStatement(sql)) {
	                ps.setInt(1, trialId);

	                try (ResultSet resultSet = ps.executeQuery()) {
	                    if (resultSet.next()) {
	                        return buildTrial(resultSet);
	                    }
	                }
	            }

	        } catch (SQLException e) {
	            System.err.println("Error getting trial: " + e.getMessage());
	        }

	        return null;
	    }
	 
	 //COMPROBADO
	 public boolean removeTrial(int trialId) {
		 String checkDoctorSql = "SELECT COUNT (*) FROM Doctors WHERE trial_id = ?";
		 String checkPatientSql = "SELECT COUNT(*) FROM Patients WHERE trial_id = ?";
		 String deleteTrialSql = "DELETE FROM Trials WHERE trial_id = ?";
		 
		 try {
		        Connection connection = connectionManager.getConnection();

		        connection.setAutoCommit(false);

		        try (PreparedStatement psDoctor = connection.prepareStatement(checkDoctorSql);
		             PreparedStatement psPatient = connection.prepareStatement(checkPatientSql);
		             PreparedStatement psDelete = connection.prepareStatement(deleteTrialSql)) {

		            psDoctor.setInt(1, trialId);

		            try (ResultSet doctorResult = psDoctor.executeQuery()) {
		                if (doctorResult.next() && doctorResult.getInt(1) > 0) {
		                    System.err.println("Trial removal went wrong. There are Doctors assigned to this trial");
		                    connection.rollback();
		                    return false;
		                }
		            }

		            psPatient.setInt(1, trialId);

		            try (ResultSet patientResult = psPatient.executeQuery()) {
		                if (patientResult.next() && patientResult.getInt(1) > 0) {
		                    System.err.println("Trial removal went wrong. There are Patients enrolled in this trial");
		                    connection.rollback();
		                    return false;
		                }
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
	        String sql = "SELECT AVG(duration_days) AS avg_duration FROM Trials"; //añado el apodo AS para lgo recuperarlo por el nombre y no la posicion
	        

	        try {
	            Connection connection = connectionManager.getConnection();

	            try (PreparedStatement ps = connection.prepareStatement(sql);
	                 ResultSet resultSet = ps.executeQuery()) {

	                if (resultSet.next()) {
	                    return resultSet.getDouble("avg_duration");
	                }
	            }

	        		
	        } catch (SQLException e) {
	        	System.err.println("Error calculating average duration: " + e.getMessage());
	        }
	        return 0.0;
	 }
	 
	//COMPROBADO
	    public double calculateAverageBudget() {
	        String sql = "SELECT AVG(budget) AS avg_budget FROM Trials";
	        try {
	            Connection connection = connectionManager.getConnection();

	            try (PreparedStatement ps = connection.prepareStatement(sql);
	                 ResultSet rs = ps.executeQuery()) {

	                if (rs.next()) {
	                    return rs.getDouble("avg_budget");
	                }
	            }


	        } catch (SQLException e) {
	            System.err.println("Error calculating average budget: " + e.getMessage());
	        }

	        return 0.0;
	    }
	    
	    //COMPROBADO
	    public String resultsComparation(int trialId) {
	        String sql =
	            "SELECT " +
	            "SUM(CASE WHEN LOWER(results) = 'positive' THEN 1 ELSE 0 END) AS positive_count, " +
	            "SUM(CASE WHEN LOWER(results) = 'negative' THEN 1 ELSE 0 END) AS negative_count " +
	            "FROM Patients WHERE trial_id = ?";
	        try {
	            Connection connection = connectionManager.getConnection();

	            try (PreparedStatement ps = connection.prepareStatement(sql)) {
	                ps.setInt(1, trialId);

	                try (ResultSet rs = ps.executeQuery()) {
	                    if (rs.next()) {
	                        int positive = rs.getInt("positive_count");
	                        int negative = rs.getInt("negative_count");

	                        if (positive > negative) {
	                            return "Results are more positive.";
	                        } else if (negative > positive) {
	                            return "Results are more negative.";
	                        } else {
	                            return "Results are balanced or equal.";
	                        }
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Error checking results: " + e.getMessage());
	        }
	           
	        return "No results available.";
	    }
	    
	    //prediccion pacientes nuevos 10
	    public int predictHowManyNewPatientsRequired(int trialId) {
	        String sql =
	            "SELECT t.target_patients, COUNT(p.patients_id) AS current_patients " +
	            "FROM Trials AS t " +
	            "JOIN Patients AS p ON t.trial_id = p.trial_id " +
	            "WHERE t.trial_id = ? " +
	            "GROUP BY t.target_patients";
	        try {
	            Connection connection = connectionManager.getConnection();

	            try (PreparedStatement ps = connection.prepareStatement(sql)) {
	                ps.setInt(1, trialId);

	                try (ResultSet rs = ps.executeQuery()) {
	                    if (rs.next()) {
	                        int targetPatients = rs.getInt("target_patients");
	                        int currentPatients = rs.getInt("current_patients");

	                        int needed = targetPatients - currentPatients;
	                        return Math.max(needed, 0);
	                    }
	                }
	            }

	        
	        } catch (SQLException e) {
	            System.err.println("Error predicting new patients required: " + e.getMessage());
	        }

	        return 0;
	    }
}//end

