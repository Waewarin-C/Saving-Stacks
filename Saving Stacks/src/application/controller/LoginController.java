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
import javafx.scene.layout.Pane;



/**
 * The LoginController class will take in a password from the user,
 * Construct the object and the object will then be created for 
 * future use. The password for security purposes will be hashed and
 * then stored in the config file. For security purposes, it is best
 * to call the verify method rather than the object
 * 
 * @author Moses J. Arocha
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
	
	
	//TODO: Link up to the Login view, check the user's password
	
	
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
	
	
	public void handleForgotPassword(ActionEvent arg0) {
		
		forgotPasswordLabel.setText("Pleae enter the answer to the security question in the password field:");
		questionLabel.setText(Main.settings.getValueWithProperty("user_question"));
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setText(System.getProperty("user.name") + "!");

		
	}	
	
}
