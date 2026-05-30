import control.HeartHealthSystem;
import persistence.DataStore;
import ui.LoginUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Path;

/**
 * Entry point for the Heart Health Imaging and Recording System.
 */
public class Main extends Application {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 650;

    @Override
    public void start(Stage primaryStage) {
        DataStore dataStore = new DataStore(Path.of("data"));
        HeartHealthSystem system = new HeartHealthSystem(dataStore);

        LoginUI loginUI = new LoginUI(primaryStage, system);
        Scene scene = new Scene(loginUI.getRoot(), WINDOW_WIDTH, WINDOW_HEIGHT);

        primaryStage.setTitle("Heart Health Imaging and Recording System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(760);
        primaryStage.setMinHeight(540);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
