package com.fittogether.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Workout {
    private IntegerProperty id;
    private IntegerProperty userId;
    private StringProperty exerciseName;
    private IntegerProperty duration;
    private IntegerProperty calories;  // New property for calories

    // Constructor for creating a new workout without an ID
    public Workout(int userId, String exerciseName, int duration, int calories) {
        this.id = new SimpleIntegerProperty(0); // Default value for id
        this.userId = new SimpleIntegerProperty(userId);
        this.exerciseName = new SimpleStringProperty(exerciseName);
        this.duration = new SimpleIntegerProperty(duration);
        this.calories = new SimpleIntegerProperty(calories);  // Initialize calories
    }

    // Constructor for existing workouts with an ID
    public Workout(int id, int userId, String exerciseName, int duration, int calories) {
        this.id = new SimpleIntegerProperty(id);
        this.userId = new SimpleIntegerProperty(userId);
        this.exerciseName = new SimpleStringProperty(exerciseName);
        this.duration = new SimpleIntegerProperty(duration);
        this.calories = new SimpleIntegerProperty(calories);  // Initialize calories
    }

    // Getter methods
    public int getId() {
        return id.get();
    }

    public int getUserId() {
        return userId.get();
    }

    public String getExerciseName() {
        return exerciseName.get();
    }

    public int getDuration() {
        return duration.get();
    }

    public int getCalories() {
        return calories.get();  // Get calories value
    }

    // Setter methods with validation
    public void setId(int id) {
        this.id.set(id);
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public void setExerciseName(String exerciseName) {
        if (exerciseName != null && !exerciseName.isEmpty()) {
            this.exerciseName.set(exerciseName);
        }
    }

    public void setDuration(int duration) {
        if (duration > 0) {
            this.duration.set(duration);
        }
    }

    public void setCalories(int calories) {
        if (calories >= 0) {
            this.calories.set(calories);
        }
    }

    // JavaFX Property Bindings
    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public StringProperty exerciseNameProperty() {
        return exerciseName;
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public IntegerProperty caloriesProperty() {
        return calories;  // Bind calories property
    }

    // Method to update workout information
    public void editWorkout(String newExerciseName, int newDuration, int newCalories) {
        setExerciseName(newExerciseName);
        setDuration(newDuration);
        setCalories(newCalories);  // Update calories
    }
}
