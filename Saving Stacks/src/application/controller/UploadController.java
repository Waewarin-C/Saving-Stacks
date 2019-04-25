package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.UploadManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class UploadController implements EventHandler<ActionEvent>, Initializable{

	@FXML private AnchorPane uploadAnchor;
	
	@FXML private Label warning, uploadPrompt;
	
	@FXML private Button fileButton;

	@FXML private ListView listView;
	
	
	private static final String BACKGROUND_COLOR_STYLE = "-fx-background-color: #33333d";
	private static final String controllerID = "UPLOAD";
	
	
	@Override
	public void handle(ActionEvent arg0) {
		
		
		FileChooser fc = new FileChooser();
		
		
		fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
		fc.setTitle("Select File to Upload.");
		UploadManager.readFile(fc.showOpenDialog(new Stage()));
		
		if (fc.getInitialFileName() == null)
		{
			warning.setVisible(true);
		}
		else
		{
			listView.toFront();
			fileButton.setVisible(false);
			warning.setVisible(false);
		}
	}

	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		listView.toBack();
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			uploadAnchor.setStyle(BACKGROUND_COLOR_STYLE);
			
			uploadPrompt.setStyle("-fx-text-fill: white");
			warning.textFillProperty().bind(uploadPrompt.textFillProperty());
			
			listView.setStyle("-fx-background-color: #25282f");
			
		}

		warning.setVisible(true);
		
		BottomBarController.attachBottomBar(uploadAnchor.getChildren(), controllerID);

		
	}
	
	
}
