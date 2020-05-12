import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import orm.CarserviceEntity;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

// Menu s moznostami vyberu akcii, ktore moze vykonat iba admin
public class AdminMenu {

    // Itemy, ktore su potrebne na gui
    public AnchorPane adminMenuAnchorPane;
    public Hyperlink addModelHyperlink;
    public ImageView reloadImage;
    public Button addVehicleButton;
    public Button regRegistrationButton;
    public Button changeStateButton;
    public Label stateLabel;
    public TextArea brandInfoTextArea;

    @FXML
    public Button brandInfosButton;

    @FXML
    public Pane brandInfoPane;

    @FXML
    private Button addButton;

    @FXML
    private Button removeVehicleButton;

    @FXML
    private Button showButton;

    @FXML
    private Button servisVehicleButton;

    @FXML
    private Button servisRegistrationButton;

    @FXML
    private Button servisShowButton;

    @FXML
    private Button servisVehicleShowButton;

    @FXML
    private Button backButton;

    @FXML
    private Pane showPane;

    @FXML
    private TableView<CarInfo> carTable;

    @FXML
    private TableColumn<CarInfo, Integer> vehicleIDColumn;

    @FXML
    private TableColumn<CarInfo, String> licensePlateNumberColumn;

    @FXML
    private TableColumn<CarInfo, String> brandColumn;

    @FXML
    private TableColumn<CarInfo, String> modelColumn;

    @FXML
    private TableColumn<CarInfo, String> colorColumn;

    @FXML
    private TableColumn<CarInfo, Integer> modelIDColumn;

    @FXML
    private TableColumn<CarInfo, String> yearColumn;

    @FXML
    private TableColumn<CarInfo, Float> priceColumn;

    @FXML
    private TableColumn<CarInfo, String> categoryColumn;

    @FXML
    private TableColumn<CarInfo, String> engineColumn;

    @FXML
    private TableColumn<CarInfo, String> transmissionColumn;

    @FXML
    private TableColumn<CarInfo, Integer> seatsColumn;

    @FXML
    private Pane addVehiclePane;

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
    private Label addVehicleLabel;

    @FXML
    private Pane servisRegistrationPane;

    @FXML
    private TextField regServiceNameTextField;

    @FXML
    private TextField regPhoneNumberTextField;

    @FXML
    private TextField regEmailTextField;

    @FXML
    private ComboBox<String> regRegionComboBox;

    @FXML
    private TextField regCityTextField;

    @FXML
    private TextField regStreetTextField;

    @FXML
    private TextField regHouseNumberTextField;

    @FXML
    private TextField regZIPTextField;

    @FXML
    private Pane servisShowPane;

    @FXML
    private TableView<ServiceInfo> serviceTable;

    @FXML
    private TableColumn<String, ServiceInfo> servisIDColumn;

    @FXML
    private TableColumn<String, ServiceInfo> servisNameColumn;

    @FXML
    private TableColumn<String, ServiceInfo> servisEmailColumn;

    @FXML
    private TableColumn<String, ServiceInfo> phoneNumberColumn;

    @FXML
    private TableColumn<String, ServiceInfo> servisRegionColumn;

    @FXML
    private TableColumn<String, ServiceInfo> servisCityColumn;

    @FXML
    private TableColumn<String, ServiceInfo> servisStreetColumn;

    @FXML
    private TableColumn<String, ServiceInfo> servisHouseNumberColumn;

    @FXML
    private TableColumn<String, ServiceInfo> servisZIPColumn;

    @FXML
    private Pane servisVehicleShowPane;

    @FXML
    private TableView<ServiceCarInfo> serviceCarTable;

    @FXML
    private ComboBox<String> stateComboBox;

    @FXML
    private Label regServiceLabel;

    @FXML
    private TableColumn<ServiceCarInfo, String> servisVehicleIDColumn;

    @FXML
    private TableColumn<ServiceCarInfo, String> servisLicensePlateNumberColumn;

    @FXML
    private TableColumn<ServiceInfo, String> servisBrandColumn;

    @FXML
    private TableColumn<ServiceCarInfo, String> servisModelColumn;

    @FXML
    private TableColumn<ServiceCarInfo, String> servisColorColumn;

    @FXML
    private TableColumn<ServiceCarInfo, String> servisYearColumn;

    @FXML
    private TableColumn<ServiceCarInfo, String> servisEngineColumn;

    @FXML
    private TableColumn<ServiceCarInfo, String> servisTransmissionColumn;

