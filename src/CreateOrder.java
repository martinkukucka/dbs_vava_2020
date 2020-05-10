import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import orm.InvoiceEntity;

import javax.persistence.Query;
import java.sql.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

// Trieda pracuje s metodami na vytvorenie objednavky a faktury
public class CreateOrder {

    // Naplnenie comboboxu, ktory sluzi k vyberu auta
    void comboBoxInit(ComboBox<String> chooseCarCombobox) {
        // Tahanie dat z databazy, z joinutej tabulky
        try {
            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from crdb.vehicle inner join crdb.model" +
                    " on crdb.vehicle.modelid = crdb.model.id order by carbrand");
            while (resultSet.next()) {
                String chooseCar = (resultSet.getString("licenseplatenumber") + ", " + resultSet.getString("carbrand") +
                        ", " + resultSet.getString("carmodel") + ", " + resultSet.getString("color"));
                chooseCarCombobox.getItems().add(chooseCar);
            }
        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured " + e);
        }
    }

    // Vytvorenie faktury, metoda vrati id poslednej pridanej faktury
    private int createInvoice(String billTo, double amount) {
        // Zapis do tabulky invoice a zistenie id je realizovane pomocou Hibernate orm
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCompanyname("Pozicovna vozidiel");
        invoice.setAmount(amount);
        invoice.setBillto(billTo);

        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(invoice);
        String hql = "SELECT max(id) FROM InvoiceEntity";
        Query query = session.createQuery(hql);
        int result = (int) query.getResultList().get(0);

        session.close();

        JavaLogger.logger.log(Level.INFO, "Invoice created successfully");
        return result;
    }

    // Ulozenie udajov o pozicani auta
    void insertToDb(ComboBox<String> chooseCarCombobox, DatePicker pickUpDatepicker, DatePicker returnDatepicker) {
        String licencePlate = chooseCarCombobox.getValue();
        licencePlate = licencePlate.substring(licencePlate.indexOf(""), licencePlate.indexOf(","));

        // Zistenie udajov o pozicanom aute
        String sql = "select * from crdb.vehicle where licenseplatenumber = '" + licencePlate + "'";
        String sqlCarRental = "insert into crdb.carrental(pickupdate, returndate, customerid, vehicleid, invoiceid)" +
                " values (?, ?, ?, ?, ?)";
        try {
            Connection conn = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement stmts = conn.createStatement();
            ResultSet resultSet = stmts.executeQuery(sql);
            int rentedVehicleId = 0;
            double rentalPrice = 0;
            while (resultSet.next()) {
                rentedVehicleId = resultSet.getInt("id");
                Date date1 = Date.valueOf(pickUpDatepicker.getValue().plusDays(1));
                Date date2 = Date.valueOf(returnDatepicker.getValue().plusDays(1));
                long daysBetween = TimeUnit.DAYS.convert(date2.getTime() - date1.getTime(), TimeUnit.MILLISECONDS);
                rentalPrice = resultSet.getInt("price") * 0.002 * daysBetween;
            }

            String name = null;
            String sqlName = "select * from crdb.customer where id = '" + Login.USERID + "'";
            resultSet = stmts.executeQuery(sqlName);
            if (resultSet.next()) {
                name = resultSet.getString("name") + " " + resultSet.getString("surname");
            }

            int invoiceId = createInvoice(name, rentalPrice);

            // Ukladanie do tabulky carrental
            PreparedStatement preparedStatement = conn.prepareStatement(sqlCarRental);
            preparedStatement.setDate(1, Date.valueOf(pickUpDatepicker.getValue().plusDays(1)));
            preparedStatement.setDate(2, Date.valueOf(returnDatepicker.getValue().plusDays(1)));
            preparedStatement.setInt(3, Login.USERID);
            preparedStatement.setInt(4, rentedVehicleId);
            preparedStatement.setInt(5, invoiceId);
            preparedStatement.executeUpdate();

            JavaLogger.logger.log(Level.INFO, "New order added to database");

        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println(e.getMessage());
        }
    }


    // Inicializacia tabulky, ktoru mozno vidiet v gui
    void fillTable(TableView<RentalInfo> seeOrderTable, TableColumn<RentalInfo, String> fromColumn,
                   TableColumn<RentalInfo, String> toColumn, TableColumn<RentalInfo, String> brandColumnU,
                   TableColumn<RentalInfo, String> modelColumnU, TableColumn<RentalInfo,
            Double> priceColumnU) {

        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        brandColumnU.setCellValueFactory(new PropertyValueFactory<>("carBrandU"));
        modelColumnU.setCellValueFactory(new PropertyValueFactory<>("carModelU"));
        priceColumnU.setCellValueFactory(new PropertyValueFactory<>("rentalPrice"));

        ObservableList<RentalInfo> rentalInfod = FXCollections.observableArrayList();

        // Tahanie dat z databazy
        try {
            String sqlModel = ("select * from crdb.carrental inner join crdb.vehicle on " +
                    "crdb.carrental.vehicleid = crdb.vehicle.id inner join crdb.model on " +
                    "crdb.vehicle.modelid = crdb.model.id inner join crdb.invoice on " +
                    "crdb.carrental.invoiceid = crdb.invoice.id where customerid =" + Login.USERID + "");

            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlModel);

            while (rs.next()) {
                RentalInfo rentalInfo = new RentalInfo(rs.getInt("id"), rs.getString("pickupdate"),
                        rs.getString("returndate"), rs.getString("carbrand"),
                        rs.getString("carmodel"), rs.getDouble("amount"));
                rentalInfod.add(rentalInfo);
            }

            seeOrderTable.setItems(rentalInfod);
        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
    }

}
