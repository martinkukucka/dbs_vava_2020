import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;

public class CreateOrder {

    @FXML
    private DatePicker pickUpDatepicker;

    @FXML
    private DatePicker returnDatepicker;

    @FXML
    private ComboBox<String> chooseCarCombobox;

    @FXML
    private Button nextButton;

    @FXML
    public void initialize() {
//        chooseCarCombobox.getItems().add("hello");
        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery("select * from crdb.vehicle inner join crdb.model on crdb.vehicle.modelid = crdb.model.id");
            while (resultSet.next()) {
                String chooseCar = (resultSet.getString("licenseplatenumber") + ", " + resultSet.getString("carbrand") +
                        ", " + resultSet.getString("carmodel") + ", " + resultSet.getString("color"));
                chooseCarCombobox.getItems().add(chooseCar);
            }
        }
        catch(SQLException e) {
            System.out.println("SQL exception occured " + e);
        }

    }

    @FXML
    void nextButtonAction(ActionEvent event) {
        String licencePlate = chooseCarCombobox.getValue();
        licencePlate = licencePlate.substring(licencePlate.indexOf(""), licencePlate.indexOf(","));


        String sql = "select * from crdb.vehicle where licenseplatenumber = '" + licencePlate + "'";
        String sqlCarRental = "insert into crdb.carrental(pickupdate, returndate, customerid, vehicleid) values (?, ?, ?, ?)";
        try {
            Connection conn = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement stmts = (Statement) conn.createStatement();
            ResultSet resultSet = stmts.executeQuery(sql);
            int rentedVehicleId = 0;
            while (resultSet.next()) {
                rentedVehicleId = resultSet.getInt("id");
            }
            PreparedStatement preparedStatement = conn.prepareStatement(sqlCarRental);

            preparedStatement.setDate(1, Date.valueOf(pickUpDatepicker.getValue()));
            preparedStatement.setDate(2, Date.valueOf(returnDatepicker.getValue()));
            preparedStatement.setInt(3, Login.USERID);
            preparedStatement.setInt(4, rentedVehicleId);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }

}
