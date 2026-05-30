package control;

import model.CTTest;
import model.PatientRecord;

import java.util.Optional;

/**
 * Represents the outcome of a Patient View or Doctor View lookup.
 */
public class PatientResult {
    public enum Status {
        READY,
        NO_DATA_AVAILABLE,
        WRONG_PATIENT_ID
    }

    private final Status status;
    private final PatientRecord patientRecord;
    private final CTTest ctTest;
    private final String riskMessage;

    private PatientResult(Status status, PatientRecord patientRecord, CTTest ctTest, String riskMessage) {
        this.status = status;
        this.patientRecord = patientRecord;
        this.ctTest = ctTest;
        this.riskMessage = riskMessage;
    }

    public static PatientResult ready(PatientRecord patientRecord, CTTest ctTest, String riskMessage) {
        return new PatientResult(Status.READY, patientRecord, ctTest, riskMessage);
    }

    public static PatientResult noData(PatientRecord patientRecord) {
        return new PatientResult(Status.NO_DATA_AVAILABLE, patientRecord, null, null);
    }

    public static PatientResult wrongPatientID() {
        return new PatientResult(Status.WRONG_PATIENT_ID, null, null, null);
    }

    public Status getStatus() {
        return status;
    }

    public Optional<PatientRecord> getPatientRecord() {
        return Optional.ofNullable(patientRecord);
    }

    public Optional<CTTest> getCtTest() {
        return Optional.ofNullable(ctTest);
    }

    public Optional<String> getRiskMessage() {
        return Optional.ofNullable(riskMessage);
    }

    public boolean isReady() {
        return status == Status.READY;
    }
}