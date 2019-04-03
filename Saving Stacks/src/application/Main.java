package application;

import application.model.SettingsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application{
	
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		
		Parent root = null;
		stage = primaryStage;
		
		//TODO: May need to load resource for CSS components later on. TBA.
		
		try {
			
			SettingsManager launchManager = SettingsManager.loadSettings("data/SettingsManagerConfig");
			
			if (!launchManager.getBooleanValueWithProperty("welcome_shown_once"))
			{
				
				root = FXMLLoader.load(getClass().getResource("view/Welcome.fxml"));
				launchManager.setValueWithBooleanProperty("welcome_shown_once", true);
				
				SettingsManager.saveSettings(launchManager);
				
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
		launch(args);
	}

}
