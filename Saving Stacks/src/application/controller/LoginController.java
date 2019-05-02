package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.Login;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



/**
 * The LoginController class will act as the middle man between
 * the login object and the Login.fxml. It creates a login object
 * and calls the correct functions to check if the password is correct
 * and behaves accordingly.
 * 
 * @author Moses J. Arocha - qiv737
 *
 */

public class LoginController implements EventHandler<ActionEvent>, Initializable
{
	@FXML
	Label wrongPassword, name, greeting, forgotPasswordLabel, questionLabel, loginError;
	
	@FXML 
	Button forgotPassword, login;
	
	@FXML
	PasswordField passwordField;
	
	@FXML
	Pane loginAnchor;
	
	@FXML
	ImageView logoImage;
	
		
	/**
	 * Handle method deals with the Login button,
	 * depends on the Login class, will redirect
	 * to the Home.fxml if password is accepted.
	 * @param event - ActionEvent, the login button
	 */
	@Override
	public void handle(ActionEvent event) {
		// Handles the Password Login
		String password = passwordField.getText();
		
		Login login = new Login(password);
		
		try {
			loginError.setText("");
			if (login.checkHashValue()) {
				Parent root = FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
				Main.stage.setScene(new Scene(root, 800, 800));
				Main.stage.show();
			}
			else {
				if(login.getLoginAttempts() == 3) {
					loginError.setText("If next login attempt fails it will lock the account for 5 seconds.");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * HandleForgotPassword method deals with with the forgot password
	 * button, it will display the security question found in the 
	 * SettingsManagerConfig file.
	 * @param arg0, ActionEvent, the forgotPassword button 
	 */
	public void handleForgotPassword(ActionEvent arg0) {
		
		forgotPasswordLabel.setText("Pleae enter the answer to the security question in the password field:");
		questionLabel.setText(Main.settings.getValueWithProperty("user_question"));	
		
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 * initialize displays the username and displays the logo in
	 * accordance to which background mode is set.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setText(System.getProperty("user.name") + "!");
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			Image img = new Image("file:./data/savinglogo_dark.jpg");
			logoImage.setImage(img);
			logoImage.setFitWidth(265);
			logoImage.setFitHeight(180);
			logoImage.setX(85);
			logoImage.setY(44);
			
			name.setTextFill(Color.WHITE);
			greeting.setTextFill(Color.WHITE);
			forgotPasswordLabel.setTextFill(Color.WHITE);
			questionLabel.setTextFill(Color.WHITE);
			loginAnchor.setStyle("-fx-background-color: #33333d");
			forgotPassword.setStyle("-fx-background-color: #33333d; -fx-text-fill: white");
			
			passwordField.setStyle("-fx-background-color: #25282f; -fx-text-fill: white; -fx-background-radius: 30"); 
			
			
		}		
		else
		{
			Image img = new Image("file:./data/logo.jpg");
			logoImage.setImage(img);
			logoImage.setFitWidth(265);
			logoImage.setFitHeight(180);
			logoImage.setX(85);
			logoImage.setY(44);
		}
		
		
	}
	
}
