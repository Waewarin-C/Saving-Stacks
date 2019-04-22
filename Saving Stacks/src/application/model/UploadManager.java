package application.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
	private static int id;
	
	//Static method to read the csv file
	public static void readFile(File chosenFile) 
	{
		
		if (chosenFile == null)
			return;
		
		id = 0;
		try
		{
			//Tokenize the specified format to read the file and load the data correctly
			String[] formatToken = UploadManager.getFormat().split(",");
			
			String fileName = chosenFile.getName();
			Scanner scan = new Scanner(new File(fileName));
			
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				String[] tokens = line.split(",");
				
				//Load the data based on format
				if(formatToken[0].equals("date"))
				{
					String date = tokens[0];
				}
				else if(formatToken[0].equals("amount"))
				{
					String amount = tokens[0];
				}
				else if(formatToken[0].equals("title"))
				{
					String title = tokens[0];
				}
				
				
				if(formatToken[1].equals("date"))
				{
					String date = tokens[0];
				}
				else if(formatToken[1].equals("amount"))
				{
					String amount = tokens[0];
				}
				else if(formatToken[1].equals("title"))
				{
					String title = tokens[0];
				}
				
				
				if(formatToken[2].equals("date"))
				{
					String date = tokens[0];
				}
				else if(formatToken[2].equals("amount"))
				{
					String amount = tokens[0];
				}
				else if(formatToken[2].equals("title"))
				{
					String title = tokens[0];
				}
				
			
				//Create new transaction object
				
				/**Any parameters in the list that still have its type are the ones
				 * that have not been figured out yet
				 */
				
				//Transaction temp = new Transaction(id, String entryDate, date, title, String tag, amount);
				
				//Add transaction to ArrayList
				//transactions.add(temp);
				
				//Increment id
				id++;
			}
			
			scan.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static String getFormat()
	{
		return format;
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
