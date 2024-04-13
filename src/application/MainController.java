package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	@FXML
    private TextField nameTextField; // Make sure this matches the fx:id of your TextField in the FXML

    public void handleSignUpAction(ActionEvent event) {
        try {
        	// Code to change scene and pass the value goes here
            String name = nameTextField.getText();
            System.out.println("Name: " + name); // For demonstration, replace this with actual scene change code
            // Implement scene change logic here, see below for details
            
            // Pass the name to the next scene's controller
            NextSceneController.setName(nameTextField.getText());

            // Load the next scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent nextSceneRoot = loader.load();
            Scene nextScene = new Scene(nextSceneRoot);

            // Get the current stage and set the new scene
            Stage primaryStage = (Stage) nameTextField.getScene().getWindow();
            primaryStage.setScene(nextScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
