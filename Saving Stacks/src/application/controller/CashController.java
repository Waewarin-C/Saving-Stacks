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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;


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
	private Label errorMsg;
	
	GoalSet goals = new GoalSet();
	private String filename = "goals.csv";
	private String filePath = "data/" + filename;
	
	private static final String controllerID = "CASH";
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		BottomBarController.attachBottomBar(cashAnchor.getChildren(), controllerID);
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			cashAnchor.setStyle("-fx-background-color: #33333d");
		}
		
		clearScene();
		
		Path path = Paths.get(filePath);
		
		if( Files.exists(path))
		{
			goals.loadGoals( filePath );
			
			if(goals.getGoalMap().size() == 0)
			{
				errorMsg.setVisible(true);
			}
			else
			{					
				for(int i = 0; i < goals.getGoalMap().size(); i ++ )
				{
					String goal = goals.getGoalMap().get(i).getTitle();
					CheckBox n = (CheckBox) getNodeByRowColumnIndex( i , 0 );
					n.setVisible(true);
					n.setText(goal);
				}
			}
		}
		else
		{
			errorMsg.setVisible(true);
		}
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		int id = Transaction.establishTransId();
		LocalDate entryDate = LocalDate.now(); 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
	    String strDate = entryDate.format(formatter);
	    String transDate = date.getText();
	    String name = nameitem.getText();
	    CheckBox c = getCheckBoxSelected();
	    String tag = c.getText();
	    double amount = Double.parseDouble(costitem.getText());
		Transaction trans = new Transaction(id, strDate, transDate, name, tag, amount);
		
		Transaction.saveTransaction( trans );
		
		resetScene();
	}
	
	/**
	 * 
	 */
	public void clearScene()
	{
		errorMsg.setVisible(false);
		date.setText("");
		nameitem.setText("");
		costitem.setText("");
		
		for(int i = 0; i < GoalController.MAX_ROWS; i++ )
		{
			CheckBox n = (CheckBox) getNodeByRowColumnIndex( i , 0 );
			n.setVisible(false);
			n.setSelected(false);
		}
	}
	
	/**
	 * 
	 */
	public void resetScene()
	{
		errorMsg.setVisible(false);
		date.setText("");
		nameitem.setText("");
		costitem.setText("");
		
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
}

// to deal with the check boxes I will make a boolean that will return one true and the others false from my previous lab. 

// reason for not implementing this rn is fxml is not opening and dont want to use wrong variables. 

// to clear the textfields just do it for each one
// TextField.clear

//this is for the price boolean 
//private boolean isint(TextField input, String whoops){
//	try{
//		int total=Integer.parseInt(input.getText())
//		return input; 
//	}catch(NumberFormatException e){
//		<we can have a text field below the price thing that'll display error, using whoops>
//	}
//}

//this is for date confirmation
//textField.date().addListener((arg0, oldValue, newValue) -> {
    //if (!newValue) { //when focus lost
        //if(!textField.getText().matches("[0-9][0-9]\/[0-9][0-9]|\/[0-9][0-9]")){
            //when it not matches the pattern (XX/XX/XX)
            //set the textField empty
            //textField.setText("");
        //}
    //}
//});


//while(!scan.hasNextInt()) scan.next();
//int demoInt = scan.nextInt();
// idea for date
