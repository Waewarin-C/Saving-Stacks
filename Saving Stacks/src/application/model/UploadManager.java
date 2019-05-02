package application.model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public static String status;
	
	/**
	 * Reads the file specified from choosing
	 * within the filesystem.
	 * 
	 * @param chosenFile File - file chosen by the user.
	 */
	public static void readFile(File chosenFile) 
	{
	
		status = "valid";
		transactions = new ArrayList<>();
			
		if (chosenFile == null)
		{
			status = "invalid";
			return;
		}
		try
		{
			
			Pattern datePattern = Pattern.compile("^(0[1-9]?|[1-9]|1[0-2])/(0[1-9]|[1-9]|1[0-9]|2[0-9]|30|31)/([0-9]{4})$");
			Pattern titlePattern = Pattern.compile("^[a-zA-Z]+.*");
			Pattern amountPattern = Pattern.compile("\\$?([-]?[0-9]+\\.[0-9][0-9])");
			
			Matcher m = null;

			Scanner scan = new Scanner(chosenFile);
			
			String transDate = "";
			double amount = 0.00;
			String name = "";

			int id = Transaction.establishTransId();
			id++;
			
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				
				
				String[] tokens = line.split(",");
				
				
				for (String str : tokens)
				{
					m = datePattern.matcher(str.trim());
					if (m.matches())
					{
						transDate = str;
						break;
					}
					
				}
				
				for (String str : tokens)
				{
					m = titlePattern.matcher(str.trim());
					if (str.isEmpty() || str.equals(" "))
						continue;
					if (m.matches())
					{
						name = str;
						break;
					}
				}
				
				for (String str : tokens)
				{
					m = amountPattern.matcher(str.trim());
					if (m.matches())
					{
						amount = Double.parseDouble(m.group(1));
						break;
					}
				}
				
				LocalDate entryDate = LocalDate.now(); 
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
				
			    String strDate = entryDate.format(formatter);

				
				Transaction temp = new Transaction(id, strDate, transDate, name, " ", amount);

				transactions.add(temp);
				

				id++;
			}

			Transaction.saveTransId(id - 1);
			
			scan.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets the transactions loaded by the file selected in the 
	 * filesystem.
	 * 
	 * @return ArrayList<Transaction> - arraylist of transactions given by uploaded
	 * file.
	 */
	public static ArrayList<Transaction> getTransactions()
	{
		return transactions;
	}
}
