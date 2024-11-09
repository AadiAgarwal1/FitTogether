package com.fittogether.database;

import com.fittogether.model.Diet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DietDAO {
    private Connection connection;

    // Constructor
    public DietDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new diet entry
    public void addDietEntry(Diet diet) throws SQLException {
        String sql = "INSERT INTO diet (user_id, food_item, calories, date_consumed, meal_type) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, diet.getUserId());
            statement.setString(2, diet.getFoodItem());
            statement.setInt(3, diet.getCalories());
            statement.setDate(4, diet.getDateConsumed()); // Using sql.Date directly
            statement.setString(5, diet.getMealType());
            statement.executeUpdate();
        }
    }

    // Method to get all diet entries for a specific user
    public List<Diet> getDietEntriesByUserId(int userId) throws SQLException {
        List<Diet> diets = new ArrayList<>();
        String sql = "SELECT * FROM diet WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String foodItem = resultSet.getString("food_item");
                int calories = resultSet.getInt("calories");
                Date dateConsumed = resultSet.getDate("date_consumed"); // Already sql.Date
                String mealType = resultSet.getString("meal_type");
                diets.add(new Diet(id, userId, foodItem, calories, dateConsumed, mealType));
            }
        }
        return diets;
    }

    // Method to delete a diet entry by its ID
    public void deleteDietEntry(int id) throws SQLException {
        String sql = "DELETE FROM diet WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Method to update an existing diet entry
    public void updateDietEntry(Diet diet) throws SQLException {
        String sql = "UPDATE diet SET food_item = ?, calories = ?, date_consumed = ?, meal_type = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, diet.getFoodItem());
            statement.setInt(2, diet.getCalories());
            statement.setDate(3, diet.getDateConsumed()); // Using sql.Date directly
            statement.setString(4, diet.getMealType());
            statement.setInt(5, diet.getId()); // Assuming that the Diet class has a method getId()
            statement.executeUpdate();
        }
    }
}