    @FXML
    private TableColumn<ServiceCarInfo, String> servisStateColumn;

    @FXML
    private TableColumn<ServiceCarInfo, String> servisCarNameColumn;

    @FXML
    private AddVehicle addVehicle = new AddVehicle();

    @FXML
    private CarDatabase carDatabase = new CarDatabase();

    @FXML
    private ServiceDatabase serviceDatabase = new ServiceDatabase();

    @FXML
    private ServiceCarDatabase serviceCarDatabase = new ServiceCarDatabase();

    @FXML
    private Register register = new Register();

    private boolean isShown = false;

    // Zobrazenie okna pre pridanie modelu
    @FXML
    void addModelHyperlinkAction() throws Exception {
        addVehicle.addModelHyperlinkAction();
    }

    // Button k priadaniu vozidla do databazy
    @FXML
    void addVehicleButtonAction() throws SQLException {
        addVehicle.addVehicleButtonAction(licensePlateNumberTextField, colorTextField, yearOfProductionTextField, priceTextField, modelComboBox, addVehicleLabel);
    }

    // Zobrazenie panelu pre ragistraciu servisov
    @FXML
    void servisRegistrationButtonAction() {
        servisRegistrationPane.toFront();
    }

    // Menenie stavu vozidla, ktore bolo urcene na servisovanie
    @FXML
    void changeStateButtonAction() throws SQLException {

        // Vozidlo bolo uz opravene, takze moze byt odstranene z tabulky carrepair
        if (!Bindings.isEmpty(serviceCarTable.getItems()).get()) {
            if (stateComboBox.getValue().equals(Login.rb.getString("status4"))) {
                ServiceCarInfo selectedItem = serviceCarTable.getSelectionModel().getSelectedItem();
                serviceCarTable.getItems().remove(selectedItem);
                String sql = ("delete from crdb.carrepair where vehicleid = ?");
                Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                connection.setAutoCommit(false);

                try {
                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setInt(1, selectedItem.getCarID());
                    statement.executeUpdate();
                    JavaLogger.logger.log(Level.INFO, "Vehicle fixed");


                } catch (SQLException e) {
                    connection.rollback();
                    JavaLogger.logger.log(Level.WARNING, "Database problem");
                    System.out.println(e.getMessage());
                }
                connection.commit();
            } else {
                // Vozidlu sa zmeni stav podla toho ako urci admin
                ServiceCarInfo selectedItem = serviceCarTable.getSelectionModel().getSelectedItem();

                String sql = "update crdb.carrepair SET description = ? where vehicleid = " + selectedItem.getCarID() + "";
                Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                PreparedStatement rs = connection.prepareStatement(sql);
                rs.setString(1, stateComboBox.getValue());
                rs.executeUpdate();
                JavaLogger.logger.log(Level.INFO, "Serviced vehicle status changed");
            }
        }
    }

