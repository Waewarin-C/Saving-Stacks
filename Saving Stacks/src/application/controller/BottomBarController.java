package application.controller;

import java.io.IOException;
import java.util.Arrays;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


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
	
	private Pane backingPane;
	private String controllerID;
	
	ObservableList<Button> buttons;
	ObservableList<Label> labels;
	
	/**
	 * Constructs the bottom bar navigation.
	 * 
	 * @param stage Stage - stage for computations.
	 */
	public BottomBarController(String controllerID)
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
		
		
		buttons = this.generateBarButtons();
		
		backingPane.getChildren().addAll(buttons);
		
		
		
		this.controllerID = controllerID;
		
		
	}
	
	/**
	 * Utility function allowing caller to attach bar to given anchor point.
	 * 
	 * @param anchor ObservableList<Node> - Preferably the children given by .getChildren() on a Parent subclass.
	 * @param controllerID TODO
	 */
	public static void attachBottomBar(ObservableList<Node> anchor, String controllerID)
	{
		BottomBarController bc = new BottomBarController(controllerID);
		anchor.add(bc.getBackingPane());
		bc.enactPushActions();

	}
	
	/**
	 * Manages the pushing of scenes and displaying the 
	 * current tab selector. 
	 * NOTE: This can be a dangerous function as it assumes there are no more than
	 * FIVE items in the ObservableList bar button items.
	 */
	public void enactPushActions()
	{
		Button home, cash, upload, goals, settings;
		
		home = this.getBarButtons().get(0);
		cash = this.getBarButtons().get(1);
		upload = this.getBarButtons().get(2);
		goals = this.getBarButtons().get(3);
		settings = this.getBarButtons().get(4);
		
		
		attachHandlerWithView(home, "Home.fxml");
		attachHandlerWithView(settings, "Setting.fxml");
		attachHandlerWithView(goals, "GoalPage.fxml");
		
		if (this.getControllerID().equals("HOME"))
		{
			home.setFont(new Font("Segoe UI bold", 14));
			home.setDisable(true);
			home.setStyle("-fx-opacity: 1.0");
		}
		else if (this.getControllerID().equals("SETTINGS"))
		{
			settings.setFont(new Font("Segoe UI bold", 14));
			settings.setDisable(true);
			settings.setStyle("-fx-opacity: 1.0");
		}
		else if (this.getControllerID().equals("GOALS"))
		{
			goals.setFont(new Font("Segoe UI bold", 14));
			goals.setDisable(true);
			goals.setStyle("-fx-opacity: 1.0");
		}
		
		
		
	}
	
	/**
	 * Generates the buttons and properties associated
	 * with the items used to navigate 
	 * through the app's interface.
	 * 
	 * @return ObservableList<Button> - Bar buttons.
	 */
	public ObservableList<Button> generateBarButtons()
	{
		
		Button home = new Button("Home");
		home.setLayoutX(14);
		home.setPrefWidth(110);
		
		
		Button cash = new Button("Cash Transactions");
		cash.setLayoutX(155);
		cash.setPrefWidth(131);
		
		
		Button upload = new Button("Upload Files");
		upload.setLayoutX(345);
		upload.setPrefWidth(110);
		
		
		Button goals = new Button("Goals");
		goals.setLayoutX(507);
		goals.setPrefWidth(110);
		
		
		Button settings = new Button("Settings");
		settings.setLayoutX(663);
		settings.setPrefWidth(110);
		
		
		ObservableList<Button> buttons = FXCollections.observableList(Arrays.asList(home, cash, upload, goals, settings));
		
		for (Button b : buttons)
		{
			b.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
			b.setLayoutY(57);
			b.setFont(new Font("Segoe UI",14));
		}
		
		
		return buttons;
		
	}
	
	
	/**
	 * Gets the current bar button items.
	 * 
	 * @return ObservableList<Button> - All buttons in the list.
	 */
	public ObservableList<Button> getBarButtons()
	{
		return this.buttons;
	}
	
	
	/**
	 * Takes a button item and attaches an event handler
	 * to push to the corresponding fxml file.
	 * 
	 * @param selection Button - button object to add listener to.
	 * @param fxml String - name of fxml file.
	 */
	public void attachHandlerWithView(Button selection, String fxml)
	{
		selection.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				try {
					Parent root = FXMLLoader.load(Main.class.getResource("view/" + fxml));
					Main.stage.setScene(new Scene(root, 800, 800));
					Main.stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}

		});
	}
	
	
	
	/**
	 * Gets the backing pane, and inherently, the children attached to this Node. 
	 * 
	 * @return Pane - returns the BackingPane.
	 */
	public Pane getBackingPane()
	{
		return backingPane;
	}

	/**
	 * Returns the corresponding controller ID "tag"
	 * represented as a string constant in the
	 * respective controllers.
	 * 
	 * @return String - controllerID for pushing.
	 */
	public String getControllerID()
	{
		return controllerID;
	}
	
	/**
	 * Sets the controllerID for the object with passed in 
	 * string value. 
	 * Recommended to pass in tags 
	 * corresponding to that of the controller, i.e. "HOME" etc.
	 * 
	 * @param controllerID String - controllerID for pushing.
	 */
	public void setControllerID(String controllerID)
	{
		this.controllerID = controllerID;
	}
	
	
}
