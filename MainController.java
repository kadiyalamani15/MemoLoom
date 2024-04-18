package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private TextField usernameTextField; // TextField for user input, annotated with @FXML to bind with FXML UI element.

    /**
     * Handles the action triggered by signing up. This method is typically bound to a sign-up button in FXML.
     * 
     * @param event The event that triggered this action, usually from a button press.
     */
    @FXML
    private TextField searchField; // TextField for entering search terms

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim(); // Retrieves and trims the text from the TextField
        if (searchTerm.isEmpty()) {
            showAlert("Please enter a search term.");
        } else {
            performSearch(searchTerm);
        }
    }

    private void performSearch(String searchTerm) {
        // Implement search logic here
        System.out.println("Searching for: " + searchTerm);
        // Placeholder for search results handling
        showAlert("Search results for: '" + searchTerm + "' displayed here.");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleSignUpAction(ActionEvent event) {
        try {
        	// Set the user name in singleton
        	UserManager.getInstance().setUserName(usernameTextField.getText()); 
        	
            // Load the Home scene FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent nextSceneRoot = loader.load();

            // Retrieve the controller associated with the Home.fxml file
            NextSceneController nextSceneController = loader.getController();
            // Pass the username entered in the TextField to the NextSceneController
            nextSceneController.setUser(usernameTextField.getText());

            // Create a new scene with the loaded FXML root
            Scene nextScene = new Scene(nextSceneRoot);
            // Get the current stage from the event source, which allows us to change the scene
            Stage primaryStage = (Stage) usernameTextField.getScene().getWindow();
            primaryStage.setScene(nextScene); // Set the new scene to the stage
        } catch (IOException e) {
            // Handle possible IOException from loading the FXML
            e.printStackTrace();
        }
    }
    
 // Resets the username text field or any other necessary UI components
    public void resetUsername() {
        usernameTextField.setText(""); // Clear the text field
    }
}
