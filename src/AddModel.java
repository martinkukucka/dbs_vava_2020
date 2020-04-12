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

public class AddModel {

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField brandTextField;

    @FXML
    private TextField modelTextField;

    @FXML
    private ComboBox<String> transmissionComboBox;

    @FXML
    private ComboBox<String> fuelComboBox;

    @FXML
    private TextField kwTextField;

    @FXML
    private TextField seatsTextField;

    @FXML
    private Label addModelLabel;

    @FXML
    private Button addModelButton;

    @FXML
    private Button cancelButton;

    @FXML
    public void initialize() {
        categoryComboBox.getItems().addAll(
                "Hatchback",
                "Sedan",
                "Combi",
                "Coupe",
                "MPV",
                "Dodávka"
        );

        fuelComboBox.getItems().addAll(
                "Benzín",
                "Diesel",
                "Hybrid",
                "Elektro"
        );

        transmissionComboBox.getItems().addAll(
                "Manuál",
                "Automat"
        );
    }

    @FXML
    void cancelButtonAction(ActionEvent event) {
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    private void addModelButtonAction(ActionEvent event) throws Exception {
        boolean modelExist = false;
        String sqlModel = "insert into crdb.model(category, carbrand, carmodel, transmission, fuel, kw, seats) values (?, ?, ?, ?, ?, ?, ?)";

        if(categoryComboBox.getSelectionModel().isEmpty() || brandTextField.getText().isEmpty()
                || modelTextField.getText().isEmpty() || transmissionComboBox.getSelectionModel().isEmpty()
                || fuelComboBox.getSelectionModel().isEmpty() || kwTextField.getText().isEmpty()
                || seatsTextField.getText().isEmpty()){
            addModelLabel.setText("Vyplňte všetky údaje.");
            addModelLabel.setTextFill(Color.RED);}
        else{
            try {
                Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("select * from crdb.model");

                while (rs.next()) {
                    if (rs.getString("category").equals(categoryComboBox.getValue()) &&
                            rs.getString("carbrand").equals(brandTextField.getText()) &&
                            rs.getString("carmodel").equals(modelTextField.getText()) &&
                            rs.getString("transmission").equals(transmissionComboBox.getValue()) &&
                            rs.getString("fuel").equals(fuelComboBox.getValue())  &&
                            rs.getString("kw").equals(kwTextField.getText())  &&
                            rs.getString("seats").equals(seatsTextField.getText())) {
                        modelExist = true;
                    }
                }

                if(modelExist){
                    addModelLabel.setText("Model už existuje v databáze");
                    addModelLabel.setTextFill(Color.RED);
                }

                else {
                    PreparedStatement preparedStatementModel = connection.prepareStatement(sqlModel);
                    preparedStatementModel.setString(1, categoryComboBox.getValue());
                    preparedStatementModel.setString(2, brandTextField.getText());
                    preparedStatementModel.setString(3, modelTextField.getText());
                    preparedStatementModel.setString(4, transmissionComboBox.getValue());
                    preparedStatementModel.setString(5, fuelComboBox.getValue());
                    preparedStatementModel.setString(6, kwTextField.getText());
                    preparedStatementModel.setString(7, seatsTextField.getText());
                    preparedStatementModel.executeUpdate();
                    addModelLabel.setText("Model úspešne pridaný do databázy");
                    addModelLabel.setTextFill(Color.GREEN);
                }

            } catch(SQLException e) {
                System.out.println("SQL exception occured: " + e);
            }
        }

    }
}
