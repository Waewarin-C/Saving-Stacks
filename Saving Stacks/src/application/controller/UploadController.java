package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.UploadManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class UploadController implements EventHandler<ActionEvent>, Initializable{

	@FXML private AnchorPane uploadAnchor;
	
	@FXML private ChoiceBox<String> choice1, choice2, choice3;
	
	@FXML private Label csvPrompt, warning, uploadPrompt;
	
	@FXML private Button fileButton;
	
	private String token1, token2, token3;
	
	private String format;
	
	private static final String BACKGROUND_COLOR_STYLE = "-fx-background-color: #33333d";
	private static final String controllerID = "UPLOAD";
	
	
	@Override
	public void handle(ActionEvent arg0) {
		
		
		if (token1 == null || token2 == null || token3 == null)
		{
			return;
		}
		
		
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));
		fc.setTitle("Select File to Upload.");
		UploadManager.readFile(fc.showOpenDialog(new Stage()));
	}

	
	public void set(ActionEvent arg0) {
	
		
		fileButton.setDisable(false);
		
		
		token1 = choice1.getValue();
		token2 = choice2.getValue();
		token3 = choice3.getValue();
		
		
		csvPrompt.setText(String.format("%s %s,%s,%s", "Specified csv format: ", token1.toLowerCase(), token2.toLowerCase(), token3.toLowerCase()));
		csvPrompt.setVisible(true);
		
		//Set format
		format = token1.toLowerCase() + "," + token2.toLowerCase() + "," + token3.toLowerCase();
		UploadManager.setFormat(format);
		
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if (Main.settings.getBooleanValueWithProperty("is_dark_mode_enabled"))
		{
			uploadAnchor.setStyle(BACKGROUND_COLOR_STYLE);
			
			uploadPrompt.setStyle("-fx-text-fill: white");
			csvPrompt.textFillProperty().bind(uploadPrompt.textFillProperty());
			warning.textFillProperty().bind(uploadPrompt.textFillProperty());
			
			choice1.setStyle("");
			choice1.getStylesheets().add(getClass().getResource("../view/choice_dark.css").toExternalForm());
			
			
			
			choice2.setStyle("");
			choice2.getStylesheets().add(getClass().getResource("../view/choice_dark.css").toExternalForm());
			
			choice3.setStyle("");
			choice3.getStylesheets().add(getClass().getResource("../view/choice_dark.css").toExternalForm());
		}

		csvPrompt.setVisible(false);
		fileButton.setDisable(true);
		
		BottomBarController.attachBottomBar(uploadAnchor.getChildren(), controllerID);
		
		choice1.getItems().add("Date");
		choice1.getItems().add("Amount");
		choice1.getItems().add("Title");
		
		choice2.getItems().add("Date");
		choice2.getItems().add("Amount");
		choice2.getItems().add("Title");
		
		choice3.getItems().add("Date");
		choice3.getItems().add("Amount");
		choice3.getItems().add("Title");
		
		
	}
	
	
}
