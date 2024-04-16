package application;
import application.FlashCardList;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class SearchController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> searchResults; // Or use ListView<FlashCard> for a custom list cell

    private FlashCardList flashCardList; // Your flashcard list manager

    @FXML
    public void initialize() {
        flashCardList = new FlashCardList(); // Initialize or get your flashcard list

        // Add listener for search field changes
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchFlashCards(newValue);
        });
    }
    

    @FXML
    private void onSearchClicked() {
        String searchText = searchField.getText();
        List<FlashCard> foundFlashCards = flashCardList.searchByQuestion(searchText);
        ObservableList<String> foundQuestions = FXCollections.observableArrayList(
                foundFlashCards.stream().map(FlashCard::getQuestion).collect(Collectors.toList()));
        searchResults.setItems(foundQuestions);
    }
    
    // Set the flashCardList (maybe passed in from the main controller)
    public void setFlashCardList(FlashCardList flashCardList) {
        this.flashCardList = flashCardList;
    }


    private void searchFlashCards(String searchText) {
        // Assuming the search method returns a List<FlashCard>
        List<FlashCard> searchResult = ((FlashCardList) flashCardList).search(searchText);
        
        // Convert the list to an ObservableList<String>
        ObservableList<String> displayItems = FXCollections.observableArrayList(
            searchResult.stream().map(FlashCard::toString).collect(Collectors.toList())
        );
        
        // Now you can safely set this ObservableList on your ListView
        searchResults.setItems(displayItems);
    }
}