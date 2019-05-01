package application.controller;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import application.Main;
import application.model.Goal;
import application.model.GoalSet;
import javafx.collections.FXCollections;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

/**
 * The GoalController interacts with the GoalPage.fxml and the GoalSet.java class.
 * If goals are already set up, then the goals are initiated on the screen. Otherwise,
 * a row of the gridpane is set up with blank GUI components. The controller allows you to
 * add, delete and edit goals in the gridpane.
 * 
 * @author Chelsea Flores (rue750)
 */
public class GoalController implements EventHandler<ActionEvent>, Initializable {
	
	/*
	 * FXML GUI components
	 */
	@FXML
	private GridPane goalGrid;
	@FXML
	private AnchorPane goalAnchor;
	@FXML
	private TextField monthlyLimit;
	@FXML
	private GridPane gridPane;
	@FXML
	private Label lockError, entryError, fieldError, goalError, monLimError;
	@FXML
	private Label title, budgetLabel, goalAdd;
	@FXML
	private Button monthlyLimitSave;
	
	public static final String controllerID = "GOALS";
	public static final int MAX_ROWS = 10;
	public static final int MAX_COLS = 6;
	private static final String INPUT_FIELD_STYLE = "-fx-background-color: #25282f; -fx-background-radius: 30; -fx-text-fill: white";
	private static final String BACKGROUND_COLOR_STYLE = "-fx-background-color: #33333d";
	
	private String filename = "goals.csv";
	private String filePath = "data/" + filename;
	private File file;	
	
	GoalSet goalMap = new GoalSet();

