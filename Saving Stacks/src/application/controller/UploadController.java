package application.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Main;
import application.model.Goal;
import application.model.GoalSet;
import application.model.Transaction;
import application.model.UploadManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class UploadController implements EventHandler<ActionEvent>, Initializable{

	@FXML private AnchorPane uploadAnchor;
	
	@FXML private Label warning, uploadPrompt, date, tranTitle, amnt, goalTitle, countLabel;
	
	@FXML private Button fileButton, continueButton, saveButton;

	@FXML private GridPane gridView;
	
	private ActionEvent moveToNextPage;
	
	private int arrayIndicator;
	private ArrayList<Transaction> transactions;
	private ArrayList<String> goalNames;
	private HashMap<String, Goal> goalMap;
	private GoalSet goals;
	
	/**
	 *  NOTE: the following lists are for removal from GridPane
	 */
	private ArrayList<ChoiceBox<String>> choiceBoxes;
	private ArrayList<Label> labels;
	private ArrayList<TextField> textFields;
	
	
	
	private static final String BACKGROUND_COLOR_STYLE = "-fx-background-color: #33333d";
	private static final String controllerID = "UPLOAD";
	private static final int MAX_ROWS = 11;
	
	
	@Override
	public void handle(ActionEvent arg0) {
		
		
		
		FileChooser fc = new FileChooser();
		
		
		fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
		fc.setTitle("Select File to Upload.");
		UploadManager.readFile(fc.showOpenDialog(new Stage()));
		
		if (UploadManager.status.equals("invalid"))
		{
			warning.setVisible(true);
			return;
		}
		
		transactions = UploadManager.getTransactions();
		gridView.toFront();
		
		continueButton.setVisible(true);
		fileButton.setVisible(false);
		warning.setVisible(false);
		date.setVisible(true); 
		tranTitle.setVisible(true);
		amnt.setVisible(true);
		goalTitle.setVisible(true);
		saveButton.setVisible(true);
		
		//IDEA: Filter in the transactions into an intermediary data structure, and have them 11 at a time
		//presented to the user. They save, and it recycles to the next set of transactions.
		int cnt = 0;
		for (int i = 0; i < transactions.size(); i++, cnt++)
		{
			
			arrayIndicator = i;
			if (cnt == MAX_ROWS)
			{
				break;
			}
			
			Label l = new Label(transactions.get(i).getTransDate());
			
			DecimalFormat df = new DecimalFormat("#.00");
	        String strAmt = df.format(transactions.get(i).getAmount());
			Label price = new Label(strAmt);
			
			TextField tf = new TextField();
			tf.setText(transactions.get(i).getName());
			
			ChoiceBox<String> cb = new ChoiceBox<>();
			cb.getItems().add("Unset");
			cb.getItems().addAll(goalNames);
			cb.setValue("Unset");
			
			if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
			{
				cb.setStyle("");
				cb.getStylesheets().add(getClass().getResource("../view/choice_dark.css").toExternalForm());
				
				tf.setStyle("-fx-background-color: #25282f; -fx-background-radius: 30; -fx-text-fill: white");
				
				l.setTextFill(Color.WHITE);
				price.setTextFill(Color.WHITE);
				
			}
			else
			{
				cb.setStyle("");
				cb.setStyle("-fx-background-color: #F5F5F5; -fx-background-radius: 30; -fx-mark-color: black");
				
				tf.setStyle("-fx-background-color: #F5F5F5; -fx-background-radius: 30; -fx-text-fill: black");
				
				l.setTextFill(Color.BLACK);
				price.setTextFill(Color.BLACK);
				
			}
			
			
			GridPane.setHalignment(l,  HPos.CENTER);
			GridPane.setHalignment(tf,  HPos.CENTER);
			GridPane.setHalignment(price,  HPos.CENTER);
			GridPane.setHalignment(cb,  HPos.CENTER);
			
			gridView.add(l, 0, i);
			labels.add(l);
			
			gridView.add(tf, 1, i);
			textFields.add(tf);
			
			gridView.add(price, 2, i);
			labels.add(price);
			
			gridView.add(cb, 3, i);
			choiceBoxes.add(cb);
			
		}
		
		countLabel.setText(arrayIndicator + "/" + transactions.size());
		countLabel.setVisible(true);
		
	}

	
	public void getNextItems(ActionEvent arg0)
	{
		
		if (arrayIndicator == transactions.size()-1)
		{
			return;
		}
		
		moveToNextPage = arg0;
		
		gridView.getChildren().removeAll(choiceBoxes);
		gridView.getChildren().removeAll(labels);
		gridView.getChildren().removeAll(textFields);
		
		
		choiceBoxes.clear();
		labels.clear();
		textFields.clear();
		
		int cnt = 0;
		for (int i = arrayIndicator; i < transactions.size(); i++, cnt++)
		{
			arrayIndicator = i;
			if (cnt == MAX_ROWS)
			{
				break;
			}
			
			Label l = new Label(transactions.get(i).getTransDate());
			
			DecimalFormat df = new DecimalFormat("#.00");
	        String strAmt = df.format(transactions.get(i).getAmount());
			Label price = new Label(strAmt);
			
			TextField tf = new TextField();
			tf.setText(transactions.get(i).getName());
			
			ChoiceBox<String> cb = new ChoiceBox<>();
			
			
			cb.getItems().add("Unset");
			cb.getItems().addAll(goalNames);
			cb.setValue("Unset");
			
			
			if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
			{
				cb.setStyle("");
				cb.getStylesheets().add(getClass().getResource("../view/choice_dark.css").toExternalForm());
				
				tf.setStyle("-fx-background-color: #25282f; -fx-background-radius: 30; -fx-text-fill: white");
				
				l.setTextFill(Color.WHITE);
				price.setTextFill(Color.WHITE);
				
			}
			else
			{
				cb.setStyle("");
				cb.setStyle("-fx-background-color: #F5F5F5; -fx-background-radius: 30; -fx-mark-color: black");
				
				tf.setStyle("-fx-background-color: #F5F5F5; -fx-background-radius: 30; -fx-text-fill: black");
				
				l.setTextFill(Color.BLACK);
				price.setTextFill(Color.BLACK);
				
			}
			
			
			GridPane.setHalignment(l,  HPos.CENTER);
			GridPane.setHalignment(tf,  HPos.CENTER);
			GridPane.setHalignment(price,  HPos.CENTER);
			GridPane.setHalignment(cb,  HPos.CENTER);
			
			gridView.add(l, 0, cnt);
			labels.add(l);
			
			gridView.add(tf, 1, cnt);
			textFields.add(tf);
			
			gridView.add(price, 2, cnt);
			labels.add(price);
			
			gridView.add(cb, 3, cnt);
			choiceBoxes.add(cb);
			
		
		}

		
		if (arrayIndicator == transactions.size() - 1)
		{
			countLabel.setText(arrayIndicator + 1 + "/" + transactions.size());
			continueButton.setVisible(false);
		}
		else
		{
			countLabel.setText(arrayIndicator + "/" + transactions.size());
		}
		
		
	}

	
	public void saveChanges(ActionEvent arg0)
	{
		
		for (int i = 0; i < textFields.size(); i++)
		{
			@SuppressWarnings("unchecked")
			String choice = ((ChoiceBox<String>) getNodeByRowColumnIndex(i, 3)).getValue();
			if (choice.equals("Unset"))
			{
				continue;
			}
			transactions.get(i).setName(textFields.get(i).getText());
			transactions.get(i).setTag(choice);
		}
		
		Transaction.saveTransaction(transactions);
		getNextItems(moveToNextPage);
	}
	
	
	public Node getNodeByRowColumnIndex( int row, int column) 
	{
	    Node result = null;
	    ObservableList<Node> childrens = gridView.getChildren();
	    for(Node node : childrens) 
	    {
	        if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) 
	        {
	            result = node;
	            break;
	        }
	    }
	    return result;
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		gridView.toBack();
		
		arrayIndicator = 0;
		
		choiceBoxes = new ArrayList<>();
		labels = new ArrayList<>();
		textFields = new ArrayList<>();
		
		GridPane.setHalignment(saveButton,  HPos.CENTER);
		saveButton.setVisible(false);
		countLabel.setVisible(false);
		countLabel.textFillProperty().bind(warning.textFillProperty());
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			uploadAnchor.setStyle(BACKGROUND_COLOR_STYLE);
			
			uploadPrompt.setStyle("-fx-text-fill: white");
			warning.textFillProperty().bind(uploadPrompt.textFillProperty());
			
			date.setTextFill(Color.WHITE); 
			tranTitle.setTextFill(Color.WHITE); 
			amnt.setTextFill(Color.WHITE); 
			goalTitle.setTextFill(Color.WHITE); 
			
			gridView.setStyle("-fx-border-color: #25282f; -fx-border-width: 3; -fx-border-radius: 20");
			
			continueButton.setStyle(BACKGROUND_COLOR_STYLE);
			continueButton.setTextFill(Color.WHITE);
		}

		warning.setVisible(true);
		
		BottomBarController.attachBottomBar(uploadAnchor.getChildren(), controllerID);

		goals = new GoalSet();
		goals.loadGoals("data/goals.csv");
		
		goalMap = new HashMap<>();
		
		
		for (int i = 0; i < goals.getGoalMap().size(); i++)
		{
			goalMap.put(goals.getGoalMap().get(i).getTitle(), goals.getGoalMap().get(i));
		}
		
		goalNames = new ArrayList<>(goalMap.keySet());
		
		
	}
	
	
}
