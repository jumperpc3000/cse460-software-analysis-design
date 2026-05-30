package model;

import persistence.DataStore;

import java.io.IOException;

/**
 * Boundary/business object for CT scan technician workflows.
 */
public class CTScanTechnician {
    private final DataStore dataStore;

    public CTScanTechnician(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public boolean performCTScan(String patientID) throws IOException {
        PatientRecord.requirePatientID(patientID);
        return dataStore.patientExists(patientID);
    }

    public CTTest recordCACScore(
            String patientID,
            int totalCACScore,
            int LMScore,
            int LADScore,
            int LCXScore,
            int RCAScore,
            int PDAScore
    ) {
        return new CTTest(patientID, totalCACScore, LMScore, LADScore, LCXScore, RCAScore, PDAScore);
    }

    public void saveCTScanResults(CTTest ctTest) throws IOException {
        if (!dataStore.patientExists(ctTest.getPatientID())) {
            throw new IllegalArgumentException("wrong patient ID");
        }
        dataStore.saveCTTest(ctTest);
    }
}