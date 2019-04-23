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
import javafx.scene.control.CheckBox;
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
	private Label goalError;
	@FXML
	private Label lockError;
	@FXML
	private Label title, budgetLabel;
	
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
		}
		
		// sets the Bottom Bar.
		BottomBarController.attachBottomBar(goalAnchor.getChildren(), controllerID);
		
		//TODO: set lock/unlock to the Monthly Spending Limit
		
		//TODO: set color scene and light/dark settings
		
		/*
		 * TODO: Error Handling
		 * 		Unlock/Lock -> will not lock unless selection is made in all 3 fields.
		 * 		Plus -> cannot click add unless lock has been selected.
		 * 		Save -> cannot save unless lock is clicked.
		 */
	
		if( monthlyLimit.getText().equals(""))
			Main.settings.setValueWithProperty("monthly_budget", "0.00");
		else
			Main.settings.setValueWithProperty("monthly_budget", monthlyLimit.getText());
		
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
			//TODO: fix the remove functionality.
			removeGridRow( row );
			clearRow( goalMap );
			goalMap = goalMap.removeGoal( goalMap, row );
			GoalSet.saveGoalArray( filePath, goalMap );
			
			setGoalstoScene();
		}
		else if( id.equals("lock"))
		{
			removeButton(btn);
			unlockTextField( row );
			addUnlockIcon( row );
		}
		else if(id.equals("unlock"))
		{
			System.out.println(checkFields( row ));
			lockError.setVisible(false);
			removeButton(btn);
			lockTextField( row );
			addLockIcon( row );
			addGoaltoMap( row );			
		}		
	}
	
	/**
	 * 
	 * @param event
	 */
	public void saveHandle( ActionEvent event )
	{
		GoalSet.saveGoalArray( filePath, goalMap );
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
	
	public void clearRow( GoalSet goal )
	{
		int row = goal.getGoalMap().size() - 1;
		
		for(int i = 0; i < MAX_COLS; i++)
		{
			Node n = getNodeByRowColumnIndex( row, i);
			goalGrid.getChildren().remove(n);
		}
	}
	
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
	
	public Boolean checkFields( int row )
	{
		Boolean result = null;
		
		TextField title = (TextField) getNodeByRowColumnIndex( row, 0);
		TextField amount = (TextField) getNodeByRowColumnIndex( row, 1);
		ChoiceBox<String> timeframe = (ChoiceBox<String>) getNodeByRowColumnIndex( row, 2);
		String t = title.getText();
		System.out.println(t);
		String a = amount.getText();
		System.out.println(a);
		String f = timeframe.getValue();
		System.out.println(f);
		
		/*
		if(t.length() < 1 && a.length() < 1 || f.equals("") )
			result = false;
		else
			result = true;*/
		
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

		System.out.println( goalTitle + goalAmt + timeframe);
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
	public void removeGridRow( int row )
	{
		ObservableList<Node> children = goalGrid.getChildren();
		ObservableList<Node> temp = FXCollections.observableArrayList();
		
		for( Node n : children)
		{		
			if(GridPane.getRowIndex(n) == row )
			{	
				n.setDisable(true);
				temp.add(n);
			}
		}

		goalGrid.getChildren().removeAll(temp);
	}
		
	/**
	 * 
	 * @param row
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
			goal.setStyle("-fx-font: 15px \"Segoe UI\"; -fx-text-fill: white; -fx-background-color: #F5F5F5; -fx-background-radius: 30");
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
			amount.setStyle("-fx-font: 15px \"Segoe UI\"; -fx-text-fill: white; -fx-background-color: #F5F5F5; -fx-background-radius: 30");
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










