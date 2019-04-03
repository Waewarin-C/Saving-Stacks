package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;


public class HomeController implements Initializable {

	@FXML
	AnchorPane homeAnchor;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		BottomBarController.attachBottomBar(homeAnchor.getChildren());
		
	}

}
