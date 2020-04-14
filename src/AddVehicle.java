import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
    private ImageView reloadImage;

    @FXML
    private Label addVehicleLabel;

    @FXML
    public void initialize(){
        modelComboBox.getItems().clear();
        try {
            String sqlModel = ("select * from crdb.model order by carbrand, carmodel");
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            PreparedStatement statement = connection.prepareStatement(sqlModel);
            ResultSet rs = statement.executeQuery(sqlModel);

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
        String sqlVehicle = "insert into crdb.vehicle(licenseplatenumber, color, yearofproduction, price, modelid) values (?, ?, ?, ?, ?)";

        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("select * from crdb.vehicle");
            while(rs.next()){
                if(rs.getString("licenseplatenumber").equals(licensePlateNumberTextField.getText())){
                    addVehicleLabel.setText("Vozidlo s týmto EČV už existuje");
                    addVehicleLabel.setTextFill(Color.RED);
                    return;
                }
            }
            if(modelComboBox.getSelectionModel().isEmpty() || licensePlateNumberTextField.getText().isEmpty()
                    || colorTextField.getText().isEmpty() || yearOfProductionTextField.getText().isEmpty()
                    || priceTextField.getText().isEmpty()){
                addVehicleLabel.setText("Vyplňte všetky údaje");
                addVehicleLabel.setTextFill(Color.RED);
            }
            else{
                String modelID = modelComboBox.getValue();
                modelID = modelID.substring(modelID.indexOf(" ") + 1, modelID.indexOf(","));
                System.out.println(modelID);

                PreparedStatement preparedStatementVehicle = connection.prepareStatement(sqlVehicle);
                preparedStatementVehicle.setString(1, licensePlateNumberTextField.getText());
                preparedStatementVehicle.setString(2, colorTextField.getText());
                preparedStatementVehicle.setString(3, yearOfProductionTextField.getText());
                preparedStatementVehicle.setString(4, priceTextField.getText());
                preparedStatementVehicle.setInt(5, Integer.parseInt(modelID));
                preparedStatementVehicle.executeUpdate();

                addVehicleLabel.setText("Vozidlo úspešne pridané do databázy");
                addVehicleLabel.setTextFill(Color.GREEN);
            }

        } catch(SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }

    }
}