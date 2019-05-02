package application.controller;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import application.Main;
import application.model.GoalSet;
import application.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**   
 * CashController initializes the CashTransactions.fxml scene and communicates between the Transaction.java class 
 * and the CashTransactions.fxml scene to allow the user to enter and save individual cash transaction that would 
 * not have otherwise been uploaded or recorded in the upload scene.
 * 
 * @author Chelsea Flores (rue750)
 * @author dakotakuczenski
 */

public class CashController implements EventHandler<ActionEvent>, Initializable {

	@FXML 
	AnchorPane cashAnchor;
	@FXML 
	private TextField date;
	@FXML 
	private TextField costitem;
	@FXML 
	private TextField nameitem;
	@FXML
	private GridPane goalCheckBox;
	@FXML
	private Label errorMsg, title, cashLabel, linkLabel;
	@FXML
	private Label whoopsdate;
	@FXML
	private Label whoopsprice, whoopstext, whoopsgoal;
	@FXML
	private ListView<String> cashView;
	@FXML
	private Button addButton;
	@FXML
	private Button deletebutton; 
	@FXML
	private Label cashStatus; 
	@FXML
	private Label cashStatus2;
	
	private GoalSet goals = new GoalSet();
	private String filename = "goals.csv";
	private String filePath = "data/" + filename;
	private ObservableList<String> items = FXCollections.observableArrayList();

	
	private static final String controllerID = "CASH";
	
	/**
	 * Initialize function initializes the GUI for the cash.fxml scene.
	 * @param URL location and ResourceBundle resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/*
		 * Attached the menu bar to the bottom of the cash controller.
		 * Additionally, if is_dark_mode_enabled is true, then the page initializes to dark mode,
		 * changing the GUI colors.
		 */
		cashView.setFixedCellSize(60);
		BottomBarController.attachBottomBar(cashAnchor.getChildren(), controllerID);
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			cashAnchor.setStyle("-fx-background-color: #33333d");
			title.setStyle("-fx-text-fill: white");
			
			cashLabel.textFillProperty().bind(title.textFillProperty());
			linkLabel.textFillProperty().bind(title.textFillProperty());
			
			date.setStyle("-fx-background-color: #25282f; -fx-background-radius: 30; -fx-text-fill: white");
			costitem.setStyle("-fx-background-color: #25282f; -fx-background-radius: 30; -fx-text-fill: white");
			nameitem.setStyle("-fx-background-color: #25282f; -fx-background-radius: 30; -fx-text-fill: white");
			
			cashView.setStyle("-fx-background-color: #25282f");
			
