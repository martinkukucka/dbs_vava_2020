import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class ServiceDatabase {

    public void initialize(TableView<ServiceInfo>serviceTable, TableColumn<String, ServiceInfo> servisIDColumn,
                           TableColumn<String, ServiceInfo> servisNameColumn, TableColumn<String, ServiceInfo> servisEmailColumn,
                           TableColumn<String, ServiceInfo> phoneNumberColumn, TableColumn<String, ServiceInfo> servisRegionColumn,
                           TableColumn<String, ServiceInfo> servisCityColumn, TableColumn<String, ServiceInfo> servisStreetColumn,
                           TableColumn<String, ServiceInfo> servisHouseNumberColumn, TableColumn<String, ServiceInfo> servisZIPColumn) throws SQLException {
        servisIDColumn.setCellValueFactory(new PropertyValueFactory<>("servisID"));
        servisNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        servisEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        servisRegionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        servisCityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        servisStreetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        servisHouseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("housenumber"));
        servisZIPColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        buildData(serviceTable);
    }


    public void buildData(TableView<ServiceInfo> serviceTable) throws SQLException {
        ObservableList<ServiceInfo> serviceinfod = FXCollections.observableArrayList();

        try{
            String sqlModel = ("select * from crdb.carservice join crdb.address on addressid = address.id " +
                    "join crdb.city on cityid = city.id join crdb.region on regionid = region.id order by name");

            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlModel);

            while(rs.next()){
                ServiceInfo serviceInfo = new ServiceInfo(rs.getInt("id"),rs.getString("name"),rs.getString("phonenumber"),
                        rs.getString("email"),rs.getString("region.regionname"),rs.getString("city.cityname"),
                        rs.getString("address.street"),rs.getString("address.housenumber"),
                        rs.getInt("city.zipcode"));
                serviceinfod.add(serviceInfo);
            }
            serviceTable.setItems(serviceinfod);
        }

        catch(SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }
    }


}
