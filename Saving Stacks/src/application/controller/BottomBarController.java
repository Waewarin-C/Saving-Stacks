package application.controller;


import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


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
	public BottomBarController()
	{
		backingPane = new Pane();
		
		backingPane.setStyle("-fx-border-color: #E0E0E0");
		
		DropShadow ds = new DropShadow();
		ds.setWidth(21.0);
		ds.setHeight(21.0);
		ds.setRadius(10);
		ds.setSpread(0.2);
		ds.setColor(Color.color(0, 0, 0, .18));
		
		backingPane.setEffect(ds);
	
		backingPane.setPrefWidth(800);
		backingPane.setPrefHeight(88);
		backingPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		backingPane.setLayoutX(0);
		backingPane.setLayoutY(712);
		
	}
	
	/**
	 * Utility function allowing caller to attach bar to given anchor point.
	 * @param anchor ObservableList<Node> - Preferably the children given by .getChildren() on a Parent subclass.
	 */
	public static void attachBottomBar(ObservableList<Node> anchor)
	{
		BottomBarController bc = new BottomBarController();
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
