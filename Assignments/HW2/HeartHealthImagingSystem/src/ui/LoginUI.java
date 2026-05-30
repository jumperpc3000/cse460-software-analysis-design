package ui;

import control.HeartHealthSystem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Role;
import model.User;
import util.AlertUtils;

import java.io.IOException;
import java.util.Optional;

/**
 * Login boundary UI for authenticating users before showing the main menu.
 */
public class LoginUI {
    private final Stage stage;
    private final HeartHealthSystem system;
    private final BorderPane root;

    private final TextField userIDField = new TextField();
    private final PasswordField passwordField = new PasswordField();

    public LoginUI(Stage stage, HeartHealthSystem system) {
        this.stage = stage;
        this.system = system;
        this.root = new BorderPane();
        displayLoginForm();
    }

    public Parent getRoot() {
        return root;
    }

    public void displayLoginForm() {
        Label title = new Label("Heart Health Imaging Login");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label subtitle = new Label("Enter your system credentials. Patients can enter their 5-digit patient ID without a password.");
        subtitle.setStyle("-fx-font-size: 14px;");

        userIDField.setPromptText("User ID");
        passwordField.setPromptText("Password");
        userIDField.setOnAction(event -> submitLogin());
        passwordField.setOnAction(event -> submitLogin());

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(12);
        form.addRow(0, new Label("User ID:"), userIDField);
        form.addRow(1, new Label("Password:"), passwordField);

        Button loginButton = WelcomePageUI.navigationButton("Log In");
        loginButton.setOnAction(event -> submitLogin());

        HBox actions = new HBox(10, loginButton);
        actions.setAlignment(Pos.CENTER_LEFT);

        VBox content = new VBox(14, title, subtitle, form, actions);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        root.setCenter(content);
    }

    public void submitLogin() {
        try {
            Optional<User> user = system.authenticateUser(userIDField.getText(), passwordField.getText());
            if (user.isEmpty()) {
                AlertUtils.warning("Login Failed", "Invalid user ID or password.");
                return;
            }

            if (isPatientIDLogin(user.get())) {
                PatientViewUI patientViewUI = new PatientViewUI(stage, system);
                stage.getScene().setRoot(patientViewUI.getRoot());
                patientViewUI.loadPatientResults(user.get().getUserID());
                return;
            }

            stage.getScene().setRoot(new WelcomePageUI(stage, system).getRoot());
        } catch (IOException ex) {
            AlertUtils.error("File Error", "Could not verify login: " + ex.getMessage());
        }
    }

    private static boolean isPatientIDLogin(User user) {
        return user.hasRole(Role.PATIENT) && user.getUserID().matches("\\d{5}");
    }
}
