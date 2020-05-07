import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.*;

public class CarDatabase {

    @FXML
    private Button addVehicleButton;

    @FXML
    private Button removeVehicleButton;

    @FXML
    private Button backButton;

//    @FXML
//    private TableView<CarInfo> carTable;
//
//    @FXML
//    private TableColumn<CarInfo,Integer> vehicleIDColumn;
//
//    @FXML
//    private TableColumn<CarInfo,String> licensePlateNumberColumn;
//    @FXML
//    private TableColumn<CarInfo,String> brandColumn;
//
//    @FXML
//    private TableColumn<CarInfo,String> modelColumn;
//
//    @FXML
//    private TableColumn<CarInfo,String> colorColumn;
//
//    @FXML
//    private TableColumn<CarInfo,Integer> modelIDColumn;
//
//    @FXML
//    private TableColumn<CarInfo,String> yearColumn;
//
//    @FXML
//    private TableColumn<CarInfo,Float> priceColumn;
//
//    @FXML
//    private TableColumn<CarInfo,String> categoryColumn;
//
//    @FXML
//    private TableColumn<CarInfo,String> engineColumn;
//
//    @FXML
//    private TableColumn<CarInfo,String> transmissionColumn;
//
//    @FXML
//    private TableColumn<CarInfo,Integer> seatsColumn;

    public void initialize(TableView<CarInfo> carTable,TableColumn<CarInfo,Integer> vehicleIDColumn,
                           TableColumn<CarInfo,String>licensePlateNumberColumn,TableColumn<CarInfo,String>brandColumn,
                           TableColumn<CarInfo,String> modelColumn,TableColumn<CarInfo,String> colorColumn,
                           TableColumn<CarInfo,Integer> modelIDColumn,TableColumn<CarInfo,String> yearColumn,
                           TableColumn<CarInfo,Float> priceColumn,TableColumn<CarInfo,String> categoryColumn,
                           TableColumn<CarInfo,String> engineColumn,TableColumn<CarInfo,String> transmissionColumn,
                           TableColumn<CarInfo,Integer> seatsColumn) throws SQLException {
        vehicleIDColumn.setCellValueFactory(new PropertyValueFactory<>("carID"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("carBrand"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("carModel"));
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        engineColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(
                () -> cellData.getValue().getFuel() + ", " + cellData.getValue().getKw()+" kW",
                cellData.getValue().fuelProperty(),
                cellData.getValue().kwProperty()
        ));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        modelIDColumn.setCellValueFactory(new PropertyValueFactory<>("modelID"));
        licensePlateNumberColumn.setCellValueFactory(new PropertyValueFactory<>("licensePlateNumber"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfProduction"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        buildData(carTable);
    }


    public void buildData(TableView<CarInfo> carTable) throws SQLException {
        ObservableList<CarInfo> carinfod = FXCollections.observableArrayList();

        try{
            String sqlModel = ("select * from crdb.vehicle join crdb.model where modelid = model.id order by carbrand,carmodel");

            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlModel);

            while(rs.next()){
                CarInfo carInfo = new CarInfo(rs.getInt("vehicle.id"),rs.getString("category"),rs.getString("carbrand"),
                        rs.getString("carmodel"),rs.getString("transmission"),rs.getString("fuel"),
                        rs.getInt("kw"),rs.getInt("seats"),rs.getInt("model.id"),
                        rs.getString("licenseplatenumber"),rs.getString("color"),
                        rs.getInt("yearofproduction"),rs.getInt("price"));
                carinfod.add(carInfo);
            }
            carTable.setItems(carinfod);
        }

        catch(SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }

        try{
            String sqlModel = ("select * from crdb.model join crdb.vehicle where modelid = model.id group by carbrand having AVG(price) >" +
                    "(SELECT AVG(price) from crdb.vehicle) order by carbrand");

            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlModel);

            while(rs.next()){
                System.out.println(rs.getString("carbrand"));
            }
        }

        catch(SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }
    }

    @FXML
    private void addVehicleButtonAction(ActionEvent event) throws Exception{
        Stage stage = (Stage) addVehicleButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/addvehicle.fxml"));
        stage.setScene(new Scene(root, addVehicleButton.getScene().getWidth(), addVehicleButton.getScene().getHeight()));
    }

//    vymazanie vozidla zo systému
    @FXML
    void removeVehicleButtonAction(TableView<CarInfo> carTable) {
        if(!Bindings.isEmpty(carTable.getItems()).get()) {
            CarInfo selectedItem = carTable.getSelectionModel().getSelectedItem();
            carTable.getItems().remove(selectedItem);
            String sqlModel = ("delete from crdb.carrepair where vehicleid = ?");

            try {
                Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                PreparedStatement statement = connection.prepareStatement(sqlModel);

                statement.setInt(1, selectedItem.getCarID());
                statement.executeUpdate();


                sqlModel = ("delete from crdb.vehicle where id = ?");

                statement = connection.prepareStatement(sqlModel);

                statement.setInt(1, selectedItem.getCarID());
                statement.executeUpdate();


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    @FXML
    private void backButtonAction(ActionEvent event) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/entry.fxml"));
        stage.setScene(new Scene(root, backButton.getScene().getWidth(), backButton.getScene().getHeight()));
    }
}