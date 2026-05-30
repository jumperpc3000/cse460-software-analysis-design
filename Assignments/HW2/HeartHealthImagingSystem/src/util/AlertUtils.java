package util;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

/**
 * Helper for consistent alerts.
 */
public final class AlertUtils {
    private AlertUtils() {
    }

    public static void info(String title, String message) {
        show(Alert.AlertType.INFORMATION, title, message);
    }

    public static void warning(String title, String message) {
        show(Alert.AlertType.WARNING, title, message);
    }

    public static void error(String title, String message) {
        show(Alert.AlertType.ERROR, title, message);
    }

    public static TextArea readOnlyTextArea(String text, int rows) {
        TextArea textArea = new TextArea(text == null ? "" : text);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(rows);
        return textArea;
    }

    private static void show(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}