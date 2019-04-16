package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class UploadController implements EventHandler<ActionEvent>, Initializable{

	@FXML AnchorPane uploadAnchor;
	
	private static final String controllerID = "UPLOAD";
	
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			uploadAnchor.setStyle("-fx-background-color: #33333d");
		}

		BottomBarController.attachBottomBar(uploadAnchor.getChildren(), controllerID);
	}

	
}
