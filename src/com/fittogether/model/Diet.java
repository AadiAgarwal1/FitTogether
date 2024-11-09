package com.fittogether.model;

import java.sql.Date; // Importing java.sql.Date

public class Diet {
    private int id; // Unique identifier for the diet entry
    private int userId; // ID of the user who logged the food item
    private String foodItem; // Name of the food item
    private int calories; // Number of calories in the food item
    private Date dateConsumed; // Date when the food item was consumed
    private String mealType; // Type of meal (Breakfast, Lunch, Dinner, Snack)

    // Constructor
    public Diet(int id, int userId, String foodItem, int calories, Date dateConsumed, String mealType) {
        this.id = id;
        this.userId = userId;
        this.foodItem = foodItem;
        this.calories = calories;
        this.dateConsumed = dateConsumed;
        this.mealType = mealType;
    }

    // Getters and Setters
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

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Date getDateConsumed() {
        return dateConsumed;
    }

    public void setDateConsumed(Date dateConsumed) {
        this.dateConsumed = dateConsumed;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}
