import java.sql.*;

public class Login {

    public boolean register(String newEmail) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select email from test.customer");
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                if (email.equals(newEmail)) {
                    System.out.println("false");
                    return false;
                }
            }

        } catch(SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
        return true;
    }

    public boolean logInto(String emailIns, String passwordIns) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select email, password from test.customer");
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                if (email.equals(emailIns) && password.equals(passwordIns)) {
                    return true;
                }
            }

        } catch(SQLException e) {
            System.out.println("SQL exception occured " + e);
        }
        System.out.println("false");
        return false;
    }


    public void insertToDatabase(String nameI, String phoneI, String id_card_numberI, String emailI, String passwordI, String cityI, String streetAddrI, String zipCodeI) {
        String sqlCustomer = "insert into test.customer(name, phone_number, id_card_number, email, password, address_id) values (?, ?, ?, ?, ?, ?)";
        String sqlAddress = "insert into test.address(city, street_address, zip_code) values (?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();
            int addr_id = 0;

            ResultSet rs = statement.executeQuery("select * from test.address");
            while (rs.next()) {

                if (rs.getString("city").equals(cityI) &&
                        rs.getString("street_address").equals(streetAddrI) &&
                        rs.getString("zip_code").equals(zipCodeI)) {
                    addr_id = rs.getInt("id");
                    PreparedStatement preparedStatementCustomer = connection.prepareStatement(sqlCustomer);
                    preparedStatementCustomer.setString(1, nameI);
                    preparedStatementCustomer.setString(2, phoneI);
                    preparedStatementCustomer.setString(3, id_card_numberI);
                    preparedStatementCustomer.setString(4, emailI);
                    preparedStatementCustomer.setString(5, passwordI);
                    preparedStatementCustomer.setInt(6, addr_id);
                    preparedStatementCustomer.executeUpdate();
                    return;
                }
            }

            PreparedStatement preparedStatementAddress = connection.prepareStatement(sqlAddress);
            preparedStatementAddress.setString(1, cityI);
            preparedStatementAddress.setString(2, streetAddrI);
            preparedStatementAddress.setString(3, zipCodeI);
            preparedStatementAddress.executeUpdate();

//            ResultSet resultSet = statement.executeQuery("select id from test.address");
            ResultSet resultSet = statement.executeQuery("select id from test.address ORDER BY id DESC LIMIT 1");

            if (resultSet.next()) {
                addr_id = resultSet.getInt("id");
//                System.out.println(addr_id);
            }

            PreparedStatement preparedStatementCustomer = connection.prepareStatement(sqlCustomer);
            preparedStatementCustomer.setString(1, nameI);
            preparedStatementCustomer.setString(2, phoneI);
            preparedStatementCustomer.setString(3, id_card_numberI);
            preparedStatementCustomer.setString(4, emailI);
            preparedStatementCustomer.setString(5, passwordI);
            preparedStatementCustomer.setInt(6, addr_id);
            preparedStatementCustomer.executeUpdate();

        } catch(SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }
    }

}
