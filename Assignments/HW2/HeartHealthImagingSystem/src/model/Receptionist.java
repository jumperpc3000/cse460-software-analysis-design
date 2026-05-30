package model;

import persistence.DataStore;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Boundary/business object for receptionist workflows.
 */
public class Receptionist {
    private final DataStore dataStore;

    public Receptionist(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public PatientRecord inputPatientInformation(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String healthHistory,
            String insuranceID
    ) throws IOException {
        String patientID = generatePatientID();
        PatientRecord record = new PatientRecord(
                patientID,
                firstName,
                lastName,
                email,
                phoneNumber,
                healthHistory,
                insuranceID
        );
        dataStore.savePatientRecord(record);
        return record;
    }

    public Appointment scheduleExam(String patientID, LocalDate examDate) throws IOException {
        PatientRecord.requirePatientID(patientID);
        if (!dataStore.patientExists(patientID)) {
            throw new IllegalArgumentException("wrong patient ID");
        }
        Appointment appointment = new Appointment("APT-" + patientID, patientID, examDate);
        dataStore.saveAppointment(appointment);
        return appointment;
    }

    public String generatePatientID() throws IOException {
        return dataStore.generateUniquePatientID();
    }
}