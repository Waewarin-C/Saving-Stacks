package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

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
	
	/*
	 * 
	 */
	public static Goal generateGoal( String title, String goalAmt, String time )
	{
		LocalDate date = LocalDate.now(); 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
	    String strDate = date.format(formatter);
	    double amount = Double.valueOf(goalAmt);
		Goal goal = new Goal( title, time, strDate, amount);
		
		return goal;
	}
	
	//TODO: remove Goal from Hashmap.
	public GoalSet removeGoal( GoalSet goalMap, int row )
	{
		goalMap.getGoalMap().remove(row);
		
		Set<Integer> keys = goalMap.getGoalMap().keySet();
		ArrayList<Goal> values = new ArrayList<Goal>();
		GoalSet tempHash = new GoalSet();
		
		for( Integer k : keys)
		{
			Goal g = goalMap.getGoalMap().get(k);
			values.add(g);
		}
		
		for(int i = 0; i < values.size(); i++ )
		{
			Goal g = values.get(i);
			tempHash.getGoalMap().put(i, g);
		}
		
		return tempHash;
	}
	
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
