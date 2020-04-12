import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.sql.*;

public class CarDatabase {

    @FXML
    private Button addVehicleButton;

    @FXML
    private Button removeVehicleButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<CarInfo> carTable;

    @FXML
    private TableColumn<CarInfo,Integer> vehicleIDColumn;

    @FXML
    private TableColumn<CarInfo,String> licensePlateNumberColumn;

    @FXML
    private TableColumn<CarInfo,String> brandColumn;

    @FXML
    private TableColumn<CarInfo,String> modelColumn;

    @FXML
    private TableColumn<CarInfo,String> colorColumn;

    @FXML
    private TableColumn<CarInfo,Integer> modelIDColumn;

    @FXML
    private TableColumn<CarInfo,String> yearColumn;

    @FXML
    private TableColumn<CarInfo,Float> priceColumn;

    @FXML
    private TableColumn<CarInfo,String> categoryColumn;

    @FXML
    private TableColumn<CarInfo,String> engineColumn;

    @FXML
    private TableColumn<CarInfo,String> transmissionColumn;

    @FXML
    private TableColumn<CarInfo,Integer> seatsColumn;

    public void initialize() throws SQLException {
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
        buildData();
    }



    public void buildData() throws SQLException {
        ObservableList<CarInfo> carinfod = FXCollections.observableArrayList();

        try{
            String sqlModel = ("select * from crdb.model, crdb.vehicle where modelid = model.id");
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlModel);

            while(rs.next()){
                CarInfo carInfo = new CarInfo(rs.getInt("vehicle.id"),rs.getString("category"),rs.getString("carbrand"),
                        rs.getString("carmodel"),rs.getString("transmission"),rs.getString("fuel"),
                        rs.getInt("kw"),rs.getInt("seats"),rs.getInt("model.id"),rs.getString("licenseplatenumber"),"1",1,5);
                carinfod.add(carInfo);
            }
            carTable.setItems(carinfod);
        }

        catch(SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }


//        carTable.setItems(carInfo);
    }

//    private ObservableList<CarInfo> data = FXCollections.observableArrayList(
//            new CarInfo(1,"Amos", "Chepchieng","1","1","1",
//                    1,1,1,"1","1",1, 1)
//            );


//    @FXML
//    public void initialize() throws SQLException {
//
//        licensePlateNumberColumn.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
//
////        try {
////            String sqlModel = ("select * from crdb.model");
////            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
////            PreparedStatement statement = connection.prepareStatement(sqlModel);
////            ResultSet rs = statement.executeQuery("select * from crdb.model");
////
////            while(rs.next()){
////
////                Object o[] = {rs.getString("catagory")};
////                carTable.add
////
//////                String carInfo = "ID: "+rs.getString("id")+", "+rs.getString("carbrand")
//////                        +" - "+rs.getString("carmodel")+", "+rs.getString("category")+", "+rs.getString("transmission")
//////                        +", "+rs.getString("fuel")+", "+rs.getString("kw")
//////                        +"kW, počet miest na sedenie: "+rs.getString("seats");
//////                modelComboBox.getItems().add(carInfo);
////            }
////
////        }
////        catch(SQLException e) {
////            System.out.println("SQL exception occured: " + e);
////        }
//        ObservableList<CarDatabase> x = FXCollections.observableArrayList(
//        new CarDatabase("ahoj");
//        );
//
//    }

    @FXML
    private void addVehicleButtonAction(ActionEvent event) throws Exception{
        Stage stage = (Stage) addVehicleButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/addvehicle.fxml"));
        stage.setScene(new Scene(root, addVehicleButton.getScene().getWidth(), addVehicleButton.getScene().getHeight()));
    }

//    @FXML
//    private void removeVehicleButtonAction(ActionEvent event) throws Exception{
//        stage = (Stage) backLoginButton.getScene().getWindow();
//        Parent root = FXMLLoader.load(getClass().getResource("GUI/entry.fxml"));
//        stage.setScene(new Scene(root, backLoginButton.getScene().getWidth(), backLoginButton.getScene().getHeight()));
//    }

    @FXML
    private void backButtonAction(ActionEvent event) throws Exception{
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/entry.fxml"));
        stage.setScene(new Scene(root, backButton.getScene().getWidth(), backButton.getScene().getHeight()));
    }

}
