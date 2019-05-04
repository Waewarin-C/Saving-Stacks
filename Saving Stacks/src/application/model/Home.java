/**
 * Home takes care of converting the data for the pie chart
 * between weekly, monthly, and yearly
 * 
 * @author Waewarin Chindarassami (fik450)
 */

package application.model;

import java.util.ArrayList;

import javafx.scene.chart.PieChart;

public class Home {

	private static ArrayList<PieChart.Data> spending;
	
	public Home()
	{
		spending = new ArrayList<PieChart.Data>();
	}
	
	/**
	 * Changes all the goal amounts entered to be weekly
	 * 
	 * @param goals GoalSet - HashMap of the goals
	 * @return ArrayList<PieChart.Data> - ArrayList of the converted amounts
	 */
	public ArrayList<PieChart.Data> retrieveWeeklyData(GoalSet goals)
	{
		for(Integer key : goals.getGoalMap().keySet())
		{
			if(goals.getGoalMap().get(key).getTime().equals("Weekly"))
			{
				double weekly = (goals.getGoalMap().get(key).getAmount());
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), weekly));
			}
			else if(goals.getGoalMap().get(key).getTime().equals("Monthly"))
			{
				double monthlyToWeekly = (goals.getGoalMap().get(key).getAmount()) / 4;
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), monthlyToWeekly));
			}
			else if(goals.getGoalMap().get(key).getTime().equals("Yearly"))
			{
				double yearlyToWeekly = (goals.getGoalMap().get(key).getAmount()) / 52;
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), yearlyToWeekly));
			}
			
		}
		
		return spending;
	}
	
	/**
	 * Changes all the goal amounts entered to be monthly
	 * 
	 * @param goals GoalSet - HashMap of the goals
	 * @return ArrayList<PieChart.Data> - ArrayList of the converted amounts
	 */
	public ArrayList<PieChart.Data> retrieveMonthlyData(GoalSet goals)
	{
		for(Integer key : goals.getGoalMap().keySet())
		{
			if(goals.getGoalMap().get(key).getTime().equals("Weekly"))
			{
				double weeklyToMonthly = (goals.getGoalMap().get(key).getAmount()) * 4;
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), weeklyToMonthly));
			}
			else if(goals.getGoalMap().get(key).getTime().equals("Monthly"))
			{
				double monthly = goals.getGoalMap().get(key).getAmount();
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), monthly));
			}
			else if(goals.getGoalMap().get(key).getTime().equals("Yearly"))
			{
				double yearlyToMonthly = (goals.getGoalMap().get(key).getAmount()) / 12;
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), yearlyToMonthly));
			}
		}
		
		return spending;
	}
	
	/**
	 * Changes all the goal amounts entered to be yearly
	 * 
	 * @param goals GoalSet - HashMap of the goals
	 * @return ArrayList<PieChart.Data> - ArrayList of the converted amounts
	 */
	public ArrayList<PieChart.Data> retrieveYearlyData(GoalSet goals)
	{
		for(Integer key : goals.getGoalMap().keySet())
		{
			if(goals.getGoalMap().get(key).getTime().equals("Weekly"))
			{
				double weeklyToYearly = (goals.getGoalMap().get(key).getAmount()) * 52;
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), weeklyToYearly));
			}
			else if(goals.getGoalMap().get(key).getTime().equals("Monthly"))
			{
				double monthlyToYearly = (goals.getGoalMap().get(key).getAmount()) * 12;
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), monthlyToYearly));
			}
			else if(goals.getGoalMap().get(key).getTime().equals("Yearly"))
			{
				double yearly = goals.getGoalMap().get(key).getAmount();
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), yearly));
			}
		}
		
		return spending;
	}
}
