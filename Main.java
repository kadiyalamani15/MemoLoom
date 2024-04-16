package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Main class for the JavaFX application, extends javafx.application.Application.
 */
public class Main extends Application {

    /**
     * The main entry point for all JavaFX applications. The start method is called
     * after the init method has returned, and after the system is ready for the
     * application to begin running.
     * 
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the main FXML layout from the 'main.fxml' file.
            Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
            // Create a scene with the loaded layout.
            Scene scene = new Scene(root);
            
            // Optional: Set the title of the window (stage).
            // primaryStage.setTitle("MySceneBuilderApp");

            // Set the scene on the primary stage and display it.
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            // Print the stack trace to the console if an exception occurs.
            e.printStackTrace();
        }
    }

    /**
     * Main method to launch the application. This method is not needed for JavaFX
     * applications when the application is packaged and deployed, but it is
     * useful for IDEs with limited FX support.
     * 
     * @param args command line arguments passed to the application.
     *             An array of strings which the Java runtime passes to the application.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application by calling launch() method.
        launch(args);
    }
}
