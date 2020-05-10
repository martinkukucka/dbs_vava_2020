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
import java.util.ResourceBundle;
import java.util.logging.Level;

// Trieda riadi uvodnu stranku programu a vsetky akcie s nou spojenou
public class Login {

    // Atribudy stagu
    public static Stage stage = new Stage();
    static Stage oldstage = new Stage();
    static double oldwitdh;
    static double oldheight;
    // Id prihlaseneho pouzivatela, ktore je potrebne dalej v programe
    static int USERID;

    // Itemy, ktore su potrebne na gui
    public AnchorPane loginAnchorPane;
    public Hyperlink adminHyperlink;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label wrongdataLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink createAccountHyperlink;

    @FXML
    private Button slovakLanguageButton;

    @FXML
    private Button englishLanguageButton;

    // Defaultny jazyk je slovensky
    static ResourceBundle rb = ResourceBundle.getBundle("Language/resource_bundle_sk_SK");

    // Zvolenie slovenskeho jazyka
    @FXML
    private void selectLanguageSK() throws IOException {
        JavaLogger.logger.log(Level.INFO, "User selected slovak language");
        rb = ResourceBundle.getBundle("Language/resource_bundle_sk_SK");

        Stage stage = (Stage) slovakLanguageButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        stage.setScene(new Scene(root, slovakLanguageButton.getScene().getWidth(), slovakLanguageButton.getScene().getHeight()));
    }

    // Zvolenie anglickeho jazyka
    @FXML
    private void selectLanguageEN() throws IOException {
        JavaLogger.logger.log(Level.INFO, "User selected english language");
        rb = ResourceBundle.getBundle("Language/resource_bundle_en_EN");

        Stage stage = (Stage) englishLanguageButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        stage.setScene(new Scene(root, englishLanguageButton.getScene().getWidth(), englishLanguageButton.getScene().getHeight()));
    }

    // Akcia prihlasenia
    @FXML
    private void loginButtonAction() throws Exception {

        // Ak nie su vyplnene vsetky udaje
        if (emailTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            wrongdataLabel.setText(rb.getString("missingInfo"));
            wrongdataLabel.setTextFill(Color.RED);
        } else {
            // Prebehne prihlasenie pouzivatela podla jeho prihlasovacich udajov v databaze
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
                // Zle zadane udaje
                JavaLogger.logger.log(Level.INFO, "User entered wrong email or password");
                wrongdataLabel.setText(rb.getString("wrongInfo"));
                wrongdataLabel.setTextFill(Color.RED);
            } catch (SQLException e) {
                JavaLogger.logger.log(Level.WARNING, "Database problem");
                System.out.println("SQL exception occured " + e);
            }
        }
    }

    // Link k spravcovskej casti
    @FXML
    private void adminHyperlinkAction() throws Exception {
        oldstage = (Stage) loginButton.getScene().getWindow();
        oldwitdh = loginButton.getScene().getWidth();
        oldheight = loginButton.getScene().getHeight();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/adminlogin.fxml"), Login.rb);
        Scene scene = new Scene(root, 350, 400);
        stage.setScene(scene);
        stage.show();
    }

    // Link k registracii pouzivatela
    @FXML
    void createAccountHyperlinkAction() throws IOException {
        Stage stage = (Stage) createAccountHyperlink.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/register.fxml"), Login.rb);
        stage.setScene(new Scene(root, createAccountHyperlink.getScene().getWidth(), createAccountHyperlink.getScene().getHeight()));
    }
}
