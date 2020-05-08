import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class CustomerMenu {

    @FXML
    private Text customerMenuText;

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private Button createOrderButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button backButton;

    @FXML
    private Button profilButton;

    @FXML
    private Pane profilPane;

    @FXML
    private Label menoLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label pismenoLabel;

    @FXML
    private Label telLabel;

    @FXML
    private Label idCardLabel;

    @FXML
    private Label ulicaLabel;

    @FXML
    private Label cisloDomuLabel;

    @FXML
    private Label mestoLabel;

    @FXML
    private Label pscLabel;

    @FXML
    private Label regionLabel;

    @FXML
    private Pane objednavkaPane;

    @FXML
    private DatePicker pickUpDatepicker;

    @FXML
    private DatePicker returnDatepicker;

    @FXML
    private ComboBox<String> chooseCarCombobox;

    @FXML
    private Pane changePasswordPane;

    @FXML
    private PasswordField oldPasswordTextField;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    private PasswordField confirmNewPasswordTextField;

    @FXML
    private Button changeButton;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button makeOrderButton;

    @FXML
    private Button seeOrdersButton;

    @FXML
    private CreateOrder createOrder = new CreateOrder();

    @FXML
    private Pane seeOrdersPane;

    @FXML
    private TableView<RentalInfo> seeOrderTable;

    @FXML
    private TableColumn<RentalInfo, String> fromColumn;

    @FXML
    private TableColumn<RentalInfo, String> toColumn;

    @FXML
    private TableColumn<RentalInfo, String> brandColumnU;

    @FXML
    private TableColumn<RentalInfo, String> modelColumnU;

    @FXML
    private TableColumn<RentalInfo, Double> priceColumnU;

    @FXML
    private Button invoicePdfButton;

    @FXML
    public void initialize() {
        createOrder.comboBoxInit(chooseCarCombobox);
    }

    @FXML
    void backButtonAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        stage.setScene(new Scene(root, backButton.getScene().getWidth(), backButton.getScene().getHeight()));
    }

    @FXML
    void changePasswordButtonAction(ActionEvent event) throws IOException {
        passwordLabel.setText("");
        oldPasswordTextField.setText("");
        newPasswordTextField.setText("");
        confirmNewPasswordTextField.setText("");
        changePasswordPane.toFront();
    }

    @FXML
    void creteOrderButtonAction(ActionEvent event) throws IOException {
//        Stage stage = (Stage) createOrderButton.getScene().getWindow();
//        Parent root = FXMLLoader.load(getClass().getResource("GUI/createorder.fxml"));
//        stage.setScene(new Scene(root, createOrderButton.getScene().getWidth(), createOrderButton.getScene().getHeight()));
//        new FadeIn(objednavkaPane).play();
        objednavkaPane.toFront();
    }


    @FXML
    void profilButtonAction(ActionEvent event) {
        String meno = null;
        String priezvisko = null;
        String email = null;
        String telefon = null;
        String idCard = null;
        String ulica = null;
        String cisloDomu = null;
        String mesto = null;
        String psc = null;
        String region = null;

        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from crdb.customer inner join crdb.address on crdb.customer.addressid = crdb.address.id inner join crdb.city on crdb.address.cityid = crdb.city.id inner join crdb.region on crdb.city.regionid = crdb.region.id where crdb.customer.id = "+Login.USERID+"");
            while (resultSet.next()) {
                meno = resultSet.getString("name");
                priezvisko = resultSet.getString("surname");
                email = resultSet.getString("email");
                telefon = resultSet.getString("phonenumber");
                idCard = resultSet.getString("idcardnumber");
                ulica = resultSet.getString("street");
                cisloDomu = resultSet.getString("housenumber");
                mesto = resultSet.getString("cityname");
                psc = resultSet.getString("zipcode");
                region = resultSet.getString("regionname");
            }

        }
        catch(SQLException e) {
            System.out.println("SQL exception occured " + e);
        }

        menoLabel.setText(meno + " " + priezvisko);
        emailLabel.setText(email);
        String firstLetter = meno.substring(0, 1).toUpperCase();
        pismenoLabel.setText(firstLetter);
        telLabel.setText(telefon);
        idCardLabel.setText(idCard);
        ulicaLabel.setText(ulica);
        cisloDomuLabel.setText(cisloDomu);
        mestoLabel.setText(mesto);
        pscLabel.setText(psc);
        regionLabel.setText(region);

