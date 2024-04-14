package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    public void handleSignUpAction(ActionEvent event) {
        try {
            // Load the Home scene FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent nextSceneRoot = loader.load();

            // Retrieve the controller associated with the Home.fxml file
            NextSceneController nextSceneController = loader.getController();
            // Pass the username entered in the TextField to the NextSceneController
            nextSceneController.setName(usernameTextField.getText());

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
}
