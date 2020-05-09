import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.logging.Level;

public class ServiceCarDatabase {

    public void initialize(TableView<ServiceCarInfo> serviceCarTable, TableColumn<ServiceCarInfo, String> servisVehicleIDColumn,
                           TableColumn<ServiceCarInfo, String> servisLicensePlateNumberColumn, TableColumn<ServiceInfo, String>servisBrandColumn,
                           TableColumn<ServiceCarInfo, String> servisModelColumn, TableColumn<ServiceCarInfo, String> servisColorColumn,
                           TableColumn<ServiceCarInfo, String> servisYearColumn, TableColumn<ServiceCarInfo, String> servisEngineColumn,
                           TableColumn<ServiceCarInfo, String> servisTransmissionColumn,TableColumn<ServiceCarInfo, String> servisCarNameColumn, TableColumn<ServiceCarInfo, String> servisStateColumn) throws SQLException {
        servisVehicleIDColumn.setCellValueFactory(new PropertyValueFactory<>("carID"));
        servisLicensePlateNumberColumn.setCellValueFactory(new PropertyValueFactory<>("licensePlateNumber"));
        servisBrandColumn.setCellValueFactory(new PropertyValueFactory<>("carBrand"));
        servisModelColumn.setCellValueFactory(new PropertyValueFactory<>("carModel"));
        servisColorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        servisYearColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfProduction"));



        servisEngineColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(
                () -> cellData.getValue().getFuel() + ", " + cellData.getValue().getKw()+" kW",
                cellData.getValue().fuelProperty(),
                cellData.getValue().kwProperty()
        ));


        servisTransmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        servisCarNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        servisStateColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleState"));
        buildData(serviceCarTable);
    }


    public void buildData(TableView<ServiceCarInfo> serviceTable) throws SQLException {
        ObservableList<ServiceCarInfo> servicecarinfod = FXCollections.observableArrayList();

        try{
            String sqlModel = ("select * from crdb.carrepair join crdb.carservice on carserviceid = carservice.id join crdb.vehicle on vehicleid = vehicle.id join crdb.model on modelid = model.id");

            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlModel);

            while(rs.next()){
                ServiceCarInfo serviceCarInfo = new ServiceCarInfo(rs.getInt("vehicle.id"),rs.getString("vehicle.licenseplatenumber"),
                        rs.getString("model.carbrand"), rs.getString("model.carmodel"),
                        rs.getString("vehicle.color"),rs.getString("vehicle.yearofproduction"),
                        rs.getString("model.transmission"),rs.getString("model.fuel"),
                        rs.getInt("model.kw"),rs.getString("carservice.name"),rs.getString("carrepair.description"));
                servicecarinfod.add(serviceCarInfo);
            }
            serviceTable.setItems(servicecarinfod);
            JavaLogger.logger.log(Level.INFO, "Data built successfully");
        }

        catch(SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
    }
}
