package hide;

import java.sql.*;

public class printTables {

    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    "root", "root");
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select name, phone_number, city, address_id from test.customer, test.address where customer.address_id = address.id;");

            while (rs.next()) {
                System.out.println(rs.getString("name") + " " +
                        rs.getString("phone_number") + " " +
                        rs.getString("city") + " " +
                        rs.getString("address_id"));
            }
        } catch(SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
    }

}
