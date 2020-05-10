import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.logging.Level;

// Metoda umoznuje adminovi pridat novy model auta do databazy
public class AddModel {

    // Itemy, ktore su potrebne na gui
    public Button addModelButton;
    @FXML
    private ComboBox<String> fuelComboBox;

    @FXML
    private TextField modelTextField;

    @FXML
    private TextField brandTextField;

    @FXML
    private Label addModelLabel;

    @FXML
    private TextField kwTextField;

    @FXML
    private TextField seatsTextField;

    @FXML
    private ComboBox<String> transmissionComboBox;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private Button cancelButton;

    // Naplnenie comboboxov
    @FXML
    public void initialize() {
        categoryComboBox.getItems().addAll(
                "Hatchback",
                "Sedan",
                "Combi",
                "Coupe",
                "MPV",
                Login.rb.getString("van")
        );

        fuelComboBox.getItems().addAll(
                Login.rb.getString("fuel1"),
                Login.rb.getString("fuel2"),
                Login.rb.getString("fuel3"),
                Login.rb.getString("fuel4")
        );

        transmissionComboBox.getItems().addAll(
                Login.rb.getString("gearbox1"),
                Login.rb.getString("gearbox2")
        );
    }

    // Zatvorenie okna na pridavanie modelov
    @FXML
    void cancelButtonAction() {
        cancelButton.getScene().getWindow().hide();
    }

    // Pridavanie novych modelov
    @FXML
    private void addModelButtonAction() {
        boolean modelExist = false;
        // Modely sa vkladaju do databazy pre mozne buduce pouzitie
        String sqlModel = "insert into crdb.model(category, carbrand, carmodel, transmission, fuel, kw, seats) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        // Neboli vyplnene vsetky udaje
        if (categoryComboBox.getSelectionModel().isEmpty() || brandTextField.getText().isEmpty()
                || modelTextField.getText().isEmpty() || transmissionComboBox.getSelectionModel().isEmpty()
                || fuelComboBox.getSelectionModel().isEmpty() || kwTextField.getText().isEmpty()
                || seatsTextField.getText().isEmpty()) {
            addModelLabel.setText(Login.rb.getString("missingInfo"));
            addModelLabel.setTextFill(Color.RED);
        } else {
            try {
                Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("select * from crdb.model");

                // Model uz existuje
                while (rs.next()) {
                    if (rs.getString("category").equals(categoryComboBox.getValue()) &&
                            rs.getString("carbrand").equals(brandTextField.getText()) &&
                            rs.getString("carmodel").equals(modelTextField.getText()) &&
                            rs.getString("transmission").equals(transmissionComboBox.getValue()) &&
                            rs.getString("fuel").equals(fuelComboBox.getValue()) &&
                            rs.getString("kw").equals(kwTextField.getText()) &&
                            rs.getString("seats").equals(seatsTextField.getText())) {
                        modelExist = true;
                        addModelLabel.setText(Login.rb.getString("modelExist"));
                        addModelLabel.setTextFill(Color.RED);
                    }
                }

                // Pridanie noveho modelu
                if (!modelExist) {
                    PreparedStatement preparedStatementModel = connection.prepareStatement(sqlModel);
                    preparedStatementModel.setString(1, categoryComboBox.getValue());
                    preparedStatementModel.setString(2, brandTextField.getText());
                    preparedStatementModel.setString(3, modelTextField.getText());
                    preparedStatementModel.setString(4, transmissionComboBox.getValue());
                    preparedStatementModel.setString(5, fuelComboBox.getValue());
                    preparedStatementModel.setString(6, kwTextField.getText());
                    preparedStatementModel.setString(7, seatsTextField.getText());
                    preparedStatementModel.executeUpdate();
                    addModelLabel.setText(Login.rb.getString("modelCreated"));
                    addModelLabel.setTextFill(Color.GREEN);
                    JavaLogger.logger.log(Level.INFO, "New model successfully created");
                }

            } catch (SQLException e) {
                JavaLogger.logger.log(Level.WARNING, "Database problem");
                System.out.println("SQL exception occured: " + e);
            }
        }

    }
}
