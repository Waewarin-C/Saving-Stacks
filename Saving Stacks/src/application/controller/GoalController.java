package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GoalController implements EventHandler<ActionEvent>, Initializable{
	
	public GridPane gridPane;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		
	}
	
	public void lockPressed()
	{
		System.out.print("lock");
	}
	
	public void removePressed()
	{
		System.out.print("remove");
	}
	
	public void addPressed( MouseEvent event )
	{
		Node a = (Node) event.getSource();
		int row = GridPane.getRowIndex(a).intValue();
		int column = GridPane.getColumnIndex(a).intValue();
		
		a.setVisible(false);
		
		for(int i = 0; i <= column; i++ )
		{
			Node b = getNode( row + 1, i );
			b.setVisible(true);
		}
		
		
	}
	
	public Node getNode( int row, int column )
	{		
		for( Node n : gridPane.getChildren() )
		{
			if( GridPane.getRowIndex(n) == row && GridPane.getColumnIndex(n) == column)
				return n;				
		}
		
		return null;
	}
	
	


}
