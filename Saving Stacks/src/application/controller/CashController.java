package application.controller;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.Main;
import application.model.GoalSet;
import application.model.Transaction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 *  NOTES AND TODO	
 * 
 * So I have check boxes for the goals, but does this return a int or string? aka is this idNumber or tag?
 * I plan on doing a return true and such for the checkboxes and that will tell what goal they click.
 * can I leave the text as goal or do I need to grab the goals name? I think you all only set it as ints.     
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
		
		Path path = Paths.get(filePath);
		
		if( Files.exists(path))
		{
			goals.loadGoals( filePath );
			
			if(goals.getGoalMap().size() == 0)
			{
				System.out.println("Please enter goals.");
			}
			else if(goals.getGoalMap().size() > 10 )
			{
				System.out.print("Too many goals");
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
			System.out.println("Please enter goals.");
		}

		
		//F = new ArrayList<String>();
		

		
		//CashView.setItems(FXCollections.observableList(F));
		//total may or may not be used. but would be interesting, hard part being does it only show whats added? 
		//another thing I question but would have to see if I could, is a remove button... 
		//total.setText();

		
	}
	
	public Node getNodeByRowColumnIndex( int row, int column) {
		
	    Node result = null;
	    ObservableList<Node> childrens = goalCheckBox.getChildren();
	    
	    for(Node node : childrens) {
	        if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
	            result = node;
	            break;
	        }
	    }
	    return result;
	}
	/*
	public void addbutton(ActionEvent event) {
		//costitem is messy I need it to be viewed as a int.ValueOf is messy. I will need to test this when I push. or before lol. 
		money = new Transaction(0, null, date.getText(), nameitem.getText(), null, valueOf(costitem.getText()) );
		setViews(money);
		
		//ensures that costitem is a int. people dumb. 
		costitem.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            costitem.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		CashView.setItems(FXCollections.observableList(F));
		
		if(nameitem.getText()== "clubbing"){
			easteregg.setText("Bands to make her dance!");
		}
	}
	
		private double valueOf(String costitem) {
		return valueOf(costitem);
	}
	
	//transaction was to cashtransaction
	void setViews(Transaction money ){
		cashArray.add(money);
		F.add(money.toString());	
	}
	*
	*
	*
	*
	*/

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
// nanana shortay is a melodyyy 2:12am