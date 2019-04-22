package application.model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	
	//Static method to read the csv file
	public static void readFile(File chosenFile) 
	{
		if (chosenFile == null)
			return;

		try
		{
			//Tokenize the specified format to read the file and load the data correctly
			String[] formatToken = UploadManager.getFormat().split(",");
			
			String fileName = chosenFile.getName();
			Scanner scan = new Scanner(new File(fileName));
			
			String transDate = "";
			double amount = 0.00;
			String name = "";
			int id = Transaction.establishTransId();
			id++;
			
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				String[] tokens = line.split(",");
				
				//Load the data based on format
				if(formatToken[0].equals("date"))
				{
					transDate = tokens[0];
				}
				else if(formatToken[0].equals("amount"))
				{
					amount = Double.parseDouble(tokens[0]);
				}
				else if(formatToken[0].equals("title"))
				{
					name = tokens[0];
				}
				
				
				if(formatToken[1].equals("date"))
				{
					transDate = tokens[0];
				}
				else if(formatToken[1].equals("amount"))
				{
					amount = Double.parseDouble(tokens[0]);
				}
				else if(formatToken[1].equals("title"))
				{
					name = tokens[0];
				}
				
				
				if(formatToken[2].equals("date"))
				{
					transDate = tokens[0];
				}
				else if(formatToken[2].equals("amount"))
				{
					amount = Double.parseDouble(tokens[0]);
				}
				else if(formatToken[2].equals("title"))
				{
					name = tokens[0];
				}
				
				LocalDate entryDate = LocalDate.now(); 
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
				
			    String strDate = entryDate.format(formatter);
			
				//Create new transaction object
				
				/**
				 * For now the tag is an empty string
				 * That needs to be figured out later
				 * when the UploadController is worked on
				 */
				
				Transaction temp = new Transaction(id, strDate, transDate, name, "", amount);
				
				//Add transaction to ArrayList
				transactions.add(temp);
				
				//Increment id
				id++;
			}
			//Keep track of the last transaction id used
			Transaction.saveTransId(id - 1);
			
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
