package application.model;

import java.util.ArrayList;

import javafx.scene.chart.PieChart;

public class Home {

	private static ArrayList<PieChart.Data> spending;
	
	public Home()
	{
		spending = new ArrayList<PieChart.Data>();
	}
	
	public ArrayList<PieChart.Data> retrieveData(GoalSet goals)
	{
		for(Integer key : goals.getGoalMap().keySet())
		{
			if(goals.getGoalMap().get(key).getTime().equals("Weekly"))
			{
				double weeklyToMonthly = weeklyConversion(goals.getGoalMap().get(key).getAmount());
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), weeklyToMonthly));
			}
			else if(goals.getGoalMap().get(key).getTime().equals("Yearly"))
			{
				double yearlyToMonthly = yearlyConversion(goals.getGoalMap().get(key).getAmount());
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), yearlyToMonthly));
			}
			else if(goals.getGoalMap().get(key).getTime().equals("Monthly"))
			{
				double monthly = goals.getGoalMap().get(key).getAmount();
				spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), monthly));
			}
		}
		
		return spending;
	}
	
	public double weeklyConversion(double amount)
	{
		return amount * 4;
	}
	
	public void monthlyConversion()
	{
		
	}
	
	public double yearlyConversion(double amount)
	{
		return amount / 12;
	}
}
