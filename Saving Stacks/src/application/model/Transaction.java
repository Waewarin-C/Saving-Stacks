package application.model;

import java.io.File;
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
	
	private String transFilename = "data/transactions.csv";
	private static String idFilename = "data/transId.csv";
	
	public Transaction(int idNumber, String entryDate, String transDate, String name, String tag, double amount)
	{
		this.transId = idNumber;
		this.entryDate = entryDate;
		this.transDate = transDate;
		this.name = name;
		this.tag = tag;
		this.amount = amount;
	}
	
	//TODO: add addTransaction function
	public void saveTransaction( Transaction transaction )
	{

		
	}
	
	public void saveTransaction( ArrayList<Transaction> transArray )
	{
		
	}

	public static int establishTransId()
	{
		Path path = Paths.get(idFilename);
		int id = 0;
		
		if( Files.exists(path))
		{
			Scanner scan = new Scanner(idFilename);
			id = scan.nextInt();
			id = id++;	
		}
		else
		{
			id = 1;
		}
		
		return id;
	}
	
	public void saveTransId( int id )
	{
		try {
			// open the file for writing	
			FileWriter writer = new FileWriter( new File( idFilename ) );
			writer.write( id );
			writer.close();			
		}catch( IOException e ) {
			e.printStackTrace();
		}
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
