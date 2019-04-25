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
			spending.add(new PieChart.Data(goals.getGoalMap().get(key).getTitle(), goals.getGoalMap().get(key).getAmount()));
		}
		
		return spending;
	}
}
