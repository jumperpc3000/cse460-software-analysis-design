package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Encapsulates CT scan data points for a patient.
 */
public class CTTest {
    private final String patientID;
    private final int totalCACScore;
    private final int LMScore;
    private final int LADScore;
    private final int LCXScore;
    private final int RCAScore;
    private final int PDAScore;

    public CTTest(
            String patientID,
            int totalCACScore,
            int LMScore,
            int LADScore,
            int LCXScore,
            int RCAScore,
            int PDAScore
    ) {
        this.patientID = PatientRecord.requirePatientID(patientID);
        this.totalCACScore = requireNonNegative(totalCACScore, "Total Agatston CAC score");
        this.LMScore = requireNonNegative(LMScore, "LM score");
        this.LADScore = requireNonNegative(LADScore, "LAD score");
        this.LCXScore = requireNonNegative(LCXScore, "LCX score");
        this.RCAScore = requireNonNegative(RCAScore, "RCA score");
        this.PDAScore = requireNonNegative(PDAScore, "PDA score");
    }

    public String getPatientID() {
        return patientID;
    }

    public int getTotalCACScore() {
        return totalCACScore;
    }

    public int getLMScore() {
        return LMScore;
    }

    public int getLADScore() {
        return LADScore;
    }

    public int getLCXScore() {
        return LCXScore;
    }

    public int getRCAScore() {
        return RCAScore;
    }

    public int getPDAScore() {
        return PDAScore;
    }

    public Map<String, String> getCACScores() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("patientID", patientID);
        values.put("totalCACScore", String.valueOf(totalCACScore));
        values.put("LMScore", String.valueOf(LMScore));
        values.put("LADScore", String.valueOf(LADScore));
        values.put("LCXScore", String.valueOf(LCXScore));
        values.put("RCAScore", String.valueOf(RCAScore));
        values.put("PDAScore", String.valueOf(PDAScore));
        return values;
    }

    public Map<String, String> storeCTScanData() {
        return getCACScores();
    }

    public static CTTest fromMap(Map<String, String> values) {
        Objects.requireNonNull(values, "values is required.");
        return new CTTest(
                values.get("patientID"),
                parseScore(values, "totalCACScore"),
                parseScore(values, "LMScore"),
                parseScore(values, "LADScore"),
                parseScore(values, "LCXScore"),
                parseScore(values, "RCAScore"),
                parseScore(values, "PDAScore")
        );
    }

    private static int parseScore(Map<String, String> values, String key) {
        try {
            return Integer.parseInt(values.get(key));
        } catch (NumberFormatException | NullPointerException ex) {
            throw new IllegalArgumentException("Invalid CT score value for " + key + ".");
        }
    }

    private static int requireNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
        return value;
    }
}