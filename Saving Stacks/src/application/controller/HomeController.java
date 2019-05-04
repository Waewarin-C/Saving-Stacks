/**
 * The HomeController interacts with the Home View (Home.fxml)
 * of the application to display the statics and progress of
 * the goals the user has entered. The user are also able to 
 * view theses statics through a weekly, monthly, or yearly view.
 * 
 * @author Waewarin Chindarassami (fik450)
 * Zoom features by Gabriel Morales (woc797)
 */

package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Main;
import application.model.GoalSet;
import application.model.Home;
import application.model.Transaction;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;


public class HomeController implements EventHandler<ActionEvent>, Initializable {

	private static final String controllerID = "HOME";
	
	private GoalSet goals;
	private Home home;
	
	@FXML
	private AnchorPane homeAnchor;
	
	@FXML
	private Pane spendPane, goalPane, switchPane;
	
	@FXML
	private Label moneyPrompt, spendingPrompt, goalPrompt, budget;
	
	@FXML
	private PieChart spendingChart;
	
	@SuppressWarnings("rawtypes")
	@FXML
	private BarChart goalGraph;
	
	@FXML
	private Button weeklyButton, monthlyButton, yearlyButton;
	
	/**
	 * Zoom feature for the Graph object shown on 
	 * the home page. Requires use of the original
	 * object due to static limitations.
	 * 
	 * @param arg0 ActionEvent - Event fired from "zoom" button
	 */
	public void expandGraphToView(ActionEvent arg0)
	{
		
		
		
		Button b = new Button("Done");
		b.setPrefHeight(37);
		b.setPrefWidth(120);
		b.setLayoutX(545);
		b.setLayoutY(45);
		b.setFont(new Font("Segoe UI", 20));
		b.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-background-radius: 30");
		
		
		DropShadow ds = new DropShadow();
		ds.setWidth(21.0);
		ds.setHeight(21.0);
		ds.setRadius(15);
		ds.setSpread(0.2);
		ds.setColor(Color.color(0, 0, 0, .50));
		
		Pane blurPane = new Pane();
		blurPane.setPrefHeight(584);
		blurPane.setPrefWidth(584);
		
		blurPane.setLayoutX(101);
		blurPane.setLayoutY(108);
		
		blurPane.setEffect(ds);
		
		GaussianBlur blur = new GaussianBlur(38.0);
		
		WritableImage image = homeAnchor.snapshot(new SnapshotParameters(), null);
		
		
		ImageView img = new ImageView(image);
		img.setLayoutX(-30);
		img.setLayoutY(0);
		img.setEffect(blur);
		img.setFitHeight(860);
		img.setFitWidth(860);
		
		
		b.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			 public void handle(ActionEvent arg0) {
				
				blurPane.getChildren().remove(goalGraph);
				
				goalGraph.setLayoutX(15);
				goalGraph.setLayoutY(14);
				goalGraph.setPrefHeight(310);
				goalGraph.setPrefWidth(350);
				goalPane.getChildren().add(goalGraph);
				
			
				
				fadeOut(img, blurPane, b);
				
	
			
			}
			
		});
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			blurPane.setBackground(new Background(new BackgroundFill(Color.web("#25282f"), new CornerRadii(30), null)));
		}
		else
		{
			blurPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(30), null)));
		}
		
		
		homeAnchor.getChildren().add(img);
		homeAnchor.getChildren().add(blurPane);
		homeAnchor.getChildren().add(b);
		
		img.toFront();
		blurPane.toFront();
		b.toFront();
		blurPane.getChildren().add(goalGraph);
		goalGraph.setLayoutX(0);
		goalGraph.setLayoutY(0);
		goalGraph.setPrefHeight(574);
		goalGraph.setPrefWidth(584);
		fadeIn(img, blurPane, b);
		
		
	}
	
	/**
	 * Zoom feature for the PieChart object shown on 
	 * the home page. Requires use of the original
	 * object due to static limitations.
	 * 
	 * @param arg0 ActionEvent - Event fired from "zoom" button
	 */
	public void expandPieChartToView(ActionEvent arg0)
	{
		
		
		
		Button b = new Button("Done");
		b.setPrefHeight(37);
		b.setPrefWidth(120);
		b.setLayoutX(545);
		b.setLayoutY(45);
		b.setFont(new Font("Segoe UI", 20));
		b.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-background-radius: 30");
		
		
		
		DropShadow ds = new DropShadow();
		ds.setWidth(21.0);
		ds.setHeight(21.0);
		ds.setRadius(15);
		ds.setSpread(0.2);
		ds.setColor(Color.color(0, 0, 0, .50));
		
		Pane blurPane = new Pane();
		blurPane.setPrefHeight(584);
		blurPane.setPrefWidth(584);
		
		blurPane.setLayoutX(101);
		blurPane.setLayoutY(108);
		
		blurPane.setEffect(ds);
		
		GaussianBlur blur = new GaussianBlur(38.0);
		
		WritableImage image = homeAnchor.snapshot(new SnapshotParameters(), null);
		
		
		ImageView img = new ImageView(image);
		img.setLayoutX(-30);
		img.setLayoutY(0);
		img.setEffect(blur);
		img.setFitHeight(860);
		img.setFitWidth(860);
		
		b.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			 public void handle(ActionEvent arg0) {
				
				blurPane.getChildren().remove(spendingChart);
				
				spendingChart.setLayoutX(15);
				spendingChart.setLayoutY(14);
				spendingChart.setPrefHeight(310);
				spendingChart.setPrefWidth(350);
				spendPane.getChildren().add(spendingChart);
				
		
				fadeOut(img, blurPane, b);
				
			
			}
			
		});
		
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			blurPane.setBackground(new Background(new BackgroundFill(Color.web("#25282f"), new CornerRadii(30), null)));
		}
		else
		{
			blurPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(30), null)));
		}
		
		
		homeAnchor.getChildren().add(img);
		homeAnchor.getChildren().add(blurPane);
		homeAnchor.getChildren().add(b);
		

		blurPane.getChildren().add(spendingChart);
		spendingChart.setLayoutX(0);
		spendingChart.setLayoutY(0);
		spendingChart.setPrefHeight(574);
		spendingChart.setPrefWidth(584);
		fadeIn(img, blurPane, b);
		
		
		
	}
	
	
	
	/**
	 * The fade-in animation when the "zoom" button is clicked.
	 * 
	 * 
	 * @param img ImageView - a screenshot of the scene graph.
	 * @param blurPane Pane - a pane shown on top of the blurred screenshot.
	 * @param b Button - the "done" button on the zoom view.
	 */
	public void fadeIn(ImageView img, Pane blurPane, Button b)
	{
		FadeTransition imgFT = new FadeTransition(Duration.millis(200), img);
		imgFT.setFromValue(0.0);
		imgFT.setToValue(1.0);
		
		FadeTransition blurPaneFT = new FadeTransition(Duration.millis(200), blurPane);
		blurPaneFT.setFromValue(0.0);
		blurPaneFT.setToValue(1.0);
		
		FadeTransition buttonFT = new FadeTransition(Duration.millis(200), b);
		buttonFT.setFromValue(0.0);
		buttonFT.setToValue(1.0);
		
		ParallelTransition pt = new ParallelTransition(imgFT, blurPaneFT, buttonFT);
		

		pt.play();
		
	}
	
	/**
	 * The fade-out animation when the "zoom" button is clicked.
	 * 
	 * 
	 * @param img ImageView - a screenshot of the scene graph.
	 * @param blurPane Pane - a pane shown on top of the blurred screenshot.
	 * @param b Button - the "done" button on the zoom view.
	 */
	public void fadeOut(ImageView img, Pane blurPane, Button b)
	{
		FadeTransition imgFT = new FadeTransition(Duration.millis(200), img);
		imgFT.setFromValue(1.0);
		imgFT.setToValue(0.0);
		
		FadeTransition blurPaneFT = new FadeTransition(Duration.millis(200), blurPane);
		blurPaneFT.setFromValue(1.0);
		blurPaneFT.setToValue(0.0);
		
		FadeTransition buttonFT = new FadeTransition(Duration.millis(200), b);
		buttonFT.setFromValue(1.0);
		buttonFT.setToValue(0.0);
		
		ParallelTransition pt = new ParallelTransition(imgFT, blurPaneFT, buttonFT);
		
		
		pt.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				homeAnchor.getChildren().remove(img);
				homeAnchor.getChildren().remove(blurPane);
				homeAnchor.getChildren().remove(b);
				
			}
			
		});
		
		
		pt.play();
		
		

		
	}
	

	/**
	 * Displays the remaining monthly budget
	 * Displays monthly statics and progress of the goals
	 * the user has entered in the goals page by default
	 * 
	 * @param arg0 URL
	 * @param arg1 ResourceBundle
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		budget.setText("$" + Main.settings.getValueWithProperty("monthly_budget"));
		
		
		Double budgetRemaining = Double.valueOf(Main.settings.getValueWithProperty("monthly_budget"));
		
		goals = new GoalSet();
		goals.loadGoals("data/goals.csv");
		
		home = new Home();
		
		//Show the monthly view by default
		ArrayList<PieChart.Data> spending = home.retrieveMonthlyData(goals);
		ObservableList<PieChart.Data> data = FXCollections.observableList(spending);
		spendingChart.setData(data);
		
		goalGraph.getXAxis().setLabel("Goals");
		goalGraph.getYAxis().setLabel("Amount");
		
		ArrayList<XYChart.Data> goalEndProgress = new ArrayList<XYChart.Data>();
		XYChart.Series goalTotal = new XYChart.Series();
		goalTotal.setName("Goal Amount");
		
		double monthlyAmount = 0.0;
		for(Integer key : goals.getGoalMap().keySet())
		{
			if(goals.getGoalMap().get(key).getTime().equals("Weekly"))
			{
				monthlyAmount = (goals.getGoalMap().get(key).getAmount()) * 4;
			}
			else if(goals.getGoalMap().get(key).getTime().equals("Yearly"))
			{
				monthlyAmount = (goals.getGoalMap().get(key).getAmount()) / 12;
			}
			else if(goals.getGoalMap().get(key).getTime().equals("Monthly"))
			{
				monthlyAmount = goals.getGoalMap().get(key).getAmount();
			}
			goalEndProgress.add(new XYChart.Data(goals.getGoalMap().get(key).getTitle(), monthlyAmount));
		}
		
		goalTotal.getData().addAll(goalEndProgress);
		
		ArrayList<XYChart.Data> goalTrackProgress = new ArrayList<XYChart.Data>();
		XYChart.Series goalTrack = new XYChart.Series();
		goalTrack.setName("Amount Spent");
		
		HashMap<String, Double> monthlyTotals = Transaction.monthTransactionByGoal(goals);
		for(String key : monthlyTotals.keySet())
		{
			goalTrackProgress.add(new XYChart.Data(key, monthlyTotals.get(key)));
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
			
			spendingChart.getStylesheets().add(getClass().getResource("../view/chart_dark.css").toExternalForm());
			goalGraph.getXAxis().setTickLabelFill(Color.WHITE);
			goalGraph.getYAxis().setTickLabelFill(Color.WHITE);
			goalGraph.getXAxis().getStylesheets().add(getClass().getResource("../view/bar_chart_dark.css").toExternalForm());
			goalGraph.getYAxis().getStylesheets().add(getClass().getResource("../view/bar_chart_dark.css").toExternalForm());
			
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

	/**
	 * Changes the views of the pie chart and bar chart based
	 * on the view the user selects from the three available 
	 * buttons (Weekly, Monthly, Yearly)
	 * 
	 * @param event ActionEvent - Event fired from any of the three view buttons
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void handle(ActionEvent event) {
		String buttonPushed = ((Button) event.getSource()).getText();
		
		goals = new GoalSet();
		goals.loadGoals("data/goals.csv");
		home = new Home();
		
		if(buttonPushed.equals("Weekly"))
		{
			//Pie chart
			spendingChart.getData().clear();
			ArrayList<PieChart.Data> spending = home.retrieveWeeklyData(goals);
			ObservableList<PieChart.Data> data = FXCollections.observableList(spending);
			spendingChart.setData(data);
			
			//Bar chart
			goalGraph.getData().clear();
			goalGraph.getXAxis().setLabel("Goals");
			goalGraph.getYAxis().setLabel("Amount");
			
			ArrayList<XYChart.Data> goalEndProgress = new ArrayList<XYChart.Data>();
			XYChart.Series goalTotal = new XYChart.Series();
			goalTotal.setName("Goal Amount");
			
			double weeklyAmount = 0.0;
			for(Integer key : goals.getGoalMap().keySet())
			{
				if(goals.getGoalMap().get(key).getTime().equals("Weekly"))
				{
					weeklyAmount = (goals.getGoalMap().get(key).getAmount());
				}
				else if(goals.getGoalMap().get(key).getTime().equals("Yearly"))
				{
					weeklyAmount = (goals.getGoalMap().get(key).getAmount()) / 52;
				}
				else if(goals.getGoalMap().get(key).getTime().equals("Monthly"))
				{
					weeklyAmount = (goals.getGoalMap().get(key).getAmount()) / 4;
				}
				goalEndProgress.add(new XYChart.Data(goals.getGoalMap().get(key).getTitle(), weeklyAmount));
			}
			
			goalTotal.getData().addAll(goalEndProgress);
			
			ArrayList<XYChart.Data> goalTrackProgress = new ArrayList<XYChart.Data>();
			XYChart.Series goalTrack = new XYChart.Series();
			goalTrack.setName("Amount Spent");
			
			HashMap<String, Double> monthlyTotals = Transaction.monthTransactionByGoal(goals);
			for(String key : monthlyTotals.keySet())
			{
				goalTrackProgress.add(new XYChart.Data(key, (monthlyTotals.get(key)) / 4));
			}
			goalTrack.getData().addAll(goalTrackProgress);
			
			goalGraph.getData().addAll(goalTotal, goalTrack);
		}
		else if(buttonPushed.equals("Monthly"))
		{
			//Pie chart
			spendingChart.getData().clear();
			ArrayList<PieChart.Data> spending = home.retrieveMonthlyData(goals);
			ObservableList<PieChart.Data> data = FXCollections.observableList(spending);
			spendingChart.setData(data);
			
			//Bar chart
			goalGraph.getData().clear();
			goalGraph.getXAxis().setLabel("Goals");
			goalGraph.getYAxis().setLabel("Amount");
			
			ArrayList<XYChart.Data> goalEndProgress = new ArrayList<XYChart.Data>();
			XYChart.Series goalTotal = new XYChart.Series();
			goalTotal.setName("Goal Amount");
			
			double monthlyAmount = 0.0;
			for(Integer key : goals.getGoalMap().keySet())
			{
				if(goals.getGoalMap().get(key).getTime().equals("Weekly"))
				{
					monthlyAmount = (goals.getGoalMap().get(key).getAmount()) * 4;
				}
				else if(goals.getGoalMap().get(key).getTime().equals("Yearly"))
				{
					monthlyAmount = (goals.getGoalMap().get(key).getAmount()) / 12;
				}
				else if(goals.getGoalMap().get(key).getTime().equals("Monthly"))
				{
					monthlyAmount = goals.getGoalMap().get(key).getAmount();
				}
				goalEndProgress.add(new XYChart.Data(goals.getGoalMap().get(key).getTitle(), monthlyAmount));
			}
			
			goalTotal.getData().addAll(goalEndProgress);
			
			ArrayList<XYChart.Data> goalTrackProgress = new ArrayList<XYChart.Data>();
			XYChart.Series goalTrack = new XYChart.Series();
			goalTrack.setName("Amount Spent");
			
			HashMap<String, Double> monthlyTotals = Transaction.monthTransactionByGoal(goals);
			for(String key : monthlyTotals.keySet())
			{
				goalTrackProgress.add(new XYChart.Data(key, monthlyTotals.get(key)));
			}
			goalTrack.getData().addAll(goalTrackProgress);
			
			goalGraph.getData().addAll(goalTotal, goalTrack);
		}
		else if(buttonPushed.equals("Yearly"))
		{
			//Pie chart
			spendingChart.getData().clear();
			ArrayList<PieChart.Data> spending = home.retrieveYearlyData(goals);
			ObservableList<PieChart.Data> data = FXCollections.observableList(spending);
			spendingChart.setData(data);
			
			//Bar chart
			goalGraph.getData().clear();
			goalGraph.getXAxis().setLabel("Goals");
			goalGraph.getYAxis().setLabel("Amount");
			
			ArrayList<XYChart.Data> goalEndProgress = new ArrayList<XYChart.Data>();
			XYChart.Series goalTotal = new XYChart.Series();
			goalTotal.setName("Goal Amount");
			
			double yearlyAmount = 0.0;
			for(Integer key : goals.getGoalMap().keySet())
			{
				if(goals.getGoalMap().get(key).getTime().equals("Weekly"))
				{
					yearlyAmount = (goals.getGoalMap().get(key).getAmount()) * 52;
				}
				else if(goals.getGoalMap().get(key).getTime().equals("Yearly"))
				{
					yearlyAmount = goals.getGoalMap().get(key).getAmount();
				}
				else if(goals.getGoalMap().get(key).getTime().equals("Monthly"))
				{
					yearlyAmount = (goals.getGoalMap().get(key).getAmount()) * 12 ;
				}
				goalEndProgress.add(new XYChart.Data(goals.getGoalMap().get(key).getTitle(), yearlyAmount));
			}
			
			goalTotal.getData().addAll(goalEndProgress);
			
			ArrayList<XYChart.Data> goalTrackProgress = new ArrayList<XYChart.Data>();
			XYChart.Series goalTrack = new XYChart.Series();
			goalTrack.setName("Amount Spent");
			
			HashMap<String, Double> monthlyTotals = Transaction.monthTransactionByGoal(goals);
			for(String key : monthlyTotals.keySet())
			{
				goalTrackProgress.add(new XYChart.Data(key, (monthlyTotals.get(key)) * 12));
			}
			goalTrack.getData().addAll(goalTrackProgress);
			
			goalGraph.getData().addAll(goalTotal, goalTrack);
		}
	}

}