package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.ColorTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class SettingController implements Initializable, EventHandler<ActionEvent>{

	private static final String controllerID = "SETTINGS";
	private BottomBarController bottomBar;
	
	@FXML
	AnchorPane settingAnchor;
	@FXML
	Label title, security, customization;
	@FXML
	RadioButton passwordRadio;
	@FXML
	TextField password;
	@FXML
	Pane accents;
	@FXML
	Button darkMode, lightMode;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		bottomBar = BottomBarController.attachBottomBar(settingAnchor.getChildren(), controllerID);
		//TODO: Condense.
		settingAnchor.backgroundProperty().bind(bottomBar.getBackingPane().backgroundProperty());
		security.textFillProperty().bind(title.textFillProperty());
		customization.textFillProperty().bind(title.textFillProperty());
		passwordRadio.textFillProperty().bind(title.textFillProperty());
		accents.backgroundProperty().bind(password.backgroundProperty());
		lightMode.backgroundProperty().bind(settingAnchor.backgroundProperty());
		lightMode.textFillProperty().bind(title.textFillProperty());
		
		
		for(Button b : bottomBar.getBarButtons())
		{
			b.backgroundProperty().bind(bottomBar.getBackingPane().backgroundProperty());
			b.textFillProperty().bind(title.textFillProperty());
		}
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		
		
		
		
	}
	
	public void darkHandle(ActionEvent darkEvent)
	{
		ColorTransition bottomBarTransition = new ColorTransition(Duration.millis(500), bottomBar.getBackingPane(), Color.web("ffffff"), Color.web("33333d"), "-fx-background-color: ");
		ColorTransition textColor = new ColorTransition(Duration.millis(500), title, Color.web("000000"), Color.web("ffffff"), "-fx-text-fill: ");
		ColorTransition passwordColor = new ColorTransition(Duration.millis(500), password, Color.web("F5F5F5"), Color.web("25282f"), "-fx-text-fill: white; -fx-background-radius: 30; -fx-background-color: ");
		
		
		
		ParallelTransition pt = new ParallelTransition(bottomBarTransition, textColor, passwordColor);
		
		darkMode.setDisable(true);
		lightMode.setDisable(false);
		
		pt.play();
	}
	
	public void lightHandle(ActionEvent darkEvent)
	{
		
		ColorTransition bottomBarTransition = new ColorTransition(Duration.millis(500), bottomBar.getBackingPane(), Color.web("33333d"), Color.web("ffffff"), "-fx-background-color: ");
		ColorTransition textColor = new ColorTransition(Duration.millis(500), title, Color.web("ffffff"), Color.web("000000"), "-fx-text-fill: ");
		ColorTransition passwordColor = new ColorTransition(Duration.millis(500), password, Color.web("25282f"), Color.web("F5F5F5"), "-fx-background-radius: 30; -fx-background-color: ");
		
		
		
		ParallelTransition pt = new ParallelTransition(bottomBarTransition, textColor, passwordColor);

		darkMode.setDisable(false);
		lightMode.setDisable(true);
		
		pt.play();
	}

}
