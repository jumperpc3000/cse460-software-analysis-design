package ui;

import control.HeartHealthSystem;
import model.PatientRecord;
import util.AlertUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Patient Intake boundary UI.
 */
public class PatientIntakeUI {
    private final Stage stage;
    private final HeartHealthSystem system;
    private final BorderPane root;

    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();
    private final TextField emailField = new TextField();
    private final TextField phoneField = new TextField();
    private final TextArea healthHistoryArea = new TextArea();
    private final TextField insuranceIDField = new TextField();
    private final DatePicker examDatePicker = new DatePicker(LocalDate.now().plusDays(1));
    private final Label generatedIDLabel = new Label("Patient ID will be generated after save.");

    public PatientIntakeUI(Stage stage, HeartHealthSystem system) {
        this.stage = stage;
        this.system = system;
        this.root = new BorderPane();
        displayIntakeForm();
    }

    public Parent getRoot() {
        return root;
    }

    public void displayIntakeForm() {
        Label title = new Label("Patient Intake Form");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        healthHistoryArea.setPrefRowCount(4);
        healthHistoryArea.setWrapText(true);

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(12);
        form.addRow(0, new Label("First Name:"), firstNameField);
        form.addRow(1, new Label("Last Name:"), lastNameField);
        form.addRow(2, new Label("Email:"), emailField);
        form.addRow(3, new Label("Phone Number:"), phoneField);
        form.addRow(4, new Label("Health History:"), healthHistoryArea);
        form.addRow(5, new Label("Insurance ID:"), insuranceIDField);
        form.addRow(6, new Label("Exam Date:"), examDatePicker);
        form.add(generatedIDLabel, 1, 7);

        Button saveButton = new Button("Save Patient and Schedule Exam");
        saveButton.setOnAction(event -> displayPatientForm());

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> clearForm());

        HBox actions = new HBox(10, saveButton, clearButton, WelcomePageUI.backButton(stage, system));
        actions.setAlignment(Pos.CENTER_LEFT);

        BorderPane content = new BorderPane();
        content.setTop(title);
        content.setCenter(form);
        content.setBottom(actions);
        BorderPane.setMargin(title, new Insets(0, 0, 18, 0));
        BorderPane.setMargin(actions, new Insets(20, 0, 0, 0));

        root.setPadding(new Insets(28));
        root.setCenter(content);
    }

    public void displayPatientForm() {
        try {
            PatientRecord record = system.intakePatient(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    healthHistoryArea.getText(),
                    insuranceIDField.getText(),
                    examDatePicker.getValue()
            );

            generatedIDLabel.setText("Generated Patient ID: " + record.getPatientID());
            AlertUtils.info(
                    "Patient Saved",
                    "Patient intake saved successfully.\n\nPatient ID: " + record.getPatientID()
                            + "\nPatient info file: " + system.getDataStore().patientInfoPath(record.getPatientID()).toAbsolutePath()
                            + "\nAppointment file: " + system.getDataStore().appointmentPath(record.getPatientID()).toAbsolutePath()
            );
        } catch (IllegalArgumentException ex) {
            AlertUtils.warning("Invalid Intake Form", ex.getMessage());
        } catch (IOException ex) {
            AlertUtils.error("File Error", "Could not save patient information: " + ex.getMessage());
        }
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        healthHistoryArea.clear();
        insuranceIDField.clear();
        examDatePicker.setValue(LocalDate.now().plusDays(1));
        generatedIDLabel.setText("Patient ID will be generated after save.");
    }
}