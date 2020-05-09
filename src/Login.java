import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class Login {

    public static Stage stage = new Stage();
    public static Stage oldstage = new Stage();
    public static double oldwitdh;
    public static double oldheight;
    public String databaseURL = "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public String databaseName = "root";
    public String databasePassword = "root";
    public static int USERID;

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label wrongdataLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button backLoginButton;

    @FXML
    private Hyperlink adminHyperlink;

    @FXML
    private Hyperlink createAccountHyperlink;

    @FXML
    private Button slovakLanguageButton;

    @FXML
    private Button englishLanguageButton;

    public static ResourceBundle rb = ResourceBundle.getBundle("Language/resource_bundle_sk_SK");

    @FXML
    private void selectLanguageSK() throws IOException {
        JavaLogger.logger.log(Level.INFO, "User selected slovak language");
        rb = ResourceBundle.getBundle("Language/resource_bundle_sk_SK");

        Stage stage = (Stage) slovakLanguageButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        stage.setScene(new Scene(root, slovakLanguageButton.getScene().getWidth(), slovakLanguageButton.getScene().getHeight()));
    }

    @FXML
    private void selectLanguageEN() throws IOException {
        JavaLogger.logger.log(Level.INFO, "User selected english language");
        rb = ResourceBundle.getBundle("Language/resource_bundle_en_EN");

        Stage stage = (Stage) englishLanguageButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        stage.setScene(new Scene(root, englishLanguageButton.getScene().getWidth(), englishLanguageButton.getScene().getHeight()));
    }

    @FXML
    private void loginButtonAction(ActionEvent event) throws Exception {

        if(emailTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
        wrongdataLabel.setText(rb.getString("missingInfo"));
        wrongdataLabel.setTextFill(Color.RED);
        }
        else {
                try {
                    Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                    Statement statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery("select id, email, password from crdb.customer");
                    while (resultSet.next()) {
                        String email = resultSet.getString("email");
                        String password = resultSet.getString("password");
                        if (emailTextField.getText().equals(email) && passwordTextField.getText().equals(password)) {
                            USERID = resultSet.getInt("id");
                            Stage stage = (Stage) loginButton.getScene().getWindow();
                            JavaLogger.logger.log(Level.INFO, "User logged in successfully");
                            Parent root = FXMLLoader.load(getClass().getResource("GUI/customermenu.fxml"), Login.rb);
                            stage.setScene(new Scene(root, loginButton.getScene().getWidth(), loginButton.getScene().getHeight()));
                            return;
                        }
                    }
                    JavaLogger.logger.log(Level.INFO, "User entered wrong email or password");
                    wrongdataLabel.setText(rb.getString("wrongInfo"));
                    wrongdataLabel.setTextFill(Color.RED);
                }
                catch(SQLException e) {
                    JavaLogger.logger.log(Level.WARNING, "Database problem");
                    System.out.println("SQL exception occured " + e);
                }
            }
    }

    @FXML
    private void adminHyperlinkAction(ActionEvent event) throws Exception{
        oldstage = (Stage) loginButton.getScene().getWindow();
        oldwitdh = loginButton.getScene().getWidth();
        oldheight = loginButton.getScene().getHeight();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/adminlogin.fxml"), Login.rb);
        Scene scene = new Scene(root,350,400);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void createAccountHyperlinkAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) createAccountHyperlink.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/register.fxml"), Login.rb);
        stage.setScene(new Scene(root, createAccountHyperlink.getScene().getWidth(), createAccountHyperlink.getScene().getHeight()));
    }
}
