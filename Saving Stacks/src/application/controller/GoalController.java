package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;

public class GoalController implements EventHandler<ActionEvent>, Initializable{
	
	@FXML
	private GridPane goalGrid;
		
	@FXML
	private AnchorPane goalAnchor;
	
	public static final String controllerID = "GOALS";
	public static int row;
	File file;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// sets the Bottom Bar.
		BottomBarController.attachBottomBar(goalAnchor.getChildren(), controllerID);
		
		if( file == null )
		{
			row = 0;
			generateRowIcons(row, null);	
		}


		
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		
	}

	
	public void generateRowIcons( int row, File f )
	{
		//ObservableList<Node> goalGrid = this.getGoalGrid().getChildren();
		if( f == null)
		{
			Button unlock = new Button();
			SVGPath unlockPath = new SVGPath();
			unlockPath.setContent("M18 8h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6h2c0-1.66 1.34-3 3-3s3 1.34 3 3v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zm0 12H6V10h12v10zm-6-3c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2z");
			unlock.setGraphic(unlockPath);
			unlock.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			goalGrid.add(unlock, 3, row);
		}
		else
		{
			Button lock = new Button("lock");
			SVGPath lockPath = new SVGPath();
			lockPath.setContent("M18 8h-1V6c0-2.76-2.24-5-5-5S7 3.24 7 6v2H6c-1.1 0-2 .9-2 2v10c0 1.1.9 2 2 2h12c1.1 0 2-.9 2-2V10c0-1.1-.9-2-2-2zM9 6c0-1.66 1.34-3 3-3s3 1.34 3 3v2H9V6zm9 14H6V10h12v10zm-6-3c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2z");
			lock.setGraphic(lockPath);
			lock.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));	
			goalGrid.add(lock, 3, row);
		}
	
		Button delete = new Button("delete");
		SVGPath deletePath = new SVGPath();
		deletePath.setContent("M7 11v2h10v-2H7zm5-9C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8z");
		delete.setGraphic(deletePath);
		delete.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));		
		goalGrid.add(delete, 4, row);
	
		
		Button add = new Button("add");
		SVGPath addPath = new SVGPath();
		addPath.setContent("M9.17 6l2 2H20v10H4V6h5.17M10 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2h-8l-2-2z");
		add.setGraphic(addPath);
		add.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));		
		goalGrid.add(add, 4, row);
		
	}
}










