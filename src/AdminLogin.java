import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.logging.Level;

// Trieda, ktora zobrazi scenu kde musi admin zadat prihlasovacie udaje
public class AdminLogin {

    // Itemy, ktore su potrebne na gui
    public AnchorPane loginAnchorPane;
    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button backLoginButton;

    // Akcia prihlasenia
    @FXML
    private void loginButtonAction() throws IOException {
        String adminPassword = "admin";
        String adminName = "admin";
        if (nameTextField.getText().equals(adminName) && passwordTextField.getText().equals(adminPassword)) {
            JavaLogger.logger.log(Level.INFO, "Admin logged successfully");
            loginButton.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("GUI/adminmenu.fxml"), Login.rb);
            Login.oldstage.setScene(new Scene(root, 1280, 900));
        }
        else {
            JavaLogger.logger.log(Level.WARNING, "Wrong admin data - ACCESS DENIED");
        }
    }

    // Button na zavretie aktualneho okna
    @FXML
    private void backLoginButtonAction() {
        backLoginButton.getScene().getWindow().hide();
    }
}
