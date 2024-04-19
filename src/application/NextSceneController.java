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

public class NextSceneController {

	@FXML
	private Label userName; // Label to display the username
	@FXML
	private FlowPane setsFlowPane; // Container for sets
	@FXML
	private Button addButton; // Button for adding new sets
	private boolean sortByDate = true;  // Default sorting by date

	private static String name;

	// Sets the user name and updates the label
	public void setUser(String newName) {
		name = newName;
		userName.setText(name);
		loadUserSets();
	}
	
	@FXML
    private void handleSortByName() {
        sortByDate = false;
//        System.out.println("Name");
        setsFlowPane.getChildren().remove(1, setsFlowPane.getChildren().size());
        loadUserSets();
    }

    @FXML
    private void handleSortByDate() {
        sortByDate = true;
        setsFlowPane.getChildren().remove(1, setsFlowPane.getChildren().size());
        loadUserSets();
    }

	// Initializes the controller and setups up the add button
	public void initialize() {
		setupAddButtonMenu();
	}

	// Load and display sets with default sorting by date
	private void loadUserSets() {
        FlashCardList flashCardList = new FlashCardList(userName.getText());
		List<SetDetails> setDetails = flashCardList.getUserSets();
//        System.out.println("Debug - Sorting by Date: " + sortByDate);
        if (sortByDate) {
            setDetails.sort(Comparator.comparing(SetDetails::getTimestamp));
        } else {
            setDetails.sort(Comparator.comparing(SetDetails::getSetName));
        }

        System.out.println(setDetails.get(0));
        setDetails.forEach(details -> displaySet(details.getSetName()));
    }

	// Display a set visually in the setsFlowPane
	private void displaySet(String setName) {
//		System.out.println("Debug - Fired displaySet");
		Label setLabel = new Label(setName);
		setLabel.setMaxWidth(106);
		setLabel.setAlignment(Pos.CENTER);
		Rectangle setRect = new Rectangle(106, 105, Color.TRANSPARENT);
		setRect.setStroke(Color.BLACK);
		VBox setBox = new VBox(setRect, setLabel);
		setBox.setAlignment(Pos.CENTER);
		setsFlowPane.getChildren().add(setBox);
		setupSetInteractions(setBox, setLabel);
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

	// Method to setup interactions based on the control type
	private void setupSetInteractions(VBox setBox, Control control) {
		if (control instanceof TextField) {
			TextField setNameField = (TextField) control;
			setNameField.setOnAction(e -> convertToLabel(setNameField, setBox));
			setBox.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					openNewScene(setNameField.getText()); // Open the set
				} else if (event.getButton() == MouseButton.SECONDARY) {
					showContextMenu(setBox, setNameField, event.getScreenX(), event.getScreenY());
				}
			});
		} else if (control instanceof Label) {
			Label setNameLabel = (Label) control;
			setBox.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					openNewScene(setNameLabel.getText()); // Open the set
				} else if (event.getButton() == MouseButton.SECONDARY) {
					showContextMenu(setBox, setNameLabel, event.getScreenX(), event.getScreenY());
				}
			});
		}
	}

	// Converts a TextField to a Label after editing and adds the set name to CSV
	private void convertToLabel(TextField setNameField, VBox parentBox) {
		String text = setNameField.getText().trim();

		Label setLabel = new Label(text);
		setLabel.setMinSize(106, 20);
		setLabel.setAlignment(Pos.CENTER);
		parentBox.getChildren().set(1, setLabel);

		// Ensuring the set name is still unique when converting to label
		FlashCardList flashCardList = new FlashCardList(userName.getText());
		List<SetDetails> existingSets = flashCardList.getUserSets();
		final String[] effectiveName = {text};  // Use an array to hold the name for mutability
		int suffix = 1;

		// Check if set name already exists and adjust accordingly
	    while (existingSets.stream().anyMatch(set -> set.getSetName().equals(effectiveName[0]))) {
	        effectiveName[0] = text + " " + suffix++;  // Update the name using the array
	    }

	    setLabel.setText(effectiveName[0]);

	    flashCardList.addSet(effectiveName[0]);
	}

	// Method to display a context menu for a set box, compatible with Label and TextField
	private void showContextMenu(VBox setBox, Control control, double x, double y) {
		ContextMenu contextMenu = new ContextMenu();
		MenuItem openItem = new MenuItem("Open");
		openItem.setOnAction(e -> {
			String setName = (control instanceof Label) ? ((Label) control).getText() : ((TextField) control).getText();
			openNewScene(setName);
		});

		MenuItem renameItem = new MenuItem("Rename");
		renameItem.setOnAction(e -> renameSet(setBox, control));

		MenuItem deleteItem = new MenuItem("Delete");
		deleteItem.setOnAction(e -> {
			String setName = (control instanceof Label) ? ((Label) control).getText() : ((TextField) control).getText();
			deleteSet(setName, setBox);
		});

		contextMenu.getItems().addAll(openItem, renameItem, deleteItem);
		contextMenu.show(setBox, x, y);
	}

	private void renameSet(VBox setBox, Control control) {
		String currentName = (control instanceof Label) ? ((Label) control).getText() : ((TextField) control).getText();

		TextField setNameField = new TextField(currentName);
		setNameField.setMaxWidth(106);
		setBox.getChildren().set(1, setNameField);
		setNameField.requestFocus();

		setNameField.setOnAction(e -> finalizeSetName(setNameField, setBox, currentName));
	}

	// Method to handle finalization of set naming
	private void finalizeSetName(TextField setNameField, VBox setBox, String oldName) {
	    String inputName = setNameField.getText().trim();
	    if (!inputName.isEmpty() && !inputName.equals(oldName)) {
	        FlashCardList flashCardList = new FlashCardList(userName.getText());
	        List<SetDetails> existingSets = flashCardList.getUserSets();
	        final String[] newName = {inputName};  // Use an array to allow modifications
	        int suffix = 1;

	        // Check for duplicates and adjust name if necessary
	        while (existingSets.stream().anyMatch(set -> set.getSetName().equals(newName[0]))) {
	            newName[0] = inputName + " " + suffix++;  // Update the name using the array
	        }

	        Label newLabel = new Label(newName[0]);
	        newLabel.setMaxWidth(106);
	        setBox.getChildren().set(1, newLabel);

	        // Rename the set in the FlashCardList
	        flashCardList.renameSet(oldName, newName[0]);

	        // Refresh the UI to reflect changes
	        setsFlowPane.getChildren().remove(1, setsFlowPane.getChildren().size());
	        loadUserSets();
	    } else {
	        // If no change in name or name is empty, revert to old name
	        setBox.getChildren().set(1, new Label(oldName));
	    }
	}

	// Method to handle the deletion of a set
	private void deleteSet(String setName, VBox setBox) {
		FlashCardList flashCardList = new FlashCardList(userName.getText());
		boolean success = flashCardList.deleteSet(setName);
		if (success) {
			setsFlowPane.getChildren().remove(setBox); // Remove from UI only if deletion is successful
			System.out.println("Set deleted successfully.");
		} else {
			System.out.println("Failed to delete set.");
		}
	}

	private void openNewScene(String setName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Flashcard.fxml"));
			Parent root = loader.load();

			FlashcardController flashcardController = loader.getController();
			flashcardController.setUser(userName.getText());
			flashcardController.initializeFlashcards(userName.getText(), setName); // Pass username and set name to
																					// FlashcardController

			Scene scene = new Scene(root);
			Stage stage = (Stage) setsFlowPane.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	// Method to handle logout and navigate back to Main.fxml
	public void logout() {
		try {
			// Load Main.fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
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
