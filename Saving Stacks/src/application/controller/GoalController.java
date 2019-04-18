package application.controller;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import javafx.geometry.Pos;
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

public class GoalController implements EventHandler<ActionEvent>, Initializable{
	
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
	
	public static final String controllerID = "GOALS";
	public static final int MAX_ROWS = 10;
	public static final int MAX_COLS = 6;
	private String filename = "goals.csv";
	private String filePath = "data/" + filename;
	private File file;
	GoalSet goalArray = new GoalSet();
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// sets the Bottom Bar.
		BottomBarController.attachBottomBar(goalAnchor.getChildren(), controllerID);
		
		if( monthlyLimit.getText().equals(""))
			Main.settings.setValueWithProperty("monthly_budget", "0.00");
		else
			Main.settings.setValueWithProperty("monthly_budget", monthlyLimit.getText());
		
		Path path = Paths.get(filePath);
		
		if( Files.exists(path))
		{
			file = new File(filePath);
			goalArray.loadGoals( filePath );
			setGoalstoScene( file, goalArray );
		}
		else
		{
			generateRow(0, "", 0.0, "");
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
			n.setVisible(false);
			generateRow( row + 1, "", 0.0, "" );
		}
		else if( id.equals("remove"))
		{
			if ( row > 0 )
				addAddIcon( row - 1 );
			removeGridRow( row );
			System.out.println(n);
			//addGridRow();
		}
		else if( id.equals("lock"))
		{
			removeButton(btn);
			unlockTextField( row );
			addUnlockIcon( row );
		}
		else if(id.equals("unlock"))
		{
			removeButton(btn);
			lockTextField( row );
			addLockIcon( row );
			addGoaltoArray( row );			
		}		
	}
	
	public void saveHandle( ActionEvent event )
	{
		goalArray.saveGoalArray( filePath, goalArray );
	}
	
	/**
	 * 
	 * @param file
	 * @param goalArray
	 */
	public void setGoalstoScene( File file, GoalSet array )
	{				
		if(array.getGoalArray().size() > 10 )
			System.out.println("Too many goals exist.");
		
		for(int i = 0; i < array.getGoalArray().size(); i++ )
		{
			String tTitle = array.getGoalArray().get(i).getTitle();
			double tAmt = array.getGoalArray().get(i).getAmount();
			String tTime = array.getGoalArray().get(i).getTime();
			generateRow(i, tTitle, tAmt, tTime);
			lockTextField( i );
			addLockIcon( i );
		}
	}
	
	/**
	 * 
	 * @param row
	 */
	public void addGoaltoArray( int row )
	{
		
		TextField text = (TextField) getNodeByRowColumnIndex( row, 0 );
		String goalTitle = text.getText();
		
		TextField amt = (TextField) getNodeByRowColumnIndex( row, 1 );
		String goalAmt = amt.getText();
		
		ChoiceBox<String> time = (ChoiceBox<String>) getNodeByRowColumnIndex( row, 2 );
		String timeframe = time.getValue();

		Goal goal = goalArray.generateGoal( goalTitle, goalAmt, timeframe);
		
		goalArray.addGoal(goal);
	}
	
	/**
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Node getNodeByRowColumnIndex( int row, int column) {
	    Node result = null;
	    ObservableList<Node> childrens = goalGrid.getChildren();
	    for(Node node : childrens) {
	        if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
	            result = node;
	            break;
	        }
	    }
	    return result;
	}
	
	/**
	 * 
	 * @param row
	 */
	public void checkFields( int row )
	{
				
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
		addAddIcon( row );
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
		if( row < MAX_ROWS - 2 )
		{
			Button add = new Button();
			SVGPath addPath = new SVGPath();
			addPath.setContent("M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z");
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
		goal.setStyle("-fx-font: 15px \"Segoe UI\";");
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
		amount.setStyle("-fx-font: 15px \"Segoe UI\";");
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
		time.setStyle("-fx-font: 15px \"Segoe UI\";");
		if( timeframe.length() != 0)
			time.setValue(timeframe);
		goalGrid.add(time, 2, row);
		GridPane.setHalignment(time, HPos.CENTER);
	}

}










