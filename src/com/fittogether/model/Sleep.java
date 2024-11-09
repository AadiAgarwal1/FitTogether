package com.fittogether.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Sleep {
    private int id;
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double duration;
    private String mood;
    private String disturbances;
    private int quality;  // Renamed from sleepQuality to quality

    // Constructor
    public Sleep(int userId, LocalDateTime startTime, LocalDateTime endTime, double duration, String mood, String disturbances, int quality) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.mood = mood;
        this.disturbances = disturbances;
        this.quality = quality;  // Updated constructor to reflect the change
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getDisturbances() {
        return disturbances;
    }

    public void setDisturbances(String disturbances) {
        this.disturbances = disturbances;
    }

    // Updated method names for consistency with the database
    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    // Properties for use in TableView
    public StringProperty startTimeProperty() {
        return new SimpleStringProperty(startTime.toString());
    }

    public StringProperty endTimeProperty() {
        return new SimpleStringProperty(endTime.toString());
    }

    public StringProperty durationProperty() {
        return new SimpleStringProperty(String.valueOf(duration));
    }

    public StringProperty moodProperty() {
        return new SimpleStringProperty(mood);
    }

    public StringProperty disturbancesProperty() {
        return new SimpleStringProperty(disturbances);
    }

    public IntegerProperty qualityProperty() {  // Updated to match the field name
        return new SimpleIntegerProperty(quality);
    }
}
