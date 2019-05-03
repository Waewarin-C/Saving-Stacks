package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * GoalSet.java class aggregates Goal objects. This class manipulates the user input on the GoalPage.fxml
 * and handles data associated with the goals entered by the user.
 * 
 * @author Chelsea Flores (rue750)
 */
public class GoalSet {
	
	//class variables
	private HashMap<Integer, Goal> goalMap;	
	
	/**
	 * GoalSet constructor for Goal Page initialization
	 */
	public GoalSet()
	{
		this.goalMap = new HashMap<Integer, Goal>();
	}

	/**
	 * saveGoalArray saves the goals in the goalMap to the goals.csv file.
	 * 
	 * @param filename - String of the name of the file .
	 */
	public static void saveGoalArray( String file, GoalSet goalMap ) {
		
		try {
			
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
	 * loadGoals loads the goals into the GoalSet HashMap and sets the key value as the 
	 * index of the grid pane row in which it was created.
	 * 
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
	
	/**
	 * generateGoal takes in String parameters to create a Goal object.
	 * 
	 * @param title - String of the goal title.
	 * @param goalAmt - String of the goal dollar amount.
	 * @param time - String of the timeframe of the goal.
	 * @return Goal that is created using the parameters.
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
	
	/**
	 * Removes the goal from the GoalSet HashMap and resets the key values.
	 * 
	 * @param goalMap - HashMap of the goals in the GoalSet object.
	 * @param row - integer value of the row to be removed.
	 * @return the GoalSet Object of the updated GoalSet HashMap.
	 */
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
	 * Returns the HashMap in the GoalSet object.
	 * 
	 * @return the goalMap
	 */
	public HashMap<Integer, Goal> getGoalMap() {
		return goalMap;
	}

	/**
	 * setGoalMap sets the HashMap in the GoalSet object.
	 * 
	 * @param goalMap the goalMap to set
	 */
	public void setGoalMap(HashMap<Integer, Goal> goalMap) {
		this.goalMap = goalMap;
	}
}
