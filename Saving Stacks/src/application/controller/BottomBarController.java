package application.controller;


import application.Main;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Gabriel Morales
 * 
 * BottomBarController class is intended to provided
 * the functionality for the navigation central
 * to the application. This will contain 
 * all the logic pertaining to the bottom
 * bar as it persists through the views.
 * 
 */

public class BottomBarController {
	
	Pane backingPane;
	
	/**
	 * Constructs the bottom bar navigation.
	 * @param stage Stage - stage for computations.
	 */
	public BottomBarController(Stage stage)
	{
		backingPane = new Pane();
		backingPane.setPrefWidth(800);
		backingPane.setPrefHeight(88);
		backingPane.setLayoutX(0);
		backingPane.setLayoutY(712);
		backingPane.setStyle("-fx-background-color: #FFFFFF");
		backingPane.setStyle("-fx-border-color: #E0E0E0");
		
		DropShadow ds = new DropShadow();
		ds.setWidth(21.0);
		ds.setHeight(21.0);
		ds.setRadius(10);
		ds.setSpread(0.2);
		ds.setColor(Color.color(0, 0, 0, .20));
		
		backingPane.setEffect(ds);
		
	
	}
	
	/**
	 * Utility function allowing caller to attach bar to given anchor point.
	 * @param anchor ObservableList<Node> - Preferably the children given by .getChildren() on a Parent subclass.
	 */
	public static void attachBottomBar(ObservableList<Node> anchor)
	{
		BottomBarController bc = new BottomBarController(Main.stage);
		anchor.add(bc.getBackingPane());
	}
	
	/**
	 * Gets the backing pane, and inherently, the children attached to this Node. 
	 * @return Pane - returns the BackingPane.
	 */
	public Pane getBackingPane()
	{
		return backingPane;
	}

}
