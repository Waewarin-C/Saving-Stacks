package application;

import java.io.IOException;


import application.model.SettingsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application{
	
	public static Stage stage;
	public static SettingsManager settings;
	
	@Override
	public void start(Stage primaryStage) {
		
		Parent root = null;
		stage = primaryStage;
		
		//TODO: May need to load resource for CSS components later on. TBA.
		
		try {
			

			
			if (!settings.getBooleanValueWithProperty("welcome_shown_once"))
			{
				
				root = FXMLLoader.load(getClass().getResource("view/Welcome.fxml"));
				settings.setValueWithBooleanProperty("welcome_shown_once", true);
				
			}
			else
			{
				root = FXMLLoader.load(getClass().getResource("view/Home.fxml"));
			}
			
			primaryStage.setScene(new Scene(root, 800, 800));
			primaryStage.show();


			
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	
	
	public static void main(String[] args) {
		
		SettingsManager settingManager = null;
		//load settings right before launch. Required for settings to remain persistent.
		try {
			
			settingManager = SettingsManager.loadSettings("data/SettingsManagerConfig");
			
			settings = settingManager;
		
			launch(args);
			
			SettingsManager.saveSettings(settings, "data/SettingsManagerConfig");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
