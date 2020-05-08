import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static String DBcon = "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static String DBuser = "root";
    public static String DBpassword = "root";


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        primaryStage.setTitle("Car rental");
        primaryStage.setScene(new Scene(root));
//        primaryStage.setResizable(false);
//        primaryStage.setMaximized(true);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }


}