//        new FadeIn(profilPane).play();
        profilPane.toFront();

    }

    @FXML
    void makeOrder(ActionEvent event) {
        System.out.println(chooseCarCombobox.getValue());
        createOrder.insertToDb(chooseCarCombobox, pickUpDatepicker, returnDatepicker);
//        pickUpDatepicker.getDatePicker().setMaxDate(System.currentTimeMillis());
    }


    @FXML
    void seeOrdersButtonAction(ActionEvent event) throws SQLException {
        createOrder.fillTable(seeOrderTable, fromColumn, toColumn, brandColumnU, modelColumnU, priceColumnU);
        seeOrdersPane.toFront();
    }

    @FXML
    void invoicePdfButtonAction(ActionEvent event) throws SQLException, DocumentException, FileNotFoundException {
        if(!Bindings.isEmpty(seeOrderTable.getItems()).get()) {
            RentalInfo selectedItem = seeOrderTable.getSelectionModel().getSelectedItem();
            int invoiceId = selectedItem.getId() + 1;

            try {
                String sqlModel = ("select * from crdb.carrental inner join crdb.vehicle on crdb.carrental.vehicleid = crdb.vehicle.id inner join crdb.model on crdb.vehicle.modelid = crdb.model.id inner join crdb.invoice on crdb.carrental.invoiceid = crdb.invoice.id where customerid =" + Login.USERID + " and invoiceid = " + invoiceId + "");

                Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlModel);
                String meno = null;

                if (rs.next()) {
                    meno = rs.getString("billto");
                }

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("bill.pdf"));

                document.open();

                Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
                Chunk chunk = new Chunk("Faktura za pozicanie auta", font);
                Paragraph preface = new Paragraph();
                // We add one empty line
                preface.add(new Paragraph(" "));
                // Lets write a big header
                preface.add(new Paragraph("Title of the document", font));
                preface.add(new Paragraph(" "));
                document.add(preface);
                document.add(chunk);

                document.close();

            }

            catch(SQLException e) {
                System.out.println("SQL exception occured: " + e);
            }

            System.out.println(invoiceId);

        }
    }


    @FXML
    void changeButtonAction() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id, password from crdb.customer");

            if(confirmNewPasswordTextField.getText().isEmpty() || newPasswordTextField.getText().isEmpty() || oldPasswordTextField.getText().isEmpty()){
                passwordLabel.setText(Login.rb.getString("missingInfo"));
                passwordLabel.setTextFill(Color.RED);
            }
            else{
                while(resultSet.next()){
                    if(resultSet.getInt("id") == Login.USERID){
                        if(resultSet.getString("password").equals(oldPasswordTextField.getText())){
                            if(confirmNewPasswordTextField.getText().equals(newPasswordTextField.getText())){
                                String sql = "update crdb.customer SET password = ? where id = "+Login.USERID+"";
                                PreparedStatement rs = connection.prepareStatement(sql);
                                rs.setString(1,newPasswordTextField.getText());
                                rs.executeUpdate();
                                passwordLabel.setText(Login.rb.getString("passwordChanged"));
                                passwordLabel.setTextFill(Color.GREEN);
                                return;
                            }
                            passwordLabel.setText(Login.rb.getString("wrong2Password"));
                            passwordLabel.setTextFill(Color.RED);
                            return;
                        }
                        passwordLabel.setText(Login.rb.getString("wrong1Password"));
                        passwordLabel.setTextFill(Color.RED);
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }
    }


    @FXML
    void enterButton(MouseEvent event) {
        if (event.getSource() == createOrderButton) {
            createOrderButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == changePasswordButton) {
            changePasswordButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == backButton) {
            backButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == profilButton) {
            profilButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
        if (event.getSource() == seeOrdersButton) {
            seeOrdersButton.setStyle("-fx-background-color: #323232; -fx-font-size: 14; -fx-font-weight: bold");
        }
    }

    @FXML
    void exitButton(MouseEvent event) {
        if (event.getSource() == createOrderButton) {
            createOrderButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == changePasswordButton) {
            changePasswordButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == backButton) {
            backButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == profilButton) {
            profilButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
        if (event.getSource() == seeOrdersButton) {
            seeOrdersButton.setStyle("-fx-background-color: #3b3b3b; -fx-font-size: 12; -fx-font-weight: bold");
        }
    }

}
