package jdc;

import java.sql.Connection;
import java.sql.Statement;

public class connectionManager {
	
	private Connection c;
	
	public connectionManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = java.sql.DriverManager.getConnection("jdbc:sqlite:db/jdc.db"); //tienes que cambiar aqui para usar el link correcto con TU sql! 
			c.createStatement().execute("PRAGMA foreign_keys = ON;"); //esto es para activar las claves foraneas en sqlite, si usas otro sql no es necesario
			System.out.println("Connection to database established.");
			createTables(); //llama a la funcion para crear las tablas
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
private void createTables() {
		try {
			Statement s = c.createStatement();
			
			String TrialTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL" +
                    ");";
			            s.execute(TrialTable);
			            
			            //aqui tienes que crear todas las tablas desde el sql!!!
			            //usando siempre el mismo metodos, tienes que poner el azul todo el DDL de SQLite
			            
			            //modify the database - execute.update 
			            // to do a select (does change the database) use executeQuery or just an execute. 
		}
		catch (Exception e) {
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
