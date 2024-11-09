package com.fittogether.ui;

import com.fittogether.database.WorkoutDAO; // Import WorkoutDAO
import com.fittogether.model.User; // Import User model
import com.fittogether.model.Workout; // Import Workout model
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class EditWorkoutPage {
    private WorkoutDAO workoutDAO; // Declare WorkoutDAO
    private User user; // Add User field

    // Constructor to accept User object
    public EditWorkoutPage(User user) {
        this.user = user; // Initialize the user
        this.workoutDAO = new WorkoutDAO(); // Initialize WorkoutDAO
    }

    // Method to set up the UI components and return the scene
    public Scene getScene(Stage primaryStage) {
        VBox layout = new VBox(20); // Increased spacing for clarity
        layout.setPadding(new Insets(20));

        // Title label with dark theme
        Label titleLabel = new Label("Edit Workouts");
        titleLabel.getStyleClass().add("title");

        // Label and ComboBox for selecting workout
        Label selectWorkoutLabel = new Label("Select Workout:");
        selectWorkoutLabel.setStyle("-fx-text-fill: green;"); // Set label color to white
        ComboBox<String> workoutComboBox = new ComboBox<>();

        // Fetch all workout names for the user and add them to the ComboBox
        List<String> workoutNames = workoutDAO.getAllWorkoutNames(user.getId());
        workoutComboBox.getItems().addAll(workoutNames);

        // Label and TextField for exercise name
        Label exerciseLabel = new Label("Exercise Name:");
        exerciseLabel.setStyle("-fx-text-fill: green;"); // Set label color to white
        TextField exerciseField = new TextField();
        exerciseField.setEditable(false); // Initially not editable

        // Label and TextField for duration
        Label durationLabel = new Label("Duration (in minutes):");
        durationLabel.setStyle("-fx-text-fill: green;"); // Set label color to white
        TextField durationField = new TextField();

        // Label and TextField for calories
        Label calorieLabel = new Label("Calories Burned:");
        calorieLabel.setStyle("-fx-text-fill: green;"); // Set label color to white
        TextField calorieField = new TextField();

        // Save button styled with theme
        Button saveButton = new Button("Save Changes");
        styleButton(saveButton);

        saveButton.setOnAction(e -> {
            String exerciseName = exerciseField.getText().trim(); // Get the exercise name
            int duration;
            int calories;

            // Validate and parse the duration
            try {
                duration = Integer.parseInt(durationField.getText().trim()); // Parse the duration
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Please enter a valid number for duration.");
                return; // Exit the method if parsing fails
            }

            // Validate and parse the calories
            try {
                calories = Integer.parseInt(calorieField.getText().trim()); // Parse the calories
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Please enter a valid number for calories.");
                return; // Exit the method if parsing fails
            }

            // Call the editWorkout method with the updated details
            Workout selectedWorkout = workoutDAO.getWorkoutByName(user.getId(), workoutComboBox.getValue());
            if (selectedWorkout != null) {
                if (workoutDAO.editWorkout(selectedWorkout.getId(), exerciseName, duration, calories)) {
                    showAlert(Alert.AlertType.INFORMATION, "Workout updated successfully!");
                    primaryStage.setScene(new WorkoutPage(primaryStage, user).getScene()); // Navigate back to WorkoutPage
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed to update workout!");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "No workout selected or workout not found.");
            }
        });

        // Delete button styled with theme
        Button deleteButton = new Button("Delete");
        styleButton(deleteButton);

        deleteButton.setOnAction(e -> {
            String selectedWorkoutName = workoutComboBox.getValue();
            if (selectedWorkoutName != null) {
                boolean deleted = workoutDAO.deleteWorkoutByExerciseName(selectedWorkoutName);
                if (deleted) {
                    showAlert(Alert.AlertType.INFORMATION, "Workout deleted successfully!");
                    primaryStage.setScene(new WorkoutPage(primaryStage, user).getScene()); // Navigate back to WorkoutPage
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed to delete workout.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "No workout selected for deletion.");
            }
        });

        // Back button styled with theme
        Button backButton = new Button("Back");
        styleButton(backButton);

        backButton.setOnAction(e -> {
            primaryStage.setScene(new WorkoutPage(primaryStage, user).getScene()); // Navigate back to WorkoutPage
        });

        // Add listener to ComboBox to fetch workout details when a workout is selected
        workoutComboBox.setOnAction(e -> {
            String selectedWorkoutName = workoutComboBox.getValue();
            if (selectedWorkoutName != null) {
                Workout selectedWorkout = workoutDAO.getWorkoutByName(user.getId(), selectedWorkoutName);
                if (selectedWorkout != null) {
                    exerciseField.setText(selectedWorkout.getExerciseName());
                    durationField.setText(String.valueOf(selectedWorkout.getDuration()));
                    calorieField.setText(String.valueOf(selectedWorkout.getCalories()));

                    // Make the exerciseField editable when a workout is selected
                    exerciseField.setEditable(true);
                }
            }
        });

        // Add all components to the layout
        layout.getChildren().addAll(
                titleLabel,
                selectWorkoutLabel, workoutComboBox,
                exerciseLabel, exerciseField,
                durationLabel, durationField,
                calorieLabel, calorieField,
                saveButton, deleteButton, backButton
        );

        // Create a ScrollPane to make the layout scrollable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true); // Make the ScrollPane fit the width of the layout
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Always show vertical scrollbar

        // Create and return the scene with the ScrollPane as root
        Scene scene = new Scene(scrollPane, 400, 350); // Adjust height for UI components
        scene.getStylesheets().add(getClass().getResource("/resources/workout.css").toExternalForm()); // Apply CSS
        return scene; // Return the created scene
    }

    // Method to style buttons with theme
    private void styleButton(Button button) {
        button.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        button.setMinWidth(150); // Increase minimum width for visibility
        button.setMinHeight(40); // Increase minimum height for better button size
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand;");
    }

    // Method to show alerts
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Error" : "Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
