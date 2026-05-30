package ui;

import control.HeartHealthSystem;
import model.CTTest;
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
 * CT Scan Technician boundary UI.
 */
public class CTScanTechViewUI {
    private final Stage stage;
    private final HeartHealthSystem system;
    private final BorderPane root;

    private final TextField patientIDField = new TextField();
    private final TextField totalCACField = new TextField();
    private final TextField LMField = new TextField();
    private final TextField LADField = new TextField();
    private final TextField LCXField = new TextField();
    private final TextField RCAField = new TextField();
    private final TextField PDAField = new TextField();

    public CTScanTechViewUI(Stage stage, HeartHealthSystem system) {
        this.stage = stage;
        this.system = system;
        this.root = new BorderPane();
        displayCTScanForm();
    }

    public Parent getRoot() {
        return root;
    }

    public void displayCTScanForm() {
        Label title = new Label("CT Scan Technician View");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(12);
        form.addRow(0, new Label("Patient ID:"), patientIDField);
        form.addRow(1, new Label("The total Agatston CAC score:"), totalCACField);
        form.addRow(2, new Label("LM:"), LMField);
        form.addRow(3, new Label("LAD:"), LADField);
        form.addRow(4, new Label("LCX:"), LCXField);
        form.addRow(5, new Label("RCA:"), RCAField);
        form.addRow(6, new Label("PDA:"), PDAField);

        Button autoTotalButton = new Button("Auto Calculate Total From Vessel Scores");
        autoTotalButton.setOnAction(event -> calculateTotalCACScore());

        Button saveButton = new Button("Save CT Scan Results");
        saveButton.setOnAction(event -> submitCTScanResults());

        HBox actions = new HBox(10, saveButton, autoTotalButton, WelcomePageUI.backButton(stage, system));
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

    private void calculateTotalCACScore() {
        try {
            int totalScore = parseScore(LMField.getText(), "LM")
                    + parseScore(LADField.getText(), "LAD")
                    + parseScore(LCXField.getText(), "LCX")
                    + parseScore(RCAField.getText(), "RCA")
                    + parseScore(PDAField.getText(), "PDA");
            totalCACField.setText(String.valueOf(totalScore));
        } catch (IllegalArgumentException ex) {
            AlertUtils.warning("Invalid CT Scan Form", ex.getMessage());
        }
    }

    public void submitCTScanResults() {
        try {
            CTTest ctTest = system.recordCTScan(
                    patientIDField.getText(),
                    parseScore(totalCACField.getText(), "Total CAC"),
                    parseScore(LMField.getText(), "LM"),
                    parseScore(LADField.getText(), "LAD"),
                    parseScore(LCXField.getText(), "LCX"),
                    parseScore(RCAField.getText(), "RCA"),
                    parseScore(PDAField.getText(), "PDA")
            );

            AlertUtils.info(
                    "CT Results Saved",
                    "CT scan results saved for patient " + ctTest.getPatientID()
                            + ".\n\nResults file: " + system.getDataStore().ctResultsPath(ctTest.getPatientID()).toAbsolutePath()
            );
        } catch (IllegalArgumentException ex) {
            AlertUtils.warning("Invalid CT Scan Form", ex.getMessage());
        } catch (IOException ex) {
            AlertUtils.error("File Error", "Could not save CT scan results: " + ex.getMessage());
        }
    }

    private static int parseScore(String rawValue, String fieldName) {
        try {
            int value = Integer.parseInt(rawValue.trim());
            if (value < 0) {
                throw new IllegalArgumentException(fieldName + " score cannot be negative.");
            }
            return value;
        } catch (NullPointerException | NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + " score must be a whole number.");
        }
    }
}
