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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AdminMenu {

    @FXML
    private AnchorPane adminMenuAnchorPane;

    @FXML
    private Button addButton;

    @FXML
    private Button addVehicleButton;

    @FXML
    private Button removeVehicleButton;

    @FXML
    private Button showButton;

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
    private TextField licensePlateNumberTextField;

    @FXML
    private TextField colorTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField yearOfProductionTextField;

    @FXML
    private ComboBox<String> modelComboBox;

    @FXML
    private Hyperlink addModelHyperlink;

    @FXML
    private ImageView reloadImage;

    @FXML
    private Label addVehicleLabel;

    @FXML
    private AddVehicle addVehicle = new AddVehicle();

    @FXML
    private CarDatabase carDatabase = new CarDatabase();

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
    void backButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"));
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
    }

    @FXML
    void initialize() {
        addVehicle.initialize(modelComboBox);
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
}