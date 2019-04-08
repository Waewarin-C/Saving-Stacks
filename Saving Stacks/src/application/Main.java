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
			
			SettingsManager settingManager = SettingsManager.loadSettings("data/SettingsManagerConfig");
			
			if (!settingManager.getBooleanValueWithProperty("welcome_shown_once"))
			{
				
				root = FXMLLoader.load(getClass().getResource("view/Welcome.fxml"));
				settingManager.setValueWithBooleanProperty("welcome_shown_once", true);
				
			}
			else
			{
				root = FXMLLoader.load(getClass().getResource("view/Home.fxml"));
			}
			
			primaryStage.setScene(new Scene(root, 800, 800));
			primaryStage.show();

			settings = settingManager;
			
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
		try {
			SettingsManager.saveSettings(settings, "data/SettingsManagerConfig");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
