import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.logging.Level;

public class CheckIfAvailable {
    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private TableView<AvailabilityInfo> carAvailabilityTable;

    @FXML
    private TableColumn<AvailabilityInfo, String> fromColumnA;

    @FXML
    private TableColumn<AvailabilityInfo, String> toColumnA;

    @FXML
    private Button backButton;

    @FXML
    void backButtonAction(ActionEvent event) {
        backButton.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        int vehicleId = CustomerMenu.rentedVehicleId;
        fromColumnA.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumnA.setCellValueFactory(new PropertyValueFactory<>("to"));
        ObservableList<AvailabilityInfo> availabilityInfod = FXCollections.observableArrayList();

        try {
            String sqlModel = ("select * from crdb.carrental where vehicleid =" + vehicleId + "");

            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlModel);

            while (rs.next()) {
                AvailabilityInfo availabilityInfo = new AvailabilityInfo(rs.getString("pickupdate"),
                        rs.getString("returndate"));
                availabilityInfod.add(availabilityInfo);
            }
            carAvailabilityTable.setItems(availabilityInfod);

        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
    }

}
