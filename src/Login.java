import java.sql.*;

public class Login {

    public boolean register(String newEmail) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select email from test.zakaznik");
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

            ResultSet resultSet = statement.executeQuery("select email, heslo from test.zakaznik");
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("heslo");
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


    public void insertToDatabase(String menoI, String mobilI, String cisloOpI, String emailI, String hesloI) {
        String sql = "insert into test.zakaznik(meno, mobil, cislo_op, email, heslo) values (?, ?, ?, ?, ?)";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, menoI);
            preparedStatement.setString(2, mobilI);
            preparedStatement.setString(3, cisloOpI);
            preparedStatement.setString(4, emailI);
            preparedStatement.setString(5, hesloI);
            preparedStatement.executeUpdate();

        } catch(SQLException e) {
            System.out.println("SQL exception occured: " + e);
        }
    }

}
