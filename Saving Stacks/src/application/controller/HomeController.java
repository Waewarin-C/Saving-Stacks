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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class HomeController implements Initializable {

	private static final String controllerID = "HOME";
	
	private GoalSet goals;
	private HashMap<Integer, Goal> goalMap;
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

}
