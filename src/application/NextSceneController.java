//package application;
//
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.control.ContextMenu;
//import javafx.scene.control.Label;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.TextField;
//import javafx.scene.input.MouseButton;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.FileChooser;
//
//import java.io.File;
//import java.util.Arrays;
//
//public class NextSceneController {
//
//    @FXML
//    private Label nameLabel;
//    @FXML
//    private FlowPane setsFlowPane;
//    @FXML
//    private Button addButton;
//
//    private static String name;
//    private int untitledCount = 1;
//
//    public static void setName(String newName) {
//        name = newName;
//    }
//
//    public void initialize() {
//        nameLabel.setText(name);
//        setupAddButtonMenu();
//    }
//
//    private void setupAddButtonMenu() {
//        ContextMenu addMenu = new ContextMenu();
//        MenuItem createFlashCardsItem = new MenuItem("Create FlashCards");
//        createFlashCardsItem.setOnAction(e -> handleAddSet());
//
//        MenuItem importFromCSVItem = new MenuItem("Import from CSV");
//        importFromCSVItem.setOnAction(e -> handleImportCSV());
//
//        addMenu.getItems().addAll(createFlashCardsItem, importFromCSVItem);
//        addButton.setOnMouseClicked(e -> addMenu.show(addButton, e.getScreenX(), e.getScreenY()));
//    }
//
//    private void handleAddSet() {
//        createSet("");
//    }
//
//    private void createSet(String setName) {
//        Rectangle newSetRect = new Rectangle(106, 105);
//        newSetRect.setFill(Color.TRANSPARENT);
//        newSetRect.setStroke(Color.BLACK);
//
//        TextField setNameField = new TextField();
//        setNameField.setMaxWidth(106);
//        setNameField.setPromptText("Enter set name");
//
//        setNameField.setOnAction(e -> convertToLabel(setNameField));
//
//        setNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
//            if (!newVal) { // Focus lost
//                convertToLabel(setNameField);
//            }
//        });
//
//        VBox setBox = new VBox(newSetRect, setNameField);
//        setBox.setAlignment(Pos.CENTER);
//        FlowPane.setMargin(setBox, new Insets(12));
//
//        setsFlowPane.getChildren().add(setBox); // Add at the end
//
//        if (!setName.isEmpty()) {
//            setNameField.setText(setName);
//        }
//        
//     // Setting up interactions
//        setBox.setOnMouseClicked(event -> {
//            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
//                openNewScene(); // Opens a new scene when double-clicked
//            }
//        });
//
//        setBox.setOnContextMenuRequested(event -> {
//            ContextMenu contextMenu = new ContextMenu();
//            MenuItem openItem = new MenuItem("Open");
//            openItem.setOnAction(e -> openNewScene());
//
//            MenuItem renameItem = new MenuItem("Rename");
//            renameItem.setOnAction(e -> renameSet(setNameField));
//
//            MenuItem deleteItem = new MenuItem("Delete");
//            deleteItem.setOnAction(e -> setsFlowPane.getChildren().remove(setBox));
//
//            contextMenu.getItems().addAll(openItem, renameItem, deleteItem);
//            contextMenu.show(setBox, event.getScreenX(), event.getScreenY());
//        });
//
//        Platform.runLater(setNameField::requestFocus);
//    }
//    
//    private void renameSet(TextField setNameField) {
//        setNameField.setEditable(true);
//        setNameField.requestFocus();
//        setNameField.selectAll();
//        setNameField.setOnAction(e -> {
//            setNameField.setEditable(false);
//            convertToLabel(setNameField);
//        });
//    }
//    
//    private void openNewScene() {
//        // Placeholder for opening a new scene
//        System.out.println("Open New Scene");
//    }
//
//    private void convertToLabel(TextField setNameField) {
//        String text = setNameField.getText().trim();
//        if (text.isEmpty()) {
//            text = "Untitled" + (untitledCount > 1 ? " " + untitledCount : "");
//            untitledCount++;
//        }
//
//        Label setLabel = new Label(text);
//        setLabel.setMinSize(106, 20);
//        setLabel.setAlignment(Pos.CENTER);
//
//        VBox parentBox = (VBox) setNameField.getParent();
//        parentBox.getChildren().set(1, setLabel);
//    }
//
//    private void handleImportCSV() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open CSV File");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
//        File csvFile = fileChooser.showOpenDialog(null);
//        if (csvFile != null) {
//            // Handle CSV file
//        }
//    }
//}
//
//

