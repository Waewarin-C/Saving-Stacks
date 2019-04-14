package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;


public class HomeController implements Initializable {

	private static final String controllerID = "HOME";
	
	@FXML
	AnchorPane homeAnchor;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			homeAnchor.setStyle("-fx-background-color: #33333d");
		}

		BottomBarController.attachBottomBar(homeAnchor.getChildren(), controllerID);
		
	}

}
