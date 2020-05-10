import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.logging.Level;

// Trieda k vypisu servisnych stredisk
public class ServiceDatabase {

    // Metoda na inicializaciu velkej tabulky z gui, ktora obsahuje vsetky informacie o servisoch
    public void initialize(TableView<ServiceInfo> serviceTable, TableColumn<String, ServiceInfo> servisIDColumn,
                           TableColumn<String, ServiceInfo> servisNameColumn, TableColumn<String, ServiceInfo> servisEmailColumn,
                           TableColumn<String, ServiceInfo> phoneNumberColumn, TableColumn<String, ServiceInfo> servisRegionColumn,
                           TableColumn<String, ServiceInfo> servisCityColumn, TableColumn<String, ServiceInfo> servisStreetColumn,
                           TableColumn<String, ServiceInfo> servisHouseNumberColumn, TableColumn<String, ServiceInfo> servisZIPColumn) {
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

    // Tahanie dat do tabulky z databazy
    private void buildData(TableView<ServiceInfo> serviceTable) {
        ObservableList<ServiceInfo> serviceinfod = FXCollections.observableArrayList();

        // Viacero tabuliek spojenych pomocov joinu
        // Data v tabulke z gui pochadzaju z tychto tabuliek
        try {
            String sqlModel = ("select * from crdb.carservice join crdb.address on addressid = address.id " +
                    "join crdb.city on cityid = city.id join crdb.region on regionid = region.id order by name");

            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlModel);

            // Vkladanie do tabulky
            while (rs.next()) {
                ServiceInfo serviceInfo = new ServiceInfo(rs.getInt("id"), rs.getString("name"), rs.getString("phonenumber"),
                        rs.getString("email"), rs.getString("region.regionname"), rs.getString("city.cityname"),
                        rs.getString("address.street"), rs.getString("address.housenumber"),
                        rs.getInt("city.zipcode"));
                serviceinfod.add(serviceInfo);
            }
            serviceTable.setItems(serviceinfod);
            JavaLogger.logger.log(Level.INFO, "Data built successfully");
        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
    }


}
