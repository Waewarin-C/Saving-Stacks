package application.controller;

import java.io.File;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class UploadController implements EventHandler<ActionEvent>, Initializable{

	@FXML private AnchorPane uploadAnchor;
	
	@FXML private ChoiceBox<String> choice1, choice2, choice3;
	
	@FXML private Label csvPrompt;
	
	@FXML private Button fileButton;
	
	private String token1, token2, token3;
	
	private static final String controllerID = "UPLOAD";
	
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		if (token1 == null || token2 == null || token3 == null)
		{
			return;
		}
		
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
		fc.setTitle("Select File to Upload.");
		UploadManager.readFile(fc.showOpenDialog(new Stage()));
	}

	
	public void set(ActionEvent arg0) {
	
		
		fileButton.setDisable(false);
		
		
		token1 = choice1.getValue();
		token2 = choice2.getValue();
		token3 = choice3.getValue();
		
		
		csvPrompt.setText(String.format("%s %s,%s,%s", "Specified csv format: ", token1.toLowerCase(), token2.toLowerCase(), token3.toLowerCase()));
		csvPrompt.setVisible(true);
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			uploadAnchor.setStyle("-fx-background-color: #33333d");
		}

		csvPrompt.setVisible(false);
		fileButton.setDisable(true);
		
		BottomBarController.attachBottomBar(uploadAnchor.getChildren(), controllerID);
		
		choice1.getItems().add("Date");
		choice1.getItems().add("Amount");
		choice1.getItems().add("Title");
		
		choice2.getItems().add("Date");
		choice2.getItems().add("Amount");
		choice2.getItems().add("Title");
		
		choice3.getItems().add("Date");
		choice3.getItems().add("Amount");
		choice3.getItems().add("Title");
		
		
	}
	
	
}