	/**
	 * initialize initializes the GoalPage.fxml.
	 * 
	 * @param location URL and resources ResourceBundle
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		/*
		 * Sets dark mode if is_dark_mode_enabled is true.
		 */
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			goalAnchor.setStyle(BACKGROUND_COLOR_STYLE);
			monthlyLimit.setStyle(INPUT_FIELD_STYLE);
			title.setTextFill(Color.WHITE);
			budgetLabel.setTextFill(Color.WHITE);
			goalAdd.setTextFill(Color.WHITE);
		}
		
		// sets the Bottom Bar.
		BottomBarController.attachBottomBar(goalAnchor.getChildren(), controllerID);
		
		//sets error messages to false
		setVisibleFalseErrMssg();

		/*
		 * Identifies if goals have been saved by the user. If the goals have been saved, they are initialized in the screen and then
		 * sets editable to false for the grid row. If the file does not exist, then a warning message appears, asking the user
		 * to enter goals prior to continuing. Similarly, if the goal file exists but no goals are in the
		 * file, then the goal error message appears.
		 */
		Path path = Paths.get(filePath);
		
		if( Files.exists(path))
		{
			file = new File(filePath);
			goalMap.loadGoals( filePath );
			
			if(goalMap.getGoalMap().size() == 0)
			{
				generateRow(0, "", 0.0, "");
				addAddIcon( 0 );
			}
			else
			{	
				setGoalstoScene();
			}
		}
		else
		{
			generateRow(0, "", 0.0, "");
			addAddIcon( 0 );
		}


		
	}
	
	/**
	 * The handle function is triggered when one of the icons in the grid row is select. Depending
	 * on which is icon is pressed a goal can do the following: the grid row can be locked or unlocked
	 * (If the grid row is locked, then a goal object is created and saved. The unlock allows the user to edit
	 * a current goal. ); delete the goal which deletes the grid row as well as the goal from the hashmap; adds
	 * a line to the grid so that another goal can  be added.
	 * 
	 * @param event ActionEvent for hitting one of the icons in the gridpane row.
	 */
	@Override
	public void handle(ActionEvent event) {
		
		/*
		 * Assigns the node of the action event as a button and a node, in addition to the 
		 * the row that the button pressed was in.
		 */
	
		Button btn = (Button) event.getSource();
        Node n = (Node)event.getSource() ;
        Integer row = GridPane.getRowIndex(n); 
		String id = btn.getId();
		
		/*
		 * Reads the button Id (add, delete, unlock, lock) to identify what action the program needs to take.
		 */
		if(id.equals("add"))
		{
			setVisibleFalseErrMssg();
			if( checkLock( row ) == true)
			{
				n.setVisible(false);
				generateRow( row + 1, "", 0.0, "" );
				addAddIcon( row + 1 );
			}
			else
			{
				lockError.setVisible(true);
			}
		}
		else if( id.equals("remove"))
		{
			setVisibleFalseErrMssg();
			Node x = getNodeByRowColumnIndex( row + 1, 0 );
			
			if( x == null && row == 0 )
			{
				TextField text = (TextField) getNodeByRowColumnIndex( row, 0 );
				text.setText("");
				TextField amount = (TextField) getNodeByRowColumnIndex( row, 1 );
				amount.setText("");
				ChoiceBox<String> time = (ChoiceBox<String>) getNodeByRowColumnIndex( row, 2 );
				time.setValue("");
				Button button = (Button) getNodeByRowColumnIndex( row, 3 );
				removeButton( button );
				addUnlockIcon( 0 );
				unlockTextField( row );
				goalMap = goalMap.removeGoal( goalMap, row );
				GoalSet.saveGoalArray( filePath, goalMap );
			}
			else
			{
				int size = goalMap.getGoalMap().size();
				goalMap = goalMap.removeGoal( goalMap, row );
				GoalSet.saveGoalArray( filePath, goalMap );
				clearGrid( size, row );			
				setGoalstoScene();
			}
		}
		else if( id.equals("lock"))
		{
			removeButton(btn);
			unlockTextField( row );
			addUnlockIcon( row );
		}
		else if(id.equals("unlock"))
		{
			Boolean isFilledOut = checkFields( row );
			Boolean isValidNum = checkDollarEntry( row );
			setVisibleFalseErrMssg();
			
			if( !isValidNum || !isFilledOut )
			{
				if( !isFilledOut )
					fieldError.setVisible(true);
				
				if( !isValidNum )
					entryError.setVisible(true);
			}
			else
			{
				setVisibleFalseErrMssg();
				removeButton(btn);
				TextField title = (TextField) getNodeByRowColumnIndex( row, 0 );
				TextField text = (TextField) getNodeByRowColumnIndex( row, 1 );
				double amount = Double.parseDouble(text.getText());
				DecimalFormat df = new DecimalFormat("#.00");
				String strAmt = df.format(amount);
				text.setText( strAmt );
				lockTextField( row );
				addLockIcon( row );
				addGoaltoMap( row );
				GoalSet.saveGoalArray( filePath, goalMap );
				goalAdd.setText("Goal \"" + title.getText() + "\" has been added!" );
			}	
		}		
	}
	
	/**
	 * Checks the value entered into the monthly limit TextField is a number and saves
	 * it to the monthly_budget.
	 * 
	 * @param event ActionEvent of the save button being pressed.
	 */
	public void saveMonthlyHandle( ActionEvent event )
	{
		setVisibleFalseErrMssg();
		String strAmount = monthlyLimit.getText();
		Boolean isNum = isDouble( strAmount );
	    
	    if( isNum )
	    {
	    	DecimalFormat df = new DecimalFormat("#.00"); 
	    	strAmount = df.format(Double.parseDouble(strAmount));
	    	Main.settings.setValueWithProperty("monthly_budget", strAmount);
	    }
	    else
	    {
	    	monLimError.setVisible(true);
	    }	
	}
	
	/**
	 * isDouble takes a string value and checks if that string value is a number value.
	 * 
	 * @param strNum String to be checked for a number value.
	 * @return Boolean value of whether or not the string contains a number.
	 */
	public Boolean isDouble( String strNum )
	{
		Boolean number = true;
	    try {
	    	double value = Double.valueOf(strNum);	
	    }catch(NumberFormatException e) {
	    	number = false;
	    }	
	    
	    return number;
	}
	
	/**
	 * setGoalstoScene iterates through the goal hashmap and sets the data to the GUI components
	 * in the gridpane. Locks the editable fields and initiates the lock icon vs. the unlock icon.
	 */
	public void setGoalstoScene()
	{				
		if(goalMap.getGoalMap().size() > 10 )
			System.out.println("Too many goals exist.");
		
		int i;
		for( i = 0; i < goalMap.getGoalMap().size(); i++ )
		{
			String tTitle = goalMap.getGoalMap().get(i).getTitle();
			double tAmt = goalMap.getGoalMap().get(i).getAmount();
			String tTime = goalMap.getGoalMap().get(i).getTime();
			generateRow(i, tTitle, tAmt, tTime);
			Button btn = (Button) getNodeByRowColumnIndex( i, 3);
			removeButton(btn);
			lockTextField( i );
			addLockIcon( i );
		}
		
		addAddIcon( i - 1 );		
	}

	/**
	 * Checks whether or not the Button id in the lock location is "lock" or "unlock."
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 * @return Boolean value of whether or not the icon id in the gridpane row
	 * is "lock" or "unlock"
	 */
	public Boolean checkLock( int row )
	{
		Boolean result = null;
		Node n = getNodeByRowColumnIndex( row, 3);
		String id = n.getId();
		if(id.equalsIgnoreCase("unlock"))
			result = false;
		else if(id.equalsIgnoreCase("lock"))
			result = true;
		
		return result;
	}
	
	/**
	 * Checks if the dollar amount in the amount TextField is a double value.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 * @return Boolean value of whether or not the user input in the amount Textfield
	 * is a double.
	 */
	public Boolean checkDollarEntry( int row )
	{
		
		TextField amount = (TextField) getNodeByRowColumnIndex( row, 1);
		String amountText = amount.getText();
		Boolean result = isDouble( amountText );
		return result;
	}
	
	/**
	 * checkFields checks the title Textfield, the amount Textfield and the a timeframe
	 * is selected and returns true or false.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 * @return Boolean value of whether or not all the user input fields are
	 * populated.
	 */
	public Boolean checkFields( int row )
	{
		Boolean result = null;
		
		TextField title = (TextField) getNodeByRowColumnIndex( row, 0);
		TextField amount = (TextField) getNodeByRowColumnIndex( row, 1);
		ChoiceBox<String> timeframe = (ChoiceBox<String>) getNodeByRowColumnIndex( row, 2);
		String t = title.getText();
		String a = amount.getText();
		String f = timeframe.getValue();		
		
		if(t != null && a != null && f != null )
			result = true;
		else
			result = false;

		return result;
	}
	
	/**
	 * addGoaltoMap adds the goal to the goalMap hash method established in the GoalSet.java
	 * class. The goal is constructed based on the user input into the the row of the gridpane.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 */
	public void addGoaltoMap( int row )
	{
		TextField text = (TextField) getNodeByRowColumnIndex( row, 0 );
		String goalTitle = text.getText();
		
		TextField amt = (TextField) getNodeByRowColumnIndex( row, 1 );
		String goalAmt = amt.getText();
		
		ChoiceBox<String> time = (ChoiceBox<String>) getNodeByRowColumnIndex( row, 2 );
		String timeframe = time.getValue();

		Goal goal = GoalSet.generateGoal( goalTitle, goalAmt, timeframe);
		
		goalMap.getGoalMap().put(row, goal);
	}
	
	/**
	 * getNodeByRowColumnIndex takes in a grid row and column and returns the node 
	 * of the grid at that location.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 * @param column int of the column to be modified in the gridpane.
	 * @return Node of the gridpane given the row and column on the gridpane.
	 */
	public Node getNodeByRowColumnIndex( int row, int column) 
	{
	    Node result = null;
	    ObservableList<Node> childrens = goalGrid.getChildren();
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
	 * Removes the button passed into the method.
	 * 
	 * @param button Button on the GUI
	 */
	public void removeButton( Button button )
	{
		goalGrid.getChildren().remove(button);
	}
	
	/**
	 * Removes all the nodes that were previously added to the gridpane.
	 * 
	 * @param row int of the row of the gridpane
	 * @param size int size of the goalhash
	 */
	public void clearGrid( int size, int row )
	{
		ObservableList<Node> children = goalGrid.getChildren();
		ObservableList<Node> temp = FXCollections.observableArrayList();
		
		for( Node n : children)
		{		
			temp.add(n);
		}	
		
		goalGrid.getChildren().removeAll(temp);
	}
	
	/**
	 * SetVisible to false for all the error messages.
	 */
	public void setVisibleFalseErrMssg()
	{
		lockError.setVisible(false);
		entryError.setVisible(false);
		fieldError.setVisible(false); 
		goalError.setVisible(false);
		monLimError.setVisible(false);
	}
		
	/**
	 * Locks the GUI items in the row given the row index entered as a parameter.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 */
	public void lockTextField( int row )
	{
		ObservableList<Node> children = goalGrid.getChildren();
		
		for( Node n : children )
		{	
			if(GridPane.getRowIndex(n) == row && GridPane.getColumnIndex(n) < MAX_COLS - 3 )
			{
				n.setDisable(true);
			}
				
		}
	}
	
	/**
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 */
	public void unlockTextField( int row )
	{
		ObservableList<Node> children = goalGrid.getChildren();
		
		for( Node n : children )
		{	
			if(GridPane.getRowIndex(n) == row && GridPane.getColumnIndex(n) < MAX_COLS - 3 )
			{
				n.setDisable(false);
			}
				
		}
	}
	
	/**
	 * 
	 * Generates a row with the goal information previously input by the user.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 * @param title String of the goal
	 * @param dollarAmount double of the dollar amount associated with the goal.
	 * @param timeframe String of the timeframe selected in the ChoiceBox.
	 */
	public void generateRow( int row, String title, double dollarAmount, String timeframe )
	{
		createGoalTextField( row, title );
		createAmtTextField( row, dollarAmount );
		createChoiceBox( row, timeframe );
		addUnlockIcon( row );
		addRemoveIcon( row );
	}
	
	/**
	 * Creates an icon for the functionality that allows you to unlock a grid row.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 */
	public void addUnlockIcon( int row )
	{
		Button unlock = new Button();
		SVGPath unlockPath = new SVGPath();
		unlockPath.setContent("M18 8h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6h2c0-1.66 1.34-3 3-3s3 1.34 3 3v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zm0 12H6V10h12v10zm-6-3c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2z");
		unlockPath.fillProperty().bind(title.textFillProperty());
		unlock.setGraphic(unlockPath);
		unlock.setId("unlock");
		unlock.setOnAction(this);
		unlock.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		goalGrid.add(unlock, 3, row);
	}
	
	/**
	 * Creates an icon for the functionality that allows you to lock a grid row.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 */
	public void addLockIcon( int row )
	{
		Button lock = new Button();
		SVGPath lockPath = new SVGPath();
		lockPath.setContent("M18 8h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zM9 6c0-1.66 1.34-3 3-3s3 1.34 3 3v2H9V6zm9 14H6V10h12v10zm-6-3c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2z");
		lockPath.fillProperty().bind(title.textFillProperty());
		lock.setGraphic(lockPath);
		lock.setId("lock");
		lock.setOnAction(this);
		lock.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));	
		goalGrid.add(lock, 3, row);
	}
		
	/**
	 * Creates an icon for the functionality that allows you to remove a grid row.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 */
	public void addRemoveIcon( int row )
	{
		Button remove = new Button();
		SVGPath removePath = new SVGPath();
		removePath.setContent("M7 11v2h10v-2H7zm5-9C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8z");
		removePath.fillProperty().bind(title.textFillProperty());
		remove.setGraphic(removePath);
		remove.setId("remove");
		remove.setOnAction(this);
		remove.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));		
		goalGrid.add(remove, 4, row);	
	}
	
	/**
	 * Creates an icon for the functionality that allows you to add a grid row as long as the 
	 * index of the row is not 0.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 */
	public void addAddIcon( int row )
	{
		if( row < MAX_ROWS - 1 )
		{
			Button add = new Button();
			SVGPath addPath = new SVGPath();
			addPath.setContent("M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z");
			addPath.fillProperty().bind(title.textFillProperty());
			add.setGraphic(addPath);
			add.setId("add");
			add.setOnAction(this);
			add.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			goalGrid.add(add, 5, row);
		}
	}
	
	/**
	 * Creates a textfield for the title to be entered or set.
	 * 
	 * @param row int of the row to be modified in the gridpane. 
	 */
	public void createGoalTextField( int row, String title )
	{
		TextField goal = new TextField();
		goal.setPromptText("Enter Your Goal");
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			goal.setStyle("-fx-font: 15px \"Segoe UI\"; -fx-text-fill: white; -fx-background-color: #25282f; -fx-background-radius: 30");
		}
		else
		{
			goal.setStyle("-fx-font: 15px \"Segoe UI\"; -fx-text-fill: black; -fx-background-color: #F5F5F5; -fx-background-radius: 30");
		}
		goal.setId("goal");
		if( title.length() != 0 )
		{
			goal.setText(title);
		}
		goalGrid.add(goal, 0, row);
	}
	
	/**
	 * Creates a textfield for the dollar amount to be entered or set.
	 * 
	 * @param row int of the row to be modified in the gridpane.
	 * @param dollarAmt double of the goal.
	 */
	public void createAmtTextField( int row, double dollarAmt )
	{
		TextField amount = new TextField();
		amount.setPromptText("Enter Amount");
		amount.setId("amount");
		amount.setMaxWidth(110.0);
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			amount.setStyle("-fx-font: 15px \"Segoe UI\"; -fx-text-fill: white; -fx-background-color: #25282f; -fx-background-radius: 30");
		}
		else
		{
			amount.setStyle("-fx-font: 15px \"Segoe UI\"; -fx-text-fill: black; -fx-background-color: #F5F5F5; -fx-background-radius: 30");
		}
		
		if( dollarAmt > 0)
		{
			DecimalFormat df = new DecimalFormat("#.00");
			String dollarText = df.format(dollarAmt);
			amount.setText(dollarText);
		}
		goalGrid.add(amount, 1, row);
		GridPane.setHalignment(amount, HPos.CENTER);
	}
	
	/**
	 * Creates a ChoiceBox in the row of the gridpane passed into the method and sets the 
	 * text to the timeframe indicated in the parameters.
	 * 
	 * @param row int value of the grid pane.
	 */
	public void createChoiceBox( int row, String timeframe )
	{
		ChoiceBox<String> time = new ChoiceBox<String>();
		time.getItems().addAll("Weekly", "Monthly", "Yearly");
		time.setId("time");
		time.setMaxWidth(110.0);
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			time.getStylesheets().add(getClass().getResource("../view/choice_dark.css").toExternalForm());
		}
		else
		{
			time.setStyle("-fx-font: 15px \"Segoe UI\"; -fx-background-color: #F5F5F5; -fx-background-radius: 30");
		}
		
		time.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
		if( timeframe.length() != 0)
			time.setValue(timeframe);
		goalGrid.add(time, 2, row);
		GridPane.setHalignment(time, HPos.CENTER);
	}

}










