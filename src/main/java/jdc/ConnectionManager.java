package jdc;

import java.sql.Connection;
import java.sql.Statement;

public class ConnectionManager {

	private Connection c;

	public ConnectionManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = java.sql.DriverManager.getConnection("\"jdbc:sqlite:proyecto_universidad.db\"");
			c.createStatement().execute("PRAGMA foreign_keys = ON;"); // esto es para activar las claves foraneas en
																		// sqlite, si usas otro sql no es necesario
			System.out.println("Connection to database established.");
			createTables();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	private void createTables() {
		try {
			Statement s = c.createStatement();

			String TrialTable = "CREATE TABLE IF NOT EXISTS Trials (" + "trial_id INTEGER NOT NULL UNIQUE, "
					+ "trial_name TEXT NOT NULL, " + "starting_date INTEGER NOT NULL, " + "PRIMARY KEY(trial_id)"
					+ ");";
	

			String DoctorsTable = "CREATE TABLE IF NOT EXISTS Doctors (" + "doctor_id INTEGER NOT NULL UNIQUE, "
					+ "doctor_name TEXT NOT NULL, " + "doctor_gender TEXT NOT NULL, " + "trial_id INTEGER NOT NULL, "
					+ "PRIMARY KEY(doctor_id), " + "FOREIGN KEY(trial_id) REFERENCES Trials(trial_id)" + ");";

			
			String HospitalsTable = "CREATE TABLE IF NOT EXISTS Hospitals ("
					+ "hospital_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " + "hospital_name TEXT NOT NULL, "
					+ "city TEXT NOT NULL" + ");";

			
			String DescriptionsTable = "CREATE TABLE IF NOT EXISTS Descriptions ("
					+ "description_id INTEGER NOT NULL PRIMARY KEY, " + "gender TEXT NOT NULL, "
					+ "cause TEXT NOT NULL, " + "patient_id INTEGER NOT NULL" + ");";

			
			String PatientsTable = "CREATE TABLE IF NOT EXISTS Patients (" + "patients_id INTEGER NOT NULL PRIMARY KEY, "
					+ "patient_name TEXT NOT NULL, " + "results TEXT NOT NULL, " + "trial_id INTEGER NOT NULL, "
					+ "hospital_id INTEGER NOT NULL, " + "description_id INTEGER NOT NULL, "
					+ "FOREIGN KEY(description_id) REFERENCES Descriptions(description_id), "
					+ "FOREIGN KEY(hospital_id) REFERENCES Hospitals(hospital_id), "
					+ "FOREIGN KEY(trial_id) REFERENCES Trials(trial_id)" + ");";

			
			String TrialHospitalRelacionTable = "CREATE TABLE IF NOT EXISTS Trial_Hospital (" + "trial_id INTEGER NOT NULL, "
					+ "hospital_id INTEGER NOT NULL, " + "PRIMARY KEY(trial_id, hospital_id), "
					+ "FOREIGN KEY(hospital_id) REFERENCES Hospitals(hospital_id), "
					+ "FOREIGN KEY(trial_id) REFERENCES Trials(trial_id)" + ");";

			
			try {
				java.sql.Statement st = c.createStatement();
				s.execute(TrialTable);
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

}
