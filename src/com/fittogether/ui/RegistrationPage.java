package com.fittogether.ui;

import com.fittogether.database.UserDAO;
import com.fittogether.model.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrationPage {
    private Stage primaryStage;
    private UserDAO userDAO;

    // Constructor to initialize primaryStage and UserDAO
    public RegistrationPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.userDAO = new UserDAO(); // Initialize UserDAO for database interaction
    }

    // Method to generate the registration page's scene
    public Scene getScene() {
        VBox layout = new VBox(20); // Create vertical layout with 20px spacing
        layout.setAlignment(Pos.CENTER); // Center align all children in the VBox
        layout.setStyle("-fx-background-color: black;"); // Set background color to black

        // Page Title
        Label pageTitle = new Label("Fit Together");
        pageTitle.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #3ea808;");

        // Registration Section Title
        Label registerTitle = new Label("Register Your Account");
        registerTitle.setStyle("-fx-font-size: 24px; -fx-text-fill: #FF5722;");

        // Section Headline
        Label headline = new Label("Create a new account");
        headline.setStyle("-fx-font-size: 18px; -fx-text-fill: #FF5722;");

        // Input fields for user information
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.getStyleClass().add("input-field");

        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.getStyleClass().add("input-field");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.getStyleClass().add("input-field");

        TextField ageField = new TextField();
        ageField.setPromptText("Enter your age");
        ageField.getStyleClass().add("input-field");

        // Buttons
        Button registerButton = new Button("Create New User");
        registerButton.setPrefWidth(200); // Set preferred width for the button
        registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        registerButton.setOnMouseEntered(e -> registerButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-weight: bold;"));
        registerButton.setOnMouseExited(e -> registerButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;"));

        Button backButton = new Button("Back to Login");
        backButton.setPrefWidth(200); // Set preferred width for the button
        backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-weight: bold;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;"));

        // Register button action to handle form submission
        registerButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String ageText = ageField.getText();

            // Validate input fields - all fields are required
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || ageText.isEmpty()) {
                showAlert(AlertType.ERROR, "Registration Error", "All fields are required!");
                return;
            }

            // Validate email format
            if (!isValidEmail(email)) {
                showAlert(AlertType.ERROR, "Registration Error", "Invalid email format.");
                return;
            }

            // Validate age
            int age;
            try {
                age = Integer.parseInt(ageText);
                if (age < 0 || age > 120) {
                    showAlert(AlertType.ERROR, "Registration Error", "Please enter a valid age between 0 and 120.");
                    return;
                }
            } catch (NumberFormatException ex) {
                showAlert(AlertType.ERROR, "Registration Error", "Age must be a number.");
                return;
            }

            // Check if user already exists
            if (userDAO.userExists(email)) {
                showAlert(AlertType.ERROR, "Registration Error", "A user already exists with this email.");
            } else {
                // Register a new user
                User newUser = new User(name, email, password, age);
                if (userDAO.registerUser(newUser)) {
                    showAlert(AlertType.INFORMATION, "Registration Successful", "User registered successfully!");
                    // Clear input fields after successful registration
                    nameField.clear();
                    emailField.clear();
                    passwordField.clear();
                    ageField.clear();
                } else {
                    showAlert(AlertType.ERROR, "Registration Error", "Failed to register user. Please try again.");
                }
            }
        });

        // Back button action to return to the login page
        backButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(primaryStage);
            primaryStage.setScene(loginPage.getScene());
            primaryStage.setTitle("Login");
            primaryStage.show();
        });

        // VBox to stack input fields and buttons
        VBox formLayout = new VBox(15); // Spacing between form elements
        formLayout.getChildren().addAll(nameField, emailField, passwordField, ageField, registerButton, backButton);
        formLayout.setAlignment(Pos.CENTER);

        // Adding the form layout inside a ScrollPane
        ScrollPane scrollPane = new ScrollPane(formLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: black;"); // Set scroll pane background color

        // Main layout with titles and form
        VBox mainLayout = new VBox(20);
        mainLayout.getChildren().addAll(pageTitle, registerTitle, headline, scrollPane);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: black;");

        // Create and return the scene
        return new Scene(mainLayout, 400, 500); // Set the scene size as needed
    }

    // Method to show alert messages
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
