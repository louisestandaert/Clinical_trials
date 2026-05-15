package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainGUI extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("JavaFx has started.");
		Parent root = FXMLLoader.load(getClass().getResource("/guiFolder/viewGUI/Welcome.fxml"));
		
		System.out.println("FXML file loaded successfully.");
		Scene scene = new Scene(root,800,500);
		
		primaryStage.setTitle("Clinical Trials Database");
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
		primaryStage.show();
		
		System.out.println("Window should be now visible.");
	}

	public static void main(String[] args) {
		System.out.println("Launching JavaFX application...");
		launch(args);
	}
	
 
}
