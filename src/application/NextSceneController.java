//package application;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//
//import java.util.Arrays;
//
//public class NextSceneController {
//
//    @FXML
//    private Label nameLabel; // Label to display the name
//    @FXML
//    private FlowPane setsFlowPane;
//    @FXML
//    private Button addButton; // The '+' button for adding new sets
//
//    private static String name; // Static variable to hold the name
//
//    // Method to set the name from the previous scene
//    public static void setName(String newName) {
//        name = newName;
//    }
//
//    // Method called by FXMLLoader after the initialization of the controller
//    public void initialize() {
//        // Set the text of the nameLabel to the name passed from the previous scene
//        nameLabel.setText(name);
//    }
//
//    @FXML
//    private void handleAddSet(ActionEvent event) {
//        Rectangle newSetRect = new Rectangle(106, 105);
//        newSetRect.setFill(Color.TRANSPARENT);
//        newSetRect.setStroke(Color.BLACK);
//        
//        TextField setName = new TextField();
//	    setName.setPromptText("Enter set name");
//	    setName.setOnAction(e -> saveSetName(setName.getText()));
//	    
//	    VBox setBox = new VBox(newSetRect, setName);
//	    setBox.setAlignment(Pos.CENTER);
//	    
//	    setsFlowPane.getChildren().add(setBox);
//    }
//
//    private void saveSetName(String name) {
//        // Logic to save the set name
//        // Update UI if necessary
//    }
//
//}

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Arrays;

public class NextSceneController {

    @FXML
    private Label nameLabel; // Label to display the name
    @FXML
    private FlowPane setsFlowPane;
    @FXML
    private Button addButton; // The '+' button for adding new sets

    private static String name; // Static variable to hold the name

    // Method to set the name from the previous scene
    public static void setName(String newName) {
        name = newName;
    }

    // Method called by FXMLLoader after the initialization of the controller
    public void initialize() {
        nameLabel.setText(name); // Set the text of the nameLabel

        // Create the context menu and menu items
        ContextMenu addMenu = new ContextMenu();
        
        MenuItem createFlashCardsItem = new MenuItem("Create FlashCards");
        createFlashCardsItem.setOnAction(e -> handleAddSet());

        MenuItem importFromCSVItem = new MenuItem("Import from CSV");
        importFromCSVItem.setOnAction(e -> handleImportCSV());

        addMenu.getItems().addAll(createFlashCardsItem, importFromCSVItem);

        // Set the context menu on the '+' button
        addButton.setOnMouseClicked(e -> {
            if (e.getButton().toString().equals("PRIMARY")) {
                addMenu.show(addButton, e.getScreenX(), e.getScreenY());
            }
        });
    }

    @FXML
    private void handleAddSet() {
        Rectangle newSetRect = new Rectangle(106, 105);
        newSetRect.setFill(Color.TRANSPARENT);
        newSetRect.setStroke(Color.BLACK);

        TextField setName = new TextField();
        setName.setPromptText("Enter set name");
        setName.setOnAction(e -> saveSetName(setName.getText()));

        VBox setBox = new VBox(newSetRect, setName);
        setBox.setAlignment(Pos.CENTER);

        setsFlowPane.getChildren().add(1, setBox); // Add the set box at the first position after the button
    }

    private void handleImportCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        
        File csvFile = fileChooser.showOpenDialog(null);
        if (csvFile != null) {
            // Logic to handle the CSV file
        }
    }

    private void saveSetName(String name) {
        // Logic to save the set name
        // Update UI if necessary
    }
}
