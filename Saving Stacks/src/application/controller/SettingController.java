package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.ColorTransition;
import application.model.Login;
import application.model.SettingsManager;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	private String selectedStyle;
	private Button settingButton;
	
	@FXML
	private AnchorPane settingAnchor;
	@FXML
	private Label title, security, customization, accentPrompt, passMsg;
	@FXML
	private RadioButton passwordRadio;
	@FXML
	private TextField password, securityQ, securityAns;
	@FXML
	private Pane accents;
	@FXML
	private Button darkMode, lightMode, tint0, tint1, tint2, tint3, tint4, saveButton, saveButton2, logoutButton;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		selectedStyle = Main.settings.getValueWithProperty("defined_tint_color");
		
		bottomBar = BottomBarController.attachBottomBar(settingAnchor.getChildren(), controllerID);
		
		passMsg.setVisible(false);
		passMsg.textFillProperty().bind(title.textFillProperty());
		
		settingAnchor.backgroundProperty().bind(bottomBar.getBackingPane().backgroundProperty());
		lightMode.backgroundProperty().bind(settingAnchor.backgroundProperty());
		
		accentPrompt.textFillProperty().bind(title.textFillProperty());
		
		security.textFillProperty().bind(title.textFillProperty());
		customization.textFillProperty().bind(title.textFillProperty());
		passwordRadio.textFillProperty().bind(title.textFillProperty());
		lightMode.textFillProperty().bind(title.textFillProperty());
		
		password.visibleProperty().bind(passwordRadio.selectedProperty());
		saveButton.visibleProperty().bind(passwordRadio.selectedProperty());
		
		securityQ.visibleProperty().bind(passwordRadio.selectedProperty());
		securityAns.visibleProperty().bind(passwordRadio.selectedProperty());
		saveButton2.visibleProperty().bind(passwordRadio.selectedProperty());
		
		securityAns.disableProperty().bind(securityQ.textProperty().isEmpty());
		saveButton2.disableProperty().bind(securityQ.textProperty().isEmpty());
		
		accents.backgroundProperty().bind(password.backgroundProperty());
		
		
		
		if (!Main.settings.getBooleanValueWithProperty("is_protection_enabled") || Main.settings.getValueWithProperty("user_password").equals("unset"))
		{
			logoutButton.setDisable(true);
		}
		else
		{
			logoutButton.setDisable(false);
		}
		
		for (Button b :bottomBar.getBarButtons())
		{
			if (b.getText().equalsIgnoreCase("settings"))
			{
				settingButton = b;
				break;
			}
			
			b.textFillProperty().bind(title.textFillProperty());
		}
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			darkMode.setDisable(true);
			lightMode.setDisable(false);
			
			title.setStyle("-fx-text-fill: white");
			password.setStyle("-fx-background-color: #25282f; ; -fx-background-radius: 30; -fx-text-fill: white");
			securityQ.setStyle("-fx-background-color: #25282f; ; -fx-background-radius: 30; -fx-text-fill: white");
			securityAns.setStyle("-fx-background-color: #25282f; ; -fx-background-radius: 30; -fx-text-fill: white");
			
			passwordRadio.setStyle("");
			passwordRadio.getStylesheets().add(getClass().getResource("../view/radio_dark.css").toExternalForm());
			
			
			tint0.setStyle("-fx-background-color: " + Main.settings.getValueWithProperty("default_tint_color_dark"));
			tint1.setStyle("-fx-background-color: #4f83cc");
			tint2.setStyle("-fx-background-color: #f05545");
			tint3.setStyle("-fx-background-color: #60ad5e");
			tint4.setStyle("-fx-background-color: #ff9d3f");
		}
		else
		{
			darkMode.setDisable(false);
			lightMode.setDisable(true);
			
			
			title.setStyle("-fx-text-fill: black");
			password.setStyle("-fx-background-color: #F5F5F5; -fx-background-radius: 30");
			securityQ.setStyle("-fx-background-color: #F5F5F5; -fx-background-radius: 30");
			securityAns.setStyle("-fx-background-color: #F5F5F5; -fx-background-radius: 30");
			
			passwordRadio.setStyle("");
			passwordRadio.getStylesheets().add(getClass().getResource("../view/radio_light.css").toExternalForm());
			
			tint0.setStyle("-fx-background-color: " + Main.settings.getValueWithProperty("default_tint_color_light"));
			tint1.setStyle("-fx-background-color: #002f6c");
			tint2.setStyle("-fx-background-color: #b71c1c");
			tint3.setStyle("-fx-background-color: #005005");
			tint4.setStyle("-fx-background-color: #ef6c00");
		}
		
		if (Main.settings.getBooleanValueWithProperty("is_protection_enabled"))
		{
			passwordRadio.setSelected(true);
		}
		else
		{
			passwordRadio.setSelected(false);
		}
		
		settingButton.setOpacity(1.0);
		
	}

	
	public void setSecurityQuestion(ActionEvent arg0)
	{
		
		if(securityQ.getText().isEmpty())
		{
			Main.settings.setValueWithProperty("user_question", "unset");
			Main.settings.setValueWithProperty("user_answer", "unset");
			return;
		}
		
		String new_security_question = securityQ.getText();
		String new_security_answer = securityAns.getText();
		
		Login login = new Login("password");
		login.setSecQuestion(new_security_question, new_security_answer);
					
		
		securityQ.clear();
		securityAns.clear();
		
		passMsg.setText("Security question successfully set!");
		passMsg.setVisible(true);
		
	}
	
	/**
	 * Dev mode option, type reset to set all defaults.
	 */
	public void resetData()
	{
		
		SettingsManager settings = Main.settings;
		
		settings.setValueWithBooleanProperty("is_login_active", false);
		settings.setValueWithBooleanProperty("is_protection_enabled", false);
		settings.setValueWithBooleanProperty("welcome_shown_once", false);
		settings.setValueWithBooleanProperty("is_dark_mode_enabled", false);
		settings.setValueWithProperty("user_password", "unset");
		settings.setValueWithProperty("user_question", "unset");
		settings.setValueWithProperty("user_answer", "unset");
		settings.setValueWithProperty("defined_tint_color", "unset");
		settings.setValueWithProperty("monthly_budget", "0.00");
	}
	
	
	@Override
	public void handle(ActionEvent arg0) {
		
		
		if (password.getText().equalsIgnoreCase("reset"))
		{
			resetData();
			password.clear();
			passMsg.setText("Defaults reset - relaunch");
			passMsg.setVisible(true);
			return;
		}
		
		String new_password = password.getText();
		
		Login login = new Login(new_password);
		
		login.setPassword();
		
		if (!password.getText().isEmpty())
		{
			Main.settings.setValueWithBooleanProperty("is_login_active", true);
			logoutButton.setDisable(false);
			password.clear();
			passMsg.setText("Password successfully set!");
			passMsg.setVisible(true);
		}
		else
		{
			Main.settings.setValueWithBooleanProperty("is_login_active", false);
			Main.settings.setValueWithProperty("user_password", "unset");
			logoutButton.setDisable(true);
			password.clear();
		}

	}
	
	/*
	 * Handles the logout button in settings
	 */
	public void handleLogout(ActionEvent arg0) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
			Main.stage.setScene(new Scene(root, 800, 800));
			Main.stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void tintHandle(ActionEvent arg0)
	{
		String style = tint0.getStyle().split(" ")[1];
		Main.settings.setValueWithProperty("defined_tint_color", style);
		selectedStyle = style;
		settingButton.setStyle("-fx-text-fill: " + style);
		
	}
	
	
	public void tintHandle1(ActionEvent arg0)
	{
		String style = tint1.getStyle().split(" ")[1];
		Main.settings.setValueWithProperty("defined_tint_color", style);
		selectedStyle = style;
		settingButton.setStyle("-fx-text-fill: " + style);
	}
	
	
	public void tintHandle2(ActionEvent arg0)
	{
		String style = tint2.getStyle().split(" ")[1];
		Main.settings.setValueWithProperty("defined_tint_color", style);
		selectedStyle = style;
		settingButton.setStyle("-fx-text-fill: " + style);
	}
	
	
	public void tintHandle3(ActionEvent arg0)
	{
		String style = tint3.getStyle().split(" ")[1];
		Main.settings.setValueWithProperty("defined_tint_color", style);
		selectedStyle = style;
		settingButton.setStyle("-fx-text-fill: " + style);
	}
	
	
	public void tintHandle4(ActionEvent arg0)
	{
		String style = tint4.getStyle().split(" ")[1];
		Main.settings.setValueWithProperty("defined_tint_color", style);
		selectedStyle = style;
		settingButton.setStyle("-fx-text-fill: " + style);
	}

	
	public void darkHandle(ActionEvent darkEvent)
	{
		
		ColorTransition bottomBarTransition = new ColorTransition(Duration.millis(500),
				bottomBar.getBackingPane(), Color.web("ffffff"), Color.web("33333d"), "-fx-background-color: ");
		
		
		ColorTransition textColor = new ColorTransition(Duration.millis(500), 
				title, Color.web("000000"), Color.web("ffffff"), "-fx-text-fill: ");
		
		
		ColorTransition passwordColor = new ColorTransition(Duration.millis(500), password, Color.web("F5F5F5"), 
				Color.web("25282f"), "-fx-text-fill: white; -fx-background-radius: 30; -fx-background-color: ");
		
		ColorTransition securityQColor = new ColorTransition(Duration.millis(500), securityQ, Color.web("F5F5F5"), 
				Color.web("25282f"), "-fx-text-fill: white; -fx-background-radius: 30; -fx-background-color: ");
		
		ColorTransition securityAnsColor = new ColorTransition(Duration.millis(500), securityAns, Color.web("F5F5F5"), 
				Color.web("25282f"), "-fx-text-fill: white; -fx-background-radius: 30; -fx-background-color: ");
		
		ParallelTransition pt = new ParallelTransition(bottomBarTransition, textColor, passwordColor, securityQColor, securityAnsColor );
		
		darkMode.setDisable(true);
		lightMode.setDisable(false);
		
		Main.settings.setValueWithBooleanProperty("is_dark_mode_enabled", true);
		
		
		tint0.setStyle("-fx-background-color: " + Main.settings.getValueWithProperty("default_tint_color_dark"));
		tint1.setStyle("-fx-background-color: #4f83cc");
		tint2.setStyle("-fx-background-color: #f05545");
		tint3.setStyle("-fx-background-color: #60ad5e");
		tint4.setStyle("-fx-background-color: #ff9d3f");
		
		
		if (selectedStyle.equals(Main.settings.getValueWithProperty("default_tint_color_light")))
		{
			selectedStyle = Main.settings.getValueWithProperty("default_tint_color_dark");
			Main.settings.setValueWithProperty("defined_tint_color", selectedStyle);
		}
		else if (selectedStyle.equals("#002f6c"))
		{
			selectedStyle = "#4f83cc";
			Main.settings.setValueWithProperty("defined_tint_color", selectedStyle);
		}
		else if (selectedStyle.equals("#b71c1c"))
		{
			selectedStyle = "#f05545";
			Main.settings.setValueWithProperty("defined_tint_color", selectedStyle);
		}
		else if (selectedStyle.equals("#005005"))
		{
			selectedStyle = "#60ad5e";
			Main.settings.setValueWithProperty("defined_tint_color", selectedStyle);
		}
		else
		{
			selectedStyle = "#ff9d3f";
			Main.settings.setValueWithProperty("defined_tint_color", selectedStyle);
		}
		
		settingButton.setStyle("-fx-text-fill: " + selectedStyle);
		
		
		passwordRadio.setStyle("");
		passwordRadio.getStylesheets().clear();
		passwordRadio.getStylesheets().add(getClass().getResource("../view/radio_dark.css").toExternalForm());
		
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
		
		ColorTransition securityQColor = new ColorTransition(Duration.millis(500), securityQ, Color.web("25282f"), 
				Color.web("F5F5F5"), "-fx-background-radius: 30; -fx-background-color: ");
		
		ColorTransition securityAnsColor = new ColorTransition(Duration.millis(500), securityAns, Color.web("25282f"), 
				Color.web("F5F5F5"), "-fx-background-radius: 30; -fx-background-color: ");
		
		ParallelTransition pt = new ParallelTransition(bottomBarTransition, textColor, passwordColor, securityQColor, securityAnsColor);

		darkMode.setDisable(false);
		lightMode.setDisable(true);
		
		
		Main.settings.setValueWithBooleanProperty("is_dark_mode_enabled", false);
		
		
		
		tint0.setStyle("-fx-background-color: " + Main.settings.getValueWithProperty("default_tint_color_light"));
		tint1.setStyle("-fx-background-color: #002f6c");
		tint2.setStyle("-fx-background-color: #b71c1c");
		tint3.setStyle("-fx-background-color: #005005");
		tint4.setStyle("-fx-background-color: #ef6c00");
		
		
		if (selectedStyle.equals(Main.settings.getValueWithProperty("default_tint_color_dark")))
		{
			selectedStyle = Main.settings.getValueWithProperty("default_tint_color_light");
			Main.settings.setValueWithProperty("defined_tint_color", selectedStyle);
		}
		else if (selectedStyle.equals("#4f83cc"))
		{
			selectedStyle = "#002f6c";
			Main.settings.setValueWithProperty("defined_tint_color", selectedStyle);
		}
		else if (selectedStyle.equals("#f05545"))
		{
			selectedStyle = "#b71c1c";
			Main.settings.setValueWithProperty("defined_tint_color", selectedStyle);
		}
		else if (selectedStyle.equals("#60ad5e"))
		{
			selectedStyle = "#005005";
			Main.settings.setValueWithProperty("defined_tint_color", selectedStyle);
		}
		else
		{
			selectedStyle = "#ef6c00";
			Main.settings.setValueWithProperty("defined_tint_color", selectedStyle);
		}
		
		settingButton.setStyle("-fx-text-fill: " + selectedStyle);
		
		passwordRadio.setStyle("");
		passwordRadio.getStylesheets().clear();
		passwordRadio.getStylesheets().add(getClass().getResource("../view/radio_light.css").toExternalForm());
		
		pt.play();
	}
	
	public void radioToggle(ActionEvent event)
	{
		
		
		
		if (passwordRadio.isSelected())
		{
			Main.settings.setValueWithBooleanProperty("is_protection_enabled", true);
			Main.settings.setValueWithBooleanProperty("is_login_active", true);
			logoutButton.setDisable(false);
		}
		else
		{
			Main.settings.setValueWithBooleanProperty("is_protection_enabled", false);
			Main.settings.setValueWithBooleanProperty("is_login_active", false);
			passMsg.setVisible(false);
			logoutButton.setDisable(true);
		}
	}

}