    // Button na vratenie sa na zaciatok
    @FXML
    void backButtonAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        stage.setScene(new Scene(root, Login.oldwitdh, Login.oldheight));
    }

    // Akcia ked kurzor prejde cez button
    @FXML
    void enterButton(MouseEvent event) {
        buttonHoverEnter(event, addButton, removeVehicleButton, showButton, backButton, servisRegistrationButton);
        if (event.getSource() == servisShowButton) {
            servisShowButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == servisVehicleButton) {
            servisVehicleButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == servisVehicleShowButton) {
            servisVehicleShowButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == brandInfosButton) {
            brandInfosButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
    }

    // Button mierne zmeni farbu a zvacsi pismo
    static void buttonHoverEnter(MouseEvent event, Button addButton, Button removeVehicleButton, Button showButton, Button backButton, Button servisRegistrationButton) {
        if (event.getSource() == addButton) {
            addButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == removeVehicleButton) {
            removeVehicleButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == showButton) {
            showButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == backButton) {
            backButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == servisRegistrationButton) {
            servisRegistrationButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
    }

    // Akcia ked kurzor opusti button
    @FXML
    void exitButton(MouseEvent event) {
        buttonHoverExit(event, addButton, removeVehicleButton, showButton, backButton, servisRegistrationButton);
        if (event.getSource() == servisShowButton) {
            servisShowButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == servisVehicleButton) {
            servisVehicleButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == servisVehicleShowButton) {
            servisVehicleShowButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == brandInfosButton) {
            brandInfosButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
    }

    // Button sa vrati do povodneho stavu
    static void buttonHoverExit(MouseEvent event, Button addButton, Button removeVehicleButton, Button showButton, Button backButton, Button servisRegistrationButton) {
        if (event.getSource() == addButton) {
            addButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == removeVehicleButton) {
            removeVehicleButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == showButton) {
            showButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == backButton) {
            backButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == servisRegistrationButton) {
            servisRegistrationButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
    }

    @FXML
    void brandInfosButtonAction() {
        brandInfoPane.toFront();
    }

    // Naplnenie comboboxov
    @FXML
    public void initialize() {
        addVehicle.initialize(modelComboBox);
        regRegionComboBox.getItems().addAll(
                "Banskobystrický",
                "Bratislavský",
                "Košický",
                "Nitriansky",
                "Prešovský",
                "Trenčiansky",
                "Trnavský",
                "Žilinský"
        );

        stateComboBox.getItems().addAll(
                Login.rb.getString("status1"),
                Login.rb.getString("status2"),
                Login.rb.getString("status3"),
                Login.rb.getString("status4")
        );

        // !
        try {
            brandInfoTextArea.clear();
//            String sqlModel = ("SELECT *, COUNT(carmodel) OVER(PARTITION BY carbrand) as grand_total \n" +
//                    "FROM crdb.model order by carbrand");

            String sql = ("select *, COUNT(carmodel) OVER(PARTITION BY carbrand) as grand_total from crdb.vehicle inner join crdb.model on vehicle.modelid = model.id order by carbrand");

            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<String> ar = new ArrayList<String>();

            while (rs.next()) {
                String carBrandInfo = rs.getString("carbrand") + " " + rs.getString("grand_total");
                if (!(ar.contains(carBrandInfo))) {
                    ar.add(carBrandInfo);
                }
            }

            for (String s : ar) {
                brandInfoTextArea.appendText(s);
                brandInfoTextArea.appendText("\n");
//                System.out.println(s);
            }

        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }

    }

    // Zobrazenie sceny na pridanie vozidiel
    @FXML
    void addButtonAction() {
        addVehiclePane.toFront();
        isShown = false;
    }

    // Refreshnutie comboboxu modelComboBox po tom ako bol pridany model
    @FXML
    void refresh() {
        addVehicle.initialize(modelComboBox);
    }

    // Odstranenie vozidla
    @FXML
    void removeVehicleButtonAction() throws SQLException {
        if (isShown) {
            carDatabase.removeVehicleButtonAction(carTable);
        }
        initialize();
    }

    // Vypis vsetkych dostupnych vozidiel
    @FXML
    void showButtonAction() {
        carDatabase.initialize(carTable, vehicleIDColumn, licensePlateNumberColumn, brandColumn, modelColumn, colorColumn,
                modelIDColumn, yearColumn, priceColumn, categoryColumn, engineColumn, transmissionColumn, seatsColumn);
        showPane.toFront();
        isShown = true;
    }

    private static CarInfo selectedItemCar;

    // Zaradenie vozidla medzi servisovane vozidla
    @FXML
    void servisVehicleButtonAction() throws Exception {
        if (isShown) {
            if (!Bindings.isEmpty(carTable.getItems()).get()) {
                selectedItemCar = carTable.getSelectionModel().getSelectedItem();

                // Moznost vybrat si servisne stredisko
                try {
                    if (selectItemId()) return;

                    Parent root = FXMLLoader.load(getClass().getResource("GUI/servischoose.fxml"), Login.rb);
                    Scene scene = new Scene(root, 300, 250);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                } catch (SQLException e) {
                    JavaLogger.logger.log(Level.WARNING, "Database problem");
                    System.out.println(e.getMessage());
                }
            }

        }
    }

    // !
    private boolean selectItemId() throws SQLException {
        Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from crdb.carrepair");

        while (rs.next()) {
            if (rs.getInt("vehicleid") == selectedItemCar.getCarID()) {
                return true;
            }
        }
        return false;
    }


    @FXML
    void servisVehicleButtonAction2(String IDs) throws Exception {

        String sql = "insert into crdb.carrepair(carserviceid, vehicleid) values (?,?)";
        Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);

        try {
            if (selectItemId()) return;

            PreparedStatement preparedStatementModel = connection.prepareStatement(sql);
            preparedStatementModel.setInt(1, Integer.parseInt(IDs));
            preparedStatementModel.setInt(2, selectedItemCar.getCarID());
            preparedStatementModel.executeUpdate();
            JavaLogger.logger.log(Level.INFO, "Vehicle added to service center management");

        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println(e.getMessage());
        }
    }


    // Registracia servisu do databazy
    @FXML
    void regServisVehicleButtonAction() throws SQLException {
        isShown = false;
        boolean servisExist = false;

        // Neboli vyplnene vsetky udaje
        if (regRegionComboBox.getSelectionModel().isEmpty() || regServiceNameTextField.getText().isEmpty()
                || regPhoneNumberTextField.getText().isEmpty() || regCityTextField.getText().isEmpty()
                || regStreetTextField.getText().isEmpty() || regEmailTextField.getText().isEmpty()
                || regHouseNumberTextField.getText().isEmpty() || regZIPTextField.getText().isEmpty()) {
            regServiceLabel.setText("Vyplňte všetky údaje.");
            regServiceLabel.setTextFill(Color.RED);
        } else {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            try {
                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("select * from crdb.carservice");

                // Servis uz existuje
                while (rs.next()) {
                    if (rs.getString("name").equals(regServiceNameTextField.getText())) {
                        servisExist = true;
                        regServiceLabel.setText(Login.rb.getString("servisExist"));
                        regServiceLabel.setTextFill(Color.RED);
                        JavaLogger.logger.log(Level.WARNING, "Service center already exists");
                    }
                }

                // Ak neexistuje, bude pridany do tabulky carservice
                if (!servisExist) {

                    int currentAddressID = 0;

                    // Servisu je pridelena aj adresa
                    String sqlRegion = "insert ignore into crdb.region(regionname) values (?)";
                    PreparedStatement preparedStatementRegion = connection.prepareStatement(sqlRegion);
                    preparedStatementRegion.setString(1, regRegionComboBox.getValue());
                    preparedStatementRegion.executeUpdate();

                    String sqlCity = "insert ignore into crdb.city(cityname, zipcode, regionid) values (?, ?, ?)";
                    register.findId(connection, statement, sqlCity, regCityTextField, regZIPTextField, "select id from crdb.region where regionname = '", regRegionComboBox.getValue(), "'");

                    String sqlAddress = "insert into crdb.address(street, housenumber, cityid) values (?, ?, ?)";
                    register.findId(connection, statement, sqlAddress, regStreetTextField, regHouseNumberTextField, "select id from crdb.city where zipcode = ", regZIPTextField.getText(), "");

                    rs = statement.executeQuery("select id from crdb.address ORDER BY id DESC LIMIT 1");

                    if (rs.next()) {
                        currentAddressID = rs.getInt("id");
                    }

                    // Zapis do tabulky je realizovany pomocou Hibernate orm
                    CarserviceEntity carserviceEntity = new CarserviceEntity();
                    carserviceEntity.setName(regServiceNameTextField.getText());
                    carserviceEntity.setPhonenumber(regPhoneNumberTextField.getText());
                    carserviceEntity.setEmail(regEmailTextField.getText());
                    carserviceEntity.setAddressid(currentAddressID);

                    SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
                    Session session = sessionFactory.openSession();

                    session.beginTransaction();
                    session.save(carserviceEntity);
                    session.getTransaction().commit();
                    session.close();

                    regServiceLabel.setText(Login.rb.getString("servisCreated"));
                    regServiceLabel.setTextFill(Color.GREEN);
                    JavaLogger.logger.log(Level.INFO, "Service center registered successfully");
                }

            } catch (SQLException e) {
                JavaLogger.logger.log(Level.WARNING, "Database problem");
                System.out.println("SQL exception occured: " + e);
            }
        }
    }

    // Zobrazenie servisnych stredisk
    @FXML
    void servisShowButtonAction() {
        isShown = false;
        serviceDatabase.initialize(serviceTable, servisIDColumn, servisNameColumn, servisEmailColumn, phoneNumberColumn,
                servisRegionColumn, servisCityColumn, servisStreetColumn, servisHouseNumberColumn, servisZIPColumn);
        servisShowPane.toFront();
    }

    // Zobrazenie aut, ktore boli vybrane adminom na servisovanie
    @FXML
    void servisVehicleShowButtonAction() {
        isShown = false;
        serviceCarDatabase.initialize(serviceCarTable, servisVehicleIDColumn, servisLicensePlateNumberColumn,
                servisBrandColumn, servisModelColumn, servisColorColumn, servisYearColumn, servisEngineColumn,
                servisTransmissionColumn, servisCarNameColumn, servisStateColumn);
        servisVehicleShowPane.toFront();
    }
}