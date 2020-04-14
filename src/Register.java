import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;

public class Register {

    @FXML
    private TextField regNameTextField;

    @FXML
    private TextField regSurnameTextField;

    @FXML
    private TextField regPhoneNumberTextField;

    @FXML
    private TextField regIDTextField;

    @FXML
    private Label regLabel;

    @FXML
    private Button regRegistrationButton;

    @FXML
    private Button regBackButton;

    @FXML
    private TextField regEmailTextField;

    @FXML
    private TextField regPasswordTextField;

    @FXML
    private ComboBox<String> regRegionComboBox;

    @FXML
    private TextField regCityTextField;

    @FXML
    private TextField regStreetTextField;

    @FXML
    private TextField regHouseNumberTextField;

    @FXML
    private TextField regZIPTextField;

    @FXML
    public void initialize() {
        regRegionComboBox.getItems().addAll(
                "Banskobystrický",
                "Bratislavský",
                "Košický",
                "Nitriansky",
                "Prešovský",
                "Trenčiansky",
                "Trnavský",
                "Žilinský"
        );
    }

    @FXML
    private void regBackButtonAction(ActionEvent event) throws Exception{
        Stage stage = (Stage) regBackButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/entry.fxml"));
        stage.setScene(new Scene(root, regBackButton.getScene().getWidth(), regBackButton.getScene().getHeight()));
    }

    @FXML
    private void regRegistrationButtonAction(ActionEvent event) throws Exception {
        String sqlCustomer = "insert into crdb.customer(name, surname, phonenumber, email, password, idcardnumber, addressid) values (?, ?, ?, ?, ?, ?, ?)";
        String sqlAddress = "insert into crdb.address(region, city, street, housenumber, zipcode) values (?, ?, ?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            int currentAddressID = 0;

            ResultSet rs = statement.executeQuery("select * from crdb.customer");
            while(rs.next()){
                if(rs.getString("email").equals(regEmailTextField.getText())){
                    regLabel.setText("E-mailová adresa existuje");
                    regLabel.setTextFill(Color.RED);
                    return;
                }
            }

            rs = statement.executeQuery("select * from crdb.address");
            while (rs.next()) {
                if (rs.getString("region").equals(regRegionComboBox.getValue()) &&
                        rs.getString("city").equals(regCityTextField.getText()) &&
                        rs.getString("street").equals(regStreetTextField.getText()) &&
                        rs.getString("housenumber").equals(regHouseNumberTextField.getText()) &&
                        rs.getString("zipcode").equals(regZIPTextField.getText())) {
                    currentAddressID = rs.getInt("id");
                    PreparedStatement preparedStatementCustomer = connection.prepareStatement(sqlCustomer);
                    addToDatabase(currentAddressID, preparedStatementCustomer);
                    return;
                }
            }

            PreparedStatement preparedStatementAddress = connection.prepareStatement(sqlAddress);
            preparedStatementAddress.setString(1, regRegionComboBox.getValue());
            preparedStatementAddress.setString(2, regCityTextField.getText());
            preparedStatementAddress.setString(3, regStreetTextField.getText());
            preparedStatementAddress.setString(4, regHouseNumberTextField.getText());
            preparedStatementAddress.setString(5, regZIPTextField.getText());
            preparedStatementAddress.executeUpdate();

            rs = statement.executeQuery("select id from crdb.address ORDER BY id DESC LIMIT 1");

            if (rs.next()) {
                currentAddressID = rs.getInt("id");
            }

            PreparedStatement preparedStatementCustomer = connection.prepareStatement(sqlCustomer);
            addToDatabase(currentAddressID, preparedStatementCustomer);

        } catch(SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }

    }

    private void addToDatabase(int currentAddressID, PreparedStatement preparedStatementCustomer) throws SQLException, java.io.IOException {
        preparedStatementCustomer.setString(1, regNameTextField.getText());
        preparedStatementCustomer.setString(2, regSurnameTextField.getText());
        preparedStatementCustomer.setString(3, regPhoneNumberTextField.getText());
        preparedStatementCustomer.setString(4, regEmailTextField.getText());
        preparedStatementCustomer.setString(5, regPasswordTextField.getText());
        preparedStatementCustomer.setString(6, regIDTextField.getText());
        preparedStatementCustomer.setInt(7, currentAddressID);
        preparedStatementCustomer.executeUpdate();
        Stage stage = (Stage) regRegistrationButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/entry.fxml"));
        stage.setScene(new Scene(root, regRegistrationButton.getScene().getWidth(), regRegistrationButton.getScene().getHeight()));
    }

}
