package application.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GoalSet {
	
	private ArrayList<Goal> goalArray;
	
	public GoalSet()
	{
		this.goalArray = new ArrayList<Goal>();
	}
	

	//save goals to file when typed into the interface
	public void saveGoal() {
		
		try {
			// open the file for writing	
			FileWriter writer = null;
			File file = new File("goal.csv");
			
			if(file.exists())
				writer = new FileWriter( new File("goal.csv") );				
			else
				System.out.print("Doesn't exists");
			
			// write goal to file 
			writer.write( toString() + "\n" );
			
			// close the file!
			writer.close();			
		}catch( IOException e ) {
			e.printStackTrace();
		}
	}
	
	//load goals from file
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
				
				goalArray.add(newGoal);
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

	
	public void addGoal(Goal goal)
	{
		this.goalArray.add(goal);
	}

}