			cashView.getStylesheets().add(getClass().getResource("../view/list_view_dark.css").toExternalForm());	
		}
		
		/*
		 * clears the scene of any previously set items that the user may have selected in prior
		 * scene sessions. 
		 */
		clearScene();
		
		/*
		 * Identifies if goals have been saved by the user. If the goals have been saved, the checkboxes 
		 * are initialized with these goals. If not, then a warning message appears, asking the user
		 * to enter goals prior to continuing. Similarly, if the goal file exists but no goals are in the
		 * file, then the goal error message appears.
		 */
		Path path = Paths.get(filePath);
		
		if( Files.exists(path))
		{
			goals.loadGoals( filePath );
			
			if(goals.getGoalMap().size() == 0)
			{
				errorMsg.setVisible(true);
				date.setDisable(true);
				costitem.setDisable(true);
				nameitem.setDisable(true);
				cashView.setDisable(true);
				addButton.setDisable(true);
			}
			else
			{			
				/*
				 * Iterates through the goals and nodes in the gridpane to set the goals to checkbox labels.
				 */
				for(int i = 0; i < goals.getGoalMap().size(); i ++ )
				{
					String goal = goals.getGoalMap().get(i).getTitle();
					CheckBox n = (CheckBox) getNodeByRowColumnIndex( i , 0 );
					
					if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))		{
						n.setTextFill(Color.WHITE);
						n.getStylesheets().add(getClass().getResource("../view/check_box_dark.css").toExternalForm());
					}
					else
					{
						n.setTextFill(Color.BLACK);
						n.getStylesheets().add(getClass().getResource("../view/check_box_light.css").toExternalForm());
					}
					
					n.setVisible(true);
					n.setText(goal);
				}
			}
		}
		else
		{
			/*
			 * Sets the error message and disables GUI components.
			 */
			errorMsg.setVisible(true);
			date.setDisable(true);
			costitem.setDisable(true);
			nameitem.setDisable(true);
			cashView.setDisable(true);
			addButton.setDisable(true);
		}
	}
	
	/**
	 * This function creates a transaction object provided the user input and saves
	 * the transaction to the transactions.csv file when the add button being pressed.
	 * @param event ActionEvent of the add button.
	 */
	@Override
	public void handle(ActionEvent event) {
		/*
		 * clears any error messages and gets all the values from the editable items in the GUI.
		 */
		clearErrorMssgs();
	    String transDate = date.getText();
	    String name = nameitem.getText();
	    CheckBox c = getCheckBoxSelected();
		//date regex
		String dateregex =("^(0[1-9]?|[1-9]|1[0-2])/(0[1-9]|[1-9]|1[0-9]|2[0-9]|30|31)/([0-9]{4})$");
		//price regex
		String priceregex =("[0-9]*\\.?[0-9][0-9]");
		
		/*
		 * If any information is missing, the error messages are set.
		 * Otherwise, a new transaction object is created using the transaction class, adds the transaction to the list view,
		 * and the transaction is saved to a file and the scene is reset.
		 */
		if( !date.getText().matches(dateregex) || !costitem.getText().matches(priceregex) || nameitem.getText().length() < 1 || c == null )
		{
			if (!date.getText().matches(dateregex))
			{
				whoopsdate.setText("Wrong date format");
				whoopsdate.setVisible(true);
				cashStatus.setVisible(false);
			}

			if (!costitem.getText().matches(priceregex))
			{
				whoopsprice.setText("Wrong price format");
				whoopsprice.setVisible(true);
				cashStatus.setVisible(false);
			}
			
			if( nameitem.getText().length() < 1 )
			{
				whoopstext.setText("Please enter a transaction name.");
				whoopstext.setVisible(true);
				cashStatus.setVisible(false);
			}
			
			if( c == null )
			{
				whoopsgoal.setText("Please select a goal.");
				whoopsgoal.setVisible(true);
				cashStatus.setVisible(false);
			}
		
		}
		
		else
		{
			int id = Transaction.establishTransId();
			LocalDate entryDate = LocalDate.now(); 
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		    String strDate = entryDate.format(formatter);
		    String tag = c.getText();
		    double amount = Double.parseDouble(costitem.getText());
			Transaction trans = new Transaction(id, strDate, transDate, name, tag, amount);
			Transaction.saveTransaction( trans );
			items.add(trans.toStringList());
			cashView.setItems(items);
			resetScene();
		}
	}
	
	/**
	 * handleChecks ensures that only one checkbox in the grid can be selected
	 * at any given time.
	 * @param event ActionEvent of a checkbox being checked.
	 */
	public void handleChecks( ActionEvent event )
	{
		/*
		 * Gets the checkbox of the event.
		 */
		CheckBox c = (CheckBox) event.getSource();
		
		/*
		 * setSelected to false for all the checkboxes in the grid and
		 * then sets the checkbox of the event to true.
		 */
		for(int i = 0; i < GoalController.MAX_ROWS; i++ )
		{
			CheckBox n = (CheckBox) getNodeByRowColumnIndex( i , 0 );
			n.setSelected(false);
		}
		
		c.setSelected(true);
	}
	
	/**
	 * Completely clears the scene and initializes/empties all the 
	 * GUI components.
	 */
	public void clearScene()
	{
		clearErrorMssgs();
		date.setText("");
		nameitem.setText("");
		costitem.setText("");
		cashStatus.setText("");
		
		for(int i = 0; i < GoalController.MAX_ROWS; i++ )
		{
			CheckBox n = (CheckBox) getNodeByRowColumnIndex( i , 0 );
			n.setVisible(false);
			n.setSelected(false);
		}
	}
	
	/**
	 * clearErrorMssgs setVisible to false for all the error messages on the 
	 * cash scene.
	 */
	public void clearErrorMssgs()
	{
		errorMsg.setVisible(false);
		whoopsprice.setVisible(false);
		whoopsdate.setVisible(false);
		whoopstext.setVisible(false);
		whoopsgoal.setVisible(false);
		cashStatus.setVisible(true);
		cashStatus2.setVisible(true);
		
	}
	
	/**
	 * resetScene resets the GUI components that can be updated by the user.
	 */
	public void resetScene()
	{
		clearErrorMssgs();
		date.setText("");
		nameitem.setText("");
		costitem.setText("");
		cashStatus.setText("Successfully added");
		cashStatus2.setText("");
		
		
		for(int i = 0; i < GoalController.MAX_ROWS; i++ )
		{
			CheckBox n = (CheckBox) getNodeByRowColumnIndex( i , 0 );
			n.setSelected(false);
		}
	}
	
	/**
	 * getNodeByRowColumnIndex takes the row and column index and returns the
	 * gridpane node at that location.
	 * 
	 * @param row int index of the grid
	 * @param column int index of the grid
	 * @return Node of the node in the gridpane with that row and column.
	 */
	public Node getNodeByRowColumnIndex( int row, int column) {
		
	    Node result = null;
	    ObservableList<Node> childrens = goalCheckBox.getChildren();
	    
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
	
	/**
	 * getCheckBoxSelected iterates through the grid and returns the checkbox 
	 * for which isSelected is true. If no checkbox is selected, null is returned.
	 * @return CheckBox of the selected checkbox.
	 */
	public CheckBox getCheckBoxSelected()
	{
		for(int i = 0; i < GoalController.MAX_ROWS; i++ )
		{
			CheckBox n = (CheckBox) getNodeByRowColumnIndex( i , 0 );
			if( n.isSelected() == true)
				return n;
		}
		return null;
	}

	/**
	 * 
	 * @param delete
	 */
	@FXML
	public void delete(ActionEvent delete){
		
		cashView.getItems().removeAll(cashView.getSelectionModel().getSelectedItem());
		cashStatus2.setText("Successfully deleted");
		cashStatus.setVisible(false);
		
		//Transaction.deleter(cashView.getSelectionModel().getSelectedItem(), filePath);
	}
}


