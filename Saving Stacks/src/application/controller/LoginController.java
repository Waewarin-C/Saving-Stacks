package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

/**
 * The LoginController class will take in a password from the user,
 * Construct the object and the object will then be created for 
 * future use. The password for security purposes will be hashed and
 * then stored in the config file. For security purposes, it is best
 * to call the verify method rather than the object
 * 
 * @author moses
 *
 */


public class LoginController implements Initializable, EventHandler<ActionEvent>
{

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
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
