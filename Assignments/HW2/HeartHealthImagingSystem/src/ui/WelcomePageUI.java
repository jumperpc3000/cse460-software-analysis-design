package ui;

import control.HeartHealthSystem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Role;
import model.User;

import java.util.Optional;

/**
 * Main UI of the system.
 */
public class WelcomePageUI {
    private final Stage stage;
    private final HeartHealthSystem system;
    private final BorderPane root;

    public WelcomePageUI(Stage stage, HeartHealthSystem system) {
        this.stage = stage;
        this.system = system;
        this.root = new BorderPane();
        displayWelcomePage();
    }

    public Parent getRoot() {
        return root;
    }

    public void displayWelcomePage() {
        Label title = new Label("Welcome to Heart Health Imaging and Recording System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-wrap-text: true;");

        Optional<User> currentUser = system.getCurrentUser();
        Label subtitle = new Label(currentUser
                .map(user -> "Signed in as " + user.getUserID() + " (" + user.getRole().getRoleName() + ")")
                .orElse("No user is signed in."));
        subtitle.setStyle("-fx-font-size: 14px;");

        Button patientIntakeButton = navigationButton("Patient Intake");
        applyRoleAccess(patientIntakeButton, Role.RECEPTIONIST);
        patientIntakeButton.setOnAction(event -> navigateToSection(new PatientIntakeUI(stage, system).getRoot()));

        Button techButton = navigationButton("CT Scan Tech View");
        applyRoleAccess(techButton, Role.CT_SCAN_TECHNICIAN);
        techButton.setOnAction(event -> navigateToSection(new CTScanTechViewUI(stage, system).getRoot()));

        Button patientButton = navigationButton("Patient View");
        applyRoleAccess(patientButton, Role.PATIENT);
        patientButton.setOnAction(event -> navigateToSection(new PatientViewUI(stage, system).getRoot()));

        Button doctorButton = navigationButton("Doctor View");
        applyRoleAccess(doctorButton, Role.HEART_SPECIALIST);
        doctorButton.setOnAction(event -> navigateToSection(new DoctorViewUI(stage, system).getRoot()));

        Button logoutButton = navigationButton("Log Out");
        logoutButton.setOnAction(event -> {
            system.logout();
            stage.getScene().setRoot(new LoginUI(stage, system).getRoot());
        });

        VBox menu = new VBox(14, title, subtitle, patientIntakeButton, techButton, patientButton, doctorButton, logoutButton);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(40));

        root.setCenter(menu);
    }

    public void navigateToSection(Parent nextScreen) {
        stage.getScene().setRoot(nextScreen);
    }

    static Button navigationButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(300);
        button.setMinHeight(42);
        button.setStyle("-fx-font-size: 15px;");
        return button;
    }

    static Button backButton(Stage stage, HeartHealthSystem system) {
        Button back = new Button("Back to Main Menu");
        back.setOnAction(event -> stage.getScene().setRoot(new WelcomePageUI(stage, system).getRoot()));
        return back;
    }

    private void applyRoleAccess(Button button, Role requiredRole) {
        boolean allowed = system.canAccess(requiredRole);
        button.setDisable(!allowed);
        button.setOpacity(allowed ? 1.0 : 0.55);
    }
}
