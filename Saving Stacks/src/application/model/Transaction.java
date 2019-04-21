package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Transaction {
	
	private int transId;
	private String entryDate;
	private String transDate;
	private String name;
	private String tag;
	private double amount;
	
	private static String transFilename = "data/transactions.csv";
	private static String idFile = "transId.csv";
	private static String idFilename = "data/" + idFile;
	static File file;
	
	public Transaction(int idNumber, String entryDate, String transDate, String name, String tag, double amount)
	{
		this.transId = idNumber;
		this.entryDate = entryDate;
		this.transDate = transDate;
		this.name = name;
		this.tag = tag;
		this.amount = amount;
	}
	
	/**
	 * 
	 * @param transaction
	 */
	public static void saveTransaction( Transaction transaction )
	{
		Path path = Paths.get(transFilename);
		
		
		if( Files.exists(path))
		{
			appendTransToFile( transaction );
		}
		else
		{
			saveTransToNewFile(transaction);
		}
	}
	
	/**
	 * 
	 * @param transArray
	 */
	public static void saveTransaction( ArrayList<Transaction> transArray )
	{
		Path path = Paths.get(transFilename);
		
		
		if( Files.exists(path))
		{
			for( int i = 0; i < transArray.size(); i++ )
			{
				Transaction temp = transArray.get(i);
				appendTransToFile( temp );
			}
		}
		else
		{
			for( int i = 0; i < transArray.size(); i++ )
			{
				Transaction temp = transArray.get(i);
				saveTransToNewFile( temp );
			}		
		}
	}

	/**
	 * 
	 * @param transaction
	 */
	public static void saveTransToNewFile( Transaction transaction )
	{
		try {
			// open the file for writing	
			FileWriter writer = new FileWriter( new File( transFilename ) );		
			writer.write( transaction.toString() );
			writer.close();			
		}catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param transaction
	 */
	public static void appendTransToFile( Transaction transaction )
	{
		try {
			// open the file for writing	
			FileWriter writer = new FileWriter( new File( transFilename ), true );		
			writer.write( transaction.toString() );
			writer.close();			
		}catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static int establishTransId()
	{
		Path path = Paths.get(idFilename);
		int id = 0;
		Scanner scan;
		
		if( Files.exists(path))
		{
			file = new File(idFilename);
			
			try {
				scan = new Scanner(file);
				String idString = scan.nextLine();
				String tokens[] = idString.split(",");
				id = Integer.parseInt(tokens[0]);
				id = id + 1;
				scan.close();
			} catch (FileNotFoundException e) {
			
				e.printStackTrace();
			}
		}
		else
		{
			id = 1;
		}
		saveTransId( id );	
		return id;
	}
	
	/**
	 * 
	 * @param id
	 */
	public static void saveTransId( int id )
	{
		try {
			// open the file for writing	
			FileWriter writer = new FileWriter( new File( idFilename ) );
			String idString = String.valueOf(id);
			writer.write( idString );
			writer.close();			
		}catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public String toString()
	{
		String ret = this.transId + "," + this.entryDate + "," + this.name + "," + this.transDate + ",";
		ret += this.amount + "," + this.tag + "\n";
		return ret;
	}
	
	/**
	 * @return the entryDate
	 */
	public String getEntryDate() {
		return entryDate;
	}

	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * @return the transDate
	 */
	public String getTransDate() {
		return transDate;
	}

	/**
	 * @param transDate the transDate to set
	 */
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	/**
	 * @return the transId
	 */
	public int getTransId() {
		return transId;
	}

	/**
	 * @param transId the transId to set
	 */
	public void setTransId(int transId) {
		this.transId = transId;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

}
