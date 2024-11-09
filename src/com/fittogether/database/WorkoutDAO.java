package com.fittogether.database;

import com.fittogether.model.Workout;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkoutDAO {
    private static final Logger LOGGER = Logger.getLogger(WorkoutDAO.class.getName());
    private Connection connection;

    // Constructor to get connection from DatabaseConnection class
    public WorkoutDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Method to add a new workout (with calories)
    public boolean addWorkout(int userId, String exerciseName, int duration, int calories) {
        String sql = "INSERT INTO Workouts (user_id, exercise_name, duration, calories) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, exerciseName);
            preparedStatement.setInt(3, duration);
            preparedStatement.setInt(4, calories);  // Add calories value
            
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        LOGGER.info("Workout added with ID: " + generatedId);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding workout", e);
        }
        return false;
    }

    // Method to edit an existing workout (with calories)
    public boolean editWorkout(int workoutId, String newExerciseName, int newDuration, int newCalories) {
        String sql = "UPDATE Workouts SET exercise_name = ?, duration = ?, calories = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newExerciseName);
            preparedStatement.setInt(2, newDuration);
            preparedStatement.setInt(3, newCalories);  // Set new calories value
            preparedStatement.setInt(4, workoutId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error editing workout", e);
        }
        return false;
    }

    // Method to retrieve all workouts for a specific user
    public List<Workout> getWorkoutsByUserId(int userId) {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM Workouts WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String exerciseName = resultSet.getString("exercise_name");
                    int duration = resultSet.getInt("duration");
                    int calories = resultSet.getInt("calories");  // Fetch calories value
                    workouts.add(new Workout(id, userId, exerciseName, duration, calories));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving workouts for user", e);
        }
        return workouts;
    }

    // Method to delete a workout by ID
    public boolean deleteWorkout(int workoutId) {
        String sql = "DELETE FROM Workouts WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, workoutId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting workout", e);
        }
        return false;
    }

    // Method to fetch all distinct exercise names for ComboBox selection
    public List<String> getAllExerciseNames() {
        List<String> exerciseNames = new ArrayList<>();
        String sql = "SELECT DISTINCT exercise_name FROM Workouts";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                exerciseNames.add(resultSet.getString("exercise_name"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching exercise names", e);
        }
        return exerciseNames;
    }

    // Method to delete workout by exercise name
    public boolean deleteWorkoutByExerciseName(String exerciseName) {
        String sql = "DELETE FROM Workouts WHERE exercise_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, exerciseName);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting workout by exercise name", e);
        }
        return false;
    }

    // Method to fetch all workouts with detailed information for a user
    public List<String> getAllWorkoutsForUser(int userId) {
        List<String> workouts = new ArrayList<>();
        String sql = "SELECT exercise_name, duration, calories FROM Workouts WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String exerciseName = rs.getString("exercise_name");
                    int duration = rs.getInt("duration");
                    int calories = rs.getInt("calories");  // Get calories value
                    workouts.add(exerciseName + " - " + duration + " mins - " + calories + " kcal");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching workouts for user", e);
        }
        return workouts;
    }

    // Method to fetch all workout names for display in ViewWorkoutPage
    public List<String> getAllWorkoutNames(int userId) {
        List<String> workoutNames = new ArrayList<>();
        String sql = "SELECT exercise_name FROM Workouts WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    workoutNames.add(rs.getString("exercise_name"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching workout names", e);
        }
        return workoutNames;
    }

    // Method to fetch details of a workout by name
    public Workout getWorkoutByName(int userId, String workoutName) {
        String sql = "SELECT * FROM Workouts WHERE user_id = ? AND exercise_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, workoutName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String exerciseName = rs.getString("exercise_name");
                    int duration = rs.getInt("duration");
                    int calories = rs.getInt("calories");
                    return new Workout(id, userId, exerciseName, duration, calories);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching workout by name", e);
        }
        return null;
    }
}
