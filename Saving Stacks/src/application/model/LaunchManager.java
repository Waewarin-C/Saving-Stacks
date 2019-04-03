package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Gabriel Morales
 *
 * LaunchManager class reads from the "LaunchManagerConfig"
 * within the data folder of the app. 
 * The format of the config is, and should remain, as follows:
 * 
 * property_to_use=boolean
 * 
 * Each boolean is mapped to a property key within
 * the properties HashMap. Most of the managing should
 * be done internally, with minimal calls externally.
 *
 */
public class LaunchManager {
	
	LinkedHashMap<String, Boolean> properties;
	
	/**
	 * Constructs a new LaunchManager.
	 * Visibility private due to only internal use of the object creation.
	 */
	private LaunchManager()
	{
		this.properties = new LinkedHashMap<String, Boolean>();
	}
	
	/**
	 * Loads the config from the "LaunchManagerConfig" file located in the data folder.
	 * Each property is mapped with a boolean value. Keys are the properties, the values
	 * are assigned to these keys (i.e. the booleans)
	 * 
	 * 
	 * @return LaunchManger - A LaunchManager object for use with settings and/or launch prep.
	 * @throws IOException
	 */
	public static LaunchManager loadConfig() throws IOException
	{
		
		LaunchManager launchManager = new LaunchManager();
		Scanner scan = new Scanner(new File("data/LaunchManagerConfig"));
		
		while (scan.hasNextLine())
		{
			
			String line = scan.nextLine();
			String[] tokens = line.split("=");
			
			//Each property contains it's own boolean.
			launchManager.getProperties().put(tokens[0], Boolean.parseBoolean(tokens[1]));
			
		}
		
		scan.close();
		
		return launchManager;
	}
	
	
	/**
	 * Saves any modifications (from scratch) to the config file. 
	 * Avoid redundant calling to reduce efficiency hits.
	 * 
	 * 
	 * @param launchManager LaunchManager - A LaunchManager object from earlier creation.
	 * @throws IOException
	 */
	public static void saveConfig(LaunchManager launchManager) throws IOException
	{
		
		FileWriter fileWrite = new FileWriter(new File("data/LaunchManagerConfig"));
			
		Set<String> hashStrings = launchManager.getProperties().keySet();
		
		for (String s : hashStrings)
		{
			fileWrite.write(String.format("%s=%s%n", s, launchManager.getProperties().get(s)));
		}
		
		
		fileWrite.close();
	}
	
	/**
	 * Utility method for easy access to the values with
	 * respective keys.
	 * Provided an error occurs, method defaults to false
	 * and notifies client of such an error.
	 * Otherwise, return the value for the key.
	 * 
	 * @param key String - Key to search for.
	 * @return boolean - Value for the key searched for.
	 */
	public boolean getValueWithProperty(String key)
	{
		
		if (!this.getProperties().containsKey(key))
		{
			
			System.out.println("WARNING: " + key + " does not exist within the HashMap, defaulted to false.");
			
			return false;
		}
		
		Boolean value = this.getProperties().get(key);
		
		return value.booleanValue();
		
	}
	
	
	/**
	 * Sets the value of "Key" string to provided boolean value.
	 * If error occurs, map is left unchanged and warning
	 * is provided.
	 * 
	 * @param key String - key to change value of.
	 * @param value Boolean - value to change to.
	 */
	public void setValueWithProperty(String key, Boolean value)
	{
		if (!this.getProperties().containsKey(key))
		{
			System.out.println("WARNING: " + key + " does not exist within the HashMap; map left unchanged.");
			return;
		}
		
		this.getProperties().put(key, value);
		
		
	}
	

	/**
	 * Gets the properties HashMap from the config.
	 * 
	 * @return HashMap<String, Boolean> - HashMap with property=boolean key-value pairs.
	 */
	public LinkedHashMap<String, Boolean> getProperties() {
		return properties;
	}


	/**
	 * Sets the properties to a new HashMap. 
	 * 
	 * @param properties HashMap<String, Boolean> - HashMap to set the properties to.
	 */
	public void setProperties(LinkedHashMap<String, Boolean> properties) {
		
		if (properties == null)
		{
			throw new NullPointerException();
		}
		
		this.properties = properties;
	}
	
	
	
	
	

}
