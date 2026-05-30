package persistence;

import model.Appointment;
import model.CTTest;
import model.PatientRecord;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Text-file persistence layer.
 *
 * Required output files:
 *   <patientID>_PatientInfo.txt
 *   <patientID>_CTResults.txt
 *
 * Additional implementation files:
 *   <patientID>_Appointment.txt
 *   <patientID>_DoctorMessage.txt
 */
public class DataStore {
    private static final int MIN_PATIENT_ID = 10000;
    private static final int MAX_PATIENT_ID = 99999;

    private final Path dataDirectory;
    private final SecureRandom secureRandom = new SecureRandom();

    public DataStore(Path dataDirectory) {
        this.dataDirectory = dataDirectory;
        ensureDataDirectory();
    }

    public String generateUniquePatientID() throws IOException {
        ensureDataDirectory();

        for (int attempt = 0; attempt < 100_000; attempt++) {
            int value = MIN_PATIENT_ID + secureRandom.nextInt(MAX_PATIENT_ID - MIN_PATIENT_ID + 1);
            String patientID = String.valueOf(value);
            if (!patientExists(patientID)) {
                return patientID;
            }
        }

        throw new IOException("Unable to generate a unique 5-digit patient ID.");
    }

    public void savePatientRecord(PatientRecord record) throws IOException {
        ensureDataDirectory();
        writeKeyValueFile(patientInfoPath(record.getPatientID()), record.storePatientInformation());
    }

    public Optional<PatientRecord> findPatientRecord(String patientID) throws IOException {
        PatientRecord.requirePatientID(patientID);
        Path path = patientInfoPath(patientID);
        if (!Files.exists(path)) {
            return Optional.empty();
        }
        return Optional.of(PatientRecord.fromMap(readKeyValueFile(path)));
    }

    public boolean patientExists(String patientID) throws IOException {
        try {
            PatientRecord.requirePatientID(patientID);
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return Files.exists(patientInfoPath(patientID));
    }

    public void saveAppointment(Appointment appointment) throws IOException {
        ensureDataDirectory();
        writeKeyValueFile(appointmentPath(appointment.getPatientID()), appointment.scheduleCTScan());
    }

    public Optional<Appointment> findAppointment(String patientID) throws IOException {
        PatientRecord.requirePatientID(patientID);
        Path path = appointmentPath(patientID);
        if (!Files.exists(path)) {
            return Optional.empty();
        }
        return Optional.of(Appointment.fromMap(readKeyValueFile(path)));
    }

    public void saveCTTest(CTTest ctTest) throws IOException {
        ensureDataDirectory();
        writeKeyValueFile(ctResultsPath(ctTest.getPatientID()), ctTest.storeCTScanData());
    }

    public Optional<CTTest> findCTTest(String patientID) throws IOException {
        PatientRecord.requirePatientID(patientID);
        Path path = ctResultsPath(patientID);
        if (!Files.exists(path)) {
            return Optional.empty();
        }
        return Optional.of(CTTest.fromMap(readKeyValueFile(path)));
    }

    public void saveDoctorMessage(String patientID, String message) throws IOException {
        PatientRecord.requirePatientID(patientID);
        ensureDataDirectory();
        Files.writeString(doctorMessagePath(patientID), message, StandardCharsets.UTF_8);
    }

    public Path patientInfoPath(String patientID) {
        return dataDirectory.resolve(patientID + "_PatientInfo.txt");
    }

    public Path ctResultsPath(String patientID) {
        return dataDirectory.resolve(patientID + "_CTResults.txt");
    }

    public Path appointmentPath(String patientID) {
        return dataDirectory.resolve(patientID + "_Appointment.txt");
    }

    public Path doctorMessagePath(String patientID) {
        return dataDirectory.resolve(patientID + "_DoctorMessage.txt");
    }

    public Path getDataDirectory() {
        return dataDirectory;
    }

    private void ensureDataDirectory() {
        try {
            Files.createDirectories(dataDirectory);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not create data directory: " + dataDirectory, ex);
        }
    }

    private static void writeKeyValueFile(Path path, Map<String, String> values) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            builder.append(entry.getKey())
                    .append("=")
                    .append(escape(entry.getValue()))
                    .append(System.lineSeparator());
        }
        Files.writeString(path, builder.toString(), StandardCharsets.UTF_8);
    }

    private static Map<String, String> readKeyValueFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        Map<String, String> values = new LinkedHashMap<>();

        for (String line : lines) {
            if (line.isBlank() || line.stripLeading().startsWith("#")) {
                continue;
            }
            int delimiter = line.indexOf('=');
            if (delimiter <= 0) {
                throw new IOException("Invalid data line in " + path + ": " + line);
            }
            String key = line.substring(0, delimiter).trim();
            String value = unescape(line.substring(delimiter + 1));
            values.put(key, value);
        }

        return values;
    }

    private static String escape(String value) {
        return value == null ? "" : value.replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
    }

    private static String unescape(String value) {
        StringBuilder builder = new StringBuilder();
        boolean escaping = false;

        for (int index = 0; index < value.length(); index++) {
            char current = value.charAt(index);

            if (escaping) {
                if (current == 'n') {
                    builder.append('\n');
                } else if (current == 'r') {
                    builder.append('\r');
                } else {
                    builder.append(current);
                }
                escaping = false;
            } else if (current == '\\') {
                escaping = true;
            } else {
                builder.append(current);
            }
        }

        if (escaping) {
            builder.append('\\');
        }

        return builder.toString();
    }
}