import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.logging.Level;

public class ServisChoose {

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private ComboBox<String> servisChooseComboBox;

    @FXML
    private Button servisChooseButton;

    @FXML
    private Button backButton;

    @FXML
    void backButtonAction(ActionEvent event) {
        backButton.getScene().getWindow().hide();
    }

    public String IDs;

    AdminMenu adminMenu = new AdminMenu();

    @FXML
    void servisChooseButtonAction(ActionEvent event) throws Exception {
        IDs = servisChooseComboBox.getValue();
        IDs = IDs.substring(IDs.indexOf(" ") + 1, IDs.indexOf(","));
        adminMenu.servisVehicleButtonAction2(IDs);
    }

    @FXML
    public void initialize(){
        servisChooseComboBox.getItems().clear();
        try {
            String sqlModel = ("select * from crdb.carservice order by name");
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            PreparedStatement statement = connection.prepareStatement(sqlModel);
            ResultSet rs = statement.executeQuery(sqlModel);

            while(rs.next()){
                String servisInfo = "ID: "+rs.getString("id")+", "+Login.rb.getString("name")+": "+rs.getString("name");
                servisChooseComboBox.getItems().add(servisInfo);
            }
        }
        catch(SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
    }

}