//package application;
//
//import java.io.File;
//
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.ContextMenu;
//import javafx.scene.control.Label;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.FileChooser;
//
//public class NextSceneController {
//
//    @FXML
//    private Label nameLabel;
//    @FXML
//    private FlowPane setsFlowPane;
//    @FXML
//    private Button addButton;
//
//    private static String name;
//    private int untitledCount = 1;
//
//    public static void setName(String newName) {
//        name = newName;
//    }
//
//    public void initialize() {
//        nameLabel.setText(name);
//        setupAddButtonMenu();
//    }
//
//    private void setupAddButtonMenu() {
//        ContextMenu addMenu = new ContextMenu();
//        MenuItem createFlashCardsItem = new MenuItem("Create FlashCards");
//        createFlashCardsItem.setOnAction(e -> handleAddSet());
//
//        MenuItem importFromCSVItem = new MenuItem("Import from CSV");
//        importFromCSVItem.setOnAction(e -> handleImportCSV());
//
//        addMenu.getItems().addAll(createFlashCardsItem, importFromCSVItem);
//        addButton.setOnMouseClicked(e -> addMenu.show(addButton, e.getScreenX(), e.getScreenY()));
//    }
//
//    private void handleAddSet() {
//        createSet("");
//    }
//
//    private void createSet(String setName) {
//        Rectangle newSetRect = new Rectangle(106, 105);
//        newSetRect.setFill(Color.TRANSPARENT);
//        newSetRect.setStroke(Color.BLACK);
//
//        TextField setNameField = new TextField();
//        setNameField.setMaxWidth(106);
//        setNameField.setPromptText("Enter set name");
//        setNameField.setText(setName);
//        setupTextFieldEvents(setNameField);
//
//        VBox setBox = new VBox(newSetRect, setNameField);
//        setBox.setAlignment(Pos.CENTER);
//        FlowPane.setMargin(setBox, new Insets(12));
//
//        setsFlowPane.getChildren().add(setBox); // Add at the end
//
//        setBox.setOnMouseClicked(event -> {
//            if (event.getClickCount() == 2) {
//                openNewScene();
//            }
//        });
//
//        setBox.setOnContextMenuRequested(event -> {
//            ContextMenu contextMenu = new ContextMenu();
//            MenuItem openItem = new MenuItem("Open");
//            openItem.setOnAction(e -> openNewScene());
//
//            MenuItem renameItem = new MenuItem("Rename");
//            renameItem.setOnAction(e -> renameSet(setNameField));
//
//            MenuItem deleteItem = new MenuItem("Delete");
//            deleteItem.setOnAction(e -> setsFlowPane.getChildren().remove(setBox));
//
//            contextMenu.getItems().addAll(openItem, renameItem, deleteItem);
//            contextMenu.show(setBox, event.getScreenX(), event.getScreenY());
//        });
//
//        Platform.runLater(setNameField::requestFocus);
//    }
//
//    private void renameSet(TextField setNameField) {
//        if (!setNameField.isEditable()) {
//            setNameField.setEditable(true);
//            setNameField.requestFocus();
//            setNameField.selectAll();
//        }
//    }
//
//    private void setupTextFieldEvents(TextField setNameField) {
//        setNameField.setOnAction(e -> convertToLabel(setNameField));
//        setNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
//            if (!newVal) { // Focus lost
//                convertToLabel(setNameField);
//            }
//        });
//    }
//
//    private void convertToLabel(TextField setNameField) {
//        if (!setNameField.isEditable()) {
//            return;
//        }
//        String text = setNameField.getText().trim();
//        if (text.isEmpty()) {
//            text = "Untitled" + (untitledCount > 1 ? " " + untitledCount : "");
//            untitledCount++;
//        }
//        Label setLabel = new Label(text);
//        setLabel.setMinSize(106, 20);
//        setLabel.setAlignment(Pos.CENTER);
//        VBox parentBox = (VBox) setNameField.getParent();
//        parentBox.getChildren().set(1, setLabel);
//        setNameField.setEditable(false);
//    }
//
//    private void openNewScene() {
//        System.out.println("Open New Scene");
//    }
//
//    private void handleImportCSV() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open CSV File");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
//        File csvFile = fileChooser.showOpenDialog(null);
//        if (csvFile != null) {
//            System.out.println("CSV File selected: " + csvFile.getAbsolutePath());
//        }
//    }
//}

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
    private Label nameLabel;
    @FXML
    private FlowPane setsFlowPane;
    @FXML
    private Button addButton;

    private static String name;
    private int untitledCount = 1;

    public static void setName(String newName) {
        name = newName;
    }

    public void initialize() {
        nameLabel.setText(name);
        setupAddButtonMenu();
    }

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

    private void handleAddSet() {
        createSet("");
    }

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

    private void setupSetInteractions(VBox setBox, TextField setNameField) {
        setNameField.setOnAction(e -> convertToLabel(setNameField, setBox));

        setNameField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // Focus lost
                convertToLabel(setNameField, setBox);
            }
        });

        setBox.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                openNewScene(); // Double click to open
            } else if (event.getButton() == MouseButton.SECONDARY) {
                showContextMenu(setBox, setNameField, event.getScreenX(), event.getScreenY());
            }
        });
    }

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

    private void openNewScene() {
        // Placeholder for opening a new scene
        System.out.println("Open New Scene");
    }

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




