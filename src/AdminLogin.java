import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;

public class AdminLogin {

    public String adminName = "admin";
    public String adminPassword = "admin";

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button backLoginButton;

    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
        if(nameTextField.getText().equals(adminName) && passwordTextField.getText().equals(adminPassword)){
            JavaLogger.logger.log(Level.INFO, "Admin logged successfully");
            loginButton.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("GUI/adminmenu.fxml"), Login.rb);
            Login.oldstage.setScene(new Scene(root, 1280, 900));
        }
        JavaLogger.logger.log(Level.WARNING, "Wrong admin data - ACCESS DENIED");
    }

    @FXML
    private void backLoginButtonAction(ActionEvent event) {
        backLoginButton.getScene().getWindow().hide();
    }
}
