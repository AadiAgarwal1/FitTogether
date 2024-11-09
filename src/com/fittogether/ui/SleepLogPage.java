package com.fittogether.ui;

import com.fittogether.model.User;
import com.fittogether.model.Sleep;
import com.fittogether.database.SleepDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SleepLogPage {
    private Stage primaryStage;
    private User user;
    private SleepDAO sleepDAO;

    public SleepLogPage(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        this.sleepDAO = new SleepDAO();  // Initialize the SleepDAO to interact with the database
        setupUI();
    }

    private void setupUI() {
        // Create a GridPane for better layout organization
        GridPane layout = new GridPane();
        layout.setVgap(10);  // Vertical spacing between rows
        layout.setHgap(10);  // Horizontal spacing between columns
        layout.setPadding(new Insets(20)); // Padding around the layout
        layout.setAlignment(Pos.CENTER); // Center the contents

        // Title label
        Label titleLabel = new Label("Add Sleep Log");
        titleLabel.getStyleClass().add("headline");

        // Add Labels (Titles) for Start Time, End Time, Mood, Disturbances, and Sleep Quality
        Label startTimeLabel = new Label("Sleep Time:");
        startTimeLabel.getStyleClass().add("box-title"); // Apply box-title style
        Label endTimeLabel = new Label("Wake Up Time:");
        endTimeLabel.getStyleClass().add("box-title"); // Apply box-title style
        Label moodLabel = new Label("Mood:");
        moodLabel.getStyleClass().add("box-title"); // Apply box-title style
        Label disturbancesLabel = new Label("Disturbances:");
        disturbancesLabel.getStyleClass().add("box-title"); // Apply box-title style
        Label sleepQualityLabel = new Label("Sleep Quality:");
        sleepQualityLabel.getStyleClass().add("box-title"); // Apply box-title style

        // Creating a DatePicker for selecting date and ComboBoxes for hour and minute selection
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setValue(LocalDate.now()); // Default to today's date
        ComboBox<Integer> startHourComboBox = new ComboBox<>();
        ComboBox<Integer> startMinuteComboBox = new ComboBox<>();
        for (int i = 0; i < 24; i++) startHourComboBox.getItems().add(i);
        for (int i = 0; i < 60; i++) startMinuteComboBox.getItems().add(i);

        ComboBox<Integer> endHourComboBox = new ComboBox<>();
        ComboBox<Integer> endMinuteComboBox = new ComboBox<>();
        for (int i = 0; i < 24; i++) endHourComboBox.getItems().add(i);
        for (int i = 0; i < 60; i++) endMinuteComboBox.getItems().add(i);

        // Set default time values (current time)
        LocalDateTime now = LocalDateTime.now();
        startHourComboBox.setValue(now.getHour());
        startMinuteComboBox.setValue(now.getMinute());
        endHourComboBox.setValue(now.getHour() + 8); // Assuming sleep duration of 8 hours by default
        endMinuteComboBox.setValue(now.getMinute());

        // Mood, Disturbances, and Sleep Quality options
        ComboBox<String> moodComboBox = new ComboBox<>();
        moodComboBox.getItems().addAll("Happy", "Neutral", "Tired");
        ComboBox<String> disturbancesComboBox = new ComboBox<>();
        disturbancesComboBox.getItems().addAll("None", "Few", "Many", "Interrupted", "Restless");
        ComboBox<Integer> sleepQualityComboBox = new ComboBox<>();
        sleepQualityComboBox.getItems().addAll(1, 2, 3, 4, 5);

        // Buttons
        Button logSleepButton = new Button("Log Sleep");
        Button backButton = new Button("Back to Sleep Tracker");

        // Apply the style class for buttons
        logSleepButton.getStyleClass().add("button");
        backButton.getStyleClass().add("button");

        // Tooltips for each input field for better clarity
        startDatePicker.setTooltip(new Tooltip("Select the date you went to sleep"));
        startHourComboBox.setTooltip(new Tooltip("Select the hour you went to sleep"));
        startMinuteComboBox.setTooltip(new Tooltip("Select the minute you went to sleep"));
        endHourComboBox.setTooltip(new Tooltip("Select the hour you woke up"));
        endMinuteComboBox.setTooltip(new Tooltip("Select the minute you woke up"));
        moodComboBox.setTooltip(new Tooltip("Select your mood after waking up"));
        disturbancesComboBox.setTooltip(new Tooltip("Select disturbances during your sleep"));
        sleepQualityComboBox.setTooltip(new Tooltip("Rate your sleep quality (1 = worst, 5 = best)"));

        // Add all elements to the GridPane
        layout.add(titleLabel, 0, 0, 2, 1);  // Title spans across two columns
        layout.add(startTimeLabel, 0, 1);
        layout.add(startDatePicker, 1, 1);
        layout.add(startHourComboBox, 1, 2);
        layout.add(startMinuteComboBox, 1, 3);
        layout.add(endTimeLabel, 0, 4);
        layout.add(endHourComboBox, 1, 4);
        layout.add(endMinuteComboBox, 1, 5);
        layout.add(moodLabel, 0, 6);
        layout.add(moodComboBox, 1, 6);
        layout.add(disturbancesLabel, 0, 7);
        layout.add(disturbancesComboBox, 1, 7);
        layout.add(sleepQualityLabel, 0, 8);
        layout.add(sleepQualityComboBox, 1, 8);
        layout.add(logSleepButton, 0, 9, 2, 1);  // Button spans across two columns
        layout.add(backButton, 0, 10, 2, 1);  // Back button spans across two columns

        // Log Sleep button logic
        logSleepButton.setOnAction(e -> {
            LocalDate startDate = startDatePicker.getValue();
            Integer startHour = startHourComboBox.getValue();
            Integer startMinute = startMinuteComboBox.getValue();
            Integer endHour = endHourComboBox.getValue();
            Integer endMinute = endMinuteComboBox.getValue();
            String mood = moodComboBox.getValue();
            String disturbances = disturbancesComboBox.getValue();
            Integer sleepQuality = sleepQualityComboBox.getValue();

            // Validate all fields are filled
            if (startDate != null && startHour != null && startMinute != null && endHour != null && endMinute != null
                    && mood != null && disturbances != null && sleepQuality != null) {

                // Create the start and end LocalDateTime objects
                LocalDateTime startDateTime = startDate.atTime(startHour, startMinute);
                LocalDateTime endDateTime = startDate.atTime(endHour, endMinute);

                // Ensure the end time is after the start time
                if (startDateTime.isBefore(endDateTime)) {
                    long durationInMinutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime);
                    double durationInHours = durationInMinutes / 60.0;

                    // Create a Sleep object
                    Sleep sleep = new Sleep(user.getId(), startDateTime, endDateTime, durationInHours, mood, disturbances, sleepQuality);

                    // Log the sleep record to the database
                    sleepDAO.logSleep(sleep);

                    // Show confirmation alert
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sleep logged successfully!", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    // Show error if end time is before start time
                    Alert alert = new Alert(Alert.AlertType.ERROR, "End time cannot be before start time.", ButtonType.OK);
                    alert.showAndWait();
                }
            } else {
                // Show error if any field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields before logging sleep.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // Back button logic to return to Sleep Tracker Page
        backButton.setOnAction(e -> {
            SleepTrackerPage sleepTrackerPage = new SleepTrackerPage(primaryStage, user);
            primaryStage.setScene(sleepTrackerPage.getScene());
        });

        // Wrap the layout in a ScrollPane to make it scrollable
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

        // Set the scene with the scrollable layout
        Scene scene = new Scene(scrollPane, 600, 700); // Adjusted size for better view

        // Load CSS file
        scene.getStylesheets().add(getClass().getResource("/resources/sleeplog.css").toExternalForm());

        primaryStage.setScene(scene);
    }

    public Scene getScene() {
        return this.primaryStage.getScene();
    }
}
