package application;

import application.model.LaunchManager;
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
		
		//TODO: May need to load resource for CSS components later on. TBA.
		
		try {
			
			LaunchManager launchManager = LaunchManager.loadConfig();
			
			if (!launchManager.getValueWithProperty("welcome_shown_once"))
			{
				
				//TODO: Might be better to place the value setting within the welcome's handle.
				root = FXMLLoader.load(getClass().getResource("view/Welcome.fxml"));
				launchManager.setValueWithProperty("welcome_shown_once", true);
				
				LaunchManager.saveConfig(launchManager);
				
			}
			else
			{
				root = FXMLLoader.load(getClass().getResource("view/Home.fxml"));
			}
			
			primaryStage.setScene(new Scene(root, 800, 800));
			primaryStage.show();

			stage = primaryStage;
			
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
