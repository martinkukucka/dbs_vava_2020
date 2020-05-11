import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;
import java.util.logging.Level;

// Trieda sluzi k registracii noveho usera
public class Register {

    // Itemy, ktore su potrebne na gui
    public PasswordField passwordTextField11;
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

    // Metoda nahra do regRegionComboBox hodnoty
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

    // Button na vratenie sa na zaciatok
    @FXML
    private void regBackButtonAction() throws Exception {
        Stage stage = (Stage) regBackButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        stage.setScene(new Scene(root, regBackButton.getScene().getWidth(), regBackButton.getScene().getHeight()));
    }

    // Akcia registracie
    @FXML
    private void regRegistrationButtonAction() throws Exception {
        // Ulozenie osobnych udajov usera
        String sqlCustomer = "insert into crdb.customer(name, surname, phonenumber, email, password, idcardnumber, addressid) values (?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
        connection.setAutoCommit(false);
        try {
            Statement statement = connection.createStatement();
            int currentAddressID = 0;

            // Email, ktory pouzivatel zadal uz bol pouzity na registraciu
            ResultSet rs = statement.executeQuery("select * from crdb.customer");
            while (rs.next()) {
                if (rs.getString("email").equals(regEmailTextField.getText())) {
                    regLabel.setText(Login.rb.getString("emailExist"));
                    regLabel.setTextFill(Color.RED);
                    JavaLogger.logger.log(Level.WARNING, "This email already exists");
                    return;
                }
            }

            // Ulozenie adresy do tabuliek region, city a address
            String sqlRegion = "insert ignore into crdb.region(regionname) values (?)";
            PreparedStatement preparedStatementRegion = connection.prepareStatement(sqlRegion);
            preparedStatementRegion.setString(1, regRegionComboBox.getValue());
            preparedStatementRegion.executeUpdate();

            String sqlCity = "insert ignore into crdb.city(cityname, zipcode, regionid) values (?, ?, ?)";
            findId(connection, statement, sqlCity, regCityTextField, regZIPTextField, "select id from crdb.region where regionname = '", regRegionComboBox.getValue(), "'");

            String sqlAddress = "insert into crdb.address(street, housenumber, cityid) values (?, ?, ?)";
            findId(connection, statement, sqlAddress, regStreetTextField, regHouseNumberTextField, "select id from crdb.city where zipcode = ", regZIPTextField.getText(), "");

            rs = statement.executeQuery("select id from crdb.address ORDER BY id DESC LIMIT 1");

            if (rs.next()) {
                currentAddressID = rs.getInt("id");
            }

            PreparedStatement preparedStatementCustomer = connection.prepareStatement(sqlCustomer);
            addToDatabase(currentAddressID, preparedStatementCustomer);
            JavaLogger.logger.log(Level.INFO, "User successfully registered");

        } catch (SQLException e) {
            connection.rollback();
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
        connection.commit();
    }

    // Metoda zisti id z tabulky region podla zadanych udajov
    void findId(Connection connection, Statement statement, String sqlAddress, TextField regStreetTextField, TextField regHouseNumberTextField, String s, String text, String s2) throws SQLException {
        ResultSet rs;
        PreparedStatement preparedStatementAddress = connection.prepareStatement(sqlAddress);
        preparedStatementAddress.setString(1, regStreetTextField.getText());
        preparedStatementAddress.setString(2, regHouseNumberTextField.getText());
        rs = statement.executeQuery(s + text + s2);
        if (rs.next()) {
            preparedStatementAddress.setInt(3, rs.getInt("id"));
        }
        preparedStatementAddress.executeUpdate();
    }

    // Vkladanie vsetkych udajov o pouzivatelovi do tabulky customer
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
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        stage.setScene(new Scene(root, regRegistrationButton.getScene().getWidth(), regRegistrationButton.getScene().getHeight()));
    }

}

