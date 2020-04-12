import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;

public class AddVehicle {

    @FXML
    private TextField licensePlateNumberTextField;

    @FXML
    private TextField colorTextField;

    @FXML
    private TextField yearOfProductionTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private ComboBox<String> modelComboBox;

    @FXML
    private Hyperlink addModelHyperlink;

    @FXML
    private Button addVehicleButton;

    @FXML
    private Button backButton;

    @FXML
    private Label addVehicleLabel;

    @FXML
    public void initialize(){
        try {
            String sqlModel = ("select * from crdb.model");
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            PreparedStatement statement = connection.prepareStatement(sqlModel);
            ResultSet rs = statement.executeQuery("select * from crdb.model");

            while(rs.next()){
                String carInfo = "ID: "+rs.getString("id")+", "+rs.getString("carbrand")
                        +" - "+rs.getString("carmodel")+", "+rs.getString("category")+", "+rs.getString("transmission")
                        +", "+rs.getString("fuel")+", "+rs.getString("kw")
                        +"kW, počet miest na sedenie: "+rs.getString("seats");
                modelComboBox.getItems().add(carInfo);
            }
        }
        catch(SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }
    }

    @FXML
    private void addModelHyperlinkAction(ActionEvent event) throws Exception{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/addmodel.fxml"));
        Scene scene = new Scene(root,800,600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/cardatabase.fxml"));
        stage.setScene(new Scene(root, backButton.getScene().getWidth(), backButton.getScene().getHeight()));
    }

    @FXML
    private void addVehicleButtonAction(ActionEvent event) throws Exception {
        String sqlCustomer = "insert into crdb.vehicle(licenseplatenumber, color, yearofproduction, price, modelid) values (?, ?, ?, ?, ?)";

        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            int currentAddressID = 0;

            ResultSet rs = statement.executeQuery("select * from crdb.vehicle");
            while(rs.next()){
                if(rs.getString("licenseplatenumber").equals(licensePlateNumberTextField.getText())){
                    addVehicleLabel.setText("E-mailová adresa existuje");
                    addVehicleLabel.setTextFill(Color.RED);
                    return;
                }
            }

//            rs = statement.executeQuery("select * from crdb.address");
//            while (rs.next()) {
//                if (rs.getString("licenseplatenumber").equals(licensePlateNumberTextField.getText()) &&
//                        rs.getString("color").equals(colorTextField.getText()) &&
//                        rs.getString("yearofproduction").equals(yearOfProductionTextField.getText()) &&
//                        rs.getString("price").equals(priceTextField.getText())) {
//                    currentAddressID = rs.getInt("id");
//                    PreparedStatement preparedStatementCustomer = connection.prepareStatement(sqlCustomer);
//                    preparedStatementCustomer.setString(1, regNameTextField.getText());
//                    preparedStatementCustomer.setString(2, regSurnameTextField.getText());
//                    preparedStatementCustomer.setString(3, regPhoneNumberTextField.getText());
//                    preparedStatementCustomer.setString(4, regEmailTextField.getText());
//                    preparedStatementCustomer.setString(5, regPasswordTextField.getText());
//                    preparedStatementCustomer.setString(6, regIDTextField.getText());
//                    preparedStatementCustomer.setInt(7, currentAddressID);
//                    preparedStatementCustomer.executeUpdate();
//                    Stage stage = (Stage) regRegistrationButton.getScene().getWindow();
//                    Parent root = FXMLLoader.load(getClass().getResource("GUI/entry.fxml"));
//                    stage.setScene(new Scene(root, regRegistrationButton.getScene().getWidth(), regRegistrationButton.getScene().getHeight()));
//                }
//            }

//            PreparedStatement preparedStatementAddress = connection.prepareStatement(sqlAddress);
//            preparedStatementAddress.setString(1, regRegionComboBox.getValue());
//            preparedStatementAddress.setString(2, regCityTextField.getText());
//            preparedStatementAddress.setString(3, regStreetTextField.getText());
//            preparedStatementAddress.setString(4, regHouseNumberTextField.getText());
//            preparedStatementAddress.setString(5, regZIPTextField.getText());
//            preparedStatementAddress.executeUpdate();
//
//            rs = statement.executeQuery("select id from crdb.address ORDER BY id DESC LIMIT 1");
//
//            if (rs.next()) {
//                currentAddressID = rs.getInt("id");
//            }

            PreparedStatement preparedStatementVehicle = connection.prepareStatement(sqlCustomer);
            preparedStatementVehicle.setString(1, licensePlateNumberTextField.getText());
            preparedStatementVehicle.setString(2, colorTextField.getText());
            preparedStatementVehicle.setString(3, yearOfProductionTextField.getText());
            preparedStatementVehicle.setString(4, priceTextField.getText());
            preparedStatementVehicle.setInt(5, 7);
            preparedStatementVehicle.executeUpdate();


        } catch(SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }

    }
}
