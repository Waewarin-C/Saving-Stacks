package application.controller;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import application.Main;
import application.model.GoalSet;
import application.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


//TODO: implement text area 
//TODO: user data error handling
//TODO: param notes need to be added.
/**   
 * 
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
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
		
		clearScene();
		
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
			errorMsg.setVisible(true);
			date.setDisable(true);
			costitem.setDisable(true);
			nameitem.setDisable(true);
			cashView.setDisable(true);
			addButton.setDisable(true);
		}
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		clearErrorMssgs();
	    String transDate = date.getText();
	    String name = nameitem.getText();
	    CheckBox c = getCheckBoxSelected();
		//datematching
		String dateregex =("^(0[1-9]?|[1-9]|1[0-2])/(0[1-9]|[1-9]|1[0-9]|2[0-9]|30|31)/([0-9]{4})$");	
		String priceregex =("[0-9]*\\.?[0-9][0-9]");
		
		if( !date.getText().matches(dateregex) || !costitem.getText().matches(priceregex) || nameitem.getText().length() < 1 || c == null )
		{
			if (!date.getText().matches(dateregex))
			{
				whoopsdate.setText("Wrong date format");
				whoopsdate.setVisible(true);
				cashStatus.setVisible(false);
			}
			//datematching
			
			//price matching
			
			if (!costitem.getText().matches(priceregex))
			{
				whoopsprice.setText("Wrong price format");
				whoopsprice.setVisible(true);
				cashStatus.setVisible(false);
			}
			//price matching
			
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
	 * 
	 * @param event
	 */
	public void handleChecks( ActionEvent event )
	{
		CheckBox c = (CheckBox) event.getSource();
		
		for(int i = 0; i < GoalController.MAX_ROWS; i++ )
		{
			CheckBox n = (CheckBox) getNodeByRowColumnIndex( i , 0 );
			n.setSelected(false);
		}
		
		c.setSelected(true);
	}
	
	/**
	 * 
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
	 * 
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
	 * 
	 * @param row
	 * @param column
	 * @return
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
	 * 
	 * @return
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
	 * @param delete
	 */
	@FXML
	public void delete(ActionEvent delete){
		
		cashView.getItems().removeAll(cashView.getSelectionModel().getSelectedItem());
		cashStatus2.setText("Successfully deleted");
		cashStatus.setVisible(false);
	}
}


