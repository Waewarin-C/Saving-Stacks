package application.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

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


public class LoginController implements EventHandler<ActionEvent>
{
	@FXML 
	Button forgotPassword, login;
	
	@FXML
	PasswordField passwordField;
	
	@FXML
	Label wrongPassword;
	//TODO: Link up to the Login view, check the user's password

	
	//TODO: Call on the settings static variable in main to check the password to decide page switch
	
		/* Dear Moses, the SettingsManager class has the following object method:
		 * 
		 * public String getValueWithProperty(String key)
		 * 
		 * The config itself has this key: user_password
		 * Use the available settings variable from main as follows:
		 * 
		 * Main.settings.getValueWithProperty("user_password");
		 * 
		 * That way you can get the value instantly from the settings
		 * HashMap :)
		 * 
		 * Feel free to change how the pass is stored in the config via the 
		 * following method in the SettingController class:
		 * 
		 * handle() line 91-96. The TextField is called "password". 
		 * 
		 */
	
	//TODO: Make sure that login can handle false user information
		
	
	@Override
	public void handle(ActionEvent event) {
		// Handles the Password Login
		String password = passwordField.getText();
		
		String stored_password = Main.settings.getValueWithProperty("user_password");
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] messageDigest = md.digest(password.getBytes());
			
			BigInteger num = new BigInteger(1, messageDigest);
			String hashtext = num.toString(16);
			
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			
			System.out.println(hashtext);
			
			if(password.equals(stored_password)) {
				System.out.println("Hello");
			}
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void handleForgotPassword(ActionEvent event) {
		
	}	
	
}
