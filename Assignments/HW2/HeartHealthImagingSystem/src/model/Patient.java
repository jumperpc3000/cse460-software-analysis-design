package model;

import persistence.DataStore;

import java.io.IOException;
import java.util.Optional;

/**
 * Boundary/business object for patient-specific behavior.
 */
public class Patient {
    private final DataStore dataStore;

    public Patient(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Optional<CTTest> viewCTScanResults(String patientID) throws IOException {
        PatientRecord.requirePatientID(patientID);
        return dataStore.findCTTest(patientID);
    }

    public boolean login(String patientID) throws IOException {
        return dataStore.patientExists(patientID);
    }
}