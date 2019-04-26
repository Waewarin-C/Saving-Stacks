package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Main;
import application.model.Goal;
import application.model.GoalSet;
import application.model.Home;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class HomeController implements EventHandler<ActionEvent>, Initializable {

	private static final String controllerID = "HOME";
	
	private GoalSet goals;
	private Home home;
	
	@FXML
	AnchorPane homeAnchor;
	
	@FXML
	Pane spendPane, goalPane, switchPane;
	
	@FXML
	Label moneyPrompt, spendingPrompt, goalPrompt, budget;
	
	@FXML
	PieChart spendingChart;
	
	@FXML
	BarChart goalGraph;
	
	@FXML
	Button weeklyButton, monthlyButton, yearlyButton;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		budget.setText("$" + Main.settings.getValueWithProperty("monthly_budget"));
		
		
		Double budgetRemaining = Double.valueOf(Main.settings.getValueWithProperty("monthly_budget"));
		
		goals = new GoalSet();
		goals.loadGoals("data/goals.csv");
		
		home = new Home();
		
		ArrayList<PieChart.Data> spending = home.retrieveData(goals);
		ObservableList<PieChart.Data> data = FXCollections.observableList(spending);
		spendingChart.setData(data);
		
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		
		goalGraph.getXAxis().setLabel("Goals");
		goalGraph.getYAxis().setLabel("Percentage");
		
		ArrayList<XYChart.Data> goalEndProgress = new ArrayList<XYChart.Data>();
		XYChart.Series goalTotal = new XYChart.Series();
		goalTotal.setName("End Goal");
		
		for(Integer key : goals.getGoalMap().keySet())
		{
			goalEndProgress.add(new XYChart.Data(goals.getGoalMap().get(key).getTitle(), 100.00));
		}
		
		goalTotal.getData().addAll(goalEndProgress);
		
		ArrayList<XYChart.Data> goalTrackProgress = new ArrayList<XYChart.Data>();
		XYChart.Series goalTrack = new XYChart.Series();
		goalTrack.setName("Progress");
		
		for(Integer key : goals.getGoalMap().keySet())
		{
			if(goals.getGoalMap().get(key).getTitle().equals("Rent"))
			{
				goalTrackProgress.add(new XYChart.Data(goals.getGoalMap().get(key).getTitle(), 80.00));
			}
			else if(goals.getGoalMap().get(key).getTitle().equals("Grocery"))
			{
				goalTrackProgress.add(new XYChart.Data(goals.getGoalMap().get(key).getTitle(), 50.00));
			}
			else if(goals.getGoalMap().get(key).getTitle().equals("Gas"))
			{
				goalTrackProgress.add(new XYChart.Data(goals.getGoalMap().get(key).getTitle(), 30.00));
			}
			else if(goals.getGoalMap().get(key).getTitle().equals("Puppies"))
			{
				goalTrackProgress.add(new XYChart.Data(goals.getGoalMap().get(key).getTitle(), 45.00));
			}
		}
		goalTrack.getData().addAll(goalTrackProgress);
		
		goalGraph.getData().addAll(goalTotal, goalTrack);
		
		
		goalPrompt.textFillProperty().bind(moneyPrompt.textFillProperty());
		spendingPrompt.textFillProperty().bind(moneyPrompt.textFillProperty());
		goalPane.backgroundProperty().bind(switchPane.backgroundProperty());
		spendPane.backgroundProperty().bind(switchPane.backgroundProperty());
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			homeAnchor.setStyle("-fx-background-color: #33333d");
			moneyPrompt.setStyle("-fx-text-fill: white");
			switchPane.setStyle("-fx-background-color: #25282f; -fx-background-radius: 30");
			
			budget.setStyle("-fx-text-fill: #60ad5e");
		}
		else
		{
			budget.setStyle("-fx-text-fill: #005005");
		}

		if (budgetRemaining <= 0)
		{
			budget.setStyle("-fx-text-fill: red");
		}
		
		BottomBarController.attachBottomBar(homeAnchor.getChildren(), controllerID);
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}