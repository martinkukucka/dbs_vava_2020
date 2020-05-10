import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.util.logging.Level;

// Trieda, ktora sluzi na zvolenie servisneho strediska pre konkretne vozidlo
public class ServisChoose {

    // Itemy, ktore su potrebne na gui
    public AnchorPane loginAnchorPane;
    public Button servisChooseButton;
    @FXML
    private ComboBox<String> servisChooseComboBox;

    @FXML
    private Button backButton;

    // Button na zatvorenie aktualneho okna
    @FXML
    void backButtonAction() {
        backButton.getScene().getWindow().hide();
    }

    private AdminMenu adminMenu = new AdminMenu();

    // !
    @FXML
    void servisChooseButtonAction() throws Exception {
        String IDs = servisChooseComboBox.getValue();
        IDs = IDs.substring(IDs.indexOf(" ") + 1, IDs.indexOf(","));
        adminMenu.servisVehicleButtonAction2(IDs);
    }

    // Vlozenie dat do servisChooseComboBox
    @FXML
    public void initialize() {
        servisChooseComboBox.getItems().clear();
        // Data sa vlozia do comboboxu z tabulky carservice
        try {
            String sqlModel = ("select * from crdb.carservice order by name");
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            PreparedStatement statement = connection.prepareStatement(sqlModel);
            ResultSet rs = statement.executeQuery(sqlModel);

            while (rs.next()) {
                String servisInfo = "ID: " + rs.getString("id") + ", " + Login.rb.getString("name") + ": " + rs.getString("name");
                servisChooseComboBox.getItems().add(servisInfo);
            }
        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
    }

}
