package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GoalSet {
	
	private ArrayList<Goal> goalArray;
	
	/**
	 * GoalSet constructor for Goal Page initialization
	 */
	public GoalSet()
	{
		this.goalArray = new ArrayList<Goal>();
	}
	

	/**
	 * save goals to file when typed into the interface
	 * @param file name as a String to use to save the goals.
	 */
	public void saveGoalArray( String file ) {
		
		try {
			// open the file for writing	
			FileWriter writer = new FileWriter( new File( file ) );
			
			for( Goal a : goalArray )
			{
				// write goal to file 
				writer.write( a.toString() );
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
	public void loadGoals( File file )
	{		
		
		try {	
			
			Scanner scan = new Scanner( file );
			
			while(scan.hasNextLine())
			{
				String line = scan.nextLine();
				String[] tokens = line.split(",");
				double dollarAmt = Double.parseDouble(tokens[2]);
				
				Goal newGoal = new Goal(tokens[0],tokens[1], dollarAmt );
				
				this.goalArray.add(newGoal);
			}
				
			scan.close();
				
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}		
	}
	
	/**
	 * @return the goalArray
	 */
	public ArrayList<Goal> getGoalArray() {
		return goalArray;
	}

	/**
	 * @param goalArray the goalArray to set
	 */
	public void setGoalArray(ArrayList<Goal> goalArray) {
		this.goalArray = goalArray;
	}

	/**
	 * Adds a goal to the goalArray.
	 * @param goal Goal to be added
	 */	
	public void addGoal(Goal goal)
	{
		this.goalArray.add(goal);
	}

}
