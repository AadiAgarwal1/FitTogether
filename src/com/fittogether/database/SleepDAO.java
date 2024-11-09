package com.fittogether.database;

import com.fittogether.model.Sleep;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SleepDAO {
    private Connection connection;

    // Constructor initializes the connection to the database
    public SleepDAO() {
        connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.err.println("Failed to establish a database connection.");
        }
    }

    // Log a new sleep record to the database (Create)
    public void logSleep(Sleep sleep) {
        String sql = "INSERT INTO sleep (user_id, start_time, end_time, duration, mood, disturbances, quality) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, sleep.getUserId());
            stmt.setTimestamp(2, Timestamp.valueOf(sleep.getStartTime()));
            stmt.setTimestamp(3, Timestamp.valueOf(sleep.getEndTime()));
            stmt.setDouble(4, sleep.getDuration());
            stmt.setString(5, sleep.getMood());
            stmt.setString(6, sleep.getDisturbances() != null ? sleep.getDisturbances() : "");  // Handle potential null disturbances
            stmt.setInt(7, sleep.getQuality());  // Use 'quality' instead of 'sleep_quality'

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Optionally handle the generated ID if needed
                        System.out.println("Sleep record added successfully.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error logging sleep: " + e.getMessage());
        }
    }

    // Retrieve all sleep records for a specific user (Read)
    public List<Sleep> getUserSleepHistory(int userId) {
        List<Sleep> sleepHistory = new ArrayList<>();
        String sql = "SELECT * FROM sleep WHERE user_id = ? ORDER BY start_time DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Timestamp startTime = rs.getTimestamp("start_time");
                Timestamp endTime = rs.getTimestamp("end_time");
                double duration = rs.getDouble("duration");
                String mood = rs.getString("mood");
                String disturbances = rs.getString("disturbances");
                int quality = rs.getInt("quality");  // 'quality' is now used instead of 'sleep_quality'

                // Create the Sleep object (excluding ID)
                Sleep sleep = new Sleep(userId, startTime.toLocalDateTime(), endTime.toLocalDateTime(),
                        duration, mood, disturbances, quality);
                sleepHistory.add(sleep);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving sleep history: " + e.getMessage());
        }

        return sleepHistory;
    }

    // Retrieve a specific sleep record by ID (Read)
    public Sleep getSleepById(int sleepId) {
        String sql = "SELECT * FROM sleep WHERE id = ?";
        Sleep sleep = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sleepId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                Timestamp startTime = rs.getTimestamp("start_time");
                Timestamp endTime = rs.getTimestamp("end_time");
                double duration = rs.getDouble("duration");
                String mood = rs.getString("mood");
                String disturbances = rs.getString("disturbances");
                int quality = rs.getInt("quality");  // 'quality' is now used instead of 'sleep_quality'

                // Create the Sleep object (excluding ID)
                sleep = new Sleep(userId, startTime.toLocalDateTime(), endTime.toLocalDateTime(),
                        duration, mood, disturbances, quality);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving sleep by ID: " + e.getMessage());
        }

        return sleep;
    }

    // Update an existing sleep record (Update)
    public void updateSleep(Sleep sleep) {
        String sql = "UPDATE sleep SET start_time = ?, end_time = ?, duration = ?, mood = ?, disturbances = ?, quality = ? WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(sleep.getStartTime()));
            stmt.setTimestamp(2, Timestamp.valueOf(sleep.getEndTime()));
            stmt.setDouble(3, sleep.getDuration());
            stmt.setString(4, sleep.getMood());
            stmt.setString(5, sleep.getDisturbances() != null ? sleep.getDisturbances() : "");  // Handle null disturbances
            stmt.setInt(6, sleep.getQuality());  // Use 'quality' instead of 'sleep_quality'
            stmt.setInt(7, sleep.getUserId());

            stmt.executeUpdate();
            System.out.println("Sleep record updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating sleep record: " + e.getMessage());
        }
    }

    // Delete a sleep record by userId (Delete)
    public void deleteSleep(int userId) {
        String sql = "DELETE FROM sleep WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            System.out.println("Sleep record deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting sleep record: " + e.getMessage());
        }
    }
}
