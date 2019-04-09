package application.model;
import java.util.date;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
/**
 * @author dakotakuczenski
 *my todo list. 
 *make the controller and connect the item to it, along with date and cost. this will grab the total.
 *decide if we need the date.
 */

public class CashTransactions {

	public double total; 
	public String item;
	public String date; 
	public String tag; 
	public int id; 
	
private Transaction CashT = new Transaction( id, date, date, item, tag, total);
	

public void handle(){
	String item = item.getText();
	String total = total.getText(); 
	
}

	
	
	
//	public CashTransactions(){
//		this.cost = cost; 
//		this.item = item; 
//		this.date = date; 
//		total = 0.0;
		
	
//	public void total(double cost){
//		total = total + cost;
//	}
//	
//	public double getTotal() {
//		return total;
//	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
