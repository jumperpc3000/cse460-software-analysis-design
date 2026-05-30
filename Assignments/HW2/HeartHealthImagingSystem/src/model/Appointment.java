package model;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Handles scheduling of CT scan appointments for patients.
 */
public class Appointment {
    private final String appointmentID;
    private final String patientID;
    private final LocalDate appointmentDate;

    public Appointment(String appointmentID, String patientID, LocalDate appointmentDate) {
        this.appointmentID = PatientRecord.requireText(appointmentID, "Appointment ID");
        this.patientID = PatientRecord.requirePatientID(patientID);
        this.appointmentDate = Objects.requireNonNull(appointmentDate, "Appointment date is required.");
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public Map<String, String> scheduleCTScan() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("appointmentID", appointmentID);
        values.put("patientID", patientID);
        values.put("appointmentDate", appointmentDate.toString());
        return values;
    }

    public String provideAppointmentInfo() {
        return "Appointment " + appointmentID + " for patient " + patientID + " on " + appointmentDate;
    }

    public static Appointment fromMap(Map<String, String> values) {
        Objects.requireNonNull(values, "values is required.");
        return new Appointment(
                values.get("appointmentID"),
                values.get("patientID"),
                LocalDate.parse(values.get("appointmentDate"))
        );
    }
}