import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;

// Menu s moznostami vyberu pre pouzivatela
public class CustomerMenu {

    // Itemy, ktore su potrebne na gui
    public AnchorPane loginAnchorPane;
    public Text customerMenuText;
    public Button makeOrderButton;
    public Button changeButton;
    public Button invoicePdfButton;

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
    private Label passwordLabel;

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
    private Label wrongDateLabel;

    // Inicializacia hodnot do comboboxu chooseCarCombobox, ktory sluzi k vyberu auta
    @FXML
    public void initialize() {
        createOrder.comboBoxInit(chooseCarCombobox);
    }

    // Button na vratenie sa na zaciatok
    @FXML
    void backButtonAction() throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("GUI/login.fxml"), Login.rb);
        stage.setScene(new Scene(root, backButton.getScene().getWidth(), backButton.getScene().getHeight()));
    }

    // Button, ktory zobrazi panel kde je mozne zmenit pouzivatelovi heslo
    @FXML
    void changePasswordButtonAction() {
        passwordLabel.setText("");
        oldPasswordTextField.setText("");
        newPasswordTextField.setText("");
        confirmNewPasswordTextField.setText("");
        changePasswordPane.toFront();
    }

    // Zobrazi moznost vytvorenia objednavky
    @FXML
    void creteOrderButtonAction() {
        objednavkaPane.toFront();
    }

    // Profilova stranka pouzivatela, kde moze vidiet vsetky informacie o sebe
    @FXML
    void profilButtonAction() {
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

        // Vsetky informacie o pouzivatelovi pochadzaju z jednej velkej joinutej tabulky
        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from crdb.customer inner join crdb.address on crdb.customer.addressid = crdb.address.id inner join crdb.city on crdb.address.cityid = crdb.city.id inner join crdb.region on crdb.city.regionid = crdb.region.id where crdb.customer.id = " + Login.USERID + "");
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

        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured " + e);
        }

        // Zobrazenie informacii
        menoLabel.setText(meno + " " + priezvisko);
        emailLabel.setText(email);
        assert meno != null;
        String firstLetter = meno.substring(0, 1).toUpperCase();
        pismenoLabel.setText(firstLetter);
        telLabel.setText(telefon);
        idCardLabel.setText(idCard);
        ulicaLabel.setText(ulica);
        cisloDomuLabel.setText(cisloDomu);
        mestoLabel.setText(mesto);
        pscLabel.setText(psc);
        regionLabel.setText(region);
        profilPane.toFront();

    }


    // Potvrdenie objednavky
    @FXML
    void makeOrder() {
        System.out.println(chooseCarCombobox.getValue());
        if (chooseCarCombobox.getValue() == null) {
            wrongDateLabel.setText("Vyplnte vsetky udaje");
            wrongDateLabel.setTextFill(Color.RED);
            return;
        }
        try {
            createOrder.insertToDb(chooseCarCombobox, pickUpDatepicker, returnDatepicker, wrongDateLabel);
        }
        catch (NullPointerException e) {
            wrongDateLabel.setText("Vyplnte vsetky udaje");
            wrongDateLabel.setTextFill(Color.RED);
        }

    }

    // Zobrazenie vsetkych vytvorenych objednavok
    @FXML
    void seeOrdersButtonAction() {
        createOrder.fillTable(seeOrderTable, fromColumn, toColumn, brandColumnU, modelColumnU, priceColumnU);
        seeOrdersPane.toFront();
    }

    // Vytvorenie pdf suboru objednavky
    @FXML
    void invoicePdfButtonAction() throws DocumentException, IOException {
        // Pre kazdu objednavku je subor iny
        if (!Bindings.isEmpty(seeOrderTable.getItems()).get()) {
            RentalInfo selectedItem = seeOrderTable.getSelectionModel().getSelectedItem();
            int invoiceId = selectedItem.getId() + 1;

            // Informacie o objednavke z joinutej tabulky
            try {
                String sqlModel = ("select * from crdb.carrental inner join crdb.vehicle on crdb.carrental.vehicleid = crdb.vehicle.id inner join crdb.model on crdb.vehicle.modelid = crdb.model.id inner join crdb.invoice on crdb.carrental.invoiceid = crdb.invoice.id where customerid =" + Login.USERID + " and invoiceid = " + invoiceId + "");

                Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sqlModel);
                String meno = null;
                String firma = null;
                String auto = null;
                String cena = null;

                if (rs.next()) {
                    meno = rs.getString("billto");
                    firma = rs.getString("companyname");
                    auto = rs.getString("carbrand") + " " + rs.getString("carmodel");
                    cena = rs.getDouble("amount") + " €";
                }

                // Vytvorenie pdf
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream("bill.pdf"));

                document.open();

                Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 20, BaseColor.BLACK);
                Font basicFont = FontFactory.getFont(FontFactory.TIMES, 12, BaseColor.BLACK);
                Paragraph preface = new Paragraph();
                preface.add(new Paragraph(" "));
                preface.add(new Paragraph("Faktura za pozicanie auta", titleFont));
                preface.add(new Paragraph(" "));
                Paragraph companyParagraph = new Paragraph("Meno spolocnosti: " + firma, basicFont);
                Paragraph nameParagraph = new Paragraph("Meno zakaznika: " + meno, basicFont);
                Paragraph carParagraph = new Paragraph("Poziciavane auto: " + auto, basicFont);
                Paragraph priceParagraph = new Paragraph("Cena: " + cena, basicFont);

                document.add(preface);
                document.add(companyParagraph);
                document.add(nameParagraph);
                document.add(carParagraph);
                document.add(priceParagraph);

                document.close();

                // Okamzite otvorenie prave vytvoreneho suboru
                Desktop desktop = Desktop.getDesktop();
                File file = new File("bill.pdf");
                if (file.exists()) {
                    desktop.open(file);
                }
                JavaLogger.logger.log(Level.INFO, "PDF format invoice created successfully");
            } catch (SQLException e) {
                JavaLogger.logger.log(Level.WARNING, "Database problem");
                System.out.println("SQL exception occured: " + e);
            }

            System.out.println(invoiceId);

        }
    }

    // Potvrdenie zmeny hesla
    @FXML
    void changeButtonAction() {
        // Tahanie udajov z tabulky user
        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id, password from crdb.customer");

            // Ak nie su vyplnene vsetky udaje
            if (confirmNewPasswordTextField.getText().isEmpty() || newPasswordTextField.getText().isEmpty() || oldPasswordTextField.getText().isEmpty()) {
                passwordLabel.setText(Login.rb.getString("missingInfo"));
                passwordLabel.setTextFill(Color.RED);
            } else {
                // Prebehne zmena hesla, udaje budu updatnute aj v databaze
                while (resultSet.next()) {
                    if (resultSet.getInt("id") == Login.USERID) {
                        if (resultSet.getString("password").equals(oldPasswordTextField.getText())) {
                            if (confirmNewPasswordTextField.getText().equals(newPasswordTextField.getText())) {
                                String sql = "update crdb.customer SET password = ? where id = " + Login.USERID + "";
                                PreparedStatement rs = connection.prepareStatement(sql);
                                rs.setString(1, newPasswordTextField.getText());
                                rs.executeUpdate();
                                passwordLabel.setText(Login.rb.getString("passwordChanged"));
                                passwordLabel.setTextFill(Color.GREEN);
                                JavaLogger.logger.log(Level.INFO, "User changed password successfully");
                                return;
                            }
                            // Zle zadane heslo pri potvrdzovani
                            passwordLabel.setText(Login.rb.getString("wrong2Password"));
                            passwordLabel.setTextFill(Color.RED);
                            JavaLogger.logger.log(Level.WARNING, "Wrong password confirmation");
                            return;
                        }
                        // Zle zadane povodne heslo
                        passwordLabel.setText(Login.rb.getString("wrong1Password"));
                        passwordLabel.setTextFill(Color.RED);
                        JavaLogger.logger.log(Level.WARNING, "Wrong original password");
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
    }


    // Akcia ked kurzor prejde cez button
    @FXML
    void enterButton(MouseEvent event) {
        AdminMenu.buttonHoverEnter(event, createOrderButton, changePasswordButton, backButton, profilButton, seeOrdersButton);
    }

    // Akcia ked kurzor opusti button
    @FXML
    void exitButton(MouseEvent event) {
        AdminMenu.buttonHoverExit(event, createOrderButton, changePasswordButton, backButton, profilButton, seeOrdersButton);
    }

}
