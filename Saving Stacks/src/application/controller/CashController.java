package application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import application.Main;
import application.model.Transaction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *  NOTES AND TODO	
 * 
 * So I have check boxes for the goals, but does this return a int or string? aka is this idNumber or tag?
 * I plan on doing a return true and such for the checkboxes and that will tell what goal they click.
 * can I leave the text as goal or do I need to grab the goals name? I think you all only set it as ints.     
 * 
 * @author dakotakuczenski
 */

public class CashController implements Initializable {

	@FXML AnchorPane cashAnchor;
	@FXML Button addbutton;
	@FXML TextField date;
	@FXML TextField costitem;
	@FXML TextField nameitem;
	@FXML ListView<String> CashView;
	@FXML Label total;
	@FXML TextField easteregg;
	
	//transaction was to cashtransaction. 
	ArrayList<Transaction> cashArray = new ArrayList<Transaction>();
	ArrayList<String> F;
	Transaction money;
	
	//whats this? GABEEEEE
	private static final String controllerID = "CASH";
	
	
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		F = new ArrayList<String>();
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			cashAnchor.setStyle("-fx-background-color: #33333d");
		}
		
		CashView.setItems(FXCollections.observableList(F));
		//total may or may not be used. but would be interesting, hard part being does it only show whats added? 
		//another thing I question but would have to see if I could, is a remove button... 
		//total.setText();
		BottomBarController.attachBottomBar(cashAnchor.getChildren(), controllerID);
		
	}
	
}
// nanana shortay is a melodyyy 2:12am