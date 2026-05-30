package model;

import persistence.DataStore;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Boundary/business object for doctor/heart specialist workflows.
 */
public class HeartSpecialist {
    private static final DateTimeFormatter MESSAGE_TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final DataStore dataStore;

    public HeartSpecialist(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Optional<CTTest> reviewCTScanResults(String patientID) throws IOException {
        PatientRecord.requirePatientID(patientID);
        return dataStore.findCTTest(patientID);
    }

    public String determineRisk(CTTest ctTest) {
        return determineRisk(ctTest.getTotalCACScore());
    }

    public String determineRisk(int agatstonCACScore) {
        if (agatstonCACScore == 0) {
            return "Zero: No plaque. Your risk of heart attack is low.";
        }
        if (agatstonCACScore >= 1 && agatstonCACScore <= 10) {
            return "1 - 10: Small amount of plaque. You have less than a 10 percent chance of having heart disease, and your risk of heart attack is low.";
        }
        if (agatstonCACScore >= 11 && agatstonCACScore <= 100) {
            return "11 - 100: Some plaque. You have mild heart disease and a moderate chance of heart attack. Your doctor may recommend other treatment in addition to lifestyle changes.";
        }
        if (agatstonCACScore >= 101 && agatstonCACScore <= 400) {
            return "101 - 400: Moderate amount of plaque. You have heart disease and plaque may be blocking an artery. Your chance of having a heart attack is moderate to high. Your health professional may want more tests and may start treatment.";
        }
        return "Over 400: Large amount of plaque. You have more than a 90 percent chance that plaque is blocking one of your arteries. Your chance of heart attack is high. Your health professional will want more tests and will start treatment.";
    }

    /**
     * Simulates the required patient email by writing the message to a text file.
     */
    public void communicateResultsToPatient(PatientRecord patientRecord, CTTest ctTest, String riskMessage) throws IOException {
        String message = """
                To: %s
                Subject: CT Scan Results and Risk Assessment

                Hello %s,

                Your CT scan results are ready.

                Total Agatston CAC score: %d
                LM: %d
                LAD: %d
                LCX: %d
                RCA: %d
                PDA: %d

                Risk Assessment:
                %s

                Sent by Heart Specialist at %s
                """.formatted(
                patientRecord.getEmail(),
                patientRecord.getPatientName(),
                ctTest.getTotalCACScore(),
                ctTest.getLMScore(),
                ctTest.getLADScore(),
                ctTest.getLCXScore(),
                ctTest.getRCAScore(),
                ctTest.getPDAScore(),
                riskMessage,
                LocalDateTime.now().format(MESSAGE_TIMESTAMP_FORMATTER)
        );

        dataStore.saveDoctorMessage(patientRecord.getPatientID(), message);
    }
}
