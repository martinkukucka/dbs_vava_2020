import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.*;
import java.util.logging.Level;

// Trieda kde moze admin pridat nove vozidlo do databazy(ponuky na pozicanie)
public class AddVehicle {

    // Itemy, ktore su potrebne na gui
    @FXML
    private Button backButton;

    // Naplnenie comboboxu s modelmi
    @FXML
    public void initialize(ComboBox<String> modelComboBox) {
        modelComboBox.getItems().clear();
        // Pouziva sa tabulka model
        try {
            String sqlModel = ("select * from crdb.model order by carbrand, carmodel");
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            PreparedStatement statement = connection.prepareStatement(sqlModel);
            ResultSet rs = statement.executeQuery(sqlModel);

            while (rs.next()) {
                String carInfo = "ID: " + rs.getString("id") + ", " + rs.getString("carbrand")
                        + " - " + rs.getString("carmodel") + ", " + rs.getString("category") + ", " + rs.getString("transmission")
                        + ", " + rs.getString("fuel") + ", " + rs.getString("kw")
                        + "kW, " + Login.rb.getString("seats") + ": " + rs.getString("seats");

                modelComboBox.getItems().add(carInfo);
            }
        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
    }

    // Link k pridavaniu modelov
    @FXML
    void addModelHyperlinkAction() throws Exception {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/addmodel.fxml"), Login.rb);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void backButtonAction() throws Exception {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/cardatabase.fxml"), Login.rb);
        stage.setScene(new Scene(root, backButton.getScene().getWidth(), backButton.getScene().getHeight()));
    }

    // Potvrdenie pridanie vozidla
    @FXML
    void addVehicleButtonAction(TextField licensePlateNumberTextField, TextField colorTextField,
                                TextField yearOfProductionTextField, TextField priceTextField, ComboBox<String> modelComboBox, Label addVehicleLabel) throws SQLException {
        // Vozidlo bude vlozene do tabulky vehicle
        String sqlVehicle = "insert into crdb.vehicle(licenseplatenumber, color, yearofproduction, price, modelid) values (?, ?, ?, ?, ?)";

        Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
        connection.setAutoCommit(false);
        try {
            Statement statement = connection.createStatement();

            // Vozidlo uz existuje
            ResultSet rs = statement.executeQuery("select * from crdb.vehicle");
            while (rs.next()) {
                if (rs.getString("licenseplatenumber").equals(licensePlateNumberTextField.getText())) {
                    addVehicleLabel.setText(Login.rb.getString("licensePlateExist"));
                    addVehicleLabel.setTextFill(Color.RED);
                    JavaLogger.logger.log(Level.WARNING, "Vehicle with this license plate already exists");
                    return;
                }
            }
            if (modelComboBox.getSelectionModel().isEmpty() || licensePlateNumberTextField.getText().isEmpty()
                    || colorTextField.getText().isEmpty() || yearOfProductionTextField.getText().isEmpty()
                    || priceTextField.getText().isEmpty()) {
                addVehicleLabel.setText(Login.rb.getString("missingInfo"));
                addVehicleLabel.setTextFill(Color.RED);
            } else {
                // Vlozenie vozidla do databazy
                String modelID = modelComboBox.getValue();
                modelID = modelID.substring(modelID.indexOf(" ") + 1, modelID.indexOf(","));

                PreparedStatement preparedStatementVehicle = connection.prepareStatement(sqlVehicle);
                preparedStatementVehicle.setString(1, licensePlateNumberTextField.getText());
                preparedStatementVehicle.setString(2, colorTextField.getText());
                preparedStatementVehicle.setInt(3, Integer.parseInt(yearOfProductionTextField.getText()));
                preparedStatementVehicle.setString(4, priceTextField.getText());
                preparedStatementVehicle.setInt(5, Integer.parseInt(modelID));
                preparedStatementVehicle.executeUpdate();

                addVehicleLabel.setText(Login.rb.getString("vehicleCreated"));
                addVehicleLabel.setTextFill(Color.GREEN);
                JavaLogger.logger.log(Level.INFO, "New vehicle successfully created");
            }

        } catch (SQLException e) {
            connection.rollback();
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
        connection.commit();

    }
}