import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    private Pane profilPane;

    @FXML
    private Label menoLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label pismenoLabel;

    @FXML
    private Label telLabel;

    @FXML
    private Label idCardLabel;

    @FXML
    private Label ulicaLabel;

    @FXML
    private Label cisloDomuLabel;

    @FXML
    private Label mestoLabel;

    @FXML
    private Label pscLabel;

    @FXML
    private Label regionLabel;

    @FXML
    private Pane objednavkaPane;

    @FXML
    private DatePicker pickUpDatepicker;

    @FXML
    private DatePicker returnDatepicker;

    @FXML
    private ComboBox<String> chooseCarCombobox;

    @FXML
    private Button makeOrderButton;

    private CreateOrder createOrder = new CreateOrder();

    @FXML
    public void initialize() {

        createOrder.comboBoxInit(chooseCarCombobox);
    }

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
//        Stage stage = (Stage) createOrderButton.getScene().getWindow();
//        Parent root = FXMLLoader.load(getClass().getResource("GUI/createorder.fxml"));
//        stage.setScene(new Scene(root, createOrderButton.getScene().getWidth(), createOrderButton.getScene().getHeight()));
//        new FadeIn(objednavkaPane).play();
        objednavkaPane.toFront();


    }


    @FXML
    void profilButtonAction(ActionEvent event) {
        String meno = null;
        String priezvisko = null;
        String email = null;
        String telefon = null;
        String idCard = null;
        String ulica = null;
        String cisloDomu = null;
        String mesto = null;
        String psc = null;
        String region = null;

        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from crdb.customer inner join crdb.address on crdb.customer.addressid = crdb.address.id inner join crdb.city on crdb.address.cityid = crdb.city.id inner join crdb.region on crdb.city.regionid = crdb.region.id where crdb.customer.id = "+Login.USERID+"");
            while (resultSet.next()) {
                meno = resultSet.getString("name");
                priezvisko = resultSet.getString("surname");
                email = resultSet.getString("email");
                telefon = resultSet.getString("phonenumber");
                idCard = resultSet.getString("idcardnumber");
                ulica = resultSet.getString("street");
                cisloDomu = resultSet.getString("housenumber");
                mesto = resultSet.getString("cityname");
                psc = resultSet.getString("zipcode");
                region = resultSet.getString("regionname");
            }

        }
        catch(SQLException e) {
            System.out.println("SQL exception occured " + e);
        }

        menoLabel.setText(meno + " " + priezvisko);
        emailLabel.setText(email);
        String firstLetter = meno.substring(0, 1).toUpperCase();
        pismenoLabel.setText(firstLetter);
        telLabel.setText(telefon);
        idCardLabel.setText(idCard);
        ulicaLabel.setText(ulica);
        cisloDomuLabel.setText(cisloDomu);
        mestoLabel.setText(mesto);
        pscLabel.setText(psc);
        regionLabel.setText(region);

//        new FadeIn(profilPane).play();
        profilPane.toFront();

    }

    @FXML
    void makeOrder(ActionEvent event) {
        System.out.println(chooseCarCombobox.getValue());
        createOrder.insertToDb(chooseCarCombobox, pickUpDatepicker, returnDatepicker);
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
