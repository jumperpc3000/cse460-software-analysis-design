package model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Patient information file linked to a unique 5-digit patient ID.
 */
public class PatientRecord {
    private final String patientID;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String healthHistory;
    private final String insuranceID;

    public PatientRecord(
            String patientID,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String healthHistory,
            String insuranceID
    ) {
        this.patientID = requirePatientID(patientID);
        this.firstName = requireText(firstName, "First name");
        this.lastName = requireText(lastName, "Last name");
        this.email = requireText(email, "Email");
        this.phoneNumber = requireText(phoneNumber, "Phone number");
        this.healthHistory = requireText(healthHistory, "Health history");
        this.insuranceID = requireText(insuranceID, "Insurance ID");
    }

    public String getPatientID() {
        return patientID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatientName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHealthHistory() {
        return healthHistory;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    /**
     * Converts the patient record to a key-value format for the text file.
     */
    public Map<String, String> storePatientInformation() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("patientID", patientID);
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        values.put("email", email);
        values.put("phoneNumber", phoneNumber);
        values.put("healthHistory", healthHistory);
        values.put("insuranceID", insuranceID);
        return values;
    }

    public String getPatientInformation() {
        return """
                Patient ID: %s
                Name: %s
                Email: %s
                Phone: %s
                Health History: %s
                Insurance ID: %s
                """.formatted(patientID, getPatientName(), email, phoneNumber, healthHistory, insuranceID);
    }

    public static PatientRecord fromMap(Map<String, String> values) {
        Objects.requireNonNull(values, "values is required.");
        return new PatientRecord(
                values.get("patientID"),
                values.get("firstName"),
                values.get("lastName"),
                values.get("email"),
                values.get("phoneNumber"),
                values.get("healthHistory"),
                values.get("insuranceID")
        );
    }

    public static String requirePatientID(String patientID) {
        String value = requireText(patientID, "Patient ID");
        if (!value.matches("\\d{5}")) {
            throw new IllegalArgumentException("Patient ID must be a 5-digit number.");
        }
        return value;
    }

    public static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        return value.trim();
    }
}