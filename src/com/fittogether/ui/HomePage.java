package com.fittogether.ui;

import com.fittogether.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class HomePage {
    private Stage primaryStage;
    private User user;
    private Scene scene;

    // Constructor to accept Stage and User object
    public HomePage(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        setupUI();
    }

    // Method to set up the UI components
    private void setupUI() {
        VBox layout = new VBox(20); // 20px spacing between components
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER); // Center align all components within the VBox
        layout.setStyle("-fx-background-color: #121212;"); // Black background color

        // Title Label for the Home Page
        Label pageTitle = new Label("FitTogether - Home");
        pageTitle.getStyleClass().add("page-title");
        pageTitle.setStyle("-fx-text-fill: #3ea808; -fx-font-size: 24px; -fx-font-weight: bold;"); // Orange color for the title

        // Welcome Label for displaying user name
        Label welcomeLabel = new Label("Welcome, " + user.getName());
        welcomeLabel.getStyleClass().add("headline");
        welcomeLabel.setStyle("-fx-text-fill: #FF5722; -fx-font-size: 20px; -fx-font-weight: bold;"); // Orange color for the welcome label

        // Create buttons for navigation with consistent sizes
        Button workoutPageButton = createStyledButton("Go to Workout Tracker Page");
        Button sleepTrackerButton = createStyledButton("Go to Sleep Tracker Page");
        Button dietTrackerButton = createStyledButton("Go to Diet Tracker Page");
        Button logoutButton = createStyledButton("Log Out");

        // Set button actions
        workoutPageButton.setOnAction(e -> {
            WorkoutPage workoutPage = new WorkoutPage(primaryStage, user);
            primaryStage.setScene(workoutPage.getScene());
        });

        sleepTrackerButton.setOnAction(e -> {
            SleepTrackerPage sleepTrackerPage = new SleepTrackerPage(primaryStage, user);
            primaryStage.setScene(sleepTrackerPage.getScene());
        });

        dietTrackerButton.setOnAction(e -> {
            DietTrackerPage dietTrackerPage = new DietTrackerPage(primaryStage, user);
            primaryStage.setScene(dietTrackerPage.getScene());
        });

        logoutButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(primaryStage);
            primaryStage.setScene(loginPage.getScene());
        });

        // Add components to layout
        layout.getChildren().addAll(pageTitle, welcomeLabel, workoutPageButton, sleepTrackerButton, dietTrackerButton, logoutButton);

        // Create a ScrollPane for the layout
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: #121212;"); // Black background for ScrollPane

        // Set the CSS file for styling
        scrollPane.getStylesheets().add(getClass().getResource("/resources/home.css").toExternalForm());

        // Create the scene and store it in the instance variable
        this.scene = new Scene(scrollPane, 600, 500); // Adjusted size for better layout fit
    }

    // Method to create a styled button with consistent size, bold text, and increased padding
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("home-link");
        button.setStyle(
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " + // Bold text
            "-fx-padding: 15px 25px; " + // Increased padding
            "-fx-border-radius: 8px; " +
            "-fx-min-width: 200px;" // Consistent button width
        );
        return button;
    }

    // Method to return the scene
    public Scene getScene() {
        return scene;
    }
}
