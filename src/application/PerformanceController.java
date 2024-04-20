package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class PerformanceController {

	@FXML
	private Label userName; // Label to display the username
	@FXML
	private Label totalSets;
	@FXML
	private Label totalCards;
	@FXML
	private Label totalBookmarks;
	@FXML
	private FlowPane setsFlowPane;

	private boolean sortByDate = true; // Default sorting by date

	private static String name;
	
	public void setLabel() {
		FlashCardList fclist = new FlashCardList(name);
		totalSets.setText("Total Sets: "+Integer.toString(fclist.getUserSets().size()));
		
		totalCards.setText("Total Cards: "+ Integer.toString(fclist.getAllFlashCards().size()));
		
		totalBookmarks.setText("Total Bookmarks: "+ Integer.toString(fclist.getAllBookmarkCards().size()));
		
	}

	// Sets the user name and updates the label
	public void setUser(String newName) {
		System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>> " + newName);
		userName.setText(newName);
		name = newName;
		
	}

	@FXML
	private void openBookmark() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Flashcard.fxml"));
			Parent root = loader.load();

			FlashcardController flashcardController = loader.getController();
			flashcardController.initializeFlashcards(userName.getText(), "", true); // Pass username and set name to
																					// FlashcardController

			Scene scene = new Scene(root);
			Stage stage = (Stage) setsFlowPane.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleSets() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
			Parent root = loader.load();

			NextSceneController nextSceneController = loader.getController();
			nextSceneController.setUser(name);; // Pass username and set name to
																					// FlashcardController

			Scene scene = new Scene(root);
			Stage stage = (Stage) setsFlowPane.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to handle logout and navigate back to Main.fxml
	public void logout() {
		try {
			// Load Main.fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
			Parent root = loader.load();

			// Optionally, you can reset the username here or ensure it's handled
			// appropriately in MainController
			MainController mainController = loader.getController();
			mainController.resetUsername(); // You need to implement this method in MainController

			// Get the stage from an existing component
			Stage stage = (Stage) setsFlowPane.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to log out and return to the main scene.");
		}
	}

}
