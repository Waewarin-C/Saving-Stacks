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

public class GoalController implements EventHandler<ActionEvent>, Initializable {
	
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
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
		
		setVisibleFalseErrMssg();
		//TODO: set color scene and light/dark settings
		
		/*
		 * TODO: Error Handling
		 * 		Save -> cannot save unless lock is clicked.
		 * 		Delete goal -> deleting the first row.
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
	 */
	@Override
	public void handle(ActionEvent event) {
		
		Button btn = (Button) event.getSource();
		
        Node n = (Node)event.getSource() ;
        Integer row = GridPane.getRowIndex(n);
        
		String id = btn.getId();
		
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
	 * 
	 * @param event
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
	 * 
	 * @param strNum
	 * @return
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
	 * 
	 * @param file
	 * @param goalArray
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
	 * 
	 * @param row
	 * @return
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
	 * 
	 * @param row
	 * @return
	 */
	public Boolean checkDollarEntry( int row )
	{
		
		TextField amount = (TextField) getNodeByRowColumnIndex( row, 1);
		String amountText = amount.getText();
		Boolean result = isDouble( amountText );
		return result;
	}
	
	/**
	 * 
	 * @param row
	 * @return
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
	 * 
	 * @param row
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
	 * 
	 * @param row
	 * @param column
	 * @return
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
	 * 
	 * @param button
	 */
	public void removeButton( Button button )
	{
		goalGrid.getChildren().remove(button);
	}
	
	/**
	 * 
	 * @param row
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
		
		/*if( size == 0 && row == 0)
		{
			
		}
		else if( size == 1 && row == 0 )
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
		}
		else	
		{
			for( Node n : children)
			{		
				temp.add(n);
			}	
				goalGrid.getChildren().removeAll(temp);
		}*/
	}
	
	public void setVisibleFalseErrMssg()
	{
		lockError.setVisible(false);
		entryError.setVisible(false);
		fieldError.setVisible(false); 
		goalError.setVisible(false);
		monLimError.setVisible(false);
	}
		
	/**
	 * 
	 * @param row
	 */
	public void lockTextField( int row )
	{
		ObservableList<Node> children = goalGrid.getChildren();
		
		//TextField text = (TextField) getNodeByRowColumnIndex( row, 1 );
		//String strText = text.getText();
		//DecimalFormat df = new DecimalFormat("#.00");
		//strText = df.format(strText);
		//text.setText(strText);
		
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
	 * @param row
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
	 * @param row
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
	 * 
	 * @param row
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
	 * 
	 * @param row
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
	 * 
	 * @param row
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
	 * 
	 * @param row
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
	 * 
	 * @param row
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
	 * 
	 * @param row
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
	 * 
	 * @param row
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










