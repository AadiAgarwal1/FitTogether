package com.fittogether.ui;

import com.fittogether.model.User;
import com.fittogether.database.UserDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class LoginPage {
    private Stage primaryStage;
    private Scene scene;

    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupUI();
    }

    private void setupUI() {
        // Main layout (VBox) for login form components
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.getStyleClass().add("root");  // Applying root class for overall styling

        // Page Title
        Label pageTitle = new Label("Fit Together");
        pageTitle.getStyleClass().add("page-title");  // Styling title with CSS class

        // Section Title
        Label headline = new Label("Login to your account");
        headline.getStyleClass().add("headline");  // Styling headline with CSS class

        // Input Fields
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.getStyleClass().add("text-field");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.getStyleClass().add("password-field");

        // Login and Register Buttons
        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button");

        Button registerButton = new Button("Create Account");
        registerButton.getStyleClass().add("button");

        // Error Message Label
        Label errorMessage = new Label();
        errorMessage.getStyleClass().add("error-message");

        // Action for login button
        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            // Check if fields are empty
            if (email.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Login Error", "All fields are required.");
                return;
            }

            // Validate email format
            if (!isValidEmail(email)) {
                showAlert(Alert.AlertType.ERROR, "Login Error", "Please enter a valid email.");
                return;
            }

            // Attempt user login
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findUserByEmailAndPassword(email, password);

            if (user != null) {
                // Navigate to HomePage if login is successful
                HomePage homePage = new HomePage(primaryStage, user);
                primaryStage.setScene(homePage.getScene());
            } else {
                // Display error message for invalid login
                errorMessage.setText("Invalid email or password.");
            }
        });

        // Action for register button to navigate to RegistrationPage
        registerButton.setOnAction(e -> {
            RegistrationPage registrationPage = new RegistrationPage(primaryStage);
            primaryStage.setScene(registrationPage.getScene());
        });

        // Adding components to layout
        layout.getChildren().addAll(pageTitle, headline, emailField, passwordField, loginButton, registerButton, errorMessage);

        // Creating a ScrollPane for scrolling capability
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.getStyleClass().add("scroll-pane");  // Applying scroll-pane CSS class
        scrollPane.setFitToWidth(true);  // Fitting content width to ScrollPane

        // Creating scene with defined dimensions
        this.scene = new Scene(scrollPane, 500, 600);

        // Loading CSS file for styling
        scene.getStylesheets().add(getClass().getResource("/resources/login.css").toExternalForm());

        // Setting the scene to primary stage
        primaryStage.setScene(scene);
    }

    // Method to get the scene
    public Scene getScene() {
        return scene;
    }

    // Utility method to display alert dialogs
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Utility method to validate email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }
}
