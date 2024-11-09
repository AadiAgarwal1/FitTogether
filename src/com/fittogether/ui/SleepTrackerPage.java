package com.fittogether.ui;

import com.fittogether.model.User;
import com.fittogether.database.SleepDAO;
import com.fittogether.model.Sleep;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SleepTrackerPage {
    private Stage primaryStage;
    private User user;
    private SleepDAO sleepDAO;

    public SleepTrackerPage(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        this.sleepDAO = new SleepDAO();
        setupUI();
    }

    private void setupUI() {
        VBox layout = new VBox(20); // Increased spacing between elements
        layout.setPadding(new Insets(20));

        // "FitTogether" title
        Label fitTogetherTitle = new Label("FitTogether");
        fitTogetherTitle.getStyleClass().add("fit-together-title"); // Apply CSS class for FitTogether title
        fitTogetherTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #3ea808;"); // Example styling for FitTogether title

        // "Sleep Tracker" title
        Label titleLabel = new Label("Sleep Tracker");
        titleLabel.getStyleClass().add("page-title"); // Apply CSS class for page title

        // Buttons for various actions
        Button logSleepButton = new Button("Log New Sleep");
        Button editSleepButton = new Button("Edit Sleep Record");
        Button viewHistoryButton = new Button("View Sleep History");
        Button goHomeButton = new Button("Go to Home");

        // Action for logging new sleep
        logSleepButton.setOnAction(e -> {
            SleepLogPage sleepLogPage = new SleepLogPage(primaryStage, user);
            primaryStage.setScene(sleepLogPage.getScene());
        });

        // Action for editing an existing sleep record
        editSleepButton.setOnAction(e -> {
            int sleepId = 1; // Replace with actual sleepId selected by the user

            Sleep sleepToEdit = sleepDAO.getSleepById(sleepId);
            if (sleepToEdit != null) {
                SleepEditPage sleepEditPage = new SleepEditPage(primaryStage, user, sleepToEdit);
                primaryStage.setScene(sleepEditPage.getScene());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Sleep log not found.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // Action for viewing sleep history
        viewHistoryButton.setOnAction(e -> {
            SleepHistoryPage sleepHistoryPage = new SleepHistoryPage(primaryStage, user);
            primaryStage.setScene(sleepHistoryPage.getScene());
        });

        // Action for "Go to Home" button
        goHomeButton.setOnAction(e -> {
            HomePage homePage = new HomePage(primaryStage, user);
            primaryStage.setScene(homePage.getScene());
        });

        // Add buttons to layout and apply CSS class
        logSleepButton.getStyleClass().add("sleep-button");
        editSleepButton.getStyleClass().add("sleep-button");
        viewHistoryButton.getStyleClass().add("sleep-button");
        goHomeButton.getStyleClass().add("sleep-button");

        layout.getChildren().addAll(fitTogetherTitle, titleLabel, logSleepButton, editSleepButton, viewHistoryButton, goHomeButton);

        // Create scene and set it on primaryStage
        Scene scene = new Scene(layout, 500, 400);

        // Load CSS file
        scene.getStylesheets().add(getClass().getResource("/resources/sleep.css").toExternalForm());

        primaryStage.setScene(scene);
    }

    public Scene getScene() {
        return this.primaryStage.getScene();
    }
}
