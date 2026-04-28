package jdbc;

import java.sql.Connection;
import java.sql.Statement;

public class ConnectionManager {

	private Connection c;

	public ConnectionManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = java.sql.DriverManager.getConnection("jdbc:sqlite:ClinicalTrials.db");
			c.createStatement().execute("PRAGMA foreign_keys = ON;"); // esto es para activar las claves foraneas en
																		// sqlite, si usas otro sql no es necesario
			System.out.println("Connection to database established.");
			createTables(); 
			initializeData();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	private void createTables() {
		try {
			Statement s = c.createStatement();
			
			String trialTable = "CREATE TABLE IF NOT EXISTS Trials ("
			        + "trial_id INTEGER NOT NULL UNIQUE, "
			        + "trial_name TEXT NOT NULL, " 
			        + "starting_date TEXT NOT NULL, " 
			        + "duration_days INTEGER NOT NULL, "
			        + "budget REAL NOT NULL, " 
			        + "target_patients INTEGER NOT NULL, " 
			        + "PRIMARY KEY(trial_id));";

			String DoctorsTable = "CREATE TABLE IF NOT EXISTS Doctors ("
				    + "doctor_id INTEGER NOT NULL UNIQUE, "
				    + "doctor_name TEXT NOT NULL, "         
				    + "doctor_gender TEXT NOT NULL, "       
				    + "doctor_specialty TEXT NOT NULL, "   
				    + "trial_id INTEGER NOT NULL, "        
				    + "PRIMARY KEY(doctor_id), "
				    + "FOREIGN KEY(trial_id) REFERENCES Trials(trial_id));";

			String HospitalsTable = "CREATE TABLE IF NOT EXISTS Hospitals ("
			        + "hospital_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " 
			        + "hospital_name TEXT NOT NULL, "
			        + "city TEXT NOT NULL);";

			String DescriptionsTable = "CREATE TABLE IF NOT EXISTS Descriptions ("
			        + "description_id INTEGER NOT NULL PRIMARY KEY, " 
			        + "gender TEXT NOT NULL, "
			        + "cause TEXT NOT NULL, " 
			        + "patient_id INTEGER NOT NULL);";

			String PatientsTable = "CREATE TABLE IF NOT EXISTS Patients (" 
			        + "patients_id INTEGER NOT NULL PRIMARY KEY, "
			        + "patient_name TEXT NOT NULL, " 
			        + "results TEXT NOT NULL, " 
			        + "trial_id INTEGER NOT NULL, "
			        + "hospital_id INTEGER NOT NULL, " 
			        + "description_id INTEGER NOT NULL, "
			        + "FOREIGN KEY(description_id) REFERENCES Descriptions(description_id), "
			        + "FOREIGN KEY(hospital_id) REFERENCES Hospitals(hospital_id), "
			        + "FOREIGN KEY(trial_id) REFERENCES Trials(trial_id));";

			String TrialHospitalRelacionTable = "CREATE TABLE IF NOT EXISTS Trial_Hospital (" 
			        + "trial_id INTEGER NOT NULL, "
			        + "hospital_id INTEGER NOT NULL, " 
			        + "PRIMARY KEY(trial_id, hospital_id), "
			        + "FOREIGN KEY(hospital_id) REFERENCES Hospitals(hospital_id), "
			        + "FOREIGN KEY(trial_id) REFERENCES Trials(trial_id));";
			
			try {
				java.sql.Statement st = c.createStatement();
				s.execute(trialTable);
				st.execute(DescriptionsTable);
				st.execute(DoctorsTable);
				st.execute(HospitalsTable);
				st.execute(PatientsTable);
				st.execute(TrialHospitalRelacionTable);
				System.out.println("Todas las tablas han sido verificadas/creadas.");
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}

		
			// modify the database - execute.update
			// to do a select (does change the database) use executeQuery or just an
			// execute.
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public Connection getConnection() {
		return c;
	}

	public void closeConnection() {
		try {
			if (c != null) {
				c.close();
				System.out.println("Connection to database closed.");
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void initializeData() {
		try (Statement stmt = c.createStatement()) {
			
			
			/*stmt.execute("DROP TABLE IF EXISTS Trials;");
			stmt.execute("DROP TABLE IF EXISTS Doctors;");
			stmt.execute("DROP TABLE IF EXISTS Hospitals;");
			stmt.execute("DROP TABLE IF EXISTS Descriptions;");
			stmt.execute("DROP TABLE IF EXISTS Patients;");*/
			
			
			try {
	            stmt.executeUpdate("ALTER TABLE Doctors ADD COLUMN doctor_specialty TEXT DEFAULT 'UNKNOWN'");
	        } catch (Exception e) {
	            System.out.println("doctor_specialty already exists.");
	        }
			
			
			stmt.executeUpdate(
					"INSERT OR IGNORE INTO Trials (trial_id, trial_name, starting_date, duration_days,"
					+ " budget, target_patients) VALUES (1, 'Trial A', '2025-01-01', 180, 1000.00, 200)");
			
			//anadir hospital
			stmt.executeUpdate("INSERT OR IGNORE INTO Hospitals (hospital_name, city, hospital_id) VALUES ('Hospital A', 'Madrid',1)");
			
			//anadir doctor
			stmt.executeUpdate("INSERT OR IGNORE INTO Doctors (doctor_id, doctor_name, doctor_gender, doctor_specialty, trial_id) VALUES (1, 'Dr. Smith','female','surgeon', 1)");
			
			//anadir descripcion
			stmt.executeUpdate("INSERT OR IGNORE INTO Descriptions (description_id, gender, cause, patient_id) VALUES (1, 'male', 'cause A', 1)");
			
			//anadir paciente
			stmt.executeUpdate("INSERT OR IGNORE INTO Patients (patients_id, patient_name, results, trial_id, hospital_id, description_id) VALUES (1, 'John Doe', 'positive', 1, 1, 1)");
			
			System.out.println("Initial data has been inserted.");
		
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}
