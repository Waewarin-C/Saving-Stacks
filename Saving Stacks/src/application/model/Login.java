package application.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import application.Main;

/**
 * @author Moses J. Arocha - qiv737
 *
 */
public class Login {
	
	private String secretValue;
	private int loginAttempts;
	

	public Login(String secretValue) {
		this.secretValue = secretValue;
	}
	
	
	public Login(String secretValue, int loginAttempts) {
		this.secretValue = secretValue;
		this.loginAttempts = loginAttempts;
	}
	
	
	//TODO: create password policies
	//correct passwords
	//TODO: continue working on this part MOSES!
	public boolean passwordStrength() {
		if(getSecretValue().matches("[123456789]*")) {
			if(getSecretValue().matches("[!@#$%^&*()-+=]*")) {
				return true;
			}
			return false;
		}
		
		return false;
	}
	
	
	public boolean checkHashValue() throws InterruptedException{
		
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
	 */
	@Override
	public String toString() {
		return "Login [secretValue=" + secretValue + "]";
	}


	/**
	 * @return the secretValue
	 */
	public String getSecretValue() {
		return secretValue;
	}


	/**
	 * @param secretValue the secretValue to set
	 */
	public void setSecretValue(String secretValue) {
		this.secretValue = secretValue;
	}


	/**
	 * @return the loginAttempts
	 */
	public int getLoginAttempts() {
		return loginAttempts;
	}


	/**
	 * @param loginAttempts the loginAttempts to set
	 */
	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}
	
	
	
}


	
	
