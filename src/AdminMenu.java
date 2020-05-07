import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class AdminMenu {

    String removeString = "4. vozidlo vyzdvihnute";

    @FXML
    private AnchorPane adminMenuAnchorPane;

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

    @FXML
    private Pane addVehiclePane;

    @FXML
    private Button addVehicleButton;

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
    private ImageView reloadImage;

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
    private Button regRegistrationButton;

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
    private Button changeStateButton;

    @FXML
    private Label stateLabel;

    @FXML
    private Label regServiceLabel;

    @FXML
    private ComboBox<String> servisComboBox;

    @FXML
    private Button chooseServisButton;

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

    @FXML
    void addModelHyperlinkAction(ActionEvent event) throws Exception {
        addVehicle.addModelHyperlinkAction();
    }

    @FXML
    void addVehicleButtonAction(ActionEvent event) throws Exception {
        addVehicle.addVehicleButtonAction(licensePlateNumberTextField,colorTextField,yearOfProductionTextField,priceTextField,modelComboBox,addVehicleLabel);
    }

    @FXML
    void servisRegistrationButtonAction(ActionEvent event) throws IOException {
        servisRegistrationPane.toFront();
    }

    @FXML
    void changeStateButtonAction(ActionEvent event) throws IOException, SQLException {

        if (!Bindings.isEmpty(serviceCarTable.getItems()).get()) {
            if (stateComboBox.getValue().equals(removeString)){
                ServiceCarInfo selectedItem = serviceCarTable.getSelectionModel().getSelectedItem();
                serviceCarTable.getItems().remove(selectedItem);
                String sql = ("delete from crdb.carrepair where vehicleid = ?");

                try {
                    Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setInt(1, selectedItem.getCarID());
                    statement.executeUpdate();


                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            else{

//                while(resultSet.next()){
//                    if(resultSet.getInt("id") == Login.USERID){
//                        if(resultSet.getString("password").equals(oldPasswordTextField.getText())){
//                            if(confirmNewPasswordTextField.getText().equals(newPasswordTextField.getText())){
//                                String sql = "update crdb.customer SET password = ? where id = "+Login.USERID+"";
//                                PreparedStatement rs = connection.prepareStatement(sql);
//                                rs.setString(1,newPasswordTextField.getText());
//                                rs.executeUpdate();
//                                passwordLabel.setText("Heslo úspešne zmenené");
//                                passwordLabel.setTextFill(Color.GREEN);
//                                return;
//                            }
//                            passwordLabel.setText("Zlé potvrdenie hesla");
//                            passwordLabel.setTextFill(Color.RED);
//                            return;
//                        }
//                        passwordLabel.setText("Pôvodné heslo sa nezhoduje");
//                        passwordLabel.setTextFill(Color.RED);
//                    }
//                }


                ServiceCarInfo selectedItem = serviceCarTable.getSelectionModel().getSelectedItem();

                String sql = "update crdb.carrepair SET description = ? where vehicleid = "+selectedItem.getCarID()+"";
                Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                PreparedStatement rs = connection.prepareStatement(sql);
                rs.setString(1,stateComboBox.getValue());
                rs.executeUpdate();



//                String sql = "update crdb.carrepair(description) values (?)";

//                try {
//                    Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
//                    PreparedStatement statement = connection.prepareStatement(sql);
//                    statement.setString(1, stateComboBox.getValue());
//                    statement.executeUpdate();
//
//                } catch (SQLException e) {
//                    System.out.println(e.getMessage());
//                }
            }
        }
    }

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"),Login.rb);
        stage.setScene(new Scene(root, Login.oldwitdh, Login.oldheight));
    }

    @FXML
    void enterButton(MouseEvent event) {
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
        if (event.getSource() == servisShowButton) {
            servisShowButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == servisVehicleButton) {
            servisVehicleButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == servisVehicleShowButton) {
            servisVehicleShowButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
    }

    @FXML
    void exitButton(MouseEvent event) {
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
        if (event.getSource() == servisShowButton) {
            servisShowButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == servisVehicleButton) {
            servisVehicleButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == servisVehicleShowButton) {
            servisVehicleShowButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
    }


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
                "1. pripravene na odovzdanie",
                "2. prebieha oprava",
                "3. pripravene na vyzdvihnutie",
                removeString
        );

    }

    @FXML
    void addButtonAction(ActionEvent event) throws Exception {
        addVehiclePane.toFront();
        isShown = false;
    }

    @FXML
    void refresh(){
        addVehicle.initialize(modelComboBox);
    }

    @FXML
    void removeVehicleButtonAction() {
        if(isShown){
            carDatabase.removeVehicleButtonAction(carTable);
        }
    }

    @FXML
    void showButtonAction(ActionEvent event) throws SQLException {
        carDatabase.initialize(carTable,vehicleIDColumn,licensePlateNumberColumn,brandColumn,modelColumn,colorColumn,
                modelIDColumn,yearColumn,priceColumn,categoryColumn,engineColumn,transmissionColumn,seatsColumn);
        showPane.toFront();
        isShown = true;
    }

    public static CarInfo selectedItemCar;

//    zaradenie vozidla medzi servisované vozidlá
    @FXML
    void servisVehicleButtonAction(ActionEvent event) throws Exception {
        if(isShown){

            String sql = "insert into crdb.carrepair(carserviceid, vehicleid) values (?,?)";

            if(!Bindings.isEmpty(carTable.getItems()).get()) {
                selectedItemCar = carTable.getSelectionModel().getSelectedItem();

                try {
                    Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);

                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("select * from crdb.carrepair");

                    while (rs.next()) {
                        if (rs.getInt("vehicleid") == selectedItemCar.getCarID()) {
                            return;
                        }
                    }

                    Parent root = FXMLLoader.load(getClass().getResource("GUI/servischoose.fxml"));
                    Scene scene = new Scene(root,300,250);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }


    @FXML
    void servisVehicleButtonAction2(String IDs) throws Exception {

            String sql = "insert into crdb.carrepair(carserviceid, vehicleid) values (?,?)";


                try {
                    Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);

                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("select * from crdb.carrepair");

                    while (rs.next()) {
                        if (rs.getInt("vehicleid") == selectedItemCar.getCarID()) {
                            return;
                        }
                    }

                    PreparedStatement preparedStatementModel = connection.prepareStatement(sql);
                    preparedStatementModel.setInt(1, Integer.parseInt(IDs));
                    preparedStatementModel.setInt(2, selectedItemCar.getCarID());
                    preparedStatementModel.executeUpdate();

                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
    }



    //registrácia servisu do databázy
    @FXML
    void regServisVehicleButtonAction() {
        isShown = false;
        boolean servisExist = false;
        String sql = "insert ignore into crdb.carservice(name, phonenumber, email, addressid) values (?, ?, ?, ?)";

        if(regRegionComboBox.getSelectionModel().isEmpty() || regServiceNameTextField.getText().isEmpty()
                || regPhoneNumberTextField.getText().isEmpty() || regCityTextField.getText().isEmpty()
                || regStreetTextField.getText().isEmpty() || regEmailTextField.getText().isEmpty()
                || regHouseNumberTextField.getText().isEmpty() || regZIPTextField.getText().isEmpty()){
            regServiceLabel.setText("Vyplňte všetky údaje.");
            regServiceLabel.setTextFill(Color.RED);}
        else{

            try {
                Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("select * from crdb.carservice");

                while (rs.next()) {
                    if (rs.getString("name").equals(regServiceNameTextField.getText())) {
                        servisExist = true;
                        regServiceLabel.setText("Servis už existuje v databáze");
                        regServiceLabel.setTextFill(Color.RED);
                    }
                }

                if(!servisExist){

                    int currentAddressID = 0;

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

                    PreparedStatement preparedStatement = connection.prepareStatement(sql);

                    preparedStatement.setString(1, regServiceNameTextField.getText());
                    preparedStatement.setString(2, regPhoneNumberTextField.getText());
                    preparedStatement.setString(3, regEmailTextField.getText());
                    preparedStatement.setInt(4, currentAddressID);
                    preparedStatement.executeUpdate();
                    regServiceLabel.setText("Servis pridaný do databázy");
                    regServiceLabel.setTextFill(Color.GREEN);
                }

            } catch(SQLException e) {
                System.out.println("SQL exception occured: " + e);
            }

        }
    }

    @FXML
    void servisShowButtonAction(ActionEvent event) throws SQLException {
        isShown = false;
        serviceDatabase.initialize(serviceTable,servisIDColumn,servisNameColumn,servisEmailColumn,phoneNumberColumn,
                servisRegionColumn,servisCityColumn,servisStreetColumn,servisHouseNumberColumn,servisZIPColumn);
        servisShowPane.toFront();
    }

    @FXML
    void servisVehicleShowButtonAction(ActionEvent event) throws SQLException {
        isShown = false;
        serviceCarDatabase.initialize(serviceCarTable,servisVehicleIDColumn,servisLicensePlateNumberColumn,servisBrandColumn,servisModelColumn,servisColorColumn,
                servisYearColumn,servisEngineColumn,servisTransmissionColumn,servisCarNameColumn,servisStateColumn);
        servisVehicleShowPane.toFront();
    }
}