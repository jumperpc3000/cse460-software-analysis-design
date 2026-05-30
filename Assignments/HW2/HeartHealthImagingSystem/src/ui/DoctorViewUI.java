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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Doctor/Heart Specialist boundary UI.
 */
public class DoctorViewUI {
    private final Stage stage;
    private final HeartHealthSystem system;
    private final BorderPane root;

    private final TextField patientIDField = new TextField();
    private final TextField totalCACField = readOnlyField();
    private final TextField LMField = readOnlyField();
    private final TextField LADField = readOnlyField();
    private final TextField LCXField = readOnlyField();
    private final TextField RCAField = readOnlyField();
    private final TextField PDAField = readOnlyField();
    private final TextArea riskArea = new TextArea();

    private PatientRecord currentPatientRecord;
    private CTTest currentCTTest;
    private String currentRiskMessage;

    public DoctorViewUI(Stage stage, HeartHealthSystem system) {
        this.stage = stage;
        this.system = system;
        this.root = new BorderPane();
        displayPatientCTResults();
    }

    public Parent getRoot() {
        return root;
    }

    public void displayPatientCTResults() {
        Label title = new Label("Doctor View - Determine Risk");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        riskArea.setEditable(false);
        riskArea.setWrapText(true);
        riskArea.setPrefRowCount(5);

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
        form.addRow(7, new Label("Risk:"), riskArea);

        Button viewButton = new Button("View CT Results and Determine Risk");
        viewButton.setOnAction(event -> enterRiskAssessment());

        Button emailButton = new Button("Email Patient About CT Results/Risk");
        emailButton.setOnAction(event -> emailPatient());

        HBox actions = new HBox(10, viewButton, emailButton, WelcomePageUI.backButton(stage, system));
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

    public void enterRiskAssessment() {
        clearResults();

        try {
            PatientResult result = system.getDoctorResult(patientIDField.getText());

            if (result.getStatus() == PatientResult.Status.WRONG_PATIENT_ID) {
                AlertUtils.warning("Wrong Patient ID", "wrong patient ID");
                return;
            }

            if (result.getStatus() == PatientResult.Status.NO_DATA_AVAILABLE) {
                AlertUtils.info("No Data Available", "No data is available yet.");
                return;
            }

            currentPatientRecord = result.getPatientRecord().orElseThrow();
            currentCTTest = result.getCtTest().orElseThrow();
            currentRiskMessage = result.getRiskMessage().orElse("");

            fillCTFields(currentCTTest);
            riskArea.setText(currentRiskMessage);
        } catch (IllegalArgumentException ex) {
            AlertUtils.warning("Invalid Patient ID", ex.getMessage());
        } catch (IOException ex) {
            AlertUtils.error("File Error", "Could not load doctor view: " + ex.getMessage());
        }
    }

    private void emailPatient() {
        if (currentPatientRecord == null || currentCTTest == null || currentRiskMessage == null) {
            AlertUtils.warning("No Result Loaded", "Load a patient's CT results before emailing the patient.");
            return;
        }

        try {
            system.sendDoctorMessage(currentPatientRecord, currentCTTest, currentRiskMessage);
            AlertUtils.info(
                    "Email Message Saved",
                    "The patient communication was saved as:\n"
                            + system.getDataStore().doctorMessagePath(currentPatientRecord.getPatientID()).toAbsolutePath()
                            + "\n\nThis simulates the email use case without requiring SMTP configuration."
            );
        } catch (IOException ex) {
            AlertUtils.error("File Error", "Could not save patient message: " + ex.getMessage());
        }
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
        currentPatientRecord = null;
        currentCTTest = null;
        currentRiskMessage = null;
        totalCACField.clear();
        LMField.clear();
        LADField.clear();
        LCXField.clear();
        RCAField.clear();
        PDAField.clear();
        riskArea.clear();
    }

    private static TextField readOnlyField() {
        TextField textField = new TextField();
        textField.setEditable(false);
        return textField;
    }
}