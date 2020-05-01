import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class CustomerMenu {

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private Button createOrderButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button backButton;

    @FXML
    private Button profilButton;

    @FXML
    private Label menoLabel;

    @FXML
    private Label priezviskoLabel;

    @FXML
    private Pane profilPane;

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/entry.fxml"));
        stage.setScene(new Scene(root, backButton.getScene().getWidth(), backButton.getScene().getHeight()));
    }

    @FXML
    void changeButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) changePasswordButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/passwordchange.fxml"));
        stage.setScene(new Scene(root, changePasswordButton.getScene().getWidth(), changePasswordButton.getScene().getHeight()));
    }

    @FXML
    void creteOrderButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) createOrderButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/createorder.fxml"));
        stage.setScene(new Scene(root, createOrderButton.getScene().getWidth(), createOrderButton.getScene().getHeight()));
    }


    @FXML
    void profilButtonAction(ActionEvent event) {
        String meno = null;
        String priezvisko = null;

        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select name, surname from crdb.customer where id = "+Login.USERID+"");
            while (resultSet.next()) {
                meno = resultSet.getString("name");
                priezvisko = resultSet.getString("surname");

            }

        }
        catch(SQLException e) {
            System.out.println("SQL exception occured " + e);
        }

        menoLabel.setText(meno);
        menoLabel.setTextFill(Color.RED);
        priezviskoLabel.setText(priezvisko);
        priezviskoLabel.setTextFill(Color.RED);
        profilPane.toFront();

    }


    @FXML
    void enterButton(MouseEvent event) {
        if (event.getSource() == createOrderButton) {
            createOrderButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == changePasswordButton) {
            changePasswordButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == backButton) {
            backButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == profilButton) {
            profilButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
    }

    @FXML
    void exitButton(MouseEvent event) {
        if (event.getSource() == createOrderButton) {
            createOrderButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == changePasswordButton) {
            changePasswordButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == backButton) {
            backButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == profilButton) {
            profilButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
    }

}
