package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import application.FlashCardList;

import java.io.IOException;
import java.util.ArrayList;

import application.FlashCard;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class FlashcardController {

	@FXML
	private TextArea questionTextArea;
	@FXML
	private Label answerLabel;
	@FXML
	private Label setLabel;
	@FXML
	private Button nextButton;
	@FXML
	private Button prevButton;
	@FXML
	private CheckBox statusCheckBox;

	private FlashCardList flashCardList;
	private ArrayList<FlashCard> cards;
	private int currentIndex = 0;

	public void initializeFlashcards(String user, String setName) {
		flashCardList = new FlashCardList(user);
		flashCardList.loadFlashCards(setName); // Make sure this method is adjusted to accept setName
		cards = new ArrayList<>(flashCardList.getAllCards()); // Ensure getAllCards exists and works
		if (!cards.isEmpty()) {
			displayCard(0);
		}
	}
	
	public void initializeFlashcards(String user, String setName, Boolean bookmark) {
		flashCardList = new FlashCardList(user);
		flashCardList.loadFlashCards(setName); // Make sure this method is adjusted to accept setName
		cards = new ArrayList<>(flashCardList.getAllBookmarkCards()); // Ensure getAllCards exists and works
		if (!cards.isEmpty()) {
			displayCard(0);
		}
	}

	private void displayCard(int index) {
		if (index < 0 || index >= cards.size())
			return;
		FlashCard card = cards.get(index);
		setLabel.setText(card.getSetName());
		questionTextArea.setText(card.getQuestion());
		answerLabel.setText(card.getAnswer()); // Set the answer keyword
		currentIndex = index;
		
		
		if (card.getBookmark().equals("True")) {
			statusCheckBox.setSelected(true);
			System.out.println("True "+ card.getBookmark());
		}
		else {
			statusCheckBox.setSelected(false);
			System.out.println("False "+ card.getBookmark());
		}
	}

	@FXML
	private void handleNextAction() {
		if (currentIndex + 1 < cards.size()) {
			displayCard(currentIndex + 1);
		}
	}

	@FXML
	private void handlePrevAction() {
		if (currentIndex - 1 >= 0) {
			displayCard(currentIndex - 1);
		}
	}

	@FXML
	private void closeWindow() {
		try {
			// Load Home.fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
			Parent root = loader.load();

			// Retrieve the controller and set the user name
			NextSceneController nextSceneController = loader.getController();
			// Assuming 'userName' is a static field or there's a way to retrieve it
			nextSceneController.setUser(UserManager.getInstance().getUserName());

			// Get the stage from an existing component
			Stage stage = (Stage) questionTextArea.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleBookmark() {

		String flag;

		if (statusCheckBox.isSelected()) {
			flag = "True";
		} else {
			flag = "False";
		}
		flashCardList.updateBookmark(setLabel.getText(), questionTextArea.getText(), flag);
	}
}
