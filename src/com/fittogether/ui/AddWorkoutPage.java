package com.fittogether.ui;

import com.fittogether.database.WorkoutDAO; // Import WorkoutDAO
import com.fittogether.model.User; // Import User model
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;  // Import ScrollPane

public class AddWorkoutPage {
    private WorkoutDAO workoutDAO; // Declare WorkoutDAO
    private User user; // Add User field

    // Constructor to accept User object
    public AddWorkoutPage(User user) {
        this.user = user; // Initialize the user
        this.workoutDAO = new WorkoutDAO(); // Initialize WorkoutDAO independently
    }

    // Method to set up the UI components and return the scene
    public Scene getScene(Stage primaryStage) {
        // Create a VBox layout with padding and spacing
        VBox layout = new VBox(15); // 15px spacing for better layout
        layout.setPadding(new Insets(20));

        // Title label styled according to the theme
        Label titleLabel = new Label("Add Workouts");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #FF5722; -fx-padding: 10px 0; -fx-alignment: center;"); // Styling with the theme

        // Label and TextField for exercise name
        Label exerciseLabel = new Label("Exercise Name:");
        exerciseLabel.setStyle("-fx-text-fill: #3ea808; -fx-font-size: 14px;"); // Changed font color to soft gray
        TextField exerciseField = new TextField();
        exerciseField.setStyle("-fx-background-color: #212121; -fx-text-fill: #ffffff; -fx-border-color: #FF5722; -fx-padding: 10px;");

        // Label and TextField for duration
        Label durationLabel = new Label("Duration (in minutes):");
        durationLabel.setStyle("-fx-text-fill: #3ea808; -fx-font-size: 14px;"); // Changed font color to soft gray
        TextField durationField = new TextField();
        durationField.setStyle("-fx-background-color: #212121; -fx-text-fill: #ffffff; -fx-border-color: #FF5722; -fx-padding: 10px;");

        // Label and TextField for calories
        Label calorieLabel = new Label("Calories Burned:");
        calorieLabel.setStyle("-fx-text-fill: #3ea808; -fx-font-size: 14px;"); // Changed font color to soft gray
        TextField calorieField = new TextField();
        calorieField.setStyle("-fx-background-color: #212121; -fx-text-fill: #ffffff; -fx-border-color: #FF5722; -fx-padding: 10px;");

        // Button to add workout, styled according to theme
        Button addButton = new Button("Add Workout");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #ffffff; -fx-font-size: 18px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        addButton.setMinWidth(200); // Make sure the button is wide enough for the text to fit
        addButton.setOnAction(e -> {
            String exerciseName = exerciseField.getText().trim(); // Get the exercise name
            int duration;
            int calories;

            // Validate and parse the duration
            try {
                duration = Integer.parseInt(durationField.getText().trim()); // Parse the duration
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid number for duration.");
                alert.showAndWait();
                return; // Exit the method if parsing fails
            }

            // Validate and parse the calories
            try {
                calories = Integer.parseInt(calorieField.getText().trim()); // Parse the calories
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid number for calories.");
                alert.showAndWait();
                return; // Exit the method if parsing fails
            }

            // Call the addWorkout method with the user ID, exercise name, duration, and calories
            if (workoutDAO.addWorkout(user.getId(), exerciseName, duration, calories)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Workout added successfully!");
                alert.showAndWait();
                primaryStage.setScene(new WorkoutPage(primaryStage, user).getScene()); // Navigate back to WorkoutPage
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to add workout!");
                alert.showAndWait();
            }
        });

        // Button to go back to WorkoutPage
        Button backButton = new Button("Back to Workout Page");
        backButton.setStyle("-fx-background-color: #FF5722; -fx-text-fill: #ffffff; -fx-font-size: 18px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        backButton.setMinWidth(200); // Set a minimum width to ensure the text is visible
        backButton.setOnAction(e -> {
            primaryStage.setScene(new WorkoutPage(primaryStage, user).getScene()); // Navigate back to WorkoutPage
        });

        // Add all components to the layout
        layout.getChildren().addAll(titleLabel, exerciseLabel, exerciseField, durationLabel, durationField, calorieLabel, calorieField, addButton, backButton);

        // Create a ScrollPane to make the layout scrollable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true); // Make the ScrollPane fit the width of the layout
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Always show vertical scrollbar

        // Create and return the scene with the ScrollPane as root
        Scene scene = new Scene(scrollPane, 400, 400); // Adjust height for UI components
        return scene; // Return the created scene
    }
}
