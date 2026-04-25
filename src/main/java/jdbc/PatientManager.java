package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import Pojos.Description;
import Pojos.Patients;


public class PatientManager {

	private Connection c;

	public PatientManager(Connection c) {
		this.c = c;
	}

	
	//este metodo esta comprobado
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
	
	//este metodo esta comprobado
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
	
	
	//este metodo esta comprobado
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
	
	//este metodo esta comprobado
	public Set<Patients> getAllPatients() {
		
		Set<Patients> patientsSet = new HashSet<>();
		
		String sql = "SELECT * FROM Patients";

		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Patients patient = new Patients(rs.getInt("patients_id"), rs.getString("patient_name"),
						rs.getString("results"), rs.getInt("trial_id"), rs.getInt("hospital_id"),
						rs.getInt("description_id"));
				patientsSet.add(patient);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("Error retrieving patients: " + e.getMessage());
		}
		return patientsSet;
	}
	
	
	//este metodo esta comprobado
	public void getPatientDescription(String patientName) {
        String sql = "SELECT d.* FROM Descriptions d JOIN Patients p ON d.description_id = p.description_id WHERE p.patient_name = ?";
        Description description = null;
        
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, patientName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Description for patient " + patientName + ":");
                description = new Description(rs.getInt("description_id"), rs.getString("gender"), rs.getString("cause"));
                System.out.println(description);

}
            }catch (SQLException e) {
            	System.err.println("Error retrieving patient description: " + e.getMessage());
            }
        }
	
	
	
		public int getPatientCountOfPositiveResults() {
			String sql = "SELECT COUNT(*) AS positive_count FROM Patients WHERE results = 'Positive'";
			int count = 0;

			try {
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					count = rs.getInt("positive_count");
				}

				rs.close();
				ps.close();
			} catch (SQLException e) {
				System.err.println("Error counting positive results: " + e.getMessage());
			}

			return count;
		}
		
		//este metodo esta comprobado
		public int getFemalePatientsCount() {
			String sql = "SELECT COUNT(*) AS count FROM Patients p JOIN Descriptions d ON p.description_id = d.description_id WHERE d.gender='Female'";
			int count = 0;
			
			try {
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    count = rs.getInt("count");
                }
                
                rs.close();
                ps.close();
            } catch (SQLException e) {
                System.err.println("Error counting female:' " + e.getMessage());
            }
			return count;
		}
		
		
		//este metodo esta comprobado
		public int getMalePatientsCount() {
			String sql = "SELECT COUNT(*) AS count FROM Patients p JOIN Descriptions d ON p.description_id = d.description_id WHERE d.gender='male'";
			int count = 0;
			
			try {
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    count = rs.getInt("count");
                }
                
                rs.close();
                ps.close();
            } catch (SQLException e) {
                System.err.println("Error counting female:' " + e.getMessage());
            }
			return count;
		}
		
		
		//este metodo esta comprobado
		public Set<Patients> getListOfPatientsWithDescription(String cause){
			String sql = "SELECT p.* FROM Patients p JOIN Descriptions d ON p.description_id = d.description_id WHERE d.cause =?";
			Set<Patients> patientsSet = new HashSet<>();

			
			try {
				PreparedStatement ps = c.prepareStatement(sql);
				ps.setString(1, cause);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Patients patient = new Patients(rs.getInt("patients_id"), rs.getString("patient_name"),
							rs.getString("results"), rs.getInt("trial_id"), rs.getInt("hospital_id"),
							rs.getInt("description_id"));
					patientsSet.add(patient);
				}

				rs.close();
				ps.close();
			} catch (SQLException e) {
				System.err.println("Error retrieving patients with description: " + e.getMessage());
			}
			return patientsSet;
		}
}
