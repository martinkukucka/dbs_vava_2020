import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;

// Trieda Main spusta program
public class Main extends Application {
    // Informacie na pripojenie k databaze
    public static String DBcon = "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static String DBuser = "root";
    public static String DBpassword = "root";

    // Metoda start otvara prvy stage
    @Override
    public void start(Stage primaryStage) throws Exception {
        JavaLogger.init();
        JavaLogger.logger.log(Level.INFO, "Application started successfully");
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        primaryStage.setTitle("Car rental");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}


