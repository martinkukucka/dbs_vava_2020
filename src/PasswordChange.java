import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;

// ?
public class PasswordChange {

    @FXML
    private TextField oldPasswordTextField;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    private PasswordField confirmNewPasswordTextField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button backButton;

    @FXML
    void backButtonAction() throws Exception {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/customermenu.fxml"));
        stage.setScene(new Scene(root, backButton.getScene().getWidth(), backButton.getScene().getHeight()));
    }

    @FXML
    void changeButtonAction() {
        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id, password from crdb.customer");

            if (confirmNewPasswordTextField.getText().isEmpty() || newPasswordTextField.getText().isEmpty() || oldPasswordTextField.getText().isEmpty()) {
                passwordLabel.setText("Vyplňte všetky údaje");
                passwordLabel.setTextFill(Color.RED);
            } else {
                while (resultSet.next()) {
                    if (resultSet.getInt("id") == Login.USERID) {
                        if (resultSet.getString("password").equals(oldPasswordTextField.getText())) {
                            if (confirmNewPasswordTextField.getText().equals(newPasswordTextField.getText())) {
                                String sql = "update crdb.customer SET password = ? where id = " + Login.USERID + "";
                                PreparedStatement rs = connection.prepareStatement(sql);
                                rs.setString(1, newPasswordTextField.getText());
                                rs.executeUpdate();
                                passwordLabel.setText("Heslo úspešne zmenené");
                                passwordLabel.setTextFill(Color.GREEN);
                                return;
                            }
                            passwordLabel.setText("Zlé potvrdenie hesla");
                            passwordLabel.setTextFill(Color.RED);
                            return;
                        }
                        passwordLabel.setText("Pôvodné heslo sa nezhoduje");
                        passwordLabel.setTextFill(Color.RED);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }
    }

}
