import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import orm.InvoiceEntity;

import javax.persistence.Query;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    void insertToDb(ComboBox<String> chooseCarCombobox, DatePicker pickUpDatepicker, DatePicker returnDatepicker, Label wrongDateLabel) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate finPickUpDate = LocalDate.parse(String.valueOf(Date.valueOf(pickUpDatepicker.getValue())), dtf);
        LocalDate finReturnDate = LocalDate.parse(String.valueOf(Date.valueOf(returnDatepicker.getValue())), dtf);
        LocalDate finCurrentDate = LocalDate.parse(String.valueOf(new java.sql.Date(System.currentTimeMillis())), dtf);

        long daysFromToday = ChronoUnit.DAYS.between(finCurrentDate, finPickUpDate);
        long checkNumOfDays = ChronoUnit.DAYS.between(finPickUpDate, finReturnDate);
        System.out.println(pickUpDatepicker.getValue());
        System.out.println(returnDatepicker.getValue());
        System.out.println ("Days: " + checkNumOfDays);
        if (daysFromToday < 0) {
//            System.out.println("Neda sa objednat");
            wrongDateLabel.setText(Login.rb.getString("today"));
            wrongDateLabel.setTextFill(Color.RED);
            return;
        }
        if (checkNumOfDays == 0) {
            wrongDateLabel.setText(Login.rb.getString("oneDay"));
            wrongDateLabel.setTextFill(Color.RED);
            return;
        }
        if (checkNumOfDays < 0) {
            wrongDateLabel.setText(Login.rb.getString("dateDiff"));
            wrongDateLabel.setTextFill(Color.RED);
            return;
        }

        String licencePlate = chooseCarCombobox.getValue();
        licencePlate = licencePlate.substring(licencePlate.indexOf(""), licencePlate.indexOf(","));

        // Zistenie udajov o pozicanom aute
        String sql = "select * from crdb.vehicle where licenseplatenumber = '" + licencePlate + "'";
        String sqlCarRental = "insert into crdb.carrental(pickupdate, returndate, customerid, vehicleid, invoiceid)" +
                " values (?, ?, ?, ?, ?)";

        Connection conn = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
        conn.setAutoCommit(false);
        try {
            Statement stmts = conn.createStatement();
            ResultSet resultSet = stmts.executeQuery(sql);
            int rentedVehicleId = 0;
            double rentalPrice = 0;
            if (resultSet.next()) {
                rentedVehicleId = resultSet.getInt("id");

                if (checkRentedCars(rentedVehicleId, pickUpDatepicker, returnDatepicker, wrongDateLabel)) {
                    return;
                }

                Date date1 = Date.valueOf(pickUpDatepicker.getValue().plusDays(1));
                Date date2 = Date.valueOf(returnDatepicker.getValue().plusDays(1));
                long daysBetween = TimeUnit.DAYS.convert(date2.getTime() - date1.getTime(), TimeUnit.MILLISECONDS);
                rentalPrice = resultSet.getInt("price") * 0.002 * daysBetween;
                System.out.println(rentalPrice);
                rentalPrice = Math.round(rentalPrice);
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

            wrongDateLabel.setText(Login.rb.getString("orderSuccess"));
            wrongDateLabel.setTextFill(Color.GREEN);

            JavaLogger.logger.log(Level.INFO, "New order added to database");

        } catch (SQLException e) {
            conn.rollback();
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println(e.getMessage());
        }
        conn.commit();
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

    private boolean checkRentedCars(int rentedVehicleId, DatePicker pickUpDatepicker, DatePicker returnDatepicker, Label wrongDateLabel) {

        try {
            String sql = ("select * from crdb.carrental where vehicleid = " + rentedVehicleId + "");

            Connection connection = DriverManager.getConnection(Main.DBcon, Main.DBuser, Main.DBpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate carRentedFrom;
            LocalDate carRentedTo = null;

            while (resultSet.next()) {
                carRentedFrom = LocalDate.parse(String.valueOf(resultSet.getDate("pickupdate").toLocalDate()), dtf);
                carRentedTo = LocalDate.parse(String.valueOf(resultSet.getDate("returndate").toLocalDate()), dtf);

                LocalDate pickUpDate = LocalDate.parse(String.valueOf(Date.valueOf(pickUpDatepicker.getValue())), dtf);
                LocalDate returnDate = LocalDate.parse(String.valueOf(Date.valueOf(returnDatepicker.getValue())), dtf);
//                System.out.println(carRentedFrom + " " + carRentedTo);

                if (!(pickUpDate.isBefore(carRentedFrom) || pickUpDate.isAfter(carRentedTo)) ||
                        !(returnDate.isBefore(carRentedFrom) || returnDate.isAfter(carRentedTo))) {
                    wrongDateLabel.setText(Login.rb.getString("rentedAlready"));
                    wrongDateLabel.setTextFill(Color.RED);
                    return true;
                }
            }

            if (carRentedTo == null) {
                return false;
            }

//            LocalDate pickUpDate = LocalDate.parse(String.valueOf(Date.valueOf(pickUpDatepicker.getValue())), dtf);
//            wrongDateLabel.setText("Vozidlo je nedostupne");
//            wrongDateLabel.setTextFill(Color.RED);

//            long daysBetween = ChronoUnit.DAYS.between(carRentedTo, pickUpDate);
//            return daysBetween < 0;

        } catch (SQLException e) {
            JavaLogger.logger.log(Level.WARNING, "Database problem");
            System.out.println("SQL exception occured: " + e);
        }
        return false;
    }

}
