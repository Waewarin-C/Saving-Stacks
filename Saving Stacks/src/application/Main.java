package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//TODO: May need to load resource for CSS components later on. TBA.
			Parent root = FXMLLoader.load(getClass().getResource("view/Home.fxml"));
			//primaryStage.setResizable(false);
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
