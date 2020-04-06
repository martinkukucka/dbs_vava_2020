import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Login {

    public String databaseURL = "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public String databaseName = "root";
    public String databasePassword = "root";

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
    private void loginButtonAction(ActionEvent event) {
        if(emailTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()){
        wrongdataLabel.setText("Vyplňte všetky údaje.");
        wrongdataLabel.setTextFill(Color.RED);}
        else{
                try {
                    Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                    Statement statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery("select email, password from crdb.customer");
                    while (resultSet.next()) {
                        String email = resultSet.getString("email");
                        String password = resultSet.getString("password");
                        if (emailTextField.getText().equals(email) && passwordTextField.getText().equals(password)) {
                            wrongdataLabel.setText("OK");
                            wrongdataLabel.setTextFill(Color.GREEN);
                            return;
                        }
                    }
                    wrongdataLabel.setText("Nesprávny e-mail alebo heslo");
                    wrongdataLabel.setTextFill(Color.RED);
                }
                catch(SQLException e) {
                    System.out.println("SQL exception occured " + e);
                }
            }
    }

    @FXML
    private void backLoginButtonAction(ActionEvent event) throws Exception{
        Stage stage = (Stage) backLoginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/entry.fxml"));
        stage.setScene(new Scene(root, backLoginButton.getScene().getWidth(), backLoginButton.getScene().getHeight()));
    }
}
