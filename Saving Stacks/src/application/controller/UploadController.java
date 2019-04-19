package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.UploadManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class UploadController implements EventHandler<ActionEvent>, Initializable{

	@FXML AnchorPane uploadAnchor;
	
	@FXML ChoiceBox<String> choice1, choice2, choice3;
	
	@FXML Label csvPrompt;
	
	private String token1, token2, token3;
	
	private static final String controllerID = "UPLOAD";
	
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		UploadManager um = new UploadManager();
		
		if (token1 == null || token2 == null || token3 == null)
		{
			return;
		}
		
		
		try
		{
			throw new IOException("TODO: Remove when implemented");
			//TODO: Use FileChooser to have user upload the csv file
			//When they click on the button
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	public void set(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		
		
		
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
