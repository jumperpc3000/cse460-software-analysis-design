package control;

import model.Appointment;
import model.CTScanTechnician;
import model.CTTest;
import model.HeartSpecialist;
import model.Patient;
import model.PatientRecord;
import model.Receptionist;
import model.Role;
import model.User;
import persistence.DataStore;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Application service/controller that coordinates UI requests and domain objects.
 */
public class HeartHealthSystem {
    private final DataStore dataStore;
    private final Receptionist receptionist;
    private final CTScanTechnician technician;
    private final Patient patient;
    private final HeartSpecialist heartSpecialist;
    private final Login login;
    private User currentUser;

    public HeartHealthSystem(DataStore dataStore) {
        this.dataStore = dataStore;
        this.receptionist = new Receptionist(dataStore);
        this.technician = new CTScanTechnician(dataStore);
        this.patient = new Patient(dataStore);
        this.heartSpecialist = new HeartSpecialist(dataStore);
        this.login = new Login();
    }

    public Optional<User> authenticateUser(String userID, String password) throws IOException {
        Optional<User> authenticatedUser = login.authenticateUser(userID, password);
        if (authenticatedUser.isEmpty()) {
            authenticatedUser = authenticatePatientByID(userID, password);
        }
        authenticatedUser.ifPresent(user -> currentUser = user);
        return authenticatedUser;
    }

    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    public boolean canAccess(Role requiredRole) {
        return login.manageAccessControl(currentUser, requiredRole);
    }

    public void logout() {
        currentUser = null;
    }

    public PatientRecord intakePatient(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String healthHistory,
            String insuranceID,
            LocalDate examDate
    ) throws IOException {
        PatientRecord record = receptionist.inputPatientInformation(
                firstName,
                lastName,
                email,
                phoneNumber,
                healthHistory,
                insuranceID
        );
        receptionist.scheduleExam(record.getPatientID(), examDate);
        return record;
    }

    public CTTest recordCTScan(
            String patientID,
            int totalCACScore,
            int LMScore,
            int LADScore,
            int LCXScore,
            int RCAScore,
            int PDAScore
    ) throws IOException {
        if (!technician.performCTScan(patientID)) {
            throw new IllegalArgumentException("wrong patient ID");
        }
        CTTest ctTest = technician.recordCACScore(patientID, totalCACScore, LMScore, LADScore, LCXScore, RCAScore, PDAScore);
        technician.saveCTScanResults(ctTest);
        return ctTest;
    }

    public PatientResult getPatientResult(String patientID) throws IOException {
        PatientRecord.requirePatientID(patientID);
        Optional<PatientRecord> patientRecord = dataStore.findPatientRecord(patientID);
        if (patientRecord.isEmpty()) {
            return PatientResult.wrongPatientID();
        }

        Optional<CTTest> ctTest = patient.viewCTScanResults(patientID);
        if (ctTest.isEmpty()) {
            return PatientResult.noData(patientRecord.get());
        }

        return PatientResult.ready(patientRecord.get(), ctTest.get(), null);
    }

    public PatientResult getDoctorResult(String patientID) throws IOException {
        PatientRecord.requirePatientID(patientID);
        Optional<PatientRecord> patientRecord = dataStore.findPatientRecord(patientID);
        if (patientRecord.isEmpty()) {
            return PatientResult.wrongPatientID();
        }

        Optional<CTTest> ctTest = heartSpecialist.reviewCTScanResults(patientID);
        if (ctTest.isEmpty()) {
            return PatientResult.noData(patientRecord.get());
        }

        String risk = heartSpecialist.determineRisk(ctTest.get());
        return PatientResult.ready(patientRecord.get(), ctTest.get(), risk);
    }

    public void sendDoctorMessage(PatientRecord patientRecord, CTTest ctTest, String riskMessage) throws IOException {
        heartSpecialist.communicateResultsToPatient(patientRecord, ctTest, riskMessage);
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public Login getLogin() {
        return login;
    }

    public Optional<Appointment> findAppointment(String patientID) throws IOException {
        return dataStore.findAppointment(patientID);
    }

    private Optional<User> authenticatePatientByID(String userID, String password) throws IOException {
        String patientID = nullSafe(userID);
        if (!nullSafe(password).isBlank() || !patientID.matches("\\d{5}") || !patient.login(patientID)) {
            return Optional.empty();
        }
        return Optional.of(new User(patientID, patientID, Role.PATIENT));
    }

    private static String nullSafe(String value) {
        return value == null ? "" : value.trim();
    }
}
