package application;

import java.io.IOException;


import application.model.SettingsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application{
	
	public static Stage stage;
	public static SettingsManager settings;
	
	@Override
	public void start(Stage primaryStage) {
		
		Parent root = null;
		stage = primaryStage;
		//TODO: Edit the icon and title name
		//stage.getIcons().add(new Image("data/logo.png"));
		//stage.setTitle("Saving Stacks");
		
		try {
			
			
			
			if (!settings.getBooleanValueWithProperty("welcome_shown_once"))
			{
				
				root = FXMLLoader.load(getClass().getResource("view/Welcome.fxml"));
				settings.setValueWithBooleanProperty("welcome_shown_once", true);
				
			}
			else if (!settings.getValueWithProperty("user_password").equals("unset") && settings.getBooleanValueWithProperty("is_login_active"))
			{
				//The user password equals flag is meant for debug purposes.
				root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
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
