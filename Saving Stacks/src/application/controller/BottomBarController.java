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
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;


/**
 * @author Gabriel Morales
 * 
 * <h1>
 * BottomBarController class is intended to provided
 * the functionality for the navigation central
 * to the application. This will contain 
 * all the logic pertaining to the bottom
 * bar as it persists through the views.
 * </h1>
 */

public class BottomBarController extends Thread{
	
	private Pane backingPane;
	private String controllerID;
	
	ObservableList<Button> buttons;
	ObservableList<SVGPath> paths;
	
	private String describedTint;
	
	
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
		
		backingPane.setLayoutX(0);
		backingPane.setLayoutY(712);
		
		
		buttons = this.generateBarButtons();
		
		if (!Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			backingPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
			this.describedTint = Main.settings.getValueWithProperty("default_tint_color_light");
		}
		else
		{
			this.describedTint = Main.settings.getValueWithProperty("default_tint_color_dark");
			backingPane.setStyle("-fx-background-color: #33333d");
			for (Button b : buttons)
				b.setStyle("-fx-text-fill: white");
		}
		
		backingPane.getChildren().addAll(buttons);
		
		this.controllerID = controllerID;
		
	}
	
	
	
	
	/**
	 * Utility function allowing caller to attach bar to given anchor point.
	 * 
	 * @param anchor ObservableList<Node> - Preferably the children given by .getChildren() on a Parent subclass.
	 * @param controllerID String - Helps identify which controller the bottom controller is connected to currently.
	 * @return BottomBarController - For use in settings only.
	 */
	public static BottomBarController attachBottomBar(ObservableList<Node> anchor, String controllerID)
	{
		BottomBarController bc = new BottomBarController(controllerID);
		anchor.add(bc.getBackingPane());
		bc.enactPushActions();
		
		return bc;

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
		SVGPath homePath, cashPath, uploadPath, settingsPath, goalPath;
		
		home = this.getBarButtons().get(0);
		cash = this.getBarButtons().get(1);
		upload = this.getBarButtons().get(2);
		goals = this.getBarButtons().get(3);
		settings = this.getBarButtons().get(4);
		
		homePath = this.getBarButtonPaths().get(0);
		cashPath = this.getBarButtonPaths().get(1);
		uploadPath = this.getBarButtonPaths().get(2);
		goalPath = this.getBarButtonPaths().get(3);
		settingsPath = this.getBarButtonPaths().get(4);
		
		attachHandlerWithView(home, "Home.fxml");
		attachHandlerWithView(settings, "Setting.fxml");
		attachHandlerWithView(goals, "GoalPage.fxml");
		
		if (this.getControllerID().equals("HOME"))
		{
			home.setFont(new Font("Segoe UI bold", 14));
			home.setDisable(true);
			home.setStyle("-fx-opacity: 1.0");
			
			homePath.setContent("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z");
			homePath.setOpacity(1);

			home.setTextFill(Color.web(this.describedTint));
			
		}
		else if (this.getControllerID().equals("SETTINGS"))
		{
			settings.setFont(new Font("Segoe UI bold", 14));
			settings.setDisable(true);
			settings.setStyle("-fx-opacity: 1.0");
			
			settingsPath.setContent("M15.95 10.78c.03-.25.05-.51.05-.78s-.02-.53-.06-.78l1.69-1.32c.15-.12.19-.34.1-.51l-1.6-2.77c-.1-.18-.31-.24-.49-.18l-1.99.8c-.42-.32-.86-.58-1.35-.78L12 2.34c-.03-.2-.2-.34-.4-.34H8.4c-.2 0-.36.14-.39.34l-.3 2.12c-.49.2-.94.47-1.35.78l-1.99-.8c-.18-.07-.39 0-.49.18l-1.6 2.77c-.1.18-.06.39.1.51l1.69 1.32c-.04.25-.07.52-.07.78s.02.53.06.78L2.37 12.1c-.15.12-.19.34-.1.51l1.6 2.77c.1.18.31.24.49.18l1.99-.8c.42.32.86.58 1.35.78l.3 2.12c.04.2.2.34.4.34h3.2c.2 0 .37-.14.39-.34l.3-2.12c.49-.2.94-.47 1.35-.78l1.99.8c.18.07.39 0 .49-.18l1.6-2.77c.1-.18.06-.39-.1-.51l-1.67-1.32zM10 13c-1.65 0-3-1.35-3-3s1.35-3 3-3 3 1.35 3 3-1.35 3-3 3z");
			settingsPath.setScaleY(1.9);
			settingsPath.setScaleX(2);
			settingsPath.setLayoutY(25);
			settingsPath.setLayoutX(705);
			settingsPath.setOpacity(1);
			
			settings.setTextFill(Color.web(this.describedTint));
			
		}
		else if (this.getControllerID().equals("GOALS"))
		{
			goals.setFont(new Font("Segoe UI bold", 14));
			goals.setDisable(true);
			goals.setStyle("-fx-opacity: 1.0");
			
			
			goalPath.setContent("M13.5.67s.74 2.65.74 4.8c0 2.06-1.35 3.73-3.41 3.73-2.07 0-3.63-1.67-3.63-3.73l.03-.36C5.21 7.51 4 10.62 4 14c0 4.42 3.58 8 8 8s8-3.58 8-8C20 8.61 17.41 3.8 13.5.67zM11.71 19c-1.78 0-3.22-1.4-3.22-3.14 0-1.62 1.05-2.76 2.81-3.12 1.77-.36 3.6-1.21 4.62-2.58.39 1.29.59 2.65.59 4.04 0 2.65-2.15 4.8-4.8 4.8z");
			goalPath.setOpacity(1);
			
			goals.setTextFill(Color.web(this.describedTint));
			
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
		ObservableList<Node> backPane = this.getBackingPane().getChildren();
		
		Button home = new Button("Home");
		SVGPath homePath = new SVGPath();
		homePath.setContent("M12 5.69l5 4.5V18h-2v-6H9v6H7v-7.81l5-4.5M12 3L2 12h3v8h6v-6h2v6h6v-8h3L12 3z");
		homePath.fillProperty().bind(home.textFillProperty());
		homePath.setLayoutX(55);
		
		home.setLayoutX(14);
		home.setPrefWidth(110);
		
		
		Button cash = new Button("Cash Transactions");
		SVGPath cashPath = new SVGPath();
		cashPath.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm.31-8.86c-1.77-.45-2.34-.94-2.34-1.67 0-.84.79-1.43 2.1-1.43 1.38 0 1.9.66 1.94 1.64h1.71c-.05-1.34-.87-2.57-2.49-2.97V5H10.9v1.69c-1.51.32-2.72 1.3-2.72 2.81 0 1.79 1.49 2.69 3.66 3.21 1.95.46 2.34 1.15 2.34 1.87 0 .53-.39 1.39-2.1 1.39-1.6 0-2.23-.72-2.32-1.64H8.04c.1 1.7 1.36 2.66 2.86 2.97V19h2.34v-1.67c1.52-.29 2.72-1.16 2.73-2.77-.01-2.2-1.9-2.96-3.66-3.42z");
		cashPath.fillProperty().bind(cash.textFillProperty());
		cashPath.setLayoutX(215);
		
		
		
		cash.setLayoutX(165);
		cash.setPrefWidth(134);
	
		
		Button upload = new Button("Upload Files");
		SVGPath uploadPath = new SVGPath();
		uploadPath.setContent("M9.17 6l2 2H20v10H4V6h5.17M10 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2h-8l-2-2z");
		uploadPath.fillProperty().bind(upload.textFillProperty());
		uploadPath.setLayoutX(385);
		
		
		
		upload.setLayoutX(345);
		upload.setPrefWidth(110);
	
		
		Button goals = new Button("Goals");
		SVGPath goalPath = new SVGPath();
		goalPath.setContent("M11.57 13.16c-1.36.28-2.17 1.16-2.17 2.41 0 1.34 1.11 2.42 2.49 2.42 2.05 0 3.71-1.66 3.71-3.71 0-1.07-.15-2.12-.46-3.12-.79 1.07-2.2 1.72-3.57 2zM13.5.67s.74 2.65.74 4.8c0 2.06-1.35 3.73-3.41 3.73-2.07 0-3.63-1.67-3.63-3.73l.03-.36C5.21 7.51 4 10.62 4 14c0 4.42 3.58 8 8 8s8-3.58 8-8C20 8.61 17.41 3.8 13.5.67zM12 20c-3.31 0-6-2.69-6-6 0-1.53.3-3.04.86-4.43 1.01 1.01 2.41 1.63 3.97 1.63 2.66 0 4.75-1.83 5.28-4.43C17.34 8.97 18 11.44 18 14c0 3.31-2.69 6-6 6z");
		goalPath.fillProperty().bind(goals.textFillProperty());
		goalPath.setLayoutX(549);
	
		
		goals.setLayoutX(507);
		goals.setPrefWidth(110);
		
		
		
		Button settings = new Button("Settings");
		SVGPath settingPath = new SVGPath();
		settingPath.setContent("M19.43 12.98c.04-.32.07-.64.07-.98 0-.34-.03-.66-.07-.98l2.11-1.65c.19-.15.24-.42.12-.64l-2-3.46c-.09-.16-.26-.25-.44-.25-.06 0-.12.01-.17.03l-2.49 1c-.52-.4-1.08-.73-1.69-.98l-.38-2.65C14.46 2.18 14.25 2 14 2h-4c-.25 0-.46.18-.49.42l-.38 2.65c-.61.25-1.17.59-1.69.98l-2.49-1c-.06-.02-.12-.03-.18-.03-.17 0-.34.09-.43.25l-2 3.46c-.13.22-.07.49.12.64l2.11 1.65c-.04.32-.07.65-.07.98 0 .33.03.66.07.98l-2.11 1.65c-.19.15-.24.42-.12.64l2 3.46c.09.16.26.25.44.25.06 0 .12-.01.17-.03l2.49-1c.52.4 1.08.73 1.69.98l.38 2.65c.03.24.24.42.49.42h4c.25 0 .46-.18.49-.42l.38-2.65c.61-.25 1.17-.59 1.69-.98l2.49 1c.06.02.12.03.18.03.17 0 .34-.09.43-.25l2-3.46c.12-.22.07-.49-.12-.64l-2.11-1.65zm-1.98-1.71c.04.31.05.52.05.73 0 .21-.02.43-.05.73l-.14 1.13.89.7 1.08.84-.7 1.21-1.27-.51-1.04-.42-.9.68c-.43.32-.84.56-1.25.73l-1.06.43-.16 1.13-.2 1.35h-1.4l-.19-1.35-.16-1.13-1.06-.43c-.43-.18-.83-.41-1.23-.71l-.91-.7-1.06.43-1.27.51-.7-1.21 1.08-.84.89-.7-.14-1.13c-.03-.31-.05-.54-.05-.74s.02-.43.05-.73l.14-1.13-.89-.7-1.08-.84.7-1.21 1.27.51 1.04.42.9-.68c.43-.32.84-.56 1.25-.73l1.06-.43.16-1.13.2-1.35h1.39l.19 1.35.16 1.13 1.06.43c.43.18.83.41 1.23.71l.91.7 1.06-.43 1.27-.51.7 1.21-1.07.85-.89.7.14 1.13zM12 8c-2.21 0-4 1.79-4 4s1.79 4 4 4 4-1.79 4-4-1.79-4-4-4zm0 6c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2z");
		settingPath.fillProperty().bind(settings.textFillProperty());
		settingPath.setLayoutX(703);
		
		
		
		settings.setLayoutX(663);
		settings.setPrefWidth(110);
		
		ObservableList<Button> buttons = FXCollections.observableList(Arrays.asList(home, cash, upload, goals, settings));
		ObservableList<SVGPath> paths = FXCollections.observableList(Arrays.asList(homePath, cashPath, uploadPath, goalPath, settingPath));
		
		for (Button b : buttons)
		{
			b.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
			b.setPrefHeight(100);
			b.setLayoutY(20);
			b.setFont(new Font("Segoe UI", 14));
			b.setOpacity(.6);
			
			
		}
		
		
		for (SVGPath p : paths)
		{
			p.setLayoutY(24);
			p.setScaleY(1.6);
			p.setScaleX(1.6);
			p.setOpacity(.6);
		}
		
		backPane.addAll(paths);
		
		this.setBarButtonPaths(paths);
		
		
		return  buttons;
		
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
	
	/**
	 * Gets the SVG paths for the bar button decorations.
	 * 
	 * @return ObservableList<SVGPath> - Bar button decorations.
	 */
	public ObservableList<SVGPath> getBarButtonPaths()
	{
		return this.paths;
	}
	
	/**
	 * Gets the SVG paths for the bar button decorations.
	 * 
	 * @return ObservableList<SVGPath> - Bar button decorations.
	 */
	public void setBarButtonPaths(ObservableList<SVGPath> paths)
	{
		this.paths = paths;
	}
	
}
