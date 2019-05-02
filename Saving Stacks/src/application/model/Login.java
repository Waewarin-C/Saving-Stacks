package application.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import application.Main;

/**
 * The Login class will take in a password from the user,
 * Construct the object and the object will then be created for 
 * verification. The password for security purposes will be hashed and
 * then stored in the SettingsManagerConfig file. For security purposes,
 *  it is best to call the checkHashValue method rather than the value.
 * 
 * @author Moses J. Arocha - qiv737
 *
 */
public class Login {
	
	private String secretValue;
	private int loginAttempts;
	

	/**
	 * Login constructor, creates the login object which can
	 * be used to check password.
	 * @param secretValue - String, the password
	 */
	public Login(String secretValue) {
		this.secretValue = secretValue;
	}
	
	
	/**
	 * Additional Login constructor, requires both a password
	 * and the number of loginAttempts used.
	 * @param secretValue - String, the password
	 * @param loginAttempts - int, the login attemps used
	 */
	public Login(String secretValue, int loginAttempts) {
		this.secretValue = secretValue;
		this.loginAttempts = loginAttempts;
	}
	
	
	//TODO: create password policies
	/**
	 * Method was supposed to check if the password contained
	 * a certain value for password strength, but I failed and 
	 * ran out of time. I am sorry, my dog needed to play fetch
	 * and I couldn't resist.
	 * @return - boolean, true if requirements meant, false otherwise
	 */
	public boolean passwordStrength() {
		if(getSecretValue().matches("[123456789]*")) {
			if(getSecretValue().matches("[!@#$%^&*()-+=]*")) {
				return true;
			}
			return false;
		}
		
		return false;
	}
	
	
	/**
	 * checkHashValue method will check to make sure that
	 * either the password or the stored answer provided
	 * matches the one given by the user.  
	 * @return - boolean, true if password matches, false otherwise
	 * @throws InterruptedException
	 */
	public boolean checkHashValue() throws InterruptedException {
		
		String stored_password = Main.settings.getValueWithProperty("user_password");
		String stored_answer = Main.settings.getValueWithProperty("user_answer");
		
		loginAttempts = Integer.parseInt(Main.settings.getValueWithProperty("login_attempts"));
		

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] messageDigest = md.digest(getSecretValue().getBytes());
			
			BigInteger num = new BigInteger(1, messageDigest);
			String hashtext = num.toString(16);
			
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			
			if(hashtext.equals(stored_password) || stored_answer.equals(hashtext)) {
				loginAttempts = 1;
				Main.settings.setValueWithProperty("login_attempts", loginAttempts+"");			
				return true;
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
		if(loginAttempts == 3) {
			Thread.sleep(5000);
			loginAttempts = 1;
			Main.settings.setValueWithProperty("login_attempts", loginAttempts+"");
		}
		
		else {
			loginAttempts++;
			Main.settings.setValueWithProperty("login_attempts", loginAttempts+"");
		}
		
		
		return false;
	}
	
	
	/**
	 * setSecQuestion method takes in a question and answer and sets
	 * them accordingly in the SettingsManagerConfig file
	 * @param question - String, the security question
	 * @param answer - String, the security answer
	 */
	public void setSecQuestion(String question, String answer) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] messageDigest = md.digest(answer.getBytes());
			
			BigInteger num = new BigInteger(1, messageDigest);
			String hashtext = num.toString(16);
			
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			
			Main.settings.setValueWithProperty("user_question", question);
			Main.settings.setValueWithProperty("user_answer", hashtext);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * SetPassword will set the password passed into the original
	 * object construction. Important to use this method whenever
	 * setting as the password as using otherwise will always result
	 * in false.
	 */
	public void setPassword() {
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] messageDigest = md.digest(getSecretValue().getBytes());
			
			BigInteger num = new BigInteger(1, messageDigest);
			String hashtext = num.toString(16);
			
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			
			Main.settings.setValueWithProperty("user_password", hashtext);
			
		} catch (NoSuchAlgorithmException e) {
		
			e.printStackTrace();
		}
	
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * Returns a string value of the password
	 */
	@Override
	public String toString() {
		return "Login [secretValue=" + secretValue + "]";
	}


	/**
	 * Returns the password
	 * @return String, the password
	 */
	public String getSecretValue() {
		return secretValue;
	}


	/**
	 * Sets the password
	 * @param password (password)
	 */
	public void setSecretValue(String secretValue) {
		this.secretValue = secretValue;
	}


	/**
	 * Returns the login attempts counter
	 * @return int, the login attempts
	 */
	public int getLoginAttempts() {
		return loginAttempts;
	}


	/**
	 * Sets the login attempts
	 * @param loginAttempts the loginAttempts to set
	 */
	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}
	
	
}


	
	
