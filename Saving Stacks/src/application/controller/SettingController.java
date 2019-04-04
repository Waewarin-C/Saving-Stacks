package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class SettingController implements Initializable {

	private static final String controllerID = "SETTINGS";
	
	@FXML
	AnchorPane homeAnchor;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		BottomBarController.attachBottomBar(homeAnchor.getChildren(), controllerID);
	}
	

}
