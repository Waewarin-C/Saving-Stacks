package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.ColorTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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

		
		settingAnchor.backgroundProperty().bind(bottomBar.getBackingPane().backgroundProperty());
		lightMode.backgroundProperty().bind(settingAnchor.backgroundProperty());
		
		security.textFillProperty().bind(title.textFillProperty());
		customization.textFillProperty().bind(title.textFillProperty());
		passwordRadio.textFillProperty().bind(title.textFillProperty());
		lightMode.textFillProperty().bind(title.textFillProperty());
		
		password.visibleProperty().bind(passwordRadio.selectedProperty());
		
		accents.backgroundProperty().bind(password.backgroundProperty());
		
		for (Button b : bottomBar.getBarButtons())
		{
			b.textFillProperty().bind(title.textFillProperty());
		}
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			
			darkMode.setDisable(true);
			lightMode.setDisable(false);
			//load dark styling from config.
			
		}
		else
		{
			darkMode.setDisable(false);
			lightMode.setDisable(true);
			//load light styling from config.
		}
		
		if (Main.settings.getBooleanValueWithProperty("is_protection_enabled"))
		{
			passwordRadio.setSelected(true);
		}
		else
		{
			passwordRadio.setSelected(false);
		}
		
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		
		Main.settings.setValueWithProperty("user_password", String.valueOf(password.getText()));
		//TODO: Display message with successful password change.
	}
	
	public void darkHandle(ActionEvent darkEvent)
	{
		
		ColorTransition bottomBarTransition = new ColorTransition(Duration.millis(500),
				bottomBar.getBackingPane(), Color.web("ffffff"), Color.web("33333d"), "-fx-background-color: ");
		
		
		ColorTransition textColor = new ColorTransition(Duration.millis(500), 
				title, Color.web("000000"), Color.web("ffffff"), "-fx-text-fill: ");
		
		
		ColorTransition passwordColor = new ColorTransition(Duration.millis(500), password, Color.web("F5F5F5"), 
				Color.web("25282f"), "-fx-text-fill: white; -fx-background-radius: 30; -fx-background-color: ");
		
		
		
		ParallelTransition pt = new ParallelTransition(bottomBarTransition, textColor, passwordColor);
		
		darkMode.setDisable(true);
		lightMode.setDisable(false);
		
		Main.settings.setValueWithBooleanProperty("is_dark_mode_enabled", true);
		
		//Change css styling in config to dark
		
		pt.play();
	}
	
	public void lightHandle(ActionEvent darkEvent)
	{
		
		ColorTransition bottomBarTransition = new ColorTransition(Duration.millis(500), bottomBar.getBackingPane(), 
				Color.web("33333d"), Color.web("ffffff"), "-fx-background-color: ");
		
		
		
		ColorTransition textColor = new ColorTransition(Duration.millis(500), title, 
				Color.web("ffffff"), Color.web("000000"), "-fx-text-fill: ");
		
		
		ColorTransition passwordColor = new ColorTransition(Duration.millis(500), password, Color.web("25282f"), 
				Color.web("F5F5F5"), "-fx-background-radius: 30; -fx-background-color: ");
		
		
		
		ParallelTransition pt = new ParallelTransition(bottomBarTransition, textColor, passwordColor);

		darkMode.setDisable(false);
		lightMode.setDisable(true);
		
		
		Main.settings.setValueWithBooleanProperty("is_dark_mode_enabled", false);
		
		//Change css styling in config to light
		
		pt.play();
	}
	
	
	public void radioToggle(ActionEvent event)
	{
		if (passwordRadio.isSelected())
		{
			Main.settings.setValueWithBooleanProperty("is_protection_enabled", true);
		}
		else
		{
			Main.settings.setValueWithBooleanProperty("is_protection_enabled", false);
		}
	}

}
