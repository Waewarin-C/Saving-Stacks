package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class HomeController implements Initializable {

	private static final String controllerID = "HOME";
	
	@FXML
	AnchorPane homeAnchor;
	
	@FXML
	Pane spendPane, goalPane, switchPane;
	
	@FXML
	Label moneyPrompt, spendingPrompt, goalPrompt, budget;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		budget.setText("$" + Main.settings.getValueWithProperty("monthly_budget"));
		
		
		Double budgetRemaining = Double.valueOf(Main.settings.getValueWithProperty("monthly_budget"));
		
		
		
		
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
