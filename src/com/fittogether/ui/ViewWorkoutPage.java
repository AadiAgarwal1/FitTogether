package com.fittogether.ui;

import com.fittogether.database.WorkoutDAO;
import com.fittogether.model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;  // Import ScrollPane
import javafx.scene.control.Label;

import java.util.List;

public class ViewWorkoutPage {
    private User user;
    private Stage primaryStage;
    private WorkoutDAO workoutDAO;

    // Constructor
    public ViewWorkoutPage(User user, Stage primaryStage) {
        this.user = user;
        this.primaryStage = primaryStage;
        this.workoutDAO = new WorkoutDAO();  // Initialize WorkoutDAO for database operations
    }

    // Method to set up the scene
    public Scene getScene() {
        // Create the layout with padding and spacing
        VBox layout = new VBox(20); // 20px spacing for a more spacious layout
        layout.setPadding(new Insets(30)); // Padding around the layout

        // Title label styled according to the theme
        Label titleLabel = new Label("View Workouts");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #FF5722; -fx-padding: 10px 0; -fx-alignment: center;"); // Styling with the theme

        // Create a ListView to display the list of workouts with details
        ListView<String> workoutListView = new ListView<>();
        workoutListView.setStyle("-fx-background-color: #212121; -fx-text-fill: #ffffff; -fx-border-color: #FF5722;"); // Dark background with light text

        // Highlight the selected item for visibility
        workoutListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);
        workoutListView.setStyle("-fx-selection-bar: #3ea808;");  // Green selection background

        // Fetch workouts from the database using WorkoutDAO
        List<String> workoutDetails = workoutDAO.getAllWorkoutsForUser(user.getId());
        workoutListView.getItems().addAll(workoutDetails);  // Populate the list with workout details

        // Button to go back to the previous page (WorkoutPage)
        Button backButton = new Button("Back to Workouts");
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #ffffff; -fx-font-size: 18px; -fx-padding: 10px 20px; -fx-border-radius: 5px;"); // Large button with padding
        backButton.setMinWidth(200); // Set a minimum width to ensure the text is visible

        // Back button functionality (to return to the WorkoutPage)
        backButton.setOnAction(e -> {
            WorkoutPage workoutPage = new WorkoutPage(primaryStage, user);  // Pass primaryStage and user to WorkoutPage
            primaryStage.setScene(workoutPage.getScene()); // Navigate back to WorkoutPage
        });

        // Add components to layout
        layout.getChildren().addAll(titleLabel, workoutListView, backButton);

        // Create a ScrollPane to make the layout scrollable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true); // Ensure it fits the width of the scroll pane
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Always show the vertical scrollbar

        // Create the scene and apply styling from the CSS
        Scene scene = new Scene(scrollPane, 500, 400); // Adjust the size for better usability
        scene.getStylesheets().add(getClass().getResource("/resources/workout.css").toExternalForm()); // Link to workout.css

        return scene; // Return the created scene
    }
}
