package application;


import application.model.SettingsManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import java.util.concurrent.*;

public class Main extends Application {
	
	public static Stage stage;
	public static SettingsManager settings;
	
	@Override
	public void start(Stage primaryStage) {
		
		Parent root = null;
		stage = primaryStage;

		stage.getIcons().add(new Image("file:./data/logo.png"));
		stage.setTitle("Saving Stacks");
		
		try {
			
			
			
			if (!settings.getBooleanValueWithProperty("welcome_shown_once"))
			{
				
				root = FXMLLoader.load(getClass().getResource("view/Welcome.fxml"));
				root.setEffect(new DropShadow());
				
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
		
		
		//load settings right before launch. Required for settings to remain persistent.
		Runnable task = new Runnable() {

			@Override
			public void run() {
				
				settings = SettingsManager.loadSettings("data/SettingsManagerConfig.txt");
				
			}
			
		};
		
		ExecutorService executorservice = Executors.newCachedThreadPool();
		executorservice.execute(task);
			
		executorservice.shutdown();
		launch(args);
			
		SettingsManager.saveSettings(settings, "data/SettingsManagerConfig.txt");
				
	}

}
