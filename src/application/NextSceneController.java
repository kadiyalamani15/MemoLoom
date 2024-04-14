package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;

public class NextSceneController {

    @FXML
    private Label nameLabel; // Label to display the username
    @FXML
    private FlowPane setsFlowPane; // Container for sets
    @FXML
    private Button addButton; // Button for adding new sets

    private static String name;
    private int untitledCount = 1; // Counter for default set names

    // Sets the user name and updates the label
    public void setName(String newName) {
        name = newName;
        nameLabel.setText(name);
    }

    // Initializes the controller and setups up the add button
    public void initialize() {
        nameLabel.setText(name);
        setupAddButtonMenu();
    }

    // Sets up the context menu for the add button with options
    private void setupAddButtonMenu() {
        ContextMenu addMenu = new ContextMenu();
        MenuItem createFlashCardsItem = new MenuItem("Create FlashCards");
        createFlashCardsItem.setOnAction(e -> handleAddSet());

        MenuItem importFromCSVItem = new MenuItem("Import from CSV");
        importFromCSVItem.setOnAction(e -> handleImportCSV());

        addMenu.getItems().addAll(createFlashCardsItem, importFromCSVItem);
        addButton.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                addMenu.show(addButton, e.getScreenX(), e.getScreenY());
            }
        });
    }

    // Handles the creation of a new set
    private void handleAddSet() {
        createSet("");
    }

    // Creates a visual representation of a set
    private void createSet(String setName) {
        Rectangle newSetRect = new Rectangle(106, 105);
        newSetRect.setFill(Color.TRANSPARENT);
        newSetRect.setStroke(Color.BLACK);

        TextField setNameField = new TextField();
        setNameField.setMaxWidth(106);
        setNameField.setPromptText("Enter set name");

        VBox setBox = new VBox(newSetRect, setNameField);
        setBox.setAlignment(Pos.CENTER);
        FlowPane.setMargin(setBox, new Insets(12));

        setsFlowPane.getChildren().add(setBox);

        setupSetInteractions(setBox, setNameField);

        Platform.runLater(setNameField::requestFocus);
    }

    // Sets up interactions for a set box
    private void setupSetInteractions(VBox setBox, TextField setNameField) {
        setNameField.setOnAction(e -> convertToLabel(setNameField, setBox));

        setNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                convertToLabel(setNameField, setBox);
            }
        });

        setBox.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                openNewScene(); // Open the set
            } else if (event.getButton() == MouseButton.SECONDARY) {
                showContextMenu(setBox, setNameField, event.getScreenX(), event.getScreenY());
            }
        });
    }

    // Converts a TextField to a Label after editing
    private void convertToLabel(TextField setNameField, VBox parentBox) {
        String text = setNameField.getText().trim();
        if (text.isEmpty()) {
            text = "Untitled" + (untitledCount > 1 ? " " + untitledCount : "");
            untitledCount++;
        }

        Label setLabel = new Label(text);
        setLabel.setMinSize(106, 20);
        setLabel.setAlignment(Pos.CENTER);
        parentBox.getChildren().set(1, setLabel);
    }

    // Displays a context menu for a set box
    private void showContextMenu(VBox setBox, TextField setNameField, double x, double y) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(e -> openNewScene());

        MenuItem renameItem = new MenuItem("Rename");
        renameItem.setOnAction(e -> renameSet(setBox));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> setsFlowPane.getChildren().remove(setBox));

        contextMenu.getItems().addAll(openItem, renameItem, deleteItem);
        contextMenu.show(setBox, x, y);
    }

    // Allows renaming of a set
    private void renameSet(VBox setBox) {
        TextField setNameField = new TextField(((Label) setBox.getChildren().get(1)).getText());
        setNameField.setMaxWidth(106);
        setNameField.setOnAction(e -> convertToLabel(setNameField, setBox));
        setNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) convertToLabel(setNameField, setBox);
        });

        setBox.getChildren().set(1, setNameField);
        setNameField.requestFocus();
    }

    // Placeholder method to open a new scene
    private void openNewScene() {
        System.out.println("Open New Scene");
    }

    // Handles the import of sets from a CSV file
    private void handleImportCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File csvFile = fileChooser.showOpenDialog(null);
        if (csvFile != null) {
            // Handle CSV file
        }
    }
}
