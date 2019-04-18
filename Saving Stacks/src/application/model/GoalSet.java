package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class GoalSet {
	
	private HashMap<Integer, Goal> goalMap;	
	/**
	 * GoalSet constructor for Goal Page initialization
	 */
	public GoalSet()
	{
		this.goalMap = new HashMap<Integer, Goal>();
	}

	/**
	 * save goals to file when typed into the interface
	 * @param file name as a String to use to save the goals.
	 */
	public static void saveGoalArray( String file, GoalSet goalMap ) {
		
		try {
			// open the file for writing	
			FileWriter writer = new FileWriter( new File( file ) );
			
			for( int i = 0; i < goalMap.getGoalMap().size(); i++ )
			{
				// write goal to file 
				writer.write( goalMap.getGoalMap().get(i).toString() );
			}	
			// close the file!
			writer.close();			
		}catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param the file File of the goal information.
	 */
	public void loadGoals( String filepath )
	{		
		int rowKey = 0;
		
		try {	
			
			File file = new File( filepath );
			Scanner scan = new Scanner( file );
			
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				String[] tokens = line.split(",");
				double dollarAmt = Double.parseDouble(tokens[3]);
				Goal newGoal = new Goal(tokens[0], tokens[1], tokens[2], dollarAmt );
				
				this.goalMap.put(rowKey, newGoal);
				
				rowKey++;
			}
				
			scan.close();
				
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}		
	}
	
	public static Goal generateGoal( String title, String dollarAmt, String time )
	{
		System.out.println("0");
		LocalDate date = LocalDate.now(); 
		System.out.println("1");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		System.out.println("2");
	    String strDate = date.format(formatter);
	    System.out.println("3");
		double amount = Double.valueOf(dollarAmt);
		System.out.println("4");
		Goal goal = new Goal( title, time, strDate, amount);
		
		System.out.println("5");
		return goal;
	}
	
	//TODO: update Goal in array
	
	//TODO: remove Goal from array
	
	/**
	 * @return the goalMap
	 */
	public HashMap<Integer, Goal> getGoalMap() {
		return goalMap;
	}

	/**
	 * @param goalMap the goalMap to set
	 */
	public void setGoalMap(HashMap<Integer, Goal> goalMap) {
		this.goalMap = goalMap;
	}
}
