package com.fittogether.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private SimpleIntegerProperty id;  
    private SimpleStringProperty name;
    private SimpleStringProperty email;
    private SimpleIntegerProperty age;
    private SimpleStringProperty password;

    // Constructor with all fields
    public User(int id, String name, String email, int age, String password) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.age = new SimpleIntegerProperty(age);
        this.password = new SimpleStringProperty(password);
    }

    // New constructor for registration (without id)
    public User(String name, String email, String password,int age) {
        this.id = new SimpleIntegerProperty(0); // Default id value (0 or another default value)
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.age = new SimpleIntegerProperty(age);
        this.password = new SimpleStringProperty(password);
    }

    // Default constructor
    public User() {
        this.id = new SimpleIntegerProperty(); 
        this.name = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.age = new SimpleIntegerProperty();
        this.password = new SimpleStringProperty();
    }

    // Getters and Setters
    public int getId() {
        return id.get(); 
    }

    public void setId(int id) {
        this.id.set(id); 
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public int getAge() {
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
}
