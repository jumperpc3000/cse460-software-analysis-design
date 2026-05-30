package ui;

import control.HeartHealthSystem;
import control.PatientResult;
import model.CTTest;
import model.PatientRecord;
import util.AlertUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Patient boundary UI for viewing CT results.
 */
public class PatientViewUI {
    private final Stage stage;
    private final HeartHealthSystem system;
    private final BorderPane root;

    private final TextField patientIDField = new TextField();
    private final Label greetingLabel = new Label("Hello <Patient Name>");
    private final TextField totalCACField = readOnlyField();
    private final TextField LMField = readOnlyField();
    private final TextField LADField = readOnlyField();
    private final TextField LCXField = readOnlyField();
    private final TextField RCAField = readOnlyField();
    private final TextField PDAField = readOnlyField();

    public PatientViewUI(Stage stage, HeartHealthSystem system) {
        this.stage = stage;
        this.system = system;
        this.root = new BorderPane();
        displayPatientResults();
    }

    public Parent getRoot() {
        return root;
    }

    public void displayPatientResults() {
        Label title = new Label("Patient View - Seeing the Results");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        hideGreeting();
        patientIDField.setOnAction(event -> enterPatientID());

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(12);
        form.addRow(0, new Label("Patient ID:"), patientIDField);
        form.add(greetingLabel, 1, 1);
        form.addRow(2, new Label("The total Agatston CAC score:"), totalCACField);
        form.addRow(3, new Label("LM:"), LMField);
        form.addRow(4, new Label("LAD:"), LADField);
        form.addRow(5, new Label("LCX:"), LCXField);
        form.addRow(6, new Label("RCA:"), RCAField);
        form.addRow(7, new Label("PDA:"), PDAField);

        Button viewButton = new Button("View Results");
        viewButton.setOnAction(event -> enterPatientID());

        HBox actions = new HBox(10, viewButton, WelcomePageUI.backButton(stage, system));
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

    public void enterPatientID() {
        clearResults();

        try {
            PatientResult result = system.getPatientResult(patientIDField.getText());

            if (result.getStatus() == PatientResult.Status.WRONG_PATIENT_ID) {
                AlertUtils.warning("Wrong Patient ID", "wrong patient ID");
                return;
            }

            PatientRecord patientRecord = result.getPatientRecord().orElseThrow();

            if (result.getStatus() == PatientResult.Status.NO_DATA_AVAILABLE) {
                showGreeting(patientRecord);
                AlertUtils.info("No Data Available", "No data is available yet.");
                return;
            }

            CTTest ctTest = result.getCtTest().orElseThrow();
            showGreeting(patientRecord);
            fillCTFields(ctTest);
        } catch (IllegalArgumentException ex) {
            AlertUtils.warning("Invalid Patient ID", ex.getMessage());
        } catch (IOException ex) {
            AlertUtils.error("File Error", "Could not load CT scan results: " + ex.getMessage());
        }
    }

    public void loadPatientResults(String patientID) {
        patientIDField.setText(patientID);
        enterPatientID();
    }

    private void fillCTFields(CTTest ctTest) {
        totalCACField.setText(String.valueOf(ctTest.getTotalCACScore()));
        LMField.setText(String.valueOf(ctTest.getLMScore()));
        LADField.setText(String.valueOf(ctTest.getLADScore()));
        LCXField.setText(String.valueOf(ctTest.getLCXScore()));
        RCAField.setText(String.valueOf(ctTest.getRCAScore()));
        PDAField.setText(String.valueOf(ctTest.getPDAScore()));
    }

    private void clearResults() {
        hideGreeting();
        totalCACField.clear();
        LMField.clear();
        LADField.clear();
        LCXField.clear();
        RCAField.clear();
        PDAField.clear();
    }

    private void showGreeting(PatientRecord patientRecord) {
        greetingLabel.setText("Hello " + patientRecord.getPatientName());
        greetingLabel.setVisible(true);
        greetingLabel.setManaged(true);
    }

    private void hideGreeting() {
        greetingLabel.setText("");
        greetingLabel.setVisible(false);
        greetingLabel.setManaged(false);
    }

    private static TextField readOnlyField() {
        TextField textField = new TextField();
        textField.setEditable(false);
        return textField;
    }
}
