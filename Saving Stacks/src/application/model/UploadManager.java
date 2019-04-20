package application.model;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Gabriel Morales (woc797)
 * @author Waewarin Chindarassami (fik450)
 * 
 * UploadManager class intended to 
 * open a prompt for the user and allow them to 
 * select which file they wish to upload. 
 * 
 * The file will be selectively parsed, and contain
 * a data structure of Transaction objects
 */


public class UploadManager {

	private static ArrayList<Transaction> transactions;
	private static String format;
	
	//Static method to read the csv file
	public static void readFile(File chosenFile) 
	{
		
	}
	
	public static void setFormat(String fileFormat)
	{
		format = fileFormat;
	}
	
	public static ArrayList<Transaction> getTransactions()
	{
		return transactions;
	}
	
	
}
